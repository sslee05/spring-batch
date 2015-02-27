package com.sslee.batch.skiplog.listener;

import org.springframework.batch.core.SkipListener;

import com.sslee.batch.stepflow.domain.User;

public class UserSkipListener implements SkipListener<User, User> {

	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInWrite(User item, Throwable t) {
		System.out.println("############# on write =>"+item.getUserId());
		
	}

	@Override
	public void onSkipInProcess(User item, Throwable t) {
		System.out.println("############# on process =>"+item.getUserId());
		
	}

}
