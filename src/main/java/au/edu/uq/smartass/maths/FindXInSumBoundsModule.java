/**
 * This file is part of SmartAss and describes class FindXInSumBoundsModule for 
 * question "Find x in expression sumi=x-1,x(a*i) = b",
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

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import au.edu.uq.smartass.engine.Engine;

/**
 * Class FindSumLowerBoundModule generates question 
 * question "Find x in expression sumi=x-1,x(a*i) = b",
 * a, b is integer numbers
 *
 * @author 
 *
 */
public class FindXInSumBoundsModule extends MathsModule {
    final int MAX_NUMBER = 4;
    final int MAX_ITERATOR = 8;
    int lowBound=RandomChoice.randInt(-1,1);
    int upperBound=lowBound+RandomChoice.randInt(1,2);
    
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx = MathsUtils.createRandomVar();
    Variable vi = new Variable(RandomChoice.makeChoice("[i]/2;[j]/2;[k]/2")[0]);

	/**
	 * @param engine 	an instance of SmartAss Engine
	 */
	public FindXInSumBoundsModule(Engine engine) {
		super(engine);
		int x = RandomChoice.randInt(-MAX_ITERATOR, MAX_ITERATOR);
		int a = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		if(a==0) a = RandomChoice.randInt(1, MAX_NUMBER);
		int b=0;
		for (int i=lowBound; i<=upperBound; i++)
			b+= a * (x+i);
		int coef=a*(upperBound-lowBound+1);
		int con=a*(upperBound+lowBound)*(upperBound-lowBound+1)/2;	
		MathsOp step;
		solution.add(question =  
				new Equality(
						new Sum(
								new Equality(vi, MathsUtils.addTwoTermsNoZeros(vx, new IntegerNumber(lowBound))),
								MathsUtils.addTwoTermsNoZeros(vx, new IntegerNumber(upperBound)),
								MathsUtils.multiplyVarToConst(a, vi)),
						new IntegerNumber(b)) );
			
		step = MathsUtils.multiplyVarToConst(a,MathsUtils.addTwoTermsNoZeros(vx,new IntegerNumber(lowBound)));
		for(int i=lowBound+1;i<=upperBound;i++) {
				step = new Addition(step, MathsUtils.multiplyVarToConst(a,
															MathsUtils.addTwoTermsNoZeros(vx, new IntegerNumber(i))));
				
		}
		solution.add(new Equality(step, new IntegerNumber(b)));
		if (con!=0)
		solution.add(new Equality(new Addition(MathsUtils.multiplyVarToConst(coef,vx),
								  new IntegerNumber(con)), new IntegerNumber(b))); 
        solution.add(new Equality(MathsUtils.multiplyVarToConst(coef,vx),new IntegerNumber(b-con)));
        
			
		/*
		solution.add(
				new Equality(
						new Addition(
								MathsUtils.multiplyVarToConst(
										a,
										new Subtraction(vx, new IntegerNumber(1))),
								MathsUtils.multiplyVarToConst(a, vx)),
						new IntegerNumber(b)) );

		solution.add(
				new Equality(
						new Addition(
								new IntegerNumber(-a),
								MathsUtils.multiplyVarToConst(2*a, vx)),
						new IntegerNumber(b)) );

		solution.add(
				new Equality(
						MathsUtils.multiplyVarToConst(2*a, vx),
						new IntegerNumber(a+b)) );
*/
		solution.add(answer = 
				new Equality(
						vx,
						new IntegerNumber(x) ));
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
	{ String sol="\\begin{align*} "
				+((Equality)solution.get(0)).getOp(0).toString()+" &= "+((Equality)solution.get(0)).getOp(1).toString() ;
	for (int i=1; i<solution.size()-1; i++)		
			sol+=" \\\\[1.8mm] "+((Equality)solution.get(i)).getOp(0).toString()+" &= "+((Equality)solution.get(i)).getOp(1).toString();
	 return sol+"\\end{align*}";
	}			
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	return super.getSection(name);
    }
}
