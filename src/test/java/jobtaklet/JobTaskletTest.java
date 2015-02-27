package jobtaklet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.sslee.batch.cronjob.log.Job;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:taskexecutor-context.xml"})
public class JobTaskletTest {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		
		try {
			ListenableFuture<Job> test = (ListenableFuture<Job>) taskExecutor.submitListenable(new UserTasklet("user01"));
			ListenableFuture<Job> test2 =  (ListenableFuture<Job>) taskExecutor.submitListenable(new UserTasklet("user02"));
			taskExecutor.execute(new UserTasklet("user03"));
			
			
			
			test.addCallback(new ListenableFutureCallback<Job> (){

				@Override
				public void onSuccess(Job result) {
					System.out.println("###1=>"+result);
					
				}

				@Override
				public void onFailure(Throwable t) {
					System.out.println("###1=>"+t);
					
				}
				
			});
			
			test2.addCallback(new ListenableFutureCallback<Job> (){

				@Override
				public void onSuccess(Job result) {
					System.out.println("###2=>"+result);
					
				}

				@Override
				public void onFailure(Throwable t) {
					System.out.println("###2=>"+t);
					
				}
				
			});
			
			
			
		}
		catch(Exception e) {
			System.out.println("############ hhhhh");
			e.printStackTrace();
		}
		finally {
			while(taskExecutor.getActiveCount() > 0) {
				//System.out.println("#####wait");
			}
			System.out.println("isEnd?");
		}
		
		
		
	}
	
	

}
