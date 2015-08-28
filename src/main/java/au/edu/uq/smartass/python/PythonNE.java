package au.edu.uq.smartass.python;

/**
 * Describe class PythonNE here.
 *
 *
 * Created: Thu Dec 11 10:58:43 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonNE extends PythonComparison {

    private static final String NE_SIGN = "!=";

    /**
     * Creates a new <code>PythonNE</code> instance.
     *
     */
    public PythonNE(PythonMaths num1, PythonMaths num2) {
        super(num1, num2, NE_SIGN);
    }

    protected boolean calculate(double n1, double n2) {
        return n1 != n2;
    }
}
