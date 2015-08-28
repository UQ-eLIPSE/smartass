package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Power;
import au.edu.uq.smartass.maths.Variable;

/**
 * Describe class PythonLog here.
 *
 *
 * Created: Wed Dec  5 01:35:43 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonExp extends PythonUnaryOp {

    private static final String NAME = "exp";

    private static final Variable BASE = new Variable("e");

    /**
     * Creates a new <code>PythonLog</code> instance.
     *
     */
    public PythonExp(PythonMaths n) {
        super(n, NAME);
    }

    public Power toMathsOp() {
        return new Power(BASE,n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.exp(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }
}
