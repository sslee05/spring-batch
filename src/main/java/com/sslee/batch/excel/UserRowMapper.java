package com.sslee.batch.excel;

import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.support.rowset.RowSet;

import com.sslee.batch.stepflow.domain.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(RowSet rs) throws Exception {
		User user = new User();
		user.setUserId(rs.getColumnValue(0));
		user.setEmail(rs.getColumnValue(1));
		user.setUserName(rs.getColumnValue(2));
		user.setAge(Float.valueOf(rs.getColumnValue(4)).intValue());
		return user;
	}
	
	public static void main(String[] args) {
		
		//System.out.println(Float.valueOf("1974.0").intValue());
	}

}
