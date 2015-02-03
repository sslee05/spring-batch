package com.sslee.batch.idgeneration;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.sslee.batch.common.ExecutorBatchItemWriter;
import com.sslee.batch.emessage.MessageSender;
import com.sslee.batch.stepflow.domain.User;

public class MessageWriter extends ExecutorBatchItemWriter<User> {
	
	private SqlSessionTemplate sqlSessionTemplate;
	private MessageSender<User> messageSender;

	public MessageWriter(SqlSessionTemplate sqlSessionTemplate) {
		super(sqlSessionTemplate);
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	protected void writeItems(List<? extends User> items) {
		
		messageSender.sendMessage(items);
		
		for(User user : items) {
			sqlSessionTemplate.insert("batch.user.addUser", user);
		}
	}

	public void setMessageSender(MessageSender<User> messageSender) {
		this.messageSender = messageSender;
	}
	
}
