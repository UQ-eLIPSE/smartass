/* @(#) SimultaneousEquationsMatrix2X2Module.java
 *
 * This file is part of SmartAss and describes class SimultaneousEquationsMatrix2X2Module.
 * Solve two linear simultaneous equations using square matrix of order 2.
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
* Class SimultaneousEquationsMatrix2X2Module generates the solution to the questions:  
* Solve two linear simultaneous equations using square matrix of order 2.   
*
* @version 1.0 15.08.2007
*/
public class SimultaneousEquationsMatrix2X2Module extends SimultaneousEquationsModule{
	
 private final int MAX_ELEMENT=10;
 private LinearEqn equation1, equation2;
 
 char numberOfSolutions; //getSection returns "o"-one, "n"-no solutins, "i"-infinite number of solutions
 int x, y; //solution to the equations	
 String solution;
 String check="Not defined";
 
 private final int DEC_SCALE=0;
 private final RoundingMode DEC_ROUNDING=RoundingMode.HALF_UP; 
/**
* Constructor SimultaneousEquationsMatrix2X2Module, generates the questions, solution, shortanswer
* 
*/
 public SimultaneousEquationsMatrix2X2Module(Engine engine) 
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
        	return "\\ensuremath{"+equation1.toString()+" }";
        if (name.equals("equation2"))
        	return "\\ensuremath{"+equation2.toString()+" }";	
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
 * Method generates coefficient for two linear equations of type:
 * ax + by = -c
 * 
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate() {
 			   
 				boolean v1x=true; //(RandomChoice.randSign()==1);
 				int[][][] initCoefs=new int [2][2][6];
 				LinearEqn [] eqns= new LinearEqn[2];
 				Variable var1, var2; 

 				if (v1x) {
 					var1=new Variable("x");
 					var2=new Variable("y");
 				} else {
 					var1=new Variable("y");
 					var2=new Variable("x");
 				}	
 					
 				//generating number of solutions:
 			    numberOfSolutions='o';// RandomChoice.makeChoice("[o]/4;[n]/1;[i]/1")[0].charAt(0);			
 			    	
 	    		solution=solve(var1, var2, numberOfSolutions, eqns);
 	    		 
 	    		equation1=eqns[0];
 	    		equation2=eqns[1];	
	
 				if (numberOfSolutions=='o') {
 	      		    int [] values=new int[2];
 	      		    if (v1x){
 	            	 x=((IntegerNumber)var1.getValue()).getInt();
 	            	 y=((IntegerNumber)var2.getValue()).getInt();
 	            	 values[0]=x; values[1]=y;
 	           		 } else {
 	            	 x=((IntegerNumber)var2.getValue()).getInt();
 	            	 y=((IntegerNumber)var1.getValue()).getInt();
 	            	 values[0]=y; values[1]=x;
 	            	}
 	            	check=" \\begin{alignat*}{3} \n";
 	            	
 	            	Vector <MathsOp> checkEq1= checkEquation(equation1,values);
 	            	Vector <MathsOp> checkEq2= checkEquation(equation2,values);
 	            	
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
/**
 * Method generates coefficient for two linear equations:
 *
 * Then this method also generates Latex-code for solution.
 * 
 * @return String solution in tex
 */
 

 protected String solve(Variable var1, Variable var2, char numberOfSolutions, LinearEqn[] eqns) {
 	            String solution="";
 		        MathsOp equation1, equation2;
 			   //String[] opstring={"\\frac"};
 				int kz, denom;		
 				int v1=0, v2=0; //solution to the equations	
 				Vector <MathsOp> equations=new Vector <MathsOp> ();
 	          	boolean rearrange=(RandomChoice.randSign()==1); //needed to rearrange initial equations?
 	    
 	          	//form of the equations:
 	          	//coefs[0]var1+coefs[1]var2 = coefs[2]
 	          	
 				int[] coefs1=new int[3];
 				int[] coefs2=new int[3];
 				int[][][] temp=new int[2][3][2];
 			      
 				MathsOp[][] opArray={{var1}, {var2}};	
 				Matrix xyMatrix=new Matrix(opArray);
 				Matrix eqnsMatrix, coefsMatrix;
 	            double[][] eqnsDArray=new double[2][2];
 	            double[][] coefsDArray=new double[2][1] ; 
 				MathsOp tempOp;	
 			   
 			    //forming initial equations
 				//3 non-zero terms only
 				
 				coefs1[0]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT)*RandomChoice.randSign();
                if (coefs1[0]==0) 			
                	coefs1[1]=RandomChoice.randInt(2,MAX_ELEMENT)*RandomChoice.randSign();
                else if (Math.abs(coefs1[0])==1)
                	coefs1[1]=RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
                else 
                	coefs1[1]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT);		
           
           
                for (int ib=0; ib<3; ib++) { //bias
                //generating second equation:
                kz= (coefs1[0]*coefs1[1]==0)? 1: 0; //making sure there is at most one zero coefficient
                coefs2[0]=RandomChoice.randInt(kz, MAX_ELEMENT)*RandomChoice.randSign();
                if (coefs2[0]==0) 
                	kz=2;
                else if (Math.abs(coefs2[0])==1)
                	kz=1;	
 				coefs2[1]=RandomChoice.randInt(kz, MAX_ELEMENT)*RandomChoice.randSign(); 

 			    switch (numberOfSolutions){
 			    	case 'o': //one solution 
 			    	    while(coefs2[1]*coefs1[0]==coefs2[0]*coefs1[1])
 			    	     coefs2[1]++;	    	  
 			    	    v1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			   			v2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);	   	
 			   			coefs1[2]=v1*coefs1[0]+v2*coefs1[1];
 			    	    coefs2[2]=v1*coefs2[0]+v2*coefs2[1];	
 			    		break;
 			    	case 'i': //infinite nomber of solutions   	   
 			    	    //coefs2[1]=coefs2[0]*coefs1[1]/coefs1[0];
 			    	    //coefs2[2]=coefs2[0]*coefs1[2]/coefs1[0];
 			    		break;
 			    	case 'n': //no solution to this simultaneous equations   	  
 			    		//coefs2[1]=coefs2[0]*coefs1[1]/coefs1[0];	
 			    		//coefs2[2]=coefs2[0]*coefs1[2]/coefs1[0]+RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 			    		break;	
 			    }
 			    
 			    if ((Math.abs(coefs2[0])!=Math.abs(coefs1[0])) && (Math.abs(coefs2[1])!=Math.abs(coefs1[1])) && 
 			    	(Math.abs(coefs1[0])!=Math.abs(coefs1[1])))
 			    	ib=3;
                }
    		   var1.setValue(new IntegerNumber(v1)); var2.setValue(new IntegerNumber(v2));  
               Variable[] vv=new Variable[2];
 			   vv[0]=var1; vv[1]=var2;       
        	   //for (int iss=0; iss<3; iss++){
 			   temp[0][0][0]=coefs1[0]; temp[0][0][1]=coefs1[1]; temp[1][0][0]=coefs1[2];	
 			   temp[0][2][1]=1;	temp[1][2][1]=1;	    
 			   temp[0][1][1]=1; temp[1][1][0]=-1;
 			   eqns[0]=new LinearEqn(temp[0],temp[1], vv);
 
 
 
