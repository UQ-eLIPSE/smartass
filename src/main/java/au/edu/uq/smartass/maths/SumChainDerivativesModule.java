/**
 * This file is part of SmartAss and describes class ChainDerivativesModule for 
 * question "Find the derivative of sum of simple functions and using chaine rule" 
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

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class ChainDerivativesModule for question 
 * "Find the derivative of sum of simple functions and using chaine rule"
 *  
 */
public class SumChainDerivativesModule extends MathsModule {
	final int MIN_PARTS = 2;
	final int MAX_PARTS = 4;
	final int MAX_NUM = 8;
	
	int num_parts = RandomChoice.randInt(MIN_PARTS, MAX_PARTS);
	int e_pos = RandomChoice.randInt(0, num_parts-1);
	int e_c = RandomChoice.randInt(2, MAX_NUM)*RandomChoice.randSign();
	DerivateFunctions f[] = new DerivateFunctions[num_parts],
		df[] = new DerivateFunctions[num_parts]; 
	
	Variable vx = new Variable("x");
	MathsOp question, answer;

	/**
	 * @param engine	an instance of MathsOp engine
	 */
	public SumChainDerivativesModule(Engine engine) {
		super(engine);
		
		for(int i=0; i<num_parts;i++) {
			if(i==e_pos)
				//	-1 - 1/(x power n), 0 - x power n, 1 - sin, 2 - cos, 3 - e power x, 4 - ln x 
				f[i] = new DerivateFunctions(3,	1,
						MathsUtils.multiplyVarToConst(e_c, vx));
			else 
				f[i] = new DerivateFunctions(
					RandomChoice.randInt(0, 4),
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(),
					vx);
			df[i] = f[i].derivate();
			if(i==e_pos) 
				df[i].cons = df[i].cons*e_c;
		}
		question = f[0].toMathsOp();
		for(int i=1;i<num_parts;i++)
			question = new Addition(question, f[i].toMathsOp());
		
		answer = df[0].toMathsOp();
		for(int i=1;i<num_parts;i++)
			answer = new Addition(answer, df[i].toMathsOp());
	}

	/**
	 * Composes section with given name
	 * "question", "solution", "shortanswer" sections is recognized
	 * (actually, whatever but "question" will return "shortanswer" 
	 * 
	 * @param name	section name
	 **/
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{y = "+question.toString()+"}";
		else 
			 return "\\ensuremath{\\frac{dy}{dx} = " + answer.toString() + "}";

	}
}

