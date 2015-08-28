package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Variable;
import au.edu.uq.smartass.maths.Function;
import au.edu.uq.smartass.maths.MathsOp;

import java.util.Arrays;
import java.util.Vector;

/**
 * Describe class PythonFunction here.
 *
 *
 * Created: Tue Dec  2 14:43:10 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonFunction extends PythonGroup implements PythonNamed {

    private class PythonFunctionApplication extends PythonMaths {

        private PythonMaths[] args;

        PythonFunctionApplication(PythonMaths[] args) {
            super(FUNCTION);
            this.args = args;
        }

        public String toPython() {
            return name + printArgs(args);
        }

        public MathsOp toMathsOp() {
            Vector<MathsOp> mops = new Vector<MathsOp>();

            for (int i = 0; i < numVars(); i++) {
                if (printVars[i]) {
                    mops.add(args[i].toMathsOp());
                }
            }

            return new Function(texName, mops.toArray(new MathsOp[0]));
        }

        public PythonNumber calculate(PythonResultBuffer pb) {
            returned = null;

            // Set all variables
            for (int i = 0; i < numVars(); i++) {
                vars[i].setOp(args[i], pb);

                if (!pb.isCalculable()) {
                    return PythonNumber.ZERO;
                }
            }

            bl.calculate(pb);

            pb.setCalculable(returned != null);

            if (!pb.isCalculable()) {
                return PythonNumber.ZERO;
            }

            return returned;
        }
    }

    private class FunctionReturn extends PythonConstruct {

        private PythonMaths op;

        FunctionReturn(PythonMaths op) {
            super();

            this.op = op;
        }

        public String toPython() {
            return "return " + op.toPython();
        }

        public MathsOp toMathsOp() {
            throw new PythonException("Shouldn't see this...");
        }

        public PythonNullOp calculate(PythonResultBuffer pb) {
            pb.setCalculable(op != null);

            if (pb.isCalculable()) {
                returned = op.calculate(pb);
            }

            return PythonNullOp.NULL_OP;
        }
    }

    private String name;
    private String texName;

    private PythonMathsVariable[] vars;
    private boolean[] printVars;

    private PythonBlock bl;

    private static final String retVar = "ans";

    private PythonNumber returned;

    private boolean allowAdds = true;

    /**
     * Creates a new <code>PythonFunction</code> instance.
     *
     */

    public PythonFunction(String name, String texName,
                          PythonMathsVariable[] vars,
                          boolean[] printVars) {
        super();

        if (printVars.length != vars.length) {
            throw new PythonException("printVars should be same size as vars");
        }

        this.name = name;
        this.texName = texName;
        this.vars = vars;
        this.printVars = printVars;

        bl = new PythonBlock();
    }

    public PythonFunction(String name, PythonMathsVariable[] vars) {
        this(name, name, vars, allPrinted(vars.length));
    }

    public PythonFunction(String name, PythonMathsVariable var) {
        this(name, new PythonMathsVariable[] {var});
    }

    public void add(PythonConstruct cmd) {
        if (!allowAdds) {
            throw new PythonException("Can't add anything else to the function");
        }

        bl.add(cmd);
    }

    public void addFuncCommand(PythonMaths func) {
        PythonMathsVariable ans = new PythonMathsVariable("ans");

        addFuncCommand(ans, func);
    }

    public void addFuncCommand(PythonMathsVariable var, PythonMaths func) {
        add(new PythonAssignment(var, func));

        setReturn(var);
    }

    public PythonConstruct funcReturn(PythonMaths expr) {
        return new FunctionReturn(expr);
    }

    public void setReturn(PythonMaths expr) {
        add(funcReturn(expr));
        allowAdds = false;
    }

    private static boolean[] allPrinted(int size) {
        boolean[] ba = new boolean[size];

        Arrays.fill(ba,true);

        return ba;
    }

    public String name() {
        return name;
    }

    public String toPython(int indentLevel) {
        StringBuffer sb = new StringBuffer(numTabs(indentLevel));

        sb.append("def " + name + printVars() + ":" + NEWLINE);
        sb.append(bl.toPython(indentLevel + 1));
        sb.append(NEWLINE);

        return sb.toString();
    }

    private String printVars() {
        return printArgs(vars);
    }

    private String printArgs(PythonMaths[] ms) {
        boolean first = true;

        StringBuffer sb = new StringBuffer("(");

        for (int i = 0; i < numVars(); i++) {
            if (printVars[i]) {

                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }

                sb.append(ms[i].toPython());
            }
        }

        sb.append(")");

        return sb.toString();
    }

    public int numVars() {
        return printVars.length;
    }

    public Variable toMathsOp() {
        return new Variable(texName);
    }

    PythonMaths callFunction(PythonMaths[] args) {
        if (args.length != numVars()) {
            throw new PythonException("Number of arguments doesn't match required number.");
        }

        return new PythonFunctionApplication(args);
    }

    PythonMaths callFunction(PythonMaths arg) {
        return callFunction(new PythonMaths[] {arg});
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        return PythonNullOp.NULL_OP;
    }
}
