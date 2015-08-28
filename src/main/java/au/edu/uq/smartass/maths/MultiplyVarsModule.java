/*
 * MultiplyVarsModule.java
 *
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.engine.*;
import au.edu.uq.smartass.auxiliary.*;
import java.util.*;

/**
 * MultiplyVarsModule: generates solutions for questions
 * like ax(bx+c)  or (bx+c)*ax or (c+bx)*ax, ax*(c+bx)
 */
public class MultiplyVarsModule extends MathsModule {
    final int MAX_NUMBER = 7;
    Variable vx;
    Vector<MathsOp> solution = new Vector<MathsOp>();
    MathsOp question, answer;
    
    /** Creates a new instance of MultiplyVarsModule 
     * @param engine
     */
    public MultiplyVarsModule(Engine engine) {
    	super(engine);
        
        //generate a, b, c
        int[] nums = {
            RandomChoice.randInt(1, MAX_NUMBER),
            RandomChoice.randInt(1, MAX_NUMBER),
            RandomChoice.randInt(1, MAX_NUMBER)};
        for(int i=0;i<3;i++)
            if(RandomChoice.randInt(0,1)==0)
                nums[i] = -nums[i];
                
        vx = MathsUtils.createRandomVar();
        //like ax(bx+c) or (bx+c)*ax?
        boolean order = (RandomChoice.randInt(0,1)==1);
        boolean bxFirst=(RandomChoice.randInt(0,1)==1);

        MathsOp op1 = MathsUtils.multiplyVarToConst(nums[0],vx);
        MathsOp op2;
        if (bxFirst)  //(bx+c)
        //generate question/first step of solution
        	op2 = new Addition(
            MathsUtils.multiplyVarToConst(nums[1], vx),
            new IntegerNumber(nums[2]));
        else //(c+bx)
        	op2 = new Addition( new IntegerNumber(nums[2]),
            MathsUtils.multiplyVarToConst(nums[1], vx));
        if(order)
            solution.add(question = new UnprintableMultiplication(op1,op2));
        else
            solution.add(question = new UnprintableMultiplication(op2,op1));
        
        //generate second step of solution
        
        
        
        op2 =  MathsUtils.multiplyVarToConst(nums[1], vx);                
        MathsOp op3 = new IntegerNumber(nums[2]);   
        if (bxFirst) {

            solution.add(
                new Addition(
                    new Multiplication(op2,op1),
                    new Multiplication(op3,op1)) );        
        
        
        //generate last step of solution
        solution.add(answer = 
            new Addition(
                MathsUtils.multiplyVarToConst(nums[0]*nums[1], new Power(vx, new IntegerNumber(2))),
                MathsUtils.multiplyVarToConst(nums[0]*nums[2], vx)) );
    }
       else { //(c+bx)

            solution.add(
                new Addition(
                    new Multiplication(op3,op1),
                    new Multiplication(op2,op1)) );        
        
        
        //generate last step of solution
        solution.add(answer = 
            new Addition(MathsUtils.multiplyVarToConst(nums[0]*nums[2], vx),
                MathsUtils.multiplyVarToConst(nums[0]*nums[1], new Power(vx, new IntegerNumber(2)))));
       }
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
		return "\\ensuremath{" + solution.get(0) + " = " + solution.get(1) + " = " + solution.get(2) + "}";
	else if (name.equals("shortanswer"))
		return "\\ensuremath{" + answer.toString() + "}";
	else if (name.equals("varname"))
		return "\\ensuremath{" + vx.toString() + "}";
	return super.getSection(name);
    }
}
