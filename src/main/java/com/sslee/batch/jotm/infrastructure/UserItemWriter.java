package com.sslee.batch.jotm.infrastructure;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.item.ItemWriter;

import com.sslee.batch.jotm.domain.Book;
import com.sslee.batch.jotm.domain.User;

public class UserItemWriter implements ItemWriter<User> {

	private SqlSessionTemplate oracleXASqlSessionTemplate;
	private SqlSessionTemplate pgsqlXASqlSessionTemplate;

	@Override
	public void write(List<? extends User> userList) throws Exception {
		
		for(User user : userList) {
			oracleXASqlSessionTemplate.update("batch.user.addUser", user);
		}
		
		Book test = new Book();
		test.setBookId(1);
		test.setName("testBook");
		
		pgsqlXASqlSessionTemplate.insert("batch.user.addBook", test);
		
	}

	public void setOracleXASqlSessionTemplate(
			SqlSessionTemplate oracleXASqlSessionTemplate) {
		this.oracleXASqlSessionTemplate = oracleXASqlSessionTemplate;
	}

	public void setPgsqlXASqlSessionTemplate(
			SqlSessionTemplate pgsqlXASqlSessionTemplate) {
		this.pgsqlXASqlSessionTemplate = pgsqlXASqlSessionTemplate;
	}
	

}
