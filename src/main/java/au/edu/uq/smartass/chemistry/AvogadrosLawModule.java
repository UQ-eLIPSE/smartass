/* @(#)AvogadrosLawModule.java
 *
 * This file is part of SmartAss and describes class AvogadrosLawModule for 
 * question on Avogadro's law. Given  V1(volume) and n1(number of moles), calculate the volume 
 * V2 of a new gas, if we know the proportion n1/n2 (this proportion passed into module as
 * parameters).
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
* Class AvogadrosLawModule describes the question on 
* Avogadro's law: 
* Given  V1(volume) and n1(number of moles), calculate the volume 
* V2 of a new gas, if we know that m1 molecules of the first gas 
* produces m2 molecules of the second (m1 and m2 passed into modules as 
* parameters).  
*
* @version 1.0 15.01.2007
*/
public class AvogadrosLawModule extends MathsModule{
 
 //private final final MIN_PRESSURE=0.5;
 //private final final MAX_PRESSURE=2.5;
 private final String MIN_V="5.0";
 private final String MAX_V="20.0";
 private final String MIN_N="0.1";
 private final String MAX_N="10.0";
  
 private String pressure=(new BigDecimal(0.5*RandomChoice.randInt(1,5)).setScale(1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros()).toPlainString();  //pressure in atm 
 private int m1, m2;
 private double v1, n1;
 private char punit=RandomChoice.makeChoice("[m]/5;[p]/5")[0].charAt(0); //m - mmHg, k - kPa
 
/**
* Constructor AvogadrosLawModule initialises the question
* with parameters passing.
* In case of 5 parameters (everything set by passing parameters):
* @params  params[0] - gas pressure,
*          parmas[1] - v1 - volume of the first gas,
*          params[2] - n1 - number of moles of the first gas, 
*          params[3] - m1 - number of molecules in the formula for the first gas,
*		   params[4] - m2 - number of molecules in the formula for the second gas,
* i.e. for oxigen into ozone 3O2->2O3, m1==3, m2==2
* or
* @params  params[0] - m1 - number of molecules in the formula for the first gas,
*		   params[1] - m2 - number of molecules in the formula for the second gas,
* i.e. for oxigen into ozone 3O2->2O3, m1==3, m2==2
*/
 public AvogadrosLawModule(Engine engine, String[] params) 
	{
				super(engine);		
				try{
			    if (params.length==5){
			    	pressure=params[0];
			    	v1=Double.parseDouble(params[1]);
			    	n1=Double.parseDouble(params[2]);
					m1=Integer.parseInt(params[3]); 
					m2=Integer.parseInt(params[4]); 
			    } else{
			    	if (params.length==2){ 
			    	   m1=Integer.parseInt(params[0]); 
					   m2=Integer.parseInt(params[1]); 	   
			      	   generate();
			    	} 
			      
				}	 
				}	catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into AvogadrosLawModule");
					throw e;
				}
	} //constructor
 
	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
		if(name.equals("pressure")) 
			return pressure;
		if(name.equals("v1"))
			return (new BigDecimal(v1).setScale(1, BigDecimal.ROUND_HALF_UP).stripTrailingZeros()).toPlainString();
		if(name.equals("v2"))
			return (new BigDecimal(v1*m2/m1).setScale(1, BigDecimal.ROUND_HALF_UP)).toString();
		if(name.equals("n1"))
			return (new BigDecimal(n1).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();
		if(name.equals("n2"))
			return (new BigDecimal(n1*m2/m1).setScale(2, BigDecimal.ROUND_HALF_UP)).toString();	
		if(name.equals("m1"))	
			return Integer.toString(m1);
		if(name.equals("m2"))	
			return Integer.toString(m2);			
		return super.getSection(name);
	}
	
// Generates the following :
// v1, n1
 public void generate() {
 	            v1=Double.parseDouble(RandomChoice.makeChoice("["+MIN_V+".."+MAX_V+"]/1")[0]);
 	            n1=Double.parseDouble(RandomChoice.makeChoice("["+MIN_N+".."+MAX_N+"]/1")[0]); 		
	}//generate 
} 
