/**
 * @(#)FractionEquation1Module.java
 *
 *
 * @author 
 * @version 1.00 2007/1/17
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

/**
 * class FractionEquation1Module generates equation of ax/b+c=d type
 */
public class FractionEquation1Module  extends FractionEquationModule {
	final int MAX_NUMBER = 6;

    public FractionEquation1Module(Engine engine) {
    	super(engine);
    	
    	vx = new Variable(varss[RandomChoice.randInt(0,2)]);
    	int[] nums = {
    		RandomChoice.randInt(1, MAX_NUMBER),
    		RandomChoice.randInt(2, MAX_NUMBER),
    		RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER),
    		RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER)};
    	if(RandomChoice.randInt(0,1)==1)
    		nums[0] *= -1;
    	if(RandomChoice.randInt(0,1)==1)
    		nums[1] *= -1;
		MathsOp varop = composeVarOp(nums[0], vx);
    	if (nums[2]!=0) {
    	 if (RandomChoice.randInt(0,1)==0)  	
    	  sol.add( question = 
    		new Equality(
    			new Addition(
    				new FractionOp(
    					varop,
    					new IntegerNumber(nums[1])),
    				new IntegerNumber(nums[2])),
    			new IntegerNumber(nums[3])) );
    	 else 
    	  sol.add( question = 
    		new Equality(new IntegerNumber(nums[3]),
    			new Addition(
    				new FractionOp(
    					varop,
    					new IntegerNumber(nums[1])),
    				new IntegerNumber(nums[2]))
    			) );	
    	} else {
    		if (nums[0]==nums[1]) {nums[0]++; varop = composeVarOp(nums[0], vx);}
    		if (RandomChoice.randInt(0,1)==0)
	    		question=new Equality( new FractionOp(varop,new IntegerNumber(nums[1])),    			
    			new IntegerNumber(nums[3]));
			else 
				question=new Equality(new IntegerNumber(nums[3]), 
					new FractionOp(varop,new IntegerNumber(nums[1])));	
    	}				
    	MathsOp fracop;
     	int hcf = HCFModule.hcf(nums[0], nums[1]);
     	if(hcf>1) {
    		nums[0] /= hcf;
    		nums[1] /= hcf;
     	}
    	if(nums[1]<0) {
   			nums[0] = - nums[0];
   			nums[1] = - nums[1];
   		}
     		
   		varop = composeVarOp(nums[0], vx);
    	if(nums[1]==1) 
    		fracop = varop;
    	else {
    		fracop = new FractionOp(
				varop,
				new IntegerNumber(nums[1]));
    	}
    	
    	if(nums[2]!=0 && nums[3]!=0)
    		sol.add(
    			new Equality(
    				fracop,
	   				new Addition(
    					new IntegerNumber(nums[3]),
    					new IntegerNumber(-nums[2]))) );

    	sol.add(
    		new Equality(
    			fracop,
    			new IntegerNumber(nums[3]-nums[2])) );

    	if(nums[3]-nums[2]==0) {
    		sol.add(answer = new Equality(vx, new IntegerNumber(0)));
    		return;
    	}
    	
    	if(nums[1]!=1) {
    		sol.add(
    			new Equality(
					varop,
					new Multiplication(
		    			new IntegerNumber(nums[3]-nums[2]),
  						new IntegerNumber(nums[1]))) );

	    	sol.add(
    			new Equality(
					varop,
    				new IntegerNumber((nums[3]-nums[2])*nums[1])) );
    	}
    	
    	if(Math.abs(nums[0])!=1) 
	    	sol.add(
    			new Equality(
    				new FractionOp(
						varop,
						new IntegerNumber(nums[0])),
    				new FractionOp(
		    			new IntegerNumber((nums[3]-nums[2])*nums[1]), 
						new IntegerNumber(nums[0]))) );

     	hcf = HCFModule.hcf((nums[3]-nums[2])*nums[1],nums[0]);
     	if(hcf==Math.abs(nums[0]))
	     	sol.add( answer = 
   				new Equality(vx, new IntegerNumber((nums[3]-nums[2])*nums[1]/nums[0])) );
    	else {
   			fracop = new FractionOp(
		    			new IntegerNumber(Math.abs((nums[3]-nums[2])*nums[1]/hcf)), 
						new IntegerNumber(Math.abs(nums[0]/hcf)));
			if((nums[3]-nums[2])*nums[1]*nums[0]<0)
				fracop = new UnaryMinus(fracop);
    		sol.add(answer = new Equality(vx,fracop));
    	}
    }
}