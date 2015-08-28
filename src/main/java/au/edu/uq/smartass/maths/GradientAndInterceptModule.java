/* @(#) GradientAndInterceptModule.java
 *
 * This file is part of SmartAss and describes class GradientAndInterceptModule.
 * Find the gradient and y-intercept of the given line (linear equation).
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
* Class GradientAndInterceptModule describes the question:  
* Find the gradient and y-intercept of the given line (equation).   
*
* @version 1.0 15.05.2007
*/
public class GradientAndInterceptModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 MathsOp m, c;
 Vector <MathsOp> workings;
 //String shortanswer;
 //String solution;
/**
* Constructor GradientAndInterceptModule initialises the question.
* 
*/
 public GradientAndInterceptModule(Engine engine) 
	{
				super(engine);	
				this.generate(engine);				
	} //constructor
	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 
		if(name.equals("m")) {		
			return m.toString();									
 		}		 	
 		if(name.equals("c")) {		
			return c.toString();													
 		}   
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
 		if (name.equals("question")){
 			return "\\ensuremath{ "+workings.get(0).toString()+" }";
 		}	
	
		return super.getSection(name);
	}
	
/**
 * Method generates coefficient for the equation of a straight line:
 * y=mx+c, m - gradient, c - y-intercept.
 *
 * Then this method also generates Latex-code for solution.
 */
 

 protected void generate(Engine eng) {
 	          	workings =new Vector <MathsOp> ();
 				int[] coefs=new int[6];
 				int[] places=new int[3];
 				int  hcf1, num, denom;
 				MathsOp [] solSteps=new MathsOp[6];  
 				MathsOp tempOp;	
 			    Variable y=new Variable("y");
 			    Variable x=new Variable("x");
 			    boolean simple=false;
 			    
 				//forming coef0y+coef1x+coef2=coef3y+coef4x+coef5;
 					
 	            coefs[0]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            coefs[1]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            coefs[2]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            coefs[3]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);  
 	            if ( (coefs[0]-coefs[3])==0)
 	            	coefs[0]++;
 	            coefs[4]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT);
 	            coefs[5]=RandomChoice.randInt(-MAX_ELEMENT, MAX_ELEMENT); 	
 	            if ( ( (coefs[4]-coefs[1])==0) && ( (coefs[5]-coefs[2])==0) )
 	            	coefs[5]++;
 	            //tempOp= multiplyVarToConst(coefs[0], y);
 	            solSteps[0]=MathsUtils.multiplyVarToConst(coefs[0],y);
 	            //tempOp= (coefs[1]==0) ? new IntegerNumber(0) : new UnprintableMultiplication(new IntegerNumber(coefs[1]), x);
 	            solSteps[1]=MathsUtils.multiplyVarToConst(coefs[1],x);
 	            solSteps[2]=new IntegerNumber(coefs[2]);
 	            
 	            //tempOp= (coefs[3]==0) ? new IntegerNumber(0) : new UnprintableMultiplication(new IntegerNumber(coefs[3]), y);
 	            solSteps[3]=MathsUtils.multiplyVarToConst(coefs[3],y);
 	            //tempOp= (coefs[4]==0) ? new IntegerNumber(0) : new UnprintableMultiplication(new IntegerNumber(coefs[4]), x);
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
 	            	       	
 	            
 	            
 	            //solution="\\ensuremath{ "+tempOp.toString()+"}, so ";
 	          
 	            workings.add(tempOp);
 	            
 	            // do we need to take terms from one part of equation to another? 
 	            
 	            if ((coefs[3]!=0) || (coefs[1]!=0) || (coefs[2]!=0) ) {
                    solSteps[3]=MathsUtils.multiplyVarToConst(-coefs[3], y);
                    solSteps[1]=MathsUtils.multiplyVarToConst(-coefs[1], x);
                    solSteps[2]=new IntegerNumber(-coefs[2]);	            
 	            	tempOp=new Equality(MathsUtils.addTwoTermsNoZeros(solSteps[0], solSteps[3]),
 	            	 MathsUtils.addTwoTermsNoZeros(
 	            	 	 MathsUtils.addTwoTermsNoZeros(MathsUtils.addTwoTermsNoZeros(solSteps[4], solSteps[1]), solSteps[5]), solSteps[2]));      	
 	            	 	 	
 	            	//solution+="\\ensuremath{ "+tempOp.toString()+"}, ;
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
 	                m=new IntegerNumber(coefs[4]);
 	                c=new IntegerNumber(coefs[5]);
 	                
 	            } else 
 	            if (coefs[0]==-1){
 	              
 	            
 	            	solSteps[1]=MathsUtils.multiplyVarToConst(-coefs[4], x);
 	            	solSteps[2]=new IntegerNumber(-coefs[5]);
 	            	tempOp=new Equality(y, MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));
 	            	
 	            	workings.add(tempOp);	
 	            	
 	            	m=new IntegerNumber(-coefs[4]);
 	            	c=new IntegerNumber(-coefs[5]);	
 	            } else {
 	            	if ((coefs[4]%coefs[0])==0){
 	            		coefs[4]/=coefs[0];
 	            		m=new IntegerNumber(coefs[4]);
 	            		solSteps[1]=MathsUtils.multiplyVarToConst(coefs[4],x);
 	            	} else {    
							hcf1=HCFModule.hcf(coefs[4],coefs[0]);			
	  				  	    num=coefs[4]/hcf1;
	  				  	    denom=coefs[0]/hcf1;
	  				  	    m=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	m=new UnaryMinus(m);  	
	  				  	    solSteps[1]=new UnprintableMultiplication(m,x);		
 	            	}
 	            	if ((coefs[5]%coefs[0])==0){
 	            		coefs[5]/=coefs[0];
 	            		c=new IntegerNumber(coefs[5]);
 	            	} else {    
							hcf1=HCFModule.hcf(coefs[5],coefs[0]);			
	  				  	    num=coefs[5]/hcf1;
	  				  	    denom=coefs[0]/hcf1;
	  				  	    c=new FractionOp(new IntegerNumber(Math.abs(num)), new IntegerNumber(Math.abs(denom)));
	  				  	    if ((num*denom)<0)
	  				  	    	c=new UnaryMinus(c);  	
	  				  	    	
 	            	}
 	            
 	            	solSteps[2]=c;
 	            	tempOp=new Equality(y, MathsUtils.addTwoTermsNoZeros(solSteps[1], solSteps[2]));
 	            	workings.add(tempOp);
 	            }
 	            	
 	            	
 	            
 	                 
 	            
 	                	   		 
	}//generate
	

 } 