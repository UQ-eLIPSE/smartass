package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Matrix;
import au.edu.uq.smartass.maths.MathsOp;

/**
 * Describe class PythonArray here.
 *
 *
 * Created: Thu Dec 20 01:23:31 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonArray extends PythonArrays
    implements PythonOpResults {

    public final static PythonArray EMPTY = new PythonArray(new PythonNumber[0]);

    private final static String ROWSTART = "[";
    private final static String ROWEND = "]";

    private final static String ARRAYSTART = "array(" + ROWSTART;
    private final static String ARRAYEND = ROWEND + ")";

    private final static String SEPARATOR = ", ";

    private final static String INDENT = numSpaces(ARRAYSTART.length());

    private PythonNumber[] vector;
    private int numElems;

    private boolean isInt;

    /**
     * Creates a new <code>PythonArray</code> instance.
     *
     */
    public PythonArray(int[] v) {
        this(toVector(v));
    }

    /**
     * Creates a new <code>PythonArray</code> instance.
     *
     */
    public PythonArray(double[] v) {
        this(toVector(v));
    }

    /**
     * Creates a new <code>PythonArray</code> instance.
     *
     */
    public PythonArray(PythonNumber[] v) {
        super(FUNCTION);

        isInt = checkNumberType(v);

        vector = v;
        numElems = vector.length;
    }

    void setFloatingPoint() {
        isInt = false;
    }

    public MathsOp toMathsOp() {
        MathsOp[][] mat = new MathsOp[1][numElems()];

        for (int i = 0; i < numElems(); i++) {
            mat[0][i] = vector[i].toMathsOp();
        }

        return new Matrix(mat);
    }

    public PythonArray calculate(PythonResultBuffer pb) {
        for (PythonNumber n : vector) {
            //Just to check all the entries are valid
            n.calculate(pb);
        }

        return this;
    }

    private String printArray(String start, String end, boolean commas) {
        String sep = commas ? SEPARATOR : SPACE;

        StringBuffer sb = new StringBuffer(start);

        sb.append(vector[0].toPython());

        for (int i = 1; i < numElems; i++) {
            sb.append(sep);
            sb.append(vector[i].toPython());
        }

        sb.append(end);

        return sb.toString();
    }

    public String toPython() {
        return printArray(ARRAYSTART, ARRAYEND, true);
    }

    PythonNumber[] value() {
        return vector;
    }

    PythonNumber value(int i) {
        if (!validValue(i)) {
            throw new PythonException("Index out of bounds");
        }

        return vector[i];
    }

    void setValue(int i, PythonMaths val, PythonResultBuffer pb) {
        pb.setCalculable(validValue(i));

        PythonNumber num = val.calculate(pb);

        if (pb.isCalculable()) {

            if (isInt) {
                num.setInt();
            } else {
                num.setFloatingPoint();
            }

            vector[i] = num;
        }
    }

    int numElems() {
        return numElems;
    }

    boolean validValue(int i) {
        return (i >= 0 && i < numElems);
    }

    private static boolean checkNumberType(PythonNumber[] v) {
        if (anyDouble(v)) {
            for (PythonNumber n : v) {
                n.setFloatingPoint();
            }

            return false;
        } else {
            return true;
        }
    }

    private static boolean anyDouble(PythonNumber[] v) {
        for (PythonNumber n : v) {
            if (!n.isInt()) {
                return true;
            }
        }

        return false;
    }

    private static PythonNumber[] toVector(int[] ns) {
        int len = ns.length;

        PythonNumber[] result = new PythonNumber[len];

        for (int i = 0; i < len; i++) {
            result[i] = new PythonNumber(ns[i]);
        }

        return result;
    }

    private static PythonNumber[] toVector(double[] ns) {
        int len = ns.length;

        PythonNumber[] result = new PythonNumber[len];

        for (int i = 0; i < len; i++) {
            result[i] = new PythonNumber(ns[i]);
        }

        return result;
    }

    public String printOutput() {
        return printArray(ROWSTART, ROWEND, false);
    }

}
