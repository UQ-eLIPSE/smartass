/** @(#)InequalityWithVarsModule.java
 *
 * This file is part of SmartAss and describes class InequalityWithVarsModule for 
 * question to write 
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.auxiliary.SmartGraph;
import au.edu.uq.smartass.engine.Engine;

/**
 * 
 */
public class InequalityWithVarsModule extends MathsModule {
	final int MAX_NUMBER = 6;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx;
    String ineq_answer, graph_answer;

	/**
	 * Constructor InequalityWithVarsModule generates a question.
	 * 
	 * @param engine
	 */
	public InequalityWithVarsModule(Engine engine) {
		super(engine);
		int tempNum;
		vx = new Variable("x");
		//0 - <, 1 - <=, 2 - >, 3 - >=
		int sign = RandomChoice.randInt(0, 3);
		int num = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER); 

		//compose inequality and graphic form
	    switch(sign) {
	    case 0: 
	    	ineq_answer = "\\left(\\infty," + num + "\\right)"; 
		    graph_answer = SmartGraph.drawInterval("-1000000", false, ""+num, false);
	    break;
	    case 1: 
	    	ineq_answer = "\\left(\\infty,"+num + "\\right]"; 
		    graph_answer = SmartGraph.drawInterval("-1000000", false, ""+num, true);
	    break;
	    case 2: 
	    	ineq_answer = "\\left("+ num + ",\\infty\\right)"; 
		    graph_answer = SmartGraph.drawInterval(""+num, false,"1000000",false);
    	break;
	    case 3: 
	    	ineq_answer = "\\left["+ num + ",\\infty\\right)"; 
	    	graph_answer = SmartGraph.drawInterval(""+num, true,"1000000",false);
	    break;
	    }
	    
		
		solution.add(0, question = answer = getSign(sign, 
				vx,
				new IntegerNumber(num)) );
		
		int[] nums1 = new int[2];
	    nums1[0] = RandomChoice.randInt(1, MAX_NUMBER);
	    if(RandomChoice.randInt(0, 1)==0)
	    	nums1[0] = - nums1[0];
	    nums1[1] = num * nums1[0];
	    if(nums1[0]!=1) {
	    	solution.add(0, getSign(sign, 
				new Division(
						MathsUtils.multiplyVarToConst(nums1[0], vx),
						new IntegerNumber(nums1[0])),
				new Division(
						new IntegerNumber(nums1[1]),
						new IntegerNumber(nums1[0]))) );
		    if(nums1[0]<0)
		    	sign = (sign + 2) % 4;
	    	solution.add(0, question = getSign(sign, 
					MathsUtils.multiplyVarToConst(nums1[0], vx),
					new IntegerNumber(nums1[1])) );
	    }
		
	    int nums2[] = new int[3];
	    nums2[2] = nums1[1]; 
	    nums2[1] = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
	    if(nums2[1]==-nums1[0])
	    	nums2[1]++;
	    nums2[0] = nums1[0] + nums2[1];
	    
	    if(nums2[1]!=0) {
	    	tempNum=-nums2[1];
	    	solution.add(0, getSign(sign, 
				MathsUtils.addTwoTermsNoZeros(
						MathsUtils.multiplyVarToConst(nums2[0], vx),
						MathsUtils.multiplyVarToConst(tempNum, vx)),
				MathsUtils.addTwoTermsNoZeros(
						MathsUtils.addTwoTermsNoZeros(
								MathsUtils.multiplyVarToConst(nums2[1], vx),
								MathsUtils.multiplyVarToConst(tempNum, vx)),
						new IntegerNumber(nums2[2]))) );
	    	solution.add(0, question = getSign(sign, 
					MathsUtils.multiplyVarToConst(nums2[0], vx),
					MathsUtils.addTwoTermsNoZeros(
							MathsUtils.multiplyVarToConst(nums2[1], vx),
							new IntegerNumber(nums2[2]))) );
	    }
	    
	    int nums3[] = new int[4];
	    nums3[0] = nums2[0]; 
	    nums3[2] = nums2[1];
	    nums3[1] = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
	    nums3[3] = nums2[2] + nums3[1];
	    
	    if(nums3[1]!=0) {
	    		tempNum=-nums3[1];
	    	solution.add(0, getSign(sign,	    	         
					MathsUtils.addTwoTermsNoZeros(
						MathsUtils.multiplyVarToConst(nums3[0], vx),
						MathsUtils.addTwoTermsNoZeros(
							new IntegerNumber(nums3[1]),
							new IntegerNumber(tempNum))),
					MathsUtils.addTwoTermsNoZeros(
						MathsUtils.multiplyVarToConst(nums3[2], vx),
					MathsUtils.addTwoTermsNoZeros(
							new IntegerNumber(nums3[3]),
							new IntegerNumber(tempNum)))) );
	    	solution.add(0, question = getSign(sign, 
					MathsUtils.addTwoTermsNoZeros(
						MathsUtils.multiplyVarToConst(nums3[0], vx),
						new IntegerNumber(nums3[1])),
					MathsUtils.addTwoTermsNoZeros(
						MathsUtils.multiplyVarToConst(nums3[2], vx),
						new IntegerNumber(nums3[3]))) );
	    }
	}

	BinaryOp getSign(int sign, MathsOp op1, MathsOp op2) {
		switch(sign) {
		  case 0: return new Less(op1,op2); 
		  case 1: return new LessOrEqual(op1,op2); 
		  case 2: return new Greater(op1,op2); 
		  case 3: return new GreaterOrEqual(op1,op2); 
		}
		return null;
	}

	/**
     * Composes section with given name
     * "varname", "question", "shortanswer", "solution", "inequality", "graph"
     *  sections is recognised
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution")) {
		String finalSolution="\\begin{align*}";
		String sign;
		for (int i=0; i<solution.size()-1; i++) {
			sign=typesetSign(solution.get(i).toString());
			finalSolution+=solution.get(i).toString().replace(" "+sign+" "," &"+sign+" ")+"\\\\";
		}
		sign=typesetSign(solution.lastElement().toString());
		finalSolution+=solution.lastElement().toString().replace(" "+sign+" "," &"+sign+" ")+"\\end{align*}";
		return finalSolution;
	}
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("interval"))
		return "\\ensuremath{" + ineq_answer + "}";
	else if (name.equals("graph"))
		return graph_answer;
	return super.getSection(name);
    }
    private String typesetSign(String input){
    	if (input.contains(" "+MathsOp.GREATER_OR_EQUAL_SIGN+" "))
    		return MathsOp.GREATER_OR_EQUAL_SIGN;
    	else if (input.contains(" "+MathsOp.GREATER_SIGN+" "))
    		return MathsOp.GREATER_SIGN;
    	else if (input.contains(" "+MathsOp.LESS_OR_EQUAL_SIGN+" "))
    		return MathsOp.LESS_OR_EQUAL_SIGN;
    	else if (input.contains(" "+MathsOp.LESS_SIGN+" "))
    		return MathsOp.LESS_SIGN;
    	else return MathsOp.EQUALITY_SIGN;
    }
}
