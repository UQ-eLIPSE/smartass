/* @(#) SimultaneousSubstitutionLinearModule.java
 *
 * This file is part of SmartAss and describes class SimultaneousSubstitutionLinearModule.
 * Solve two linear simultaneous equations using substitution. 
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
* Class SimultaneousSubstitutionLinearModule generates the solution to the questions:  
* Solve two linear simultaneous equations using substitution.   
*
* @version 1.0 15.05.2007
*/
public class SimultaneousSubstitutionLinearModule extends SimultaneousEquationsModule{

 private final int MAX_ELEMENT=10;
 private LinearEqn equation1, equation2;
 char numberOfSolutions; //getSection returns "o"-one, "n"-no solutins, "i"-infinite number of solutions
 int x, y; //solution to the equations	
 String solution;
 String check="Not defined";


/**
* Constructor SimultaneousSubstitutionLinearModule, just envoking super(engine).
* 
*/
 public SimultaneousSubstitutionLinearModule(Engine engine) 
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
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate() {
 			 
 				boolean v1x=(RandomChoice.randSign()==1);
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
 			    numberOfSolutions=RandomChoice.makeChoice("[o]/4;[n]/1;[i]/1")[0].charAt(0);			
 			    	
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
 	            	
 	                check+="(1)\\hspace{2mm} & "+checkEq1.get(0).toString()+
 	                "& \\hspace{30 mm} (2)\\hspace{2mm} & "+checkEq2.get(0);
 	                int mm=Math.max(checkEq1.size(), checkEq2.size());
 	                
 	            	for (int i=1; i<mm; i++) {
 	            		check+="\\\\ \n &";
 	            		if (checkEq1.size()>i)
 	              		   check+=checkEq1.get(i).toString();
 	              		check+="&&";
 	              		if (checkEq2.size()>i)
 	              			check+=checkEq2.get(i).toString();
 	            	}		   
 	            	
 	            	check+="\\end{alignat*}";	
 	            		
 	            }
 	        
 	            	 
         } //generate
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept and coordinates for the 
 * point (x1, y1).
 *
 * Then this method also generates Latex-code for solution.
 * 
 * @return String solution in tex
 */
 

 protected String solve(Variable var1, Variable var2, char numberOfSolutions, LinearEqn[] eqns) {
 	            String solution;
 			    int order=RandomChoice.randInt(1,2); //will be extracting a variable from first or second equation
 		        MathsOp equation1, equation2;
 				int eqnNum;	
 				int v1=0, v2=0; //solution to the equations	
 				Vector <MathsOp> equations=new Vector <MathsOp> ();
           
 					
 	       //   	equations =new Vector <MathsOp> ();
 	          	
 	          	//form of the equations:
 	          	//coefs[0]var1+coefs[1]var2 + coefs[2]=0
 	          	
 				int[] coefs1=new int[3];
 				int[] coefs2=new int[3];
 				
 				// terms[0]var1+terms[1]var2+terms[2]=terms[3]var1+terms[4]var2+terms[5];
 				int[][] terms=new int[2][6];
 				int[][] places=new int[4][3];
 				MathsOp [] solSteps=new MathsOp[6]; 
 				int[] ccc=new int[8];	
 			
 				
 				MathsOp tempOp;	
 			   
 			    //forming initial equations
 				//3 non-zero terms only;
 				do {
 				coefs1[0]=RandomChoice.randInt(1, MAX_ELEMENT)*RandomChoice.randSign();
 				coefs1[1]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT)*coefs1[0];
 				coefs1[2]=RandomChoice.randInt(-MAX_ELEMENT,MAX_ELEMENT)*coefs1[0];
 				if (RandomChoice.randSign()==1) {
 			    	 terms[0][0]=coefs1[0];
 			    	 terms[0][3]=0;
 			    } else {
 			    	 terms[0][3]=-coefs1[0];
 			    	 terms[0][0]=0;
 			    }
 			    if (RandomChoice.randSign()==1) {
 			    	 terms[0][1]=coefs1[1];
 			    	 terms[0][4]=0;
 			    } else {
 			    	 terms[0][4]=-coefs1[1];
 			    	 terms[0][1]=0;
 			    }
 			    if (RandomChoice.randSign()==1) {
 			    	 terms[0][2]=coefs1[2];
 			    	 terms[0][5]=0;
 			    } else {
 			    	 terms[0][5]=-coefs1[2];
 			    	 terms[0][2]=0;
 			    }
 			    solSteps[0]=MathsUtils.multiplyVarToConst(terms[0][0],var1);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(terms[0][1],var2);
 	            solSteps[2]=new IntegerNumber(terms[0][2]);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(terms[0][3],var1);
 	            solSteps[4]=MathsUtils.multiplyVarToConst(terms[0][4],var2);
 	            solSteps[5]=new IntegerNumber(terms[0][5]);
 			    places[0]=RandomChoice.randPerm(3);
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[0][0]], solSteps[places[0][1]]),
 	            	       	solSteps[places[0][2]]);
 	            places[1]=RandomChoice.randPerm(3);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[1][0]], solSteps[3+places[1][1]]),
 	            	       	solSteps[3+places[1][2]]));
 	            equations.add(tempOp);
 	            
                for (int ib=0; ib<3; ib++) { //bias
                //generating second equation:
                coefs2[0]=RandomChoice.randInt(2, MAX_ELEMENT)*RandomChoice.randSign();
 				coefs2[1]=RandomChoice.randInt(2, MAX_ELEMENT)*RandomChoice.randSign();
 			    
 			    
 
               	 v1=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 			     v2=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);	    
 			    switch (numberOfSolutions){
 			    	case 'o': //one solution
 			    	    while((coefs2[1]==coefs2[0]*coefs1[1]/coefs1[0]) || (coefs2[1]==0))
 			    	     coefs2[1]++;	    	  
 			    		coefs2[2]=-coefs2[1]*v2+coefs2[0]*(v2*coefs1[1]/coefs1[0]+coefs1[2]/coefs1[0]);
 			    		v1=(-coefs1[1]*v2-coefs1[2])/coefs1[0];
 			    	
 			    		break;
 			    	case 'i': //infinite nomber of solutions   	   
 			    	    coefs2[1]=coefs2[0]*coefs1[1]/coefs1[0];
 			    	    coefs2[2]=coefs2[0]*coefs1[2]/coefs1[0];
 			    		break;
 			    	case 'n': //no solution to this simultaneous equations   	  
 			    		coefs2[1]=coefs2[0]*coefs1[1]/coefs1[0];	
 			    		coefs2[2]=coefs2[0]*coefs1[2]/coefs1[0]+RandomChoice.randInt(1,MAX_ELEMENT)*RandomChoice.randSign();
 			    		break;	
 			    }
 			    if ((Math.abs(coefs2[0])!=Math.abs(coefs1[0])) && (Math.abs(coefs2[1])!=Math.abs(coefs1[1])) && 
 			    	(Math.abs(coefs1[0])!=Math.abs(coefs1[1])))
 			    	ib=3;
                }
                
                }    while ((coefs1[1]==0) && (coefs2[1]==0));
 			    if (RandomChoice.randSign()==1) {
 			    	 terms[1][0]=coefs2[0];
 			    	 terms[1][3]=0;
 			    } else {
 			    	 terms[1][3]=-coefs2[0];
 			    	 terms[1][0]=0;
 			    }
 			    if (RandomChoice.randSign()==1) {
 			    	 terms[1][1]=coefs2[1];
 			    	 terms[1][4]=0;
 			    } else {
 			    	 terms[1][4]=-coefs2[1];
 			    	 terms[1][1]=0;
 			    }
 			    if (RandomChoice.randSign()==1) {
 			    	 terms[1][2]=coefs2[2];
 			    	 terms[1][5]=0;
 			    } else {
 			    	 terms[1][5]=-coefs2[2];
 			    	 terms[1][2]=0;
 			    }
 			    solSteps[0]=MathsUtils.multiplyVarToConst(terms[1][0],var1);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(terms[1][1],var2);
 	            solSteps[2]=new IntegerNumber(terms[1][2]);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(terms[1][3],var1);
 	            solSteps[4]=MathsUtils.multiplyVarToConst(terms[1][4],var2);
 	            solSteps[5]=new IntegerNumber(terms[1][5]);
 			    places[2]=RandomChoice.randPerm(3);
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[2][0]], solSteps[places[2][1]]),
 	            	       	solSteps[places[2][2]]);
 	            places[3]=RandomChoice.randPerm(3);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[3][0]], solSteps[3+places[3][1]]),
 	            	       	solSteps[3+places[3][2]]));
 	            equations.add(tempOp);
 			    if (order==1)
 			    { 
 			    	equation1=equations.get(0);
 			    	equation2=equations.get(1);
 			    }	
 			    else {
 			    	equation2=equations.get(0);
 			    	equation1=equations.get(1);
 			    }
 			    int [][][] ic= new int[2][2][6];
 			    int [][][]temp=new int[2][3][3];
 			    Variable[] vv=new Variable[2];
 			    vv[0]=var1; vv[1]=var2;
 			    for (int z=0; z<2; z++)
 			    for (int kk=0; kk<6; kk++) {
 			    	ic[z][0][kk]=terms[(z+order-1)%2][kk];
 			    	ic[z][1][kk]=places[(z+order-1)%2*2+(int)(kk/3)][kk%3];
 			    }
 			    for (int iss=0; iss<3; iss++){
 			      temp[0][0][iss]=ic[0][0][iss];
 			      temp[0][2][iss]=ic[0][1][iss];
 			      temp[1][0][iss]=ic[0][0][iss+3];
 			      temp[1][2][iss]=ic[0][1][iss+3];
 			    } 	
 			    temp[0][1][0]=0; temp[0][1][1]=1; temp[0][1][2]=-1;
 			    temp[1][1][0]=0; temp[1][1][1]=1; temp[1][1][2]=-1;
 			   
 			    
 			    eqns[0]=new LinearEqn(temp[0], temp[1], vv);
 			    
 			    for (int iss=0; iss<3; iss++){
 			      temp[0][0][iss]=ic[1][0][iss];
 			      temp[0][2][iss]=ic[1][1][iss];
 			      temp[1][0][iss]=ic[1][0][iss+3];
 			      temp[1][2][iss]=ic[1][1][iss+3];
 			    } 	
 			    eqns[1]=new LinearEqn(temp[0], temp[1], vv );
		    
 			    	
 			   //generating solution 
 			   eqnNum=3;
 			    solution="First we number the equations for convenience:  \n"+
 			    	"\\begin{align*}"+ equation1.toString()+"\\hspace{10mm}(1) \\\\" + equation2.toString()+ " \\hspace{10mm}(2) \\end{align*} \n"+
 			    "We solve these using substitution. ";
 			    boolean lh=true; 
 			    int ord2=0;
 			    if 	( (terms[0][0]!=0) && (terms[0][1]==0) && (terms[0][2]==0) ) {
 			    	if (coefs1[0]!=1)
 			    		solution+="Dividing both sides of equation ("+order+") by \\ensuremath{"+coefs1[0]+"} gives ";
 			    	else solution+=" Equation	("+order+") gives \n ";
 			    	if ((places[1][0]==2) || ((places[1][1]==2)&&(places[1][0]==0))) 
 			    		 ord2=1;
 			    } else if ( (terms[0][3]!=0) && (terms[0][4]==0) && (terms[0][5]==0)) {
 			    	lh=false;
 			    	if (coefs1[0]!=-1)
 			    		solution+="Dividing both sides of equation ("+order+") by \\ensuremath{"+(-coefs1[0])+"} gives ";
 			    	else solution+=" Equation ("+order+") gives \n";
 			    	if ((places[0][0]==2) || ((places[0][1]==2)&&(places[0][0]==0))) 
 			    		 ord2=1;	
 			    }  else {
 			    	if ((terms[0][3]!=0)&&((terms[0][4]==0)||(terms[0][5]==0)))
 			    	lh=false;
 			    	solution+="Rearranging equation ("+order+") with \\ensuremath{"+var1.toString()+"} on the "+
 			    		((lh)? "left": "right")+"-hand side gives \n";
 			    	if ((lh)&&(coefs1[0]!=1)){
 			    	solution+="\\begin{align*}";	
 			    	solSteps[0]=MathsUtils.multiplyVarToConst(coefs1[0],var1);
 	            	solSteps[1]=MathsUtils.multiplyVarToConst(-coefs1[1],var2);
 	          		solSteps[2]=new IntegerNumber(-coefs1[2]);
 			    	tempOp=new Equality(solSteps[0], MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));	
 			    	solution+=tempOp.toString()+"\\hspace{10mm}("+eqnNum+") ";
 			    	solution+=" \\end{align*} Dividing both sides of ("+eqnNum+") by \\ensuremath{"+coefs1[0]+"}, gives \n";
 			    	eqnNum++;
 			    	} else 
 			    	if ((!lh)&&(coefs1[0]!=-1)) {
 			    	solution+="\\begin{align*}";	
 			    	solSteps[0]=MathsUtils.multiplyVarToConst(-coefs1[0],var1);
 	            	solSteps[1]=MathsUtils.multiplyVarToConst(coefs1[1],var2);
 	          		solSteps[2]=new IntegerNumber(coefs1[2]);
 			    	tempOp=new Equality( MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]),solSteps[0]);	
 			    	solution+=tempOp.toString()+"\\hspace{10mm}("+eqnNum+") ";
 			    	solution+=" \\end{align*} Dividing both sides of ("+eqnNum+") by \\ensuremath{"+(-coefs1[0])+"} gives \n";
 			    	eqnNum++;	
 			    	}		 
 			       	
 			    }
 			    		
 			    solSteps[1]=MathsUtils.multiplyVarToConst(-coefs1[1]/coefs1[0],var2);
 			    solSteps[2]=new IntegerNumber(-coefs1[2]/coefs1[0]); 	
 			    //equations[2] contains an expression for var1	
 			    var1.setValue(MathsUtils.addTwoTermsNoZeros(solSteps[ord2+1], solSteps[2-ord2]));
 			    tempOp= (lh) ? new Equality(var1, var1.getValue()) : new  Equality(var1.getValue(), var1) ;	 	
 			    solution+=" \\begin{align*} "+tempOp.toString()+	"\\hspace{10mm} ("+eqnNum+") \\end{align*}";
 			    eqnNum++;
 		
