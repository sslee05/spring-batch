package com.sslee.batch.cronjob;

import java.util.concurrent.ConcurrentMap;

import com.sslee.batch.exception.BatchException;


public abstract class AbstractJobExecutor<T> implements JobExecutor{
	
	private ReadItemService<T> readItemService;
	private ProcessItemService<T> processItemService;
	private long repeatMillSeconds;
	private String jobName;
	private boolean isStop;
	private ConcurrentMap<String,String> parameters;
	
	public void initValidate() {
		
		if(this.jobName == null) throw new BatchException("jobName must be not null");
		if(this.processItemService == null)
			throw new BatchException("processItemService must be not null");
	}
	
	@Override
	public void executeJob() {
		
		while(!isStop) {
			try {
				run(parameters);
				Thread.sleep(repeatMillSeconds);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	abstract protected void run(ConcurrentMap<String,String> parameters);
	
	@Override
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public ProcessItemService<T> getProcessItemService() {
		return processItemService;
	}

	public void setProcessItemService(ProcessItemService<T> processItemService) {
		this.processItemService = processItemService;
	}

	public long getRepeatMillSeconds() {
		return repeatMillSeconds;
	}

	public void setRepeatMillSeconds(long repeatMillSeconds) {
		this.repeatMillSeconds = repeatMillSeconds;
	}
	
	@Override
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public boolean isStop() {
		return isStop;
	}

	public ReadItemService<T> getReadItemService() {
		return readItemService;
	}

	public void setReadItemService(ReadItemService<T> readItemService) {
		this.readItemService = readItemService;
	}

	public ConcurrentMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(ConcurrentMap<String, String> parameters) {
		this.parameters = parameters;
	}
	
	
	
	

}
