package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.UnaryOp;

public class Brackets extends UnaryOp {
	final String LEFT_BRACKET="\\left(";
	final String RIGHT_BRACKET="\\right)";
	
	public Brackets(MathsOp op) {
		super(op);
		if (getTex()==null)
			setTex(new String[]{LEFT_BRACKET, RIGHT_BRACKET});
	}	
		
	public String toString() {
		return getLocalTex()[0]+ op.toString() + getLocalTex()[1];
	}
}
 
