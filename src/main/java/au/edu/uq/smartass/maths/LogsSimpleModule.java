/* @(#) LogsSimpleModule.java
 *
 * This file is part of SmartAss and describes class LogsSimpleModule.
 * A few simple questions on logarithms.
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
* Class LogsSimpleModule describes the questions on 
* logarithms.   
*
* @version 1.0 26.05.2007
*/
public class LogsSimpleModule extends MathsModule{
 private final int MAX_ELEMENT=10;
 private final int NUMBER_OF_QUESTIONS=8; 
 	
 private String[] questions=new String[NUMBER_OF_QUESTIONS];
 private String[] solutions=new String[NUMBER_OF_QUESTIONS];
 private String[] shortanswers=new String[NUMBER_OF_QUESTIONS]; 
 
 
 
/**
* Constructor LogsSimpleModule initialises the question.
* 
*/
 public LogsSimpleModule(Engine engine) 
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
 	
 		
 // user is supposed to number questions from 1 to NUMBER_OF_QUESTIONS
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
 * Method generates simple logarithms and 
 * and Latex-code for questions/solutions/shortanswers.
 */
 
// Generates sets
 protected void generate() {
				int b1, a1, p1;
				final Variable e= new Variable("e");
				MathsOp base, argument, power;
				
		//Q1: log_{[2..20]}base^[10..20]
				b1=RandomChoice.randInt(2,MAX_ELEMENT*2);
				p1=RandomChoice.randInt(MAX_ELEMENT, MAX_ELEMENT*2)*RandomChoice.randSign();
				base=new IntegerNumber(b1);
				power=new IntegerNumber(p1);
				argument=new Power (base, power);
				questions[0]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
			    solutions[0]="\\ensuremath{"+(new Equality(new Log(base, argument), power)).toString()+" }";
				shortanswers[0]="\\ensuremath{" + power.toString() + " }";
			
 	     //Q2: log_{[2..5]}valueOf(base^[0..3])
				b1=RandomChoice.randInt(2,MAX_ELEMENT/2);
				p1=RandomChoice.randInt(0, 3);
				a1=1;
				for (int i=0; i<p1; i++ )
					a1*=b1;
				base=new IntegerNumber(b1);
				argument=new IntegerNumber(a1);
				power=new IntegerNumber(p1);				
				questions[1]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
			    solutions[1]="\\ensuremath{"+(new Equality(argument,new Power(base, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+ (new Equality(new Log (base, argument), power)).toString()+" }";
				shortanswers[1]="\\ensuremath{" + power.toString() + " }"; 

		//Q3: log_{[2..5]}(1/base^[0..3])
				b1=RandomChoice.randInt(2,MAX_ELEMENT/2);
				p1=RandomChoice.randInt(1, 3);
				a1=1;
				for (int i=0; i<p1; i++ )
					a1*=b1;
				base=new IntegerNumber(b1);
				argument=new FractionOp(new IntegerNumber(1),new IntegerNumber(a1));
				power=new IntegerNumber(p1);				
				questions[2]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
				power=new UnaryMinus(power);
			    solutions[2]="\\ensuremath{"+(new Equality(argument,new Power(base, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+(new Equality (new Equality(new Log (base, argument), 
			    		new Log (base, new Power(base, power))), power)).toString()+" }";
				shortanswers[2]="\\ensuremath{" + power.toString() + " }"; 
					
 		//Q4: log_{10}valueOf(10^[0..6])
				b1=10;
				p1=RandomChoice.randInt(0, 6);
				a1=1;
				for (int i=0; i<p1; i++ )
					a1*=b1;
				base=new IntegerNumber(b1);
				argument=new IntegerNumber(a1);
				power=new IntegerNumber(p1);				
				questions[3]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
			    solutions[3]="\\ensuremath{"+(new Equality(argument,new Power(base, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+(new Equality(new Log (base, argument), power)).toString()+" }";
				shortanswers[3]="\\ensuremath{" + power.toString() + " }"; 					
					     		 
		//Q5: log_{10}(1/10^[0..6])
				b1=10;
				p1=RandomChoice.randInt(1, 6);
				a1=1;
				for (int i=0; i<p1; i++ )
					a1*=b1;
				base=new IntegerNumber(b1);
				argument=new FractionOp(new IntegerNumber(1),new IntegerNumber(a1));
				power=new IntegerNumber(p1);				
				questions[4]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
				power=new UnaryMinus(power);
			    solutions[4]="\\ensuremath{"+(new Equality(argument,new Power(base, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+(new Equality(new Log (base, argument), power)).toString()+" }";
				shortanswers[4]="\\ensuremath{" + power.toString() + " }"; 
		
		//Q6: ln e^[0..20]
		        p1=Integer.valueOf(RandomChoice.makeChoice("[2.."+(MAX_ELEMENT*2)+"]/6;[0]/2;[1]/2")[0]);
		        if (p1<=1){
		        	argument=(p1==0)? (new IntegerNumber(1)) : e;  
		        } else {
		        	p1*=RandomChoice.randSign();	
		        	argument=new Power(e, new IntegerNumber(p1));
		        }
		     	power=new IntegerNumber(p1);
						
				questions[5]="\\ensuremath{"+(new Ln(argument)).toString()+" }";
				if (Math.abs(p1)>1)
			    	solutions[5]="\\ensuremath{"+(new Equality(new Ln(argument), power)).toString()+" }";
			    else 
			    	solutions[5]="\\ensuremath{"+(new Equality(argument,new Power(e, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+(new Equality(new Ln (argument), power)).toString()+" }";	
				shortanswers[5]="\\ensuremath{" + power.toString() + " }";
								
		//Q7: ln 1/e^[0..20]
		        p1=Integer.valueOf(RandomChoice.makeChoice("[2.."+(MAX_ELEMENT*2)+"]/6;[1]/3")[0]);
		        if (p1==1)
		        	argument=new FractionOp(new IntegerNumber(1), e);  
		        else 
		        	argument=new FractionOp (new IntegerNumber(1), new Power(e, new IntegerNumber(p1)));
		        
		     	power=new UnaryMinus(new IntegerNumber(p1));
						
				questions[6]="\\ensuremath{"+(new Ln(argument)).toString()+" }";
			
			    solutions[6]="\\ensuremath{"+(new Equality(argument,new Power(e, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} "+(new Equality (new Equality(new Ln (argument),
			    	new Ln(new Power(e, power))), power)).toString()+" }";	
				shortanswers[6]="\\ensuremath{" + power.toString() + " }";
				
 		//Q8: log_{[2..5]}valueOf(base^[0..3])
				a1=RandomChoice.randInt(2,MAX_ELEMENT/2);
				p1=RandomChoice.randInt(2, 3);
				b1=1;
				for (int i=0; i<p1; i++ )
					b1*=a1;
				base=new IntegerNumber(b1);
				argument=new IntegerNumber(a1);	
				power=new FractionOp(new IntegerNumber(1), new IntegerNumber(p1));
				
				String[] oldStr=power.getTex();
				String[] newStr=new String[oldStr.length];
				newStr[0]="\\frac";
				for (int z=1; z<oldStr.length; z++)
					newStr[z]=oldStr[z];				
				power.setTex(newStr);				
				questions[7]="\\ensuremath{"+(new Log(base, argument)).toString()+" }";
			    solutions[7]="\\ensuremath{"+(new Equality(argument,new Power(base, power))).toString()+
			    	" ,\\mbox{ so }\\hspace{3mm} ";
			    power.setTex(oldStr);
			    solutions[7]+=(new Equality(new Log (base, argument), power)).toString()+" }";
				shortanswers[7]="\\ensuremath{" + power.toString() + " }"; 		
						     		 
	}//generate
	
	// returns first argument - solution, second argument - fraction, nom/denom is not an integer, denom!=0!
/*	private MathsOp[] fractionSimple(int num, int denom){
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
*/	
 } 