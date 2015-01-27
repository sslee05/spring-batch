package com.sslee.batch.norollbackexception;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-mock-context.xml"})
public class NoRollbackExceptionTest {
	
	@Test
	public void testNoRollbackTest() throws Exception {
		
		CommandLineJobRunner.main(new String[]{"job/norollbackexceptiontest-context.xml",
	   	          "noRollbackTestJob",
	              "timestamp="+System.currentTimeMillis()});
		
	}

}
