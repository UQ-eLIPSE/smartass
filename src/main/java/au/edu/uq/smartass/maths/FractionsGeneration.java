/**
 * @(#)FractionsGeneration.java
 *
 * This file is part of SmartAss and describes class FractionGeneration for 
 * the generation of fractions to use in SimplifyFractionModule. 
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
 * @version 1.00 2007/3/13
 */
package au.edu.uq.smartass.maths;

import java.util.Vector;

import au.edu.uq.smartass.auxiliary.RandomChoice;

/**
* Class FractionGeneration contains static methods for generating "good" fractions for 
* SimplifyFractionModule.   
*
* @version 1.0 13.03.2007
*/
public class FractionsGeneration {

 // final static int BIAS set to 2 and controls a level of BIAS against "not good looking" fractions,
 // for instance, where numerator == 0, or denominator == 1 etc.
 final static int BIAS=2;

/**
 * generateSecondAddend generates an addend (numerator and denominator) for
 * addition of two fractions. 
 * First addend fraction is given in the parameters (numerator1, denominator1). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the resulting 
 * and initial fractions are also given as parameters.
 * @param  numerator1		numerator of the first fraction addend
 * @param  denominator1		denominator of the first fraction addend
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in resulting and initial fractions 
 * @return int[]            element 0 - numerator of the second addend; 
 *							element 1 - denominator 
 */
 public static int[] generateSecondAddend (int numerator1, int  denominator1, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num, denom, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num=(startN+k)%(2*resultLimit+1)-resultLimit;
 			    hcf1=HCFModule.hcf(denominator1, denom);
 			    tempDenom=denominator1*denom/hcf1;
 			    if (!(Math.abs(tempDenom)>middleLimit)) {
 			    	temp1=numerator1*denom/hcf1;
 			    	temp2=num*denominator1/hcf1;
 			        tempNum=temp1+temp2;
				if (Math.abs(tempNum)<=middleLimit && Math.abs(temp1)<=middleLimit && Math.abs(temp2)<=middleLimit) {  
						hcf1=HCFModule.hcf(tempNum,tempDenom);		
	  	  	            tempNum/=hcf1;
	  		            tempDenom/=hcf1;
	  		        if ((Math.abs(tempNum)<=resultLimit) && (Math.abs(tempDenom)<=resultLimit)){
	  		          fract=new int[2];
	  		          fract[0]=num;
	  		          fract[1]=denom;
	  		         if (((num!=0) && (Math.abs(denom)!=1) && (Math.abs(num)!=Math.abs(denom))) || (stop>=BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;
	  		        
	  		        }
				}  	 			    	
 			    }
 			}
 		}
 			
 	}	
 	} while (stop<BIAS+3);
 	
  return fract; 
 }  
 
 
 /**
 * generateSubtrahend generates a subtahend (numerator and denominator) for
 * the operation of subtaction on two fractions. 
 * Minuend fraction is given in the parameters (numerator1, denominator1). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the resulting 
 * and initial fractions are also given as parameters.
 * @param  numerator1		numerator of the first fraction - minuend
 * @param  denominator1		denominator of the first fraction - minuend
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in resulting and initial fractions 
 * @return int[]            element 0 - numerator of the subtrahend; element 1 - denominator 
 */	
 public static int[] generateSubtrahend (int numerator1, int  denominator1, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num, denom, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num=(startN+k)%(2*resultLimit+1)-resultLimit;
 			    hcf1=HCFModule.hcf(denominator1, denom);
 			    tempDenom=denominator1*denom/hcf1;
 			    if (!(Math.abs(tempDenom)>middleLimit)) {
 			    	temp1=numerator1*denom/hcf1;
 			    	temp2=num*denominator1/hcf1;
 			        tempNum=temp1-temp2;
				if (Math.abs(tempNum)<=middleLimit && Math.abs(temp1)<=middleLimit && Math.abs(temp2)<=middleLimit) {  
						hcf1=HCFModule.hcf(tempNum,tempDenom);		
	  	  	            tempNum/=hcf1;
	  		            tempDenom/=hcf1;
	  		        if ((Math.abs(tempNum)<=resultLimit) && (Math.abs(tempDenom)<=resultLimit)){
	  		          fract=new int[2];
	  		          fract[0]=num;
	  		          fract[1]=denom;
	  		         if (((num!=0) && (Math.abs(denom)!=1) && (Math.abs(num)!=Math.abs(denom))) || (stop>=BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;
	  		        
	  		        }
				}  	 			    	
 			    }
 			}
 		}
 			
 	}	
 	} while (stop<BIAS+3);
 	
  return fract; 
 }  	
 
 
 /**
 * generateMultiplier generates a multiplier (numerator and denominator) for
 * the operation of multiplication on two fractions. 
 * Multiplicand fraction is given in the parameters (numerator1, denominator1). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the resulting 
 * and initial fractions are also given as parameters.
 * @param  numerator1		numerator of the multiplicand fraction 
 * @param  denominator1		denominator of the multiplicand fraction
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in resulting and initial fractions 
 * @return int[]            element 0 - numerator of the multiplier; element 1 - denominator 
 */	
 public static int[] generateMultiplier (int numerator1, int  denominator1, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num, denom, stop;
 	int tempNum, tempDenom, hcf1;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num=(startN+k)%(2*resultLimit+1)-resultLimit;
 			    hcf1=HCFModule.hcf(num*numerator1,denominator1*denom); 			    
 			    tempDenom=denominator1*denom/hcf1;
 			    tempNum=num*numerator1/hcf1;
				 if (Math.abs(tempNum)<=resultLimit && Math.abs(tempDenom)<=resultLimit) {  
	  		          fract=new int[2];
	  		          fract[0]=num;
	  		          fract[1]=denom;
	  		         if (((num!=0) && (Math.abs(denom)!=1) && (Math.abs(num)!=Math.abs(denom))) || (stop>=BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;
	  		        
	  		        }
				}	    	
 			    }
 			}
 		
 	} while (stop<BIAS+3);
 	
  return fract; //new FractionOp(new IntegerNumber(0), new IntegerNumber(0));
 }  
 
 
 /**
 * generateDivisor generates a divisor (numerator and denominator) for
 * the operation of division on two fractions. 
 * Dividend fraction is given in the parameters (numerator1, denominator1). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the resulting 
 * and initial fractions are also given as parameters.
 * @param  numerator1		numerator of the dividend fraction 
 * @param  denominator1		denominator of the dividend fraction
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in resulting and initial fractions 
 * @return int[]            element 0 - numerator of the divisor; element 1 - denominator 
 */	
 public static int[] generateDivisor (int numerator1, int  denominator1, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num, denom, stop;
 	int tempNum, tempDenom, hcf1;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num=(startN+k)%(2*resultLimit+1)-resultLimit;
 				if (num!=0) {
 			    hcf1=HCFModule.hcf(denom*numerator1,denominator1*num); 			    
 			    tempDenom=denominator1*num/hcf1;
 			    tempNum=denom*numerator1/hcf1;
				 if (Math.abs(tempNum)<=resultLimit && Math.abs(tempDenom)<=resultLimit) {  
	  		          fract=new int[2];
	  		          fract[0]=num;
	  		          fract[1]=denom;
	  		         if (((num!=0) && (Math.abs(denom)!=1) && (Math.abs(num)!=Math.abs(denom))) || (stop>=BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;
				 } 
	  		     }
				}	    	
 			    }
 			}
 		
 	} while (stop<BIAS+3);
 	
  return fract; //new FractionOp(new IntegerNumber(0), new IntegerNumber(0));
 }  	
 	
