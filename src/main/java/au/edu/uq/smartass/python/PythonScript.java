package au.edu.uq.smartass.python;

/**
 * Describe class PythonScript here.
 *
 *
 * Created: Wed Nov 21 14:11:09 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonScript extends PythonCode {

    protected final static String NEWLINE = "\n";

    private final static String BEFORE = "\\begin{quote}"+NEWLINE+"\\begin{verbatim}"+NEWLINE;
    private final static String AFTER = NEWLINE+"\\end{verbatim}"+NEWLINE+"\\end{quote}"+NEWLINE;

    protected PythonBlock bl;

    private PythonResultBuffer pb;

    /**
     * Creates a new <code>PythonScript</code> instance.
     *
     */
    public PythonScript() {
        super(BEFORE,AFTER);
        bl = new PythonBlock();
        super.setOp(bl);

        pb = null;
    }

    public void addBlank() {
        bl.addBlank();
    }

    public void add(PythonConstruct op) {
        bl.add(op);
    }

    public boolean isCalculable() {
        return printOutput().isCalculable();
    }

    protected String addImports() {
        PythonBlock impBl = new PythonBlock();

        impBl.add(IMPORTS);
        impBl.addBlank();

        return impBl.toPython() + NEWLINE;
    }

    public PythonResultBuffer printOutput() {
        if (pb == null) {
            pb = new PythonResultBuffer();

            try {
                bl.calculate(pb);
            } catch (Exception e) {
                System.out.println("runtime problem...");
                System.out.println(e);
                e.printStackTrace();
            }
        }

        return pb;
    }

    public void resetOutput() {
        pb = null;
    }
}
