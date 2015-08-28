package au.edu.uq.smartass.python;

/**
 * Describe interface PythonVariables here.
 *
 *
 * Created: Mon Dec 17 00:49:00 2007
 *
 * @author <a href="mailto:ilm@bart.maths.uq.edu.au">Ivan Miljenovic</a>
 * @version 1.0
 */
interface PythonVariables extends PythonNamed {

    public void setOp(PythonOp value, PythonResultBuffer pb);

    public boolean validOp(PythonOp value);

    //redefine it so PythonAssignment works, requires variables to have
    // public toPython methods
    public String toPython();
}
