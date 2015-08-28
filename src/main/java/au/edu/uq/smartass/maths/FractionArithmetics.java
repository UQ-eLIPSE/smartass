/*
 * @(#)FractionArithmetics.java
 * This file is part of SmartAss and describes class FractionArithmetics for 
 * arithmetical operations on fractions. 
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
 *
 * @version 1.00 2007/1/26
 */
package au.edu.uq.smartass.maths;
import java.util.*;
import java.math.*;


/**
* Class FractionArithmetics contains methods used in arithmetical 
* operations on fractions.   
*
* @version 1.0 26.01.2007
*/
public class FractionArithmetics {

    
 /**
 * presentableFraction typesets a common fraction in the most presentable way
 * i.e.  improper fraction methods typesets as a mixed number. 
 * takes minus to the front. If fraction has not been modified - returns ""
 * Note - presentableFraction does not simplify the given fraction.
 * @param  fractionNumerator
 * @param  fractionDenominator
 * @return a String containing Latex code for the simplified fraction or empty
 * string if fraction was not modified
 */
 public static String presentableFraction(IntegerNumber fractionNumerator, IntegerNumber fractionDenominator) { 	
 	            IntegerNumber numerator=(IntegerNumber)fractionNumerator.clone();
 	            IntegerNumber denominator=(IntegerNumber)fractionDenominator.clone();
				FractionOp fraction=new FractionOp(numerator, denominator);
				String finalString="";
				int num=numerator.getInt();
				int denom=denominator.getInt();
				int whole;				
				if((num*denom)<0) { //minus  - in front				
					finalString="-";
					num=Math.abs(num);
					denom=Math.abs(denom);
					fraction.setOp(0,new IntegerNumber(num));
					fraction.setOp(1,new IntegerNumber(denom));					
				}
				if (((float)num/denom)>=1){ //generating mixed number if fraction is improper
				    whole=num/denom;
					finalString+=whole;
					if( (num%denom)!=0) {											
						num=num-whole*denom;
						fraction.setOp(0, new IntegerNumber(num));	
						finalString+=fraction.toString();	
					} 
				} else if ( finalString.startsWith("-")) finalString+=fraction.toString();															
			return finalString;								
	} //presentableFraction


