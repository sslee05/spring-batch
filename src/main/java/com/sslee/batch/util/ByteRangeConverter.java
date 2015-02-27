package com.sslee.batch.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.sslee.batch.common.ByteRange;

public class ByteRangeConverter implements Converter<String, List<ByteRange>> {

	@Override
	public List<ByteRange> convert(String source) {
		
		List<ByteRange> byteRanges = new ArrayList<ByteRange>();
		
		String[] ranges = source.split(",");
		for(String range : ranges) {
			String[] datas = range.split("-");
			int startByteIndex = Integer.parseInt(datas[0]);
			int byteLength = startByteIndex;
			if(datas.length > 1) byteLength = Integer.parseInt(datas[1]);
			byteRanges.add(new ByteRange(startByteIndex,byteLength));
		}
		
		return byteRanges;
	}

}
