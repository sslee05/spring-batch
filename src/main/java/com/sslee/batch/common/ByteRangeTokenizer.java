package com.sslee.batch.common;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.AbstractLineTokenizer;

import com.sslee.batch.exception.BatchException;

public class ByteRangeTokenizer extends AbstractLineTokenizer {
	
	private List<ByteRange> byteRanges;
	private String endcoding;

	@Override
	protected List<String> doTokenize(String line) {
		
		List<String> tokens = new ArrayList<String>(this.byteRanges.size());
		
		try {
			byte[] targetByte = line.getBytes(this.endcoding);
			
			for(ByteRange range : byteRanges) {
				int startByteIndex = range.getStartByteIndex()-1;
				int byteLength = range.getByteLength();
				
				tokens.add(":"+new String(targetByte,startByteIndex,byteLength,Charset.forName(this.endcoding))+":");
			}
		}
		catch(UnsupportedEncodingException e) {
			throw new BatchException(e);
		}
		
		
		return tokens;
	}
	
	public void setColumns(List<ByteRange> ranges) {
		this.byteRanges = ranges;
	}

	public void setEndcoding(String endcoding) {
		this.endcoding = endcoding;
	}

	

}
