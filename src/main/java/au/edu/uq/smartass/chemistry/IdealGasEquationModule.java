/* @(#)IdealGasEquationModule.java
 *
 * This file is part of SmartAss and describes class IdealGasEquationModule for 
 * question on ideal gas equation. Given  V1(volume) , T1 (temperature) and P1(pressure), 
 * calculate the new volume V2 of the same gas, if we know T2 and P2 (new temperature and 
 * new pressure).
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
* Class IdealGasEquationModule describes the question on 
* ideal gas equation P1V1/T1=P2V2/T2: 
* Given  V1(volume), T1(pressure) and P1, find the new volume V2 
* if we know new temperature T2 and new pressure P2.   
*
* @version 1.0 15.01.2007
*/
public class IdealGasEquationModule extends MathsModule{
 
 private String MIN_P="20.0"; 
 private String MAX_P="80.0";
 private String MIN_V="5.0"; // x 10^-3 c.m.
 private String MAX_V="20.0";
 private String MIN_T="250"; //temperature in K
 private String MAX_T="350"; 
  
 //private String p1=(new BigDecimal(0.5*RandomChoice.randInt(1,5)).setScale(1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros()).toString();  //pressure in atm 
 private char pvt=RandomChoice.makeChoice("[p]/3;[v]/3;[t]/3")[0].charAt(0); //which one we need to find? the rest of parameters are changing
 //for instance if pvt=='v', P and T are changing conditions, find new volume V2
 private int t1, t2;
 private double v1, v2, p1, p2;
 
/**
* Constructor IdealGasEquationModule initialises the question
* with parameters passing.
* In case of 7 parameters (limits are set by passing parameters):
* @params  params[0] - pvt, if "p" find pressure, "v" - find volume, "t" - find temperature
* 		   params[1] - MIN_P - minimum pressure,
*          params[2] - MAX_P - maximum pressure,
*          params[3] - MIN_V - minimum volume,
*          params[4] - MAX_V - maximum volume,
*          params[5] - MIN_T - minimum temperature in K,
*          params[6] - MAX_T - maximum temperature	   
*
* or In case of 6 parameters (everything is set by passing parameters):
* @params  params[0] - pvt, if "p" find pressure, "v" - find volume, "t" - find temperature
*		   params[1] - p1 - initial pressure,
*		   params[2] - v1 - initial volume,
*          params[3] - t1 - initial temperature in K,
*          params[4] - p2 or v2 (depending on pvt passed),
*          params[5] - v2 or t2 (depending on pvt passed).
*/
 public IdealGasEquationModule(Engine engine, String[] params) 
	{
				super(engine);		
				try{
			    if (params.length==7){
			    	pvt=params[0].charAt(0);
			    	MIN_P=params[1];
			    	MAX_P=params[2];
			    	MIN_V=params[3];
			    	MAX_V=params[4];
			    	MIN_T=params[5];
			    	MAX_T=params[6];
			    	generate();
			    } else
			    	if (params.length==6){ 
			    	   pvt=params[0].charAt(0);
			    	   p1=Double.parseDouble(params[1]);
			    	   v1=Double.parseDouble(params[2]);
			    	   t1=Integer.parseInt(params[3]); 
					   switch(pvt){
					   	case 'p':
					   		v2=Double.parseDouble(params[4]);
					   		t2=Integer.parseInt(params[5]);
					   		p2=p1*v1/t1*t2/v2;
					   		break;
					   	case 'v':
					   		p2=Double.parseDouble(params[4]);
					   		t2=Integer.parseInt(params[5]);
					   		v2=p1*v1/t1*t2/p2;
					   		break;
					   	case 't':
					   		p2=Double.parseDouble(params[4]);
					   		v2=Double.parseDouble(params[5]);
					   		t2=(int)(p2*v2/p1/v1*t1);
					   		break;
					   } //end of switch		
					   }
			    	 else 
			    	throw new IllegalArgumentException();  
				}		
				catch (IllegalArgumentException e){
					System.out.println("IllegalArgumentException while processing parameters passed into BoylesLawModule");
					throw e;
				}
	} //constructor
 
/**
* Constructor IdealGasEquationModule initialises the question
* @params  Engine
*/
 public IdealGasEquationModule(Engine engine) 
	{
				super(engine);		
			generate();
	} //constructor	
/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
		if(name.equals("pvt")) 
			return Character.toString(pvt);
		if(name.equals("p1"))
			return (new BigDecimal(p1).setScale(1, BigDecimal.ROUND_HALF_UP)).toString();	
		if(name.equals("p2"))
			return (new BigDecimal(p2).setScale(1, BigDecimal.ROUND_HALF_UP)).toString();			
		if(name.equals("v1")){
			if (v1<1000)
				return (new Multiplication(new DecimalNumber(v1, 2), 
					new Power(new IntegerNumber(10),new IntegerNumber(-3)))).toString();
			else		
			  return (new BigDecimal(v1/1000).setScale(1, BigDecimal.ROUND_HALF_UP)).toString();
		}	  
		if(name.equals("v2")){
			if (v2<1000)
				return (new Multiplication(new DecimalNumber(v2, 2), 
					new Power(new IntegerNumber(10),new IntegerNumber(-3)))).toString();
			else		
			  return (new BigDecimal(v2/1000).setScale(1, BigDecimal.ROUND_HALF_UP)).toString();
		}	  			
		if(name.equals("t1k"))
			return Integer.toString(t1);
		if(name.equals("t2k"))
			return Integer.toString(t2);
		if(name.equals("t1c"))
			return Integer.toString(t1-273);
		if(name.equals("t2c"))
			return Integer.toString(t2-273);		
		return super.getSection(name);
	}
	
// Generates the following :
// p1, v1, t1, p2, v2, t2
 public void generate() {
 	            p1=Double.parseDouble(RandomChoice.makeChoice("["+MIN_P+".."+MAX_P+"]/1")[0]); 	
 	            v1=Double.parseDouble(RandomChoice.makeChoice("["+MIN_V+".."+MAX_V+"]/1")[0]);		
 	            t1=Integer.parseInt(RandomChoice.makeChoice("["+MIN_T+".."+MAX_T+"]/1")[0]);
 	            do{
				t2=Integer.parseInt(RandomChoice.makeChoice("["+MIN_T+".."+MAX_T+"]/1")[0]);
				p2=Double.parseDouble(RandomChoice.makeChoice("["+MIN_P+".."+MAX_P+"]/1")[0]); 	
 	            } while (((int)p2==(int)p1) && (t2==t1));
 	            v2=p1*v1/t1*t2/p2;
	}//generate 
} 
