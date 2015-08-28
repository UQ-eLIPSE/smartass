package au.edu.uq.smartass.maths;

public class TernaryOp extends MathsOp {
	MathsOp[] op = new MathsOp[3];

	public TernaryOp(MathsOp op1, MathsOp op2, MathsOp op3) {
		super();
		
		op[0] = op1;
		op[1] = op2;
		op[2] = op3;
	}
	
	public MathsOp getOp(int position) {
		return op[position];
	}
	
	public void setOp(int position, MathsOp new_op) {
		op[position] = new_op;
	}

}

