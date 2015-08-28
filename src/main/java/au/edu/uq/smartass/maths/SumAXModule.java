/**
 * 
 * This file is part of SmartAss and describes class SumAXModule for 
 * question "Find x in equation SUMi=l,m(a*x)=b" where a, b is integer numbers   
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
 * Class SumAXModule for question 
 * "Find x in equation SUMi=l,m(a*x)=b" where a, b is integer numbers
 *    
 */
public class SumAXModule extends MathsModule {
    final int MAX_NUMBER = 4;
    final int MAX_ITERATOR = 4;
    final int MAX_ITERATOR_RANGE = 5;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx = MathsUtils.createRandomVar();
    Variable vi = new Variable("i");

	/**
	 * @param engine 	an instance of SmartAss Engine
	 */
	public SumAXModule(Engine engine) {
		super(engine);

		int l = RandomChoice.randInt(-MAX_ITERATOR, MAX_ITERATOR);
		int m = RandomChoice.randInt(1, MAX_ITERATOR_RANGE) + l;
		int a = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		if(a==0) a += RandomChoice.randInt(1, MAX_NUMBER);
		int k = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		int b = a * (m - l + 1) * k;
		
		//question/step 1 of solution
		solution.add(question = new Equality(
				new Sum(
						new Equality(vi,new IntegerNumber(l)),
						new IntegerNumber(m),
						MathsUtils.multiplyVarToConst(a, vx)),
				new IntegerNumber(b)) );
		
		//step 2 of solution
		MathsOp vax = MathsUtils.multiplyVarToConst(a,vx);
		MathsOp step =vax;
		for(int i=l+1;i<=m;i++)
			step = new Addition((MathsOp)(step.clone()), vax);
		solution.add(new Equality((MathsOp)(step.clone()),	new IntegerNumber(b)) );
		
		//step 3 of solution
		solution.add(new Equality(
				MathsUtils.multiplyVarToConst(a*(m-l+1), vx),
				new IntegerNumber(b)));
		
		//answer/step 4 of solution
		solution.add(answer = new Equality(
				vx,
				new IntegerNumber(k)));
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
    		MathsUtils.composeSolution(solution, "},\\qquad so\\qquad \\ensuremath{") +
    		"}";
    	else if (name.equals("shortanswer"))
    		return "\\ensuremath{" + answer.toString() + "}";
    	else if (name.equals("varname"))
    		return "\\ensuremath{" + vx.toString() + "}";
    	return super.getSection(name);
    }
}
