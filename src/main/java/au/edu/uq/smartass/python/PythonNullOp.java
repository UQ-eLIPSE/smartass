package au.edu.uq.smartass.python;

/**
 * Describe class PythonNullOp here.
 *
 *
 * Created: Mon Dec 17 01:15:25 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonNullOp implements PythonResults {

    final static PythonNullOp NULL_OP = new PythonNullOp();

    /**
     * Creates a new <code>PythonNullOp</code> instance.
     *
     */
    private PythonNullOp() {
        super();
    }

    public String printOutput() {
        throw new PythonException("Null op can't be printed");
    }

}
