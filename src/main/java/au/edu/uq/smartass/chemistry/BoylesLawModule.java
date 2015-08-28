/* @(#)BoylesLawModule.java
 *
 * This file is part of SmartAss and describes class BoylesLawModule for 
 * question on Boyle's law. Given P1 and V1, calculate the pressure, if new volume is V2.
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
package au.edu.uq.smartass.chemistry;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import au.edu.uq.smartass.maths.*;
import java.math.*;

/**
* Class BoylesLawModule describes the question on 
* Boyle's law: Given P1 and V1, calculate the pressure, if new volume is V2.   
*
* @version 1.0 15.01.2007
*/
public class BoylesLawModule extends MathsModule{
 private int minPressurePa=80000;  //pressure is always in Pa!
 private int maxPressurePa=110000; 
 private int minVolume=100;
 private int maxVolume=1200; 
 private int v1, v2, p1;
 private double p2;
 private char punit=RandomChoice.makeChoice("[m]/5;[p]/5")[0].charAt(0); //m - mmHg, k - kPa
 
/**
* Constructor BoylesLawModule initialises the question
* with parameters passing.
* In case of 4 parameters:
* @params  params[0] - p1 - initial pressure, in Pa or mmHg (note  - only integers allowed)
*          parmas[1] - units of p1,
*          params[2] - v1 - initial volume, 
*          params[3] - v2 - new volume
* or
* @params  params[0] - string "limits", indicating that there are limits being passed
*		   params[1] - minimum pressure in pa, 
*          parmas[2] - maximum pressure in pa,
*          params[3] - minimum value,
*          params[4] - maximum value
*/
 public BoylesLawModule(Engine engine, String[] params) 
	{
				super(engine);
				
				try{
			    if (params.length==4){
			    	punit=params[1].charAt(0);
					p1=Integer.parseInt(params[0]); 
					if (punit=='m')	
					 p1=(int)((double)p1*760/101325);		
 					v1=Integer.parseInt(params[2]);
 					v2=Integer.parseInt(params[3]);
			    } else{
			    	if (params.length==5){ 
			    	   minPressurePa=Integer.parseInt(params[1]);
			    	   maxPressurePa=Integer.parseInt(params[2]);
			    	   minVolume=Integer.parseInt(params[3]);
			    	   maxVolume=Integer.parseInt(params[4]);   
			    	}   
			      generate();
				}	 
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into BoylesLawModule");
					throw e;
				}
	} //constructor
 
/**
* Constructor BoylesLawModule initialises the question
*/
 public BoylesLawModule(Engine engine) 
	{
				super(engine);
				generate();
              
	} //constructor
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
		if(name.equals("p1kpa")) 
			return (new BigDecimal((double)p1/1000).setScale(2,BigDecimal.ROUND_HALF_UP)).toString();

		if(name.equals("p1mmhg"))
			return (new BigDecimal((double)p1*760/101325).setScale(0, BigDecimal.ROUND_HALF_UP)).toString();
			 
		if(name.equals("v1"))
			return ""+v1;
		
		if(name.equals("v2"))
			return ""+v2;

		if(name.equals("p2kpa")) 
			return (new BigDecimal(p2/1000).setScale(2,BigDecimal.ROUND_HALF_UP)).toString();

		if(name.equals("p2mmhg")){
			double tempP=p2*760/101325;

			if (tempP>=1000)
				return (new Multiplication(new DecimalNumber(tempP/1000, 2), 
					new Power(new IntegerNumber(10),new IntegerNumber(3)))).toString();
			else		
			  return (new BigDecimal(tempP).setScale(0, BigDecimal.ROUND_HALF_UP)).toString();
		}
		if(name.equals("punit"))
			return Character.toString(punit);
			
		return super.getSection(name);
	}
	
// Generates the following :
// p1, p2, v1, v2
 public void generate() {
 	            int mind=(int)((maxVolume-minVolume)/10); //minimum difference - 10 percent, unless specified differently
 	            if (mind==0) mind=1;
					p1=RandomChoice.randInt(minPressurePa,maxPressurePa);
				v1=RandomChoice.randInt(minVolume,maxVolume);		 
				if ((v1-mind)<minVolume)
					v2=RandomChoice.randInt(v1+mind,maxVolume);
				else 
				if ((v1+mind)>maxVolume)
					v2=RandomChoice.randInt(minVolume,v1-mind);
				else 			
				if (RandomChoice.randSign()==1)	
					v2=RandomChoice.randInt(v1+mind, maxVolume);
				else
					v2=RandomChoice.randInt(minVolume, v1-mind);
				p2=(double)p1*v1/v2;		
						
	}//generate 
} 