 /**
 * multiplyFractions method multiplies two common fractions  .Method generates a solution and returns it in
 * Vector of MathOps. multiplyFractions does not simplify resulting fraction (@see presentableFraction).
 * The last element of resulting vector would be either IntegerNumber or FractionOp.
 * @param  fraction1_Numerator	numerator of first multiplier
 * @param  fraction1_Denominator denominator of first multiplier
 * @param  fraction2_Numerator   numerator of second multiplier
 * @param  fraction2_Denominator denominator of second multiplier
 * @return a Vector <MathsOp> containing steps of solution (from initial expression to the answer);
 * @throws ArithmeticException if one of the denominators is equal to 0.
 * 
 */	
public static Vector <MathsOp> multiplyFractions(IntegerNumber fraction1_Numerator, IntegerNumber fraction1_Denominator, 
											IntegerNumber fraction2_Numerator, IntegerNumber fraction2_Denominator) throws ArithmeticException {
	MathsOp numerator1=(MathsOp)fraction1_Numerator.clone();
	MathsOp denominator1=(MathsOp)fraction1_Denominator.clone();
	MathsOp numerator2=(MathsOp)fraction2_Numerator.clone();
	MathsOp denominator2=(MathsOp)fraction2_Denominator.clone();	
	Vector <MathsOp> working = new Vector<MathsOp> ();
	int num1=fraction1_Numerator.getInt();
	int denom1=fraction1_Denominator.getInt();
	int num2=fraction2_Numerator.getInt();
	int denom2=fraction2_Denominator.getInt();
	int remn1, remd1, remn2, remd2 , hcf1, temp;
	boolean typeFraction=true, foundEqual=false, denominatorsChanged=false;
	Vector <MathsOp> factorsN1, factorsN2, factorsD1, factorsD2;
	MathsOp N1,D1, N2,D2;		
	
	if ((denom2*denom1)==0)  throw new ArithmeticException("Division by zero");
	if((num1*num2)==0){ // one of the numerators == 0
	working.add(new Multiplication(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );
	if(num1==0) N1=new IntegerNumber(0);
		else N1=new FractionOp(numerator1,denominator1);
	if (num2==0) N2=new IntegerNumber(0);
		else N2=new FractionOp(numerator2,denominator2);
	working.add(new Multiplication(N1,N2));		
	working.add(new IntegerNumber(0));
	return working;	
	}  //if num==0

	// check if equal absolute values in numerators and denominators:
	if (! ( (Math.abs(num1)==1 && Math.abs(denom1)==1 ) || (Math.abs(num2)==1 && Math.abs(denom2)==1) 
		|| (Math.abs(num1)==1 && Math.abs(denom2)==1 ) || (Math.abs(num2)==1 && Math.abs(denom1)==1) ) )
		{			
	if (Math.abs(num1)==Math.abs(denom1)) {	 
	  numerator1= new CancelOp(new IntegerNumber(num1));
	  num1=Integer.signum(num1);		
	  denominator1=new CancelOp(new IntegerNumber(denom1));
	  denom1=Integer.signum(denom1);
	  foundEqual=true;
	} 
	if (Math.abs(num2)==Math.abs(denom2)) {	
	  numerator2=new CancelOp(new IntegerNumber(num2));
	  num2=Integer.signum(num2);
	  denominator2=new CancelOp(new IntegerNumber(denom2));
	  denom2=Integer.signum(denom2);
	  foundEqual=true;		
	} 							
	if ((Math.abs(num1)==Math.abs(denom2)) && (Math.abs(num1*denom2)!=1))  {
	  numerator1=new CancelOp(new IntegerNumber(num1));
	  num1=Integer.signum(num1);
	  denominator2=new CancelOp(new IntegerNumber(denom2));
	  denom2=Integer.signum(denom2);
	  foundEqual=true;	
	} 
	if ((Math.abs(num2)==Math.abs(denom1)) && (Math.abs(num2*denom1)!=1)) {
	  numerator2=new CancelOp(new IntegerNumber(num2));
	  num2=Integer.signum(num2);
	  denominator1=new CancelOp(new IntegerNumber(denom1));
	  denom1=Integer.signum(denom1);
	  foundEqual=true;		 		
	}  
	
	if (foundEqual) {
		working.add(new Multiplication(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );	
		
	
	if ((denom1<0) || (denom2<0)) { //change denominators to positive	 
//	 working.add(new Multiplication(new FractionOp(numerator1,denominator1)
//		                                  , new FractionOp(numerator2,denominator2))); 
	 num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 denom1=Math.abs(denom1);
	 num2=Integer.signum(num2*denom2)*Math.abs(num2);
	 denom2=Math.abs(denom2);
	 denominatorsChanged=true;
	 numerator1=new IntegerNumber(num1);
	 numerator2=new IntegerNumber(num2);
	 denominator1=new IntegerNumber(denom1);
	 denominator2=new IntegerNumber(denom2);
	}					
		working.add(new Multiplication(new FractionOp(new IntegerNumber(num1),new IntegerNumber(denom1)),
		 new FractionOp(new IntegerNumber(num2), new IntegerNumber(denom2)) ) );
	}
		else	{	//no equal numbers  

	if ((denom1<0) || (denom2<0)) { //change denominators to positive	 
	 working.add(new Multiplication(new FractionOp(numerator1,denominator1)
		                                  , new FractionOp(numerator2,denominator2))); 
	 num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 denom1=Math.abs(denom1);
	 num2=Integer.signum(num2*denom2)*Math.abs(num2);
	 denom2=Math.abs(denom2);
	 denominatorsChanged=true;
	 numerator1=new IntegerNumber(num1);
	 numerator2=new IntegerNumber(num2);
	 denominator1=new IntegerNumber(denom1);
	 denominator2=new IntegerNumber(denom2);
	}	
		
		
	   if (!denominatorsChanged) 
	   	 working.add(new Multiplication(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );	   
	   if (! Primes.areCoprime(num1*num2, denom1*denom2)) {

	   		factorsN1=new Vector<MathsOp>();
       		factorsD1=new Vector<MathsOp>();
      	 	factorsN2=new Vector<MathsOp>();
      	    factorsD2=new Vector<MathsOp>();

	  		hcf1=HCFModule.hcf(num1,denom1);	  			  		
	  		if (hcf1!=1) {
		  		remn1=num1/hcf1;
	  			remd1=denom1/hcf1;		  			
	  			factorsN1.add(new CancelOp(new IntegerNumber(hcf1)));
	  			factorsD1.add(new CancelOp(new IntegerNumber(hcf1)));
	  		} else {
	  			remn1=num1;
	  			remd1=denom1;
	  		}	 //if	  		
	  		hcf1=HCFModule.hcf(num2,denom2)	;	  	
	  	    if (hcf1!=1) {
	  	    	remn2=num2/hcf1;
	  	    	remd2=denom2/hcf1;	  	    	  	    		
	  	    	factorsN2.add(new CancelOp(new IntegerNumber(hcf1)));
	  	    	factorsD2.add(new CancelOp(new IntegerNumber(hcf1)));
	  	    } else {
	  	    	remn2=num2;
	  	    	remd2=denom2;
	  	    }	  	      		
	  		hcf1=HCFModule.hcf(remn1,remd2);	  	    
	  	    if (hcf1!=1) {
	  	    	remn1/=hcf1;
	  		    remd2/=hcf1;	
	  	   		factorsN1.add(new CancelOp(new IntegerNumber(hcf1)));
	  		    factorsD2.add(new CancelOp(new IntegerNumber(hcf1)));	
	  	    }			    
	  	    hcf1=HCFModule.hcf(remn2,remd1)	;	  	   
	  	    if (hcf1!=1) {  	    	
	  	    	remn2/=hcf1;
	  	   		remd1/=hcf1;
	  	    	factorsN2.add(new CancelOp(new IntegerNumber(hcf1)));
	  	    	factorsD1.add(new CancelOp(new IntegerNumber(hcf1)));
	  	    }
	  	    	  	    	  	    
			if ( (remn1!=1) || (factorsN1.size()==0) )
				factorsN1.add(new IntegerNumber(remn1)); 
			if ( (remn2!=1) || (factorsN2.size()==0) )
				factorsN2.add(new IntegerNumber(remn2));
			if ( (remd1!=1) || (factorsD1.size()==0) )
				factorsD1.add(new IntegerNumber(remd1));
			if ( (remd2!=1) || (factorsD2.size()==0) )
				factorsD2.add(new IntegerNumber(remd2));			
			
			N1=factorsIntoOp(factorsN1);
			D1=factorsIntoOp(factorsD1);
			N2=factorsIntoOp(factorsN2);
			D2=factorsIntoOp(factorsD2);
						  	    
	  	    working.add(new Multiplication(new FractionOp(N1, D1), new FractionOp(N2, D2)) );	
			working.add(new Multiplication(new FractionOp(new IntegerNumber(remn1), new IntegerNumber(remd1)), 
											new FractionOp(new IntegerNumber(remn2), new IntegerNumber(remd2))));												  	    		  	
			num1=remn1;
			num2=remn2;
			denom1=remd1;
			denom2=remd2;					
	
	  }//if ! areCoprime
	
	  
	working.add(new FractionOp(new Multiplication(new IntegerNumber(num1), new IntegerNumber(num2)), 
										new Multiplication(new IntegerNumber(denom1), new IntegerNumber(denom2))));		
	} //no equal numbers
	} else { 	//below the code in case of pairs of 1	


		typeFraction=false;				
		if ((Math.abs(num1*num2)==Math.abs(denom1*denom2)) && (!(Math.abs(num1*num2*denom1*denom2)==1))) {
			if (Math.abs(num1)!=1) numerator1=new CancelOp(new IntegerNumber(num1)); num1=Integer.signum(num1);
			if (Math.abs(num2)!=1) numerator2=new CancelOp(new IntegerNumber(num2)); num2=Integer.signum(num2);
			if (Math.abs(denom1)!=1) {denominator1=new CancelOp(new IntegerNumber(denom1)); denom1=1;}
			if (Math.abs(denom2)!=1) {denominator2=new CancelOp(new IntegerNumber(denom2)); denom2=1;}
			working.add(new Multiplication(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );

			if ((denom1<0) || (denom2<0)) { //change denominators to positive	 
	 			num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 			denom1=Math.abs(denom1);
	 			num2=Integer.signum(num2*denom2)*Math.abs(num2);
	 			denom2=Math.abs(denom2);
	 			denominatorsChanged=true;
	 			numerator1=new IntegerNumber(num1);
	 			numerator2=new IntegerNumber(num2);
	 			denominator1=new IntegerNumber(denom1);
	 			denominator2=new IntegerNumber(denom2);
			}	
		
	//		working.add(new Multiplication( new FractionOp(new IntegerNumber(num1), new IntegerNumber(denom1)),
	//										new FractionOp(new IntegerNumber(num2), new IntegerNumber(denom2))) );
		} else {
			if ((denom1<0) || (denom2<0)) { //change denominators to positive	 
	 			working.add(new Multiplication(new FractionOp(numerator1,denominator1)
		                                  , new FractionOp(numerator2,denominator2))); 
	 			num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 			denom1=Math.abs(denom1);
	 			num2=Integer.signum(num2*denom2)*Math.abs(num2);
	 			denom2=Math.abs(denom2);
	 			denominatorsChanged=true;
	 			numerator1=new IntegerNumber(num1);
	 			numerator2=new IntegerNumber(num2);
	 			denominator1=new IntegerNumber(denom1);
	 			denominator2=new IntegerNumber(denom2);
			} else working.add(new Multiplication(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );		
		}							
		working.add (new FractionOp(new IntegerNumber(num1*num2), new IntegerNumber(denom1*denom2)));	
	}
	num1*=num2; denom1*=denom2;
	if (! Primes.areCoprime(num1, denom1)) {       
		    typeFraction=true;
			hcf1=HCFModule.hcf(num1,denom1);
			if ((num1<0) && (denom1<0)) hcf1=-hcf1;
	  		num1=num1/hcf1;
	  		denom1=denom1/hcf1;	
	working.add(new FractionOp(new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(num1)),
								new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(denom1) ) ));  			
	}					
			if( (num1<0) && (denom1<0) ) //if numerator and denominator are both negative we make them positive
			{num1=-num1; denom1=-denom1; typeFraction=true;}	
	if (typeFraction)	
			working.add (new FractionOp(new IntegerNumber(num1), new IntegerNumber(denom1)));			
			return working;					
} //multiplyFractions

/**
 * divideFractions method divides two common fractions  .Method generates a solution and returns it in
 * Vector of MathOps. divideFractions does not simplify resulting fraction.
 * The last element of resulting vector would be either IntegerNumber or FractionOp.
 * @param  fraction1_Numerator	numerator of first multiplier
 * @param  fraction1_Denominator denominator of first multiplier
 * @param  fraction2_Numerator   numerator of second multiplier
 * @param  fraction2_Denominator denominator of second multiplier
 * @return a Vector <MathsOp> containing steps of solution (from initial expression to the answer);
 * @throws ArithmeticException if one of the denominators or numerator of a second fraction 
 *			is equal to 0
 * @see FractionArithmetics.PresentableFraction method
 */	
public static Vector <MathsOp> divideFractions(IntegerNumber fraction1_Numerator, IntegerNumber fraction1_Denominator, 
											IntegerNumber fraction2_Numerator, IntegerNumber fraction2_Denominator) throws ArithmeticException {
	Vector <MathsOp> working = new Vector<MathsOp> ();
	IntegerNumber numerator1=(IntegerNumber)fraction1_Numerator.clone();
	IntegerNumber denominator1=(IntegerNumber)fraction1_Denominator.clone();
	IntegerNumber numerator2=(IntegerNumber)fraction2_Numerator.clone();
	IntegerNumber denominator2=(IntegerNumber)fraction2_Denominator.clone();	
	int num1=numerator1.getInt();
	int denom1=denominator1.getInt();
	int num2=numerator2.getInt();
	int denom2=denominator2.getInt();
	
	MathsOp N1,N2;		
	
	if ((denom2*denom1*num2)==0)  throw new ArithmeticException("Division by zero");
	working.add(new Division (new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );

	if((num1)==0){ // first numerator == 0
		N1=new IntegerNumber(0);
		N2=new FractionOp(numerator2, denominator2);
	working.add(new Division(N1,N2));		
	working.add(new IntegerNumber(0));
	return working;	
	} //if num1==0
   
    if ((denom1<0) || (num2<0)) { //change denominators to positive
	 num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 denom1=Math.abs(denom1);
	 denom2=Integer.signum(num2*denom2)*Math.abs(denom2);
	 num2=Math.abs(num2);
	 numerator1=new IntegerNumber(num1);
	 numerator2=new IntegerNumber(num2);
	 denominator1=new IntegerNumber(denom1);
	 denominator2=new IntegerNumber(denom2);
	}	
      
    working.addAll(multiplyFractions(numerator1,denominator1,denominator2,numerator2));    

	return working;					
} //divideFractions

/**
 * addFractions method adds two common fractions. Method generates a solution and returns it in
 * Vector of MathOps. addFractions does not simplify resulting fraction (@see presentableFraction).
 * The last element of resulting vector would be either IntegerNumber or FractionOp.
 * @param  fraction1_Numerator	numerator of first multiplier
 * @param  fraction1_Denominator denominator of first multiplier
 * @param  fraction2_Numerator   numerator of second multiplier
 * @param  fraction2_Denominator denominator of second multiplier
 * @return a Vector <MathsOp> containing steps of solution (from initial expression to the answer);
 * @throws ArithmeticException if one of the denominators is equal to 0.
 * 
 */	
public static Vector <MathsOp> addFractions(IntegerNumber fraction1_Numerator, IntegerNumber fraction1_Denominator, 
											IntegerNumber fraction2_Numerator, IntegerNumber fraction2_Denominator) throws ArithmeticException {
	IntegerNumber numerator1=(IntegerNumber)fraction1_Numerator.clone();
	IntegerNumber denominator1=(IntegerNumber)fraction1_Denominator.clone();
	IntegerNumber numerator2=(IntegerNumber)fraction2_Numerator.clone();
	IntegerNumber denominator2=(IntegerNumber)fraction2_Denominator.clone();	
		
	Vector <MathsOp> working = new Vector<MathsOp> ();
	int num1=numerator1.getInt();
	int denom1=denominator1.getInt();
	int num2=numerator2.getInt();
	int denom2=denominator2.getInt();
	int resnum, resdenom, hcf1;
	byte sign=1;
	boolean typeFraction=false, operationChanged=false, denominatorsChanged=false;
	MathsOp N1,N2;		
	
	if ((denom2*denom1)==0)  throw new ArithmeticException("Division by zero");
	working.add(new Addition(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );
	if ((num2*denom2)<0) operationChanged=true;  
	num2=Math.abs(num2);	
	numerator2=new IntegerNumber(num2);
	if ((denom1<0) || (denom2<0)) { //change denominators to positive
	 num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 denom1=Math.abs(denom1);
	 denom2=Math.abs(denom2);
	 denominatorsChanged=true;
	 numerator1=new IntegerNumber(num1);
	 denominator1=new IntegerNumber(denom1);
	 denominator2=new IntegerNumber(denom2);
	}				
	
	if((num1*num2)==0){ // one of the numerators == 0
	if(num1==0) {N1=new IntegerNumber(0); resnum=num2; resdenom=denom2;}	 
		else {N1=new FractionOp(numerator1,denominator1); resnum=num1; resdenom=denom1;} //resnum/resdenom - resulting fraction
	if (num2==0) N2=new IntegerNumber(0); 
		else N2=new FractionOp(numerator2,denominator2);								
	if (operationChanged) working.add(new Subtraction(N1,N2)); else working.add(new Addition(N1,N2));		
    if ((num1+num2)==0) { //both fractions ==0
		working.add(new IntegerNumber(0));
		return working;		
    }     
    if (operationChanged) {sign=-1; working.add(new UnaryMinus(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom))));}
	    else working.add(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));
	} else { //below is the code in case if num1*num2!=0
	hcf1=HCFModule.hcf(denom1, denom2);
	resdenom=denom1*denom2/hcf1;		
	if (denom1!=denom2)
	{
	 if (denom2!=hcf1)		 
		 N1=new FractionOp(new Multiplication(new IntegerNumber(num1), new IntegerNumber (denom2/hcf1) ), 
	 				new Multiplication(new IntegerNumber(denom1), new IntegerNumber(denom2/hcf1)));	 	 
	 else 
	 	 N1=new FractionOp(new IntegerNumber(num1),new IntegerNumber(denom1));
	 if (denom1!=hcf1)	 	 	 					
	 	N2=new FractionOp(new Multiplication(new IntegerNumber(num2), new IntegerNumber (denom1/hcf1) ), 
	 				new Multiplication(new IntegerNumber(denom2), new IntegerNumber(denom1/hcf1)));	 						 					
	 else 
	 	N2=new FractionOp(new IntegerNumber(num2), new IntegerNumber(denom2));	 					
	 if (operationChanged) working.add(new Subtraction(N1,N2));
	   else working.add(new Addition(N1,N2));				 	
	} 
	if (operationChanged)	
		N1=new FractionOp(new Subtraction(new IntegerNumber(num1*denom2/hcf1), new IntegerNumber(num2*denom1/hcf1)), 
	 					new IntegerNumber(resdenom));
	else N1=new FractionOp(new Addition(new IntegerNumber(num1*denom2/hcf1), new IntegerNumber(num2*denom1/hcf1)), 
	 					new IntegerNumber(resdenom));	
	 working.add(N1);															 						
	 resnum= operationChanged ? (num1*denom2-num2*denom1)/hcf1 : (num1*denom2+num2*denom1)/hcf1;						
	 working.add(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));					
	} //else ,  num1*num2!=0
	if (resnum==0)
	{working.add(new IntegerNumber(0));
	 return working;
	}
	//one fraction remaining : resnum/resdenom
	if (resnum<0) typeFraction=true;
	if ((resnum*sign)<0) sign=-1; else sign=1;
	resnum=Math.abs(resnum); 
	if (! Primes.areCoprime(resnum, resdenom)) {       
		    typeFraction=true;
			hcf1=HCFModule.hcf(resnum,resdenom);		
	  		resnum=resnum/hcf1;
	  		resdenom=resdenom/hcf1;
			N1=new FractionOp(new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(resnum)),
								new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(resdenom) ) );   			
			if (sign<0)								
				working.add(new UnaryMinus(N1));
			else working.add(N1);  			
	}					
	if (typeFraction) {
		if (sign<0)
			working.add (new UnaryMinus(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom))));
			else working.add (new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));
	}	
							
	return working;					
} //addFractions

