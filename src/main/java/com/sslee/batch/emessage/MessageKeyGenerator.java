package com.sslee.batch.emessage;

import java.util.List;

import com.sslee.batch.sequence.SequenceService;

public interface MessageKeyGenerator {
	
	List<String> generateMessageKey(SequenceService sequenceService);

}
