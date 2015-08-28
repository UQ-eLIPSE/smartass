/**
 * 
 * This file is part of SmartAss and describes class SumXModule for 
 * question "Find x in equation SUMi=l,m(x*i)=a"   
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
 * Class SumXModule: generates question 
 * "Find x in equation SUMi=l,m(x*i)=a"
 */
public class SumXModule extends MathsModule {
    final int MAX_NUMBER = 4;
    final int MAX_ITERATOR = 4;
    final int MAX_ITERATOR_RANGE = 5;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx = MathsUtils.createRandomVar();
    Variable vi = new Variable("i");

	/**
	 * @param engine an instance of application Engine
	 */
	public SumXModule(Engine engine) {
		super(engine);
		
		int l = RandomChoice.randInt(-MAX_ITERATOR, MAX_ITERATOR);
		int m = RandomChoice.randInt(1, MAX_ITERATOR_RANGE) + l;
		if(m==-l) m++;		
		int k = l;
		MathsOp step = null;
		step = MathsUtils.multiplyVarToConst(l,vx);
		for(int i=l+1;i<=m;i++)
			if(i==0) 
				step = new Addition((MathsOp)(step.clone()), new IntegerNumber(0));
			else {
				step = new Addition((MathsOp)(step.clone()), MathsUtils.multiplyVarToConst(i,vx));
				k += i;
			}
		int a = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);

		solution.add(question = new Equality(
				new Sum(
						new Equality(vi,new IntegerNumber(l)),
						new IntegerNumber(m),
						new UnprintableMultiplication(vx, vi)),
				new IntegerNumber(a*k)) );
		
		solution.add(new Equality((MathsOp)(step.clone()), new IntegerNumber(a*k)));
		
		solution.add(new Equality(
				MathsUtils.multiplyVarToConst(k, vx),
				new IntegerNumber(a*k)) );
		
		solution.add(answer = new Equality(vx, new IntegerNumber(a)));
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
