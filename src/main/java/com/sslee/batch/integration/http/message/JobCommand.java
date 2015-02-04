package com.sslee.batch.integration.http.message;

import java.io.Serializable;

public class JobCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String systemName;
	private String jobName;
	
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	

}
