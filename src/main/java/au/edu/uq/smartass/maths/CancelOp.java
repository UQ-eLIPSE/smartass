/*
 * @(#)CancelOp.java
 * This file is part of SmartAss and describes class CancelOp for 
 * cancelling terms in tex-documents using cancel.sty. Use this class
 * only with cancel.sty installed. 
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
 *
 *
 * @version 1.00 2007/1/31
 */

package au.edu.uq.smartass.maths;
import au.edu.uq.smartass.maths.FloatCalculable;
/**
* Class CancelOp contains toString() method for 
* typesetting cancelled terms in tex- documents. 
* CancelOp.toString() needs cancel.sty installed.
* @version 1.0 31.01.2007
*/

public class CancelOp extends UnaryOp implements FloatCalculable {
	public double calculate() {
		//ToDo: throw exception here - can't calculate!!!
		return - ((FloatCalculable)op).calculate();
	}

/**
 * Constructor, instantiate CancelOp.
 * @param MathsOp op
 */	
		
public CancelOp(MathsOp op) {
		super(op);
	}	

/**
 * Method returns "\ensuremath{\cancel{" + op.toString()+"}}", i.e.
 * latex code for the cancelled term.
 * @return "\ensuremath{\cancel{" + op.toString()+"}}";
 * 
 */	
public String toString() {	
			return "\\ensuremath{\\cancel{" + op.toString()+"}}";
	}
}
