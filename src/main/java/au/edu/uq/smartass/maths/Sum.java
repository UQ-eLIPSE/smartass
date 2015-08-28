package au.edu.uq.smartass.maths;

public class Sum extends TernaryOp {

	public Sum(MathsOp low_iter, MathsOp high_iter, MathsOp operand) {
		super(low_iter, high_iter, operand);
	}

	public String toString() {
		return "\\displaystyle\\sum_{" + op[0].toString() + "}^{" + op[1].toString() + 
				"} " + op[2].toString();   
	}
}
