package com.sslee.batch.stepflow.infrastructure;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.item.ItemWriter;

import com.sslee.batch.exception.BatchException;
import com.sslee.batch.stepflow.domain.User;

public class UserWriter implements ItemWriter<User> {
	
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public void write(List<? extends User> items) throws Exception {
		
		//throw new BatchException("##################");
		
		//duplicate exception 발생 
		//Attempt to update step execution id=1062 with wrong version (1)
		//can't controll step flow
		int i = 0;
		for(User item : items) {
			//if(i>0) throw new BatchException("custom exception");
			sqlSessionTemplate.insert("batch.user.addUser", item);
			i++;
		}
	
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	

}
