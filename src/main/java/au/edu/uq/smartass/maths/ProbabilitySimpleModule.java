/* @(#) ProbabilitySimpleModule.java
 *
 * This file is part of SmartAss and describes class ProbabilitySimpleModule.
 * A few simple questions on probabilities.
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
* Class ProbabilitySimpleModule describes the questions on 
* probabilities.   
*
* @version 1.0 26.01.2007
*/
public class ProbabilitySimpleModule extends MathsModule{
 private final int MIN_ELEMENT=1;
 private final int MAX_ELEMENT=9;
 private int[] number1Limits=new int[2];
 private int[] number2Limits=new int[2];
 private final int NUMBER_OF_QUESTIONS=9; 
 Variable v1, v2;
 private String[] questions=new String[NUMBER_OF_QUESTIONS];
 private String[] solutions=new String[NUMBER_OF_QUESTIONS];
 private String[] shortanswers=new String[NUMBER_OF_QUESTIONS]; 
 
/**
* Constructor ProbabilitySimpleModule initialises the question.
* 
*/
 public ProbabilitySimpleModule(Engine engine) 
	{
				super(engine);	
				this.generate();				
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 	String section[]=new String[2];
 	
		if(name.equals("name1")) {		
			return v1.toString();									
 		}		 	
 		if(name.equals("name2")) {		
			return v2.getName();													
 		}
 		if(name.equals("var1leftlimit")){
 			return String.valueOf(number1Limits[0]);
 		}
 		if(name.equals("var1rightlimit")){
 			return String.valueOf(number1Limits[1]);
 		}
 		if(name.equals("var2leftlimit")){
 			return String.valueOf(number2Limits[0]);
 		}
 		if(name.equals("var2rightlimit")){
 			return String.valueOf(number2Limits[1]);
 		}
 		
 // user is supposed to number questions from 1 to 9
 		section[0]=name.substring(0,name.length()-1);
 		section[1]=name.substring(name.length()-1); 
   	    try{   	    
 	    int num=Integer.valueOf(section[1]).intValue();
 	    if ((num<=questions.length) && (num>0) && (questions[num-1]!=null)){ 	     
 			 if (section[0].equals("question"))
 		 		return questions[num-1];
 		 	 if (section[0].equals("solution"))
 		 	 	return solutions[num-1];
 		 	 if (section[0].equals("shortanswer"))
 		 	 	return shortanswers[num-1];
 		}
   	    }
   	    catch (NumberFormatException e){
   	    }
   	    
 	
	
		return super.getSection(name);
	}
	
/**
 * Method generates two sets of whole positive numbers of random size
 * with random numbers (from 1 to 9) as their elements. 
 * Then this method generates Latex-code for questions/solutions/shortanswers.
 */
 
// Generates sets
 protected void generate() {
 	            number1Limits[0]=RandomChoice.randInt(MIN_ELEMENT, MAX_ELEMENT-2);
 	            number1Limits[1]=RandomChoice.randInt(number1Limits[0]+1,MAX_ELEMENT);
 	            number2Limits[0]=RandomChoice.randInt(MIN_ELEMENT, MAX_ELEMENT-2);
 	            number2Limits[1]=RandomChoice.randInt(number2Limits[0]+1,MAX_ELEMENT);
 	            String varname=RandomChoice.makeChoice("[r]/2;[s]/2;[t]/2")[0];
 	            boolean even=(RandomChoice.randInt(0,1)==0) ? true : false;
 	            String tempClause;
 	            MathsOp[] tempOps= new MathsOp[2];
 	            int[] probRatio=new int[2];
 	            int a, k; 	            
 	            MathsOp op;
 	            ProbableEvent[] events = new ProbableEvent[NUMBER_OF_QUESTIONS+1];
 	            int[] newLimits=new int[2];
 	            int[][] fractions=new int[3][2]; 
 	           // HashSet[] sets= new HashSet[2];
 	           // String clauses[]=new String[2];
 	           // int[][] probs=new int[7][2]; 
 	           // int[] evenNums=new int[2];
 	        	
 	            v1=new Variable("\\ensuremath{"+varname+"_{1} }");
 	            v2=new Variable("\\ensuremath{"+varname+"_{2} }");
 	            
 	      // Q1: What is the probability of "v1 is even (odd)"?
 	            tempClause=  (even) ?  v1.toString()+" \\mbox{ is even}" : v1.toString()+" \\mbox{ is odd}"; 	            
 	            questions[0]=tempClause+"?";	
 	            if (even){ 
 	            if (((number1Limits[0]%2)==0) && ((number1Limits[1]%2)==0)) 
 	            	probRatio[0]=(number1Limits[1]-number1Limits[0])/2+1;
 	            else 
 	            	probRatio[0]=(number1Limits[1]-number1Limits[0]+1)/2;
 	            
 	            } else {
 	            if (((number1Limits[0]%2)==1) && ((number1Limits[1]%2)==1)) 
 	            	probRatio[0]=(number1Limits[1]-number1Limits[0])/2+1;
 	            else 
 	            	probRatio[0]=(number1Limits[1]-number1Limits[0]+1)/2;
 	                     
 	            }
 	            
 	            probRatio[1]=number1Limits[1]-number1Limits[0]+1; 	            
 	            fractions[0][0]=probRatio[0]; fractions[0][1]=probRatio[1];	
 	            if ((probRatio[0]%probRatio[1])==0){
 	            	events[0]=new ProbableEvent(tempClause,new IntegerNumber(probRatio[0]/probRatio[1]));
 	            	solutions[0]="\\ensuremath{"
 	            		+(new Equality(new Equality(events[0],
 	            		new FractionOp(new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[0].getProbability())).toString()+" }";
 	            	}
 	            else {  
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]);
 	            	events[0]=new ProbableEvent(tempClause,(MathsOp)(tempOps[1].clone()));
 	                solutions[0]="\\ensuremath{"
 	            		+(new Equality(events[0],tempOps[0])).toString()+" }";
 	            }
 	           	shortanswers[0]="\\ensuremath{"+(events[0].getProbability()).toString()+" }";
 	           	
 	        // Q2: What is the probability of "v1 = a"?
 	            k=1;
 	            a=RandomChoice.randInt(number1Limits[0], number1Limits[1]+1);
 	            if (a==(number1Limits[1]+1)){
 	            	events[1]=new ProbableEvent((new Equality(v1,new IntegerNumber(a))).toString(),new IntegerNumber(0));
 	                solutions[1]="\\ensuremath{ "+(new Equality(events[1],events[1].getProbability())).toString()+" }";
 	            } 
 	            else {
 	            probRatio[0]=1;  	            
 	            probRatio[1]=number1Limits[1]-number1Limits[0]+1;    
 	            if (probRatio[1]==1){
 	            	events[1]=new ProbableEvent((new Equality(v1,new IntegerNumber(a))).toString(),new IntegerNumber(1));
 	            	solutions[1]="\\ensuremath{"+(new Equality(new Equality(events[1],
 	            	new FractionOp(new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[1].getProbability())).toString()+" }";
 	            	}
 	            else {
 	            	events[1]=new ProbableEvent((new Equality(v1,new IntegerNumber(a))).toString(),
 	            		new FractionOp(new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1])));
 	                solutions[1]="\\ensuremath{"+(new Equality(events[1],events[1].getProbability())).toString()+" }";
 	            }
 	            }    
 	            questions[k]="\\ensuremath{"+events[k].getClause()+" } ?";	
 	            shortanswers[1]="\\ensuremath{"+(events[1].getProbability()).toString()+"}";	
 	            	
 	        // Q3: What is the probability of "v1 <= (>=a)"?     
 	            k=2;
 	            switch (RandomChoice.randInt(0,3)){
 	            case	0:
 	            	     a=RandomChoice.randInt(number1Limits[0]+1, number1Limits[1]);
 	            		 op=new Less(v1, new IntegerNumber(a));
 	            		 newLimits[0]=number1Limits[0];
 	            		 newLimits[1]=a-1;
 	            		 break; 	            		 
 	            case	1:
					 	 a=RandomChoice.randInt(number1Limits[0], number1Limits[1]);
 	            		 op=new LessOrEqual(v1, new IntegerNumber(a));
 	            		 newLimits[0]=number1Limits[0];
 	            		 newLimits[1]=a;
 	            		 break; 	     	 	            			 	
 	            case	2: 
 	            		 a=RandomChoice.randInt(number1Limits[0], number1Limits[1]);
 	            		 op=new GreaterOrEqual(v1, new IntegerNumber(a));
 	            		 newLimits[0]=a;
 	            		 newLimits[1]=number1Limits[1];
 	            		 break; 	    		
 	            default: 
 	            		 a=RandomChoice.randInt(number1Limits[0], number1Limits[1]-1);
 	            		 op=new Greater(v1, new IntegerNumber(a));
 	            		 newLimits[0]=a+1;
 	            		 newLimits[1]=number1Limits[1];
 	            		 break; 	    		
 	            } //case
 	            
 	  
 	            			
 	            probRatio[0]=newLimits[1]-newLimits[0]+1;
 	            probRatio[1]=number1Limits[1]-number1Limits[0]+1;
 	            if ((probRatio[0]%probRatio[1])==0){
 	            	events[k]=new ProbableEvent(op.toString(),new IntegerNumber(probRatio[0]/probRatio[1]));
 	            	solutions[k]="\\ensuremath{"+(new Equality(new Equality(events[k],new FractionOp(
 	            		new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[k].getProbability())).toString()+" }";
 	            	}
 	            else {
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]);
 	            	events[k]=new ProbableEvent(op.toString(),(MathsOp)(tempOps[1].clone()));
 	                solutions[k]="\\ensuremath{"
 	            		+(new Equality(events[k],tempOps[0])).toString()+" }";	
 	            }
 	            questions[k]="\\ensuremath{"+events[k].getClause()+"} ?";
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	
 	          
 	      // Q4: What is the probability of "event 0 and event 2" ?
 	            k=3;
 	            tempClause=events[0].getClause()+" \\mbox{ and } "+events[2].getClause();
 				probRatio[0]=0;
 				for (int i=newLimits[0]; i<=newLimits[1]; i++ )
 					if (even){
 						if ((i%2)==0) 
 					    	probRatio[0]++;
 					} else {
 						if ((i%2)==1)
 							probRatio[0]++;
 					}
 				probRatio[1]=number1Limits[1]-number1Limits[0]+1; 				
 			    if ((probRatio[0]%probRatio[1])==0){
 	            	events[k]=new ProbableEvent(tempClause,new IntegerNumber(probRatio[0]/probRatio[1]));
 	            	solutions[k]="\\ensuremath{"+(new Equality(new Equality(events[k],new FractionOp(
 	            		new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[k].getProbability())).toString()+" }";
 	            	}
 	            else {
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]);
 	            	events[k]=new ProbableEvent(tempClause,(MathsOp)(tempOps[1].clone()));
 	                solutions[k]="\\ensuremath{"
 	            		+(new Equality(events[k],tempOps[0])).toString()+" }";	
 	            }
 	            questions[k]="\\ensuremath{"+events[k].getClause()+"} ?";
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	
 	           		
 	      // Q5: What is the probability of "event 0 and event 2"  ?
 	       		k=4;
 	       		tempClause=events[0].getClause()+" \\mbox{ or } "+events[2].getClause();
 	       		probRatio[0]=0;
 	       		for (int i=number1Limits[0]; i<=number1Limits[1]; i++ )
 					if ( ((even) && ((i%2)==0)) || ((!(even)) && ((i%2)==1)) || ((newLimits[0]<=i)&&(i<=newLimits[1])) )
 					    	probRatio[0]++;
 	            probRatio[1]=number1Limits[1]-number1Limits[0]+1; 	
 	            if ((probRatio[0]%probRatio[1])==0){
 	            	events[k]=new ProbableEvent(tempClause,new IntegerNumber(probRatio[0]/probRatio[1]));
 	            	solutions[k]="\\ensuremath{"+(new Equality(new Equality(events[k],new FractionOp(
 	            		new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[k].getProbability())).toString()+" }";
 	            	}
 	            else {
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]);
 	            	events[k]=new ProbableEvent(tempClause,(MathsOp)(tempOps[1].clone()));
 	                solutions[k]="\\ensuremath{"
 	            		+(new Equality(events[k],tempOps[0])).toString()+" }";	
 	            }
 	            questions[k]="\\ensuremath{"+events[k].getClause()+"} ?";
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	
 	          
 	      // Q6: What is the probability of "event 0 given that event 2"  ?  
 	      		k=5;
 	      		tempClause=events[0].getClause()+" \\mbox{ given that } "+events[2].getClause();
 	      		probRatio[1]=newLimits[1]-newLimits[0]+1;
 	      	    if (even){ 
 	            if (((newLimits[0]%2)==0) && ((newLimits[1]%2)==0)) 
 	            	probRatio[0]=(newLimits[1]-newLimits[0])/2+1;
 	            else 
 	            	probRatio[0]=probRatio[1]/2;
 	            } else {
 	            if (((newLimits[0]%2)==1) && ((newLimits[1]%2)==1)) 
 	            	probRatio[0]=(newLimits[1]-newLimits[0])/2+1;
 	            else 
 	            	probRatio[0]=probRatio[1]/2;   
 	            }
 	      	    if ((probRatio[0]%probRatio[1])==0){
 	            	events[k]=new ProbableEvent(tempClause,new IntegerNumber(probRatio[0]/probRatio[1]));
 	            	solutions[k]="\\ensuremath{"+(new Equality(new Equality(events[k],new FractionOp(
 	            		new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1]))),
 	            		events[k].getProbability())).toString()+" }";
 	            	}
 	            else {
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]);
 	            	events[k]=new ProbableEvent(tempClause,(MathsOp)(tempOps[1].clone()));
 	                solutions[k]="\\ensuremath{"
 	            		+(new Equality(events[k],tempOps[0])).toString()+" }";	
 	            }
 	            questions[k]="\\ensuremath{"+events[k].getClause()+"} ?";
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";
 	      	    
 	      	    
 	      // Q7: What is the probability of "both v1(event 0) and v2 are even (odd)"?
 	           k=6;
 	           	tempClause= (even) ? v1.toString()+" \\mbox{ and }"+v2.toString()+" \\mbox{ are even }" :
 	           		 v1.toString()+" \\mbox{ and }"+v2.toString()+" \\mbox{ are odd }";
 	           events[k]=new ProbableEvent("\\mbox{both }"+tempClause);	   	  		  	
 	           if (even){ 
 	           	events[NUMBER_OF_QUESTIONS]=new ProbableEvent(v2.toString()+"\\mbox{ is even}");
 	            if (((number2Limits[0]%2)==0) && ((number2Limits[1]%2)==0)) 
 	            	probRatio[0]=(number2Limits[1]-number2Limits[0])/2+1;
 	            else 
 	            	probRatio[0]=(number2Limits[1]-number2Limits[0]+1)/2; 	            
 	            } else {
 	            events[NUMBER_OF_QUESTIONS]=new ProbableEvent(v2.toString()+"\\mbox{ is odd}");	
 	            if (((number2Limits[0]%2)==1) && ((number2Limits[1]%2)==1)) 
 	            	probRatio[0]=(number2Limits[1]-number2Limits[0])/2+1;
 	            else 
 	            	probRatio[0]=(number2Limits[1]-number2Limits[0]+1)/2;
 	            }
 	            probRatio[1]=number2Limits[1]-number2Limits[0]+1; 	              	
 	            fractions[1][0]=probRatio[0]; fractions[1][1]=probRatio[1];	 	
 	            if ((probRatio[0]%probRatio[1])==0){
 	            	events[NUMBER_OF_QUESTIONS].setProbability(new IntegerNumber(probRatio[0]/probRatio[1]));	
 	            	tempOps[0]=new Equality(
 	            		new FractionOp(new IntegerNumber(probRatio[0]), new IntegerNumber(probRatio[1])),
 	            		events[NUMBER_OF_QUESTIONS].getProbability());
 	            	}
 	            else {  
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]); 	
 	            	events[NUMBER_OF_QUESTIONS].setProbability((MathsOp)(tempOps[1].clone()));
 	            }
 	            
 	            probRatio[0]=fractions[0][0]*fractions[1][0];
 	            probRatio[1]=fractions[0][1]*fractions[1][1];
 	            fractions[2][0]=probRatio[0];
 	            fractions[2][1]=probRatio[1];
 	            solutions[k]="\\ensuremath{"+(new Equality(events[0],events[0].getProbability())).toString()+
 	            	"}, and  \\ensuremath{"+(new Equality(events[NUMBER_OF_QUESTIONS],tempOps[0])).toString()+
 	            		" } .\\\\[1.5mm] \n Now \\ensuremath{"+v1.toString()+" \\mbox{ and } "+v2.toString()+
 	            			"} are chosen independently, \\\\[1.5mm]  \n so \\ensuremath{ "+
 	            		(new Equality(events[k],new Multiplication(events[0],events[NUMBER_OF_QUESTIONS]))).toString()+" }. \\\\[1.5mm] \n Hence ";
 	            		
 	            if ((probRatio[0]%probRatio[1])==0)
 	            	events[k].setProbability(new IntegerNumber(probRatio[0]/probRatio[1]));	
 	            else {  
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]); 	
 	            	events[k].setProbability((MathsOp)(tempOps[1].clone()));
 	            }	
 	            solutions[k]+="\\ensuremath{"+(new Equality(events[k],new Equality(new Multiplication(events[0].getProbability(),
 	            	events[NUMBER_OF_QUESTIONS].getProbability()), events[k].getProbability()))).toString()+" }";		   	
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	  	
 	           	questions[k]="\\ensuremath{ \\mbox{Both }" + tempClause + " }?"; 
 	           		
 	       // Q8: What is the probability of "at least one of v1(event 0) and v2 are even (odd)"?
 	       		k=7;
 	       		tempClause= (even) ? v1.toString()+" \\mbox{ and }"+v2.toString()+" \\mbox{ is even }" :
 	           		 v1.toString()+" \\mbox{ and }"+v2.toString()+" \\mbox{ is odd }";
 	           	events[k]=new ProbableEvent(events[0].getClause()+" \\mbox{ \\bf or }"+events[NUMBER_OF_QUESTIONS].getClause());
 	           	probRatio[0]=fractions[0][0]*fractions[1][1]*fractions[2][1]+fractions[1][0]*fractions[0][1]*fractions[2][1]-
 	           		fractions[2][0]*fractions[0][1]*fractions[1][1];
 	           	probRatio[1]=fractions[0][1]*fractions[1][1]*fractions[2][1];
 	           	
 	           	if ((probRatio[0]%probRatio[1])==0)
 	            	events[k].setProbability(new IntegerNumber(probRatio[0]/probRatio[1]));	
 	            else {  
 	            	tempOps=fractionSimple(probRatio[0], probRatio[1]); 	
 	            	events[k].setProbability((MathsOp)(tempOps[1].clone()));
 	            }
 	            solutions[k]="By the principle of inclusion\\textbackslash exclusion, \\\\[1.5mm] \n \\ensuremath{"+
 	            	(new Equality(events[k],new Subtraction(new Addition(events[0],events[NUMBER_OF_QUESTIONS]),events[6]))).toString()+
 	            		" }. \\\\ [1.5mm] \n Hence \\ensuremath{"+(new Equality(new Equality(events[k],
 	            		new Subtraction(new Addition(events[0].getProbability(), events[NUMBER_OF_QUESTIONS].getProbability()),
 	            		events[6].getProbability())), events[k].getProbability())).toString()+" }";
 	            		 	            	
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	 	 
 	           	questions[k]="\\ensuremath{ \\mbox{At least one of }" + tempClause + " }?"; 	 
 	           		
 	       // Q9: What is the probability of "v1 is even (or odd) (event 0) given v2 are even (odd)"?
 	            k=8;
 	            tempClause=(RandomChoice.randInt(0,1)==1) ? "even" : "odd";   		 
 	       		events[k]= (even) ? new ProbableEvent( v1.toString()+" \\mbox{ is even \\bf given that }"+v2.toString()+
 	       			" \\mbox{ is "+tempClause+" }") :
 	           		 new ProbableEvent(v1.toString()+" \\mbox{ is odd \\bf given that }"+v2.toString()+" \\mbox{ is "+tempClause+" }");
 	           	events[k].setProbability(events[0].getProbability());	 
 	           	questions[k]="\\ensuremath{ "+events[k].getClause().replace("\\bf","")+" }?";
 	           	solutions[k]="\\ensuremath{ "+new Equality(events[k],events[0])+" }. \\\\ [1.5mm] \n Hence \\ensuremath{ "+
 	           		(new Equality(events[k], events[k].getProbability())).toString()+" }";
 	           	shortanswers[k]="\\ensuremath{"+(events[k].getProbability()).toString()+" }";	 	 
 	           	
 	           		 
	}//generate
	
	// returns first argument - solution, second argument - fraction, nom/denom is not an integer, denom!=0!
	private MathsOp[] fractionSimple(int num, int denom){
		int resnum, resdenom, hcf1;
		MathsOp[] res= new MathsOp[2];
			if (! Primes.areCoprime(num, denom) ) {       
						hcf1=HCFModule.hcf(num,denom);		
	  					resnum=num/hcf1;
	  					resdenom=denom/hcf1;
						res[0]=new Equality (new FractionOp(new IntegerNumber(num),new IntegerNumber(denom)), 
							 (new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom) )));
						res[1]= new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom) ); 	 
		
			} else {
				res[0]=new FractionOp(new IntegerNumber(num),new IntegerNumber(denom)); 
				res[1]= new FractionOp(new IntegerNumber(num), new IntegerNumber(denom) ); 	
			}
		
		return res; 
	} //fractionSimple 
 } 