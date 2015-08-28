/* @(#)SqrtMultiply1Module.java
 *
 * This file is part of SmartAss and describes class FractionTimesFractionModule for
 * multiplication of two fractions.
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

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

/**
 * generates question and solution of type
 * sqrt(a)*(sqrt(b)+sqrt(c)) or sqrt(a)*(sqrt(b)-sqrt(c))
 */
public class SqrtMultiply1Module extends MathsModule {
    final int MAX_NUMBER = 8;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    
    /** Creates a new instance of SqrtMultiply1Module, 
     *  generates question and solution 
     */
    public SqrtMultiply1Module(Engine engine) {
    	super(engine);
        
        //generate a, b, c
        int[] nums = {
            RandomChoice.randInt(2,MAX_NUMBER),
            RandomChoice.randInt(2,MAX_NUMBER),
            RandomChoice.randInt(2,MAX_NUMBER) };
        //+ or - ?
        boolean signum = RandomChoice.randInt(0,1)==1;
        boolean sqrtFirst =RandomChoice.randInt(0,1)==1;
        //compose question/step 1
        MathsOp[] ops = {
            new Sqrt(new IntegerNumber(nums[0])),
            new Sqrt(new IntegerNumber(nums[1])),
            new Sqrt(new IntegerNumber(nums[2])) };
            
        if (sqrtFirst) {        
        if(signum)
            solution.add(question = 
                new UnprintableMultiplication(ops[0],
                    new Addition(ops[1], ops[2])) );
        else
            solution.add(question = 
                new UnprintableMultiplication(ops[0],
                    new Subtraction(ops[1], ops[2])) );
        } else {
         if(signum)
            solution.add(question = 
                new UnprintableMultiplication(
                    new Addition(ops[1], ops[2]), ops[0]) );
         else
            solution.add(question = 
                new UnprintableMultiplication(
                    new Subtraction(ops[1], ops[2]), ops[0] ));	
        } 
        	
        
        if(nums[1]==nums[2]) {
        	if(signum){
                //path 1 - b=c in (b+c) expression   
        		if(sqrtFirst)
        			solution.add(
        					new Multiplication(
        							ops[0],
        							MathsUtils.multiplyVarToConst(2, ops[2])));
        		else
        			solution.add(
        					new UnprintableMultiplication(
        							MathsUtils.multiplyVarToConst(2, ops[2]),
        							ops[0]));
       			if(nums[0]==nums[1]) {
       				//special case: a=b=c 
       				solution.add(new Multiplication(
       						new IntegerNumber(2),
       						new IntegerNumber(nums[0])));
       				solution.add(new IntegerNumber(2*nums[0]));
       			} else { //a!=b
       				solution.add(
       						MathsUtils.multiplyVarToConst(2, 
       								new Sqrt( new Multiplication(
       										new IntegerNumber(nums[0]),
       										new IntegerNumber(nums[1])))
       						));
       				solution.add(answer = 
       						MathsUtils.multiplyVarToConst(2, 
       								new Sqrt( new IntegerNumber(nums[0] * nums[1]))) );
       				int[] root= SqrtMultiplyModule.extractRoot(nums[0] * nums[1]);
       				if(root[0]!=1) {
       					solution.add(
       							new Multiplication(
       									new IntegerNumber(2),
       									SqrtMultiplyModule.composeRoot(root)) );
       					solution.add(answer = 
       						SqrtMultiplyModule.composeRoot(new int[]{2*root[0],root[1]}));
       				}
       			}        		
	        } else { //path 2 - b=c, in (b-c) expression
	            if (sqrtFirst)
	            solution.add(new Multiplication(ops[0],
	                new IntegerNumber(0)) );
	            else solution.add(new Multiplication(new IntegerNumber(0),ops[0]));
	            solution.add(answer = new IntegerNumber(0));
	        }
        } else {
        	//path 3 - b != c
            if(signum)
                solution.add(
                    new Addition(
                        new Multiplication(ops[0], ops[1]),
                        new Multiplication(ops[0], ops[2])) );
            else
                solution.add(
                    new Subtraction(
                        new Multiplication(ops[0], ops[1]),
                        new Multiplication(ops[0], ops[2])) );

            //compose step 3
            if(signum)
                solution.add(
                    new Addition(
                        new Sqrt(
                            new Multiplication(
                                new IntegerNumber(nums[0]),
                                new IntegerNumber(nums[1]))),
                        new Sqrt(
                            new Multiplication(
                                new IntegerNumber(nums[0]),
                                new IntegerNumber(nums[2])))) );
            else
                solution.add(
                    new Subtraction(
                        new Sqrt(
                            new Multiplication(
                                new IntegerNumber(nums[0]),
                                new IntegerNumber(nums[1]))),
                        new Sqrt(
                            new Multiplication(
                                new IntegerNumber(nums[0]),
                                new IntegerNumber(nums[2])))) );

            //compose step 4
            if(signum)
                solution.add(answer = 
                    new Addition(
                        SqrtMultiplyModule.multiplyRoots(nums[0], nums[1]),
                        SqrtMultiplyModule.multiplyRoots(nums[0], nums[2])) );
            else
                solution.add(answer = 
                    new Subtraction(
                        SqrtMultiplyModule.multiplyRoots(nums[0], nums[1]),
                        SqrtMultiplyModule.multiplyRoots(nums[0], nums[2])) );

            //compose step 5
            int[][] roots = {
                SqrtMultiplyModule.extractRoot(nums[0]*nums[1]),
                SqrtMultiplyModule.extractRoot(nums[0]*nums[2]) };
            //if both natural parts is 1 don't need this step in solution
            if((roots[0][0]!=1 && roots[0][1]!=1) || (roots[1][0]!=1 && roots[1][1]!=1))
                if(signum)
                    solution.add(answer = 
                        new Addition(
                            SqrtMultiplyModule.composeRoot(roots[0]),
                            SqrtMultiplyModule.composeRoot(roots[1])) );
                else
                    solution.add(answer = 
                        new Subtraction(
                            SqrtMultiplyModule.composeRoot(roots[0]),
                            SqrtMultiplyModule.composeRoot(roots[1])) );

            //compose step 5 - only if a root part of both operands is equal
            if(roots[0][1]==roots[1][1])
                if(signum)
                    solution.add(answer = 
                        SqrtMultiplyModule.composeRoot(new int[]{roots[0][0]+roots[1][0],roots[0][1]}) );
                else
                    solution.add(answer = 
                        SqrtMultiplyModule.composeRoot(new int[]{roots[0][0]-roots[1][0],roots[0][1]}) );
        }
    }
    
    /**
     * Composes section with given name
     * "question", "shortanswer" and "solution" sections is recognised
     * 
     * @param name section name
     **/
    public String getSection(String name) {
	if(name.equals("question"))
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution")) {	
		String res = "\\begin{align*}"+solution.get(0).toString();
		for(int i=1;i<solution.size();i++)
			res += "&=" + solution.get(i).toString()+"\\\\";
	    res+="\\end{align*}";
		return res;
		}

	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	return super.getSection(name);
    }
}