/**
 * generateFractionsToAdd generates two addends (four integer numbers in array) for
 * addition of two fractions, where the required result is given. 
 * Required resulting fraction is given in the parameters (resultNumerator, resultDenominator). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the addends. 
 * @param  resultNumerator		numerator of the required result
 * @param  resultDenominator	denominator of the required result
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in the addends 
 * @return int[]            element 0 - numerator of the first addend,
 *							element 1 - denominator of the first addend,
 *							element 2 - numerator of the second addend,
 *							element 3 - denominator of the second addend
 */ 	

 public static int[] generateFractionsToAdd (int resultNumerator, int  resultDenominator, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num1, denom1, num2, denom2, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom1=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom1!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num1=(startN+k)%(2*resultLimit+1)-resultLimit; 				
 			    hcf1=HCFModule.hcf(resultDenominator, denom1);
 			    tempDenom=resultDenominator*denom1/hcf1;
				tempNum=(resultNumerator*denom1-num1*resultDenominator)/hcf1;
 			    if ((Math.abs(tempDenom)<=resultLimit)&&(Math.abs(tempNum)<=resultLimit)){
 			    	if (tempNum!=0) 
 			    	{temp1=Math.abs(resultLimit/tempNum); 
 			    	temp2=Math.abs(resultLimit/tempDenom);
 			    	temp1= (temp1<temp2) ? temp1 : temp2;
 			    	} else 
 			    	temp1=Math.abs(resultLimit/tempDenom); 			    	
 			    	temp1=RandomChoice.randInt(1,temp1);
 			    	num2=tempNum*temp1;
 			    	denom2=tempDenom*temp1;
 			    	hcf1=HCFModule.hcf(denom1,denom2);
 			    	tempDenom=denom1*denom2/hcf1;
 			    	if (!(Math.abs(tempDenom)>middleLimit)) {
 			    	temp1=num1*denom2/hcf1;
 			    	temp2=num2*denom1/hcf1;
 			    	tempNum=temp1+temp2;
 			    	if (Math.abs(tempNum)<=middleLimit && Math.abs(temp1)<=middleLimit && Math.abs(temp2)<=middleLimit) {  
					   fract=new int[4];
					   fract[0]=num1;
					   fract[1]=denom1;
					   fract[2]=num2;
					   fract[3]=denom2;
					if (((num1!=0) && (Math.abs(denom1)!=1) && (Math.abs(num1)!=Math.abs(denom1)) && (num2!=0) &&
						(Math.abs(denom2)!=1) && (Math.abs(num2)!=Math.abs(denom2)) && (Math.abs(denom2)!=(Math.abs(denom1)) )) || (stop>=(20*BIAS))) 
	  		        	return fract;
	  		         else 	
	  		            stop++;   
 			    }
 			    }
 			    }	
 			}
 		}
 	}	
 	} while (stop<20*BIAS+3);
  return fract; 
 }  	
 	
