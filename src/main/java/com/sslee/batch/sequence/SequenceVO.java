package com.sslee.batch.sequence;


public class SequenceVO {
	
	private int chunkSize;
	private String type;
	private long startSequence;
	private long endSequence;
	
	public int getChunkSize() {
		return chunkSize;
	}
	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getStartSequence() {
		return startSequence;
	}
	public void setStartSequence(long startSequence) {
		this.startSequence = startSequence;
	}
	public long getEndSequence() {
		return endSequence;
	}
	public void setEndSequence(long endSequence) {
		this.endSequence = endSequence;
	}
	
	

}
