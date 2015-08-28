package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Variable;
import au.edu.uq.smartass.maths.MathsOp;

/**
 * Describe class PythonAssignment here.
 *
 *
 * Created: Mon Dec 17 01:16:38 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonAssignment extends PythonConstruct {

    private static final String ASSIGNMENT = " = ";

    PythonVariables var;

    PythonOp op;

    /**
     * Creates a new <code>PythonAssignment</code> instance.
     *
     */
    public PythonAssignment(PythonVariables var, PythonOp op) {
        super();

        this.var = var;
        this.op = op;
    }

    public String toPython() {
        return (var.toPython() + ASSIGNMENT + op.toPython());
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        pb.setCalculable(var.validOp(op));

        if (pb.isCalculable()) {
            var.setOp(op, pb);
        }

        return PythonNullOp.NULL_OP;
    }

    public MathsOp toMathsOp() {
        if (!(var instanceof PythonNumericVariables
              || (op instanceof PythonNumeric))) {
            throw new PythonException("cannot create a maths op");
        }

        Variable v = ((PythonNumericVariables)var).toMathsOp();

        v.setValue(((PythonNumeric)op).toMathsOp());

        return v.varEquality();
    }
}
