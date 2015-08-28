/*
 * MathsUtils.java
 *
 *
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.auxiliary.RandomChoice;
import java.util.Vector;

/**
 * Utility for MathsModules that operate with variables
 * 
 */
public class MathsUtils {
    final static String[] varss = {"x","y","z"};
    
    /**
     * Create Variable with random name from the given set of var names 
     * 
     * @param	vars	list of possible variable names 
     * @return created Variable object
     */
    public static Variable createRandomVar(String vars[]) {
        return new Variable(vars[RandomChoice.randInt(0,vars.length-1)]);
    }
    
    /**
     * Create Variable object with random name from default set of var names - x,y,z
     * 
     * @return created Variable object
     */
    public static Variable createRandomVar() {
        return createRandomVar(varss);
    }
    
    /**
     * Creates Op for operation like a*x, taking into account special
     * cases like a=1 or a=-1 
     * 
     * @param constant	number to variable will be multiplied
     * @param vx	variable
     * @return	created operation 
     */
    public static MathsOp multiplyVarToConst(int constant, MathsOp vx){
        if(constant==1)
            return vx;
        else if(constant==-1)
            return new UnaryMinus(vx);
        else if(constant==0)
        	return new IntegerNumber(0);
        else if (vx instanceof FractionOp)        	  
        	return new Multiplication(new IntegerNumber(constant), vx);
        else	
            return new UnprintableMultiplication(new IntegerNumber(constant), vx);
    }

    /**
     * Creates Op for the substitution of the value of variable into equation like f(x)=a*x,
     * f(-1)=a*(-1)
     * taking into account special
     * cases like a=0, a=1 or a=-1 
     * 
     * @param coef	coefficient a
     * @param number number to substitute
     * @return	created operation 
     */
  public static MathsOp coefTimesNumber(int coef, MathsOp number){
    	if(coef==1)
            return number;
        else if(coef==-1)
            return new UnaryMinus(number);
        else 
        	 if(coef==0)
        	return new IntegerNumber(0);
        else        	  
            return new Multiplication(new IntegerNumber(coef), number);
    }
    
    
    public static MathsOp multiplyConstToPower(int constant, MathsOp vx, int power) {
    	if(power==0)
    		return new IntegerNumber(constant);
    	else if(power==1)
    		return multiplyVarToConst(constant, vx);
    	return multiplyVarToConst(constant, new Power(vx, new IntegerNumber(power)));
    }
    
    /**
     * Takes a Vector of MathsOp and convert it into String  
     * with solution steps delimited by string given in second parameter   
     * 
     * @param solution	list of solution steps 
     * @param delimiter	delimiter between steps
     * @return String of composed solution
     */
    public static String composeSolution(Vector<MathsOp> solution, String delimiter) {
    	if(solution.size()==0)
    		return "";
    	String res = "\\ensuremath{" + solution.get(0).toString() + "}";
    	for(int i=1;i<solution.size()-1;i++)
            res = res + delimiter +"\\ensuremath{" + solution.get(i).toString() + "}";
    	return res;
    }

    /**
     * Takes a Vector of MathsOp and convert it into String  
     * with solution steps delimited by default " = " clause   
     * 
     * @param solution	list of solution steps 
     * @return String of composed solution
     */
    public static String composeSolution(Vector<MathsOp> solution) {
    	return composeSolution(solution, " = ");
    }
    
     /**
     * Takes two terms MathsOp op1, MathsOp op2, 
     * returns op1 if op2 is IntegerNumber(0),
     * returns op2 if op1 is IntegerNumber(0),
     * otherwise returns Addition(op1,op2)
     * 
     * @param op1	 first term to add
     * @param op2    second term to add 
     * @return MathsOp resulting MathsOp
     */
    public static MathsOp addTwoTermsNoZeros(MathsOp op1, MathsOp op2){
    		if ((op1 instanceof IntegerNumber) && (((IntegerNumber)op1).getInt()==0))
    			return op2;
    		if ((op2 instanceof IntegerNumber) && (((IntegerNumber)op2).getInt()==0))
    			return op1;
    	return new Addition(op1, op2);	
    }

    public static MathsOp createPiFraction(int mul, int div) {
		if(mul==0) 
			return new IntegerNumber(0);
		boolean sign = (mul*div>0);
		mul = Math.abs(mul);
		div = Math.abs(div);
		MathsOp op = multiplyVarToConst(mul, new Variable("\\pi"));
		if(div!=1)
			op = new FractionOp(op, new IntegerNumber(div));
		if(!sign)
			op = new UnaryMinus(op);
		return op;
    }
    
    public static MathsOp createPiDivision(int mul, int div) {
		if(mul==0) 
			return new IntegerNumber(0);
		boolean sign = (mul*div>0);
		mul = Math.abs(mul);
		div = Math.abs(div);
		MathsOp op = multiplyVarToConst(mul, new Variable("\\pi"));
		if(div!=1)
			op = new UnprintableMultiplication(new UnprintableMultiplication(op, new Variable("/")), new IntegerNumber(div));
		if(!sign)
			op = new UnaryMinus(op);
		return op;
    }
    
    /** Creates a new instance of VariableUtils */
    public MathsUtils() {
    }
    
    
}
