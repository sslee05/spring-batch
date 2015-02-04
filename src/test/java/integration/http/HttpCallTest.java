package integration.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sslee.batch.integration.http.message.JobCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration/httpcall-context.xml"})
public class HttpCallTest {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired @Qualifier("requestChannel")
	private MessageChannel requestChannel;
	
	@Test
	public void testHttpCall() {
		
		JobCommand bean = new JobCommand();
		bean.setJobName("2015020400380001001");
		bean.setSystemName("viewer");
		
		Message<JobCommand> message = MessageBuilder.withPayload(bean).build();
		requestChannel.send(message);
		
	}
}
