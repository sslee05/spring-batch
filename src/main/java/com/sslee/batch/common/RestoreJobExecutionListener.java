package com.sslee.batch.common;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class RestoreJobExecutionListener implements JobExecutionListener {
	
	private Restoration restoration;
	private ExitStatus exitStatus;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(this.exitStatus == null) this.exitStatus = new ExitStatus(ExitStatus.FAILED.getExitCode());
		
		if(jobExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode()) &&
				restoration != null)
			restoration.restorate(jobExecution);
	}
	
	public RestoreJobExecutionListener(Restoration restoration) {
		this.restoration = restoration;
	}
	
	public void setOperateExitStatus(String code) {
		this.exitStatus = new ExitStatus(code);
	}

}
