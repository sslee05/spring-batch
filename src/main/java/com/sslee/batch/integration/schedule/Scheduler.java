package com.sslee.batch.integration.schedule;

import org.springframework.integration.core.PollableChannel;



public class Scheduler {
	
	private PollableChannel requestChannel;
	
	public void callJobLauncher() {
		System.out.println("########### schedule start");
		//Message<String> message = MessageBuilder.withPayload("testJob").build();
		//requestChannel.send(message);
	}

	public void setRequestChannel(PollableChannel requestChannel) {
		this.requestChannel = requestChannel;
	}
	
	

}
