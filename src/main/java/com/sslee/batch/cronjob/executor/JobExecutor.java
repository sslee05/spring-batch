package com.sslee.batch.cronjob.executor;

public interface JobExecutor extends Runnable {
	
	void setStop(boolean isStop);
	boolean isStop();
	String getJobName();
	void setRepeatMillSeconds(long repeatMillSeconds);

}
