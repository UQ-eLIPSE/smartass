/* @(#) RadiansDegreesConvertModule.java
 *
 * This file is part of SmartAss and describes class RadiansDegreesConvertModule.
 * Convert the angle from radians (degrees) to degrees (radians).
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
* Class RadiansDegreesConvertModule describes the question:  
* Convert the angle from radians (degrees) to degrees(radians).
*
* @version 1.0 20.06.2007
*/
public class RadiansDegreesConvertModule extends MathsModule{

	private final Variable pi = new Variable("\\pi");

	MathsOp radians;
	MathsOp degrees;  
 
/**
* Constructor RadiansDegreesConvertModule initialises the question.
* 
* @param engine
*/
 public RadiansDegreesConvertModule(Engine engine) {
				super(engine);	
				generate();				
	} //constructor

 

/**
 * getSection method typesets a question and solution 
 * @return a String containing Latex code for the section
 */
 public String getSection(String name) {
 									 	
 		if(name.equals("radians")) 
			return radians.toString();											 												
 		if(name.equals("degrees")) 		
			return degrees.toString();		
		return super.getSection(name);
	}
		
/*
 * Method generates numbers for degrees and corresponding
 * MathsOp for radians.
 *
 */
 
 private void generate() {
                int num=0, denom=0, deg=0;
                for (int i=0; i<2; i++){ //bias
 	            num=RandomChoice.randInt(-24,24);
 	            denom=Integer.valueOf(RandomChoice.makeChoice("[1..6]/6;[9..10]/2;[12]/1;[15]/1;[18]/1;[20]/1")[0]);
 	            //num/denom pi - value in radians
 	            deg=num*180/denom;
 	             if ((Math.abs(deg)<500) || ((num%denom)==0)) i=2;
                }
                degrees= new IntegerNumber(deg);
                
 	            if ((num%denom)==0)
 	             radians=MathsUtils.multiplyVarToConst((num/denom), pi);
 	            else {
 	                
 	            	    int resnum, resdenom, hcf1;
						hcf1=HCFModule.hcf(num,denom);		
	  					resnum=Math.abs(num)/hcf1;
	  					resdenom=denom/hcf1;
						radians=new FractionOp(MathsUtils.multiplyVarToConst(resnum, pi), new IntegerNumber(resdenom));
						if (num<0)
							radians=new UnaryMinus(radians);						
				}		 
	}//generate
 } 
