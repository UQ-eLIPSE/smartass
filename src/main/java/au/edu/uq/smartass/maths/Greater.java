package au.edu.uq.smartass.maths;

public class Greater extends BinaryOp {
	public Greater(MathsOp op1, MathsOp op2) {
		super(op1, op2);
	}

	public String toString() {
		return op[0].toString() + " "+GREATER_SIGN+" "+ op[1].toString(); 
	}
	
}
