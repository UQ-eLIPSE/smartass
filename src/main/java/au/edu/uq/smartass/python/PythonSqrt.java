package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Sqrt;

/**
 * Describe class PythonSqrt here.
 *
 *
 * Created: Tue Dec  4 04:50:55 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonSqrt extends PythonUnaryOp {

    private static final String NAME = "sqrt";

    /**
     * Creates a new <code>PythonSqrt</code> instance.
     *
     */
    public PythonSqrt(PythonMaths n) {
        super(n, NAME);
    }

    public Sqrt toMathsOp() {
        return new Sqrt(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.sqrt(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

    public boolean canCalculate(double n) {
        return (n > 0);
    }

}
