/*
 * MultiplyVars2Module.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.auxiliary.RandomChoice;
import java.util.Vector;

/**
 * MultiplyVars2Module: generates solutions for questions
 * like (ax+b)*(cx+d)
 */
public class MultiplyVars2Module  extends MathsModule {
    final int MAX_NUMBER = 7;
    Variable vx;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    MathsOp question, answer;
    
    /** Creates a new instance of MultiplyVars2Module
     *
     * @param engine    an instance of Engine from application thal creates module
     */
    public MultiplyVars2Module(Engine engine) {
    	super(engine);
        
        //generate a, b, c, d
        int[] nums = {
            RandomChoice.randInt(1, MAX_NUMBER),
            RandomChoice.randInt(1, MAX_NUMBER),
            RandomChoice.randInt(1, MAX_NUMBER),
            RandomChoice.randInt(1, MAX_NUMBER)};
        for(int i=0;i<4;i++)
            if(RandomChoice.randInt(0,2)==0)
                nums[i] = -nums[i];
        vx = MathsUtils.createRandomVar();
        //prepare inner ops 
        MathsOp []ops=new MathsOp[4];
        int[] powers=new int[4];
        int[] finalCoefs= {1, 0, 0};
        int a,b,c,d;
        powers[0]=RandomChoice.randInt(0,1);
        powers[1]=(powers[0]+1)%2;
        powers[2]=RandomChoice.randInt(0,1);
        powers[3]=(powers[2]+1)%2; 
        
        a=nums[powers[1]];
        b=nums[powers[0]];
        c=nums[powers[3]+2];
        d=nums[powers[2]+2];
        
        for (int i=0; i<4; i++)
        	if (powers[i]==0)
                ops[i]=new IntegerNumber(nums[i]);
            else ops[i]=MathsUtils.multiplyVarToConst(nums[i],vx);
                
 /////////////////////////////////////////////////        
      /*  if (RandomChoice.randInt(0,1)==0)
        {ops[0]=MathsUtils.multiplyVarToConst(nums[0],vx);
         ops[1]=new IntegerNumber(nums[1]);
        } else {
         ops[1]=MathsUtils.multiplyVarToConst(nums[0],vx);
         ops[0]=new IntegerNumber(nums[1]);	
        }
        if (RandomChoice.randInt(0,1)==0)
         = {
            MathsUtils.multiplyVarToConst(nums[0],vx),
            new IntegerNumber(nums[1]),
            MathsUtils.multiplyVarToConst(nums[2],vx),
            new IntegerNumber(nums[3])
        };
*/        
        //step 1/question
        solution.add(question = 
            new UnprintableMultiplication(
                new Addition(ops[0], ops[1]),
                new Addition(ops[2], ops[3])) );
        
        //step 2
        solution.add(
            new Addition(
                new Multiplication(
                    ops[0],ops[2]),
                new Addition(
                    new Multiplication(ops[0],ops[3]),
                    new Addition(
                        new Multiplication(ops[1],ops[2]),
                        new Multiplication(ops[1],ops[3])))) );

        //step 3
        	
        ops[0]=createTerm (vx, powers[0], powers[2], nums[0], nums[2]);
        ops[1]=createTerm (vx, powers[0], powers[3], nums[0], nums[3]);
        ops[2]=createTerm (vx, powers[1], powers[2], nums[1], nums[2]);
        ops[3]=createTerm (vx, powers[1], powers[3], nums[1], nums[3]);
          
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       	
        	
        solution.add(
            new Addition(
                    ops[0],
                new Addition(
                    ops[1],
                    new Addition(
                         ops[2], ops[3])) ));
        
        //step 4/answer
      
        if ((a*d+b*c)==0)
         solution.add(answer = 
            new Addition(
                MathsUtils.multiplyVarToConst(
                    a*c,
                    new Power(vx,new IntegerNumber(2))),
                    new IntegerNumber(b*d)));	
        else 	
         solution.add(answer = 
            new Addition(
                MathsUtils.multiplyVarToConst(
                    a*c,
                    new Power(vx,new IntegerNumber(2))),
                new Addition(
                    MathsUtils.multiplyVarToConst(
                        a*d+b*c,
                        vx),
                    new IntegerNumber(b*d))) );
    }
    
    /**
     * Composes section with given name
     * "question", "shortanswer", "solution" and "varname" sections is recognised
     * 
     * @param name section name
     **/
    public String getSection(String name) {
	if(name.equals("question"))
		return "\\ensuremath{" + question.toString() + "}";
	else if (name.equals("solution"))
		return "\\ensuremath{" + solution.get(0) + " = " + solution.get(1) + " = " + solution.get(2) + " = " + solution.get(3) + "}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	return super.getSection(name);
    }
    
    private MathsOp createTerm (Variable v, int power1, int power2, int coef1, int coef2){
    	int pow=power1+power2;
    	if (pow == 0)
    		return new IntegerNumber(coef1*coef2);
    	else 
    	 if	(pow == 1)
    		return MathsUtils.multiplyVarToConst(coef1*coef2, v);
    	else 
    	  return MathsUtils.multiplyVarToConst(coef1*coef2, new Power(v, new IntegerNumber(pow) ));
    	  
    }
    
}
