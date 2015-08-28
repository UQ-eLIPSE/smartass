/**
 * This file is part of SmartAss and describes class UnaryApprox for 
 * typesetting approximation sign before the operand.
 *  
 * Copyright (C) 2007 Department of Mathematics, The University of Queensland
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
package au.edu.uq.smartass.maths;

/**
 * Class {@link UnaryAppox} - approximation sign 
 * (e.g. approximation sign before the operand)
 *
 */
public class UnaryApprox extends UnaryOp {
    final String APPROX_SIGN =" \\approx "; //default tex
	/**
	 * Constructor
	 * @param op	operand
	 */
	public UnaryApprox(MathsOp op) {
		super(op);
		if (getTex()==null)
  			setTex(new String[]{APPROX_SIGN});
	}
	
	/**
	 * Typeset approximation sign (default latex - "\approx")
	 * before the MathsOp operand.
	 *
	 * @return a string  - latex representation of approximation 
	 * sign and operand 
	 */
	public String toString() {
			return getTex()[0] + op.toString();
	}
}
