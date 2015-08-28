/**
 * @(#)SqrtMultiplyModule.java
 *
 *
 * @author 
 * @version 1.00 2007/1/14
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;


public class SqrtMultiplyModule extends MathsModule {
	MathsOp question, result;
	LinkedList<MathsOp> solution = new LinkedList<MathsOp>();
   	BinaryOp op1, op2;
   	private final int biasIndex=3;
   	int[] signums = new int[4];

    public SqrtMultiplyModule(Engine engine) {
    	super(engine);
    	
    	if(RandomChoice.randInt(0,1)==0)
    		op1 = new Addition(null, null);
    	else
    		op1 = new Subtraction(null, null);
    	if(RandomChoice.randInt(0,1)==0)
    		op2 = new Addition(null, null);
    	else
    		op2 = new Subtraction(null, null);
    	
    	int[] nums = new int[4]; 
        int a = 2;//(new Integer(RandomChoice.makeChoice("[2..3]/2;[5]/1;[7]/1")[0])).intValue();   	
        int b = 3;//(new Integer(RandomChoice.makeChoice("[2..3]/2;[5]/1;[7]/1")[0])).intValue();          	
        int temp;
    	for(int i=0;i<4;i++) { 	
    	    nums[i]=(RandomChoice.randInt(0,1)==1) ? a : b;
    	    for (int l=0; l<biasIndex; l++) {
    	    for (int k=RandomChoice.randInt(0,3); k<3; k++){
    	    	temp=(RandomChoice.randInt(0,1)==1) ? (nums[i]*a) : (nums[i]*b);
    	    	if (temp<12)     	    	 	
    	    		nums[i]=temp;
    	    	else k=3; 
    	    }
    	    if (((i==1)||(i==3)) && (nums[i]!=nums[i-1]))
    	        l=biasIndex;    	        	  
    	    }
    	}   	
    	
    	//question
    	op1.setOp(0, new Sqrt(new IntegerNumber(nums[0])));
    	op1.setOp(1, new Sqrt(new IntegerNumber(nums[1])));
    	op2.setOp(0, new Sqrt(new IntegerNumber(nums[2])));
    	op2.setOp(1, new Sqrt(new IntegerNumber(nums[3])));
    	question = new UnprintableMultiplication(op1, op2);
    	solution.add(question);
    	
    	if(op1 instanceof Subtraction && nums[0]==nums[1]){
    		//special case = 0*(c+d)
    		if (op2 instanceof Subtraction && nums[2]==nums[3])
    			solution.add(new Multiplication(new IntegerNumber(0), new IntegerNumber(0)));
    		else 
    		   solution.add(new Multiplication(new IntegerNumber(0), op2));
    		solution.add(result = new IntegerNumber(0));
    	} else if(op2 instanceof Subtraction && nums[2]==nums[3]){
	    	//special case = (a+b)*0
    		solution.add(new Multiplication(op1, new IntegerNumber(0)));
    		solution.add(result = new IntegerNumber(0));
    	} else { 
   		
	    	//step 1 of solution
    		signums[0] = 1;
 	   		if(op2 instanceof Subtraction) 
    			signums[1] = -1;
	    	else
    			signums[1] = 1;
    		if(op1 instanceof Subtraction) 
	    		signums[2] = -1;
    		else
    			signums[2] = 1;
	    	if(op2 instanceof Subtraction ^ op1 instanceof Subtraction) 
    			signums[3] = -1;
    		else
    			signums[3] = 1;

	    	MathsOp[] opp = new MathsOp[4];
	    	opp[0] = new Multiplication(new Sqrt(new IntegerNumber(nums[0])), new Sqrt(new IntegerNumber(nums[2])));
	    	opp[1] = new Multiplication(new Sqrt(new IntegerNumber(nums[0])), new Sqrt(new IntegerNumber(nums[3])));
	    	opp[2] = new Multiplication(new Sqrt(new IntegerNumber(nums[1])), new Sqrt(new IntegerNumber(nums[2])));
	    	opp[3] = new Multiplication(new Sqrt(new IntegerNumber(nums[1])), new Sqrt(new IntegerNumber(nums[3])));
	    	composeOps(opp);
	    	
	    	//step 2 of solution
	    	opp[0] = new Sqrt(new Multiplication(new IntegerNumber(nums[0]), new IntegerNumber(nums[2])));
	    	opp[1] = new Sqrt(new Multiplication(new IntegerNumber(nums[0]), new IntegerNumber(nums[3])));
	    	opp[2] = new Sqrt(new Multiplication(new IntegerNumber(nums[1]), new IntegerNumber(nums[2])));
	    	opp[3] = new Sqrt(new Multiplication(new IntegerNumber(nums[1]), new IntegerNumber(nums[3])));
	    	composeOps(opp);
	
	    	//step 3 of solution
	    	int[][] nroots = new int[4][];
	    	opp[0] = multiplyRoots(nums[0], nums[2]);
	    	opp[1] = multiplyRoots(nums[0], nums[3]);
	    	opp[2] = multiplyRoots(nums[1], nums[2]);
	    	opp[3] = multiplyRoots(nums[1], nums[3]);
	    	MathsOp[] ops = composeOps(opp).clone();
	    	
	    	//step 4 of solution
	    	opp[0] = composeRoot(nroots[0]=extractRoot(nums[0]*nums[2]));
	    	opp[1] = composeRoot(nroots[1]=extractRoot(nums[0]*nums[3]));
	    	opp[2] = composeRoot(nroots[2]=extractRoot(nums[1]*nums[2]));
	    	opp[3] = composeRoot(nroots[3]=extractRoot(nums[1]*nums[3]));
	    	int changed=0;
	    	for(int i=0;i<4;i++) 
	    		if(nroots[i][0]!=1 && nroots[i][1]!=1)
	    			changed++;
	    	if(changed>0)
	    		ops = composeOps(opp).clone();
	    	
	    	//step 5 of solution
			int[] tmp;
			int tmpsg;
			boolean ord_changed = false;
			for(int i=0;i<nroots.length-1;i++) 
				for(int j=i+1;j<nroots.length;j++) 
					if(nroots[i][1]>nroots[j][1]) {
						ord_changed = true;
						tmp = nroots[i]; 
						nroots[i] = nroots[j]; 
						nroots[j] = tmp;
						tmpsg = signums[i];
						signums[i] = signums[j];
						signums[j] = tmpsg;
	   				}
			
			if(ord_changed) {
				for(int i=0;i<4;i++)
					/*if(nroots[i][0]==1) {
						opp[i] = new Sqrt(new IntegerNumber(nroots[i][1]));
						if(signums[i]<0)
							opp[i] = new UnaryMinus(opp[i]);
					} else if(nroots[i][1]==1)
						opp[i] = new IntegerNumber(nroots[i][0]*signums[i]);
					else */{
						opp[i] = composeRoot(nroots[i]);
						if(signums[i]==-1)
							opp[i] = new UnaryMinus(opp[i]);
					}
				ops[0] = new Addition(opp[0],opp[1]);
				ops[1] = new Addition(ops[0],opp[2]);
				ops[2] = new Addition(ops[1],opp[3]);
				solution.add(result = ops[2]);
			}
			
	    	//step 6 of solution
			opp = new MathsOp[]{null,null,null,null};
			int opos = 0;
			int cn = nroots[0][0] * signums[0];
			for(int i=1;i<4;i++) 
				if(nroots[i-1][1]==nroots[i][1])
					cn += nroots[i][0] * signums[i];
				else {
					if(cn!=0) 
						if(nroots[i-1][1]==1)
							opp[opos++] = new IntegerNumber(cn);
						else if(cn==1)
							opp[opos++] = new Sqrt(new IntegerNumber(nroots[i-1][1]));
						else
							opp[opos++] = new UnprintableMultiplication(new IntegerNumber(cn), new Sqrt(new IntegerNumber(nroots[i-1][1])));
					cn = nroots[i][0] * signums[i];
				}
			if(cn!=0) 
				if(nroots[3][1]==1)
					opp[opos++] = new IntegerNumber(cn);
				else 
					if(cn==1)
						opp[opos++] = new Sqrt(new IntegerNumber(nroots[3][1]));
					else
						opp[opos++] = new UnprintableMultiplication(new IntegerNumber(cn), new Sqrt(new IntegerNumber(nroots[3][1])));

			if(opos==4)	//nothing has changed since last step, so we already have the solution
				result = ops[2];
			else {
				if(opp[0]==null)
					result = new IntegerNumber(0);
				else
					if(opos==1)
						result = opp[0];
					else {
						result = new Addition(opp[0],opp[1]);
						for(int i=2;i<opos;i++) 
							result = new Addition(result, opp[i]);
					}
				solution.add(result);
			}
    	}
    }
    
	static MathsOp composeRoot(int[] num) {
		if(num[0]>1 && num[1]>1)
			return new UnprintableMultiplication(new IntegerNumber(num[0]), new Sqrt(new IntegerNumber(num[1])));
		else if(num[0]>1)
			return new IntegerNumber(num[0]);
		else
			return new Sqrt(new IntegerNumber(num[1]));
	}
	static int[] extractRoot(int num) {
		int i=2;
		int no = 1, ni = 1;
		while(num>1){
			if(num%(i*i)==0) {
				no *= i;
				num /= i*i;
			} else if(num%i==0) {
				ni *= i;
				num /= i;
				i++;
			} else
				i++;
		}
		return new int[]{no,ni};
	}
	
	static MathsOp multiplyRoots(int num1, int num2){
    	if(num1==num2) {
    		return new IntegerNumber(num1);
    	}
    	else
    		return new Sqrt(new IntegerNumber(num1*num2));
	}
	
    MathsOp[] composeOps(MathsOp[] opp) {
    	MathsOp[] ops = new MathsOp[3];
    	if(op2 instanceof Subtraction) 
    		ops[0] = new Subtraction(opp[0], opp[1]);
    	else
    		ops[0] = new Addition(opp[0], opp[1]);
    	if(op1 instanceof Subtraction) 
    		ops[1] = new Subtraction(ops[0], opp[2]);
    	else
    		ops[1] = new Addition(ops[0], opp[2]);
    	if(op2 instanceof Subtraction ^ op1 instanceof Subtraction) 
    		ops[2] = new Subtraction(ops[1], opp[3]);
    	else
    		ops[2] = new Addition(ops[1], opp[3]);
    	solution.add(result = ops[2]);
    	return ops;
    }
    
	public String getSection(String name) {
		if(name.equals("question"))
			return "\\ensuremath{" + question.toString() + "}";
		else if (name.equals("solution"))
			return composeSolution();
		else if (name.equals("shortanswer"))
			return "\\ensuremath{" + result.toString() + "}";
		return super.getSection(name);
	}

	private String composeSolution() {
		String res = "\\begin{align*}"+solution.getFirst().toString();
		ListIterator it = solution.listIterator(0);
		it.next();
		while(it.hasNext()) 
			res = res + "&=" + it.next().toString()+"\\\\";
	    res+="\\end{align*}";
		return res;
	}
}