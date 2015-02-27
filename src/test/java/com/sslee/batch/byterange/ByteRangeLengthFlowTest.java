package com.sslee.batch.byterange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-mock-context.xml"})
public class ByteRangeLengthFlowTest {
	
	@Test
	public void testBytefixedJob() throws Exception {
		
		CommandLineJobRunner.main(new String[]{"job/byterangelengthjob-context.xml",
	   	          "byterangelengthJob",
	   	          "successFlag=Y",
	   	          "fileName=user_info_20150116.txt",
	   	          "test=1",
	              "timestamp="+System.currentTimeMillis()});
		
	}

}
