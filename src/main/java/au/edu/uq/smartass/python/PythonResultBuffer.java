package au.edu.uq.smartass.python;

import java.util.Vector;

/**
 * Describe class PythonResultBuffer here.
 *
 *
 * Created: Tue Dec 16 10:30:18 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonResultBuffer extends PythonScript {

    private class PrintLine extends PythonOp
        implements PythonOpResults {

        private String str;

        private PrintLine(String str) {
            super();

            this.str = str;
        }

        public PrintLine calculate(PythonResultBuffer pb) {
            return this;
        }

        public String toPython() {
            return str;
        }

        public String printOutput() {
            throw new PythonException("Shouldn't see this...");
        }
    }

    public static final PythonResultBuffer NO_OUTPUT = new PythonResultBuffer(false);

    private Vector<String> printed;

    private boolean isCalculable;

    protected final static String NEWLINE = "\n";

    private PythonResultBuffer(boolean doPrint) {
        super();

        isCalculable = true;

        if (doPrint) {
            printed = new Vector<String>();
        }
    }

    /**
     * Creates a new <code>PythonResultBuffer</code> instance.
     *
     */
    public PythonResultBuffer() {
        this(true);
    }

    public PythonResultBuffer(PythonConstruct op) {
        this();

        add(op);
    }

    public void add(String pr) {
        if (printed != null) {
            printed.add(pr);
        }
    }

    public boolean hasPrinting() {
        return (numPrinting() > 0);
    }

    public int numPrinting() {
        return (printed != null ? printed.size() : 0);
    }

    public String toString() {
        if (!hasPrinting()) {
            return "";
        }

        StringBuffer sb = new StringBuffer(before);

        for (String str : printed) {
            sb.append(str);
            sb.append(NEWLINE);
        }

        sb.append(after);

        return sb.toString();
    }

    public boolean isCalculable() {
        return isCalculable;
    }

    public void setCalculable(boolean opCalc) {
        isCalculable = isCalculable && opCalc;
    }

    public PythonResultBuffer printOutput() {
        throw new PythonException("Cannot print print outputs");
    }

    public PythonInline getLastPrint() {
        return new PythonInline(new PrintLine(printed.lastElement()));
    }

}
