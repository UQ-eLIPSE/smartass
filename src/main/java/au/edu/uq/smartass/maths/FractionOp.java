package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.BinaryOp;

public class FractionOp extends BinaryOp  implements FloatCalculable{
	final String FRACTION_TEX = "\\dfrac";
	/**
	 * Method FractionOp
	 *
	 *
	 */
	public FractionOp(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		if(getTex()==null)
			setTex(new String[]{FRACTION_TEX});
	};
	
	public String toString() {
		return getLocalTex()[0]+"{"+op[0].toString()+"}{"+op[1].toString()+"}";
	}	

	public double calculate() {
		return ((FloatCalculable)op[0]).calculate() / ((FloatCalculable)op[1]).calculate();
	}
}
