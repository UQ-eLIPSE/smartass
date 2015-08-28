/**
 * @(#)Variable.java
 *
 *
 * @author
 * @version 1.00 2007/1/15
 */

package au.edu.uq.smartass.maths;

public class Variable extends MathsOp {

        String var_name;
        MathsOp var_value;

    public Variable(String var_name) {
        super();
        setVarName(var_name);
    }

    public Variable(String var_name, MathsOp var_value) {
        this(var_name);
        setValue(var_value);
    }

    public void setVarName(String new_name) {
        var_name = new_name;
    }

    public String getName() {
        return var_name;
    }

    public String toString() {
        return var_name;
    }

    public void setValue(MathsOp new_value){
        var_value = new_value;
    }

    public MathsOp getValue(){
        return var_value;
    }

    public Equality varEquality() {

        Equality eq = new Equality(this,var_value);

        return eq;
    }

}
