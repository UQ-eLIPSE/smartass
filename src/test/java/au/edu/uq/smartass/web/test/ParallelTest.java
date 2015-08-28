package au.edu.uq.smartass.web.test;

public class ParallelTest extends Test {
	public final static int RESULT_INTERRUPTED = 101;
	
	//Maximum time in seconds that ParallelTest waits for its child test thread to complete  
	protected int maxWait;
	protected int threadsNumber;
	protected Test[] tests;
	protected Thread[] threads;
	
	public ParallelTest(String name, int threadsNumber, int maxWait) {
		super(name);
		this.maxWait = maxWait;
		this.threadsNumber = threadsNumber;
	}

	@Override
	protected void execute() {
		tests = new Test[threadsNumber];
		threads = new Thread[threadsNumber];
		initTests();
		runTests();
		afterRunTests();
	}
	
	protected void initTests() {
		//default behavior of this method assumes creation of identical tests by createTest() 
		for(int i=0; i<threadsNumber; i++) {
			tests[i] = createTest(Integer.toString(i));
		}
	}
	
	protected void runTests() {
		for(int i=0; i<threadsNumber; i++) 
			(threads[i] = new Thread(tests[i])).start();
		try {
			for(int i=0;i<threadsNumber;i++) 
				threads[i].join(maxWait*1000);
		} catch (InterruptedException e) {
			testOutcome = RESULT_INTERRUPTED;
		}
	}
	
	protected Test createTest(String name) {
		return null;
	}
	
	protected void afterRunTests() {
		
	}
}
