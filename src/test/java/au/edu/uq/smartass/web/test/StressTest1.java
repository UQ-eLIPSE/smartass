package au.edu.uq.smartass.web.test;

import java.util.Random;

public class StressTest1 extends ParallelTest {
	private static final String LOGIN_LOCATION = "http://smartassignments.virtual.vps-host.net/index.htm";
	//private static final String LOGIN_LOCATION = "http://localhost:8180/smartass/login.htm";
	private static final int MAX_TEST_DURATION = 300; //seconds
	private int distribution;
	Random random;

	long maxms; 
	long minms;
	long avgms;
	int incomplNum;
	int failedNum;
	
	public StressTest1(String name, int threadsNumber, int maxWait, int distribution) {
		super(name, threadsNumber, maxWait); 
		this.distribution = distribution;
		random = new Random();
	}
	
	@Override
	protected Test createTest(String name) {
		if(distribution>0)
			return new ReadPageTest(name, LOGIN_LOCATION, random.nextInt(distribution*1000));
		else
			return new ReadPageTest(name, LOGIN_LOCATION, 0);
	}
	
	@Override
	protected void afterRunTests() {
		maxms = 0; 
		minms = Long.MAX_VALUE;
		avgms = 0;
		incomplNum = 0;
		failedNum = 0;
		
		for(int i=0; i<threadsNumber; i++) {
			switch(tests[i].getTestOutcome()) {
			case RESULT_INCOMPLETE: incomplNum ++; break;
			case RESULT_FAILED: failedNum++; break;
			case RESULT_COMPLETE:
				avgms += tests[i].ticks;
				if(maxms<tests[i].ticks)
					maxms = tests[i].ticks;
				if(minms>tests[i].ticks)
					minms = tests[i].ticks;
			}
				
		}
		avgms = avgms / (threadsNumber-incomplNum-failedNum);
		System.out.println("Min " + minms + "ms, Max " + maxms + "ms, Average " + avgms + "ms");
	/*		System.out.println("" + runsNumber + "tests, " + (runsNumber-incompl_num-failed_num) + "succsessful, " 
					+ "ge " + avgms + "ms");*/
	}

	public long getMaxms() {
		return maxms;
	}

	public void setMaxms(long maxms) {
		this.maxms = maxms;
	}

	public long getMinms() {
		return minms;
	}

	public void setMinms(long minms) {
		this.minms = minms;
	}

	public long getAvgms() {
		return avgms;
	}

	public void setAvgms(long avgms) {
		this.avgms = avgms;
	}

	public int getIncompNum() {
		return incomplNum;
	}

	public void setIncompNum(int incompNum) {
		this.incomplNum = incompNum;
	}

	public int getFailedNum() {
		return failedNum;
	}

	public void setFailedNum(int failedNum) {
		this.failedNum = failedNum;
	}
}
