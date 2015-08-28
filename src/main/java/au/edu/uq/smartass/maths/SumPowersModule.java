/**
 * This file is part of SmartAss and describes class SumPowersModule for 
 * question like SUMi=l,m(a^i*i) 
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
 * Class SumPowers generates solution of question like SUMi=l,m(a^i*i)
 * 
 * @author 
 *
 */
public class SumPowersModule extends MathsModule {
    final int MAX_NUMBER = 2;
    final int MAX_ITERATOR_TOP =6;
    final int MAX_ITERATOR_BOTTOM = 2;
    MathsOp question, answer, sol1, sol2;
    Variable vi = new Variable(RandomChoice.makeChoice("[i]/2;[j]/2;[k]/2")[0]);

	/**
	 * constructor of SumPowersModule
	 * generates question, solution and answer
	 * 
	 * @param engine 	an instance of SmartAss Engine
	 */
	public SumPowersModule(Engine engine) {
		super(engine);

		int l = RandomChoice.randInt(0, MAX_ITERATOR_BOTTOM);
		int m = RandomChoice.randInt(l+1, MAX_ITERATOR_TOP);
		int a =RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		if((a==0) || (a==1)) a=-1;

		//generate question
		question = new Sum(
				new Equality(
						vi,
						new IntegerNumber(l)),
				new IntegerNumber(m),
				new UnprintableMultiplication(
						new Power(
								new IntegerNumber(a), 
								vi),
						vi));
		
		//generate step 1 and 2 of soltion
		sol1 = new Multiplication(
						new Power(
								new IntegerNumber(a),
								new IntegerNumber(l)),
						new IntegerNumber(l));
		int pow = (int) Math.pow(a, l);
		int sum = pow * l;
		sol2 = new IntegerNumber(pow*l);
		for(int i=l+1;i<=m;i++) {
			pow = (int) Math.pow(a, i);
			sol1 = new Addition(
					sol1,
					new Multiplication(
						new Power(
								new IntegerNumber(a),
								new IntegerNumber(i)),
						new IntegerNumber(i)));
			sol2 = new Addition(
					sol2,
					new IntegerNumber(pow*i));
			sum += pow*i;
		}
		
		//generate answer
		answer = new IntegerNumber(sum);
		
	}

    /**
     * Composes section with given name
     * "question", "shortanswer", "solution" sections is recognized
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return "\\ensuremath{" +
			question + " = " + sol1 + " = " + sol2 + " = " + answer +"}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	return super.getSection(name);
    }
}
