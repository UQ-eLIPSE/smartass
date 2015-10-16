/**
 * @(#)Union.java
 *
 * This file is part of SmartAss and describes union of 
 * two logical sets in smartass.
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
 *
 * @version 1.00 2007/04/8
 */
package au.edu.uq.smartass.maths;
import java.util.*; 

public class Union extends SetBinaryOp implements SetCalculable {

    public Union(SetOp op1, SetOp op2) {
    	super(op1, op2);
    }
 
        /**
         *
         *
         */
        @SuppressWarnings("unchecked")
	public AbstractSet calculate() {
		HashSet res=(HashSet)(((SetCalculable)op[0]).calculate());
		res.addAll(((SetCalculable)op[1]).calculate());
		return res;
	}
/**
 * Returns a Latex representation of this Union. 
 * The Latex representation consists of string representation of the first and second sets
 * with union sign between them
 * @return a String representation of this Union.
 */
	public String toString() {
		String res = op[1].toString();
		if(!(op[1] instanceof MathsSet))
			res=LEFT_BRACKET+res+RIGHT_BRACKET;
		if(!(op[0] instanceof MathsSet))
			return LEFT_BRACKET + op[0].toString() + RIGHT_BRACKET +" "+UNION_SIGN+" " +res;
		else 
			return op[0].toString()+" "+UNION_SIGN+" " +res;
	}   
   public String getName() {
   		String name=op[1].getName();
   		if(!(op[1] instanceof MathsSet))
			name=LEFT_BRACKET+name+RIGHT_BRACKET;
		if(!(op[0] instanceof MathsSet))
			return LEFT_BRACKET + op[0].getName() + RIGHT_BRACKET +" "+UNION_SIGN+" " +name;
		else 
			return op[0].getName()+" "+UNION_SIGN+" " +name;
   } 
}
