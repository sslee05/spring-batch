package message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-mock-context.xml"})
public class MessageSendTest {
	
	@Test
	public void testMessageSend() throws Exception {
		
		CommandLineJobRunner.main(new String[]{"job/messagesend-context.xml",
	   	          "messageSendJob",
	   	          "successFlag=Y",
	   	          "fileName=user_info_20150114.txt",
	   	          "test=1",
	              "timestamp="+System.currentTimeMillis()});
		
	}

}
