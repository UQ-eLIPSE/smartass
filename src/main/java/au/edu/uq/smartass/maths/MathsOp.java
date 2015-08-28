/* 
 * This file is part of SmartAss and describes class MathsOp  - root of MathsOp hierarchy
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
 /**
 * @author 
 * @version 1.00 2006/11/13
 */

package  au.edu.uq.smartass.maths;

import java.util.HashMap;

/** 
 * Calculates result of Op as double or throws exception, if can't. 
 */
public abstract class MathsOp implements Cloneable {
	private static HashMap<String, String[]> tex_map = new HashMap<String, String[]>();
	
	/**
	 * Sets Math-op related TeX settings by MathsOp class name
	 * 
	 * @param className		class name
	 * @param tex			array of TeX strings 
	 */
	static public void setTex(String className, String[] tex) {
		tex_map.put(className, tex);
	}
	
	/**
	 * Sets Math-op related TeX settings by MathsOp class
	 * 
	 * @param cls		class 
	 * @param tex		array of TeX strings 
	 */
	static public void setTex(Class cls, String[] tex) {
		setTex(cls.getName(), tex);
	}
	
	/**
	 * Sets Math-op related TeX settings for <i>this</i> class
	 * 
	 * @param tex			array of TeX strings 
	 */
	public void setTex(String[] tex) {
		setTex(this.getClass(), tex);
	}
	
	/**
	 * Returns Math-op related TeX settings by MathsOp class name
	 * 
	 * @param className		class name
	 * 
	 * @return 				array of TeX strings 
	 */
	static public String[] getTex(String className) {
		return tex_map.get(className);
	}

	/**
	 * Returns Math-op related TeX settings by MathsOp class
	 * 
	 * @param className		class name
	 * 
	 * @return 				array of TeX strings 
	 */
	static public String[] getTex(Class cls) {
		return getTex(cls.getName());
	}

	/**
	 * Returns Math-op related TeX settings for <i>this</i> class
	 * 
	 * @param className		class name
	 * 
	 * @return 				array of TeX strings 
	 */
	public String[] getTex() {
		return tex_map.get(this.getClass().getName());
	}
	
	static public void clearAllTex() {
		tex_map.clear();
		//m.b. more complex activity - set default values
	}
	
	protected MathsOp() {
		super();
	}
	
	//local (e.g. for this MathsOp instance) TeX settings
	private String[] local_tex;
	
	/**
	 * Sets Math-op related TeX settings for this MathOp instance
	 * 
	 * @param tex			array of TeX strings 
	 */
	public void setLocalTex(String[] tex) {
		local_tex = tex;
	}
	
	/**
	 * Returns Math-op related TeX settings forthis MathOp instance
	 * 
	 * @return 				array of TeX strings 
	 */
	public String[] getLocalTex() {
		if(local_tex!=null)
			return local_tex;
		else
			return getTex();
	}

	
	public Object clone() {
		try {
			MathsOp op = (MathsOp) super.clone();
			String[] ltex=getLocalTex();
			if(ltex!=null && ltex!=getTex())
				op.setLocalTex(ltex.clone());
			return op;
		} catch(CloneNotSupportedException e) {
		}
		return null;
	}
	
//	public static final String PI = "\\pi";
	
	public static final String LEFT_BRACKET = "\\left(";
	public static final String RIGHT_BRACKET = "\\right)";
	public static final String EQUALITY_SIGN="=";
	public static final String GREATER_OR_EQUAL_SIGN="\\ge";
	public static final String GREATER_SIGN=">";
	public static final String LESS_OR_EQUAL_SIGN="\\le";
	public static final String LESS_SIGN = "<";
	public static final String UNION_SIGN= "\\cup";
	public static final String INTERSECTION_SIGN= "\\cap";
	public static final String SETDIFFERENCE_SIGN= "\\backslash";
}