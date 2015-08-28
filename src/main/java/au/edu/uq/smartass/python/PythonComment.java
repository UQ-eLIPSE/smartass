package au.edu.uq.smartass.python;

/**
 * Describe class PythonComment here.
 *
 *
 * Created: Thu Dec  4 15:12:48 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonComment extends PythonConstruct {

    private String cmt;

    /**
     * Creates a new <code>PythonComment</code> instance.
     *
     */
    public PythonComment(String cmt) {
        super();
        this.cmt = cmt;
    }

    public String toPython() {
        return "# " + cmt;
    }

}
