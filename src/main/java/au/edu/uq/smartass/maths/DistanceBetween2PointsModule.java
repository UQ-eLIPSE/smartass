/* @(#) DistanceBetween2PointsModule.java
 *
 * This file is part of SmartAss and describes class DistanceBetween2PointsModule.
 * Find the distance between 2 points.
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
* Class DistanceBetween2PointsModule describes the question:  
* Find the distance between 2 points.   
*
* @version 1.0 10.05.2007
*/
public class DistanceBetween2PointsModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 int x1, x2, y1, y2;
 MathsOp opx1, opx2, opy1, opy2;
 String shortanswer;
 String solution;
/**
* Constructor DistanceBetween2PointsModule initializes the question.
* 
*/
 public DistanceBetween2PointsModule(Engine engine) 
	{
				super(engine);	
				this.generate(engine);				
	} //constructor
	
/**
* Constructor DistanceBetween2PointsModule initializes the question with parameters.
* @param      params  two points in format x1, y1, x2, y2 (coordinates separated by commas)
*/
 public DistanceBetween2PointsModule(Engine engine, String[] params) 
	{
				super(engine, params);	
				this.generate(engine,params);
	} //constructor	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 
		if(name.equals("x1")) {		
			return (opx1).toString();									
 		}		 	
 		if(name.equals("x2")) {		
			return (opx2).toString();													
 		}
 		if(name.equals("y1")) {		
			return (opy1).toString();									
 		}		 	
 		if(name.equals("y2")) {		
			return (opy2).toString();													
 		}
 	    if(name.equals("shortanswer")) {
 	    	return (shortanswer);
 	    }
 		if(name.equals("solution")) {
 			return solution;
 		} 	
	
		return super.getSection(name);
	}
	
/**
 * Method generates coordinates of two points, using whole numbers from
 * an interval [-MAX_ELEMENT, MAX_ELEMENT]. MAX_ELEMENT==10
 * Then this method also generates Latex-code for solution.
 */
 
// Generates sets
 protected void generate(Engine eng) {
 	            boolean[] roots= new boolean[2];
 	            MathsOp[] tempOps=new MathsOp[2];
 	            int sq;
 	            int dx, dy;
 	            if (RandomChoice.randInt(0,5)==0) {
 	       	
 	            	sq=RandomChoice.randInt(1,3);
 	            	sq*=sq;
 	             if (RandomChoice.randInt(0,1)==0){
 	             	x1=Integer.valueOf(RandomChoice.makeChoice("[2]/2;[3]/2;[5]/2;[7]/2")[0]);
 	             	x2=x1*sq;
 	             } else {
 	             	x2=Integer.valueOf(RandomChoice.makeChoice("[2]/2;[3]/2;[5]/2;[7]/2")[0]);
 	             	x1=x2*sq;
 	             }
 	             opx1=new Sqrt(new IntegerNumber(x1));
 	             opx2=new Sqrt(new IntegerNumber(x2));
 	             roots[0]=true;
 	            } else {
 	            
 	            x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            x2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            opx1=new IntegerNumber(x1);
 		        opx2=new IntegerNumber(x2);
 	            roots[0]=false;
 	            }
 	             
 	          	if (RandomChoice.randInt(0,5)==0) {
 	          	 do {
 	          	 
 	          		sq=RandomChoice.randInt(1,3);
 	            	sq*=sq;
 	             if (RandomChoice.randInt(0,6)==0){
 	             	y1=Integer.valueOf(RandomChoice.makeChoice("[2]/2;[3]/2;[5]/2;[7]/2")[0]);
 	             	y2=y1*sq;
 	             } else {
 	             	y2=Integer.valueOf(RandomChoice.makeChoice("[2]/2;[3]/2;[5]/2;[7]/2")[0]);
 	             	y1=y2*sq;
 	             }
 	          	 } while (roots[0] && (x1==y1) && (x2==y2));
 	             opy1=new Sqrt(new IntegerNumber(y1));
 	             opy2=new Sqrt(new IntegerNumber(y2));
 	             roots[1]=true;
 	            } else {
 	            do { 
 	            y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            y2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            } while ((!roots[1]) && (y1==x1) && (y2==x2)); 
 	            
 	            opy1=new IntegerNumber(y1);
 		        opy2=new IntegerNumber(y2);
 	            roots[1]=false;
 	            } 
 	            	

 	          	solution="\\ensuremath{";
				MathsOp tempOp=new Sqrt(new Addition(new Power(new Subtraction(opx1, opx2),new IntegerNumber(2)),
				new Power(new Subtraction(opy1, opy2),new IntegerNumber(2))));
              
                if (roots[0])
                	{
                	  dx=x1-2*(int)Math.round(Math.sqrt(x1*x2))+x2;     
                	  if (x1>x2)	 
                	  	tempOps[0]=new Power(new Subtraction(new Sqrt( new Multiplication(
                	  		new IntegerNumber(x1/x2), new IntegerNumber(x2))), opx2), new IntegerNumber(2));           	  	
                	  else 
                	  if (x2>x1)
                	  	tempOps[0]=new Power(new Subtraction(opx1, new Sqrt( new Multiplication(
                	  		new IntegerNumber(x2/x1), new IntegerNumber(x1)))), new IntegerNumber(2));     
                	  else 
                	  {
                	  	tempOps[0]=new Power(new IntegerNumber(0), new IntegerNumber(2));	        	
                	  	roots[0]=false;	
                	  }		
                	} else {
                	  dx=x1-x2;
                	  tempOps[0]=new Power(new IntegerNumber(dx), new IntegerNumber(2));
                	  dx*=dx;
                	}
                	if (roots[1])
                	{
                	  dy=y1-2*(int)Math.round(Math.sqrt(y1*y2))+y2; 
                	  if (y1>y2)	     
                	   tempOps[1]=new Power(new Subtraction(new Sqrt( new Multiplication(
                	  		new IntegerNumber((int)(y1/y2)), new IntegerNumber(y2))), opy2), new IntegerNumber(2));   
                	  else 
                	  if (y2>y1)
                	  	tempOps[1]=new Power(new Subtraction(opy1, new Sqrt( new Multiplication(
                	  		new IntegerNumber(y2/y1), new IntegerNumber(y1)))), new IntegerNumber(2));     
                	  else 
                	  {
                	  	tempOps[1]=new Power(new IntegerNumber(0), new IntegerNumber(2));	        	
                	  	roots[1]=false;	
                	  }					         	  	
                	} else {
                	  dy=y1-y2;
                	  tempOps[1]=new Power(new IntegerNumber(dy), new IntegerNumber(2));
                	  dy*=dy;	
                	}
                		
                
                tempOp=new Equality(tempOp, new Sqrt(new Addition(tempOps[0], tempOps[1]) ));
                solution+=tempOp.toString();
                      	 	
                if (roots[0] || roots[1]) {                  
                  	  
                 	if (roots[0])
                	{
                	 
                	  if (x1>x2)	 
                	  	tempOps[0]=new Power(new Subtraction(new UnprintableMultiplication(new 
                	  		IntegerNumber((int)Math.round(Math.sqrt(x1/x2))), opx2), opx2), new IntegerNumber(2));           	  	
                	  else 
                	  if (x2>x1)
                	  	tempOps[0]=new Power(new Subtraction(opx1, new UnprintableMultiplication(new 
                	  		IntegerNumber((int)Math.round(Math.sqrt(x2/x1))), opx1)), new IntegerNumber(2));     
                	        	
                	} 
          		
                    if (roots[1])
                	{
  
                	  if (y1>y2)	 
                	  	tempOps[1]=new Power(new Subtraction(new UnprintableMultiplication(new 
                	  		IntegerNumber((int)Math.round(Math.sqrt(y1/y2))), opy2), opy2), new IntegerNumber(2));           	  	
                	  else 
                	  if (y2>y1)
                	  	tempOps[1]=new Power(new Subtraction(opy1, new UnprintableMultiplication(new 
                	  		IntegerNumber((int)Math.round(Math.sqrt(y2/y1))), opy1)), new IntegerNumber(2));           	
                	} 
                solution+="\\\\ \n";  
                solution+=" = ";	  	
                tempOp=new Sqrt(new Addition(tempOps[0], tempOps[1]) );
                solution+=tempOp.toString();	 
               	  	
                }; 		
                solution+=" = ";

	            tempOp=new Sqrt(new Addition(new IntegerNumber(dx), new IntegerNumber(dy)) );
				dx+=dy;
				tempOp=new Equality(tempOp, new Sqrt(new IntegerNumber(dx)));
				String[] pars=new String[1];		
				pars[0]=Integer.toString(dx);
				SqrtModule root=new SqrtModule(eng, pars);
				shortanswer=root.getSection("shortanswer");
				solution+=tempOp.toString()+" }";
 	       	
 		        	
 	         	   		 
	}//generate
	
