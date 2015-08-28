package au.edu.uq.smartass.python;

/**
 * Describe class PythonAnd here.
 *
 *
 * Created: Fri Dec 12 15:27:50 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonAnd extends PythonLogic {

    private static final int AND_PRECEDENCE = 3;

    private PythonLogic b1, b2;

    /**
     * Creates a new <code>PythonAnd</code> instance.
     *
     */
    public PythonAnd(PythonLogic b1, PythonLogic b2) {
        super(AND_PRECEDENCE);

        this.b1 = b1;
        this.b2 = b2;
    }

    public PythonBool calculate(PythonResultBuffer pb) {
        PythonBool r1 = b1.calculate(pb);
        PythonBool r2 = b2.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonBool.TRUE;
        }

        return new PythonBool(r1.value() && r2.value());
    }

    public String toPython() {
        return b1.toPythonLogic() + " and " + b2.toPythonLogic();
    }
}
