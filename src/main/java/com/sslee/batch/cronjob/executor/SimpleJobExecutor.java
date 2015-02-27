package com.sslee.batch.cronjob.executor;

import java.util.concurrent.ConcurrentMap;

import com.sslee.batch.cronjob.job.SimpleProcessItemService;

public class SimpleJobExecutor<T> extends AbstractJobExecutor<T> {

	@Override
	protected void run(ConcurrentMap<String, String> parameters) {
		try {
			SimpleProcessItemService<T> service = (SimpleProcessItemService<T>)super.getProcessItemService();
			service.execute(parameters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
