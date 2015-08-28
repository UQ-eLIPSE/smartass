/* @(#)FractionDividedByFractionModule.java
 *
 * This file is part of SmartAss and describes class FractionDividedByFractionModule for 
 * the division of two fractions. 
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
import java.math.*;

/**
* Class FractionDividedByFractionModule describes the question on 
* the division of two fractions.   
*
* @version 1.0 19.01.2007
*/
public class FractionDividedByFractionModule extends MathsModule{
 private int a,b,c,d;
 private Vector <MathsOp> workings;
 
/**
* Constructor FractionDividedByFractionModule initialises the question.
* 
*/
 public FractionDividedByFractionModule(Engine engine) 
	{
				super(engine);
				this.generate("no parameters");
			
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 	int num,denom,whole;
 	FractionOp fraction;
 	String addition;
		if(name.equals("question")) {			
			return "\\ensuremath{"+workings.get(0).toString()+" }";									
 		}	
 				
 		if(name.equals("solution")) {
       
		    String solution="\\begin{align*}"+workings.get(0).toString();	
			for (int i=1; i<workings.size()-1; i++)
				solution+="&="+workings.get(i)+"\\\\[1.8mm]";;
			solution+="&="+workings.get(workings.size()-1);
			if (workings.lastElement() instanceof FractionOp){ //working out shortanswer
				fraction=(FractionOp)(workings.lastElement().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));
			
			if (addition.length()!=0)
				solution+="\\\\[1.8mm] &="+addition;
			}		
			solution+="\\end{align*}"; //closing ensuremath				
			return solution;								
 		}
 		if(name.equals("shortanswer")) {
 			String answer="\\ensuremath{";
 				addition="";
 			if (workings.lastElement() instanceof FractionOp){ //working out shortanswer
				fraction=(FractionOp)(workings.lastElement().clone());							
				num=((IntegerNumber)fraction.getOp(0)).getInt();
				denom=((IntegerNumber)fraction.getOp(1)).getInt();				
						
				if((num*denom)<0) { //minus  - in front
			    
					addition="-";
					num=Math.abs(num);
					denom=Math.abs(denom);
					fraction.setOp(0,new IntegerNumber(num));
					fraction.setOp(1,new IntegerNumber(denom));			
				}
				if (((float)num/denom)>=1){ //generating mixed number if fraction is improper
					whole=num/denom;
					addition+=whole;
					if( (num%denom)!=0) {											
						num=num-whole*denom;
						fraction.setOp(0, new IntegerNumber(num));	
						addition+=fraction.toString();			
							
					} 
				} else if ( addition.startsWith("-")) addition+=fraction.toString();
			
			}
			if (addition.length()!=0)
				answer+=addition;
			else answer+=workings.lastElement().toString();
			answer+="}"; //closing ensuremath	
			return answer;								
 		}		
						
		return super.getSection(name);
	}
	

//Generates initial numerators and denominators
 public void generate(String params) {
 	            a=RandomChoice.randInt(-15,15);
 	            b=Integer.valueOf(RandomChoice.makeChoice("[-20..0)/2;(0..20]/8")[0]);
 	            c=Integer.valueOf(RandomChoice.makeChoice("[-20..0)/2;(0..20]/8")[0]);;
 	            d=Integer.valueOf(RandomChoice.makeChoice("[-15..0)/2;(0..15]/8")[0]);   		     
 	          
 	            workings=FractionArithmetics.divideFractions(new IntegerNumber(a),new IntegerNumber(b),
 	            		new IntegerNumber(c),new IntegerNumber(d));	
	}///generate 
} 
