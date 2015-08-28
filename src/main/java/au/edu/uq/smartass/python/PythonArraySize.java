package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.AbsOp;

/**
 * Describe class PythonArraySize here.
 *
 *
 * Created: Fri Dec  5 13:44:13 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonArraySize extends PythonMaths {

    private PythonArrays vec;

    /**
     * Creates a new <code>PythonArraySize</code> instance.
     *
     */
    public PythonArraySize(PythonArrays vec) {
        super(FUNCTION);
        this.vec = vec;
    }

    public String toPython() {
        return "size" + vec.toPythonBrackets();
    }

    public PythonNumber calculate(PythonResultBuffer pb) {
        PythonArray a = vec.calculate(pb);

        if (!pb.isCalculable()) {
            return PythonNumber.ZERO;
        }

        return new PythonNumber(a.numElems());
    }

    public AbsOp toMathsOp() {
        return new AbsOp(vec.toMathsOp());
    }

}
