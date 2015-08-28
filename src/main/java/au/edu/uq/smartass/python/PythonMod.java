package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;

/**
 * Describe class PythonMod here.
 *
 *
 * Created: Thu Jan 15 12:33:03 2009
 *
 * @author <a href="mailto:ilm@gulrot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonMod extends PythonBinaryOp {

    private static final int MOD_PRECEDENCE = 13;

    //Mod in particular is hard to see and read, so use spaces.
    private static final String MOD_SIGN = " % ";

    /**
     * Creates a new <code>PythonMod</code> instance.
     *
     */
    public PythonMod(PythonMaths n1, PythonMaths n2) {
        super(MOD_PRECEDENCE, n1, n2, MOD_SIGN);
    }

    public MathsOp toMathsOp() {
        throw new PythonException("No mod MathsOp defined as yet...");
    }

    protected double calculatedValue(double v1, double v2, boolean isInt) {
        //To simplify things, allow doubles to be modded as well, as
        //both Java and Python allow it.

        double res = v1 % v2;

        //Java sometimes returns a negative result.
        if (res < 0) {
            res += v2;
        }

        return res;
    }

    protected boolean isValid(double v1, double v2) {
        return (v2 > 1);
    }

}
