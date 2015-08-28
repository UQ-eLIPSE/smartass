package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Multiplication;

/**
 * Describe class PythonArrayScalarMult here.
 *
 *
 * Created: Tue Dec  9 11:33:29 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonArrayScalarMult extends PythonArrays {

    private static final int MULT_PRECEDENCE = 13;

    private static final String MULT_SIGN = "*";

    private PythonMaths k;

    private PythonArrays a;

    /**
     * Creates a new <code>PythonArrayScalarMult</code> instance.
     *
     */
    public PythonArrayScalarMult(PythonMaths k, PythonArrays a) {
        super(MULT_PRECEDENCE);

        this.k = k;
        this.a = a;
    }

    public String toPython() {
        return k.toPythonPrecedence(precedence())
            + MULT_SIGN + a.toPythonPrecedence(precedence());
    }

    public PythonArray calculate(PythonResultBuffer pb) {
        PythonNumber n = k.calculate(pb);
        PythonArray m = a.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonArray.EMPTY;
        }

        if (!n.isInt()) {
            //Should this be enabled?
            m.setFloatingPoint();
        }

        int size = m.numElems();

        PythonNumber[] result = new PythonNumber[size];

        for (int i = 0; i < size; i++) {
            result[i] = (new PythonMult(n, m.value(i))).calculate(pb);
        }

        return new PythonArray(result);
    }

    public Multiplication toMathsOp() {
        return new Multiplication(k.toMathsOp(), a.toMathsOp());
    }
}
