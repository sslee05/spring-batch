package com.sslee.batch.stepflow.infrastructure;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BackupTasklet implements Tasklet {
	
	private String successFlag;

	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		
		if("Y".equals(this.successFlag)) {
			System.out.printf("success backup process");
			stepContribution.setExitStatus(new ExitStatus("CUSTOM_SUCCESS"));
		}
		else {
			System.out.printf("fail backup process");
			stepContribution.setExitStatus(ExitStatus.FAILED);
		}
		
		return RepeatStatus.FINISHED;
	}
	
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

}
