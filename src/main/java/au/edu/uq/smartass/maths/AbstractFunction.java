package au.edu.uq.smartass.maths;

/**
 * 
 * class AbstractFunction - implements behavior that is common
 * for any function op (e.g. f(x), ln x, sin(5x-2) etc) 
 * but without TeX representation  
 *
 */
public class AbstractFunction extends MathsOp {
	protected String op_name;
	protected MathsOp[] op;
	
	/**
	 * @param op
	 */
	public AbstractFunction(String op_name) {
		super();
		this.op_name = op_name; 
		this.op = new MathsOp[]{};
	}

	public AbstractFunction(String op_name, MathsOp op) {
		super();
		this.op_name = op_name; 
		this.op = new MathsOp[]{op};
	}

	public AbstractFunction(String op_name, MathsOp[] ops) {
		super();
		this.op_name = op_name; 
		this.op = ops;
	}
	
	public int opsCount() {
		return op.length;
	}
	
	public MathsOp getOp(int position) {
		return op[position];
	}
	
	public void setOp(int position, MathsOp new_op) {
		op[position] = new_op;
	}
	
	public String getName() {
		return op_name;
	}
	
	public Object clone() {
		AbstractFunction f = (AbstractFunction) super.clone();
		f.op = op.clone();
		for(int i=0;i<op.length;i++)
			if(op[i]!=null)
				f.op[i] = (MathsOp)op[i].clone();
		return f;
	}
}
