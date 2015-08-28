/**
 * 
 * This file is part of SmartAss and describes class IndefiniteIntegral  
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
 * class IndefiniteIntegral represents indefinite integral Op
 *
 */
public class IndefiniteIntegral extends BinaryOp {

	/**
	 * @param expr	expression under integral sign
	 * @param v		variable of integration
	 */
	public IndefiniteIntegral(MathsOp expr, Variable v) {
		super(expr, v);
	}
	
	public String toString() {
		return "\\int \\left(" + op[0].toString() +"\\right) d" + op[1].toString() ;
	}
}
