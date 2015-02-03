package com.sslee.batch.sequence;

public abstract class AbstractKeyGenerator implements KeyGenerator {

	protected String getFormatKey(long id,String prefix,int size,String fillCharater) {
		
		//String formatStr = "%046d";
		String formatStr = "%"+fillCharater+size+"d";
		return (prefix+String.format(formatStr, id));
	}
	
	protected String getFormatKey(long id,String formatStr) {
		
		return String.format(formatStr, id);
	}
}