 			   temp[0][0][0]=coefs2[0]; temp[0][0][1]=coefs2[1]; temp[1][0][0]=coefs2[2];			    
 			   if (rearrange)
 			   		{temp[0][2][0]=1; temp[0][2][1]=0;}
               eqns[1]=new LinearEqn(temp[0],temp[1], vv);
                  
             
               	equation1=new Equality(new Addition(new Multiplication(new IntegerNumber(coefs1[0]), var1),
               		new Multiplication(new IntegerNumber(coefs1[1]), var2)), 
               			new IntegerNumber(coefs1[2]));
               	equation2=new Equality(new Addition(new Multiplication(new IntegerNumber(coefs2[0]), var1),
               		new Multiplication(new IntegerNumber(coefs2[1]), var2)), 
               			new IntegerNumber(coefs2[2]));	
               	if (rearrange && coefs1[0]*coefs1[0]*coefs2[0]*coefs2[1]==0)
                  solution="First we rearrange the equations so that the order of the variables match and all coefficients that equal 0 are clearly identified:";
                else if (rearrange)
                  solution="First we rearrange the equations so that the order of the variables match: ";
                else if(coefs1[0]*coefs1[0]*coefs2[0]*coefs2[1]==0)
                  solution="First we rewrite the equations with all coefficients that equal 0 clearly identified: "; 
                else
                  solution="First we rewrite the equations so that the coefficients are clearly identified:";				
 			    solution+="\n\\begin{align*}"+ equation1.toString()+" \\\\" + equation2.toString()+
 			    	 " \\end{align*} \n"+
 			    "In matrix notation these equations become \\\\[3mm] \n";	    
 //STEP 0:	    
               eqnsDArray[0][0]=coefs1[0]; eqnsDArray[0][1]=coefs1[1];
               eqnsDArray[1][0]=coefs2[0]; eqnsDArray[1][1]=coefs2[1];
               coefsDArray[0][0]=coefs1[2]; coefsDArray[1][0]=coefs2[2];      
 			   eqnsMatrix=MatrixAlgebra.makeDecimalMatrix(eqnsDArray,DEC_SCALE, DEC_ROUNDING, false);
 			   coefsMatrix=MatrixAlgebra.makeDecimalMatrix(coefsDArray,DEC_SCALE, DEC_ROUNDING, false);	
 			   solution+="\\ensuremath{"+(new Equality
 			   	(new Multiplication(eqnsMatrix,xyMatrix),coefsMatrix)).toString()+"}, \\quad so  \n";
 //STEP 1:			   		
 			   	 
