package com.sslee.batch.stepflow.helper;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;

import com.sslee.batch.common.Restoration;

public class UserDataRestoration implements Restoration {

	@Override
	public ExitStatus restorate(JobExecution jobExecution) {
		
		System.out.println("#########=> listener restorated ");
		
		return new ExitStatus("RESTORATION_COMPLETED");
	}

}
