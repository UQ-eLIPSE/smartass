/**
 * @(#)SqrtEquation.java
 *
 *
 * @author 
 * @version 1.00 2007/1/15
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class SqrtEquationModule extends MathsModule {
	MathsOp question, solution, result;
	final String[] varss = {"x","y","z"};
	Variable vx;

    public SqrtEquationModule(Engine engine) {
    	super(engine);
    	int num1 = RandomChoice.randInt(2,8);
    	int num2 = RandomChoice.selectInt(new int[]{0,0,5,5,0,2,1,1});
    	vx = new Variable(varss[RandomChoice.randInt(0,2)]);
    	question = new Equality(new Sqrt(new IntegerNumber(num1*num1*num2)), 
    		new UnprintableMultiplication(vx, new Sqrt(new IntegerNumber(num2))));
    	solution = new Equality(new Sqrt(new IntegerNumber(num1*num1*num2)),
    			new Equality(new Sqrt(new Multiplication(new IntegerNumber(num1*num1),new IntegerNumber(num2))),
    		new Equality(new Sqrt(new Multiplication(new Multiplication(new IntegerNumber(num1), new IntegerNumber(num1)),
    			new IntegerNumber(num2))), new UnprintableMultiplication(new IntegerNumber(num1), new Sqrt(new IntegerNumber(num2))))));
    	result = new Equality(vx, new IntegerNumber(num1));
    	
    }
    
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{" + question.toString() + "}";
		else if (name.equals("solution"))
			return "\\ensuremath{" + question + "}. Now \\ensuremath{"+solution+"}. Hence \\ensuremath{"+result+"}";
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + result.toString() + "}";
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
		return super.getSection(name);
	}
}