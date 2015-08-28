/**
 * This file is part of SmartAss and describes class ToSumNotationModule for 
 * question "Write sum of 1/i in summation notation"  
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
 * Class ToSumNotationModule generates 
 * question - an expression like 1/l + 1/(l+1) + ... + 1/m or l +(l+1)+(l+2) etc. 
 * and answer that is a summation notation for given expression 
 *  
 * @author 
 *
 */
public class ToSumNotationModule extends MathsModule {
    final int MAX_NUMBER = 6;
    final int MAX_ITERATOR_TOP = 10;
    final int MAX_ITERATOR_BOTTOM = 5;
    Variable dummy=new Variable(RandomChoice.makeChoice("[i]/2;[j]/2;[k]/2")[0]); 
    boolean multiply = (RandomChoice.randInt(0,1)==1) ? true : false; //multiplication or division?	
    MathsOp question, answer;
    MathsOp sol = null;

	/**
	 * constructor of ToSumNotationModule
	 * generates question, solution and answer
	 *
	 * @param engine 	an instance of SmartAss Engine
	 */
	public ToSumNotationModule(Engine engine) {
		super(engine);
		
		int l = RandomChoice.randInt(1, MAX_ITERATOR_BOTTOM);
		int m = RandomChoice.randInt(l+2, MAX_ITERATOR_TOP);
		int a = RandomChoice.randInt(-MAX_NUMBER, MAX_NUMBER);
		if(a==0) a += RandomChoice.randInt(1, MAX_NUMBER);
		
		answer = new Sum(
				new Equality(
						dummy,
						new IntegerNumber(l)),
				new IntegerNumber(m),
				generateTerm(multiply,
						new IntegerNumber(a),
						dummy));
						
		if (!multiply) {
		 if (a>=0) {
		 	question =new FractionOp(new IntegerNumber(a)
					                     ,new IntegerNumber(l));
		for(int i=l+1;i<=m;i++) 
			question = new Addition(
					question,
					new FractionOp(
							new IntegerNumber(a),
							new IntegerNumber(i)));
		 } else { //a<0
		 question =new UnaryMinus(new FractionOp(new IntegerNumber(-a)
					                     ,new IntegerNumber(l)));
		 for(int i=l+1;i<=m;i++) 
			question = new Subtraction(
					question,
					new FractionOp(
							new IntegerNumber(-a),
							new IntegerNumber(i)));
		 } 
		} else { //multiplication
			question =new IntegerNumber(a*l);
		 for(int i=l+1;i<=m;i++) 
			question = new Addition(
					question,
					new IntegerNumber(a*i));
		 if (Math.abs(a)!=1) {
		 	sol=new Multiplication(new IntegerNumber(a), new IntegerNumber(l));
		   for(int i=l+1;i<=m;i++) 
			 sol = new Addition(
					sol,
					new Multiplication(new IntegerNumber(a), new IntegerNumber(i)));	
		 }						
		}
	}

    /**
     * Composes section with given name
     * "question", "shortanswer", "solution" sections is recognised
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution")){
		if (sol==null)
		return "\\ensuremath{" +
			question + " = " + answer +"}";
		else return "\\ensuremath{"+ 
			question + "\\\\[1.8mm] = " + sol+ 
				" \\\\[1.8mm] = "+ answer +"}";	
	}	
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	return super.getSection(name);
    }
    
    private MathsOp generateTerm(boolean multiplication, MathsOp op1, MathsOp op2){
         if (multiplication){
         	if  (op1 instanceof IntegerNumber) {
         		if (((IntegerNumber)op1).getInt()==1)	
         			return (MathsOp)op2.clone();
         		if (((IntegerNumber)op1).getInt()==-1)
         			return new UnaryMinus(op2);
         	}
            return new UnprintableMultiplication(op1, op2);
         } else 
         return new FractionOp(op1, op2);	        		
    }
    
}