/**
 * generateFractionsToSubtract generates two fractions - minuend
 * and subtrahend (return in four integer numbers in array) for
 * subtraction of two fractions, where the required difference is given. 
 * Required difference fraction is given in the parameters (resultNumerator, resultDenominator). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the addends. 
 * @param  resultNumerator		numerator of the required result
 * @param  resultDenominator	denominator of the required result
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in the minuend and subtrahend
 * @return int[]            element 0 - numerator of the minuend,
 *							element 1 - denominator of the minuend,
 *							element 2 - numerator of the subtrahend,
 *							element 3 - denominator of the subtrahend
 */ 	 	
 public static int[] generateFractionsToSubtract (int resultNumerator, int  resultDenominator, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num1, denom1, num2, denom2, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom1=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom1!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num1=(startN+k)%(2*resultLimit+1)-resultLimit; 				
 			    hcf1=HCFModule.hcf(resultDenominator, denom1);
 			    tempDenom=resultDenominator*denom1/hcf1;
				tempNum=(resultNumerator*denom1-num1*resultDenominator)/hcf1;
 			    if ((Math.abs(tempDenom)<=resultLimit)&&(Math.abs(tempNum)<=resultLimit)){
 			    	if (tempNum!=0) 
 			    	{temp1=Math.abs(resultLimit/tempNum); 
 			    	temp2=Math.abs(resultLimit/tempDenom);
 			    	temp1= (temp1<temp2) ? temp1 : temp2;
 			    	} else 
 			    	temp1=Math.abs(resultLimit/tempDenom); 			    	
 			    	temp1=RandomChoice.randInt(1,temp1);
 			    	num2=tempNum*temp1;
 			    	denom2=tempDenom*temp1;
 			    	hcf1=HCFModule.hcf(denom1,denom2);
 			    	tempDenom=denom1*denom2/hcf1;
 			    	if (!(Math.abs(tempDenom)>middleLimit)) {
 			    	temp1=num1*denom2/hcf1;
 			    	temp2=num2*denom1/hcf1;
 			    	tempNum=temp1+temp2;
 			    	if (Math.abs(tempNum)<=middleLimit && Math.abs(temp1)<=middleLimit && Math.abs(temp2)<=middleLimit) {  
					   fract=new int[4];
					   fract[0]=num1;
					   fract[1]=denom1;
					   fract[2]=-num2;
					   fract[3]=denom2;
					if (((num1!=0) && (Math.abs(denom1)!=1) && (Math.abs(num1)!=Math.abs(denom1)) && (num2!=0) &&
						(Math.abs(denom2)!=1) && (Math.abs(num2)!=Math.abs(denom2)) && (denom1!=denom2) ) || (stop>=(20*BIAS))) 
	  		        	return fract;
	  		         else 	
	  		            stop++;   
 			    }
 			    }
 			    }	
 			}
 		}
 	}	
 	} while (stop<20*BIAS+3);
  return fract; 
 }  	    
 	
