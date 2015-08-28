package au.edu.uq.smartass.python;

/**
 * Describe class PythonIf here.
 *
 *
 * Created: Fri Dec 12 12:58:57 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonIf extends PythonGroup {

    private PythonLogic cond;
    private PythonBlock tr, fls;

    /**
     * Creates a new <code>PythonIf</code> instance.
     *
     */
    public PythonIf(PythonLogic cond) {
        super();

        this.cond = cond;
    }

    public void addIf(PythonConstruct op) {
        if (tr == null) {
            tr = new PythonBlock();
        }

        tr.add(op);
    }

    public void addElse(PythonConstruct op) {
        if (fls == null) {
            fls = new PythonBlock();
        }

        fls.add(op);
    }

    public String toPython(int indentLevel) {
        StringBuffer sb = new StringBuffer(numTabs(indentLevel));

        sb.append("if " + cond.toPython() + " :" + NEWLINE);
        sb.append(tr.toPython(indentLevel+1));

        if (fls != null) {
            sb.append(NEWLINE + numTabs(indentLevel) + "else :" + NEWLINE);
            sb.append(fls.toPython(indentLevel+1));
        }

        return sb.toString();
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        pb.setCalculable(tr != null);

        PythonBool isIf = cond.calculate(pb);

        if (pb.isCalculable()) {
            if (isIf.value()) {
                tr.calculate(pb);
            } else if (fls != null) {
                fls.calculate(pb);
            }
        }

        return PythonNullOp.NULL_OP;
    }

}
