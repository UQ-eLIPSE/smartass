/**
 * @(#)LinearEquationModule.java
 *
 *
 * @author 
 * @version 1.00 2007/1/17
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;


public class LinearEquationModule extends MathsModule {
	final static String[] varss = {"x","y","z"};
	Variable vx;
	
    public LinearEquationModule(Engine engine) {
    	super(engine);
    }
    
    public static int[] generateNumbers(int minA, int maxA, int minB, int maxB,int minC, int maxC) {
    	//generate constants a, b, c for equation of ax+b=c type
    	int[] nums = new int[]{RandomChoice.randInt(minA,maxA), 
    		RandomChoice.randInt(minB, maxB), 
    		RandomChoice.randInt(minC,maxC)};
    	if(RandomChoice.randInt(0,1)==1)
    		nums[0] *= -1; //a is non-zero
    	return nums; 
    }
    
    Variable initVar() {
    	return vx = new Variable(varss[RandomChoice.randInt(0,2)]);
    }
    
    public ArrayList<MathsOp>[] compose(int[] nums){
    	ArrayList[] ops = new ArrayList[2];
    	//ops[0] is the part of equation with x,
    	ops[0] = new ArrayList<MathsOp>();
    	//ops[1] is the part of equation without x
    	ops[1] = new ArrayList<MathsOp>();
    	
    	//step 1/question: ax+b=c
    	
 		ops[0].add(new Addition(
 					new UnprintableMultiplication(new IntegerNumber(nums[0]), vx),
 					new IntegerNumber(nums[1])));
 		ops[1].add(new IntegerNumber(nums[2]));

    	//step 2: ax=-b+c
 		if(nums[1]!=0 && nums[2]!=0) {
	 		ops[0].add(new UnprintableMultiplication(new IntegerNumber(nums[0]), vx));
	 		if (nums[1]>0) ops[1].add(new Subtraction(new IntegerNumber(nums[2]), new IntegerNumber(nums[1])));
	 		else 
 			ops[1].add(new Addition(new IntegerNumber(nums[2]), new IntegerNumber(-nums[1])));
 		}
 		
 		ops[0].add(new UnprintableMultiplication(new IntegerNumber(nums[0]), vx));
 		ops[1].add(new IntegerNumber(-nums[1]+nums[2]));

    	//step 3: x=(-b+c)/a
 		int hcf = HCFModule.hcf(-nums[1]+nums[2], nums[0]);
 		if((Math.abs(nums[0])!=1) && ((-nums[1]+nums[2])!=0)) { //(-b+c)/a isn't an integer number 
			ops[0].add(new FractionOp(
 				new UnprintableMultiplication(new IntegerNumber(nums[0]), vx),
 				new IntegerNumber(nums[0])));
 			ops[1].add(new FractionOp(
	 			new IntegerNumber(-nums[1]+nums[2]),
 				new IntegerNumber(nums[0])));
		}
 		
 		//last step/solution
 		int n1 = (-nums[1]+nums[2])/hcf*(int)Math.signum(nums[0]); 
 		int n2 = nums[0]/hcf*(int)Math.signum(nums[0]); 
		ops[0].add(vx);
 		if(n2==1) 
			ops[1].add(new IntegerNumber(n1));
 	    else if(n1*n2>0) 
 			ops[1].add(new FractionOp(
 					new IntegerNumber(n1),
 					new IntegerNumber(n2)) );
		else
 			ops[1].add(new UnaryMinus(new FractionOp(
 					new IntegerNumber(Math.abs(n1)),
 					new IntegerNumber(Math.abs(n2)))) );
    	
    	return ops;
    }
    
    
}