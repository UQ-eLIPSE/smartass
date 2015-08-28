/*
 * Power.java
 */

package au.edu.uq.smartass.maths;

/**
 * class Power - implements power operation in LaTeX
 */
public class Power extends BinaryOp {
    private String[] texString={"","^{","}"};
    /** Creates a new instance of Power 
     *
     * @param  op1  Op that is raised to op2 power
     * @param  op2  Op that represent a power to which op1 is raised
     */
    public Power(MathsOp op1, MathsOp op2) {
	super(op1, op2);
	if (getTex()==null)
			setTex(texString);
    }
    
    /** compose LaTeX representation of Op
     * 
     */
    public String toString() {
    	String[] currentTex=getLocalTex();
        String res = op[0].toString();
        //Need brackets around first Op?
        if(op[0] instanceof UnaryOp || op[0] instanceof BinaryOp || res.trim().charAt(0)=='-')
            res = LEFT_BRACKET + res + RIGHT_BRACKET;
        return currentTex[0]+res + currentTex[1] + op[1] + currentTex[2];
    }
    
}
