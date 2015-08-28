package au.edu.uq.smartass.python;

import java.util.Vector;
import java.util.Collection;

/**
 * Describe class PythonBlock here.
 *
 *
 * Created: Wed Nov 21 14:16:10 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonBlock extends PythonGroup {

    private Vector<PythonConstruct> elems;

    /**
     * Creates a new <code>PythonBlock</code> instance.
     *
     */
    public PythonBlock() {
        //start with negative indent, since toPython(i) will indent by i+1
        super();
        elems = new Vector<PythonConstruct>();
    }

    public void addBlank() {
        add(PythonBlank.BLANK);
    }

    public void add(PythonConstruct op) {
        elems.add(op);
    }

    public void add(Collection<? extends PythonConstruct> ops) {
        elems.addAll(ops);
    }

    public String toPython(int indentLevel) {
        StringBuffer sb = new StringBuffer();

        for(PythonConstruct op : elems) {
            sb.append(op.toPython(indentLevel));
            sb.append(NEWLINE);
        }

        //Remove the last newline to prevent double newline.
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public PythonNullOp calculate(PythonResultBuffer pb) {
        for (PythonConstruct op : elems) {
            op.calculate(pb);

            if (!pb.isCalculable()) {
                break;
            }
        }

        return PythonNullOp.NULL_OP;
    }

    Vector<PythonConstruct> getElems() {
        return elems;
    }

}
