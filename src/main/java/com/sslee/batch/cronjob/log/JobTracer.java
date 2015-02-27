package com.sslee.batch.cronjob.log;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JobTracer {
	
	void insertJob(Job job);
	void updateJob(Job job);
	void insertJobTrace(JobItemTrace jobItemTrace);
	void insertJobTrace(Job job);
	void updateJobTrace(JobItemTrace jobItemTrace);
	void updateJobTrace(Job job);

}
