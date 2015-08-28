/** @(#)PowerNegBaseModule.java
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

public class PowerNegBaseModule extends MathsModule {
    final int MAX_POWER = 3;
    final int MAX_VAL = 5;
    MathsOp solution, question, answer;
    int base;
    int pow;

    /**
     * @param engine    engine instance
     */
    public PowerNegBaseModule(Engine engine) {
        super(engine);
        base = RandomChoice.randInt(-MAX_VAL, -1);
        pow= RandomChoice.randInt(0,MAX_POWER);
        generate();
    }
     /**
     * @param engine    engine instance
     * @param params    firs argument - base, second argument - power
     */
   public PowerNegBaseModule(Engine engine, String[] params) {
		super(engine);
		base=Integer.parseInt(params[0]);
		pow=Integer.parseInt(params[1]);
		generate();
	}

    /**
     * Composes section with given name
     * "question", "shortanswer" and "solution" sections are recognised
     *
     * @param name  section name
     * @return the LaTeX representation of the required section
     **/
    public String getSection(String name) {
        if(name.equals("question"))
            return "\\ensuremath{"+question.toString()+"}";
        if (name.equals("solution")) 
	        return "\\ensuremath{"+solution.toString()+"}";
        if (name.equals("shortanswer"))
            return "\\ensuremath{"+answer.toString()+"}";
        return super.getSection(name);
    }
    
//generate question, solution, answer 
  private void generate(){
  	
  	MathsOp baseOp=new IntegerNumber(base);
  	MathsOp powOp=new IntegerNumber(pow);
  	question=new Power(baseOp, powOp);
  	if (pow==0){
  		answer=new IntegerNumber(1);
  		solution=new Equality(question, answer);
  	} else if (pow==1)	 
  		{
        answer=baseOp;
  		solution=new Equality(question, answer);
  	} else {
  		MathsOp tmp=baseOp;
  		int ans=base;
  		for (int i=1; i<pow; i++) {
  			tmp=new Multiplication(tmp, baseOp);
  		    ans*=base;	
  		}	
  		answer=new IntegerNumber(ans);
  		solution=new Equality(question, new Equality(tmp, answer));	
  	}
  	return;
  	
  } //generate
}
