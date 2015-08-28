/**
 * @(#)SqrtModule.java
 *
 *
 * @author 
 * @version 1.00 2007/1/11
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

public class SqrtModule extends MathsModule {
	MathsOp op, decoded, result; //question;
	LinkedList<MathsOp> solution = new LinkedList<MathsOp>(); 
    public SqrtModule(Engine engine) {
    	super(engine);
    	generate();
	
    }
    
// with number passed into the module as a parameter    
public SqrtModule(Engine engine, String[] param) {
    	super(engine);
    	generate(param);
    	
    }
	//generating good number for square root
    private int generateNumber(String numbers, int max) {
       	int n, temp;
        temp = Integer.valueOf(RandomChoice.makeChoice(numbers)[0]);
        n = temp;
        while (temp*n<max) {
        	n *= temp;
	        temp = Integer.valueOf(RandomChoice.makeChoice(numbers)[0]);
        }
        return n;
    }

       public String getSection(String name) {
               if(name.equals("question"))
                       return "\\ensuremath{"+op.toString()+"}";
               else if (name.equals("solution")) {
               	      if ( (((UnaryOp)op).getOp() instanceof IntegerNumber) 
               	      	&& Primes.isPrime(((IntegerNumber)(((UnaryOp)op).getOp())).getInt() ) )   
               	      	return ((IntegerNumber)((UnaryOp)op).getOp()).toString()+" is prime, so you can not simplify \\ensuremath{"+op.toString()+"} ";
               	      else 
               	      	if (((IntegerNumber)(((UnaryOp)op).getOp())).getInt()<2)
               	      		return "\\ensuremath{"+composeSolution()+"} ";
               	      else	
               	      	if (decoded==null)
               	      		return 	"\\ensuremath{"+composeSolution()+"} \n\\\\So \\ensuremath{"+op.toString() 
               	      			+"} can not be simplified. \n\\\\Hence the solution is \\ensuremath{" +
                               result.toString() + "}" ;	
                       else 
                       return "\\ensuremath{"+composeSolution()+"}. \n\\\\Then \\ensuremath{"+op.toString() +
                               " = " + decoded.toString() + "}.\n\\\\Hence the solution is \\ensuremath{" +
                               result.toString() + "}" ;
               }
               else if (name.equals("shortanswer"))
                       return "\\ensuremath{" + result.toString() + "}" ;
       //      if (name.equals("number"))
       //              return String.valueOf(generateNumber());
              
               return super.getSection(name);
       }
	
	private String composeSolution() {  
		String res = solution.getFirst().toString();
		ListIterator it = solution.listIterator(0);
		it.next();
		int endLineFlag=0;
		while(it.hasNext()) 
		{
			if( ((endLineFlag % 3) == 0) && (endLineFlag!=0))
		  	   res+="\\\\";	
		 	res = res + " = " + it.next().toString();
			endLineFlag++; 
		
		}		
		return res;
	}
	
	
	private void generate (){
				
		int num = generateNumber("[2]/6;[3]/5;[5]/4;[7]/2", RandomChoice.randInt(300,500));
		
		//compose question
		solution.add(op = new Sqrt(new IntegerNumber(num)));

		//compose solution steps 
		int i, nnums = 0, 
			maxnums = num / 2, //no more than num/2 numbers in solution 
			curnum = num, 
			pos = 0,
			fac = 2;  
		int[] numlist = new int[maxnums];
		MathsOp step;
		while(curnum>1) {
			while(curnum%fac==0) {
				numlist[pos++] = fac;
				curnum /= fac;
				if(curnum>1) {
					step = new IntegerNumber(curnum);
					for(i=pos-1;i>=0;i--) 
						step = new Multiplication(new IntegerNumber(numlist[i]), step);
					solution.add(step = new Sqrt(step));
				}
			}
			fac++; 
		}

    	//compose last step and result
    	MathsOp outop = null;
    	MathsOp inop = null;
    	int ncount=1;
    	for(i=1;i<pos;i++) {
    		if(numlist[i-1]==numlist[i]) {
    			ncount++;
	    		if(ncount%2==0) {
	    			if(outop==null)
    					outop = new IntegerNumber(numlist[i]);
    				else
    					outop = new Multiplication(outop, new IntegerNumber(numlist[i]));
	    		}
    		}
    		else {
    			if(ncount%2==1)
    				if(inop==null)
    					inop = new IntegerNumber(numlist[i-1]);
    				else
    					inop = new Multiplication(inop, new IntegerNumber(numlist[i-1]));
    			ncount = 1;
    		}
    	}
   		if(ncount%2==1)
 			if(inop==null)
   				inop = new IntegerNumber(numlist[i-1]);
   			else
   				inop = new Multiplication(inop, new IntegerNumber(numlist[i-1]));

   		if(inop==null) {
    		decoded = outop;
	    	result = new IntegerNumber((int)((FloatCalculable)outop).calculate());
   		}
    	else {
    		decoded = new Multiplication(outop, new Sqrt(inop));
	    	result = new UnprintableMultiplication(new IntegerNumber((int)((FloatCalculable)outop).calculate()), 
    			new Sqrt(new IntegerNumber((int)((FloatCalculable)inop).calculate())));
    	}
	}
 private void generate(String[] number){
 	int num;

    	if (number.length<1) {
	             generate();
	            return;
	            }
		try{   	    
 	  		 num=Integer.valueOf(number[0]);
 			}
   	    	catch (NumberFormatException e){
   	    			System.out.println("Incorrect parameter format passed into SqrtModule.java... Generating random numbers.");
   	    			generate();
   	    			return;
   	    }	
		
		if (num<0){
				System.out.println("Negative parameter  passed into SqrtModule.java... Generating random numbers.");
   	    			generate();
   	    			return;
		}	
		
			
		//compose question
		solution.add(op = new Sqrt(new IntegerNumber(num)));

	
		//number is prime
		if (Primes.isPrime(num)){
			result=op;
			decoded=op;
			return;
		}
		if (num<2){
			solution.add(decoded=new IntegerNumber(num));
			result=decoded;
			return;
		}
		
		//compose solution steps 
		int i, nnums = 0, 
		maxnums = num / 2, //no more than num/2 numbers in solution 
		curnum = num, 
		pos = 0,
		fac = 2;  			
		int[] numlist = new int[maxnums];
		MathsOp step;
		while(curnum>1) {
			while(curnum%fac==0) {
				numlist[pos++] = fac;
				curnum /= fac;
				if(curnum>1) {
					step = new IntegerNumber(curnum);
					for(i=pos-1;i>=0;i--) 
						step = new Multiplication(new IntegerNumber(numlist[i]), step);
					solution.add(step = new Sqrt(step));
				}
			}
			fac++; 
		}

    	//compose last step and result
    	MathsOp outop = null;
    	MathsOp inop = null;
    	int ncount=1;
    	for(i=1;i<pos;i++) {
    		if(numlist[i-1]==numlist[i]) {
    			ncount++;
	    		if(ncount%2==0) {
	    			if(outop==null)
    					outop = new IntegerNumber(numlist[i]);
    				else
    					outop = new Multiplication(outop, new IntegerNumber(numlist[i]));
	    		}
    		}
    		else {
    			if(ncount%2==1)
    				if(inop==null)
    					inop = new IntegerNumber(numlist[i-1]);
    				else
    					inop = new Multiplication(inop, new IntegerNumber(numlist[i-1]));
    			ncount = 1;
    		}
    	}
   		if(ncount%2==1)
 			if(inop==null)
   				inop = new IntegerNumber(numlist[i-1]);
   			else
   				inop = new Multiplication(inop, new IntegerNumber(numlist[i-1]));

   		if(inop==null) {
    		decoded = outop;
	    	result = new IntegerNumber((int)((FloatCalculable)outop).calculate());
   		}
    	else 
    	if (outop==null){
    	  decoded=null;
    	  result=op;		
    	}	
    	else		{
    		decoded = new Multiplication(outop, new Sqrt(inop));
	    	result = new UnprintableMultiplication(new IntegerNumber((int)((FloatCalculable)outop).calculate()), 
    			new Sqrt(new IntegerNumber((int)((FloatCalculable)inop).calculate())));
    	}
 }
	
}