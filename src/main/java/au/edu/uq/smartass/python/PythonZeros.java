package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;

/**
 * Describe class PythonZeros here.
 *
 *
 * Created: Fri Dec  5 13:50:54 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonZeros extends PythonArrays {

    private PythonMaths size;

    private boolean isInt;

    /**
     * Creates a new <code>PythonZeros</code> instance.
     *
     */
    public PythonZeros(PythonMaths size) {
        this(size, false);
    }

    public PythonZeros(PythonMaths size, boolean isInt) {
        super(FUNCTION);

        this.size = size;
        this.isInt = isInt;
    }

    public PythonZeros(int size) {
        this(new PythonNumber(size));
    }

    public String toPython() {
        StringBuffer sb = new StringBuffer("zeros(" + size.toPython());

        if (!isInt) {
            sb.append(", dtype=float");
        }

        sb.append(")");

        return sb.toString();
    }

    public PythonArray calculate(PythonResultBuffer pb) {
        PythonNumber n = size.calculate(pb);
        pb.setCalculable(n.isInt());

        if (!pb.isCalculable()) {
            return PythonArray.EMPTY;
        }

        int len = (int)n.intValue();

        PythonArray result = new PythonArray(new int[len]);

        if (!isInt) {
            result.setFloatingPoint();
        }

        return result;
    }

    public MathsOp toMathsOp() {
        throw new PythonException("No math representation");
    }
}
