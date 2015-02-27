package com.sslee.batch.cronjob.job;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface ReadItemService<T> {
	
	List<T> getItems(ConcurrentMap<String,String> parameters) throws Exception;
}
