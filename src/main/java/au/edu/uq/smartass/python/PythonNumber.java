/*
 * This file is part of SmartAss and describes class PythonNumber.
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
import au.edu.uq.smartass.maths.LongNumber;
import au.edu.uq.smartass.maths.DecimalNumber;
import au.edu.uq.smartass.maths.Variable;

import java.math.MathContext;

/**
 * Represents a Python number.  There are two main considerations to take care of:
 *
 <ul>
 <li> Representing both integers and decimals in a sane fashion that
      allows dynamic conversion from one to the other (usually int to double).
 <li> Python treats negative numbers as having a negation operation
      applied to the positive value (mainly for precedence purposes).
      Since we can't do that here (we're expecting a
      <code>PythonNumber</code>, not a <code>PythonNegation</code> applied
      to a number), this requires a slight amount of hackery.
 </ul>

 *
 * @author Ivan Lazar Miljenovic
 */
public class PythonNumber extends PythonMaths
    implements PythonOpResults {


    /**
     * Slightly altered value that has a <code>MathsOp</code> variable
     * value, as well as the Python <code>pi</code> representation
     * rather than the value.
     */
    public static final PythonNumber PI = new PythonPi();

    /**
     * The value <code>0</code>.  Use this as the default return value
     * if a <code>PythonMaths</code> calculation fails.
     */
    public static final PythonNumber ZERO = new PythonNumber(0,true);

    /**
     * The value <code>1</code>.
     */
    public static final PythonNumber ONE = new PythonNumber(1,true);

    //Allowed maximum rounding error: value must be within 1%
    private static final double ETA = 0.01;

    //For printing purposes to avoid too many decimal digits.
    private final static int MAX_DEC = 6;
    private final static int MAX_PREC = MAX_DEC + 3;

    //Use a double value to represent the number.  Difference between
    //an Int and a Double is how they're printed and what the MathsOp
    //value is.
    private double num;

    //Flags to tell if this number represents an integer, and if it's a valid value.
    private boolean isInt, validNumber;

    /**
     * Create a new integer value.
     *
     * @param num The <code>int</code> value.
     */
    public PythonNumber(int num) {
        this(1.0*num, true);
    }

    /**
     * Create a new decimal value.
     *
     * @param num The <code>double</code> value.
     */
    public PythonNumber(double num) {
        this(num, false);
    }

    /**
     * Create a new <code>PythonNumber</code>, specifying if it's
     * meant to be an integer or not.
     *
     * @param num The numeric value.
     * @param isInt Whether this is meant to be an integer or not.
     */
    PythonNumber(double num, boolean isInt) {
        /*
          Normally, numbers are literal values... unless its negative,
          in which case we use a hack to fix the precedence up when
          printing python code, etc.  This should be replaced by an
          actual composition of a (non-negative) number and a negation
          operator, which requires either having negation extending
          PythonNumber, or having calculate() return a PythonMaths
          instead of PythonNumber.  Probably want to make the
          constructors private then, and have helper creator
          functions.
        */
        super(num >= 0 ? LITERAL_VALUE : PythonNegation.NEGATION_PRECEDENCE);

        this.isInt = isInt;

        validNumber = valueCheck(num, isInt);

        this.num = num;
    }

    /**
     * The textual representation of this value.  Tries to avoid too
     * many decimal digits if it's a double value.
     *
     * @return The Python code representation.
     */
    public String toPython() {
        if (isInt) {
            return "" + intValue();
        } else {
            //Finding out how many decimal digits are actually there...
            String[] parts = (""+num).split("\\.");
            String[] decs = parts[1].split("E");
            char[] ds = decs[0].toCharArray();

            int numDec = ds.length;

            while (numDec > 0 && ds[numDec-1] == '0') {
                numDec--;
            }

            numDec = Math.max(1, Math.min(MAX_DEC, numDec));

            numDec += parts[0].length();

            numDec = Math.min(MAX_PREC, numDec);

            String fmt = "%." + numDec + "g";

            return String.format(fmt, num);
        }
    }

    public String printOutput() {
        return toPython();
    }

    /**
     * Creates the appropriate <code>MathsOp</code> numeric class.
     *
     * @return A <code>LongNumber</code> (due to the possible size of
     *         the number stored) for integers, and a
     *         <code>DecimalNumber</code> otherwise.
     */
    public MathsOp toMathsOp() {
        if (isInt) {
            return new LongNumber(intValue());
        } else {
            return new DecimalNumber("" + num, MathContext.DECIMAL64);
        }
    }

    public PythonNumber calculate(PythonResultBuffer pb) {
        pb.setCalculable(validNumber);

        return this;
    }

    /**
     * Returns the integral value of the number stored.
     *
     * @return The integral value as a <code>long</code> value
     */
    public long intValue() {
        return intValue(num);
    }

    private static long intValue(double n) {
        return Math.round(n);
    }

    /**
     * Whether or not this represents an integral value.
     *
     * @return <code>true</code> if and only if the number is an
     *         integral value.
     */
    public boolean isInt() {
        return isInt;
    }

    /**
     * Determine if the number being stored is <em>meant</em> to be an
     * integral value, despite being classified as a double.
     *
     * @return <code>true</code> if and only if there are no decimal points.
     */
    public boolean isIntegralValue() {
        return ((double)intValue() == num);
    }

    /**
     * Set this value to be a double.
     */
    void setFloatingPoint() {
        isInt = false;
    }

    /**
     * Set this value to be an integer.
     */
    void setInt() {
        isInt = true;

        num = 1.0*intValue();
    }

    /**
     * The numeric value stored in this <code>PythonNumber</code>.
     *
     * @return The value as a <code>double</code>.
     */
    public double value() {
        if (isInt) {
            return intValue();
        } else {
            return num;
        }
    }

    //Checks sanity of this value by checking for overflow, underflow,
    //etc.
    private static boolean valueCheck(double n, boolean isInt) {
        if (n == Double.POSITIVE_INFINITY) {
            return false;
        }

        if (n == Double.NEGATIVE_INFINITY) {
            return false;
        }

        if (Double.isNaN(n)) {
            return false;
        }

        if (Math.abs(n) >= Double.MAX_VALUE) {
            return false;
        }

        if ((Math.abs(n) <= Double.MIN_VALUE) && (n != 0)) {
            return false;
        }

        //Allow a slight amount of rounding error
        if (isInt && Math.abs((n - intValue(n)) / n) >= ETA) {
            return false;
        }

        return true;
    }

}

//A sub-class to override the toPython and toMathsOp methods.
class PythonPi extends PythonNumber {

    /**
     * Creates a new <code>PythonPi</code> instance.
     *
     */
    public PythonPi() {
        super(Math.PI);
    }

    public String toPython() {
        return "pi";
    }

    public MathsOp toMathsOp() {
        return new Variable("\\pi");
    }
}
