package com.sslee.batch.cronjob.serviceactivator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.integration.annotation.Header;

import com.sslee.batch.cronjob.JobBootHandler;
import com.sslee.batch.cronjob.executor.JobExecutor;
import com.sslee.batch.cronjob.executor.RepeatOneItemJobExecutor;
import com.sslee.batch.cronjob.executor.SimpleJobExecutor;
import com.sslee.batch.cronjob.executor.TaskletJobExecutor;
import com.sslee.batch.cronjob.job.ReadItemService;
import com.sslee.batch.cronjob.job.RepeatOneProcessItemService;
import com.sslee.batch.cronjob.job.SimpleProcessItemService;

public class JobHandleServiceActivator<T> {
	
	private JobBootHandler jobBootHandler;
	
	public void setJobBootHandler(JobBootHandler jobBootHandler) {
		this.jobBootHandler = jobBootHandler;
	}
	
	public String handleJob(@Header("jobName") String jobName,@Header("command") String command) {
		
		if(jobName == null) return "JOB_NAME parameter must be exsit!";
		if(command == null || !(command.equalsIgnoreCase("STOP")||command.equalsIgnoreCase("START")) ) 
			return "COMMAND parameter value must be START or STOP!";
		
		if(command.equalsIgnoreCase("START")) return this.jobBootHandler.startJob(jobName);
		if(command.equalsIgnoreCase("STOP")) return this.jobBootHandler.stopJob(jobName);
		
		return "parameter illegal Expression";
	}
	
	public String changeSecondsPeriod(@Header("jobName") String jobName,@Header("command") String command,
			@Header("millseconds") String millseconds) {
		
		if(jobName == null) return "JOB_NAME parameter must be exsit!";
		if(command == null) return "COMMAND parameter value must be 'change' !";
		if(millseconds == null) return "millseconds parameter must be";
		
		long milsec = 1000l;
		try {
			milsec = Long.valueOf(millseconds);
		}catch(Exception e) {
			return "millseconds parameter illegal value";
		}
		
		if(milsec <= 1000l) return "millseconds must be greater than 1000 value";
		
		return this.jobBootHandler.changeRepeatPeriod(jobName, milsec);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String debugJob(@Header("jobName") String jobName) {
		
		Map<String,JobExecutor> datas = this.jobBootHandler.getJobExecutors();
		JobExecutor jobExecutor = datas.get(jobName);
		if(jobExecutor == null) return "jobName is "+jobName+"is not registration";
		
		if(jobExecutor instanceof SimpleJobExecutor) {
			SimpleJobExecutor executor = (SimpleJobExecutor)jobExecutor;
			SimpleProcessItemService service = (SimpleProcessItemService)executor.getProcessItemService();
			try {
				
				service.execute(null);
				return "SUCCESS";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FAIL";
			}
		}
		else if(jobExecutor instanceof RepeatOneItemJobExecutor) {
			RepeatOneItemJobExecutor executor = (RepeatOneItemJobExecutor)jobExecutor;
			ReadItemService readService = executor.getReadItemService();
			RepeatOneProcessItemService processService = (RepeatOneProcessItemService)executor.getProcessItemService();
			try {
				List<T> targets = readService.getItems(null);
				for(T item : targets) {
					processService.execute(item, null);
				}
				
				return "SUCCESS";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FAIL";
			}
		}
		else  {
			
			try {
				TaskletJobExecutor executor = (TaskletJobExecutor)jobExecutor;
				executor.run(null);
				return "SUCCESS";
			}
			catch(Exception e) {
				e.printStackTrace();
				return "FAIL";
			}
		}
	}

}
