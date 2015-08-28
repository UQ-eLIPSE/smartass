/**
 * @(#)SimpleEquation2Module.java
 *
 *
 * @author 
 * @version 1.00 2007/1/17
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class SimpleEquation2Module extends LinearEquationModule {
	final int MAX_NUMBER = 6;
	ArrayList<MathsOp>[] sol;
    private int[] nums;
    public SimpleEquation2Module(Engine engine) {
    	super(engine);
    	nums = generateNumbers(2, MAX_NUMBER, -MAX_NUMBER, MAX_NUMBER, -2, MAX_NUMBER);
    	initVar();
		sol = compose(nums);    	
    }
    
	public String getSection(String name) {
		if(name.equals("question"))
			if (nums[1]==0)
			  return "\\ensuremath{" + sol[0].get(1).toString() + " = " + sol[1].get(1).toString() + "}";	
			else	
			 return "\\ensuremath{" + sol[0].get(0).toString() + " = " + sol[1].get(0).toString() + "}";
		else if (name.equals("solution"))
			return composeSolution(sol);
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + sol[0].get(sol[1].size()-1).toString() + " = " + sol[1].get(sol[1].size()-1).toString() + "}";
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
		return super.getSection(name);
	}
    
	private String composeSolution(ArrayList<MathsOp>[] solution) {
		int start=0;
		if (nums[1]==0)
			start=1;
		String res = "\\ensuremath{" + solution[0].get(start).toString() + " = " + solution[1].get(start).toString() + "}";
		int i;
		for(i=start+1;i<solution[0].size()-1;i++)
			res = res + ", so \\ensuremath{" + solution[0].get(i).toString() + " = " + solution[1].get(i).toString() + "}";
//		res = res + ", so \\ensuremath{" + solution[0].get(i).toString() + " = " + solution[1].get(i).toString() + "}";
		return res;
	}
}