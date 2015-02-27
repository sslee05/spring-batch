package com.sslee.batch.cronjob.executor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.batch.core.ExitStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.sslee.batch.cronjob.job.TaskletItemService;
import com.sslee.batch.cronjob.log.Job;
import com.sslee.batch.cronjob.log.JobItemTrace;
import com.sslee.batch.cronjob.log.JobTracer;
import com.sslee.batch.cronjob.util.JobParameterHolder;
import com.sslee.batch.cronjob.util.ThreadCallbackListenerHolder;

public class TaskletJobExecutor<T> extends AbstractJobExecutor<T> {
	
	private ThreadPoolTaskExecutor taskExecutor;
	private JobTracer jobTracer;
	private int threadCount = 1;
	private int minCount = 10;
	
	
	private List<List<T>> getSubItems(List<T> items) {
		
		int totalCount = items.size();
		if(totalCount <= this.minCount || this.threadCount == 1) {
			List<List<T>> results = new ArrayList<List<T>>();
			results.add(items);
			
			return results;
		}
		
		int index = 0;
		int lastIndex = totalCount/this.threadCount;
		int threadIdx = this.threadCount;
		
		List<List<T>> results = new ArrayList<List<T>>();
		
		while(threadIdx > 0) {
			
			List<T> subs = items.subList(index,lastIndex);
			threadIdx--;
			
			index = lastIndex;
			lastIndex = threadIdx <= 1 ? totalCount : lastIndex+lastIndex;
			
			results.add(subs);
		}
		
		return results;
	}
	
	private List<Job> getJobs(int itemSize,String jobName) {
		
		List<Job> jobs = new ArrayList<Job>();
		for(int i=0;i<itemSize;i++) {
			Job job = new Job(jobName,new Date());
			jobs.add(job);
		}
		
		return jobs;
	}
	
	private void traceLog(final Job job,ListenableFuture<Job> listenableFuture,final List<T> items,
			final ThreadCallbackListenerHolder hodler,final JobParameterHolder jobParameterHolder) {
		
		listenableFuture.addCallback(new ListenableFutureCallback<Job> () {

			@Override
			public void onSuccess(Job result) {
				
				job.setTotalCount(items.size());
				if(jobParameterHolder.constainsKey("SKIP_ITEMS")) {
					job.addItemTraces((List<JobItemTrace>)jobParameterHolder.getData("SKIP_ITEMS"));
				}
				hodler.addDoneFlag(true);
			}

			@Override
			public void onFailure(Throwable t) {
				//always not call
				hodler.addDoneFlag(true);
			}
			
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run(ConcurrentMap<String,String> parameters) {
		
		List<T> items = null;
		
		JobParameterHolder jobParameterHolderParam = null;
		if(parameters != null) jobParameterHolderParam = new JobParameterHolder(parameters.entrySet());
		else jobParameterHolderParam = new JobParameterHolder();
		
		
		TaskletItemService processService = (TaskletItemService)super.getProcessItemService();
		Date startDate = new Date();
		try {
			items = processService.getItems(jobParameterHolderParam);
		}
		catch(Exception e) {
			
			e.printStackTrace();
			Job job = new Job(super.getJobName(),new Date());
			job.setStartDate(startDate);
			job.setExitStatus(ExitStatus.FAILED);
			job.setEndDate(new Date());
			JobItemTrace itemTrace = new JobItemTrace(super.getJobName());
			itemTrace.setTrace(e);
			job.addJobItemTraces(itemTrace);
			this.jobTracer.insertJob(job);
			
			return;
		}
		
		if(items == null) return;
		
		List<List<T>> subItems = this.getSubItems(items);
		List<Job> jobs = this.getJobs(subItems.size(), super.getJobName());
		ThreadCallbackListenerHolder listenerHolder = new ThreadCallbackListenerHolder(subItems.size());
		
		try {
			
			int i=0;
			for(final List<T> datas : subItems) {
				
				JobParameterHolder jobParameterHolder = null;
				if(parameters != null) jobParameterHolder = new JobParameterHolder(parameters.entrySet());
				else jobParameterHolder = new JobParameterHolder();
				
				if(i== 1) {
					jobParameterHolder.addData("isTest", "Y");//TEST
				}
				
				JobTasklet jobTasklet = new JobTasklet(processService,jobParameterHolder,datas);
				ListenableFuture<Job> listenableFuture = (ListenableFuture<Job>)this.taskExecutor.submitListenable(jobTasklet);
				this.traceLog(jobs.get(i), listenableFuture, datas,listenerHolder,jobParameterHolder);
			    i++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			while(!listenerHolder.isDone() || this.taskExecutor.getActiveCount() > 0 ) {}
			
			Job resultJob = null;
			int i= 0;
			
			for(Job job : jobs ) {
				if(i == 0) resultJob = job;
				else  {
					List<JobItemTrace> itemTraces = job.getJobItemTraces();
					
					resultJob.addSkipCount(itemTraces == null? 0: itemTraces.size());
					resultJob.addTotalCount(job.getTotalCount());
					resultJob.addItemTraces(job.getJobItemTraces());
				}
				i++;
			}
			
			resultJob.setSuccessCount(resultJob.calculateSuccessCount());
			resultJob.setEndDate(new Date());
			if(resultJob.getTotalCount() == resultJob.getSuccessCount()) resultJob.setExitStatus(ExitStatus.COMPLETED);
			else resultJob.setExitStatus(ExitStatus.FAILED);
			resultJob.injectJobItemTrace();
			
			this.jobTracer.insertJob(resultJob);
			
		}
	}
	
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public void setJobTracer(JobTracer jobTracer) {
		this.jobTracer = jobTracer;
	}
	
}
