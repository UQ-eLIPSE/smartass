/* @(#)FractionAddFractionModule.java
 *
 * This file is part of SmartAss and describes class FractionAddFractionModule for 
 * addition of two fractions. 
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
* Class FractionAddFractionModule describes the question on 
* addition of two fractions.   
*
* @version 1.0 26.01.2007
*/
public class FractionAddFractionModule extends MathsModule{
 private int a,b,c,d;
 private Vector <MathsOp> workings;
 
/**
* Constructor FractionAddFractionModule initialises the question.
* 
*/
 public FractionAddFractionModule(Engine engine) 
	{
				super(engine);
				this.generate();
			
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 	int num,denom,whole;
 	FractionOp fraction;
 	MathsOp O;
 	String addition;
		if(name.equals("question")) {		
			return "\\ensuremath{"+workings.get(0).toString()+" }";									
 		}	 				
 		if(name.equals("solution")) {       
		    String solution="\\begin{align*}"+workings.get(0).toString();	
			for (int i=1; i<workings.size()-1; i++)
				solution+="&="+workings.get(i)+"\\\\[1.8mm]";
			solution+="&="+workings.get(workings.size()-1);	
			if (workings.lastElement() instanceof FractionOp){ //working out shortanswer
				fraction=(FractionOp)(workings.lastElement().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));			
			if (addition.length()!=0)
				solution+="\\\\[1.8mm] &="+addition;
			}	else {
			O=workings.lastElement();	
			if ((O instanceof UnaryMinus) && (((UnaryMinus)O).getOp() instanceof FractionOp)) {
			
				fraction=(FractionOp)(((UnaryMinus)O).getOp().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));
				if (addition.length()!=0) {	
					if (addition.startsWith("-")) solution+="\\\\[1.8mm] &="+addition.substring(1);
					else solution+="\\\\[1.8mm] &=-"+addition;
				}	
			}
			} //else					
			solution+="\\end{align*}"; //closing ensuremath				
			return solution;								
 		}
 		if(name.equals("shortanswer")) {
 			String answer="\\ensuremath{";
 				addition="";
 					
			if (workings.lastElement() instanceof FractionOp){ //working out shortanswer
				fraction=(FractionOp)(workings.lastElement().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));			
			if (addition.length()!=0)
				answer+=addition;
			}	else {
			O=workings.lastElement();	
			if ((O instanceof UnaryMinus) && (((UnaryMinus)O).getOp() instanceof FractionOp)) {
				
				fraction=(FractionOp)(((UnaryMinus)O).getOp().clone());
				addition=FractionArithmetics.presentableFraction((IntegerNumber)fraction.getOp(0),(IntegerNumber)fraction.getOp(1));								
				if (addition.length()!=0) {
					if (addition.startsWith("-")) answer+=addition.substring(1);
					else answer+="-"+addition;	
				}
			}
			} //else
		if (addition.length()==0)
			answer+=workings.lastElement().toString();
		answer+="}"; //closing ensuremath	
		return answer;												
 		}								
		return super.getSection(name);
	}
	
/**
 * Method generates initial random numerators and 
 * denominators for the two fractions to add 
 */
 
// Generates initial numerators and denominators
 public void generate() {
 	            a=RandomChoice.randInt(-15,15);
 	            b=Integer.valueOf(RandomChoice.makeChoice("[-20..0)/1;(0..20]/9")[0]);
 	            if ((a%b)==0) b=Math.abs(b)+1;
 	            c=RandomChoice.randInt(-15,15);
 	            d=Integer.valueOf(RandomChoice.makeChoice("[-20..0)/1;(0..20]/9")[0]);   		     
 	            if ((c%d)==0) d=Math.abs(d)+1;          
 	            workings=FractionArithmetics.addFractions(new IntegerNumber(a),new IntegerNumber(b),
 	            		new IntegerNumber(c),new IntegerNumber(d));	
 	        			
	}//generate 
} 
