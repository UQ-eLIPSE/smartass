package au.edu.uq.smartass.python;

/**
 * Describe class PythonGT here.
 *
 *
 * Created: Thu Dec 11 10:58:43 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonGT extends PythonComparison {

    private static final String GT_SIGN = ">";

    /**
     * Creates a new <code>PythonGT</code> instance.
     *
     */
    public PythonGT(PythonMaths num1, PythonMaths num2) {
        super(num1, num2, GT_SIGN);
    }

    protected boolean calculate(double n1, double n2) {
        return n1 > n2;
    }
}
