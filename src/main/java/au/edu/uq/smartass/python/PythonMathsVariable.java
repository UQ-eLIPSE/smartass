package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Variable;

/**
 * Describe class PythonMathsVariable here.
 *
 *
 * Created: Wed Nov 21 15:02:43 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonMathsVariable extends PythonMaths
    implements PythonNumericVariables {

    private String var, texName;

    private PythonNumber value;

    /**
     * Creates a new <code>PythonMathsVariable</code> instance.
     *
     */
    public PythonMathsVariable(String varname) {
        this(varname, varname);
    }

    public PythonMathsVariable(String var, String texName) {
        super(LITERAL_VALUE);

        this.var = var;
        this.texName = texName;
        value = null;
    }

    public void setOp(PythonMaths value, PythonResultBuffer pb) {
        this.value = value.calculate(pb);
    }

    public void setOp(PythonOp value, PythonResultBuffer pb) {
        if (!validOp(value)) {
            throw new PythonException("Invalid numeric value: " + value.toPython());
        }

        setOp((PythonMaths)value, pb);
    }

    public boolean validOp(PythonOp value) {
        return (value instanceof PythonMaths);
    }

    public String toPython() {
        return var;
    }

    public String name() {
        return var;
    }

    public Variable toMathsOp() {
        if (value == null) {
            return new Variable(texName);
        } else {
            return new Variable(texName, value.toMathsOp());
        }
    }

    public PythonNumber calculate(PythonResultBuffer pb) {
        pb.setCalculable(value != null);

        PythonNumber n = value.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonNumber.ZERO;
        }

        return n;
    }

    public PythonAssignment increment() {
        return new PythonAssignment(this, succ());
    }

    public PythonMaths succ() {
        return new PythonAdd(this, PythonNumber.ONE);
    }

}

