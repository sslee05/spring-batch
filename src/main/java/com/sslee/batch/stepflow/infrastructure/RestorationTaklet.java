package com.sslee.batch.stepflow.infrastructure;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class RestorationTaklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution StepContribution, ChunkContext chunkContext) throws Exception {
		
		System.out.printf("###################=>restoration processing");
		
		return RepeatStatus.FINISHED;
	}

}
