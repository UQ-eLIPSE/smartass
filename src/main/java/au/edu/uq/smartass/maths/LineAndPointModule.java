/* @(#) LineAndPointModule.java
 *
 * This file is part of SmartAss and describes class LineAndPointModule.
 * Is a given line passing through the point (x1,y1)?
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
* Class LineAndPointModule describes the question:  
* Is the given line passing through the point (x1,y1)?   
*
* @version 1.0 15.05.2007
*/
public class LineAndPointModule extends MathsModule{
 private final int MAX_ELEMENT=10;
// MathsOp m, c;
 boolean passing;
 Vector <MathsOp> workings;
 int x1, y1;
 //String shortanswer;
 //String solution;
 
/**
* Constructor LineAndPointModule initialises the question.
* 
*/
 public LineAndPointModule(Engine engine) 
	{
				super(engine);	
				this.generate(engine);				
	} //constructor
	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
        
        if(name.equals("x1"))
        	return Integer.toString(x1);
        if (name.equals("y1"))
        	return Integer.toString(y1);	
 		if(name.equals("solution")) {
 			String solution="\\begin{align*}"+workings.get(0).toString().replace("=","&=");
 			if (workings.size()>1)
 			{ solution+=", \\mbox{  so} \\\\ \n";	
			for (int i=1; i<workings.size()-1; i++)
				solution+=workings.get(i).toString().replace("=","&=")+"\\\\";
			solution+=workings.get(workings.size()-1).toString().replace("=","&=");	
 			}	
			solution+="\\end{align*}"; //closing ensuremath				
			return solution;							
 		} 	
 		if (name.equals("equation")){
 			return "\\ensuremath{ "+workings.get(0).toString()+" }";
 		}	
	    if (name.equals("yorn")){
	    	return (passing) ? "y" : "n";
	    }
		return super.getSection(name);
	}
	
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate(Engine eng) {
 	          	workings =new Vector <MathsOp> ();
 				int[] coefs=new int[6];
 				int[] places1=new int[6];
				int[] places2=new int[6];
 				int  m, c, num;
 				MathsOp [] solSteps=new MathsOp[6];  
 				MathsOp tempOp;	
 			    Variable y=new Variable("y");
 			    Variable x=new Variable("x");
 			   
 			    
 			    //forming coef0y+coef1x+coef2=coef3y+coef4x+coef5;
 				//3 non-zero terms only;
 				 m=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);	
 				 c=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);		
 				 num=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 if (num==0) { //vertical line
 				 	coefs[0]=0; coefs[3]=0;
 				 	m=RandomChoice.randInt(1, MAX_ELEMENT)*RandomChoice.randSign();
 				 	if (RandomChoice.randSign()==1){
 				 		coefs[1]=-m;
 				 		coefs[4]=0;
 				 	}	else {
 				 		coefs[4]=m;
 				 		coefs[1]=0;
 				 	}
 				 	if (RandomChoice.randSign()==1) {
 				 		coefs[2]=m*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 		coefs[5]=0;
 				 	} else {
 				 		coefs[2]=0;
 				 		coefs[5]=-m*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 	}
 				 	x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 	if (RandomChoice.randSign()==1)
 				 		x1=(coefs[5]-coefs[2])/(coefs[1]-coefs[4]);	
 				 	if (x1==(coefs[5]-coefs[2])/(coefs[1]-coefs[4]))
 				 		passing=true;
 				 	else passing=false;		
 				 		y1=RandomChoice.randInt(-MAX_ELEMENT*2, MAX_ELEMENT*2);
 				 }	else { //horizontal line 
 				 	if (m==0) {
 				 		coefs[1]=0; coefs[4]=0;
 				 		if (RandomChoice.randSign()==1){
 	                       coefs[0]=RandomChoice.randInt(1, MAX_ELEMENT)*RandomChoice.randSign();
 	                       coefs[3]=0;			 			
   				 		} else{
   				 		   coefs[3]=RandomChoice.randInt(1, MAX_ELEMENT)*RandomChoice.randSign();
   				 		   coefs[0]=0;
   				 		}
 				 		if (RandomChoice.randSign()==1){
 				 			coefs[2]=(coefs[0]-coefs[3])*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 			coefs[5]=0;
 				 		} else {
 				 			coefs[2]=(coefs[0]-coefs[3])*RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 			coefs[5]=0;
 				 		}
 				 		y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 				 		if (RandomChoice.randSign()==1)
 				 			y1=(coefs[5]-coefs[2])/(coefs[0]-coefs[3]);	
 				 		if (y1==(coefs[5]-coefs[2])/(coefs[0]-coefs[3]))
 				 		passing=true;
 				 		else passing=false;		
 				 		x1=RandomChoice.randInt(-MAX_ELEMENT*2, MAX_ELEMENT*2);	
 				 	} else{ //line is not parallel to the axis
 				 	

                        if (RandomChoice.randSign()==1){
                        	coefs[0]=num;
                        	coefs[3]=0;
                        } else {
                        	coefs[3]=num;
                        	coefs[0]=0;
                        }
                        					 	
 				 		if (RandomChoice.randSign()==1){
 	                       coefs[1]=m*num;
 	                       coefs[4]=0;			 			
   				 		} else {
   				 		   coefs[4]=m*num;
   				 		   coefs[1]=0;
   				 		}
   				 		if (RandomChoice.randSign()==1){
 	                       coefs[2]=c*num;
 	                       coefs[5]=0;			 			
   				 		} else {
   				 		   coefs[5]=-c*num;
   				 		   coefs[2]=0;
   				 		}
   				 	   x1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
   				 	   y1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
   				 	   if (RandomChoice.randSign()==1)
   				 	   	 y1=((coefs[4]-coefs[1])*x1 +coefs[5]-coefs[2])/(coefs[0]-coefs[3]);
   				 	   if (y1==((coefs[4]-coefs[1])*x1 +coefs[5]-coefs[2])/(coefs[0]-coefs[3]))
   				 	   	passing=true;
   				 	   else passing=false;	 
 				 	}
 				 }	
 		
                   	            
 	            solSteps[0]=MathsUtils.multiplyVarToConst(coefs[0],y);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(coefs[1],x);
 	            solSteps[2]=new IntegerNumber(coefs[2]);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(coefs[3],y);
 	            solSteps[4]=MathsUtils.multiplyVarToConst(coefs[4],x);
 	            solSteps[5]=new IntegerNumber(coefs[5]);
 	            
 	            places1=RandomChoice.randPerm(3);
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places1[0]], solSteps[places1[1]]),
 	            	       	solSteps[places1[2]]);
 	            places2=RandomChoice.randPerm(3);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places2[0]], solSteps[3+places2[1]]),
 	            	       	solSteps[3+places2[2]]));
 	            workings.add(tempOp);

