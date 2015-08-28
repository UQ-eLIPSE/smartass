package au.edu.uq.smartass.python;

/**
 * Describe class PythonLogic here.
 *
 *
 * Created: Thu Dec 11 10:38:04 2008
 *
 * @author <a href="mailto:ilm@morot.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public abstract class PythonLogic extends PythonOp {

    /**
     * Creates a new <code>PythonLogic</code> instance.
     *
     */
    protected PythonLogic(int precedence) {
        super(precedence);
    }

    public abstract PythonBool calculate(PythonResultBuffer pb);

    public final PythonBool calculate() {
        return calculate(PythonResultBuffer.NO_OUTPUT);
    }

    //When constructing complex logic constructs, ignore Python's precedence
    //of operators and bracket any logic construct that isn't a literal boolean.
    //By checking the precedence, this allows us to extend this to variables as well.
    protected final String toPythonLogic() {
        if (precedence() == LITERAL_VALUE) {
            return toPython();
        } else {
            return toPythonBrackets();
        }
    }
}