/**
 * Method takes 4 coordinates of two points in the format 
 * Then this method also generates Latex-code for solution.
 * @param      coords  to points in format x1, y1, x2, y2 (coordinates separated by commas)
 */	
 protected void generate(Engine eng, String[] coords) {
	            if (coords.length<4) {
	            	generate(eng);
	            	return;
	            }
	            try{   	    
 	  			   x1=Integer.valueOf(coords[0]).intValue();
 				   x2=Integer.valueOf(coords[1]).intValue();
 				   y1=Integer.valueOf(coords[2]).intValue();	    	    
 		           y2=Integer.valueOf(coords[3]).intValue();
 		           opx1=new IntegerNumber(x1);
 		           opx2=new IntegerNumber(x2);
 		           opy1=new IntegerNumber(y1);
 		           opy2=new IntegerNumber(y2);
 				}
   	    		catch (NumberFormatException e){
   	    			System.out.println("Incorrect parametres format passed into DistanceBetween2PointsModule.java... Generating random numbers.");
   	    			generate(eng);
   	    			return;
   	    		}
   	            solve(eng);
	           
	}//generate 	
private void solve(Engine en){
	int dx, dy;
	solution="\\ensuremath{";
	MathsOp tempOp=new Sqrt(new Addition(new Power(new Subtraction(new IntegerNumber(x1), new IntegerNumber(x2)),new IntegerNumber(2)),
		new Power(new Subtraction(new IntegerNumber(y1), new IntegerNumber(y2)),new IntegerNumber(2))));
	dx=x1-x2; dy=y1-y2;
	tempOp=new Equality(tempOp, new Sqrt(new Addition(new Power(new IntegerNumber(dx), new IntegerNumber(2)), new Power(new IntegerNumber(dy), 
		new IntegerNumber(2)))));
	dx*=dx;
	dy*=dy;
	tempOp=new Equality(tempOp, new Sqrt(new Addition(new IntegerNumber(dx), new IntegerNumber(dy))));
	dx+=dy;
	tempOp=new Equality(tempOp, new Sqrt(new IntegerNumber(dx)));
	String[] pars=new String[1];
	pars[0]=Integer.toString(dx);
	SqrtModule root=new SqrtModule(en, pars);
	shortanswer=root.getSection("shortanswer");
	solution+=tempOp.toString()+" }";
}	
	
 } 