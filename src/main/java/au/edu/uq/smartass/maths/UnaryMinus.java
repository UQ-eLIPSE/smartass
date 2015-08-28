package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.FloatCalculable;

public class UnaryMinus extends UnaryOp implements FloatCalculable {
	public double calculate() {
		//ToDo: throw exception here - can't calculate!!!
		return - ((FloatCalculable)op).calculate();
	}
	
	public UnaryMinus(MathsOp op) {
		super(op);
	}	

	public String toString() {
		String res=op.toString();
		if(res.charAt(0)=='-' || op instanceof Addition || op instanceof Subtraction)
			return "-\\left(" + op.toString()+"\\right)";
		else
			return "-" + op.toString();
	}
}
