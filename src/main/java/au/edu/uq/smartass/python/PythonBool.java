package au.edu.uq.smartass.python;

/**
 * Describe class PythonBool here.
 *
 *
 * Created: Thu Dec 11 10:45:48 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonBool extends PythonLogic
    implements PythonOpResults {

    //Use TRUE as a default
    public static final PythonBool TRUE = new PythonBool(true);
    public static final PythonBool FALSE = new PythonBool(false);

    private boolean value;

    /**
     * Creates a new <code>PythonBool</code> instance.
     *
     */
    public PythonBool(boolean value) {
        super(LITERAL_VALUE);

        this.value = value;
    }

    public PythonBool calculate(PythonResultBuffer pb) {
        return this;
    }

    public String toPython() {
        return (value ? "True" : "False");
    }

    public String printOutput() {
        return toPython();
    }

    boolean value() {
        return value;
    }

}
