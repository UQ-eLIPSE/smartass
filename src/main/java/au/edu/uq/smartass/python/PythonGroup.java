package au.edu.uq.smartass.python;

/**
 * Describe class PythonGroup here.
 *
 *
 * Created: Mon Nov 26 10:30:22 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonGroup extends PythonConstruct {

    protected int default_indent;

    protected PythonGroup(int default_indent) {
        super();
        this.default_indent = default_indent;
    }

    protected PythonGroup() {
        this(DEFAULT_INDENT);
    }

    public String toPython() {
        return toPython(default_indent);
    }

    //No group object can return a value.
    public abstract PythonNullOp calculate(PythonResultBuffer pb);

    public abstract String toPython(int indentLevel);

}