//have isolated var1, now need to substitute it to another equation
				solution+="Substituting for \\ensuremath{"+var1+"} in equation ("+(order%2+1)+"), \n \\begin{align*} ";
				solSteps[0]=constTimesExpression(terms[1][0],var1.getValue());
 	            solSteps[1]=MathsUtils.multiplyVarToConst(terms[1][1],var2);
 	            solSteps[2]=new IntegerNumber(terms[1][2]);
 	            solSteps[3]=constTimesExpression(terms[1][3],var1.getValue());
 	            solSteps[4]=MathsUtils.multiplyVarToConst(terms[1][4],var2);
 	            solSteps[5]=new IntegerNumber(terms[1][5]);	
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[2][0]], solSteps[places[2][1]]),
 	            	       	solSteps[places[2][2]]);
 	            tempOp=new Equality(tempOp,
 	               MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[3][0]], solSteps[3+places[3][1]]),
 	            	       	solSteps[3+places[3][2]]));	
 	            solution+=tempOp.toString()+" \\hspace{10mm}("+eqnNum+") \\end{align*} Now ("+eqnNum+") is an equation only involving \\ensuremath{"+var2+
 	            	"} which gives: \n \\begin{align*}"; //at this point if coefs1[1]==0, we have found var1!
 	            eqnNum++;
 	           //equation is in the form: ccc[0]*var2+ccc[1]+ccc[2]*var2+ccc[3]= ccc[4]*var2+ccc[5]+ccc[6]*var2+ccc[7],
 	           //where var1=ccc[0]*var2+ccc[1] or var1=ccc[4]*var2+ccc[5]
 	            lh=true;
 	            
 	            if (coefs1[1]==0) {
 	            	ccc[0]=0;
 	            	ccc[1]=terms[1][0]*v1;
 	            	ccc[4]=0;
 	            	ccc[5]=terms[1][3]*v1;
 	            	solSteps[0]=new IntegerNumber(ccc[1]);
 	            	solSteps[3]=new IntegerNumber(ccc[5]); 			            		
 	            } else {
 	            	ccc[0]=-terms[1][0]*coefs1[1]/coefs1[0];
 	            	ccc[1]=-coefs1[2]*terms[1][0]/coefs1[0];
 	            	ccc[4]=-terms[1][3]*coefs1[1]/coefs1[0];
 	            	ccc[5]=-coefs1[2]*terms[1][3]/coefs1[0];
 	            	solSteps[0]=(ord2==0) ? MathsUtils.addTwoTermsNoZeros(MathsUtils.multiplyVarToConst(ccc[0], var2),
 	            	 				new IntegerNumber(ccc[1])) :
 	            	 				MathsUtils.addTwoTermsNoZeros(new IntegerNumber(ccc[1]),MathsUtils.multiplyVarToConst(ccc[0], var2));
 	            	 				
 	            	solSteps[3]=(ord2==0) ? MathsUtils.addTwoTermsNoZeros(MathsUtils.multiplyVarToConst(ccc[4], var2),
 	            	 				new IntegerNumber(ccc[5])) :
 	            	 				MathsUtils.addTwoTermsNoZeros(	new IntegerNumber(ccc[5]),MathsUtils.multiplyVarToConst(ccc[4], var2));	
 	            }
 	            tempOp=MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[places[2][0]], solSteps[places[2][1]]),
 	            	       	solSteps[places[2][2]]);
 	            tempOp=new Equality(tempOp,
 	               	MathsUtils.addTwoTermsNoZeros(
 	            	       	MathsUtils.addTwoTermsNoZeros(solSteps[3+places[3][0]], solSteps[3+places[3][1]]),
 	            	       	solSteps[3+places[3][2]]));	
 	            	solution+=tempOp.toString().replace(MathsOp.EQUALITY_SIGN,"&=");       	
 	            ccc[2]=terms[1][1];
 	            ccc[3]=terms[1][2];
 	            ccc[6]=terms[1][4];
 	            ccc[7]=terms[1][5];
 	            if (!( ( ((Integer.signum(ccc[0])+Integer.signum(ccc[2]))==1) && (ccc[4]==0) && (ccc[6]==0) && (ccc[1]==0) && (ccc[3]==0)
 	            	   && ((ccc[5]*ccc[7])==0)	) ||
 	            	  ( ((Integer.signum(ccc[4])+Integer.signum(ccc[6]))==1) && (ccc[0]==0) && (ccc[2]==0) && (ccc[5]==0) && (ccc[7]==0)
 	            	   && ((ccc[1]*ccc[3])==0)	))) { //answer is not found yet
 	            if ( (ccc[0]+ccc[2]-ccc[4]-ccc[6])==0){	   //numberofsolution=='i' or 'n'
 	                   tempOp=new Equality(new IntegerNumber(ccc[1]+ccc[3]), new IntegerNumber(ccc[5]+ccc[7]));
 	                   solution+=" \\\\ \n "+tempOp.toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	                   
 	            //end of story		      
 	            } else {
 	            if ((ccc[0]==0) && (ccc[2]==0))	
 	            	lh=false;
 	            ccc[0]+=ccc[2]-ccc[4]-ccc[6];
 	            ccc[1]=ccc[5]+ccc[7]-ccc[1]-ccc[3];
 	            	   
 	            //equation is in the form: ccc[0]*var2=ccc[1]
 	            tempOp= (lh)? new Equality(MathsUtils.multiplyVarToConst(ccc[0], var2), new IntegerNumber(ccc[1])): 
 	            	new Equality(new IntegerNumber(ccc[1]=-ccc[1]), MathsUtils.multiplyVarToConst(ccc[0]=-ccc[0], var2));
 	            solution+=" \\\\ \n "+tempOp.toString().replace(MathsOp.EQUALITY_SIGN,"&=");
 	            
 	            if (ccc[0]!=1) {
 	            	tempOp= ( lh ) ? new Equality(var2, new IntegerNumber(v2)) : new Equality(new IntegerNumber(v2), var2);
 	            	solution+=" \\\\ \n "+tempOp.toString().replace(MathsOp.EQUALITY_SIGN,"&=");  
 	            	
 	            } //answer is found 
 	            }  
 	            }
                solution+=" \\end{align*}";
 	            if (numberOfSolutions=='i')
 	                   	solution+="This statement is {\\bf always true}, so there is an infinite number of solutions to our simultaneous equations.";
 	            else{ 
 	            	if (numberOfSolutions=='n')
 	                   	solution+="This statement is {\\bf never true}, so there is no solution to our simultaneous equations. The lines are parallel.";		  
 	            	else { // one solution existing
 	            	if (coefs1[1]!=0){
               			 solution+="Next we substitute the value for \\ensuremath{"+var2.toString()+"} into equation ("+(eqnNum-2)+")"+
                			" to obtain the value for \\ensuremath{"+var1.toString()+"}, giving \n \\begin{align*}";
                   	 
 			   		 if ((Math.abs(coefs1[1]/coefs1[0])==1) &&  (coefs1[2]==0) )
 			   		 	tempOp=new Equality(var1, new IntegerNumber(v1));	
 			   		 else {
 			   		 	solSteps[0]=constTimesExpression(-coefs1[1]/coefs1[0],new IntegerNumber(v2));
 			   		    solSteps[1]=new IntegerNumber(-coefs1[2]/coefs1[0]); 	
 			   		    if ((v2==0)&&(coefs1[2]!=0))
 			   		    	tempOp=(ord2==0)? new Equality(new Equality(var1,new Addition(new IntegerNumber(0), solSteps[1])), new IntegerNumber(v1)):
 			   		    		new Equality(new Equality(var1, new Addition(solSteps[1],new IntegerNumber(0) )), new IntegerNumber(v1));
 			   		    else    
 			    		 tempOp=(ord2==0) ? new Equality(
 			    		 	new Equality(var1, MathsUtils.addTwoTermsNoZeros(solSteps[0], solSteps[1])), new IntegerNumber(v1)):
 			    		 	new Equality(
 			    		 	new Equality(var1, MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[0])), new IntegerNumber(v1));
 			   		 }
 			   		solution+=tempOp.toString()+"\\end{align*}";
 	            	}	
                       
 	            		
 	            	}
 	            }
 	var1.setValue(new IntegerNumber(v1)); var2.setValue(new IntegerNumber(v2));            
 	return solution;	
 
	}//solve
         
 
 
 } 