package com.sslee.batch.cronjob.job;

import java.util.concurrent.ConcurrentMap;

public interface SimpleProcessItemService<T> extends ProcessItemService<T> {
	
	void execute(ConcurrentMap<String,String> parameters) throws Exception;
}
