package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsModule;
import au.edu.uq.smartass.maths.MathsOp;
import au.edu.uq.smartass.maths.Equality;
import au.edu.uq.smartass.engine.Engine;

/**
 * Describe class PythonModule here.
 *
 *
 * Created: Wed Dec  5 02:59:13 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonModule extends MathsModule {

    private static final String WARNING
        = "Please note that there may be some slight "
        + "rounding errors present, and that the results "
        + "may not be formatted exactly as you would find "
        + "in Python.";

    /**
     * Creates a new <code>PythonModule</code> instance.
     *
     */
    public PythonModule(Engine engine) {
        super(engine);
    }

    public String getSection(String name) {
        if (name.equals("resultwarning")) {
            return WARNING;
        }

        return "Unknown section: " + name;
    }

    protected String toMaths(PythonNumeric m) {
        return toTex(m.toMathsOp());
    }

    protected String toMaths(PythonNumericVariables v, PythonNumeric m) {
        Equality eq = new Equality(v.toMathsOp(), m.toMathsOp());

        return toTex(eq);
    }

    protected String toTex(int m) {
        return "\\ensuremath{" + m + "}";
    }

    protected String toTex(double m) {
        return "\\ensuremath{" + m + "}";
    }

    protected String toTex(MathsOp m) {
        return "\\ensuremath{" + m.toString() + "}";
    }

    protected String toTex(String str) {
        return str;
    }

    protected String toTex(PythonCode p) {
        return p.toString();
    }

    protected String toTex(PythonConstruct p) {
        return p.toPython();
    }

    protected static boolean sameName(PythonNamed a, PythonNamed b) {
        return a.name().equals(b.name());
    }

    /*
    protected static PythonAdd add(PythonMaths n1, PythonMaths n2) {
        return new PythonAdd(n1, n2);
    }

    protected static PythonMult mult(PythonMaths n1, PythonMaths n2) {
        return new PythonMult(n1, n2);
    }

    protected static PythonMod mod(PythonMaths n1, PythonMaths n2) {
        return new PythonMod(n1, n2);
    }
    */

    protected static PythonAssignment assign(PythonVariables v, PythonOp x) {
        return new PythonAssignment(v, x);
    }

    protected static PythonComment comment(String str) {
        return new PythonComment(str);
    }

    protected static PythonPrint print(String str) {
        return new PythonPrint(str);
    }

    protected static PythonPrint print(PythonOp op) {
        return new PythonPrint(op);
    }

    protected static PythonPrint print(PythonOp[] ops) {
        return new PythonPrint(ops);
    }

    protected static PythonPrint print() {
        return new PythonPrint();
    }

}
