/**
 * @(#)PrimesCompositeQuestion.java
 *
 *
 * @author 
 * @version 1.00 2006/11/17
 */

package au.edu.uq.smartass.maths;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;

public class PrimesCompositeQuestion extends MathsModule {
	private int num1, num2;
	String solution1, solution2;
	String shortanswer1, shortanswer2;
	
	 
    public PrimesCompositeQuestion(Engine engine) {					
				super(engine);
				this.generate("no parameters");		
	} //constructor
	
	/* 
	 * Note: This composite questions consists of 4 questions, logically connected
	 *		 However, any of them could be asked separatly 
	 *
	 **/
	 
    public String getSection(String name) {	
		if (name.equals("question1"))
			return "Write \\ensuremath{"+num1+"} as the product of prime factors.";
		if (name.equals("question2"))
			return "Write \\ensuremath{"+num2+"} as the product of prime factors.";
		if (name.equals("question3"))
			return "Are \\ensuremath{"+num1+"} and \\ensuremath{"+num2+"} relatively prime?"; 
		if (name.equals("question4"))
			return "Write \\ensuremath{"+num1*num2+"} as the product of prime factors."; 
		if (name.equals("question4Hint"))
			return "\\ensuremath{"+num1*num2+"="+num1+"\\times "+num2+"}";			
		if (name.equals("solution1"))
			return solution1;
		if (name.equals("shortanswer1"))
			return "\\ensuremath{"+shortanswer1+"}";
		if (name.equals("solution2"))
			return solution2;
		if (name.equals("shortanswer2"))
			return "\\ensuremath{"+shortanswer2+"}";
		if (name.equals("solution3")){
			if (Primes.areCoprime(num1,num2))
				return "Yes, the highest common factor of \\ensuremath{"+num1+"} and \\ensuremath{"+num2+"} is 1, so {\\bf they are} relatively prime.";
				else return "No, the highest common factor of \\ensuremath{"+num1+"} and \\ensuremath{"+num2+"} is \\ensuremath{"
					+HCFModule.hcf(num1,num2)+"}, so {\\bf they are not} relatively prime.";
		}
		
		
		if (name.equals("shortanswer3")){
			if (Primes.areCoprime(num1,num2))
				return ("Yes");
				else return ("No");
		}		 	
		if (name.equals("solution4")){
			return "\\ensuremath{"+num1*num2+"="+num1+"\\times"+num2+"=("+shortanswer1+")\\times ("+shortanswer2+")="
					+shortanswer1+"\\times "+shortanswer2+"} ";
		}
		if (name.equals("shortanswer4")){
			return "\\ensuremath{"+shortanswer1+"\\times "+shortanswer2+"}";
		}

		if (name.equals("question"))
	
				
			return "\\begin{enumerate} \\item "+getSection("question1")
				+"\\item "+getSection("question2")+"\\item "+getSection("question3")
					+"\\item "+getSection("question4")+" (Hint: "
						+getSection("question4Hint") +"). \\end{enumerate}";
			
				
		if (name.equals("solution"))
			return 	"\\begin{enumerate} \\item "+getSection("solution1")
				+"\\item "+getSection("solution2")+"\\item "+getSection("solution3")
					+"\\item "+getSection("solution4")+"\\end{enumerate}";	
				
				
		if (name.equals("shortanswer"))
			return "\\begin{enumerate} \\item "+getSection("shortanswer1")
				+"\\item "+getSection("shortanswer2")+"\\item "+getSection("shortanswer3")
					+"\\item "+getSection("shortanswer4")+"\\end{enumerate}";	
		return super.getSection(name);	
	} //getSection
	
void generate (String param){
	int temp;
	num1=RandomChoice.randInt(2,100);
	
 	while (Primes.isPrime(num1)) num1++;
 	num2=RandomChoice.randInt(2,100);
 	while (Primes.isPrime(num2) || (num2==num1)) num2++;

 
 	int[] num1Factors=Primes.primeFactorsOf(num1);
 	int[] num2Factors=Primes.primeFactorsOf(num2);
 	temp=num1;
 	solution1="$"+num1; 
	for(int i=1; i<num1Factors.length; i++)
	{
		solution1+="=";
		temp/=num1Factors[i-1];
			for(int k=0; k<i; k++)
				solution1+=num1Factors[k]+"\\times ";
			
 		solution1+=temp;
	}
 	solution1+="$ ";
 		
 	temp=num2;
 	solution2="$"+num2; 
	for(int i=1; i<num2Factors.length; i++)
	{
		solution2+="=";
		temp/=num2Factors[i-1];
			for(int k=0; k<i; k++)
				solution2+=num2Factors[k]+"\\times ";
			
 		solution2+=temp;
	}
 	solution2+="$ ";
  	shortanswer1=solution1.substring(solution1.lastIndexOf('=')+1,solution1.length()-2);	
  	shortanswer2=solution2.substring(solution2.lastIndexOf('=')+1,solution2.length()-2);
 } //generate
   
}