package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Power;

/**
 * Describe class PythonPower here.
 *
 *
 * Created: Tue Nov 27 13:45:18 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonPower extends PythonBinaryOp {

    private static final int EXP_PRECEDENCE = 16;

    private static final String EXP_SIGN = "**";

    /**
     * Creates a new <code>PythonPower</code> instance.
     *
     */
    public PythonPower(PythonMaths n1, PythonMaths n2) {
        //Technically, Python treats powers as being right associative,
        //but it's easier to read if its interpreted as bein not associative
        super(EXP_PRECEDENCE, n1, n2, EXP_SIGN, Assocs.NOT);
    }

    public Power toMathsOp() {
        return new Power(leftMaths(), rightMaths());
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        return Math.pow(v1, v2);
    }

    protected boolean isIntResult(boolean b1, boolean b2, double v1, double v2) {
        return b1 && b2 && (v2 >= 0);
    }

    public static PythonPower square(PythonMaths m) {
        return new PythonPower(m, new PythonNumber(2));
    }

    public static PythonPower pTen(PythonMaths p) {
        return new PythonPower(new PythonNumber(10.0), p);
    }
}
