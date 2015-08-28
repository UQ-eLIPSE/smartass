package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;

import java.util.Vector;

/**
 * Describe class PythonARange here.
 *
 *
 * Created: Thu Dec 20 01:42:25 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonARange extends PythonArrays {

    private static final PythonNumber DEFAULT_START = PythonNumber.ZERO;
    private static final PythonNumber DEFAULT_STEP = PythonNumber.ONE;

    private final static String F_START = "arange(";
    private final static String SPACER = ", ";
    private final static String F_END = ")";

    private PythonMaths start, stop, step;

    private int numArgs;

    /**
     * Creates a new <code>PythonARange</code> instance.
     *
     */
    public PythonARange(PythonMaths stop) {
        this(DEFAULT_START, stop, DEFAULT_STEP, 1);
    }

    /**
     * Creates a new <code>PythonARange</code> instance.
     *
     */
    public PythonARange(PythonMaths start,
                        PythonMaths stop) {
        this(start, stop, DEFAULT_STEP, 2);
    }

    /**
     * Creates a new <code>PythonARange</code> instance.
     *
     */
    public PythonARange(PythonMaths start,
                        PythonMaths stop,
                        PythonMaths step) {
        this(start, stop, step, 3);
    }

    /**
     * Creates a new <code>PythonARange</code> instance.
     *
     */
    public PythonARange(PythonMaths start,
                        PythonMaths stop,
                        PythonMaths step,
                        int numArgs) {
        super(FUNCTION);

        this.start = start;
        this.stop = stop;
        this.step = step;
        this.numArgs = numArgs;
    }

    public PythonArray calculate(PythonResultBuffer pb) {

        PythonNumber starter = start.calculate(pb);
        PythonNumber stopper = stop.calculate(pb);
        PythonNumber stepper = step.calculate(pb);

        pb.setCalculable(validBounds(starter, stopper, stepper));

        if (!pb.isCalculable()) {
            return PythonArray.EMPTY;
        }

        //Just for readability purposes
        PythonNumber val = starter;

        double stopAt = stopper.value();
        double inc = stepper.value();

        Vector<PythonNumber> result = new Vector<PythonNumber>();

        while (pb.isCalculable() && continueLoop(val.value(), stopAt, inc)) {
            result.add(val);
            //Adding the value won't cause any printing, etc.,
            //but will test for overflows, etc.
            val = (new PythonAdd(val, stepper)).calculate(pb);
        }

        //Even if it isn't calculable, we can still construct some array
        //for typechecking purposes.

        //The empty array at the end is only there for the type.
        return new PythonArray(result.toArray(new PythonNumber[0]));
    }

    private static boolean validBounds(PythonNumber start, PythonNumber stop, PythonNumber step) {
        return (Math.signum(stop.value() - start.value()) == Math.signum(step.value()))
            && step.value() != 0;
    }

    public MathsOp toMathsOp() {
        throw new PythonException("not allowed");
    }

    public String toPython() {
        StringBuffer sb = new StringBuffer(F_START);

        switch (numArgs) {
        case 2 :
            sb.append(start.toPython());
            sb.append(SPACER);
        case 1 :
            sb.append(stop.toPython());
            break;
        default :
            sb.append(start.toPython());
            sb.append(SPACER);
            sb.append(stop.toPython());
            sb.append(SPACER);
            sb.append(step.toPython());
            break;
        }

        sb.append(F_END);

        return sb.toString();
    }

    private static boolean continueLoop(double val, double end,
                                        double inc) {
        if (inc < 0) {
            return (val > end);
        } else {
            return (val < end);
        }
    }

}
