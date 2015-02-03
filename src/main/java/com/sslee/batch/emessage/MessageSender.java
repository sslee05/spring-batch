package com.sslee.batch.emessage;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.sslee.batch.emessage.vo.EmailVO;
import com.sslee.batch.emessage.vo.Message;
import com.sslee.batch.emessage.vo.SmsVO;
import com.sslee.batch.exception.BatchException;
import com.sslee.batch.sequence.AbstractKeyGenerator;
import com.sslee.batch.sequence.SequenceService;
import com.sslee.batch.sequence.SequenceVO;

public class MessageSender<S> {
	
	private String messageTypeName;
	private String fillCharacter;
	private String systemName;
	private String queryId;
	private MessageRowMapper<S,? extends Message> messageRowMapper;
	private SequenceService sequenceService;
	private MessageKeyGenerator keyGenerator;
	private SqlSessionTemplate sqlSessionTemplate;
	private int keyColumnLength;
	
	public MessageSender() {
		this.keyGenerator = new MessageKeyGenerator();
	}
	
	public void init() {
		if(this.messageTypeName == null) throw new BatchException("messageType must not be null !!");
		if(this.systemName == null || this.systemName.length()!=4) 
			throw new BatchException("messageType must not be systemType !! and character length must be 4 length");
		if(this.queryId == null) throw new BatchException("queryId must not be systemType !!");
		if(this.keyColumnLength <= 0) throw new BatchException("keyColumnLength must be greater than zero");
		if(this.fillCharacter == null) this.fillCharacter = "0";
	}
	
	public List<String> sendMessage(List<? extends S> items) {
		
		List<String> keys = this.keyGenerator.generateKey(sequenceService,items.size());
		
		int idx= 0;
		for(S s : items) {
			
			Message message = this.messageRowMapper.convertMessage(s,keys.get(idx));
			if(message instanceof EmailVO) {
				EmailVO param = (EmailVO)message;
				sqlSessionTemplate.insert(this.queryId, param);
			}
			else {
				SmsVO param = (SmsVO)message;
				sqlSessionTemplate.insert(this.queryId, param);
			}
			
			idx++;
		}
		
		this.sqlSessionTemplate.flushStatements();
		
		return keys;
	}
	
	private class MessageKeyGenerator extends AbstractKeyGenerator {

		@Override
		public List<String> generateKey(SequenceService sequenceService,int size) {
			SequenceVO sequence = sequenceService.getMessageSequence(messageTypeName, size);
			
			List<String> keys = new ArrayList<String>();
			long startId = sequence.getStartSequence();
			long endId = sequence.getEndSequence();
			
			while(startId<=endId) {
				keys.add(getFormatKey(startId, systemName+"-", keyColumnLength, fillCharacter));
				startId++;
			}
			
			return keys;
		}
	}
	
	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}
	public void setFillCharacter(String fillCharacter) {
		this.fillCharacter = fillCharacter;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public void setMessageRowMapper(MessageRowMapper<S, ? extends Message> messageRowMapper) {
		this.messageRowMapper = messageRowMapper;
	}
	public void setKeyColumnLength(int keyColumnLength) {
		this.keyColumnLength = keyColumnLength;
	}
	
	
	
}
