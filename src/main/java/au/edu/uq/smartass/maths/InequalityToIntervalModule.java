/* @(#)InequalityToIntervalModule.java
 *
 * This file is part of SmartAss and describes class InequalityToIntervalModule for 
 * question to write interval using inequality sign and mark it on the real line.
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

/**
 * Class InequalityToIntervalModule describes the following task:
 * "Write interval and mark it on the real line."   
 *
 * @version 1.0 15.12.2006
 * @author Andriy Kvyatkovskyy
 */
public class InequalityToIntervalModule extends MathsModule {
	private String leftNumber, rightNumber;
	private boolean leftInclusive, rightInclusive;
/**
 * Constructor InequalityToInterval generates a question. 
 * @param  engine 	
 */		 
    public InequalityToIntervalModule (Engine engine) {					
				super(engine);
				this.generate("no parameters");		
	} //constructor
	
/**
 * getSection method typesets a question (inequality) and solution 
 * (interval and number line) for InequalityToIntervalModule.
 * This module does not have a short answer (ie. shortanswer is the same with solution).
 * @param  name 	section name
 * @return a String containing Latex code for the section
 * @see    IntervalToInequalityModule
 */
	 
    public String getSection(String name) {	
		if (name.equals("question")) {
			String question="\\ensuremath{";
			if ( leftNumber.equalsIgnoreCase("-1000000") ) {			
			   //number to the	right
				if (rightInclusive) question+="x \\le"+rightNumber; else question+=" x<"+rightNumber;	
			} else 			
			if (rightNumber.equalsIgnoreCase("1000000") ) {
			     //number to the right
			   	if (leftInclusive) question+="x\\geq"+leftNumber; else question+="x>"+leftNumber;
			} else { //no infinity
				if (leftInclusive) question+=leftNumber+"\\leq x"; else question+=leftNumber+"<x";
				if (rightInclusive) question+="\\leq "+rightNumber; else question+="<"+rightNumber;
			}					
	    	return question+"}";		
		}									
		if (name.equals("solution")) {
			String solution="In interval form the answer is \\ensuremath{";			
			if (leftInclusive) solution+="["; else solution+="(";
			if (leftNumber.equalsIgnoreCase("-1000000") ) solution+="-\\infty"; else solution+=leftNumber;
			solution+=",";
			if (rightNumber.equalsIgnoreCase("1000000") ) solution+="\\infty"; else solution+=rightNumber;
			if (rightInclusive) solution+="]"; else solution+=")";										
			solution+="} and on a real line the answer is:\\\\ \n";		
            solution+=SmartGraph.drawInterval(leftNumber,leftInclusive,rightNumber,rightInclusive);					
	    	return solution;			    		
		}// solution	
		return super.getSection(name);	
	} //getSection
	
void generate (String param){
	leftNumber=RandomChoice.makeChoice("[-1000000]/2;[-11.0..11.0]/4;[-11..11]/6")[0];
	leftInclusive=false; rightInclusive=false;
	if (leftNumber.equalsIgnoreCase("-1000000")) { //negative infinity
		rightNumber=RandomChoice.makeChoice("[-5..20]/5;[-10.0..20.0]/5")[0];
		if (RandomChoice.randInt(0,1)==0)
			rightInclusive=true;		
	} else {
		if (RandomChoice.randInt(0,1)==1) leftInclusive=true;
		rightNumber=RandomChoice.makeChoice("[1000000]/2;("+leftNumber+"..15.0]/4;("+leftNumber+"..13]/6")[0];
		if ( ( ! rightNumber.equalsIgnoreCase("1000000") )&& (RandomChoice.randInt(0,1)==0) ) 		
			rightInclusive=true;							
	}
  return;
 } //generate
   
}