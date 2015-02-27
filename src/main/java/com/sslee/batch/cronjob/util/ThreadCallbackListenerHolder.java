package com.sslee.batch.cronjob.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadCallbackListenerHolder {
	
	private List<Boolean> isDones;
	private int size;
	
	public ThreadCallbackListenerHolder(int size) {
		this.size = size;
	}
	
	public synchronized void addDoneFlag(boolean isDone) {
		this.isDones = this.isDones == null ? new ArrayList<Boolean>() : this.isDones;
		this.isDones.add(isDone);
	}
	
	public synchronized boolean isDone() {
		if(this.isDones == null) return false;
		return (this.isDones.size() >= this.size);
	}

}
