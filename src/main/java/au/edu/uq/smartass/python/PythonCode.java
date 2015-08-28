package au.edu.uq.smartass.python;

import java.util.Collection;
import java.util.Arrays;

/**
 * Describe class PythonCode here.
 *
 *
 * Created: Wed Nov 21 13:55:27 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonCode {

    protected PythonConstruct op;
    protected String before, after;
    private boolean printImports;

    protected static final PythonImport DIVISION
        = new PythonImport("__future__", "division");

    protected static final PythonImport PYLAB
        = new PythonImport("pylab");

    protected static final Collection<PythonImport> IMPORTS
        = Arrays.asList(DIVISION, PYLAB);

    /**
     * Creates a new <code>PythonCode</code> instance.
     *
     */
    protected PythonCode(String before, String after) {
        this(null, before, after);
    }

    protected PythonCode(PythonOp op, String before, String after) {
        this.op = op;
        this.before = before;
        this.after = after;

        enableImports();
    }

    protected void setOp(PythonConstruct op) {
        this.op = op;
    }

    public void enableImports() {
        printImports = true;
    }

    public void disableImports() {
        printImports = false;
    }

    public String toString() {

        if (op == null) {
            throw new PythonException("call setOp(op) first!");
        }

        if (printImports) {
            String imports = addImports();

            return setInterior(imports, op.toPython());
        }

        return setInterior(op.toPython());
    }

    public String printImports() {

        if (op == null) {
            throw new PythonException("call setOp(op) first!");
        }

        String imports = addImports();

        return setInterior(imports);
    }

    private String setInterior(String imports, String code) {
        StringBuffer sb = new StringBuffer(before);

        if (imports != null) {
            sb.append(imports);
        }

        sb.append(code);
        sb.append(after);

        return sb.toString();
    }

    private String setInterior(String code) {
        return setInterior(null,code);
    }

    protected abstract String addImports();
}
