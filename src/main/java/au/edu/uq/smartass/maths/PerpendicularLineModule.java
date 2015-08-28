/* @(#) PerpendicularLineModule.java
 *
 * This file is part of SmartAss and describes class PerpendicularLineModule.
 * Find the equation of the line perpendicular to ax+by+c=0 and passing through the point (x1,y1).
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
* Class PerpendicularLineModule describes the question:  
* Find the equation of the line perpendicular to the ax+by=c.
* All generated numbers are integers.   
*
* @version 1.0 25.05.2007
*/
public class PerpendicularLineModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 int x1,y1;
 MathsOp original, equation; 
 String solution;
 
/**
* Constructor PerpendicularLineModule initialises the question.
* 
*/
 public PerpendicularLineModule(Engine engine) 
	{
				super(engine);	
				String[] v={"nothing"};
				this.generate(v);				
	} //constructor
	
/**
* Constructor PerpendicularLineModule initialises the question.
* 
* @param     String[] params  if params[0]=="vertical" the module will generate 
* the solution for the vertical line (initial line would be horizontal).
* if params[0]=="horizontal" the module will generate 
* the solution for the horizontal line (initial line would be vertical).
*/
 public PerpendicularLineModule(Engine engine, String[] params) 
	{
				super(engine, params);	
				this.generate(params);
	} //constructo	

/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 									 	
 		if(name.equals("x1")) 
			return Integer.toString(x1);											 												
 		if(name.equals("y1")) 		
			return Integer.toString(y1);		
 		if(name.equals("originalequation")) {		
 			return "\\ensuremath{ "+original.toString()+" }";													
		} 	
 		if(name.equals("solution")) {	
			return solution;							
 		} 	
	    if (name.equals("equation")){
 			return "\\ensuremath{ "+equation.toString()+" }";
 		}	
	    
		return super.getSection(name);
	}
	
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept
 * and coordinates for the point (x1,y1).
 *
 * Then this method also generates Latex-code for solution.
* @param      vertical  if vertical[0]=="vertical" the module will generate 
* the solution for the vertical line (initial line would be horizontal). 
 */
 

 protected void generate(String[] vertical) {
 	          	Vector <MathsOp> workings =new Vector <MathsOp> ();
 				int[] coefs=new int[6];
 				int[] places=new int[3];
 				int  hcf1, num, denom, m, c;
 				MathsOp [] solSteps=new MathsOp[6];  
 				MathsOp tempOp, mop, cop;	
 			    Variable y=new Variable("y");
 			    Variable x=new Variable("x");
 			    boolean simple=false;
 			    if  ( (vertical.length>0) && vertical[0].equals("horizontal") ){
 			    	coefs[0]=0;
 			    	coefs[3]=0;
 			    if (RandomChoice.randSign()==-1){
 	            	coefs[1]=RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 	            	coefs[4]=0;
 	            } else {
 	            	coefs[4]=RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 	            	coefs[1]=0;
 	            }
 	            if (RandomChoice.randSign()==-1){
 	            	coefs[2]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT);
 	            	coefs[5]=0;
 	            } else {
 	            	coefs[5]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT);
 	            	coefs[2]=0;
 	            }
 	            solSteps[0]=MathsUtils.multiplyVarToConst(coefs[0],y);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(coefs[1],x);
 	            solSteps[2]=new IntegerNumber(coefs[2]);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(coefs[3],y);
 	            solSteps[4]=MathsUtils.multiplyVarToConst(coefs[4],x);
 	            solSteps[5]=new IntegerNumber(coefs[5]);
 	            
 	            places=RandomChoice.randPerm(3);
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[0]], solSteps[places[1]]),
 	            	       	solSteps[places[2]]);
 	            places=RandomChoice.randPerm(3);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[0]], solSteps[3+places[1]]),
 	            	       	solSteps[3+places[2]]));
 	            workings.add(tempOp);
 	            x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                original=workings.get(0);
                equation=new Equality(y,new IntegerNumber(y1)); 	
                solution="The original line has an infinite gradient; it is vertical and parallel to the \\ensuremath{y}-axis.  "+
                	"Therefore the line perpendicular to it will be horizontal with equation of the form \\ensuremath{y=c}, "+
                		 "where c is a constant.\\\\ \n "+
                "The point \\ensuremath{("+x1+","+y1+")} lies on the new line, so the equation of the "+
                		"new line is \\hspace{2mm} \\ensuremath{"+equation.toString()+"}.";             	
 			     return;	
 			    } 
 			    
 				//forming coef0y+coef1x+coef2=coef3y+coef4x+coef5;
 				if (RandomChoice.randSign()==-1) {
 	            	coefs[3]=RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 	            	coefs[0]=0;
 	            } else {
 	            	coefs[0]=RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 	            	coefs[3]=0;
 	            }
 	           
 	            if ( (vertical.length>0) && vertical[0].equals("vertical") ) 	        
 	               m=0;
 	             else
 	            	m=RandomChoice.randInt(-MAX_ELEMENT/2, MAX_ELEMENT/2);			
 	            if (RandomChoice.randSign()==-1){
 	            	coefs[1]=-(coefs[0]-coefs[3])*m;
 	            	coefs[4]=0;
 	            } else {
 	            	coefs[4]=(coefs[0]-coefs[3])*m;
 	            	coefs[1]=0;
 	            }
 	            
 	            if (RandomChoice.randSign()==1){
 	            	coefs[2]=(coefs[0]-coefs[3])*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            	coefs[5]=0;
 	            } else {
 	            	coefs[5]=(coefs[0]-coefs[3])*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            	coefs[2]=0;
 	            }
 				   	            
 	            solSteps[0]=MathsUtils.multiplyVarToConst(coefs[0],y);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(coefs[1],x);
 	            solSteps[2]=new IntegerNumber(coefs[2]);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(coefs[3],y);
 	            solSteps[4]=MathsUtils.multiplyVarToConst(coefs[4],x);
 	            solSteps[5]=new IntegerNumber(coefs[5]);
 	            
 	            places=RandomChoice.randPerm(3);
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[0]], solSteps[places[1]]),
 	            	       	solSteps[places[2]]);
 	            places=RandomChoice.randPerm(3);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[0]], solSteps[3+places[1]]),
 	            	       	solSteps[3+places[2]]));
 	            	       	
 	          
 	            workings.add(tempOp);

