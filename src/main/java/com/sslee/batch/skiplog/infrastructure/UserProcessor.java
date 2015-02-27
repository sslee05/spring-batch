package com.sslee.batch.skiplog.infrastructure;

import org.springframework.batch.item.ItemProcessor;

import com.sslee.batch.stepflow.domain.User;

public class UserProcessor implements ItemProcessor<User, User> {

	@Override
	public User process(User item) throws Exception {
		//if(item.getUserId().equals("sslee04") || item.getUserId().equals("sslee07"))
			//return null;
			//throw new BatchException("test");
		
		
		return item;
	}

}
