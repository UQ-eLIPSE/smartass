package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.UnaryOp;

public class AbsOp extends UnaryOp {
	
	/**
	 * Method AbsOp
	 *
	 *
	 */
	public AbsOp(MathsOp op) {
		super(op);
	}	
		
	public String toString() {
		return "\\left\\lvert " + op.toString() + "\\right\\rvert";
	}
		
}
