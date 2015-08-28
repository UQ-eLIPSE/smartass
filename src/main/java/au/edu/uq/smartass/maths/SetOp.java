/* @(#)SetOp.java
 *
 * This file is part of SmartAss and describes abstact class SetOp for 
 * operations on logical sets in smartass.
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
 */


package  au.edu.uq.smartass.maths;
import java.util.*;
/**
* Class SetOp describes operation on logical sets for use in smartass
*
* @version 1.0 10.01.2007
*/
public abstract class SetOp extends MathsOp {
	abstract public String toString();
	abstract public String getName();
}
