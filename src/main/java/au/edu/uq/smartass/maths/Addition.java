package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.BinaryOp;

public class Addition extends BinaryOp  implements FloatCalculable {
	final String PLUS_SIGN = "+";
	
	public Addition(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		if(getTex()==null)
			setTex(new String[]{PLUS_SIGN});
	}	
	
	public String toString(){
		String result = op[1].toString();
		if(result.trim().charAt(0)!='-') 
			result = getTex()[0] + result;
		return op[0].toString() + result;
	}

	public double calculate() {
		return ((FloatCalculable)op[0]).calculate() + ((FloatCalculable)op[1]).calculate();
	}
}
