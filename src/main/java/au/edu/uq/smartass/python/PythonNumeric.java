/*
 * This file is part of SmartAss and describes class PythonNumeric.
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
 * PythonNumeric represents those Python operations that have a
 * numerical equivalent (or at least their evaluated versions do).
 * The defining point of these operations are that they have an
 * equivalent <code>MathsOp</code> representation.
 *
 * @author Ivan Lazar Miljenovic
 */
public abstract class PythonNumeric extends PythonOp {

    protected PythonNumeric(int precedence) {
        super(precedence);
    }

    /**
     * Creates and returns the equivalent <code>MathsOp</code>
     * representation.
     *
     * @return The <code>MathsOp</code> representation.
     */
    public abstract MathsOp toMathsOp();

}
