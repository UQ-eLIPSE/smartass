package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Variable;

/**
 * Describe class PythonArrayVariable here.
 *
 *
 * Created: Fri Dec  5 11:57:53 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public class PythonArrayVariable extends PythonArrays
    implements PythonNumericVariables {

    class VectorAccessVariable extends PythonMaths
        implements PythonNumericVariables {

        private PythonMaths elem;

        private VectorAccessVariable(PythonMaths elem) {
            super(LITERAL_VALUE);
            this.elem = elem;
        }

        public String name() {
            return toPython();
        }

        public void setOp(PythonMaths val, PythonResultBuffer pb) {

            int i = index(pb);

            //Since we can only set values of PythonArray values.
            value = value.calculate(pb);

            if (pb.isCalculable()) {
                ((PythonArray)value).setValue(i,val,pb);
            }
        }

        public void setOp(PythonOp value, PythonResultBuffer pb) {
            if (!validOp(value)) {
                throw new PythonException("Invalid inner value: " + value.toPython());
            }

            setOp((PythonMaths)value, pb);
        }

        private int index(PythonResultBuffer pb) {
            PythonNumber n = elem.calculate(pb);

            pb.setCalculable(n.isIntegralValue());

            if (!pb.isCalculable()) {
                //Type-checks, but invalid
                return -1;
            }

            return (int)n.intValue();
        }

        public PythonNumber calculate(PythonResultBuffer pb) {
            PythonArray a = value.calculate(pb);

            int i = index(pb);

            if (!pb.isCalculable()) {
                return PythonNumber.ZERO;
            }

            return a.value(i);
        }

        public Variable toMathsOp() {
            throw new PythonException("You should never see this...");
        }

        public String toPython() {
            return var + "[" + elem.toPython() + "]";
        }

        public boolean validOp(PythonOp value) {
            return (value instanceof PythonMaths);
        }

    }



    private String var;

    private PythonArrays value;

    /**
     * Creates a new <code>PythonArrayVariable</code> instance.
     *
     */
    public PythonArrayVariable(String varname) {
        super(LITERAL_VALUE);
        var = varname;
    }

    public String name() {
        return var;
    }

    public void setOp(PythonArrays value, PythonResultBuffer pb) {
        this.value = value.calculate(pb);
    }

    public void setOp(PythonOp value, PythonResultBuffer pb) {
        if (!validOp(value)) {
            throw new PythonException("Invalid vector value: " + value.toPython());
        }

        setOp((PythonArrays)value, pb);
    }

    public boolean validOp(PythonOp value) {
        return (value instanceof PythonArrays);
    }

    /**
     * Describe <code>toPython</code> method here.
     *
     * @return a <code>String</code> value
     */
    public String toPython() {
        return var;
    }

    /**
     * Describe <code>toMathsOp</code> method here.
     *
     * @return a <code>Variable</code> value
     */
    public Variable toMathsOp() {
        if (value == null) {
            return new Variable(var);
        } else {
            return new Variable(var, value.toMathsOp());
        }
    }

    public PythonArray calculate(PythonResultBuffer pb) {
        pb.setCalculable(value != null);

        if (!pb.isCalculable()) {
            return PythonArray.EMPTY;
        }

        return value.calculate(pb);
    }

    public VectorAccessVariable elem(PythonMaths elem) {
        return new VectorAccessVariable(elem);
    }

}
