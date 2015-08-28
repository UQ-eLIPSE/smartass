/* @(#) LineThrough2PointsModule.java
 *
 * This file is part of SmartAss and describes class LineThrough2PointsModule.
 * Find the equation of the straight line passing through  2 given points (x1, y1), (x2, y2).
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
* Class LineThrough2PointsModule describes the question:  
* Find the equation of the straight line passing through  2 points: (x1, y1) and (x2, y2).
*
* @version 1.0 10.05.2007
*/
public class LineThrough2PointsModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 int x1, x2, y1, y2;

 String shortanswer;
 String solution;
 
 boolean badInput=false;
/**
* Constructor LineThrough2PointsModule initialises the question.
* 
*/
 public LineThrough2PointsModule(Engine engine) 
	{
				super(engine);	
				this.generate(engine);				
	} //constructor
	
/**
* Constructor LineThrough2PointsModule initialises the question with parameters.
* @param      params  two points in format x1, y1, x2, y2 (coordinates separated by commas)
*/
 public LineThrough2PointsModule(Engine engine, String[] params) 
	{
				super(engine, params);	
				this.generate(engine,params);
	} //constructor	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 		if (badInput){
        	return "Incorrect parameters passed into LineThrough2PointsModule!";
        }
		if(name.equals("x1")) {		
			return Integer.toString(x1);									
 		}		 	
 		if(name.equals("x2")) {		
			return Integer.toString(x2);													
 		}
 		if(name.equals("y1")) {		
			return Integer.toString(y1);									
 		}		 	
 		if(name.equals("y2")) {		
			return Integer.toString(y2);													
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
 	          	
 	          	x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            x2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            y2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            
				solve();	         
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
 				   y1=Integer.valueOf(coords[1]).intValue();
 				   x2=Integer.valueOf(coords[2]).intValue();	    	    
 		           y2=Integer.valueOf(coords[3]).intValue();
 		           
 				}
   	    		catch (NumberFormatException e){
   	    			System.out.println("Incorrect parametres format passed into DistanceBetween2PointsModule.java... Generating random numbers.");
   	    			generate(eng);
   	    			return;
   	    		}
   	            solve();
	           
	}//generate

