package jobtaklet;


public class UserTasklet implements Runnable{
	
	private String name;
	
	public UserTasklet(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		System.out.println(this.name);
		if(this.name.equals("user02")) {
			String test = null;
			test.split(",");
		}
		
		if(this.name.equals("user03")) {
			try {
				Thread.sleep(3000l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	

}
