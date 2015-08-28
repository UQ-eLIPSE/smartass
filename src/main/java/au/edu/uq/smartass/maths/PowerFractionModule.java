/** @(#)PowerFractionModule.java
 * This file is part of SmartAss and describes class PowerFractionModule for 
 * question (x^n*x^m)/(x^l*x^k)
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
import au.edu.uq.smartass.engine.Engine;

/**
 * class PowerFractionModule generates solution for question (x^n*x^m)/(x^l*x^k)
 *
 */
public class PowerFractionModule extends MathsModule {
    final int MAX_NUMBER = 5;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx = MathsUtils.createRandomVar();
    IntegerNumber coef=new IntegerNumber((RandomChoice.randInt(0,2)==0) 
    	? RandomChoice.randInt(-15,-1) : RandomChoice.randInt(1,15));
	/**
	 * @param engine	engine instance
	 */
	public PowerFractionModule(Engine engine) {
		super(engine);
		MathsOp finalThing;
		int finalPow;
		int[] pws = {
				RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER),
				RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER),
				RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER),
				RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER)};
		
		solution.add(question = 
			new FractionOp(removeOneFromMultiplication(coef,
				new UnprintableMultiplication(
					new Power(vx, new IntegerNumber(pws[0])),
					new Power(vx, new IntegerNumber(pws[1])))),
				new UnprintableMultiplication(
					new Power(vx, new IntegerNumber(pws[2])),
					new Power(vx, new IntegerNumber(pws[3])))) );

		solution.add( 
			new FractionOp(removeOneFromMultiplication(coef,
				new Power(vx, new Addition(
					new IntegerNumber(pws[0]),
					new IntegerNumber(pws[1])))),
				new Power(vx, new Addition(
					new IntegerNumber(pws[2]),
					new IntegerNumber(pws[3])))) );

		solution.add( 
				new FractionOp(removeOneFromMultiplication(coef,
					new Power(vx, new IntegerNumber(pws[0] + pws[1]))),
					new Power(vx, new IntegerNumber(pws[2] + pws[3]))) );

		//if(pws[2] + pws[3]>=0)  // Michael didn't want 1+1, he wanted 1-(-1)
			solution.add(removeOneFromMultiplication(coef, 
				new Power(vx, new Subtraction( 
					new IntegerNumber(pws[0] + pws[1]),
					new IntegerNumber(pws[2] + pws[3]))) ));
		//else
		//	solution.add(removeOneFromMultiplication(coef, 
		//		new Power(vx, new Addition( 
		//			new IntegerNumber(pws[0] + pws[1]),
		//			new IntegerNumber(-(pws[2] + pws[3])))) ));
		
		finalPow=pws[0] + pws[1]-(pws[2] + pws[3]);
		solution.add(removeOneFromMultiplication(coef,
				new Power(vx, 
					new IntegerNumber(finalPow)) ));
		if (finalPow==0)		
			solution.add(answer = (IntegerNumber)(coef.clone()));
		else if (finalPow==1)
			solution.add(answer=removeOneFromMultiplication(coef,
				vx) );
		else 
			answer=solution.lastElement();		 
	}

    /**
     * Composes section with given name
     * "varname", "question", "shortanswer" and "solution" sections is recognised
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return MathsUtils.composeSolution(solution);
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	return super.getSection(name);
	
	}
	
  private MathsOp removeOneFromMultiplication(MathsOp op1, MathsOp op2){
   	if (op1 instanceof IntegerNumber) {
   	 int num=((IntegerNumber)op1).getInt();
   	  if (num==1)
   	  	return (MathsOp)(op2.clone());
   	  else if (num==-1)
   	  	return new UnaryMinus(op2);		
   	}
    return new UnprintableMultiplication(op1,op2);	
 }
} 