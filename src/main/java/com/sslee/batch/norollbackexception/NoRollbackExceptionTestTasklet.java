package com.sslee.batch.norollbackexception;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.sslee.batch.jotm.domain.User;

public class NoRollbackExceptionTestTasklet implements Tasklet {
	
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		User user01 = new User("sslee01","sslee01","sslee01@gmail.com",1);
		User user02 = new User("sslee01","sslee01","sslee01@gmail.com",1);
		User user03 = new User("sslee02","sslee02","sslee02@gmail.com",2);
		
		List<User> datas = new ArrayList<User>();
		datas.add(user01);
		datas.add(user02);
		datas.add(user03);
		
		for(User user : datas) {
			
			try {
				sqlSessionTemplate.insert("batch.user.addUser", user);
			}
			catch(Exception e) {
				return RepeatStatus.FINISHED;
			}
		}
		return RepeatStatus.FINISHED;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	

}
