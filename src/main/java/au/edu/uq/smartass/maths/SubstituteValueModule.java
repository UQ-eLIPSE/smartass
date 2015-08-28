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

public class SubstituteValueModule extends LinearEquationModule {
	final int MAX_NUMBER = 6;
	ArrayList<MathsOp>[] sol;
    private int[] nums;
    Variable substVar;
    private final int leftSide=RandomChoice.randInt(0,1);
    private final int rightSide=(leftSide+1)%2;
    public SubstituteValueModule(Engine engine) {
    	super(engine);
    	nums = generateNumbers(2, MAX_NUMBER, -MAX_NUMBER, MAX_NUMBER, -3, MAX_NUMBER);
    	int varChoice1=RandomChoice.randInt(0,2);
    	int varChoice2=(varChoice1+RandomChoice.randInt(1,2))%3;
    	vx = new Variable(varss[varChoice1]);    	
    	substVar= new Variable(varss[varChoice2]);	
		sol = compose(nums);    	
    }
    
	public String getSection(String name) {
		if(name.equals("question")) {
		if (leftSide==1) {
			if (nums[1]==0)
			  return "\\ensuremath{" + substVar.toString() + " = " + sol[0].get(1).toString() + "}";	
			else	
			 return "\\ensuremath{" + substVar.toString() + " = " + sol[0].get(0).toString() + "}";
		 } else {
		    if (nums[1]==0)
			  return "\\ensuremath{"  + sol[0].get(1).toString() + " = " + substVar.toString()+"}";	
			else	
			 return "\\ensuremath{" + sol[0].get(0).toString() + " = " + substVar.toString()+"}";	
		 } 
          		 
		} 
		else if (name.equals("solution"))
			return composeSolution(sol);
	    else if (name.equals("vartosubstitutename"))
	    	return "\\ensuremath{"+substVar.toString()+"}";
	    else if (name.equals("valuetosubstitute"))
	    	return String.valueOf(nums[2]);
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
		String res = "\\ensuremath{" + solution[leftSide].get(start).toString() + " = " + solution[rightSide].get(start).toString() + "}";
		int i;
		for(i=start+1;i<solution[0].size()-1;i++)
			res = res + ", so \\ensuremath{" + solution[0].get(i).toString() + " = " + solution[1].get(i).toString() + "}";
//		res = res + ", so \\ensuremath{" + solution[0].get(i).toString() + " = " + solution[1].get(i).toString() + "}";
		return res;
	}
}