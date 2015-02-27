package com.sslee.batch.skiplog.listener;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

public class JobLogListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		Collection<StepExecution> stepExcutions = jobExecution.getStepExecutions();
		
		int readCount = 0;
		int writeCount = 0;
		int commitCount = 0;
		int skipCount = 0;
		int readSkipCount = 0;
		int filterCount = 0;
		int rollbackCount = 0;
		
		if(stepExcutions != null && stepExcutions.size() > 0) {
			Iterator<StepExecution> iter = stepExcutions.iterator();
			
			while(iter.hasNext()) {
				
			}
		}
	}
	
	private String getLog(StepExecution stepExecution) {
		
		StringBuilder sb = new StringBuilder();
		String stepName = stepExecution.getStepName();
		
		
		return null;
	}

}
