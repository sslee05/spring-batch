package com.sslee.batch.cronjob.job;

import java.util.concurrent.ConcurrentMap;

public interface RepeatOneProcessItemService<T> extends ProcessItemService<T> {
	void execute(T item,ConcurrentMap<String,String> parameters) throws Exception;
}
