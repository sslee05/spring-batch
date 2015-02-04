package com.sslee.batch.cronjob;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

import com.sslee.batch.exception.BatchException;

@Component
public class JobBootHandler {
	
	@Autowired @Qualifier("requestChannel")
	private MessageChannel messageChannel;
	
	@Autowired
	private List<JobExecutor> jobExecutorList;
	private Map<String,JobExecutor> jobExecutors;
	
	@PostConstruct
	public void init() {
		
		this.jobExecutors = new HashMap<String,JobExecutor>();
		for(JobExecutor bean : jobExecutorList) {
			if(this.jobExecutors.containsKey(bean.getJobName()))
				throw new BatchException(bean.getJobName() +" jobExecutor name must be unique");
			
			this.jobExecutors.put(bean.getJobName(), bean);
			
			bean.executeJob();
		}
	}
	
	public String handleJob(JobParameter jobParameter) {
		JobExecutor jobExecutor = this.jobExecutors.get(jobParameter.getJobName());
		
		if(jobExecutor == null) return "FAIL";
		jobExecutor.setStop(!jobParameter.isExecuteFlag());
		
		return "COMPLELETED";
				
	}
	
	

}
