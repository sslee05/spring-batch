package com.sslee.batch.emessage;

import com.sslee.batch.emessage.vo.Message;

public interface MessageRowMapper<S,T extends Message> {
	
	T convertMessage(S s,String messageId);

}
