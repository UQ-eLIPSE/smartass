/** @(#)PowerOperationsModule.java
 * This file is part of SmartAss and describes class PowerFractionModule for 
 * multiplication and division of powers of to different variables 
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

public class PowerOperationsModule extends MathsModule {
    final int MAX_POWER = 3;
    final int MAX_ELEMENTS = 6;
    MathsOp question, answer;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    Variable vx[] = {new Variable("x"), new Variable("y")};
	
	/**
	 * @param engine	engine instance
	 */
    public PowerOperationsModule(Engine engine) {
		super(engine);
		
		//what variable in wich postion is: 0 is x and 1 is y
		int[] pattern = new int[MAX_ELEMENTS]; 
		for(int i=0;i<MAX_ELEMENTS;i++)
			pattern[i] = RandomChoice.randInt(0, 1);
		
		//positions of operations 
		int[] oppos = new int[2];
		oppos[0] = RandomChoice.randInt(1, MAX_ELEMENTS-2);
		oppos[1] = RandomChoice.randInt(oppos[0]+1, MAX_ELEMENTS-1);
		    
		//0 - the multiplication is first, 1 the division is
		int	oporder = RandomChoice.randInt(0, 1);
		
		//get powers for each variable 
		int[] powers = new int[MAX_ELEMENTS];
		for(int i=0;i<MAX_ELEMENTS;i++)
			if(RandomChoice.randInt(0,10)==0) //lower probability of zero
				powers[i] = 0; 
			else {
				powers[i] = RandomChoice.randInt(1, MAX_POWER);
				if(RandomChoice.randInt(0,1)==0)
					powers[i] = - powers[i];
			}
		
		//question/step 1 of solution
		MathsOp[] vops = new MathsOp[MAX_ELEMENTS];
		for(int i=0;i<MAX_ELEMENTS;i++)
			vops[i] = new Power(vx[pattern[i]], new IntegerNumber(powers[i]));

		MathsOp[] ops = new MathsOp[3];
		ops[0] = vops[0];
		for(int i=1;i<oppos[0];i++)
			ops[0] = new UnprintableMultiplication(ops[0], vops[i]);
		ops[1] = vops[oppos[0]];
		for(int i=oppos[0]+1;i<oppos[1];i++)
			ops[1] = new UnprintableMultiplication(ops[1], vops[i]);
		ops[2] = vops[oppos[1]];
		for(int i=oppos[1]+1;i<MAX_ELEMENTS;i++)
			ops[2] = new UnprintableMultiplication(ops[2], vops[i]);
		
		if(oporder==0)
		 question = ops[2] instanceof UnprintableMultiplication		
		 	        ? new Division(new Multiplication(ops[0],ops[1]), new Brackets(ops[2])) 
		 	        : new Division(new Multiplication(ops[0],ops[1]), ops[2]); 	         	  		
		else
		 question = ops[1] instanceof UnprintableMultiplication	
		 			? new Multiplication(new Division(ops[0],new Brackets(ops[1])),ops[2])
		 			: new Multiplication(new Division(ops[0],ops[1]),ops[2]);
//		solution.add(question);
		
		//step 2 of solution - turn division into multiplication
		int ri, re, ro;
		if(oporder==1) {
			ri = oppos[0];
			re = oppos[1];
			ro = 0; 
		} else {
			ri = oppos[1];
			re = MAX_ELEMENTS;
			ro = 1;
		} 
		for(int i=ri;i<re;i++) {
			powers[i] = -powers[i];
			vops[i] = new Power(vx[pattern[i]], new IntegerNumber(powers[i]));
		}
		ops[ro+1] = vops[oppos[ro]];
		for(int i=oppos[ro]+1;i<re;i++)
			ops[ro+1] = new UnprintableMultiplication(ops[ro+1], vops[i]);
		solution.add(new Multiplication(ops[0], new Multiplication(ops[1], ops[2])) );
		
		//step 3 - sort powers by variable name
		int[] xpowers = new int[MAX_ELEMENTS];
		int[] ypowers = new int[MAX_ELEMENTS];
		int xpc = 0, ypc = 0;
		for(int i=0;i<MAX_ELEMENTS;i++)
			if(pattern[i]==0)
				xpowers[xpc++] = powers[i];
			else
				ypowers[ypc++] = powers[i];
		MathsOp xop = new Power(vx[0], new IntegerNumber(xpowers[0])),
			yop = new Power(vx[1], new IntegerNumber(ypowers[0]));
		for(int i=1;i<xpc;i++) 
			xop = new UnprintableMultiplication(xop, 
					new Power(vx[0], new IntegerNumber(xpowers[i])));
		for(int i=1;i<ypc;i++) 
			yop = new UnprintableMultiplication(yop, 
					new Power(vx[1], new IntegerNumber(ypowers[i])));
		solution.add(new UnprintableMultiplication(xop, yop));

		//step 4 - transform x^a*x^b into x^(a+b)
		xop = new IntegerNumber(xpowers[0]);
		for(int i=1;i<xpc;i++) 
			xop = new Addition(xop, new IntegerNumber(xpowers[i]));
		yop = new IntegerNumber(ypowers[0]);
		for(int i=1;i<ypc;i++) 
			yop = new Addition(yop, new IntegerNumber(ypowers[i]));
		solution.add(new UnprintableMultiplication(
			new Power(vx[0], xop),
			new Power(vx[1], yop)) );
		
		//step 5 - calc a sum of powers
		int xp = xpowers[0], yp = ypowers[0];
		for(int i=1;i<xpc;i++) xp += xpowers[i];
		for(int i=1;i<ypc;i++) yp += ypowers[i];
		solution.add(answer = //this will be answer, if none of powers is 0 or 1
			new UnprintableMultiplication(
				xop = new Power(vx[0], new IntegerNumber(xp)),
				yop = new Power(vx[1], new IntegerNumber(yp))) );
		if (xp==1) xop=vx[0]; 
		if (yp==1) yop=vx[1];			
		if (xp==0 || yp==0 || xp==1 || yp==1 ){
		//x^0 = 1, so drop it out
		if(xp==0 || yp==0) {
			if(xp!=0)
				answer = xop;
			else if(yp!=0)
				answer = yop;
			else
				answer = new IntegerNumber(1);
		  } else answer = new UnprintableMultiplication(xop, yop);
		 solution.add(answer);
        }
}

    /**
     * Composes section with given name
     * "varname", "question", "shortanswer", "solution", "" sections is recognised
     * 
     * @param name	section name
     **/
    public String getSection(String name) {
	if(name.equals("question")) 
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return "\\begin{align*}" +
			question + " &= " +
			MathsUtils.composeSolution(solution, "\\\\ &= ") + 
			"\\\\ &="+solution.lastElement().toString()+	
			"\\end{align*}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	return super.getSection(name);
    }
}
