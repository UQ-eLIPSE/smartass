/* @(#)ExpGrowthModule.java
 *
 * This file is part of SmartAss and describes class ExpGrowthModule for 
 * question on exponential growth. Formulae F=Ie^(rt)
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
//import java.util.*;
import java.math.*;

/**
* Class ExpGrowthModule describes the question on 
* exponential growth "Find F=Ixe^(rxt).   
*
* @version 1.0 15.01.2007
*/
public class ExpGrowthModule extends MathsModule{
 private int I, t, now;
 private double r, F, Fdecay, Fwant;
 
/**
* Constructor ExpGrowthModule initialises the question.
* I=100 x [1..10]
* r=0.01x [1..10]
* t=[2..20]
*/
 public ExpGrowthModule(Engine engine) 
	{
				super(engine);
				this.generate("no parameters");
			
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
		if(name.equals("question")) {
			String question="\\ensuremath{ I="+I+" r="+(new BigDecimal(r).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()
												+" t="+t+" }";
			return question;									
 		}		
		if(name.equals("i")) 
			return "\\ensuremath {"+I+"}";
						
		if(name.equals("r")) 
			return "\\ensuremath {"+(new BigDecimal(r).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";
			
		if(name.equals("rpercent"))
			return 	"\\ensuremath {"+(new BigDecimal(r*100).setScale(1,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("t"))
			return "\\ensuremath {"+t+"}";
		
		if(name.equals("f"))		
			return "\\ensuremath {"+(new BigDecimal(F).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("rt"))		
			return "\\ensuremath {"+(new BigDecimal(r*t)).
				setScale(4,BigDecimal.ROUND_HALF_UP).stripTrailingZeros()+"}";
	
		if(name.equals("fwant"))		
			return "\\ensuremath {"+(new BigDecimal(Fwant).setScale(0,BigDecimal.ROUND_HALF_UP)).toString()+"}";			
		
		if(name.equals("growthsol"))		
			return "\\ensuremath {"+(new BigDecimal(F).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";

		if(name.equals("decaysol"))		
			return "\\ensuremath {"+(new BigDecimal(Fdecay).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";		
		
		if(name.equals("tsol"))		
			return "\\ensuremath {"+(new BigDecimal(Math.log(Fwant/I)/r).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";		
					
		if(name.equals("rsol"))		
			return "\\ensuremath {"+(new BigDecimal(Math.log(Fwant/I)/t).setScale(4,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("foni"))		
			return "\\ensuremath {"+(new BigDecimal(Fwant/I).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";

		if(name.equals("logfoni"))		
			return "\\ensuremath {"+(new BigDecimal(Math.log(Fwant/I)).setScale(2,BigDecimal.ROUND_HALF_UP)).toString()+"}";
		
		if(name.equals("now"))
			return "\\ensuremath {"+now+"}";
		
		if(name.equals("tage"))
			return "\\ensuremath {"+((new BigDecimal(Math.log(Fwant/I)/r).setScale(0,BigDecimal.ROUND_HALF_UP)).add(new BigDecimal(now))).toString()+"}";			
				
		return super.getSection(name);
	}
	
// Generates the following :
// I=100 x [1..10]
// r=0.01x [1..10]
// t=[2..20]
 public void generate(String params) {
 	            I=100*RandomChoice.randInt(1,10);
 	            r=0.01*RandomChoice.randInt(1,10);
 	            t=RandomChoice.randInt(2,20);
		     	F=I*Math.exp(r*t);	
		    	Fdecay=I*Math.exp(-r*t);	
		    	Fwant=(1.5+0.1*RandomChoice.randInt(1,15))*I;		     
		  		now=RandomChoice.randInt(10,20);		
	}//generate 
} 
