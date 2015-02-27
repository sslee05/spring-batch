package com.sslee.batch.cronjob.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.batch.core.ExitStatus;

public class Job {
	
	private String jobName;
	private ExitStatus exitStatus;
	private String status;
	private Date startDate;
	private Date endDate;
	private int totalCount;
	private int skipCount;
	private int successCount;
	private String exitCode;
	private List<JobItemTrace> jobItemTraces;
	
	@Override
	public String toString() {
		
		String message = String.format("jobName: %s totalCount: %d successCount: %d skipCount: %d exitStatus: %s startDate:%s endDate:%s"  , 
				this.jobName,this.totalCount,this.successCount,this.skipCount,this.exitCode,this.startDate,this.endDate);
		return message;
	}
	
	public Job(){}
	
	public Job(String jobName) {
		this.jobName = jobName;
	}
	
	public Job(String jobName,Date startDate) {
		this.jobName = jobName;
		this.startDate = startDate;
	}
	
	public void addTotalCount(int count) {
		this.totalCount = this.totalCount + count;
	}
	
	public void addSkipCount(int count) {
		this.skipCount = this.skipCount + count;
	}
	
	public void addSuccessCount(int count) {
		this.successCount = this.successCount + count;
	}
	
	public int calculateSuccessCount() {
		return (this.totalCount - this.skipCount);
	}
	
	public void injectJobItemTrace() {
		
		if(this.jobItemTraces != null) {
			for(JobItemTrace item : this.jobItemTraces) {
				item.setJobName(this.jobName);
				item.setStartDate(this.startDate);
				item.setEndDate(this.endDate);

			}
		}
	}
	
	public void addJobItemTraces(JobItemTrace jobItemTrace) {
		this.jobItemTraces = this.jobItemTraces == null ? new ArrayList<JobItemTrace>() : this.jobItemTraces;
		this.jobItemTraces.add(jobItemTrace);
	}
	
	public void addItemTraces(List<JobItemTrace> jobItemTraces) {
		
		this.jobItemTraces = this.jobItemTraces == null ? new ArrayList<JobItemTrace>() : this.jobItemTraces;
		if(jobItemTraces != null && jobItemTraces.size() > 0)
			this.jobItemTraces.addAll(jobItemTraces);
	}
	
	public void setJobItemTraces(List<JobItemTrace> jobItemTraces) {
		this.jobItemTraces = jobItemTraces;
	}
	
	public List<JobItemTrace> getJobItemTraces() {
		return this.jobItemTraces;
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSkipCount() {
		return skipCount;
	}
	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public ExitStatus getExitStatus() {
		return exitStatus;
	}
	public void setExitStatus(ExitStatus exsitStatus) {
		this.exitStatus = exsitStatus;
		this.setExitCode(this.exitStatus.getExitCode());
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}
	
}
