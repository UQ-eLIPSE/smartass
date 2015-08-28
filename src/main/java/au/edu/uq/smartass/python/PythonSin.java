package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Sin;

/**
 * Describe class PythonSin here.
 *
 *
 * Created: Tue Dec  4 05:12:45 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonSin extends PythonUnaryOp {

    private static final String NAME = "sin";

    /**
     * Creates a new <code>PythonSin</code> instance.
     *
     */
    public PythonSin(PythonMaths n) {
        super(n, NAME);
    }

    public Sin toMathsOp() {
        return new Sin(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.sin(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

}