/**
 * generateFractionsToMultiply generates two fractions - multiplicand
 * and multiplier (returning four integer numbers in array) for
 * multiplication of two fractions, where the required product is given. 
 * Required product fraction is given in the parameters (resultNumerator, resultDenominator). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the addends. 
 * @param  resultNumerator		numerator of the required result
 * @param  resultDenominator	denominator of the required result
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in the multiplicand and multiplier 
 * @return int[]            element 0 - numerator of the multiplicand,
 *							element 1 - denominator of the multiplicand,
 *							element 2 - numerator of the multiplier,
 *							element 3 - denominator of the multiplier
 */ 	     
public static int[] generateFractionsToMultiply (int resultNumerator, int  resultDenominator, int middleLimit, int resultLimit) {
 	int startN;
 	int startD;
 	int num1, denom1, num2, denom2, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	if (resultNumerator==0) { 	
 	  fract=new int[4];
 	  if (RandomChoice.randInt(0,1)==0){
 	  	fract[0]=0;
 	  	fract[2]=RandomChoice.randInt(-resultLimit,resultLimit);
 	  } else {
 	  	fract[0]=RandomChoice.randInt(-resultLimit, resultLimit);
 	  	fract[2]=0; 	  	
 	  }
 	  
 	  fract[1]=Integer.valueOf(RandomChoice.makeChoice("[-"+resultLimit+"..0)/1;(0.."+resultLimit+"]/9")[0]);
 	  fract[3]=Integer.valueOf(RandomChoice.makeChoice("[-"+resultLimit+"..0)/1;(0.."+resultLimit+"]/9")[0]);
	 return fract; 	  	
 	}
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom1=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom1!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num1=(startN+k)%(2*resultLimit+1)-resultLimit; 
 				if (num1!=0) {	
 			   		 hcf1=HCFModule.hcf(denom1*resultNumerator,resultDenominator*num1); 			    
 			    	 tempDenom=resultDenominator*num1/hcf1;
 			   		 tempNum=denom1*resultNumerator/hcf1;	  
 			   	if ((Math.abs(tempDenom)<=resultLimit)&&(Math.abs(tempNum)<=resultLimit)){ 
 			    	temp1=Math.abs(resultLimit/tempNum); 
 			    	temp2=Math.abs(resultLimit/tempDenom);
 			    	temp1= (temp1<temp2) ? temp1 : temp2;  					    				    			    	
 			    	temp1=RandomChoice.randInt(1,temp1);
 			    	num2=tempNum*temp1;
 			    	denom2=tempDenom*temp1;	 
 			    	fract=new int[4];	
 			    	fract[0]=num1;
 			    	fract[1]=denom1;
 			    	fract[2]=num2;
 			    	fract[3]=denom2; 			     			   		 
	  		        if (((Math.abs(denom1)!=1) && (Math.abs(num1)!=Math.abs(denom1)) && (Math.abs(num2)!=Math.abs(denom2)) &&
	  		        	(Math.abs(denom2)!=1)) || (stop>=20*BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;	
 			   	}
 				}
 			}
 		}
 	}	
 	} while (stop<20*BIAS+3);
  return fract; 
 }
 
