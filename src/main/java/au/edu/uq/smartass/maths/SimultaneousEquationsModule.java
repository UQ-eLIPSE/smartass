/* @(#) SimultaneousEquationsModule.java
 *
 * This file is part of SmartAss and describes class SimultaneousEquationsModule.
 * Contains methods and inner class LinearEqn for use in questions on 
 * simultaneous equations.
 * Known subclasses:
 * SimultaneousSubstitutionLinearModule, SimultaneousEliminationLinearModule
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
* Class SimultaneousEquationsModule contains methods 
* and class LinearEqn for use in quetions on 
* simultaneouse equations.  
* See it's subclasses SimultaneousSubstitutionLinearModule and
* SimultaneousEliminationLinearModule
* @version 1.0 15.05.2007
*/
public class SimultaneousEquationsModule extends MathsModule{
  
 
/**
* Constructor SimultaneousEquationsModule, just envoking super(engine).
* 
*/
 public SimultaneousEquationsModule(Engine engine) 
	{
				super(engine);				
	} //constructor
	
	 
/**
 * Method composes vector of equations  from the given equations
 * and array of values for the variables.
 * Vector ends with equation of the form int=int
 *
 * @param LinearEqn eqn   equation to check 
 *			(by substituting values of variables)  
 * @param int[] values    vector of values of variables
 *
 * @return Vector<MathsOp> vector of Equality-s (equations,
 * calculated after substituting values)
 */
	
 protected Vector<MathsOp> checkEquation(LinearEqn eqn, int[] values ){
        int[][][] cc=new int[2][][];
        int[] size= new int[2];
        int [] sums=new int[2];
        int tempSum, tmp2;
        boolean toPrint;
        Vector <MathsOp> resCheck=new Vector<MathsOp>();
 		MathsOp[] sides = new MathsOp[2];
 	
 		MathsOp [][] solSteps=new MathsOp[2][];
 				
        cc[0]=eqn.getLeft();
        cc[1]=eqn.getRight();
   
        
       	size[0]=cc[0][0].length; //number of terms on the left side
        size[1]=cc[1][0].length; //number of terms on the right side
        solSteps[0]=new MathsOp[size[0]];
 		solSteps[1]=new MathsOp[size[1]];
		
 		for (int k=0; k<2 ; k++){
 		for (int i=0; i<size[k]; i++){
 
 			if (cc[k][1][i]==-1)
 				solSteps[k][i]=new IntegerNumber(cc[k][0][i]);
 			else
 				solSteps[k][i]=constTimesExpression(cc[k][0][i],new IntegerNumber(values[cc[k][1][i]]));
 		}		
 		sides[k]=solSteps[k][cc[k][2][0]];
 	    for (int i=1; i<size[k]; i++)
 	    	sides[k]=MathsUtils.addTwoTermsNoZeros(sides[k],solSteps[k][cc[k][2][i]]);
 		}
 		
 		resCheck.add(new Equality(sides[0], sides[1]));
 		
       toPrint=false;

 forcycle1:
   for (int k=0; k<2; k++)
 	   {
 	   	tempSum=0;
 	   	for (int i=0; i<size[k]; i++){

 	   		if  (cc[k][1][i]!=-1) {
 
 	   			if ((Math.abs(cc[k][0][i])>1) || ( (cc[k][0][i]<0) && (values[cc[k][1][i]]<0)))
 	   				{ 	  
 	   				  toPrint=true; 			  	
 	   				  break	forcycle1;

 	   				}	else
 	   				tempSum+=Math.abs(cc[k][0][i]);	 
 	   			} else tempSum+=Math.abs(Math.signum(cc[k][0][i]));		
	     			
 	   	 if (tempSum>1)	{	   	 	
 			toPrint=true;
 	   	 	break forcycle1;	
 	   	 }		
	   	 	
 	   	}//for (int i=0...		
 	   } //for (int k=0 ...     		
 	  	     				     				 	   
 	   
 	   if (toPrint){		
 	   	for (int k=0; k<2; k++){
 	   		for (int i=0; i<size[k]; i++)
 			 if (cc[k][1][i]==-1)
 				solSteps[k][i]=new IntegerNumber(cc[k][0][i]);
 			else
 				solSteps[k][i]=new IntegerNumber(cc[k][0][i]*values[cc[k][1][i]]);
 				
 		sides[k]=solSteps[k][cc[k][2][0]];
 	    for (int i=1; i<size[k]; i++)
 	    	sides[k]=MathsUtils.addTwoTermsNoZeros(sides[k],solSteps[k][cc[k][2][i]]);
 	   	}
 	   	boolean tp=false;
 	   	for (int k22=0; k22<2; k22++)
 	   		for (int i22=0; i22<size[k22]; i22++)
 			   if ((cc[k22][1][i22]!=-1)&& 
 			     		(!((cc[k22][0][i22]==0)||((Math.abs(cc[k22][0][i22])==1) &&  (values[cc[k22][1][i22]]>0)))))
 			     			tp=true;
 		if (tp)	     			
 	   		resCheck.add(new Equality(sides[0], sides[1]));
 	   	toPrint=false; 	 		
 	   	for (int k=0; k<2; k++)
 	   {
 	   	tempSum=0;
 	   	for (int i=0; i<size[k]; i++) {
 	   		tmp2=(cc[k][1][i]==-1)? cc[k][0][i]: cc[k][0][i]*values[cc[k][1][i]];
 	   		tempSum+=Math.abs(Math.signum(tmp2)); 
 	   		sums[k]+=tmp2;		   			
 	   	}		
 	   	 if (tempSum>1)	
 	   	 	toPrint=true;
 	   } //for (int k=0 .
 	   	
 	   	if (toPrint) {
 	   	   resCheck.add(new Equality(new IntegerNumber(sums[0]), new IntegerNumber(sums[1])));
 	   	} //if toPrint
 	   		
 	   	
 	   } //if toPrint

 	    return resCheck; 		 
 } //checkEquation

protected MathsOp constTimesExpression(int constant, MathsOp expression){
		MathsOp resExp;
        if(Math.abs(constant)==1) {
        	if ((expression instanceof Addition) || (expression instanceof Subtraction))
        		resExp=new Brackets(expression);
        	else resExp=expression;      		
        if(constant==-1)
            return new UnaryMinus(resExp);
        else 
        	return resExp;
        } else 
        if(constant==0)
        	return new IntegerNumber(0);
        else 	        	  
            return new Multiplication(new IntegerNumber(constant), expression);
            
         }
   
	

 
 } 