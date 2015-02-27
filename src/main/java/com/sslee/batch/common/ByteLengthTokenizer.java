package com.sslee.batch.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.AbstractLineTokenizer;

import com.sslee.batch.exception.BatchException;

public class ByteLengthTokenizer extends AbstractLineTokenizer {
	
	private String endcoding;
	private List<RangeByte> ranges;

	@Override
	protected List<String> doTokenize(String line) {
		
		List<String> tokens = new ArrayList<String>(ranges.size());
		try {
			byte[] targetByte = line.getBytes(this.endcoding);

			for (RangeByte range : this.ranges) {
				tokens.add(new String(targetByte,range.getStartIdx(),range.getEndIdx(),Charset.forName(this.endcoding)));
			}
		}
		catch(UnsupportedEncodingException e) {
			throw new BatchException(e);
		}
		
		return tokens;
	}
	
	public void setColumns(int[] ranges) {
		
		this.ranges = new ArrayList<RangeByte>();
		
		int startIdx = 0;
		for(int value : ranges) {
			this.ranges.add(new RangeByte(startIdx,value));
			startIdx = startIdx+value;
			
		}
	}
	
	public void setEndcoding(String endcoding) {
		this.endcoding = endcoding;
	}
	
	private class RangeByte {
		
		private int startIdx;
		private int endIdx;
		
		private RangeByte(int startIdx,int endIdx) {
			this.startIdx = startIdx;
			this.endIdx = endIdx;
		}
		public int getStartIdx() {
			return startIdx;
		}
		public int getEndIdx() {
			return endIdx;
		}
	}
	
	
}
