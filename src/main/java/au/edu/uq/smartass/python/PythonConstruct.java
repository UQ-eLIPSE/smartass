/*
 * This file is part of SmartAss and describes class PythonConstruct.
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

import java.util.Arrays;

/**
 * <code>PythonConstruct</code> is the root class of objects representing Python code.
 *
 * @author Ivan Lazar Miljenovic
 */
public abstract class PythonConstruct {

    /**
     * The number of spaces in a tab.
     *
     */
    protected final static int TABCOUNT = 4;
    protected final static char SPACE_CHAR = ' ';
    protected final static String SPACE = " ";
    protected final static int DEFAULT_INDENT = 0;
    protected final static String NEWLINE = "\n";

    /**
     * Creates a new <code>PythonConstruct</code> instance.
     *
     */
    public PythonConstruct() {
        super();
    }

    /**
     * Creates a string with spaces, length of the given number of tabstops.
     *
     * @param n an <code>int</code> value stating how many tabstops to create.
     * @return a <code>String</code> containing spaces with <code>n</code> tabstops.
     * @see #numSpaces
     */
    protected static final String numTabs(int n) {
        return numSpaces(n * TABCOUNT);
    }

    /**
     * Create a <code>String</code> with the given number of spaces.
     *
     * @param n the number of spaces
     * @return a <code>String</code> containing <code>n</code> spaces.
     */
    protected static final String numSpaces(int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, SPACE_CHAR);

        return new String(chars);
    }

    /**
     * Generate the Python code representing this
     * <code>PythonConstruct</code>.  Must be implemented by every
     * extending class.
     *
     * @return the Python code as a <code>String</code>
     */
    public abstract String toPython();

    /**
     * Generates the Python code representing this
     * <code>PythonConstruct</code> indented by
     * <code>indentLevel</code> tabstops.  By default, it indents the
     * result of <code>toPython()</code>.
     *
     * @param indentLevel an <code>int</code> representing how much to indent by.
     * @return the indented Python code
     * @see #toPython
     * @see #numTabs
     */
    public String toPython(int indentLevel) {
        StringBuffer sb = new StringBuffer(numTabs(indentLevel));
        sb.append(toPython());

        return sb.toString();
    }

    /**
     * Calculate the result of running this construct in Python
     * itself.  This should also perform sanity checks on any inputs,
     * etc. and set the calculability of the given
     * <code>PythonResultBuffer</code> appropriately.  If at any stage
     * the construct is incalculable, then bail out by returning a
     * default value.
     *
     * By default, the construct does nothing and returns a
     * <code>PythonNullOp</code>.
     *
     * @param pb The <code>PythonResultBuffer</code> to use.
     * @return The result
     */
    public PythonResults calculate(PythonResultBuffer pb) {
        return PythonNullOp.NULL_OP;
    }

    /**
     * As above, but use a default <code>PythonResultBuffer</code>
     * that doesn't record results (for quick and dirty calculations:
     * might be removed in the future).
     *
     * @return The calculated value
     * @see #calculate
     */
    public PythonResults calculate() {
        return calculate(PythonResultBuffer.NO_OUTPUT);
    }

}
