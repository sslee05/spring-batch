package com.sslee.batch.cronjob.log;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public class JobTracerImpl implements JobTracer {
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public Job getJobKey(Job job) {
		return this.sqlSessionTemplate.selectOne("job.getJobKey", job);
	}

	@Override
	public void insertJob(Job job) {
		System.out.println("###=>"+job);
		if(job.getJobItemTraces() != null) {
			List<JobItemTrace> traces = job.getJobItemTraces();
			for(JobItemTrace item : traces) {
				System.out.println("###=>"+item.getTraceLog());
			}
		}
		Job old = this.sqlSessionTemplate.selectOne("job.getJobKey", job);
		if(old == null) sqlSessionTemplate.insert("job.addJob", job);
		else sqlSessionTemplate.update("job.updateJob",job);
		this.insertJobTrace(job);
	}

	@Override
	public void updateJob(Job job) {
		sqlSessionTemplate.update("job.updateJob",job);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void insertJobTrace(Job job) {
		
		List<JobItemTrace> items = job.getJobItemTraces();
		if(items == null) return; 
				
		for(JobItemTrace item : items) {
			item.setStartDate(job.getStartDate());
			item.setEndDate(job.getEndDate());
			this.sqlSessionTemplate.insert("job.addJobTrace", item);
		}
		
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void insertJobTrace(JobItemTrace jobItemTrace) {
		this.sqlSessionTemplate.insert("job.addJobTrace", jobItemTrace);
		
	}
	
	@Override
	public void updateJobTrace(Job job) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void updateJobTrace(JobItemTrace jobItemTrace) {
		// TODO Auto-generated method stub
		
	}
	
}
