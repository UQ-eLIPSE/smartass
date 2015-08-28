/**
 * 
 * This file is part of SmartAss and describes class SumIPowersModule for 
 * question "Find x in equation SUMi=l,m b(i^a)" where a, b is integer numbers   
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
 * Class SumIPowersModule for question 
 * "Find x in equation SUMi=l,m(a*x)=b" where a, b is integer numbers
 *
 */
public class SumIPowersModule  extends MathsModule {
    final int MAX_POWER = 3;
    final int MAX_ITERATOR = 4;
    final int MAX_ITERATOR_RANGE = 5;
    final int MAX_CONST=5;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx = MathsUtils.createRandomVar();
    Variable vi = new Variable(RandomChoice.makeChoice("[i]/2;[j]/2;[k]/2")[0]);

	/**
	 * @param engine 	an instance of SmartAss Engine
	 * 
	 */
	public SumIPowersModule(Engine engine) {
		super(engine);
		
		int l = RandomChoice.randInt(-MAX_ITERATOR, MAX_ITERATOR);
		int m = RandomChoice.randInt(1, MAX_ITERATOR_RANGE) + l;
		int a = RandomChoice.randInt(0, MAX_POWER);
		int b = RandomChoice.randInt(1, MAX_CONST);
		if (RandomChoice.randInt(0,2)==0) b=-b;
		MathsOp opa = new IntegerNumber(a);
		
		//question/step 1 of solution
		solution.add(new Sum(
						new Equality(vi, new IntegerNumber(l)),
						new IntegerNumber(m),
						generateTerm(b, new Power(vi,opa)))) ;
		
		question = solution.get(0);
		
		//steps 2-4 of solution
		int sumpow = (int)Math.pow(l, a)*b;
		MathsOp step = generateTerm(b,new Power(new IntegerNumber(l), new IntegerNumber(a)));
		MathsOp step3 = new IntegerNumber(sumpow);
		for(int i=l+1;i<=m;i++) {
			step = new Addition(step, generateTerm(b,new Power(new IntegerNumber(i), new IntegerNumber(a))));
			step3 = new Addition(step3, new IntegerNumber((int)Math.pow(i, a)*b));
			sumpow += (int)Math.pow(i, a)*b;
		}
		
		solution.add(step); //step 2
		solution.add(step3); //step 3
		solution.add(answer = new IntegerNumber(sumpow)); //step 4
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
    		MathsUtils.composeSolution(solution, " = ") + " = "+solution.lastElement().toString()+ 
    		"}";
    	else if (name.equals("shortanswer"))
    		return "\\ensuremath{" + answer.toString() + "}";
    	else if (name.equals("varname"))
    		return "\\ensuremath{" + vx.toString() + "}";
    	return super.getSection(name);
    }
    
    private MathsOp generateTerm (int con, MathsOp op){
    	if (con==1)
    		return op;
    	if (con==-1)
    		return new UnaryMinus(op);
    	if (op instanceof IntegerNumber)	
    	return new Multiplication(new IntegerNumber(con), op);
    	if ((op instanceof Power) && (((Power)op).getOp(0) instanceof IntegerNumber))
    			return new Multiplication(new IntegerNumber(con), op);
    	return new UnprintableMultiplication(new IntegerNumber(con), op);
    		  
    }
}
