package com.sslee.batch.cronjob.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JobParameterHolder {
	
	private ConcurrentMap<String,Object> datas;
	
	public JobParameterHolder() {
		this.initDatas();
	}
	
	public JobParameterHolder(ConcurrentMap<String,Object> datas) {
		this.initDatas(datas);
	}
	
	public JobParameterHolder(Set<Entry<String,String>> entries) {
		
		this.datas = new ConcurrentHashMap<String,Object>(); 
		Iterator<Entry<String,String>> iter = entries.iterator();
		while(iter.hasNext()) {
			Entry<String,String> entry = iter.next();
			this.datas.put(entry.getKey(), entry.getValue());
		}
		
	}
	
	public void initDatas(ConcurrentMap<String,Object> datas) {
		
		if(this.datas == null) {
			if(datas == null) this.datas = new ConcurrentHashMap<String,Object>();
			else this.datas = datas;
		}
		else {
			this.datas.clear();
			this.datas = datas;
		}
	}
	
	public void initDatas() {
		datas = datas == null ? new ConcurrentHashMap<String,Object>() : datas;
		datas.clear();
	}
	
	public Object getData(String key) {
		return this.datas.get(key);
	}
	
	public void addData(String key,Object data) {
		this.datas.put(key, data);
	}
	
	public boolean constainsKey(String key) {
		return this.datas.containsKey(key);
	}
	
}
