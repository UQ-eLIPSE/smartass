/**
 * This file is part of SmartAss and describes class Log for 
 * typesetting a logarithm function 
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
 * Class {@link Log} - operation log_{a} b 
 * i.e. typesetting a logarithm function
 *
 */
public class Log extends BinaryOp {
	final String LOG_SIGN = "\\log";	//default tex
	
/**
 * @param op1	base of logarithm
 * @param op2   argument of log function 
 */
	public Log(MathsOp op1, MathsOp op2) {
		super(op1, op2);
		if(getTex()==null)
			setTex(new String[]{LOG_SIGN, "_{", "}"});
	}	
	public String toString(){
		String result = getTex()[0]+getTex()[1]+op[0].toString()+getTex()[2];
		return (new SmartFunction(result,op[1])).toString();
	}	
	
}
