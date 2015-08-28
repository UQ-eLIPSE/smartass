package au.edu.uq.smartass.python;

/**
 * Describe class PythonComparison here.
 *
 *
 * Created: Thu Dec 11 10:58:43 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonComparison extends PythonLogic {

    protected static final int COMPARISONS = 7;

    private String sign;

    protected PythonMaths num1, num2;

    /**
     * Creates a new <code>PythonComparison</code> instance.
     *
     */
    public PythonComparison(PythonMaths num1, PythonMaths num2, String sign) {
        super(COMPARISONS);

        this.num1 = num1;
        this.num2 = num2;
        this.sign = sign;
    }

    public final PythonBool calculate(PythonResultBuffer pb) {
        PythonNumber n1 = num1.calculate(pb);
        PythonNumber n2 = num2.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonBool.TRUE;
        }

        return new PythonBool(calculate(n1.value(), n2.value()));
    }

    protected abstract boolean calculate(double n1, double n2);

    public final String toPython() {
        return num1.toPythonPrecedence(precedence()) + " "
            + sign + " " + num2.toPythonPrecedence(precedence());
    }
}
