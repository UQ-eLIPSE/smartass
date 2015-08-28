/**
 * @(#)FractionEquation2Module.java
 *
 *
 * @author 
 * @version 1.00 2007/1/21
 */


package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

/**
 * class FractionEquation2Module generates equation of a/bx+c=d type
 */
public class FractionEquation2Module extends FractionEquationModule {
	final int MAX_NUMBER = 6;

	/**
	 * constructor FractionEquation2Module
	 * initializes a question and solution 
	 * @ param Engine engine instance 
	 */
    public FractionEquation2Module(Engine engine) {
    	super(engine);
    	
    	vx = new Variable(varss[RandomChoice.randInt(0,2)]);

    	//generate a b c d
    	int[] nums = {
    		RandomChoice.randInt(1, MAX_NUMBER),
    		RandomChoice.randInt(1, MAX_NUMBER),
    		RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER),
    		RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER)};
    	//a and b is non-zero numbers
    	if(RandomChoice.randInt(0,1)==1)
    		nums[0] *= -1;
    	if(RandomChoice.randInt(0,1)==1)
    		nums[1] *= -1;
    	//c-d can not be zero
    	if(nums[2]==nums[3])
    		nums[2]++;
 
		MathsOp varop = composeVarOp(nums[1], vx);
    	
     	//step 1/question
     	if (nums[2]!=0){
     	 if (RandomChoice.randInt(0,1)==0)
           sol.add( question = 
    	 	new Equality(
    			new Addition(
    				new FractionOp(
    					new IntegerNumber(nums[0]),
    					varop),
    				new IntegerNumber(nums[2])),
    			new IntegerNumber(nums[3])) );
    	 else
    	  sol.add( question = 
    	 	new Equality(
    			new Addition(new IntegerNumber(nums[2]),
    				new FractionOp(
    					new IntegerNumber(nums[0]),
    					varop)),  				
    			new IntegerNumber(nums[3])) );
     	} else
     		if (RandomChoice.randInt(0,1)==0)
     			question=new Equality(new FractionOp(new IntegerNumber(nums[0]),varop),
    						new IntegerNumber(nums[3]));  
    		else question=new Equality(new IntegerNumber(nums[3])
    			,new FractionOp(new IntegerNumber(nums[0]),varop));
    			  		
     	int hcf = HCFModule.hcf(nums[0], nums[1]);
     	if(hcf>1) {
    		nums[0] /= hcf;
    		nums[1] /= hcf;
     	}
    	if(nums[1]<0) {
   			nums[0] = - nums[0];
   			nums[1] = - nums[1];
   		}
		varop = composeVarOp(nums[1], vx);
     		
     	//step 2 1/bx=-c+d
     	//only if c and d <> 0, otherwise go to step 3 
     	if(nums[2]!=0 && nums[3]!=0) {
     		sol.add( 
     			new Equality(
	   				new FractionOp(
   						new IntegerNumber(nums[0]),
   						varop),
    				new Addition(
    					new IntegerNumber(-nums[2]),
    					new IntegerNumber(nums[3]))) );
     	}
     	
     	//step 3 
     	sol.add( 
     		new Equality(
   				new FractionOp(
   					new IntegerNumber(nums[0]),
   					varop),
   				new IntegerNumber(-nums[2]+nums[3])) );
     	//step 4
     	if(nums[1]!=1 && -nums[2]+nums[3]!=1)
	     	sol.add( 
    	 		new Equality(
					new IntegerNumber(nums[0]),
   					new Multiplication(
   						new IntegerNumber(-nums[2]+nums[3]),
   						varop)) );

     	//step 4
     	int n2 = (-nums[2]+nums[3])*nums[1];
     	varop = composeVarOp(n2, vx);
     	sol.add( 
     		new Equality(
				new IntegerNumber(nums[0]),
				varop) );
		if(Math.abs(n2)!=1)
     		sol.add( 
     			new Equality(
     				vx,
					new FractionOp(
						new IntegerNumber(nums[0]),
   						new IntegerNumber(n2))) );
					
     	hcf = HCFModule.hcf(nums[0], n2);
     	if (hcf==Math.abs(n2))
     		sol.add(answer = new Equality(vx, new IntegerNumber(nums[0]/n2)));
     	else {    	
     	if(hcf>1) {
    		nums[0] /= hcf;
    		n2 /= hcf;
     	}
    	if(n2<0) {
   			nums[0] = - nums[0];
   			n2 = - n2;
   		}
   		if(nums[0]>0)
     		sol.add(answer = 
     			new Equality(
     				vx,
					new FractionOp(
						new IntegerNumber(nums[0]),
   						new IntegerNumber(n2))) );
   		else
     		sol.add(answer = 
     			new Equality(
     				vx,
					new UnaryMinus(new FractionOp(
						new IntegerNumber(-nums[0]),
   						new IntegerNumber(n2)))) );
     	}				
    }
}