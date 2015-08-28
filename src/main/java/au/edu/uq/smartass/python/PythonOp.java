/*
 * This file is part of SmartAss and describes class PythonOp.
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

//The table below should be fixed up

/**
 * Describes Python constructs that are operations and values
 * (e.g. can be stored in a variable).
 *
 * <code>PythonOp</code> objects have the concept of "precedence"
 * which is used to decide order of operations.  The following table
 * (with the exception of the first and last rows) is taken from <a
 * href=http://www.python.org/doc/2.4/ref/summary.html>the Python
 * documentation</a> .  Operators higher up in the table have lower
 * precedence.

 <table>
        <tr>    <th>Precedence</th>       <th>Operator</th>             <th>Description</th></tr>
        <tr>    <td>0</td>      <td>&nbsp;</td>                              <td>Default precedence</td></tr>
        <tr>    <td>1</td>      <td>lambda</td>                         <td>Lambda expression</td></tr>
        <tr>    <td>2</td>      <td>or</td>                             <td>Boolean OR</td></tr>
        <tr>    <td>3</td>      <td>and</td>                            <td>Boolean AND</td></tr>
        <tr>    <td>4</td>      <td>not x</td>                          <td>Boolean NOT</td></tr>
        <tr>    <td>5</td>      <td>in, not in</td>                     <td>Membership tests</td></tr>
        <tr>    <td>6</td>      <td>is, is not</td>                     <td>Identity tests</td></tr>
        <tr>    <td>7</td>      <td><, <=, >, >=, <>, !=, == </td>      <td>Comparisons</td></tr>
        <tr>    <td>8</td>      <td>|</td>                              <td>Bitwise OR</td></tr>
        <tr>    <td>9</td>      <td>^</td>                              <td>Bitwise XOR</td></tr>
        <tr>    <td>1</td>      <td>&</td>                              <td>Bitwise AND</td></tr>
        <tr>    <td>11</td>     <td><<, >></td>                         <td>Shifts</td></tr>
        <tr>    <td>12</td>     <td>+, -</td>                           <td>Addition and subtraction</td></tr>
        <tr>    <td>13</td>     <td>*, /, %</td>                        <td>Multiplication, division, remainder</td></tr>
        <tr>    <td>14</td>     <td>+x, -x</td>                         <td>Positive, negative</td></tr>
        <tr>    <td>15</td>     <td>~x</td>                             <td>Bitwise not</td></tr>
        <tr>    <td>16</td>     <td>**</td>                             <td>Exponentiation</td></tr>
        <tr>    <td>17</td>     <td>x.attribute</td>                    <td>Attribute reference</td></tr>
        <tr>    <td>18</td>     <td>x[index]</td>                       <td>Subscription</td></tr>
        <tr>    <td>19</td>     <td>x[index:index]</td>                 <td>Slicing</td></tr>
        <tr>    <td>20</td>     <td>f(arguments...)</td>                <td>Function call</td></tr>
        <tr>    <td>21</td>     <td>(expressions...)</td>               <td>Binding or tuple display</td></tr>
        <tr>    <td>22</td>     <td>[expressions...]</td>               <td>List display</td></tr>
        <tr>    <td>23</td>     <td>{key:datum...}</td>                 <td>Dictionary display</td></tr>
        <tr>    <td>24</td>     <td>`expressions...`</td>               <td>String conversion</td></tr>
        <tr>    <td>25</td>     <td>2, "string", etc.</td>              <td>Literal values</td></tr>
 </table>

 *
 * @author Ivan Lazar Miljenovic
 */
public abstract class PythonOp extends PythonConstruct {

    /**
     * The precedence of a literal value (number, String, etc.)
     */
    protected final static int LITERAL_VALUE = 26;

    /**
     * Precedence values of functions.
     */
    protected final static int FUNCTION = 20;

    /**
     * Default precedence if none specified.
     */
    protected final static int DEFAULT_PRECEDENCE = 0;

    private int precedence;

    /**
     * Creates a new <code>PythonOp</code> instance with default
     * precedence.
     */
    protected PythonOp() {
        this(DEFAULT_PRECEDENCE);
    }

    /**
     * Creates a new <code>PythonOp</code> instance with the given
     * precedence.
     *
     * @param precedence the precedence this <code>PythonOp</code> has.
     */
    protected PythonOp(int precedence) {
        super();

        this.precedence = precedence;
    }

    /**
     * Get the precedence of this <code>PythonOp</code>
     *
     * @return the precedence
     */
    protected final int precedence() {
        return precedence;
    }

    /**
     * Calculate this <code>PythonOp</code>.  Must return a
     * <code>PythonOpResults</code> rather than just an ordinary
     * <code>PythonResults</code> value.  This ensures that it can't
     * return a <code>PythonNullOp</code>.
     *
     * @param pb the <code>PythonResultBuffer</code> to use
     * @return The result
     */
    public abstract PythonOpResults calculate(PythonResultBuffer pb);

    /**
     * As above, but use a default <code>PythonResultBuffer</code>
     * that doesn't record results (for quick and dirty calculations:
     * might be removed in the future): redefined to get the types right.
     *
     * @return The calculated value
     * @see #calculate
     */
    public PythonOpResults calculate() {
        return calculate(PythonResultBuffer.NO_OUTPUT);
    }

    /**
     * Wraps the result of <code>toPython()</code> in brackets.
     *
     * @return the bracketed Python code.
     * @see PythonConstruct#toPython
     */
    protected final String toPythonBrackets() {
        return "(" + toPython() + ")";
    }

    /**
     * Returns the Python code, wrapping it in brackets if the given
     * precedence (of the surrounding <code>PythonOp</code>) is of a
     * lower precedence.
     *
     * @param outer_precedence The precedence of the outer operation.
     * @return The possibly bracketed Python code.
     * @see PythonConstruct#toPython
     * @see #toPythonBrackets
     */
    protected final String toPythonPrecedence(int outer_precedence) {
        if (outer_precedence <= precedence) {
            return toPython();
        } else {
            return toPythonBrackets();
        }
    }

}
