package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Log;
import au.edu.uq.smartass.maths.IntegerNumber;

/**
 * Describe class PythonLog here.
 *
 *
 * Created: Wed Dec  5 01:35:43 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonLog10 extends PythonUnaryOp {

    private static final String NAME = "log10";

    private static final IntegerNumber BASE = new IntegerNumber(10);

    /**
     * Creates a new <code>PythonLog</code> instance.
     *
     */
    public PythonLog10(PythonMaths n) {
        super(n, NAME);
    }

    public Log toMathsOp() {
        return new Log(BASE,n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.log10(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

    public boolean canCalculate(double n) {
        return (n > 0);
    }

}
