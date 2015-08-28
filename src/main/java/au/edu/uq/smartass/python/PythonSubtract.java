package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Subtraction;

/**
 * Describe class PythonSubtract here.
 *
 *
 * Created: Fri Nov 23 10:55:14 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonSubtract extends PythonBinaryOp {

    private static final int SUB_PRECEDENCE = 12;

    private static final String MINUS_SIGN = "-";

    /**
     * Creates a new <code>PythonSubtract</code> instance.
     *
     */
    public PythonSubtract(PythonMaths n1, PythonMaths n2) {
        super(SUB_PRECEDENCE, n1, n2, MINUS_SIGN, Assocs.LEFT);
    }

    public Subtraction toMathsOp() {
        return new Subtraction(leftMaths(), rightMaths());
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        return v1 - v2;
    }

}
