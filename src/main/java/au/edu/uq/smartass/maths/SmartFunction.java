package au.edu.uq.smartass.maths;

/**
 * class SmartFunction 
 * "smart" function op implementation that decides whether it prints brackets around its 
 * parameter(s) or not
 *
 */
public class SmartFunction extends AbstractFunction {

	public SmartFunction(String op_name) {
		super(op_name);
	}

	public SmartFunction(String op_name, MathsOp op) {
		super(op_name, op);
	}

	public SmartFunction(String op_name, MathsOp[] ops) {
		super(op_name, ops);
	}

	protected String opsToString() {
		if(opsCount()==0)
			return "";
		else {
			String res = op[0].toString();
			for(int i=1; i<opsCount(); i++)
				res = res + ',' + op[i];
			return res;
		}
	}
	
	public String toString() {
		String res = op_name;
		String ops = opsToString();
		//if function has more then one parameter, use brackets
		if(opsCount()==1 &&
				(op[0] instanceof Variable || op[0] instanceof IntegerNumber 
					|| op[0] instanceof DecimalNumber || op[0] instanceof FractionOp
					|| op[0] instanceof Brackets || op[0] instanceof Power)
					&& ops.trim().charAt(0)!='-')
			return res + " " + ops;
		return res + "\\left(" + ops + "\\right)";
	}
}
