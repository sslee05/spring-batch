package com.sslee.batch.stepflow.infrastructure;

import org.springframework.batch.item.ItemProcessor;

import com.sslee.batch.exception.BatchException;
import com.sslee.batch.stepflow.domain.User;

public class UserProcessor implements ItemProcessor<User, User> {

	@Override
	public User process(User item) throws Exception {
		
		return item;
	}

}
