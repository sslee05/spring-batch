package com.sslee.batch.cronjob.executor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.batch.core.ExitStatus;

import com.sslee.batch.cronjob.job.RepeatOneProcessItemService;
import com.sslee.batch.cronjob.log.Job;
import com.sslee.batch.cronjob.log.JobItemTrace;
import com.sslee.batch.cronjob.log.JobTracer;
import com.sslee.batch.exception.BatchException;

public class RepeatOneItemJobExecutor<T> extends AbstractJobExecutor<T> {
	
	private JobTracer jobTracer;
	
	@Override
	public void initValidate() {
		
		super.initValidate();
		if(super.getJobName() == null) throw new BatchException("jobName must be not null");
		if(super.getReadItemService() == null)
			throw new BatchException("one item transaction type must be readItemService but is null");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void run(ConcurrentMap<String,String> parameters) {
		
		List<T> items = null;
		
		RepeatOneProcessItemService processService = (RepeatOneProcessItemService)super.getProcessItemService();
		
		Date startDate = new Date();
		try {
			items = super.getReadItemService().getItems(parameters);
		}
		catch(Exception e) {
			e.printStackTrace();
			Job job = new Job(super.getJobName(),startDate);
			job.setExitStatus(ExitStatus.FAILED);
			job.setEndDate(new Date());
			JobItemTrace itemTrace = new JobItemTrace(super.getJobName());
			itemTrace.setTrace(e);
			job.addJobItemTraces(itemTrace);
			this.jobTracer.insertJob(job);
			
			return;
		}
		
		if(items == null) return;
		
		Job job = new Job(super.getJobName(),new Date());
		job.setExitStatus(ExitStatus.EXECUTING);
		job.setTotalCount(items.size());
		
		int skipCount = 0;
		
		for(T item : items) {
			try {
				processService.execute(item,parameters);
			}catch(Exception e) {
				skipCount++;
				e.printStackTrace();
				JobItemTrace jobItemTrace = new JobItemTrace(super.getJobName());
				jobItemTrace.setTrace(e, item);
				job.addJobItemTraces(jobItemTrace);
			}
		}
		
		job.setSuccessCount(job.calculateSuccessCount());
		job.setEndDate(new Date());
		job.setExitStatus(skipCount >0 ? ExitStatus.FAILED : ExitStatus.COMPLETED);
		this.jobTracer.insertJob(job);
	}
	
	public void setJobTracer(JobTracer jobTracer) {
		this.jobTracer = jobTracer;
	}
}
