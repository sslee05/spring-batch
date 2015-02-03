package com.sslee.batch.sequence;

import java.util.List;

public interface KeyGenerator {
	
	List<String> generateKey(SequenceService sequenceService,int size);

}
