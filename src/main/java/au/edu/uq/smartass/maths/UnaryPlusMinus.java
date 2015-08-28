/**
 * This file is part of SmartAss and describes class UnaryPlusMinus for 
 * operation  +- a
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
 * Class {@link UnaryPlusMinus} - operation +- b 
 * (e.g. sign plus-minus before the operand
 *
 */
public class UnaryPlusMinus extends UnaryOp {
    final String PM_SIGN =" \\pm "; //default tex
	/**
	 * @param op	operand
	 */
	public UnaryPlusMinus(MathsOp op) {
		super(op);
		if (getTex()==null)
  			setTex(new String[]{PM_SIGN});
	}
	
	public String toString() {
		
		String res=op.toString();
		if(res.charAt(0)=='-' || op instanceof Addition || op instanceof Subtraction)
			return getTex()[0]+ LEFT_BRACKET + op.toString()+RIGHT_BRACKET;
		else
			return getTex()[0] + op.toString();
	}
}
