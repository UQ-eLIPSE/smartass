package au.edu.uq.smartass.maths;

public class LineBreakEqualityOp extends BinaryOp {

	public LineBreakEqualityOp(MathsOp op1, MathsOp op2) {
		super(op1, op2);
	}
	
	@Override
	public String toString() {
		return "\\ensuremath{" + op[0].toString() + "} = \\\\ = \\ensuremath{" + op[1].toString() +"}";
	}

}
