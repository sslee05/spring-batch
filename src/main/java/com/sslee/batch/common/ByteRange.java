package com.sslee.batch.common;

public class ByteRange {
	
	private int startByteIndex;
	private int byteLength;
	
	public ByteRange(int startByteIndex,int byteLength) {
		this.startByteIndex = startByteIndex;
		this.byteLength = byteLength;
	}
	
	public int getStartByteIndex() {
		return startByteIndex;
	}
	public void setStartByteIndex(int startByteIndex) {
		this.startByteIndex = startByteIndex;
	}
	public int getByteLength() {
		return byteLength;
	}
	public void setByteLength(int byteLength) {
		this.byteLength = byteLength;
	}
	
	

}
