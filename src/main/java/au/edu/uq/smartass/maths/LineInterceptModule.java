/* @(#) LineInterceptModule.java
 *
 * This file is part of SmartAss and describes class LineInterceptModule.
 * Find the equation of the line with the given gradient m and passing through
 * the given point.
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
* Class LineInterceptModule describes the question:  
* Find the equation y=mx+c of the line with the given gradient m and passing through
* the given point (x1,y1). 
*
* @version 1.0 15.05.2007
*/
public class LineInterceptModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 int m, c, x1, y1;
 MathsOp substituted, equation;
 boolean badInput=false;

 

/**
* Constructor LineInterceptModule initialises the question.
* 
*/
 public LineInterceptModule(Engine engine) 
	{
				super(engine);	
				this.generate(engine);				
	} //constructor
	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        if (badInput){
        	return "Incorrect format of parameters passed into LineInterceptModule!";
        }
        	
		if(name.equals("m")) {		
			return Integer.toString(m);
 		}		 	
 		if(name.equals("c")) {		
			return Integer.toString(c);													
 		}   
 		if(name.equals("x1")) {		
			return Integer.toString(x1);													
 		}   
 		if(name.equals("y1")) {		
			return Integer.toString(y1);													
 		}   	
 		if (name.equals("substituted")){
 			return "\\ensuremath{"+substituted.toString()+" }";
 		}	
 		if (name.equals("equation")){
 			return "\\ensuremath{"+equation.toString()+" }";
 		}			
		return super.getSection(name);
	}
	
/**
 * Method generates coordinates x1,y1 for the point,
 * gradient m, and evaluates y-intercept c of the equation 
 * for a straight line: y=mx+c
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate(Engine eng) {
 	          
 					
 	            x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            m=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            c=y1-x1*m;
 	            Variable y=new Variable("y");
 	            Variable x=new Variable("x");
 	            substituted=new Equality(new IntegerNumber(y1), new Addition(new Multiplication(new IntegerNumber(m), 
 	            	new IntegerNumber(x1)), new Variable("c")));
 	            equation=new Equality(y, MathsUtils.addTwoTermsNoZeros(MathsUtils.multiplyVarToConst(m,x),new IntegerNumber(c)));
 	          
	}//generate
	
/**
 * Method takes gradient m and 2 coordinates of the point in the format m, x1, y1
 * Then this method also generates Latex-code for solution.
 * @param      coords  gradient and 2 coordinates of the point 
 * in format m, x1, y1  (separated by commas)
 */	
	
 protected void generate(Engine eng, String[] coords) {
	            if (coords.length<3) {
	            	badInput=true;
	            	System.out.println("Incorrect parametres format passed into LineInterceptModule...");
	            	return;
	            }
	            try{   	    
 	  			   m=Integer.valueOf(coords[0]).intValue();
 				   x1=Integer.valueOf(coords[1]).intValue();
 				   y1=Integer.valueOf(coords[2]).intValue();	    	    
 		        
 				}
   	    		catch (NumberFormatException e){
   	    			System.out.println("Incorrect parametres format passed into LineInterceptModule...");
   	    			badInput=true;
   	    			return;
   	    		}
   	           c=y1-x1*m;
 	            Variable y=new Variable("y");
 	            Variable x=new Variable("x");
 	            substituted=new Equality(new IntegerNumber(y1), new Addition(new Multiplication(new IntegerNumber(m), 
 	            	new IntegerNumber(x1)), new Variable("c")));
 	            equation=new Equality(y, MathsUtils.addTwoTermsNoZeros(MathsUtils.multiplyVarToConst(m,x),new IntegerNumber(c)));
	           
	}//generate 	

 } 