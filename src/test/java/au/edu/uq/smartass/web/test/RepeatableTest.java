package au.edu.uq.smartass.web.test;

public class RepeatableTest extends Test {
	protected int repeatsNumber;
	protected int currentRun;
	
	public RepeatableTest(String name, int repeatsNumber) {
		super(name);
		this.repeatsNumber = repeatsNumber;
	}
	
	@Override
	protected void execute() {
		try {
			for(currentRun=1; currentRun<=repeatsNumber; currentRun++) {
				executeTest();
			}
		} catch (Exception e) {
			testOutcome = RESULT_FAILED;
		}
		testOutcome = RESULT_COMPLETE;
	}
	
	protected void executeTest() throws Exception {
		
	}
}
