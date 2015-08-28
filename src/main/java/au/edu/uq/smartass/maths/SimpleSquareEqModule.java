/** @(#)SimpleSquareEqModule.java
 * This file is part of SmartAss and describes class SimpleSquareEqModule for 
 * find x in equation f(x)=ax^2+bx+c or f(x)=(ax)^2+bx+c (some of a, b, c can be zero)
 *  
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

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * class SimpleSquareEqModule.java
 * 
 * Generates question "find x in equation f(x)=ax^2+bx+c or f(x)=(ax)^2+bx+c"
 * some of a, b, c can be zero
 * 
 */
public class SimpleSquareEqModule extends MathsModule {
	final int MAX_NUM = 10;
	final int MAX_TERM = 100;
	String f_name = "f";
	int num;
	int[] coef;
	boolean in_square;
	MathsOp var; 
	MathsOp question, answer;
	MathsOp solution;

	/**
	 * @param engine	an instance of smartass Engine
	 */
	public SimpleSquareEqModule(Engine engine) {
		super(engine);
		
		//a*x^2 (if false) or (a*x)^2 (if true)
		in_square = RandomChoice.randInt(0, 4)==0;
		num = RandomChoice.randInt(-MAX_NUM, MAX_NUM);
		int max_num = MAX_NUM;
		if(num!=0) max_num = MAX_TERM/num/num;
		if(max_num==0) 
			max_num = 1;
		else if(max_num>MAX_NUM)
			max_num = MAX_NUM;
//		System.out.println(num + " " + max_num);
		coef = new int[]{
				RandomChoice.randInt(-max_num, max_num),
				RandomChoice.randInt(-MAX_NUM, MAX_NUM),
				RandomChoice.randInt(-MAX_NUM, MAX_NUM)};
//		System.out.println(""+coef[0] + " " + coef[1] + " " + coef[2]);
		if(coef[0]==0 && coef[1]==0) //at least one of two first terms have to be nonzero
			coef[RandomChoice.randInt(0,1)] = RandomChoice.randInt(1, MAX_NUM);
		generate();

	}

	public SimpleSquareEqModule(Engine engine,String[] param) {
		super(engine);
		
		in_square = false;
		int param_count = param.length;
		if(param_count==4) {
			num = new Integer(param[3]);
			param_count--;
		} else 
			num = RandomChoice.randInt(-MAX_NUM, MAX_NUM);
		coef = new int[3];
		for(int i=0;i<param_count;i++)
			coef[i] = new Integer(param[i]);
		
		generate();
	}
	
	void generate() {
		var = MathsUtils.createRandomVar();
		MathsOp numop = new IntegerNumber(num);

		//prepare first part of equation (depending on in_square value)
		MathsOp first_part;
		MathsOp next = null;
		int first_num = 0;
		if(coef[0]!=0)
			if(!in_square) { 
				first_part = MathsUtils.multiplyVarToConst(coef[0], new Power(var, new IntegerNumber(2)));
				next = MathsUtils.coefTimesNumber(coef[0], new Power(numop, new IntegerNumber(2)));
				first_num = coef[0]*num*num;
			} else {
				first_part = new Power(MathsUtils.multiplyVarToConst(coef[0], var), new IntegerNumber(2));
				next = new Power(MathsUtils.coefTimesNumber(coef[0], numop), new IntegerNumber(2));
				first_num = (coef[0]*num)*(coef[0]*num);
			}
		else
			first_part = new IntegerNumber(0);
		
		question = new Equality(
				new Function(f_name, var),
				MathsUtils.addTwoTermsNoZeros(
						MathsUtils.addTwoTermsNoZeros(
								first_part,
								MathsUtils.multiplyVarToConst(coef[1], var)),
						new IntegerNumber(coef[2])));
		
		if(coef[0]!=0) 
			solution = next;
		else 
			solution = null;
		if(coef[1]!=0) {
			next = MathsUtils.coefTimesNumber(coef[1], numop);
			if(solution!=null)
				solution = new Addition(solution, next);
			else
				solution = next;
		}	
		if(coef[2]!=0) {
			next = new IntegerNumber(coef[2]);
			if(solution!=null)
				solution = new Addition(solution, next);
			else
				solution = next;
		}

		MathsOp step2 = null;
		if(coef[0]!=0) 
			step2 = new IntegerNumber(first_num);
		if(coef[1]!=0) {
			next = new IntegerNumber(coef[1]*num);
			if(step2!=null)
				step2 = new Addition(step2, next);
			else
				step2 = next;
		}	
		if(coef[2]!=0) {
			next = new IntegerNumber(coef[2]);
			if(step2!=null)
				step2 = new Addition(step2, next);
			else
				step2 = next;
		}
		
		solution = new Equality(
				new Function(f_name, numop),
				solution);
		solution = new Equality(
				solution,
				step2);
		solution = new Equality(
				solution,
				answer = new IntegerNumber(first_num+coef[1]*num+coef[2]));
	}
	
    /**
     * Composes section with given name
     * "varname", "functionname", "varvalue", "question", 
     * "shortanswer", "solution" sections is recognized
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return "\\ensuremath{" + solution + "}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + var.toString() + "}";
	else if (name.equals("functionname"))
		return "\\ensuremath{" + f_name + "}";
	else if (name.equals("varvalue"))
		return "\\ensuremath{" + num + "}";
	return super.getSection(name);
    }
    
    
}
