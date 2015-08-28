/**
 * @(#)FractionEquationModule.java
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
 * class FractionEquationModule is the base class for FractionEquation1Module and FractionEquation2Module classes
 */
public class FractionEquationModule extends MathsModule {
	final static String[] varss = {"x","y","z"};
	ArrayList<MathsOp> sol = new ArrayList<MathsOp>();
	Variable vx;
	MathsOp question, answer;

    public FractionEquationModule(Engine engine) {
    	super(engine);

   }
    
	MathsOp composeVarOp(int num0, MathsOp vx) {
		if(num0==1) 
			return vx;
		else if(num0==-1)
			return new UnaryMinus(vx);
		else
			return new UnprintableMultiplication(new IntegerNumber(num0), vx);
	}
    
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{" + question.toString() + "}";
		else if (name.equals("solution"))
			return composeSolution(sol);
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + answer.toString() + "}";
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
		return super.getSection(name);
	}
    
	private String composeSolution(ArrayList<MathsOp> solution) {
		String res = "\\ensuremath{" + sol.get(0).toString() + "}";
		for(int i=1;i<sol.size()-1;i++)
			res = res + ", so \\ensuremath{" + sol.get(i).toString() + "}";
		return res;
	}
}