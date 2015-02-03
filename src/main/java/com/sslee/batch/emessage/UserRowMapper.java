package com.sslee.batch.emessage;

import com.sslee.batch.emessage.vo.EmailVO;
import com.sslee.batch.stepflow.domain.User;

public class UserRowMapper implements MessageRowMapper<User, EmailVO> {

	@Override
	public EmailVO convertMessage(User user, String messageId) {
		
		EmailVO bean = new EmailVO();
		bean.setMessageId(messageId);
		bean.setEmail(user.getEmail());
		
		return bean;
	}

}
