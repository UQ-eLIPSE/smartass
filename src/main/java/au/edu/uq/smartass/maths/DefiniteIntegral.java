/**
 * 
 */
package au.edu.uq.smartass.maths;

/**
 * @author carnivore
 *
 */
public class DefiniteIntegral extends MathsOp {
	MathsOp hi, lo, expr, v;
	private String[] texString={"\\displaystyle \\int_{","}^{","} \\left(", "\\right) d"}; 
	/**
	 * @param hi	high limit
	 * @param lo 	low limit
	 * @param expr	expression under integral sign
	 * @param v		variable of integration
	 */
	public DefiniteIntegral(MathsOp hi, MathsOp lo, MathsOp expr, MathsOp v) {
	    super();
		this.hi = hi;
		this.lo = lo;
		this.expr = expr;
		this.v = v;
		if (getTex()==null)
			setTex(texString);	
	}

	public String toString() {
		String[] currentTex=getLocalTex();
		return currentTex[0]+ lo + currentTex[1] + hi +currentTex[2] + expr +currentTex[3] + v;
	}
}
