package au.edu.uq.smartass.python;

/**
 * Describe class PythonBlank here.
 *
 *
 * Created: Mon Dec  8 10:41:40 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonBlank extends PythonConstruct {

    public static final PythonBlank BLANK = new PythonBlank();

    /**
     * Creates a new <code>PythonBlank</code> instance.
     *
     */
    private PythonBlank() {
        super();
    }

    public String toPython() {
        //Use a space in case of line breaking purposes
        return " ";
    }

}
