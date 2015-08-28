/**
 * 
 * This file is part of SmartAss and describes class IndefiniteIntegralModule.  
 * This class contains functionality that is common for 
 * SimpleIndefiniteIntegralModule and ComplexIndefiniteIntegralModule
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
 */
package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.Engine;

/**
 * Class IndefiniteIntegralModule.  
 * This class contains functionality that is common for  
 * SimpleIndefiniteIntegralModule and ComplexIndefiniteIntegralModule
 */
public class IndefiniteIntegralModule extends MathsModule {
	final int MAX_PARTS = 3;
	final int MAX_NUM = 8;
	Variable vx = new Variable("x");
	MathsOp question, answer;
	DerivateFunctions[] f = new DerivateFunctions[MAX_PARTS];

	/**
	 * Class IndefiniteIntegralModule contains functionality that is common for 
	 * SimpleIndefiniteIntegralModule and ComplexIndefiniteIntegralModule	 
	 */ 
	public IndefiniteIntegralModule(Engine engine) {
		super(engine);
	}
	
	protected void generate() {
		question = f[0].toMathsOp();
		answer = f[0].integrate().toMathsOp();
		for(int i=1;i<MAX_PARTS;i++) {
			question = new Addition(
					question,
					f[i].toMathsOp());
			answer = new Addition(
					answer,
					f[i].integrate().toMathsOp());
		}
		question = new IndefiniteIntegral(question, vx);
		answer = new Addition(answer, new Variable("C"));
	}

	/**
     * Composes section with given name
     * "question" and "shortanswer" sections is recognized
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{"+question.toString()+"}";
		else 
			return "\\ensuremath{" + question.toString()+ " = " + 
				answer.toString() + "}";
	}
}
