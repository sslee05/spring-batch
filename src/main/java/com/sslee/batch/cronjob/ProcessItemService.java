package com.sslee.batch.cronjob;

import java.util.concurrent.ConcurrentMap;

public interface ProcessItemService<T> {
	
	void execute(T item,ConcurrentMap<String,String> parameters);
	void execute(ConcurrentMap<String,String> parameters);

}
