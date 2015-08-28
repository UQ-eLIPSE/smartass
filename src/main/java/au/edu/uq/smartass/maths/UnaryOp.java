package au.edu.uq.smartass.maths;

public class UnaryOp extends MathsOp {
	protected MathsOp op;
	
	UnaryOp(MathsOp op) {
		this.op = op;
	}

	public MathsOp getOp() {
		return op;
	}
	
	public void setOp(MathsOp new_op) {
		op = new_op;
	}

	public Object clone() {
		UnaryOp res = (UnaryOp) super.clone();
		if(op!=null)
			res.op = (MathsOp) ((MathsOp)op).clone();
		return res;
	} 
}