//REWRITING ORIGINAL EQUATION 	            
 	            // do we need to take terms from one part of equation to another? 
 	            
 	            if ((coefs[3]!=0) || (coefs[1]!=0) || (coefs[2]!=0) ) {
                    solSteps[3]=MathsUtils.multiplyVarToConst(-coefs[3], y);
                    solSteps[1]=MathsUtils.multiplyVarToConst(-coefs[1], x);
                    solSteps[2]=new IntegerNumber(-coefs[2]);	            
 	            	tempOp=new Equality(MathsUtils.addTwoTermsNoZeros(solSteps[0], solSteps[3]),
 	            	 MathsUtils.addTwoTermsNoZeros(
 	            	 	 MathsUtils.addTwoTermsNoZeros(MathsUtils.addTwoTermsNoZeros(solSteps[4], solSteps[1]), solSteps[5]), solSteps[2]));      	
 	            	 	 	
 	            	workings.add(tempOp);
 	            	
 	            // 3 coefficients are remaining, we are going to write equation in the form: newcoef0y=newcoef4x+newcoef5;
 	           		if (((coefs[0]*coefs[3])==0) && ((coefs[1]*coefs[4])==0) && ((coefs[2]*coefs[5])==0) )
 	             		simple=true; 
 	             	
 	            	coefs[0]-=coefs[3];
 	            	coefs[4]-=coefs[1];
 	            	coefs[5]-=coefs[2];
 	            	solSteps[0]=MathsUtils.multiplyVarToConst(coefs[0],y);
 	            	solSteps[1]=MathsUtils.multiplyVarToConst(coefs[4], x);
 	            	solSteps[2]=new IntegerNumber(coefs[5]);
 	            	
 	            	tempOp=new Equality(solSteps[0], MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));
 	            	if (!simple)  
 	            		workings.add(tempOp);
 	            		
 	            }  	
 	            	            
 	            if (coefs[0]==1)
 	            {
 	                mop=new IntegerNumber(coefs[4]);
 	                cop=new IntegerNumber(coefs[5]);
 	                
 	            } else 
 	            if (coefs[0]==-1){
 	              
 	            
 	            	solSteps[1]=MathsUtils.multiplyVarToConst(-coefs[4], x);
 	            	solSteps[2]=new IntegerNumber(-coefs[5]);
 	            	tempOp=new Equality(y, MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));
 	            	
 	            	workings.add(tempOp);	
 	            	
 	            	mop=new IntegerNumber(-coefs[4]);
 	            	cop=new IntegerNumber(-coefs[5]);	
 	            } else {
 	            	if ((coefs[4]%coefs[0])==0){
 	            		coefs[4]/=coefs[0];
 	            		mop=new IntegerNumber(coefs[4]);
 	            		solSteps[1]=MathsUtils.multiplyVarToConst(coefs[4],x);
 	            	} else {    
							hcf1=HCFModule.hcf(coefs[4],coefs[0]);			
	  				  	    num=coefs[4]/hcf1;
	  				  	    denom=coefs[0]/hcf1;
	  				  	    mop=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	mop=new UnaryMinus(mop);  	
	  				  	    solSteps[1]=new UnprintableMultiplication(mop,x);		
 	            	}
 	            	if ((coefs[5]%coefs[0])==0){
 	            		coefs[5]/=coefs[0];
 	            		cop=new IntegerNumber(coefs[5]);
 	            	} else {    
							hcf1=HCFModule.hcf(coefs[5],coefs[0]);			
	  				  	    num=coefs[5]/hcf1;
	  				  	    denom=coefs[0]/hcf1;
	  				  	    cop=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	cop=new UnaryMinus(cop);  	
	  				  	    	
 	            	}
 	            
 	            	solSteps[2]=cop;
 	            	tempOp=new Equality(y, MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));
 	            	workings.add(tempOp);
 	            }
 	            solution="To find the equation of the new line, we first need the gradient of the original line. Now,  \n";
 	            solution+="\\begin{align*}"+workings.get(0).toString().replace("=","&=");
 				if (workings.size()>1) solution+=", \\mbox{  so} \\\\ \n";	
				for (int i=1; i<workings.size()-1; i++)
					solution+=workings.get(i).toString().replace("=","&=")+"\\\\";
				solution+=workings.get(workings.size()-1).toString().replace("=","&=");	
				solution+="\\end{align*}"; //closing align		
                solution+="Hence the gradient of the original line is \\ensuremath{"+(new Equality(new Variable("m_{0}"),
                new IntegerNumber(m))).toString() +"}. \\\\ \n";				
 				
                original=workings.get(0);
                c=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                if (m==0){ //original line is horizontal
                   x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                   y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
                   equation=new Equality(x,new IntegerNumber(x1)); 
                   solution+="The original line is horizontal (its gradient is equal to 0), so the new line is vertical and has "+
                	" an equation of the form \\ensuremath{x=c}. The point \\ensuremath{("+x1+","+y1+")} lies on the new line, so the equation of the "+
                		"new line is \\hspace{2mm} \\ensuremath{"+equation.toString()+"}."; 
                 
                   return;
                } 
         //original line is not horizontal
        x1=m*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
        y1=-x1/m+c;
        
        if (Math.abs(m)==1)	{
           	mop=new IntegerNumber(-m);
            cop=MathsUtils.multiplyVarToConst(-m,x);
        }
            else {
                   	if (m<0) mop=new FractionOp(new IntegerNumber(1), new IntegerNumber(Math.abs(m)));
                   	else mop=new UnaryMinus(new FractionOp(new IntegerNumber(1), new IntegerNumber(Math.abs(m))));
                   	cop=new UnprintableMultiplication(mop,x);
                   }	
       solution+="The new line is perpendicular to the original line, so the new line has gradient \\ensuremath{"+
                   	(new Equality(new Variable("m"),new UnaryMinus(new FractionOp(new IntegerNumber(1),new Variable("m_{0}"))))).toString()+
                   	"\\mbox{. Hence }"+(new Equality(new Variable("m"),mop)).toString()+"}. \\\\ \n"+
                   		"Thus the equation of the line is \\hspace{2mm} \\ensuremath{"+
                   	(new Equality(y, new Addition(cop, new Variable("c")))).toString()+"} \\hspace{2mm} and we can "+
                   	"substitute the coordinates of the point \\ensuremath{(x_{1},y_{1})=("+x1+","+y1+")} into this equation to "+
                   		"get the value of \\ensuremath{c}: \\\\ \n";	 
                
 	            
