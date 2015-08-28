package au.edu.uq.smartass.python;

/**
 * Describe class PythonUnaryOp here.
 *
 *
 * Created: Mon Dec  3 05:32:02 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonUnaryOp extends PythonMaths {

    protected PythonMaths n;

    private String left, right;

    private boolean is_function = false;

    /**
     * Creates a new <code>PythonUnaryOp</code> instance.
     *
     */
    public PythonUnaryOp(int precedence, PythonMaths n,
                         String left, String right,
                         boolean is_function) {
        super(precedence);

        this.n = n;

        this.left = left;
        this.right = right;

        this.is_function = is_function;
    }

    public PythonUnaryOp(PythonMaths n, String function_name) {
        this(FUNCTION, n, function_name + "(", ")", true);
    }

    public PythonUnaryOp(int precedence, PythonMaths n, String left) {
        this(precedence, n, left, "");
    }

    public PythonUnaryOp(int precedence, PythonMaths n,
                         String left,
                         String right) {
        this(precedence, n, left, right, false);
    }

    public String toPython() {
        StringBuffer sb = new StringBuffer(left);

        if (is_function) {
            sb.append(n.toPython());
        } else {
            sb.append(n.toPythonPrecedence(precedence()));
        }

        sb.append(right);

        return sb.toString();
    }

    public final PythonNumber calculate(PythonResultBuffer pb) {

        PythonNumber num = n.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonNumber.ZERO;
        }

        double v = num.value();

        pb.setCalculable(canCalculate(v));

        boolean isInt = isIntResult(num.isInt(),v);
        double value = calculatedValue(v, isInt);

        PythonNumber r = new PythonNumber(value, isInt);

        return r.calculate(pb);
    }

    protected abstract double calculatedValue(double v, boolean isInt);

    protected boolean isIntResult(boolean b, double v) {
        return b;
    }

    protected boolean canCalculate(double v) {
        return true;
    }

}
