package integration.schedule;

import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:integration/schedule-context.xml"})
public class JobScheduleTest {
	
	@Autowired @Qualifier("schedule")
	private PollableChannel schedule;
	
	@Test
	public void testScheduleTest() {
		
		final Scanner scanner = new Scanner(System.in);
		
        
		while(true) {
			
			final String input = scanner.nextLine();
			if("q".equals(input.trim())) {
				break;
			}
			
			Message<String> test = MessageBuilder.withPayload("hi").build();
			schedule.send(test);
			
		}
	}

}