/**
 * subtractFractions method subtract one common fraction from another. Method generates a solution and returns it in
 * Vector of MathOps. subtractFractions does not simplify resulting fraction (@see presentableFraction).
 * Note that the last element of the resulting vector could be IntegerNumber, FractionOp or UnaryMinus where op is instance of
 * FractionOp class. 
 * @param  fraction1_Numerator	numerator of first multiplier
 * @param  fraction1_Denominator denominator of first multiplier
 * @param  fraction2_Numerator   numerator of second multiplier
 * @param  fraction2_Denominator denominator of second multiplier
 * @return a Vector <MathsOp> containing steps of solution (from initial expression to the answer);
 * @throws ArithmeticException if one of the denominators is equal to 0.
 * 
 */	
public static Vector <MathsOp> subtractFractions(IntegerNumber fraction1_Numerator, IntegerNumber fraction1_Denominator, 
											IntegerNumber fraction2_Numerator, IntegerNumber fraction2_Denominator) throws ArithmeticException {
	IntegerNumber numerator1=(IntegerNumber)fraction1_Numerator.clone();
	IntegerNumber denominator1=(IntegerNumber)fraction1_Denominator.clone();
	IntegerNumber numerator2=(IntegerNumber)fraction2_Numerator.clone();
	IntegerNumber denominator2=(IntegerNumber)fraction2_Denominator.clone();	
		
	Vector <MathsOp> working = new Vector<MathsOp> ();
	int num1=numerator1.getInt();
	int denom1=denominator1.getInt();
	int num2=numerator2.getInt();
	int denom2=denominator2.getInt();
	int resnum, resdenom, hcf1;
	byte sign=1;
	boolean typeFraction=false, operationChanged=false, denominatorsChanged=false;
	MathsOp N1,N2;		
	
	if ((denom2*denom1)==0)  throw new ArithmeticException("Division by zero");
	working.add(new Subtraction(new FractionOp(numerator1,denominator1), new FractionOp(numerator2,denominator2)) );
	if ( (num1==num2) && (denom1==denom2) ){ //subtraction of equal numbers , return 0
		working.add(new IntegerNumber(0));
		return working;
	}	
	if ((num2*denom2)<0) operationChanged=true;  
	num2=Math.abs(num2);	
	numerator2=new IntegerNumber(num2);
	if ((denom1<0) || (denom2<0)) { //change denominators to positive
	 num1=Integer.signum(num1*denom1)*Math.abs(num1);
	 denom1=Math.abs(denom1);
	 denom2=Math.abs(denom2);
	 denominatorsChanged=true;
	 numerator1=new IntegerNumber(num1);
	 denominator1=new IntegerNumber(denom1);
	 denominator2=new IntegerNumber(denom2);
	}					
	if((num1*num2)==0){ // one of the numerators == 0
	if(num1==0) {N1=new IntegerNumber(0); resnum=num2; resdenom=denom2;}	 
		else {N1=new FractionOp(numerator1,denominator1); resnum=num1; resdenom=denom1;} //resnum/resdenom - resulting fraction
	if (num2==0) N2=new IntegerNumber(0); 
		else N2=new FractionOp(numerator2,denominator2);								
	if (operationChanged) working.add(new Addition(N1,N2)); else working.add(new Subtraction(N1,N2));		
    if ((num1+num2)==0) { //both fractions ==0
		working.add(new IntegerNumber(0));
		return working;		
    }     
    if ((!operationChanged)&&(num1==0)) {sign=-1; working.add(new UnaryMinus(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom))));}
	    else working.add(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));
	} else { //below is the code in case if num1*num2!=0
	hcf1=HCFModule.hcf(denom1, denom2);
	resdenom=denom1*denom2/hcf1;		
	if (denom1!=denom2)
	{
	 if (denom2!=hcf1)		 
		 N1=new FractionOp(new Multiplication(new IntegerNumber(num1), new IntegerNumber (denom2/hcf1) ), 
	 				new Multiplication(new IntegerNumber(denom1), new IntegerNumber(denom2/hcf1)));	 	 
	 else 
	 	 N1=new FractionOp(new IntegerNumber(num1),new IntegerNumber(denom1));
	 if (denom1!=hcf1)	 	 	 					
	 	N2=new FractionOp(new Multiplication(new IntegerNumber(num2), new IntegerNumber (denom1/hcf1) ), 
	 				new Multiplication(new IntegerNumber(denom2), new IntegerNumber(denom1/hcf1)));	 						 					
	 else 
	 	N2=new FractionOp(new IntegerNumber(num2), new IntegerNumber(denom2));	 					
	 if (operationChanged) working.add(new Addition(N1,N2));
	   else working.add(new Subtraction(N1,N2));				 	
	} 
	if (operationChanged)	
		N1=new FractionOp(new Addition(new IntegerNumber(num1*denom2/hcf1), new IntegerNumber(num2*denom1/hcf1)), 
	 					new IntegerNumber(resdenom));
	else N1=new FractionOp(new Subtraction(new IntegerNumber(num1*denom2/hcf1), new IntegerNumber(num2*denom1/hcf1)), 
	 					new IntegerNumber(resdenom));	
	 working.add(N1);															 						
	 resnum= operationChanged ? (num1*denom2+num2*denom1)/hcf1 : (num1*denom2-num2*denom1)/hcf1;						
	 working.add(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));					
	} //else ,  num1*num2!=0
	if (resnum==0)
	{working.add(new IntegerNumber(0));
	 return working;
	}
	//one fraction remaining : resnum/resdenom
	if (resnum<0) typeFraction=true;
	if ((resnum*sign)<0) sign=-1; else sign=1;
	resnum=Math.abs(resnum); 
	if (! Primes.areCoprime(resnum, resdenom)) {       
		    typeFraction=true;
			hcf1=HCFModule.hcf(resnum,resdenom);		
	  		resnum=resnum/hcf1;
	  		resdenom=resdenom/hcf1;
			N1=new FractionOp(new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(resnum)),
								new Multiplication(new CancelOp(new IntegerNumber(hcf1)), new IntegerNumber(resdenom) ) );   			
			if (sign<0)								
				working.add(new UnaryMinus(N1));
			else working.add(N1);  			
	}					
	if (typeFraction) {
		if (sign<0)
			working.add (new UnaryMinus(new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom))));
			else working.add (new FractionOp(new IntegerNumber(resnum), new IntegerNumber(resdenom)));
	}	
							
	return working;					
} //subtractFractions



//method factorsIntoOp creates a multiplication of few numbers from array of 
//numbers
private static MathsOp factorsIntoOp(Vector<MathsOp> input_factors) {
    Vector <MathsOp> factors=(Vector<MathsOp>)input_factors.clone();
	if (factors.size()==1)
		return factors.get(0);
	Multiplication	result=new Multiplication(factors.get(0), factors.get(1));
	for (int i=2; i<factors.size(); i++)
		result=new Multiplication((Multiplication)result.clone(),factors.get(i));
	return result;		
}// factorsIntoOp  
    
} //FractionArithmetics
