package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Addition;

/**
 * Describe class PythonAdd here.
 *
 *
 * Created: Fri Nov 23 10:55:14 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonAdd extends PythonBinaryOp {

    private static final int ADD_PRECEDENCE = 12;

    private static final String PLUS_SIGN = "+";

    /**
     * Creates a new <code>PythonAdd</code> instance.
     *
     */
    public PythonAdd(PythonMaths n1, PythonMaths n2) {
        super(ADD_PRECEDENCE, n1, n2, PLUS_SIGN);
    }

    public Addition toMathsOp() {
        return new Addition(leftMaths(), rightMaths());
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        return v1 + v2;
    }

}
