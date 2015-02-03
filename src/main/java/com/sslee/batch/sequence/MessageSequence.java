package com.sslee.batch.sequence;

import org.mybatis.spring.SqlSessionTemplate;

public class MessageSequence implements SequenceService {
	
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public SequenceVO getMessageSequence(String type, int chunkSize) {
		
		SequenceVO param = new SequenceVO();
		param.setType(type);
		param.setChunkSize(chunkSize);
		
		long nextSequence = sqlSessionTemplate.selectOne("batchId.getNextId", param);
		param.setStartSequence(nextSequence);
		param.setEndSequence(nextSequence + (new Integer(chunkSize)).longValue());
		
		sqlSessionTemplate.update("batchId.updateId",param);
		
		return param;
	}
	
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
