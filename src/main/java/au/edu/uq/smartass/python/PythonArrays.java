package au.edu.uq.smartass.python;

/**
 * Describe class PythonArrays here.
 *
 *
 * Created: Thu Dec 20 01:22:06 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonArrays extends PythonNumeric {

    /**
     * Creates a new <code>PythonArrays</code> instance.
     *
     */
    protected PythonArrays(int precedence) {
        super(precedence);
    }

    public abstract PythonArray calculate(PythonResultBuffer pb);

    public final PythonArray calculate() {
        return calculate(PythonResultBuffer.NO_OUTPUT);
    }

}