//SUBSTITUTING x1, y1 into the equation:
               
                if (Math.abs(coefs[0])<=1)
                	solSteps[0]=new IntegerNumber(y1*coefs[0]);
                else
                	solSteps[0]=new Multiplication(new IntegerNumber(coefs[0]), new IntegerNumber(y1));	
                if (Math.abs(coefs[1])<=1)
                	solSteps[1]=new IntegerNumber(x1*coefs[1]);
                else
                	solSteps[1]=new Multiplication(new IntegerNumber(coefs[1]), new IntegerNumber(x1));	    		 	            
 			
 	            solSteps[2]=new IntegerNumber(coefs[2]);
 	            if (Math.abs(coefs[3])<=1)
                	solSteps[3]=new IntegerNumber(y1*coefs[3]);
                else
                	solSteps[3]=new Multiplication(new IntegerNumber(coefs[3]), new IntegerNumber(y1));	    	
 	            if (Math.abs(coefs[4])<=1)
                	solSteps[4]=new IntegerNumber(x1*coefs[4]);
                else
                	solSteps[4]=new Multiplication(new IntegerNumber(coefs[4]), new IntegerNumber(x1));	    	
 	           
 	            solSteps[5]=new IntegerNumber(coefs[5]);
                tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places1[0]], solSteps[places1[1]]),
 	            	       	solSteps[places1[2]]);
                tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places2[0]], solSteps[3+places2[1]]),
 	            	       	solSteps[3+places2[2]]));
 	            workings.add(tempOp);
 	            
 	            if ( (Math.abs(coefs[0]+coefs[3])>1) || (Math.abs(coefs[1]+coefs[4])>1) ) { //need to evaluate terms
 	           	 solSteps[0]=new IntegerNumber(coefs[0]*y1);
 	           	 solSteps[1]=new IntegerNumber(x1*coefs[1]);
 	           	 solSteps[2]=new IntegerNumber(coefs[2]);
 	           	 solSteps[3]=new IntegerNumber(coefs[3]*y1);
 	           	 solSteps[4]=new IntegerNumber(coefs[4]*x1);
 	           	 solSteps[5]=new IntegerNumber(coefs[5]);
 	           	 tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places1[0]], solSteps[places1[1]]),
 	            	       	solSteps[places1[2]]);
               	 tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places2[0]], solSteps[3+places2[1]]),
 	            	       	solSteps[3+places2[2]]));
 	             workings.add(tempOp);
 	            }	
 	            
 	            if ( ((Math.abs(Math.signum(x1*coefs[1])) + Math.abs(Math.signum(y1*coefs[0])) + Math.abs(Math.signum(coefs[2])))>1)  ||
 	            	((Math.abs(Math.signum(y1*coefs[3])) + Math.abs(Math.signum(x1*coefs[4])) + Math.abs(Math.signum(coefs[5])))>1)) {
 	            tempOp=new IntegerNumber(x1*coefs[1]+y1*coefs[0]+coefs[2]);
 	            tempOp=new Equality(tempOp, new IntegerNumber(y1*coefs[3]+x1*coefs[4]+coefs[5]));
 	            workings.add(tempOp);
 	            	}
 	    //////////////////////////////////////////////////////////////////////////////////////        
 	//			workings.add(new IntegerNumber(0)); passing=true;
 	//			x1=0; y1=0;
//////////////////////////////////////////////////////////////////////////////////////////////// 
 
	}//generate
	

 } 