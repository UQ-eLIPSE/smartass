/**
 * @(#)Sqrt.java
 *
 *
 * @author 
 * @version 1.00 2007/1/11
 */

package au.edu.uq.smartass.maths;

import au.edu.uq.smartass.maths.MathsOp;

public class Sqrt  extends UnaryOp {
	private String[]  texString= {"\\sqrt{","}"};
    public Sqrt(MathsOp op) {
    	super(op);
    	if(getTex()==null)
			setTex(texString);
    }
    
    public String toString() {
    	String[] currentTex=getLocalTex();
    	return currentTex[0] + op.toString() + currentTex[1];
    }
}