package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.UnaryMinus;

/**
 * Describe class PythonNegation here.
 *
 *
 * Created: Mon Dec  3 06:10:51 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonNegation extends PythonUnaryOp {

    final static int NEGATION_PRECEDENCE = 14;

    private final static String NEGATION_SYMBOL = "-";

    /**
     * Creates a new <code>PythonNegation</code> instance.
     *
     */
    public PythonNegation(PythonMaths n) {
        super(NEGATION_PRECEDENCE, n, NEGATION_SYMBOL);
    }

    public UnaryMinus toMathsOp() {
        return new UnaryMinus(n.toMathsOp());
    }

    protected double calculatedValue(double v, boolean isInt) {
        return -1*v;
    }

}
