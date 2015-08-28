package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Ln;

/**
 * Describe class PythonLn here.
 *
 *
 * Created: Wed Dec  5 01:35:43 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonLn extends PythonUnaryOp {

    private static final String NAME = "log";

    /**
     * Creates a new <code>PythonLn</code> instance.
     *
     */
    public PythonLn(PythonMaths n) {
        super(n, NAME);
    }

    public Ln toMathsOp() {
        return new Ln(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return Math.log(v);
    }

    protected boolean isIntResult(boolean b, double v) {
        return false;
    }

    public boolean canCalculate(double n) {
        return (n > 0);
    }
}
