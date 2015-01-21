package com.sslee.batch.stepflow.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class UserProcessStepExecutionListener implements StepExecutionListener {

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		String exiteCode = stepExecution.getExitStatus().getExitCode();
		System.out.println(exiteCode);
		
		return stepExecution.getExitStatus();
	}

	@Override
	public void beforeStep(StepExecution arg0) {
		// TODO Auto-generated method stub

	}

}
