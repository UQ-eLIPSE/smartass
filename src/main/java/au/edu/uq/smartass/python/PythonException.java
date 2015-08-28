package au.edu.uq.smartass.python;

/**
 * Describe class PythonException here.
 *
 *
 * Created: Mon Dec 10 03:22:56 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonException extends RuntimeException {

    /**
     * Creates a new <code>PythonException</code> instance.
     *
     */
    public PythonException() {
        this("Undefined error in Python-related objects");
    }

    public PythonException(String str) {
        super(str);
        System.out.println("PYTHON ERROR!!!");
        System.err.println(str);
    }

}
