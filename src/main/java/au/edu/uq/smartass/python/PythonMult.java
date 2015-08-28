package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Multiplication;

/**
 * Describe class PythonMult here.
 *
 *
 * Created: Tue Nov 27 11:50:49 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonMult extends PythonBinaryOp {

    private static final int MULT_PRECEDENCE = 13;

    private static final String MULT_SIGN = "*";

    /**
     * Creates a new <code>PythonMult</code> instance.
     *
     */
    public PythonMult(PythonMaths n1, PythonMaths n2) {
        super(MULT_PRECEDENCE, n1, n2, MULT_SIGN);
    }

    public Multiplication toMathsOp() {
        return new Multiplication(leftMaths(), rightMaths());
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        return v1 * v2;
    }

}
