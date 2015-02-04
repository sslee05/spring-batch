package com.sslee.batch.cronjob;

import java.io.Serializable;

public class JobParameter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String jobName;
	private boolean executeFlag;
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public boolean isExecuteFlag() {
		return executeFlag;
	}
	public void setExecuteFlag(boolean executeFlag) {
		this.executeFlag = executeFlag;
	}
	
	
	
	

}
