package au.edu.uq.smartass.python;

/**
 * Describe class PythonWhileLoop here.
 *
 *
 * Created: Thu Dec 11 11:04:53 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonWhileLoop extends PythonGroup {

    private PythonLogic criteria;
    private PythonBlock bl;

    /**
     * Creates a new <code>PythonWhileLoop</code> instance.
     *
     */
    public PythonWhileLoop(PythonLogic criteria) {
        super();

        this.criteria = criteria;
        bl = new PythonBlock();
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        while (canContinue(criteria, pb)) {
            bl.calculate(pb);
        }

        return PythonNullOp.NULL_OP;
    }

    private static boolean canContinue(PythonLogic crit, PythonResultBuffer pb) {
        if (!pb.isCalculable()) {
            return false;
        }

        PythonBool b = crit.calculate(pb);

        return pb.isCalculable() && b.value();
    }

    public String toPython(int indentLevel) {
        StringBuffer sb = new StringBuffer(numTabs(indentLevel));

        sb.append("while " + criteria.toPython() + " :" + NEWLINE);
        sb.append(bl.toPython(indentLevel+1));

        return sb.toString();
    }

    public void add(PythonConstruct op) {
        bl.add(op);
    }

    public void addBlank() {
        add(PythonBlank.BLANK);
    }

}
