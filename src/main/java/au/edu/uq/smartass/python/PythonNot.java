package au.edu.uq.smartass.python;

/**
 * Describe class PythonNot here.
 *
 *
 * Created: Fri Dec 12 15:27:50 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonNot extends PythonLogic {

    private static final int NOT_PRECEDENCE = 4;

    private PythonLogic b;

    /**
     * Creates a new <code>PythonNot</code> instance.
     *
     */
    public PythonNot(PythonLogic b) {
        super(NOT_PRECEDENCE);

        this.b = b;
    }

    public PythonBool calculate(PythonResultBuffer pb) {
        PythonBool r = b.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonBool.TRUE;
        }

        return new PythonBool(!r.value());
    }

    public String toPython() {
        return "not " + b.toPythonLogic();
    }
}
