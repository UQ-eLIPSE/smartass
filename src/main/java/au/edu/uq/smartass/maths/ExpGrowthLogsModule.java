/* @(#)ExpGrowthLogsModule.java
 *
 * This file is part of SmartAss and describes class ExpGrowthLogsModule for 
 * question on exponential growth with the use of logarithms. Formulae 2=e^(kh)
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
* exponential growth  2=e^(kh), find k.   
*
* @version 1.0 14.04.2008
*/
public class ExpGrowthLogsModule extends MathsModule{
 private double h,k;
 
/**
* Constructor ExpGrowthLogsModule initialises the question.
* k=0.5 x [2..40]
*/
 public ExpGrowthLogsModule(Engine engine) 
	{
				super(engine);
				h=RandomChoice.randInt(20,100)*0.5;
			    k=RandomChoice.randInt(1,20)*0.01;
	} //constructor
	
/**
* Constructor ExpGrowthLogsModule initialises the question.
* @params	params[0] - h - doubling time (or growth rate) in the formula 2=e^(kh)
* 			params[1] - k - growth rate (per hour).
*/	
public ExpGrowthLogsModule (Engine engine, String[] params){
		super(engine);		
				try{
					if (params.length==1){
					
			    	h=Double.parseDouble(params[0]); 
			    	k=RandomChoice.randInt(1,20)*0.01;
					} else {
						h=Double.parseDouble(params[0]); 
			    	    k=Double.parseDouble(params[1]); 
					}
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into ExpGrowthLogsModule");
					throw e;
				}
	
}

/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
		if(name.equals("h"))		
			return "\\ensuremath {"+(new DecimalNumber((new BigDecimal(h)).
				setScale(1,BigDecimal.ROUND_HALF_UP))).trimZeros()+"}";
		if(name.equals("round_k"))		
			return "\\ensuremath {"+(new DecimalNumber((new BigDecimal(Math.log(2)/h)).
				setScale(4,BigDecimal.ROUND_HALF_UP))).trimZeros()+"}";				
		if(name.equals("k")) 
			return "\\ensuremath {"+(new DecimalNumber((new BigDecimal(k)).
				setScale(2,BigDecimal.ROUND_HALF_UP))).trimZeros()+"}";
		if(name.equals("round_h"))		
			return "\\ensuremath {"+(new DecimalNumber((new BigDecimal(Math.log(2)/k)).
				setScale(4,BigDecimal.ROUND_HALF_UP))).trimZeros()+"}";						
		return super.getSection(name);
	}
	

} 
