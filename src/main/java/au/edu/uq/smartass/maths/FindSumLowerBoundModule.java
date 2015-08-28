/**
 * This file is part of SmartAss and describes class FindSumLowerBoundModule for 
 * question "Find x, where x is an lower bound of expression sumi=x,m(a*i) = b",
 * a, b is integer numbers.
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
 * Class FindSumLowerBoundModule generates question 
 * "Find x, where x is an upper bound of expression sumi=x,m(a*i) = b",
 * a, b is integer numbers
 *
 * @author 
 *
 */
public class FindSumLowerBoundModule extends MathsModule {
    final int MAX_NUMBER = 4;
    final int MAX_ITERATOR_BOTTOM = 4;
    final int MAX_ITERATOR_RANGE = 4;
    MathsOp question, answer;
    Variable vx = MathsUtils.createRandomVar();
    Variable vi = new Variable("i");

	/**
	 * @param engine 	an instance of SmartAss Engine
	 */
	public FindSumLowerBoundModule(Engine engine) {
		super(engine);
		
		int m = RandomChoice.randInt(-MAX_ITERATOR_BOTTOM,0);
		int x = RandomChoice.randInt(m-MAX_ITERATOR_RANGE,m-1);
		int a = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		if(a==0) a = RandomChoice.randInt(1, MAX_NUMBER);
		int b = a*x;
		for(int i=x+1;i<=m;i++) 
			b += a*i;
		
		question = new Equality(
				new Sum(
					new Equality(vi, vx),
					new IntegerNumber(m),
					MathsUtils.multiplyVarToConst(a,vi)),
				new IntegerNumber(b));
		
		answer = new Equality(vx, new IntegerNumber(x));
	}

    /**
     * Composes section with given name
     * "varname", "question", "shortanswer", sections is recognized
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
		if(name.equals("question")) 
			return "\\ensuremath{" + question.toString() + "}";
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + answer.toString() + "}";
		else if (name.equals("varname"))
			return "\\ensuremath{" + vx.toString() + "}";
		return super.getSection(name);
    }
}
