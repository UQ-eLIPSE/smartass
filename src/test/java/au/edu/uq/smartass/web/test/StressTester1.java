package au.edu.uq.smartass.web.test;

public class StressTester1 extends RepeatableTest {
	int threadsNumber;
	int distribution;
	
	long maxms; 
	long minms = Long.MAX_VALUE;
	long avgms;
	int incomplNum;
	int failedNum;
	
	public StressTester1(String name, int repeatsNumber, int threadsNumber, int distribution) {
		super(name, repeatsNumber);
		this.threadsNumber = threadsNumber;
		this.distribution = distribution;
	}

	@Override
	protected void executeTest() throws Exception {
		System.out.print("run "+currentRun+": ");
		StressTest1 test = new StressTest1("One click per " + distribution + " test", threadsNumber, 300, distribution);
		test.run();
		avgms += test.getAvgms();
		if(maxms<test.getMaxms())
			maxms = test.getMaxms();
		if(minms>test.minms)
			minms = test.minms;
	}
	
	@Override
	protected void afterExecute() {
		avgms = avgms / repeatsNumber;
	}
	
	private static void doTest(int threadsNumber, int distribution, boolean print)  throws InterruptedException {
		StressTester1 test = new StressTester1("One click per " + distribution + " test", 10, threadsNumber, distribution);
		System.out.println("Test " + threadsNumber + " connects distributed on " + distribution + "ms time interval");
		System.out.println("--------------------------------------");
		test.run();
		System.out.println("--------------- TOTALS ---------------");
		System.out.println("Min " + test.minms + "ms, Max " + test.maxms + "ms, Average " + test.avgms + "ms");

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Simultaneous connects test");
		doTest(10, 0, false);
		doTest(25, 0, false);
		doTest(50, 0, false);

		System.out.println("\nRealistic user connects tests, one click per 3 seconds at random moment");
		doTest(25, 3, false);
		doTest(50, 3, false);
		doTest(100, 3, false);
		doTest(200, 3, false);
//		System.out.println("\n" + RUNS_NUMBER*10 + " users");
//		doTest(RUNS_NUMBER*10, 3, false);

		System.out.println("\nRealistic user connects tests, one click per 5 seconds at random moment");
		doTest(25, 5, false);
		doTest(50, 5, false);
		doTest(100, 5, false);
		doTest(200, 5, false);

		System.out.println("\nRealistic user connects tests, one click per 10 seconds at random moment");
		doTest(25, 10, false);
		doTest(50, 10, false);
		doTest(100, 10, false);
		doTest(200, 10, false);
	}
}
