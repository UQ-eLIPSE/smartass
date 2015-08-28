package au.edu.uq.smartass.python;

/**
 * Describe class PythonInline here.
 *
 *
 * Created: Fri Nov 23 12:49:02 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonInline extends PythonCode {

    private static final String before = "{\\tt ";
    private static final String after = "}";

    /**
     * Creates a new <code>PythonInline</code> instance.
     *
     */
    public PythonInline(PythonOp op) {
        super(op, before, after);
    }

    protected String addImports() {
        //Don't actually return anything
        return "";
    }
}
