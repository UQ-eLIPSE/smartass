package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.MathsOp;

public class BinaryOp extends MathsOp {
	protected MathsOp op[] = {null, null};
	
	BinaryOp(MathsOp op1, MathsOp op2) {
		op[0] = op1;
		op[1] = op2;
	}
	
	public MathsOp getOp(int position) {
		return op[position];
	}
	
	public void setOp(int position, MathsOp new_op) {
		op[position] = new_op;
	}
	
	public Object clone() {
		BinaryOp res = (BinaryOp) super.clone();
		res.op = (MathsOp[])res.op.clone();
		if(res.op[0]!=null)
			res.op[0] = (MathsOp) res.op[0].clone();
		if(res.op[1]!=null)
			res.op[1] = (MathsOp) res.op[1].clone();
		return res;
	} 
}
