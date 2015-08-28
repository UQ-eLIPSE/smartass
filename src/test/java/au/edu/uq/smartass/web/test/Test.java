package au.edu.uq.smartass.web.test;

public class Test implements Runnable {
	public static final int RESULT_INCOMPLETE = 0;
	public static final int RESULT_COMPLETE = 1;
	public static final int RESULT_FAILED = 2;
	
	private String name;
	protected volatile long ticks;
	protected volatile int testOutcome;

	public void run() {
		beforeExecute();
		long tbegin = System.currentTimeMillis();
		execute();
		ticks = System.currentTimeMillis() - tbegin; 
		afterExecute();
	}
	
	public Test(String name) {
		this.name = name; 
	}
	
	protected void beforeExecute() {
		
	}
	
	protected void execute() {
		testOutcome = RESULT_COMPLETE;
	}
	
	protected void afterExecute() {
		
	}

	//setters and getters
	public int getTestOutcome() {
		return testOutcome;
	}
	
	public long getTicks() {
		return ticks;
	}
	
	public String getName() {
		return name;
	}
}
