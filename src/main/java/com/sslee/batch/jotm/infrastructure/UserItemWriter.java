package com.sslee.batch.jotm.infrastructure;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.sslee.batch.common.ExecutorBatchItemWriter;
import com.sslee.batch.jotm.domain.Book;
import com.sslee.batch.jotm.domain.User;

public class UserItemWriter extends ExecutorBatchItemWriter<User> {
	
	private SqlSessionTemplate oracleXASqlSessionTemplate;
	private SqlSessionTemplate pgsqlXASqlSessionTemplate;

	public UserItemWriter(List<SqlSessionTemplate> sqlSessionTemplates) {
		
		super(sqlSessionTemplates);
		
		this.oracleXASqlSessionTemplate = sqlSessionTemplates.get(0);
		this.pgsqlXASqlSessionTemplate = sqlSessionTemplates.get(1);
		
	}

	@Override
	protected void writeItems(List<? extends User> items) {
		
		for(User user : items) {
			oracleXASqlSessionTemplate.update("batch.user.addUser", user);
		}
		
		Book test = new Book();
		test.setBookId(1);
		test.setName("testBook");
		
		pgsqlXASqlSessionTemplate.insert("batch.user.addBook", test);
		
	}

}
