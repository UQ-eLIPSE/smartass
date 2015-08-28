package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.BinaryOp;

public class Subtraction extends  BinaryOp implements FloatCalculable {
	
	public Subtraction(MathsOp op1, MathsOp op2) {
		super(op1, op2);
	}	

	public double calculate() {
		return ((FloatCalculable)op[0]).calculate() - ((FloatCalculable)op[1]).calculate();
	}

	public String toString() {
		String res = op[1].toString();
		if(res.charAt(0)=='-' || op[1] instanceof Addition || op[1] instanceof Subtraction)
			res = LEFT_BRACKET + res + RIGHT_BRACKET;
		return op[0].toString() + "-" + res; 
	}
}
