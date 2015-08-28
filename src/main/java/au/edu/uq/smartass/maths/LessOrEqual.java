package au.edu.uq.smartass.maths;

public class LessOrEqual extends BinaryOp {
	public LessOrEqual(MathsOp op1, MathsOp op2) {
		super(op1, op2);
	}

	public String toString() {
		return op[0].toString() +" "+ LESS_OR_EQUAL_SIGN+" " + op[1].toString(); 
	}
	
}
