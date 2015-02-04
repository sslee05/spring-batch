package com.sslee.batch.cronjob;

import java.util.concurrent.ConcurrentMap;

public class SimpleJobExecutor<T> extends AbstractJobExecutor<T> {

	@Override
	protected void run(ConcurrentMap<String, String> parameters) {
		try {
			super.getProcessItemService().execute(parameters);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
