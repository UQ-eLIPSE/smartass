/**
 * @(#)Equality.java
 *
 *
 * @author 
 * @version 1.00 2007/1/15
 */

package au.edu.uq.smartass.maths;

public class Equality extends BinaryOp {
    final String EQUAL_SIGN ="="; //default tex
    public Equality(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		if (getTex()==null)
  			setTex(new String[]{EQUAL_SIGN});
	}	
	public String toString() {
		return 	op[0].toString() +" "+ getTex()[0] + " "+ op[1].toString(); 
	}
	

}