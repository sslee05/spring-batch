package com.sslee.batch.cronjob.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.sslee.batch.cronjob.job.TaskletItemService;
import com.sslee.batch.cronjob.log.JobItemTrace;
import com.sslee.batch.cronjob.util.JobParameterHolder;

public class JobTasklet<T> implements Callable<T> {
	
	private TaskletItemService<T> taskletItemService;
	private JobParameterHolder jobParameterHolder;
	private List<T> items;
	
	public JobTasklet(TaskletItemService<T> taskletItemService,JobParameterHolder jobParameterHodler,List<T> items) {
		this.taskletItemService = taskletItemService;
		this.jobParameterHolder = jobParameterHodler;
		this.items = items;
	}

	@Override
	public T call() throws Exception {
		
		List<JobItemTrace> skipItems = new ArrayList<JobItemTrace>();
		
		for(T item : items) {
			
			try {
				taskletItemService.execute(item,this.jobParameterHolder);
			}
			catch(Exception e) {
				e.printStackTrace();
				JobItemTrace trace = new JobItemTrace();
				trace.setItemId(item.toString());
				trace.setTrace(e);
				skipItems.add(trace);
				jobParameterHolder.addData("SKIP_ITEMS", skipItems);
			}
		}
		
		return null;
	}
	

}
