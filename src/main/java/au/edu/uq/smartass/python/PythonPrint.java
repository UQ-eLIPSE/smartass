package au.edu.uq.smartass.python;

/**
 * Describe class PythonPrint here.
 *
 *
 * Created: Thu Dec  4 12:48:09 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonPrint extends PythonConstruct {

    PythonOp[] ops;

    public PythonPrint() {
        this(new PythonOp[0]);
    }

    /**
     * Creates a new <code>PythonPrint</code> instance.
     *
     */
    public PythonPrint(PythonOp op) {
        this(new PythonOp[] {op});
    }

    public PythonPrint(PythonOp[] ops) {
        super();

        this.ops = ops;
    }

    PythonPrint(String str) {
        this(new PythonString(str));
    }

    public String toPython() {
        StringBuffer sb = new StringBuffer("print");

        String sep = " ";

        for (PythonOp op : ops) {
            sb.append(sep);
            sb.append(op.toPython());

            //Different just for the first one
            sep = ", ";
        }

        return sb.toString();
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        StringBuffer sb = new StringBuffer();

        PythonResults val;

        for (PythonOp op : ops) {
            val = op.calculate(pb);

            if (!pb.isCalculable()) {
                return PythonNullOp.NULL_OP;
            }

            sb.append(val.printOutput() + " ");
        }

        //Remove trailing space
        if (ops.length > 0) {
            sb.deleteCharAt(sb.length()-1);
        }

        pb.add(sb.toString());

        return PythonNullOp.NULL_OP;
    }

}
