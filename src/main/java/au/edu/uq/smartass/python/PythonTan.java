package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Tan;

/**
 * Describe class PythonTan here.
 *
 *
 * Created: Tue Dec  4 05:12:45 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonTan extends PythonUnaryOp {

    private static final String NAME = "tan";

    private static final double DIVIDER = Math.PI / 2;

    /**
     * Creates a new <code>PythonTan</code> instance.
     *
     */
    public PythonTan(PythonMaths n) {
        super(n, NAME);
    }

    public Tan toMathsOp() {
        return new Tan(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.tan(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

    public boolean canCalculate(double n) {
        return ((n / DIVIDER) % 2 != 0);
    }

}
