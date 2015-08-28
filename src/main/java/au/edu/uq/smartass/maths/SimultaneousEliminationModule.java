/* @(#) SimultaneousEliminationModule.java
 *
 * This file is part of SmartAss and describes class SimultaneousEliminationModule.
 * Solve two linear simultaneous equations using elimination. Known subclasses:
 * SimultaneousEliminationLinearModule, SimultaneousEliminationNonLinearModule.
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
* Class SimultaneousEliminationModule generates the solution to the questions:  
* Solve two linear simultaneous equations using elimination.   
* @see it's subclasses SimultaneousEliminationLinearModule and
* SimultaneousEliminationNonLinearModule
* @version 1.0 15.05.2007
*/
public class SimultaneousEliminationModule extends SimultaneousEquationsModule{
 private final int MAX_ELEMENT=10;
 
 /**
* Constructor SimultaneousEliminationModule, envokes super(engine).
* 
*/
 public SimultaneousEliminationModule(Engine engine) 
	{
				super(engine);		
	} //constructo
 

/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 * 
 * @return String solution in tex, and equations are in 
 * LinearEqn[] eqns
 * 
 */
 
// v[0], v[1] - solution to the equations
 protected String solve(Variable[] vars, char numberOfSolutions, LinearEqn[] eqns, int[] vs) {
 	            String solution;
 			    int order=RandomChoice.randInt(1,2); //randomly generating first or second equation
 		      //  MathsOp equation1, equation2;
 				int eqnNum, mlt, hcf1;
 				int [] multipliers=new int[2];	
 			    LinearEqn[] tmpEqns=new LinearEqn[2];
 			    int[][][] tmpTerms;
 			    int varInd=0;
 					   	          
 	          	//form of the equations:
 	          	//coefs[0]var1+coefs[1]var2 + coefs[2]=0
 				int[][] coefs=new int[2][3];
 				
 				// terms[0][0][0]var1+terms[0][0][1]var2+terms[0][0][2]=terms[0][1][0]var1+terms[0][1][2]var2+terms[0][1][3];
 				//(fourth index - places)
 				int[][][][] terms=new int[2][2][3][3];				
 				
 				
 				MathsOp [] solSteps=new MathsOp[2];
 				 
 				MathsOp tempOp;	
 			     	
 			    //forming initial equations
 				//3 non-zero terms only;
 				coefs[0][0]=RandomChoice.randInt(2, (int)(MAX_ELEMENT/2))*RandomChoice.randSign();
 				coefs[0][1]=RandomChoice.randInt(2, MAX_ELEMENT)*RandomChoice.randSign();			
 				coefs[0][2]=-coefs[0][0]*vs[0]-coefs[0][1]*vs[1];
 				   
                //generating second equation:
     
                coefs[1][0]=RandomChoice.randInt(2, (int)(MAX_ELEMENT*1.5))*RandomChoice.randSign();
 				coefs[1][1]=RandomChoice.randInt(2, MAX_ELEMENT)*RandomChoice.randSign();
	    
 			  
 			    switch (numberOfSolutions){
 			    	case 'o': //one solution
 			    	    while((coefs[1][1]*coefs[0][0]==coefs[1][0]*coefs[0][1]) || (coefs[1][1]==0))
 			    	     coefs[1][1]++;	    	  
 			    		coefs[1][2]=-coefs[1][0]*vs[0]-coefs[1][1]*vs[1];
 			    		if (Math.abs(coefs[0][0])==Math.abs(coefs[1][0])) {
 			    			multipliers[0]=-Integer.signum(coefs[0][0]*coefs[1][0]);
 			    			multipliers[1]=1; 
 			    			varInd=0;} else 
 			    		if (Math.abs(coefs[0][1])==Math.abs(coefs[1][1])) {
 			    			multipliers[0]=-Integer.signum(coefs[0][1]*coefs[1][1]);
 			    			multipliers[1]=1;
 			    			varInd=1; } else 
 			    				{		    			
 			    			int[][] tempMults=new int[2][2];
 			    			int weight=0; //higher weight - better multiplier
 			    	
 			    			for (int i2=0; i2<2; i2++){
 			    			    hcf1=HCFModule.hcf(coefs[0][i2], coefs[1][i2]);	
 			    			    tempMults[0][i2]=Math.abs(coefs[1][i2]/hcf1);
 			    			    tempMults[1][i2]=Math.abs(coefs[0][i2]/hcf1);	
 			    			    if ((coefs[1][i2]*coefs[0][i2])>0){
 			    			     if (Math.abs(tempMults[0][i2])>Math.abs(tempMults[1][i2]))
 			    			     	tempMults[0][i2]=-tempMults[0][i2];	
 			    			     else tempMults[1][i2]=-tempMults[1][i2];
 			    			    } 		
 			    			}  
 			    				//choose the best set of multipliers	
 			   				for (int i2=0; i2<2; i2++){	
 			    			    if ((tempMults[0][i2]*tempMults[1][i2])==1) {
 			    			    
 			    			    	varInd=i2;
 			    			    	weight=10;
 			    		            break;	    	
 			    			    } else if( ((tempMults[0][i2]*tempMults[1][i2])==-1) &&
 			    			    	(weight<9)){
 			    			    	
 			    			    	varInd=i2;
 			    			    	weight=9;
 			    			    	
 			    			    } else if ( ((tempMults[0][i2]==1) || (tempMults[1][i2]==1)) &&
 			    			    	(weight<8) ){
 			    			    
 			    			    	varInd=i2;
 			    			    	weight=8;
 			    			    	
 			    			    } else if ( ((Math.abs(tempMults[0][i2])==1) || (Math.abs(tempMults[1][i2])==1)) &&
 			    			    	(weight<7)){
 			    			    
 			    			    	varInd=i2;
 			    			    	weight=7;
 			    			    	
 			    			    } else if (((Math.abs(tempMults[0][i2])+Math.abs(tempMults[1][i2]))<
 			    			    	       (Math.abs(tempMults[0][varInd])+Math.abs(tempMults[1][varInd]))) &&
 			    			    	 (weight<6) ){
 			    			    	
 			    			    	varInd=i2;
 			    			    	weight=6;
 			    			   	       	
 			    			    } else if (((Integer.signum(tempMults[0][i2])+Integer.signum(tempMults[1][i2]))>
 			    			    	       (Integer.signum(tempMults[0][varInd])+Math.abs(tempMults[1][varInd]))) &&
 			    			    	 (weight<5) ){
 			    			    
 			    			    	varInd=i2;
 			    			    	weight=5;
 			    			    }  else if (((Math.abs(tempMults[0][i2]*coefs[0][(i2+1)%2])+
 			    			    			Math.abs(tempMults[1][i2]*coefs[1][(i2+1)%2]))<
 			    			    	       (Math.abs(tempMults[0][varInd]*coefs[0][(varInd+1)%2])+
 			    			    			Math.abs(tempMults[1][varInd]*coefs[1][(varInd+1)%2])))&&
 			    			    	 (weight<4) ){
 			    			    
 			    			    	varInd=i2;
 			    			    	weight=4;
 			    			    }   		  	 		  		
 			    			    multipliers[0]=tempMults[0][varInd];
 			    			    multipliers[1]=tempMults[1][varInd];
 			    			}  	 			
 			    		 			    				
 			    		}
  		    		 			    		 			    			 			    			 			    			 			    			
 			    		break;
 			    	case 'i': //infinite nomber of solutions 
 			    	    mlt=RandomChoice.randInt(2, MAX_ELEMENT)*RandomChoice.randSign();;   
 			    	    coefs[1][0]=coefs[0][0]*mlt;
 			    	    coefs[1][1]=coefs[0][1]*mlt;
 			    	    coefs[1][2]=coefs[0][2]*mlt;
 			    	    multipliers[0]=-mlt;
 			    	    multipliers[1]=1;
 			    		break;
 			    	case 'n': //no solution to this simultaneous equations   
 			    	    mlt=RandomChoice.randInt(1, MAX_ELEMENT)*RandomChoice.randSign();;  	  
 			    		coefs[1][0]=coefs[0][0]*mlt;
 			    	    coefs[1][1]=coefs[0][1]*mlt;
 			    		coefs[1][2]=coefs[0][2]*mlt+RandomChoice.randInt(2, MAX_ELEMENT*2)*RandomChoice.randSign();
 			    		multipliers[0]=-mlt;
 			    		multipliers[1]=1;
 			    		break;	
 			    }
 			    
 			   for (int k=0; k<2; k++) { //initializing coefficients to build LinearEqns
	                
 			    	 terms[k][0][0][0]=coefs[k][0];
 			    	 terms[k][1][0][0]=0;
 			         terms[k][0][0][1]=coefs[k][1];
 			    	 terms[k][1][0][1]=0;
 			         terms[k][1][0][2]=-coefs[k][2];
 			    	 terms[k][0][0][2]=0;
 			             
 			     for (int i=0; i<2; i++){
                  terms[k][i][1][0]=0;
                  terms[k][i][1][1]=1;
                  terms[k][i][1][2]=-1;	
                  terms[k][i][2][0]=0; terms[k][i][2][1]=1; terms[k][i][2][2]=2;			
 			     }
 			   }
                	    
	           
	           	eqns[0]=new LinearEqn(terms[order-1][0], terms[order-1][1], vars);
	           	eqns[1]=new LinearEqn(terms[order%2][0], terms[order%2][1], vars);
	           	

 			   //generating solution 
 			   eqnNum=3;
 			    solution="\\begin{align*}"+ eqns[0].toString()+"\\hspace{10mm}(1) \\\\" + eqns[1].toString()+ "\\hspace{10mm}(2) \\end{align*} \n"+
 			    "It's probably easier to solve these using elimination. ";
 			        
              if ((multipliers[0]!=1) || (multipliers[1]!=1)) {
                  solution+=" Multiply equation ";
                  if (multipliers[order-1]!=1) {
                  	solution+="(1) by \\ensuremath{"+multipliers[order-1]+"} ";
                  	if (multipliers[order%2]!=1) solution+=" and equation ";
                  }	
                  if (multipliers[order%2]!=1) 
                  	solution+="(2) by \\ensuremath{"+multipliers[order%2]+"} ";
                  for (int k=0; k<2; k++) {
                  	for (int i=0; i<2; i++){
                  	terms[k][i][0][0]*=multipliers[k];
                  	terms[k][i][0][1]*=multipliers[k];
                  	terms[k][i][0][2]*=multipliers[k];
                  }
                   tmpEqns[k]=new LinearEqn(terms[k][0], terms[k][1], vars);
                  }		
                  	
                  solution+=", giving \n \\begin{align*}"+tmpEqns[order-1].toString()+" \\hspace{10mm}("+(eqnNum++)+") \\\\ \n"+
                  	tmpEqns[order%2].toString()+ "\\hspace{10mm}("+(eqnNum++)+")  \n \\end{align*}";	
                  		         	
              }
              solution+=" We add both sides of equations ("+(eqnNum-2)+") and ("+(eqnNum-1)+") , giving \\begin{align*}";
              
              tmpTerms=new int[2][3][4];
              for (int i=0; i<4; i++) {
               tmpTerms[0][2][i]=i;
               tmpTerms[1][2][i]=i;
               tmpTerms[0][1][i]=-1;
               tmpTerms[1][1][i]=-1;
              } 
               tmpTerms[0][0][0]=terms[0][0][0][0];
               tmpTerms[0][1][0]=terms[0][0][1][0];
               tmpTerms[0][0][1]=terms[1][0][0][0];
               tmpTerms[0][1][1]=terms[1][0][1][0];
               
               tmpTerms[0][0][2]=terms[0][0][0][1];
               tmpTerms[0][1][2]=terms[0][0][1][1];
               tmpTerms[0][0][3]=terms[1][0][0][1];
               tmpTerms[0][1][3]=terms[1][0][1][1];
             
               tmpTerms[1][0][0]=terms[0][1][0][2];
               tmpTerms[1][0][1]=terms[1][1][0][2];
               
               solution+=(new LinearEqn(tmpTerms[0], tmpTerms[1], vars)).toString()+" \\hspace{10mm} ("+(eqnNum++)+") \n \\end{align*}"+
              "Simplifying 	equation ("+(eqnNum-1)+") gives \n";
               if (numberOfSolutions=='i') {
               	 solution+=" \\begin{align*} 0=0 \\hspace{10mm} ("+eqnNum+") \\end{align*} \n"+
               	 	" Statement ("+eqnNum+") is {\\bf always true}, so there is an infinite number of solutions to our simultaneous equations.";
               	 return	solution;	
               } 
               if (numberOfSolutions=='n') {
               	 solution+=" \\begin{align*} "+(new Equality(new IntegerNumber(0), new IntegerNumber(-coefs[0][2]*multipliers[0]-
               	 	coefs[1][2]*multipliers[1]))).toString()+
               	 	 "\\hspace{10mm} ("+eqnNum+") \\end{align*} \n"+
               	 	" Statement ("+eqnNum+") is {\\bf never true}, so there is no solution to our simultaneous equations. The lines are parallel.";
               	 return	solution;	
               } 
          // numberOfSolutions=='o'  
     
               mlt=terms[0][0][0][(varInd+1)%2]+terms[1][0][0][(varInd+1)%2];   	
               solution+=" \\begin{align*}"+(new Equality (MathsUtils.multiplyVarToConst(mlt,vars[(varInd+1)%2]), 
               	new IntegerNumber(-coefs[0][2]*multipliers[0]-coefs[1][2]*multipliers[1]))).toString()+
               	" \\hspace{10mm} ("+(eqnNum++)+") ";
               if (mlt!=1)
               	solution+=" \\\\ \n "+(new Equality (vars[(varInd+1)%2],new IntegerNumber(vs[(varInd+1)%2]))).toString()+
               		" \\hspace{10mm} ("+(eqnNum++)+") ";
               solution+=" \\end{align*} \n "+
               	"Next we substitute the value for \\ensuremath{"+vars[(varInd+1)%2].toString()+"} into equation (1) to obtain the value for "
               		+"\\ensuremath{"+vars[varInd]+"}, giving \n \\begin{align*} ";
               solSteps[varInd]=MathsUtils.multiplyVarToConst(coefs[order-1][varInd], vars[varInd]);
               solSteps[(varInd+1)%2]=new Multiplication(new IntegerNumber(coefs[order-1][(varInd+1)%2]), new IntegerNumber(vs[(varInd+1)%2]));
              tempOp=MathsUtils.addTwoTermsNoZeros(solSteps[0], solSteps[1]); 
               solution+=tempOp.toString()+" &= "+(new IntegerNumber(-coefs[order-1][2])).toString()+"\\\\ \n"+
               	solSteps[varInd].toString()+" &= "+ (new IntegerNumber(-coefs[order-1][2]-coefs[order-1][(varInd+1)%2]*vs[(varInd+1)%2])).toString()+
               	" \\hspace{10mm} \\mbox{ so } \\\\ \n"+
               	vars[varInd].toString()+" &= "+ (new IntegerNumber(vs[varInd])).toString()+ "\\end{align*}";	
    
               return solution;
   
         } //solve

 } 