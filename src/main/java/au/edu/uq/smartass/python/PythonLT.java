package au.edu.uq.smartass.python;

/**
 * Describe class PythonLT here.
 *
 *
 * Created: Thu Dec 11 10:58:43 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonLT extends PythonComparison {

    private static final String LT_SIGN = "<";

    /**
     * Creates a new <code>PythonLT</code> instance.
     *
     */
    public PythonLT(PythonMaths num1, PythonMaths num2) {
        super(num1, num2, LT_SIGN);
    }

    protected boolean calculate(double n1, double n2) {
        return n1 < n2;
    }
}
