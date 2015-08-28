/**
 * @(#)SqrtEquation1Module.java
 *
 *
 * @author 
 * @version 1.00 2007/1/16
 */


package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;

public class SqrtEquation1Module extends MathsModule {
	MathsOp question, step1, step2, result;
	final String[] varss = {"x","y","z"};
	Variable vx;

    public SqrtEquation1Module(Engine engine) {
    	super(engine);
    	
    	//int a = RandomChoice.randInt(2,8), b = RandomChoice.randInt(2,7), c = RandomChoice.randInt(2,5);
    	int num2 = RandomChoice.randInt(2,13);
    	int num3 = RandomChoice.randInt(2,15);
    	int num1=1;
    	int[] primefactors = Primes.primeFactorsOf(num2*num2*num3);
    	int k;	
    	for (int l=0; l<8; l++){
    		num1=1;
    		k=RandomChoice.randInt(1,primefactors.length);
    		for (int i=0; i<k; i++)
    			num1*=primefactors[i];
        	if ((num1<200) && (num1!=num2) && (num1!=num3) && (num1!=(num2*num2))) l=8;
    	}	    	
    	/*int num1 = a, num2 = a, num3 = c;
    	if(RandomChoice.randInt(0,1)==1)
    		num1 *= a;
   		else if(RandomChoice.randInt(0,1)==1) {
    		num1 *= b;
    		if(RandomChoice.randInt(0,1)==1) 
    			num2 *= b;
    		else
    			num3 *= b;	
   		}
   		*/
    	vx = new Variable(varss[RandomChoice.randInt(0,2)]);
    	question = new Equality(new Sqrt(new UnprintableMultiplication(new IntegerNumber(num1), vx)), 
    		new UnprintableMultiplication(new IntegerNumber(num2), new Sqrt(new IntegerNumber(num3))));
    	step1 = new Equality(new Equality(new Sqrt(new UnprintableMultiplication(new IntegerNumber(num1), vx)), 
    		new Sqrt(new Multiplication(new Multiplication(new IntegerNumber(num2), new IntegerNumber(num2)),
    		    new IntegerNumber(num3)))), new Sqrt(new IntegerNumber(num2*num2*num3)));
    	step2 = new Equality(new UnprintableMultiplication(new IntegerNumber(num1), vx), 
    		new IntegerNumber(num2*num2*num3));
    	result = new Equality(vx, new IntegerNumber(num2*num2*num3/num1));
    	
    }
    
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{" + question.toString() + "}";
		else if (name.equals("step1"))
			return "\\ensuremath{" +step1+"}";
		else if (name.equals("step2"))
			return "\\ensuremath{" +step2+"}";
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + result.toString() + "}";
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
		return super.getSection(name);
	}
}