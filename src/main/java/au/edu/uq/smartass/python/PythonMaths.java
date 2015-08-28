/*
 * This file is part of SmartAss and describes class PythonMaths.
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

/**
 * Covers all standard mathematical operators.  Defined as being all
 * Python operations that evaluate to a number.
 *
 * @author Ivan Lazar Miljenovic
 */
public abstract class PythonMaths extends PythonNumeric {

    protected PythonMaths(int precedence) {
        super(precedence);
    }

    /**
     * As for <code>PythonConstruct</code>, but has to return a
     * <code>PythonNumber</code>.
     *
     * @param pb a <code>PythonResultBuffer</code> to use.
     * @return The result.
     * @see PythonConstruct#calculate
     */
    public abstract PythonNumber calculate(PythonResultBuffer pb);

    public final PythonNumber calculate() {
        return calculate(PythonResultBuffer.NO_OUTPUT);
    }

}
