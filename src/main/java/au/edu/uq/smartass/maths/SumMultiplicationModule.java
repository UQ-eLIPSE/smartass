/**
 * This file is part of SmartAss and describes class SumMultiplicationModule for 
 * question like SUMi=l,m(a*i*x)
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

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * This file is part of SmartAss and describes class SumMultiplicationModule for 
 * question like SUMi=l,m(a*i*x)
 * 
 */
public class SumMultiplicationModule extends MathsModule {
    final int MAX_NUMBER = 4;
    final int MAX_ITERATOR = 6;
    MathsOp question, answer, solution;
    Variable vx = MathsUtils.createRandomVar();
    Variable dummy = new Variable(RandomChoice.makeChoice("[i]/2;[j]/2;[k]/2")[0]);
	/**
	 * @param engine	an instance of SmartAss Engine
	 */
	public SumMultiplicationModule(Engine engine) {
		super(engine);

		int l = RandomChoice.randInt(-MAX_ITERATOR, +MAX_ITERATOR);
		int m = RandomChoice.randInt(-MAX_ITERATOR, +MAX_ITERATOR);
		if(l==m) 
			m++;
		else if(l>m) {
			int tmp = l;
			l = m;
			m = tmp;
		}
		int a = RandomChoice.randInt(-MAX_NUMBER, +MAX_NUMBER);
		if(a==0) a += RandomChoice.randInt(1, +MAX_NUMBER);
		question = new Sum(
				new Equality(
						dummy,
						new IntegerNumber(l)),
				new IntegerNumber(m),
				new UnprintableMultiplication(
						MathsUtils.multiplyVarToConst(a, dummy),
						vx));
		
		solution = new Addition(
				MathsUtils.multiplyVarToConst(l*a, vx),
				MathsUtils.multiplyVarToConst((l+1)*a, vx));
		int coeff = a*(l+l+1);
		for(int i=l+2;i<=m;i++) {
			solution = new Addition(
					solution,
					MathsUtils.multiplyVarToConst(i*a, vx));
			coeff += i*a;
		}
			
		if(coeff==0)
			answer = new IntegerNumber(coeff);
		else
			answer = MathsUtils.multiplyVarToConst(coeff, vx); 
	}

    /**
     * Composes section with given name
     * "varname", "question", "shortanswer", "solution" sections is recognized
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return "\\ensuremath{" +
			question + " = " + solution + " = " + answer +"}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	return super.getSection(name);
    }
}
