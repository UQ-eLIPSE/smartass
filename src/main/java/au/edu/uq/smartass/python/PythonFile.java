package au.edu.uq.smartass.python;

/**
 * Describe class PythonFile here.
 *
 *
 * Created: Thu Dec  4 11:18:39 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonFile extends PythonScript {

    /**
     * Creates a new <code>PythonFile</code> instance.
     *
     */
    public PythonFile() {
        super();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Here is the program:" + NEWLINE);
        sb.append(super.toString() + NEWLINE);

        PythonResultBuffer pb = printOutput();

        if (pb.hasPrinting()) {
            sb.append("Here is the output from running the program:" + NEWLINE);
            sb.append(pb.toString());
        }

        return sb.toString();
    }
}
