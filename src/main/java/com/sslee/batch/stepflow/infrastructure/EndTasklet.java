package com.sslee.batch.stepflow.infrastructure;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class EndTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution,ChunkContext chunkContext) throws Exception {
		System.out.println("####### END JOB #####");
		return RepeatStatus.FINISHED;
	}

}
