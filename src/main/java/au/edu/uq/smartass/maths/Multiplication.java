package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.BinaryOp;

public class Multiplication extends BinaryOp  implements FloatCalculable{
	
	public Multiplication(MathsOp op1, MathsOp op2) {
		super(op1, op2);
	}	

	public double calculate() {
		return ((FloatCalculable)op[0]).calculate() * ((FloatCalculable)op[1]).calculate();
	}

	public String toString() {
		String res = op[1].toString();
		if(res.charAt(0)=='-' || op[1] instanceof Addition || op[1] instanceof Subtraction)
			res = LEFT_BRACKET + res + RIGHT_BRACKET;
		if(op[0] instanceof Addition || op[0] instanceof Subtraction)
			return LEFT_BRACKET + op[0].toString() + RIGHT_BRACKET + " \\times " + res; 
		else
			return op[0].toString() + " \\times " + res; 
	}
}
