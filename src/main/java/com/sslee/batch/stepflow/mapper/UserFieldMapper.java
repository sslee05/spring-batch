package com.sslee.batch.stepflow.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.sslee.batch.stepflow.domain.User;

public class UserFieldMapper implements FieldSetMapper<User> {

	@Override
	public User mapFieldSet(FieldSet fieldSet) throws BindException {
		User user = new User();
		user.setUserName(fieldSet.readString("userName"));
		user.setUserId(fieldSet.readString("userId"));
		user.setEmail(fieldSet.readString("email"));
		user.setAge(fieldSet.readInt("age"));
		return user;
	}

}
