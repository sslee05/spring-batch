package com.sslee.batch.cronjob;

public interface JobExecutor {
	
	void executeJob();
	void setStop(boolean isStop);
	String getJobName();

}
