package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.MathsOp;

abstract public class SetBinaryOp extends SetOp {
	protected SetOp op[] = {null, null};
	
	SetBinaryOp(SetOp op1, SetOp op2) {
		op[0] = op1;
		op[1] = op2;
	}
	
	public SetOp getOp(int position) {
		return op[position];
	}
	
	public void setOp(int position, SetOp new_op) {
		op[position] = new_op;
	}
	
	public Object clone() {
		SetBinaryOp res = (SetBinaryOp) super.clone();
		res.op = (SetOp[])res.op.clone();
		if(res.op[0]!=null)
			res.op[0] = (SetOp) res.op[0].clone();
		if(res.op[1]!=null)
			res.op[1] = (SetOp) res.op[1].clone();
		return res;
	}
	
	
	 
}
