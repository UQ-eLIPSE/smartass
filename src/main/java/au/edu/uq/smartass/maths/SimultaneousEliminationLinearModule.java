/* @(#) SimultaneousEliminationLinearModule.java
 *
 * This file is part of SmartAss and describes class SimultaneousEliminationLinearModule.
 * Solve two linear simultaneous equations using elimination. 
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
import java.lang.reflect.*;
/**
* Class SimultaneousEliminationLinearModule generates the solution to the questions:  
* Solve two linear simultaneous equations using elimination.   
*
* @version 1.0 15.05.2007
*/
public class SimultaneousEliminationLinearModule extends SimultaneousEliminationModule{

 private final int MAX_ELEMENT=10;
 private LinearEqn[] equations=new LinearEqn[2];
 char numberOfSolutions; //getSection returns "o"-one, "n"-no solutins, "i"-infinite number of solutions
 int x, y; //solution to the equations	
 String solution;
 String check="Not defined";


/**
* Constructor SimultaneousEliminationLinearModule, just envoking super(engine).
* 
*/
 public SimultaneousEliminationLinearModule(Engine engine) 
	{
				super(engine);	
				generate();
								
	} //constructor
	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("equation1"))
        	return "\\ensuremath{"+equations[0].toString()+" }";
        if (name.equals("equation2"))
        	return "\\ensuremath{"+equations[1].toString()+" }";	
 		if(name.equals("solution")) 
 		 return solution;	
 	
 		
 		if (name.equals("x")) {
 			if (numberOfSolutions=='o')
 				return Integer.toString(x);
 				else return "Undefined";
 		}
 		if (name.equals("y")) {
 			if (numberOfSolutions=='o')
 				return Integer.toString(y);
 				else return "Undefined";
 		}
	    if (name.equals("numberofsolutions"))
	    	return Character.toString(numberOfSolutions);
	    if (name.equals("check"))
	    	return check;		    
		return super.getSection(name);
	}
	
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate() {
 			
 				int v1x=RandomChoice.randInt(0,1);
 				
 				Variable[] vars=new Variable[2]; 
 				int [] vs=new int[2];
 				x=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				y=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 
 
 				
 				vars[v1x]=new Variable("x");
 				vars[(v1x+1)%2]=new Variable("y");
 				vs[v1x]=x;
 				vs[(v1x+1)%2]=y;
 			
 				//generating number of solutions:
 			    numberOfSolutions=RandomChoice.makeChoice("[o]/4;[n]/1;[i]/1")[0].charAt(0);			
 			    	
 	    		solution="First we number the equations for convenience:  \n "+solve(vars, numberOfSolutions, equations, vs);
 	 
 				if (numberOfSolutions=='o') {
 	      		    
 	            	
 	            	check=" \\begin{alignat*}{3} \n";
 	            	
 	            	Vector <MathsOp> checkEq1= checkEquation(equations[0],vs);
 	            	Vector <MathsOp> checkEq2= checkEquation(equations[1],vs);
 	            	
 	                check+="(1)\\hspace{2mm}  "+checkEq1.get(0).toString().replace(MathsOp.EQUALITY_SIGN,"&=")+
 	                " \\hspace{30 mm} & (2)\\hspace{2mm}  "+checkEq2.get(0).toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	                int mm=Math.max(checkEq1.size(), checkEq2.size());
 	                
 	            	for (int i=1; i<mm; i++) {
 	            		check+="\\\\ \n ";
 	            		if (checkEq1.size()>i)
 	              		   check+=checkEq1.get(i).toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	              		else check+=" & ";   
 	              		check+=" & ";
 	              		if (checkEq2.size()>i)
 	              			check+=checkEq2.get(i).toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	            	}		   
 	            	
 	            	check+="\\end{alignat*}";	
 	            		
 	            }
 	        
 	            	 
         } //generate

 
 } 