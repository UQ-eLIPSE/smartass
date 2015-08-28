/* @(#)CompoundingCompositeModule.java
 *
 * This file is part of SmartAss and describes class CompoundingCompositeModule for 
 * the set of questions on compounding interest. Formulae F=P(1+r)^n or F=Pe^(n*r)
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
//import java.lang.reflect.Array;
import java.math.*;

/**
* Class CompoundingCompositeModule describes the set of question on 
* periodical compounding F=P(1+r)^n monthly, quarterly, every six months or annually;
* and compounding continuously F=Pe^(r*n)   
*
* @version 1.0 05.02.2007
*/
public class CompoundingCompositeModule extends MathsModule{
 final int MONTHS_IN_YEAR=12;
 private int  P, t;
 private double  annualr;
 private double [] r=new double[5]; //0 - continuouse, 1 - monthly, 2 - quarterly, 3 - six months, 4 - annually
 private double [] F=new double[5];
 private int n [] =new int[5];
 private String approxSign="\\approx";//MathsOp.getTex(Approx.class)[0]; 
 private String equalSign="=";//MathsOp.getTex(Equality.class)[0];	                   	 
 private String[] answers=new String[5]; 
 private String[] eqsigns=new String[5];	
/**
* Constructor CompoundingCompositeModule initialises the question.
* I=100 x [1..10]
* r=0.01x [1..10]
* t=[1..10]
*/
 public CompoundingCompositeModule(Engine engine) 
	{
				super(engine);
				P=100*RandomChoice.randInt(1,4); //initial amount
 				annualr=0.01*RandomChoice.randInt(3,10);
 				t=RandomChoice.randInt(1,10); //time in years        	 
                solve();
	} //constructor

/**
* Constructor CompoundingCompositeModule initialises the question
* with parameters passing.
* @params  params[0] - initial amount
*          parmas[1] - annual interest rate
*          params[2] - number of years
*/
 public CompoundingCompositeModule(Engine engine, String[] params) 
	{
				super(engine);
				P=Integer.parseInt(params[0]); //initial amount
 				annualr=Double.parseDouble(params[1]); //annual interest rate
 				t=Integer.parseInt(params[2]); //time in years
                solve();
	} //constructor

	
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 	    
       
      
		if(name.equals("p")) 
			return String.valueOf(P);
					
				
		if(name.equals("ttime")) {
		    if (t==1) return "1 year";
		      else return t+" years";
		}
				
					
		if(name.equals("t")) {
			return String.valueOf(t);
		}
		if(name.equals("txr"))
			return (new BigDecimal(t*annualr).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString();			
        if(name.equals("fcont"))
        	return answers[0]; 
        if(name.equals("contsign"))
        	return eqsigns[0];		
        	
        if(name.equals("rmonthly"))
        	return  (new BigDecimal(r[1]).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString(); 	
        if(name.equals("nmonthly"))
        	return String.valueOf(n[1]); 
        if(name.equals("fmonthly"))
        	return answers[1];
        if(name.equals("rplus1monthly"))
			return (new BigDecimal(r[1]+1).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString();	
        if(name.equals("percentmonthly"))
			return 	(new BigDecimal(r[1]*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString();
	    if(name.equals("monthlysign"))
			return eqsigns[1];	
			
		if(name.equals("rquarterly"))
        	return  (new BigDecimal(r[2]).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString(); 	
        if(name.equals("nquarterly"))
        	return String.valueOf(n[2]); 
        if(name.equals("fquarterly"))
        	return answers[2];
		if(name.equals("rplus1quarterly"))
			return (new BigDecimal(r[2]+1).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString();	
        if(name.equals("percentquarterly"))
			return 	(new BigDecimal(r[2]*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString();
		if(name.equals("quarterlysign"))	
			return eqsigns[2];
			
		if(name.equals("rsixmonths"))
        	return  (new BigDecimal(r[3]).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString(); 	
        if(name.equals("nsixmonths"))
        	return String.valueOf(n[3]); 
        if(name.equals("fsixmonths"))
        	return answers[3];
		if(name.equals("rplus1sixmonths"))
			return (new BigDecimal(r[3]+1).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString();	
        if(name.equals("percentsixmonths"))
			return 	(new BigDecimal(r[3]*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString();
		if (name.equals("sixmonthssign"))
			return eqsigns[3];	
			
        if(name.equals("rannual"))
        	return  (new BigDecimal(r[4]).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString(); 	
        if(name.equals("fannual"))
        	return answers[4]; 
		if(name.equals("rplus1annual"))
			return (new BigDecimal(r[4]+1).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString();	
        if(name.equals("percentannual"))
			return 	(new BigDecimal(r[4]*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString();				
		if(name.equals("annualsign"))
			return eqsigns[4];		
		return super.getSection(name);
	}
	
// Generates the following :
// I=100 x [1..10]
// r=0.01x [1..10]
// t=[2..20]
 public void solve() {
 	           
       					
 	        	n[0]=t; //continuous
 	        	r[0]=annualr;
 	        	F[0]=P*Math.exp(r[0]*n[0]);	
 	        		
 	            n[1]=t*12; //monthly
 	            r[1]=annualr/12;
 	            F[1]=P*Math.pow(1+r[1], n[1]);
 	            
 	            n[2]=t*4; //quarterly
 	            r[2]=annualr/4;
 	            F[2]=P*Math.pow(1+r[2], n[2]);
 	            
 	            n[3]=t*2; //every six months
 	            r[3]=annualr/2;
 	            F[3]=P*Math.pow(1+r[3], n[3]);
 	            
 	            n[4]=t; //annually
 	            r[4]=annualr;
 	            F[4]=P*Math.pow(1+r[4], n[4]);
 	            for (int i=0; i<5; i++){
       	 		  answers[i]= (new BigDecimal(F[i]).setScale(2,BigDecimal.ROUND_HALF_UP)).toString();
       	 	 	  eqsigns[i]=(F[i]==Double.valueOf(answers[i]))? equalSign : approxSign;
       	 
 	           }
 	        			     		
	}//sovle
} 
