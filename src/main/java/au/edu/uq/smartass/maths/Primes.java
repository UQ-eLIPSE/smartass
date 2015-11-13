/** This file is part of SmartAss and describes 
 * Class Primes for working with prime numbers
 * 
 */
package au.edu.uq.smartass.maths;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class Primes extends MathsModule{
 private int num1;
 private String solution;
 
 public Primes(Engine engine) 
	{
				super(engine);
				this.generate("no parameters");
			
	} //constructor
/*
 * 
 **/
 public String getSection(String name) {
		if(name.equals("question"))
			return "Is \\ensuremath{"+num1+"} a prime number? Why?";
		if(name.equals("solution"))
			return solution;
		if(name.equals("shortanswer")){
			if (isPrime(num1)) return "Yes";
			else return "No";
		}
		return super.getSection(name);
	}
	
 
	//is 'n' prime number?
	public static boolean isPrime(int n) {
		if(n<2) return(false);
		if (n==4) return(false);
		for(int i=2; i<n/2; i++) {
			if(n%i==0) return(false);
		}
		return true;
	} 
   
 public static boolean areCoprime(int m, int n) { //method determines whether m and n are relatively prime
   		return (HCFModule.hcf(m,n)==1);
   	} //relativelyPrime
   	
    	
   	public static int[] primeFactorsOf(int m) { //represents integer m as a product of prime factors, returns array of prime factors
          int[] prmsArray;
		  if (m<4) {
		  	prmsArray=new int[1];
		  	prmsArray[0]=m;
		  	return prmsArray; 
		  }
		  Vector <Integer> prms=new Vector <Integer> ();
		  while ((m%2)==0){
		  	prms.add(new Integer(2));
		  	m=m/2;
		  }
		  for (int dvsr=3; dvsr<=m; dvsr+=2) {
		  	while((m%dvsr)==0){
		  	
		  		prms.add(new Integer(dvsr));
		  		m=m/dvsr;
		  	}		  }
		  prmsArray=new int[prms.size()];
		  for (int i=0; i<prmsArray.length; i++)
		  	prmsArray[i]=prms.get(i);
          return prmsArray; 		
   	}
     
 void generate (String param){
 	num1=RandomChoice.randInt(2,100);
 	if (isPrime(num1)) {	
 		solution="Yes, its only factors are $"+num1+"$ and 1 ";
 		return;		
 		}
 	int[] primeFactors=primeFactorsOf(num1);
 	solution="No, since $"+num1+"="+primeFactors[0]+"\\times "+num1/primeFactors[0]+"$ ";

 }
} 
