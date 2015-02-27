package com.sslee.batch.cronjob.executor;

import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sslee.batch.cronjob.http.UserHttpServletRequest;
import com.sslee.batch.cronjob.job.ProcessItemService;
import com.sslee.batch.cronjob.job.ReadItemService;
import com.sslee.batch.exception.BatchException;


public abstract class AbstractJobExecutor<T> implements JobExecutor{
	
	private ReadItemService<T> readItemService;
	private ProcessItemService<T> processItemService;
	private long repeatMillSeconds;
	private String jobName;
	private boolean isStop;
	private ConcurrentMap<String,String> parameters;
	private String hostName;
	private static final String UID_KEY = "kcsUID";
	private static final String DASH_STR = "-";
	
	@Autowired
    private ServletContext servletContext;
	
	@PostConstruct
	public void initValidate() {
		
		if(this.jobName == null) throw new BatchException("jobName must be not null");
		if(this.processItemService == null)
			throw new BatchException("processItemService must be not null");
		
		try {
			this.hostName = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			this.hostName = "localhost";
		}
	}
	
	@Override
	public void run() {
		this.executeJob();
	} 
	
	private void executeJob() {
		
		while(!isStop) {
			try {
				
				HttpServletRequest request = new UserHttpServletRequest(servletContext);
				request.setAttribute(UID_KEY,this.hostName+DASH_STR+this.jobName+DASH_STR);
				RequestAttributes requestAttributes = new ServletRequestAttributes(request);
				RequestContextHolder.setRequestAttributes(requestAttributes);
				
				run(parameters);
				Thread.sleep(repeatMillSeconds);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	abstract protected void run(ConcurrentMap<String,String> parameters);
	
	@Override
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public ProcessItemService<T> getProcessItemService() {
		return processItemService;
	}

	public void setProcessItemService(ProcessItemService<T> processItemService) {
		this.processItemService = processItemService;
	}

	public long getRepeatMillSeconds() {
		return repeatMillSeconds;
	}

	public void setRepeatMillSeconds(long repeatMillSeconds) {
		this.repeatMillSeconds = repeatMillSeconds;
	}
	
	@Override
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public boolean isStop() {
		return isStop;
	}

	public ReadItemService<T> getReadItemService() {
		return readItemService;
	}

	public void setReadItemService(ReadItemService<T> readItemService) {
		this.readItemService = readItemService;
	}

	public ConcurrentMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(ConcurrentMap<String, String> parameters) {
		this.parameters = parameters;
	}
	
}
