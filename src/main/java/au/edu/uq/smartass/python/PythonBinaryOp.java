/*
 * This file is part of SmartAss and describes class PythonBinaryOp.
 * Copyright (C) 2008 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package au.edu.uq.smartass.python;

import au.edu.uq.smartass.maths.MathsOp;

/**
 * This class represents mathematical functions/constructs that take
 * in two arguments.
 *
 * @author Ivan Lazar Miljenovic
 */
public abstract class PythonBinaryOp extends PythonMaths {

    private PythonMaths n1, n2;

    //left, inner and right formatting components.
    private String left, inner, right;

    /**
     * The different types of associativity that a binary op can have.
     * Note that Python only has NOT, LEFT and RIGHT associativity,
     * but TOTAL has been included for operations such as addition
     * since it's easier/nicer to read "1 + 2 + 3" than "(1 + 2) + 3".
     */
    protected static enum Assocs {NOT, TOTAL, LEFT, RIGHT};

    //The associativity of this particular binary op
    private Assocs associativity;

    /**
     * Creates a new <code>PythonBinaryOp</code> instance with
     * <code>TOTAL</code> associativity and no text on either side.
     *
     * @param precedence The op's precedence
     * @param n1 The left/first value
     * @param n2 The right/second value
     * @param inner The <code>String</code> symbol between the values.
     */
    public PythonBinaryOp(int precedence,
                          PythonMaths n1, PythonMaths n2,
                          String inner) {
        this(precedence, n1, n2, inner, Assocs.TOTAL);
    }

    /**
     * Creates a new <code>PythonBinaryOp</code> instance with no text
     * on either side.
     *
     * @param precedence The op's precedence
     * @param n1 The left/first value
     * @param n2 The right/second value
     * @param inner The <code>String</code> symbol between the values.
     * @param associativity The op's associativity.
     */
    public PythonBinaryOp(int precedence,
                          PythonMaths n1, PythonMaths n2,
                          String inner, Assocs associativity) {
        this(precedence, n1, n2, "", inner, "", associativity);
    }

    /**
     * Creates a new <code>PythonBinaryOp</code> instance with
     * <code>TOTAL</code> associativity.
     *
     * @param precedence an <code>int</code> value
     * @param n1 a <code>PythonMaths</code> value
     * @param n2 a <code>PythonMaths</code> value
     * @param left The <code>String</code> at the beginning of the Python code.
     * @param inner The <code>String</code> symbol between the values.
     * @param right The <code>String</code> at the end of the Python code.
     */
    public PythonBinaryOp(int precedence,
                          PythonMaths n1, PythonMaths n2,
                          String left, String inner, String right) {
        this(precedence, n1, n2, left, inner, right, Assocs.TOTAL);
    }

    /**
     * Creates a new <code>PythonBinaryOp</code> instance.
     *
     * @param precedence an <code>int</code> value
     * @param n1 a <code>PythonMaths</code> value
     * @param n2 a <code>PythonMaths</code> value
     * @param left The <code>String</code> at the beginning of the Python code.
     * @param inner The <code>String</code> symbol between the values.
     * @param right The <code>String</code> at the end of the Python code.
     * @param associativity The op's associativity.
     */
    public PythonBinaryOp(int precedence,
                          PythonMaths n1, PythonMaths n2,
                          String left, String inner, String right,
                          Assocs associativity) {
        super(precedence);

        this.n1 = n1;
        this.n2 = n2;

        this.left = left;
        this.inner = inner;
        this.right = right;

        this.associativity = associativity;
    }

    public final String toPython() {
        StringBuffer sb = new StringBuffer(left);

        sb.append(opPython(Assocs.RIGHT, n1));

        sb.append(inner);

        sb.append(opPython(Assocs.LEFT, n2));

        sb.append(right);

        return sb.toString();
    }

    //Properly convert the given PythonMaths to Python code.
    private String opPython(Assocs assoc, PythonMaths n) {
        if ((associativity == assoc || associativity == Assocs.NOT)
            && precedence() == n.precedence()) {
            return n.toPythonBrackets();
        } else {
            return n.toPythonPrecedence(precedence());
        }
    }

    /**
     * Creates the <code>MathsOp</code> representation of the
     * left/first value.
     *
     * @return The mathematical representation of the left/first value.
     */
    protected final MathsOp leftMaths() {
        return n1.toMathsOp();
    }

    /**
     * Creates the <code>MathsOp</code> representation of the
     * right/second value.
     *
     * @return The mathematical representation of the right/second value.
     */
    protected final MathsOp rightMaths() {
        return n2.toMathsOp();
    }

    /**
     * Calculates the binary op, using the values from
     * <code>calculatedValue</code>, <code>isIntResult</code> and
     * <code>isValid</code>.
     *
     * @param pb the <code>PythonResultBuffer</code> to use
     * @return The calculated result.
     */
    public final PythonNumber calculate(PythonResultBuffer pb) {

        PythonNumber num1 = n1.calculate(pb);
        PythonNumber num2 = n2.calculate(pb);

        //One of the params isn't valid.
        if (!pb.isCalculable()) {
            return PythonNumber.ZERO;
        }

        double v1 = num1.value();
        double v2 = num2.value();

        //Extra sanity checks.
        pb.setCalculable(isValid(v1, v2));

        if (!pb.isCalculable()) {
            return PythonNumber.ZERO;
        }

        //What type of numerical value is returned.
        boolean isInt = isIntResult(num1.isInt(), num2.isInt(), v1, v2);

        //The actual calculated value.
        double value = calculatedValue(v1, v2, isInt);

        PythonNumber r = new PythonNumber(value, isInt);

        //Checking for overflows, etc.
        return r.calculate(pb);
    }

    /**
     * Calculate the actual value of this binary op.
     *
     * @param v1 The left/first value.
     * @param v2 The right/second value.
     * @param isInt Is the result expected to be an integer?
     * @return The calculated value.
     */
    protected abstract double calculatedValue(double v1, double v2, boolean isInt);

    /**
     * Whether or not the calculated value will be an integer.
     * Defaults to <code>b1 && b2</code>.
     *
     * @param b1 If the left/first value is an integer.
     * @param b2 If the right/second value is an integer.
     * @param v1 The left/first value.
     * @param v2 The right/second value.
     * @return If the result will be an integer.
     */
    protected boolean isIntResult(boolean b1, boolean b2, double v1, double v2) {
        return b1 && b2;
    }

    /**
     * Perform sanity checks on the input to determine if the
     * operation is valid (e.g. checking for division by zero).
     * Defaults to <code>true</code>.
     *
     * @param v1 The left/first value.
     * @param v2 The right/second value.
     * @return If the result will be valid.
     */
    protected boolean isValid(double v1, double v2) {
        return true;
    }

}
