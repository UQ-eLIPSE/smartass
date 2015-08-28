/** This file is part of SmartAss and describes 
 * Class Gcd for the highest common factor operations
 * 
 */
package au.edu.uq.smartass.maths;
import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.lang.reflect.Array;

public class HCFModule  extends MathsModule {
    public HCFModule(Engine engine) {
    	super(engine);
    	this.generate("No parametres");
    }
    
	private int num1, num2, res;

	public String getSection(String name) {
		if(name.equals("question"))
			return "What is the highest common factor of $"+num1+"$ and $"+num2+"$ ?";
		else if(name.equals("solution"))
			return String.valueOf(res);
		return super.getSection(name);
	}
	
	public void generate(String params) {
				int r;
				for (int i=0; i<2; i++) { // introducing bias
				num1=RandomChoice.randInt(5,20);//Integer.valueOf((String)Array.get(RandomChoice.makeChoice("[5..20]/10;[-20..-5]/0"),0)); 
				num2=RandomChoice.randInt(15,40);//Integer.valueOf((String)Array.get(RandomChoice.makeChoice("[15..40]/10;[-40..-15]/0"),0));
				res=hcf(num1,num2);
				if (!(res==1)) i=2; 
				}
				if (RandomChoice.randInt(0,1)==1){
					r=num1; num1=num2; num2=r;}
				// res=hcf(num1,num2);
	}//generate
	
	public static int hcf(int m, int n) //method finds highest common factor, returns integer
   	{
   		int r;
		m=Math.abs(m); n=Math.abs(n);
		if(m==0)	 return n ;
		if (n==0) return m;
		r=n%m;
		while(r>0)
		{
	  	n=m;	
	  	m=r;	
	  	r=n%m;
		}
		return(m);
   	} //hcf		
   	
   	public static int hcf(int[] numbers) //method finds highest common factor of several given numbers
     {
     	int[][] arrays=new int[numbers.length][]; //array of arrays of primes
     	int[] index=new int[numbers.length];
     	int minSize=0; 
     	int minSizeIndex=0, m, k;
     	boolean found;
     	arrays[0]=Primes.primeFactorsOf(numbers[0]);  
     	minSize=arrays[0].length; minSizeIndex=0; index[0]=0; 	
   		for (int i=1; i<numbers.length; i++) {   		 	
   			arrays[i]=Primes.primeFactorsOf(numbers[i]);
   			if (minSize>arrays[i].length) {minSize=arrays[i].length; minSizeIndex=i;};
   			index[i]=0;
   		}
   		m=1;
   		for (int i=0; i<minSize; i++) {
   			found=true;
   			k=0;
   			while ( (found) && (k<numbers.length) ){   				
   				if (k!=minSizeIndex) {
   					found=false;
   					for (int i2=index[k]; i2<arrays[k].length; i2++)
   						if (arrays[k][i2]==arrays[minSizeIndex][i]) {
   							found=true;
   							index[k]=i2+1; i2=arrays[k].length;
   						}
   				}
   				k++;
   			}
   			if (found) m*=arrays[minSizeIndex][i]; 
   		}		
		return(m);
   	} //hcf		
   	
   	
}
