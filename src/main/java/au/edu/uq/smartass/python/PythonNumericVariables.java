package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.Variable;

/**
 * Describe interface PythonNumericVariable here.
 *
 *
 * Created: Mon Dec 17 03:42:36 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
public interface PythonNumericVariables extends PythonVariables {

    public Variable toMathsOp();

}
