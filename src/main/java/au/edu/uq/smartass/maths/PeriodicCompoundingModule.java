/* @(#)PeriodicCompoundingModule.java
 *
 * This file is part of SmartAss and describes class ExpGrowthModule for 
 * question on exponential growth. Formulae F=I(1+r)^t
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
import java.lang.reflect.Array;
import java.math.*;

/**
* Class PeriodicCompoundingModule describes the question on 
* periodical compounding F=I(1+r)^t.   
*
* @version 1.0 05.02.2007
*/
public class PeriodicCompoundingModule extends MathsModule{
 final int MONTHS_IN_YEAR=12;
 private int I, t, period, n;
 private double annualr, Fdecay, r;
 
 
/**
* Constructor ExpGrowthModule initialises the question.
* I=100 x [1..10]
* r=0.01x [1..10]
* t=[2..20]
*/
 public PeriodicCompoundingModule(Engine engine) 
	{
				super(engine);
				this.generate("no parameters");
			
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {

		if(name.equals("i")) 
			return "\\ensuremath {"+I+"}";
						
		if(name.equals("rpercent")) 
			return "\\ensuremath {"+(new BigDecimal(r*100).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("r")) 
			return "\\ensuremath {"+(new BigDecimal(r).setScale(4,BigDecimal.ROUND_HALF_UP)).stripTrailingZeros().toString()+"}";			

		if (name.equals("rsolution")){
			if (period==12) return "";
			else return "\\ensuremath{"+period+"\\times\\dfrac{"
				+(new BigDecimal(annualr*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString()+"}{12}=}";
		}							
			
        if (name.equals("nsolution")){
        	if (period==12) return "";
        	else return "\\ensuremath{"+t+"\\div"+period+"=}";
        }
        
		if(name.equals("annualpercent"))
			return 	"\\ensuremath {"+(new BigDecimal(annualr*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("ttime")) {
		    if (t==1) return "1 month'";
		    if ((t%12)==0){
		    	int years=t/12;
		    	if (years==1) return "1 year'";
		    	else
		    	return years+" years'";
		    }
			return t+" months'";
		}

		if(name.equals("t")) {
			return String.valueOf(t);
		}

		if(name.equals("period"))
			return "\\ensuremath {"+period+"}";

		if(name.equals("periodinwords")){
			switch(period) {
				case 12:
					return "annually";
				case 6:
					return "every 6 months";
				case 3:
					return "quarterly";
				case 1:
					return "monthly";
				default:
					return "never!";
			}
		}

		if(name.equals("oneplusrinton"))		
			return "\\ensuremath {"+(new BigDecimal(Math.pow(1+r,n))).
				setScale(4,BigDecimal.ROUND_HALF_UP).stripTrailingZeros()+"}";

		if(name.equals("decaysol"))		
			return "\\ensuremath {"+(new BigDecimal(Fdecay).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";		
		
		if(name.equals("n"))
			return "\\ensuremath {"+n+"}";
	
		return super.getSection(name);
	}
	
// Generates the following :
// I=100 x [1..10]
// r=0.01x [1..10]
// t=[2..20]
 public void generate(String params) {
 	            I=100*RandomChoice.randInt(1,4);
 	            annualr=0.01*RandomChoice.randInt(3,10);
 	            t=RandomChoice.randInt(1,24);
 	            if ((t%12)==0) 	
 	            	period=12; 	            	
 	            else if ((t%6)==0)
 	            	period=6;	
 	            else if ((t%3)==0)
 	            	period=3;
 	            else period=1;	           	 
 	            r=(double)period*annualr/MONTHS_IN_YEAR; 	                       
 	            n=t/period;	
		     //	F=	
		    	Fdecay=(double)I*Math.pow(1+r,-n);		    			     		
	}//generate 
} 
