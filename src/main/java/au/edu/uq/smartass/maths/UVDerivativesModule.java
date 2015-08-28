/**
 * 
 * This file is part of SmartAss and describes class UVDerivativesModule, the 
 * ancestor of QuotientDerivativesModule and MultiplyDerivativesModule holding 
 * common for both classes functionality 
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
 * Class UVDerivativesModule is the ancestor of QuotientDerivativesModule 
 * and MultiplyDerivativesModule and contains functionality that is common for both classes  
 */
public class UVDerivativesModule extends MathsModule {
	final int MAX_NUM = 8;
	DerivateFunctions f[] = new DerivateFunctions[2],
	fs[] = new DerivateFunctions[2];
	Variable vx = new Variable("x");
	MathsOp question, u, v;
	Vector<MathsOp> solution = new Vector<MathsOp>();
	int c[];

	/**
	 * @param engine	an instance of smartass Engine
	 */
	public UVDerivativesModule(Engine engine) {
		super(engine);
		
		c = new int[]{RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign(), 
				RandomChoice.randInt(1, MAX_NUM)*RandomChoice.randSign()};
		for(int i=0;i<2;i++) {
			int t = RandomChoice.randInt(1, MAX_NUM);
			f[i] = new DerivateFunctions(
					0,
					t*RandomChoice.randSign(),
					RandomChoice.randInt(1, MAX_NUM-t+2),  //make numbers not so big...
					vx);
			fs[i] = f[i].derivate();
		}
	}

	/**
     * Composes section with given name
     * "question", "shortanswer", "u", "v", "du", "dv", "solution" sections is recognized
     * 
     * @param name	section name
     **/
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{"+question.toString() + "}";
		else if (name.equals("u"))
			return "\\ensuremath{u = " + u.toString() + "}";
		else if (name.equals("v"))
			return "\\ensuremath{v = " + v.toString() + "}";
		else if (name.equals("du"))
			return "\\ensuremath{u' = " + fs[0].toMathsOp().toString() + "}";
		else if (name.equals("dv"))
			return "\\ensuremath{v' = " + fs[1].toMathsOp().toString() + "}";
		else if (name.equals("solution"))
			return "\\ensuremath{\\frac{dy}{dx} = " + MathsUtils.composeSolution(solution) + "}";
		else //if(name.equals("shortanswer"))
			 return "\\ensuremath{y' = " + solution.lastElement().toString() + "}";
	}
}
