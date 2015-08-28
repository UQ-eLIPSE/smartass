package au.edu.uq.smartass.python;

/**
 * Describe class PythonImport here.
 *
 *
 * Created: Mon Dec  3 06:39:04 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonImport extends PythonConstruct {

    private static final String IMPORT_ALL = "*";

    private String library;
    private String imp;

    /**
     * Creates a new <code>PythonImport</code> instance.
     *
     */
    public PythonImport(String library, String imp) {
        super();

        this.library = library;
        this.imp = imp;
    }

    public PythonImport(String library) {
        this(library, IMPORT_ALL);
    }

    public String toPython() {
        return "from " + library + " import " + imp;
    }

}
