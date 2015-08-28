package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.FractionOp;

/**
 * Describe class PythonDivision here.
 *
 *
 * Created: Thu Dec  6 04:00:10 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonDivision extends PythonBinaryOp {

    private static final int DIV_PRECEDENCE = 13;

    private static final String DIV_SIGN = "/";

    /**
     * Creates a new <code>PythonDivision</code> instance.
     *
     */
    public PythonDivision(PythonMaths n1, PythonMaths n2) {
        super(DIV_PRECEDENCE, n1, n2, DIV_SIGN);
    }

    public FractionOp toMathsOp() {
        return new FractionOp(leftMaths(), rightMaths());
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        return v1 / v2;
    }

    protected boolean isIntResult(boolean b1, boolean b2, double v1, double v2) {
        //Python's new "true division" always returns a float (ignoring complex)
        return false;
    }

    protected boolean isValid(double v1, double v2) {
        return (v2 != 0);
    }

}
