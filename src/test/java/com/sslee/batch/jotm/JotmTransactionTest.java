package com.sslee.batch.jotm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:job/jotmjob-context.xml"})
public class JotmTransactionTest {
	
	@Test
	public void testJotmTransaction() throws Exception {
		
		CommandLineJobRunner.main(new String[]{"job/jotmjob-context.xml",
	   	          "jotmTransactionJob",
	   	          //"targetDirectory=/Users/sslee/work/batch/temp/",
	   	          "targetDirectory=/Users/sslee/work/projects/kt/pilotBatch/files/",
	   	          "sourceFile=user_info",
	   	          "dateYmd=20150113",
	   	          "convertKey= TARGET: ",
	              "timestamp="+System.currentTimeMillis()});
		
	}

}
