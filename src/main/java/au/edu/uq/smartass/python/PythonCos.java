package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Cos;

/**
 * Describe class PythonCos here.
 *
 *
 * Created: Tue Dec  4 05:12:45 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonCos extends PythonUnaryOp {

    private static final String NAME = "cos";

    /**
     * Creates a new <code>PythonCos</code> instance.
     *
     */
    public PythonCos(PythonMaths n) {
        super(n, NAME);
    }

    public Cos toMathsOp() {
        return new Cos(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.cos(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

}
