package com.sslee.batch;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context/batch-context.xml"})
public class ContextLoadTest {
	
	@Test
	public void testContextLoad() {
		
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String target = "이상석 박유정  ";
		System.out.println("이상석".getBytes().length);
		String test = new String(target.getBytes(),0,10,Charset.forName("UTF-8"));
		String test2 = new String(target.getBytes(),10,11,Charset.forName("UTF-8"));
		
		String test3 = "이상석   sslee01";
		String test4 = new String(test3.getBytes(),12,7,Charset.forName("UTF-8"));
		
		System.out.println(":"+test+":");
		System.out.println(":"+test2+":");
		System.out.println(":"+test4+":");
		
	}

}
