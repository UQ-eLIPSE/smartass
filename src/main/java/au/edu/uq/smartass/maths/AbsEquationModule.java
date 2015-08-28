/**
 * @(#)AbsEquationModule.java
 *
 *
 * @author 
 * @version 1.00 2007/1/17
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class AbsEquationModule extends LinearEquationModule {
	final int MAX_NUMBER = 6;
	
	ArrayList<MathsOp>[] sol1,	sol2;
    private String firstLine;
    int[] nums;
    public AbsEquationModule(Engine engine) {
    	super(engine);
        
    	nums = generateNumbers(2, MAX_NUMBER, -MAX_NUMBER, MAX_NUMBER, 0, MAX_NUMBER);
    	Variable v=initVar();
		sol1 = compose(nums);    	
		nums[2] = - nums[2];
		sol2 = compose(nums);   
		if (RandomChoice.randInt(0,1)==0) 
			firstLine=sol1[0].get(0).toString();
		else 
			if (nums[1]==0)
			 firstLine=MathsUtils.multiplyVarToConst(nums[0],v).toString();
			else
			 firstLine=(new Addition(MathsUtils.multiplyVarToConst(nums[0],v), new IntegerNumber(nums[1]))).toString();  	
    }
   
	public String getSection(String name) {
		
		if(name.equals("question"))	
			return "\\ensuremath{\\lvert" + firstLine + "\\rvert = " + sol1[1].get(0).toString() + "}";			
		else if (name.equals("equation1"))
		 if (nums[1]==0)
			  return "\\ensuremath{" + sol1[0].get(1).toString() + " = " + sol1[1].get(1).toString() + "}";	
			else	
			 return "\\ensuremath{" + sol1[0].get(0).toString() + " = " + sol1[1].get(0).toString() + "}";
		 	
			//return "\\ensuremath{" + sol1[0].get(0).toString() + " = " + sol1[1].get(0).toString() + "}";
		else if (name.equals("equation2"))
			if (nums[1]==0)
			  return "\\ensuremath{" + sol1[0].get(1).toString() + " = " + sol1[1].get(1).toString() + "}";	
			else	
			 return "\\ensuremath{" + sol1[0].get(0).toString() + " = " + sol1[1].get(0).toString() + "}";			
		else if (name.equals("solution1"))
			return composeSolution(sol1);
		else if (name.equals("solution2"))
			return composeSolution(sol2);
		else if (name.equals("shortanswer1"))
			return "\\ensuremath{" + sol1[0].get(sol1[0].size()-1).toString() + " = " + 
				sol1[1].get(sol1[0].size()-1).toString() + "}";
		else if (name.equals("shortanswer2"))
			return "\\ensuremath{" + sol2[0].get(sol2[0].size()-1).toString() + " = " + 
				sol2[1].get(sol2[0].size()-1).toString() + "}";
		else if (name.equals("shortanswer"))
			    if (nums[2]==0) 
			    	return getSection("shortanswer1");
			    else 
			    	return getSection("shortanswer1")+" and "+getSection("shortanswer2");
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";		
		else if (name.equals("solution")){
			String solution;			
			solution="\\ensuremath{\\lvert" + firstLine + "\\rvert = " + sol1[1].get(0).toString()+"}, so \n";	
			if (nums[2]==0){			
			    solution+="\\begin{align*} \n";			 
			    solution+=composeSolution(sol1) +"\\end{align*}";
			    solution+="Hence the solution is: \\ensuremath{"+sol1[0].get(sol1[0].size()-1).toString() + " = " + 
				sol1[1].get(sol1[0].size()-1).toString() +"}";
			    return solution; 
			} else {
				solution+="\\begin{alignat*}{3} \n";
				int start=0;
				if (nums[1]==0)
					start=1;
				solution+=" & "+ sol1[0].get(start).toString() + " = " + sol1[1].get(start).toString()
					+"&\\hspace{20 mm}  or    \\hspace{20 mm} "+" & "+ sol2[0].get(start).toString() + " = " + sol2[1].get(start).toString() ; 	
				for(int i=start+1;i<sol1[0].size()-1;i++)
				  solution +=  "\\\\ \n & " + sol1[0].get(i).toString() + " = " + sol1[1].get(i).toString()
				  	 + "&&"+sol2[0].get(i).toString() + " = " + sol2[1].get(i).toString();
			    solution+="\\end{alignat*} Hence the solutions are: \\ensuremath{"+sol1[0].get(sol1[0].size()-1).toString() + " = "
					+ sol1[1].get(sol1[0].size()-1).toString() +"} and "
					+ "\\ensuremath{"+sol2[0].get(sol2[0].size()-1).toString() + " = "
					+ sol2[1].get(sol2[0].size()-1).toString() +"}";	
			   return solution;		
			}	   
		}
		return super.getSection(name);
		
	}
    
	private String composeSolution(ArrayList<MathsOp>[] solution) {
	
		int start=0;
		if (nums[1]==0)
			start=1;
		String res = " & "+ solution[0].get(start).toString() + " = " + solution[1].get(start).toString();
		int i;
		for(i=start+1;i<solution[0].size()-1;i++)
			res +=  "\\\\ \n & " + solution[0].get(i).toString() + " = " + solution[1].get(i).toString();
		return res;
	}
}