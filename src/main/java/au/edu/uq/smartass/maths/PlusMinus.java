/**
 * This file is part of SmartAss and describes class PlusMinus for 
 * operation a +- b
 *  
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
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
 * Class {@link PlusMinus} - operation a +- b 
 * (e.g. sign plus-minus between first and second operands
 *
 */
public class PlusMinus extends BinaryOp {

	/**
	 * @param op1	first operand
	 * @param op2	second operand
	 */
	public PlusMinus(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		
	}
	
	public String toString() {
		return op[0].toString() + " \\pm " +op[1].toString(); 
	}

}
