/**
 * 
 */
package au.edu.uq.smartass.maths;

import java.math.BigDecimal;

/**
 * @author carnivore
 *
 */
public class SingleInterval {
	static public BigDecimal NEGATIVE_INF = new BigDecimal(-1000000);  
	static public BigDecimal POSITIVE_INF = new BigDecimal(1000000);
	
	static protected int scale = 0;
	
	BigDecimal l, r;
	boolean l_inclusive, r_inclusive;

	/**
	 * 
	 */
	public SingleInterval(int left, int right, boolean left_inclusive, boolean right_inclusive) {
		super();
		l = new BigDecimal(left).setScale(scale);
		r = new BigDecimal(right).setScale(scale);
		l_inclusive = left_inclusive;
		r_inclusive = right_inclusive;
	}

	public SingleInterval(double left, double right, boolean left_inclusive, boolean right_inclusive) {
		super();
		l = new BigDecimal(left);
		r = new BigDecimal(right);
		l_inclusive = left_inclusive;
		r_inclusive = right_inclusive;
	}

	public SingleInterval(BigDecimal left, BigDecimal right, boolean left_inclusive, boolean right_inclusive) {
		super();
		l = left;
		r = right;
		l_inclusive = left_inclusive;
		r_inclusive = right_inclusive;
	}

	public String toString() {
		String s;
		if(l_inclusive) 
			s = "\\left[";
		else
			s = "\\left(";
		if(l.compareTo(NEGATIVE_INF)==0)
			s = s + "-\\infty"; 
		else if(l.compareTo(POSITIVE_INF)==0)
			s = s + "\\infty"; 
		else
			s = s + l;
		s = s + ",";
		if(r.compareTo(NEGATIVE_INF)==0)
			s = s + "-\\infty"; 
		else if(r.compareTo(POSITIVE_INF)==0)
			s = s + "\\infty"; 
		else
			s = s + r;
		if(r_inclusive) 
			s = s + "\\right]";
		else
			s = s + "\\right)";
		return s;
	}
	
	public MathsOp asComparison(MathsOp var) {
		if(l.compareTo(r)==0)
			return new Equality(var, new DecimalNumber(l, 0));
		else if(l.compareTo(NEGATIVE_INF)==0 && r.compareTo(POSITIVE_INF)==0)
			return new Less(new Variable("-\\infty"), new Less(var, new Variable("\\infty")));
		else if(l.compareTo(NEGATIVE_INF)==0) {
			if(r_inclusive)
				return new LessOrEqual(var, new DecimalNumber(r, 0));
			else
				return new Less(var, new DecimalNumber(r, 0));
		} else if(r.compareTo(POSITIVE_INF)==0) {
			if(l_inclusive)
				return new GreaterOrEqual(var, new DecimalNumber(l, 0));
			else
				return new Greater(var, new DecimalNumber(l, 0));
		} else {
			MathsOp ineq;
			if(r_inclusive)
				ineq = new LessOrEqual(var, new DecimalNumber(r, 0));
			else
				ineq = new Less(var, new DecimalNumber(r, 0));
			if(l_inclusive)
				ineq = new LessOrEqual(new DecimalNumber(l, 0), ineq);
			else
				ineq = new Less(new DecimalNumber(l, 0), ineq);
			return ineq;
		}
	}
	
	public void shift(BigDecimal val) {
		if(l.compareTo(NEGATIVE_INF)!=0 && l.compareTo(POSITIVE_INF)!=0)
			l = l.add(val);
		if(r.compareTo(NEGATIVE_INF)!=0 && r.compareTo(POSITIVE_INF)!=0)
			r = r.add(val);
	}
	
	public void scale(BigDecimal val) {
		if(l.compareTo(NEGATIVE_INF)!=0 && l.compareTo(POSITIVE_INF)!=0)
			l = l.multiply(val);
		if(r.compareTo(NEGATIVE_INF)!=0 && r.compareTo(POSITIVE_INF)!=0)
			r = r.multiply(val);
	}
	
	public void mirror() {
		BigDecimal t = l;
		l = BigDecimal.ZERO.subtract(r);
		r = BigDecimal.ZERO.subtract(t);
		boolean b = l_inclusive;
		l_inclusive = r_inclusive;
		r_inclusive = b;
	}
	
	public Object clone() {
/*		try {
			return super.clone();
		} catch(CloneNotSupportedException e) {
		}*/
		return new SingleInterval(l, r, l_inclusive, r_inclusive);
	}
	
	public static int getDefaultScale() {
		return scale;
	}
	
	public static void setDefaultScale(int new_scale) {
		scale = new_scale;
	}
}
