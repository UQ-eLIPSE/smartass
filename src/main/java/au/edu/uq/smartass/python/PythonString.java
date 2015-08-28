package au.edu.uq.smartass.python;

/**
 * Describe class PythonString here.
 *
 *
 * Created: Fri Dec 12 13:48:28 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonString extends PythonOp
    implements PythonOpResults {

    public static final PythonString BLANK = new PythonString("");

    private static final String QUOTE = "'";
    private static final String DBL_QUOTE = "\"";
    private static final String SLASH = "\\";
    private static final String SLASH_Q = SLASH + QUOTE + SLASH;
    private static final String SLASH_DQ = SLASH + DBL_QUOTE + SLASH;

    private String str;

    /**
     * Creates a new <code>PythonString</code> instance.
     *
     */
    public PythonString(String str) {
        super(LITERAL_VALUE);

        this.str = str;
    }

    public PythonString calculate(PythonResultBuffer pb) {
        return this;
    }

    private boolean contains(String cnt) {
        return (str.indexOf(cnt)) >= 0;
    }

    public String toPython() {
        return DBL_QUOTE + str.replaceAll(DBL_QUOTE, SLASH_DQ) + DBL_QUOTE;
    }

    //Kept in case we ever need to do the full string quoting stuff again.
    /*
    private String outputForm() {
        if (contains(DBL_QUOTE)) {
            String qStr = str.replaceAll(QUOTE, SLASH_Q);
            return QUOTE + qStr + QUOTE;
        }

        if (contains(QUOTE)) {
            return DBL_QUOTE + str + DBL_QUOTE;
        }

        return QUOTE + str + QUOTE;
    }
    */

    public String printOutput() {
        return str;
    }
}
