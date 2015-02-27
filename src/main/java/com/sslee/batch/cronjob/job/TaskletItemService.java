package com.sslee.batch.cronjob.job;

import java.util.List;

import com.sslee.batch.cronjob.util.JobParameterHolder;

public interface TaskletItemService<T> extends ProcessItemService<T>{
	
	List<T> getItems(JobParameterHolder jobParameterHolder);
	void execute(T item,JobParameterHolder jobParameterHolder);

}
