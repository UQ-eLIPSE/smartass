/**
 * 
 */
package au.edu.uq.smartass.maths;

/**
 * class Function 
 * function op implementation that always print brackets around its 
 * parameter(s)
 *
 */
public class Function extends AbstractFunction {
	/**
	 * @param op
	 */
	public Function(String op_name) {
		super(op_name);
	}

	public Function(String op_name, MathsOp op) {
		super(op_name, op);
	}

	public Function(String op_name, MathsOp[] ops) {
		super(op_name, ops);
	}
	
	public String toString() {
		String res = op_name + LEFT_BRACKET;
		if(opsCount()>0) {
			res = res + op[0].toString();
			for(int i=1; i<opsCount(); i++)
				res = res + ',' + op[i];
		}
		return res + RIGHT_BRACKET;
	}
}
