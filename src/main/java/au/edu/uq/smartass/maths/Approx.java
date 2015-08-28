/**
 * This file is part of SmartAss and describes class Approx for 
 * typesetting approximation sign between the operands.
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
 * Class {@link Appox} - approximation op 
 * (e.g. approximation sign between the two parts of equation)
 *
 */
public class Approx extends BinaryOp {
    final String APPROX_SIGN =" \\approx "; //default tex
	/**
	 * Constructor
	 * @param op1	first operand - left part of equation
	 * @param op2   second operand - right part of equation
	 */
	public Approx(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		if (getTex()==null)
  			setTex(new String[]{APPROX_SIGN});
	}
	
	/**
	 * Typeset an approximation sign (default latex - "\approx")
	 * before the MathsOp operands (i.e. between the parts of
	 * equation).
	 *
	 * @return a string  - latex representation of approximation 
	 * sign between the operands 
	 */
	public String toString() {
			return op[0].toString() + getTex()[0] + op[1].toString();
	}
}