    solution+="\\begin{align*}"+
 			   	xyMatrix+" &= "+(new Multiplication(new Power(eqnsMatrix, new IntegerNumber(-1)),coefsMatrix)).toString()+" \\\\[2mm] \n";
 //STEP 2:
 			   eqnsDArray[0][0]=coefs2[1]; eqnsDArray[0][1]=-coefs1[1];
               eqnsDArray[1][0]=-coefs2[0]; eqnsDArray[1][1]=coefs1[0];   
 			   eqnsMatrix=MatrixAlgebra.makeDecimalMatrix(eqnsDArray,DEC_SCALE, DEC_ROUNDING, false);
 			   solution+=" &="+(new Multiplication(new Multiplication(
 			   	new FractionOp(new IntegerNumber(1), new Subtraction(
 			   		new Multiplication(new IntegerNumber(coefs1[0]), new IntegerNumber(coefs2[1])),
 			   		new Multiplication(new IntegerNumber(coefs1[1]), new IntegerNumber(coefs2[0])))),
 			   	eqnsMatrix), coefsMatrix)).toString()+" \\\\[2mm] \n";
 //STEP 3:      
			   coefsDArray=MatrixAlgebra.multiplyTwoDecimalMatrices(eqnsDArray, coefsDArray, coefsMatrix, 
 																				DEC_SCALE, DEC_ROUNDING,false);
 			   tempOp=new IntegerNumber(coefs1[0]*coefs2[1]-coefs1[1]*coefs2[0]);
			   solution+=" &="+(new Multiplication(new FractionOp(new IntegerNumber(1), tempOp),
			        coefsMatrix)).toString()+" \\\\[2mm] \n"+
 //STEP 4:		
 			   " &="+(new Multiplication(new FractionOp(new IntegerNumber(1), tempOp),	        	
			        coefsMatrix=MatrixAlgebra.makeDecimalMatrix(coefsDArray,DEC_SCALE, DEC_ROUNDING, false))).toString()+" \\\\[2mm] \n";			   
 //STEP 5:	
 			   opArray[0][0]=new Division(coefsMatrix.getElement(0,0),tempOp);//new FractionOp(coefsMatrix.getElement(0,0),tempOp); opArray[0][0].setLocalTex(opstring); 
			   opArray[1][0]=new Division(coefsMatrix.getElement(1,0),tempOp);//new FractionOp(coefsMatrix.getElement(1,0),tempOp); opArray[1][0].setLocalTex(opstring); 	    
               coefsMatrix=new Matrix(opArray); 			   
			   solution+=" &="+coefsMatrix	+" \\\\[2mm] \n";
 //STEP 6:			   	 
			   opArray[0][0]=new IntegerNumber(v1);
			   opArray[1][0]=new IntegerNumber(v2);
			   coefsMatrix=new Matrix(opArray);
			   solution+=" &="+coefsMatrix+"\\end{align*} \n";
  
 	return solution;	
 
	}//solve
         
 
 
 } 