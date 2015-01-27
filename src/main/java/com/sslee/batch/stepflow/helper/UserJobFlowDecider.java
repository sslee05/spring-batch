package com.sslee.batch.stepflow.helper;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class UserJobFlowDecider implements JobExecutionDecider {

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution,StepExecution stepExecution) {
		
		String exiteCode = stepExecution.getExitStatus().getExitCode();
		
		if(ExitStatus.FAILED.getExitCode().equals(exiteCode))
			return new FlowExecutionStatus("FAIL_JOB_PROCESS");
		else
			return new FlowExecutionStatus(jobExecution.getExitStatus().toString());
	}

}