/**
 * generateFractionsToDivide generates two fractions - dividend
 * and divisor (returning four integer numbers in array) for
 * division of two fractions, where the required quotient is given. 
 * Required quotient fraction is given in the parameters (resultNumerator, resultDenominator). 
 * The maximum absolute value for the integers in the intermediate calculations and 
 * the maximum absolute value for the integers allowed in the addends. 
 * @param  resultNumerator		numerator of the required result
 * @param  resultDenominator	denominator of the required result
 * @param  middleLimit		maximum allowed abs. value in the intermediate computations
 * @param  resultLimit		maximum allowed abs. value in the multiplicand and multiplier 
 * @return int[]            element 0 - numerator of the dividend,
 *							element 1 - denominator of the dividend,
 *							element 2 - numerator of the divisor,
 *							element 3 - denominator of the divisor
 */ 	      
public static int[] generateFractionsToDivide (int resultNumerator, int  resultDenominator, int middleLimit, int resultLimit) {   	
 int startN;
 	int startD;
 	int num1, denom1, num2, denom2, stop;
 	int tempNum, tempDenom, hcf1, temp1, temp2;
 	int[] fract=null;
 	if (resultNumerator==0) { 	
 	  fract=new int[4];
 	  fract[0]=0;
 	  fract[2]=Integer.valueOf(RandomChoice.makeChoice("[-"+resultLimit+"..0)/1;(0.."+resultLimit+"]/9")[0]);
 	  fract[1]=Integer.valueOf(RandomChoice.makeChoice("[-"+resultLimit+"..0)/1;(0.."+resultLimit+"]/9")[0]);
 	  fract[3]=Integer.valueOf(RandomChoice.makeChoice("[-"+resultLimit+"..0)/1;(0.."+resultLimit+"]/9")[0]);
	 return fract; 	  	
 	}
 	stop=0;
 	do {
 	startN=RandomChoice.randInt(0,2*resultLimit);
 	startD=RandomChoice.randInt(0,2*resultLimit);
 	for (int i=0; i<=2*resultLimit; i++){
 		denom1=(startD+i)%(2*resultLimit+1)-resultLimit;
 		if (denom1!=0){
 			for (int k=0; k<=2*resultLimit; k++) {
 				num1=(startN+k)%(2*resultLimit+1)-resultLimit; 
 				if (num1!=0) {	
 			   		 hcf1=HCFModule.hcf(denom1*resultNumerator,resultDenominator*num1); 			    
 			    	 tempDenom=resultDenominator*num1/hcf1;
 			   		 tempNum=denom1*resultNumerator/hcf1;	  
 			   	if ((Math.abs(tempDenom)<=resultLimit)&&(Math.abs(tempNum)<=resultLimit)){ 
 			    	temp1=Math.abs(resultLimit/tempNum); 
 			    	temp2=Math.abs(resultLimit/tempDenom);
 			    	temp1= (temp1<temp2) ? temp1 : temp2;  					    				    			    	
 			    	temp1=RandomChoice.randInt(1,temp1);
 			    	num2=tempNum*temp1;
 			    	denom2=tempDenom*temp1;	 
 			    	fract=new int[4];	
 			    	fract[0]=num1;
 			    	fract[1]=denom1;
 			    	fract[2]=denom2;
 			    	fract[3]=num2;     			   		 
	  		        if (((Math.abs(denom1)!=1) && (Math.abs(num1)!=Math.abs(denom1)) && (Math.abs(num2)!=Math.abs(denom2)) &&
	  		        	(Math.abs(denom2)!=1)) || (stop>=20*BIAS))    
	  		        	return fract;
	  		         else 	
	  		            stop++;	
 			   	}
 				}
 			}
 		}
 	}	
 	} while (stop<20*BIAS+3);
  return fract; 
 }
}        
    
  