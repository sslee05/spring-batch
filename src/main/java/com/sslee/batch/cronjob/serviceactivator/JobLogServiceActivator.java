package com.sslee.batch.cronjob.serviceactivator;

import java.util.Date;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.ExitStatus;
import org.springframework.integration.annotation.Header;

import com.sslee.batch.cronjob.log.Job;

public class JobLogServiceActivator {
	
	private SqlSessionTemplate sqlSessionTemplate; 
	
	public Job getJobLog(@Header("jobName") String jobName,Map<String,String> test) {
		
		System.out.println("jobName:"+jobName+" command:"+test);
		System.out.println("jobName:"+jobName+" command:"+test);
		
		Job param = new Job(jobName);
		//Job result = sqlSessionTemplate.selectOne("job.getJob",param);
		Job result = sqlSessionTemplate.selectOne("job.getJobWithDetails",param);
		
		Job job = new Job("XMP0110",new Date());
		job.setExitStatus(ExitStatus.COMPLETED);
		job.setTotalCount(20);
		job.setSuccessCount(20);
		
		
		return result;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	

}
