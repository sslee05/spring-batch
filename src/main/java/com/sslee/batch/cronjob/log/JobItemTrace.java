package com.sslee.batch.cronjob.log;

import java.util.Date;
import java.util.List;

public class JobItemTrace<T> {
	
	private String jobName;
	private String itemId;
	private String traceLog;
	private Date startDate;
	private Date endDate;
	
	public JobItemTrace(){}
	
	public JobItemTrace(String jobName) {
		this.jobName = jobName;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getTraceLog() {
		return traceLog;
	}
	public void setTraceLog(String traceLog) {
		this.traceLog = traceLog;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setTrace(Throwable t) {
		StringBuilder sb = new StringBuilder(); 
		sb.append(t.toString()).append("\n");
		
		StackTraceElement[] els = t.getStackTrace();
		for(StackTraceElement el : els) {
			sb.append(el.toString()).append("\n");
		}
		
		this.traceLog = sb.toString();
	}
	
	public void setTrace(Throwable t,String skipIds) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(skipIds).append("\n");
		
		StackTraceElement[] els = t.getStackTrace();
		for(StackTraceElement el : els) {
			sb.append(el.toString()).append("\n");
		}
		
		this.traceLog = sb.toString();
	}
	
	public void setTrace(Throwable t,List<T> items) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i=0;
		int size = items.size();
		for(T item : items) {
			sb.append(item);
			if(i >= (size-1)) continue;
			sb.append(",");
			i++;
		}
		sb.append("]").append("\n");
		
		sb.append(t.toString()).append("\n");
		
		StackTraceElement[] els = t.getStackTrace();
		for(StackTraceElement el : els) {
			sb.append(el.toString()).append("\n");
		}
		
		this.traceLog = sb.toString();
	}
	
    public void setTrace(Throwable t,T item) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(item).append("]").append("\n");
		sb.append(t.toString()).append("\n");
		
		StackTraceElement[] els = t.getStackTrace();
		for(StackTraceElement el : els) {
			sb.append(el.toString()).append("\n");
		}
		
		this.traceLog = sb.toString();
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
    

}