//FINDING c (mop is a new gradient)
     solution+="\\ensuremath{ ";           	            	
 	if (Math.abs(m)>1){
 	 tempOp=new Equality(new IntegerNumber(y1), new Addition(new Multiplication(mop, new IntegerNumber(x1)), new Variable("c")));
 	 solution+=tempOp.toString()+"\\mbox{, so   }";
 	} 
 	 if (x1==0){
 	 	solution+=(new Equality(new IntegerNumber(y1),new Variable("c"))).toString() +"}. \\\\ \n";
 	 } else{
 	
 	 tempOp=new Equality(new IntegerNumber(y1), new Addition(new IntegerNumber(-x1/m), new Variable("c")));
 	 solution+=tempOp.toString()+"\\mbox{. Hence }";
 	 tempOp=new Equality (new Equality(new Variable("c"), new Subtraction(new IntegerNumber(y1),new IntegerNumber(-x1/m))),
 	 new IntegerNumber(c));
 	 solution+=tempOp.toString()+"}. \\\\ \n ";
 	 }
 	 
 	 equation=new Equality(new Variable("y"),MathsUtils.addTwoTermsNoZeros(cop,
 	 new IntegerNumber(c)));
 	 solution+="Hence the equation of the line is \\hspace{2mm} \\ensuremath{"+equation.toString()+"}.";       	
 
 
                                  	   		 
	}//generate
 } 