//composing solution, shortanswer	 	
private void solve(){
	MathsOp tempOp, m, c, fr; 
	int num, denom, hcf1;
	
	 if ((x1==x2) && (y1==y2))
 	            	x1++;
 	 solution="Let \\ensuremath{(x_{1},y_{1})=("+x1+","+y1+")} and \\ensuremath{(x_{2},y_{2})=("+x2+","+y2+")}. "+
 	            	"To find the equation of the line through \\ensuremath{(x_{1},y_{1})} and \\ensuremath{(x_{2},y_{2})} you must "+
 	            	"find the gradient \\ensuremath{m} and the \\ensuremath{y}-intercept \\ensuremath{c}. \\\\ \\vspace{1.2mm} \n";
 	            		
 	            				
 	 tempOp=
 	            	new Equality(
 	            	new Equality(new Equality(new Variable("m"), new FractionOp(new Subtraction(new Variable("y_{2}"), new Variable("y_{1}")),
 	            	new Subtraction(new Variable("x_{2}"), new Variable("x_{1}")))),
 	            	new FractionOp(new Subtraction(new IntegerNumber(y2), new IntegerNumber(y1)), 
 	            					new Subtraction(new IntegerNumber(x2), new IntegerNumber(x1)))), 
 	            	new FractionOp(new IntegerNumber(y2-y1), new IntegerNumber(x2-x1)));
 	 solution+="Then \\ensuremath{ "+tempOp.toString()+" }."; 
 	            	
 	            	
 	            	
 	            		
 	 if (x1==x2) { 
 	            	solution+="\\\\ \\vspace{1.2mm} \n Therefore this line has an infinite gradient, "+
 	            		"and is parallel to the \\ensuremath{y}-axis. It's equation is of the form \\ensuremath{x=k},"+
 	            			" where \\ensuremath{k} is a constant. \\\\ \n"+
 	            	"Hence the equation of the line is \\ensuremath{x="+x1+" }.";
 	            	shortanswer="\\ensuremath{x="+x1+" }";	
 	            	return;
 	            }
 	 num=y2-y1;
 	 denom=x2-x1;
 	 if ((num%denom)==0){
 	            	m=new IntegerNumber(num/denom);
 	            	} else {    
							hcf1=HCFModule.hcf(num,denom);			
	  				  	    num=num/hcf1;
	  				  	    denom=denom/hcf1;
	  				  	    m=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	m=new UnaryMinus(m);  	
	  				  	    	
 	            	}
 	            	
 	 solution+="  Hence \\ensuremath{m="+m.toString()+"}. \\\\ \\vspace{1.2mm} \n";
 	
 	 tempOp=new Equality(new Variable("y"), MathsUtils.addTwoTermsNoZeros(createTerm(m,new Variable("x")), new Variable("c")));  	 
 	 solution+=	"Thus the equation of the line is \\ensuremath{"+tempOp.toString()+"} and we can substitute the coordinates"+
 	 		 " of the point \\ensuremath{(x_{1},y_{1})=("+x1+","+y1+")} into this equation to get"+
 	 		 	" the value for \\ensuremath{c}. \\\\ \\vspace{1.2mm} \n Hence \\ensuremath{";
 	 if ((m instanceof IntegerNumber) && (((IntegerNumber)m).getInt()==0)) {
 	    c=new IntegerNumber(y1);
 	 	solution+=(new Equality(new IntegerNumber(y1),new Variable("c"))).toString() +"}. \\\\ \\vspace{1.2mm} \n";
 	 }	else{	
 	 tempOp=new Equality(new IntegerNumber(y1), new Addition(new Multiplication(m, new IntegerNumber(x1)), new Variable("c")));
 	 solution+=tempOp.toString()+"\\mbox{, so   }";
 	 num=(y2-y1)*x1;
 	 if (num==0){
 	 	c=new IntegerNumber(y1);
 	 	solution+=(new Equality(new IntegerNumber(y1),new Variable("c"))).toString() +"}. \\\\ \\vspace{1.2mm} \n";
 	 } else{
 	 
 	 denom=(x2-x1);
 	 if ((num%denom)==0){
 	            	fr=new IntegerNumber(num/denom);
 	            	} else {    
							hcf1=HCFModule.hcf(num,denom);			
	  				  	    num=num/hcf1;
	  				  	    denom=denom/hcf1;
	  				  	    fr=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	fr=new UnaryMinus(fr);  	
	  				  	    	
 	            	}		 	
 	 tempOp=new Equality(new IntegerNumber(y1), new Addition(fr, new Variable("c")));
 	 solution+=tempOp.toString()+"\\mbox{. Hence }";
     denom=x2-x1; 	
 	 num=y1*(x2-x1)-(y2-y1)*x1;
 	 if ((num%denom)==0){
 	            	c=new IntegerNumber(num/denom);
 	            	} else {    
							hcf1=HCFModule.hcf(num,denom);			
	  				  	    num=num/hcf1;
	  				  	    denom=denom/hcf1;
	  				  	    c=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	c=new UnaryMinus(c);  	
	  				  	    	
 	            	}		
 	 tempOp=new Equality (new Equality(new Variable("c"), new Subtraction(new IntegerNumber(y1),fr)),
 	 c);
 	 solution+=tempOp.toString()+"}. \\\\ \\vspace{1.2mm} \n ";
 	 }
 	 }
 	 tempOp=new Equality(new Variable("y"),MathsUtils.addTwoTermsNoZeros(createTerm(m, new Variable("x")),c));
 	 solution+="Hence the equation of the line is \\ensuremath{"+tempOp.toString()+"}.";
 	 shortanswer="\\ensuremath{ "+tempOp.toString()+" }";  
 	 
 	           	
} //solve	
	private MathsOp createTerm(MathsOp coef, Variable var){
		if (coef instanceof IntegerNumber)
		{
			if (((IntegerNumber)coef).getInt()==1)
				return var;
			if (((IntegerNumber)coef).getInt()==-1)
				return new UnaryMinus(var);
			if (((IntegerNumber)coef).getInt()==0)
				return new IntegerNumber(0);		
		} 
		return new UnprintableMultiplication(coef, var);
	}
 } 