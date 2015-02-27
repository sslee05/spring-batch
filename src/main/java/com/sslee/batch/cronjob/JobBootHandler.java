package com.sslee.batch.cronjob;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.integration.MessageChannel;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.sslee.batch.cronjob.executor.JobExecutor;
import com.sslee.batch.exception.BatchException;

public class JobBootHandler implements ApplicationListener<ApplicationEvent>{
	
	@Autowired @Qualifier("requestChannel")
	private MessageChannel messageChannel;
	
	//private IntegrationRequestMappingHandlerMapping test;
	
	private SimpleMappingExceptionResolver test;
	private HttpRequestHandler test2;
	private DispatcherServlet test3;
	
	@Autowired
	private List<JobExecutor> jobExecutorList;
	private Map<String,JobExecutor> jobExecutors;
	private Map<String,Thread> jobThreadMap = new HashMap<String,Thread>();
	
	private boolean isStart = false;
	private boolean isEnd = false;
	
	@PostConstruct
	public void init() {
		
		this.jobExecutors = new HashMap<String,JobExecutor>();
		for(JobExecutor bean : jobExecutorList) {
			if(this.jobExecutors.containsKey(bean.getJobName()))
				throw new BatchException(bean.getJobName() +" jobExecutor name must be unique");
			
			this.jobExecutors.put(bean.getJobName(), bean);
			
			//bean.executeJob();
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		if(event instanceof ContextRefreshedEvent && !this.isStart) {
			System.out.println("################# start job boot");
			for(JobExecutor bean : jobExecutorList) {
				Thread t = new Thread(bean);
				this.jobThreadMap.put(bean.getJobName(),t);
				t.start();
			}
			
			this.isStart = true;
		}
		else if(event instanceof ContextClosedEvent && !this.isEnd) {
			
			for(JobExecutor bean : this.jobExecutorList) {
				bean.setStop(true);
			}
			/*
			try {
				
				if(this.jobThreadMap.isEmpty()) return;
				
				Set<Entry<String,Thread>> threads = this.jobThreadMap.entrySet();
				Iterator<Entry<String,Thread>> iter = threads.iterator();
				
				while(iter.hasNext()) {
					Entry<String,Thread> entry = iter.next();
					Thread bean = entry.getValue();
					if(bean.isAlive()) bean.join();
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				System.out.println("################## Job unnormal Stopped ################");
			}
			finally {
				System.out.println("################## ALL Job Stopped ################");
				this.isEnd = true;
			}
			*/
			
		}
	}
	
	public String stopJob(String jobName) {
		
		Thread t = this.jobThreadMap.get(jobName);
		JobExecutor jobExecutor = this.jobExecutors.get(jobName);
		if(t == null|| jobExecutor == null) return "jobName:"+jobName+ " is not exsit! or already stoppted!!";
		if(!t.isAlive() && jobExecutor.isStop() ) return jobName+" is already stopped!!";
		jobExecutor.setStop(true);
		this.jobThreadMap.remove(jobExecutor.getJobName());
		
		return "sucess stopped";
	}
	
	public String startJob(String jobName) {
		
		Thread t = this.jobThreadMap.get(jobName);
		JobExecutor jobExecutor = this.jobExecutors.get(jobName);
		if(jobExecutor == null) return "jobName:"+jobName+ " is not exsit!";
		if(t != null && t.isAlive() && !jobExecutor.isStop() ) return jobName+" is already running!!";
		
		jobExecutor.setStop(false);
		t = new Thread(jobExecutor);
		t.start();
		
		this.jobThreadMap.put(jobExecutor.getJobName(), t);
		
		return "sucess started";
	}
	
	public String changeRepeatPeriod(String jobName,long millseconds) {
		
		Thread t = this.jobThreadMap.get(jobName);
		JobExecutor jobExecutor = this.jobExecutors.get(jobName);
		
		if(t == null|| jobExecutor == null) return "jobName:"+jobName+ " is not exsit! or already stoppted!!";
		if(!t.isAlive() && jobExecutor.isStop() ) return jobName+" is already stopped!!";
		
		jobExecutor.setRepeatMillSeconds(millseconds);
		
		return "SUCCESS change repeat period";
	}

	public Map<String, JobExecutor> getJobExecutors() {
		return jobExecutors;
	}
	
	

}
