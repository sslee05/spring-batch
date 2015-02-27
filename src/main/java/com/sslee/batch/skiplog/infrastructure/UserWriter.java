package com.sslee.batch.skiplog.infrastructure;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.sslee.batch.common.ExecutorBatchItemWriter;
import com.sslee.batch.exception.BatchException;
import com.sslee.batch.stepflow.domain.User;

//public class UserWriter extends ExecutorBatchItemWriter<User> {
public class UserWriter extends ExecutorBatchItemWriter<User> {
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	public UserWriter(SqlSessionTemplate sqlSessionTemplate) {
		super(sqlSessionTemplate);
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	protected void writeItems(List<? extends User> items) {
		
//		int i=0;
		for(User item : items) {
			//if(item.getUserId().equals("sslee04") || item.getUserId().equals("sslee06"))
			//	throw new BatchException("skip");
//			if(i>0) {
//				String test = null;
//				test.split(";");
//			}
			sqlSessionTemplate.insert("batch.user.addUser", item);
			
//			i++;
		}
		
	}
	
	

}
