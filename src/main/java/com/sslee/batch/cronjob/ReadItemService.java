package com.sslee.batch.cronjob;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public interface ReadItemService<T> {
	
	List<T> getItems(ConcurrentMap<String,String> parameters);
}
