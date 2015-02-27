package com.sslee.batch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-mock-context.xml"})
public class SkiplogNorollbackJobTest {
	
	@Test
	public void testSkiplogJobflow() throws Exception {
		
		CommandLineJobRunner.main(new String[]{"job/skiplog-norollback-job-context.xml",
	   	          "skiplognorollbackjob",
	   	          "successFlag=Y",
	   	          "fileName=user_info_20150114.txt",
	   	          "test=1",
	              "timestamp="+System.currentTimeMillis()});
		
	}

}
