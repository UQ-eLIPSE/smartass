/* @(#)InequalityToIntervalModule.java
 *
 * This file is part of SmartAss and describes class HCFOfManyModule for 
 * question "Find highest common factor of several numbers".
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
* Class HCFOfManyModule describes the following task:
* "Find highest common factor of several numbers."   
*
* @version 1.0 21.12.2006
*/
public class HCFOfManyModule extends MathsModule{
 private int[] numbers;
 int res;
 
/**
* Constructor HCFOfManyModule initialises the question.
* All generated numbers are different and biased against coprime numbers  .
* 
*/
 public HCFOfManyModule(Engine engine) 
	{
				super(engine);
				this.generate("no parameters");
			
	} //constructor
/**
 * getSection method typesets a question and solution 
 * for HCFOfManyModule.
 * This class does not have a specified shortanswer.
 * @param  name 	section name
 * @return a String containing Latex code for the section
 * @see    HCFModule
 */
 public String getSection(String name) {
		if(name.equals("question")) {
			String question=String.valueOf(numbers[0]);
			for (int i=1; i<numbers.length; i++)
			     if (i==numbers.length-1)	
			     	question+=" and "+numbers[i];
			     	else question+=", "+numbers[i];
			return question;
			}
		if(name.equals("hcf"))
			return String.valueOf(res);
		if(name.equals("oneoverhcf"))
			if (res==1) return ("1 minute"); 
				else return "\\ensuremath{"+(new FractionOp (new IntegerNumber(1), new IntegerNumber(res))).toString()+"} of a minute";	
		if(name.equals("seconds")) {	
			String seconds="";	
		    if ((60%res)==0) return String.valueOf(60/res);
		    else{
		    	 if ((600%res)!=0) seconds="around ";
		    	 seconds+=(new BigDecimal(60.0/res)).setScale(1,RoundingMode.HALF_EVEN).toString();
		     }
		    return seconds; 		
		}    	
		if (name.equals("number"))
			return String.valueOf(numbers.length);
		if (name.equals("number_in_words"))
			return IntegerIntoWords.convert(numbers.length);						
		return super.getSection(name);
	}
	
// numbers are different , biased agains coprime numbers
 public void generate(String params) {
 	            int sizeOfNumberArray=RandomChoice.randInt(2,4);
 	            numbers=new int[sizeOfNumberArray];
				for (int i=0; i<8; i++) { // introducing bias
				for (int k=0; k<sizeOfNumberArray; k++)
				 do numbers[k]=RandomChoice.randInt(1,60);	
				 while ((k>0) && (numbers[k]==numbers[k-1]));		
					res=HCFModule.hcf(numbers);
					if (!(res==1)) i=8; 
				}
								
	}//generate
 
} 
