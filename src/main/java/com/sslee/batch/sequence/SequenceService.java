package com.sslee.batch.sequence;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRES_NEW)
public interface SequenceService {
	
	SequenceVO getMessageSequence(String type,int chunkSize);

}
