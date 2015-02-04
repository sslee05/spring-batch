package com.sslee.batch.cronjob;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.poi.hssf.record.formula.functions.T;

import com.sslee.batch.exception.BatchException;

public class OneItemJobExecutor extends AbstractJobExecutor<T> {
	
	
	@Override
	public void initValidate() {
		
		super.initValidate();
		if(super.getJobName() == null) throw new BatchException("jobName must be not null");
		if(super.getReadItemService() == null)
			throw new BatchException("one item transaction type must be readItemService but is null");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void run(ConcurrentMap<String,String> parameters) {
		List<T> items = super.getReadItemService().getItems(parameters);
		ProcessItemService processService = super.getProcessItemService();
		
		if(items == null) return;
		
		for(T item : items) {
			try {
				processService.execute(item,parameters);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		

	}
}
