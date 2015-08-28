/* @(#)MathsSet.java
 *
 * This file is part of SmartAss and describes class MathsSet for 
 * logical sets in smartass.
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


package au.edu.uq.smartass.maths;
import java.util.*;
//import au.edu.uq.smartass.maths.MathsOp;

/**
* Class MathsSet describes logical sets for use in smartass
*
* @version 1.0 10.01.2007
*/
public class MathsSet extends SetOp implements SetCalculable {
	protected HashSet set;
	protected String name;


/**
 * Constructor MathsSet initializes a set n with HashSet s
 * @param  n name of set
 * @param  s container for set	
 */	
	public MathsSet (String n, HashSet s) {
		setSet(s);
		setName(n);
	}
/**
 * Constructor MathsSet creates a set n
 * @param  n name of set
 */	
	public MathsSet(String n) {
		set=new HashSet();
		setName(n);
	}			
/**
 * Constructor MathsSet initializes a set with HashSet s
 * @param  s container for set	
 */	
	public MathsSet ( AbstractSet s) {
		setSet(s);
	}				
/**
 * Returns a latex-string that contains all the elements of a set.
 * Method uses String.valueOf(E) to print an element E.
 * String return is in \ensuremath command, elements - in brackets, listed
 * separated by ","
 * @return  latex-string representing this set. 	
 */			

	public String toString() {	
	    if (set.isEmpty())
	    	return "\\ensuremath{\\emptyset}"; 
	    String setInLatex="\\ensuremath{\\{";	
		for (Iterator i=set.iterator();i.hasNext();){
			setInLatex=setInLatex+String.valueOf(i.next())+",";
		}
		
		return  setInLatex.substring(0,setInLatex.length()-1)+"\\} }";
	}
/**
 * Set this set's name.
 * @param  n name of set. 	
 */			
	public void setName(String n) {name=n;	}

/**
 * Returns this set's name.
 * @return name of this set. 	
 */		
	public String getName() {return name;}

/**
 * Returns this set's container set.
 * @return name of this set. 	
 */		
	public AbstractSet getSet() {return (AbstractSet)set.clone();}
	
/**
 * Returns true if this set contains no elements
 * @return true if this set contains no elements. 	
 */		
	public boolean isEmpty() {return set.isEmpty(); }	

/**
 * Sets container HashSet to s
 * @param s set   	
 */				 
	public void setSet(AbstractSet s) {
		this.set = new HashSet(s);
	}
	
/**
 * Adds the specified element to this set if it is not already present. 
 * @param  o element to be added
 * @return true if the set did not already contain the specified element.  	
 */				 
public boolean add(Object o) {
 return set.add(o);
}

/**
 * Removes the specified element from this set. 
 * @param  o element to be removed
 * @return true if the set contained the specified element.
 */				 
public boolean remove(Object o) {
 return set.remove(o);
}

/**
 * Returns the number of elements in this set.
 * @return the number of elements in this set.
 */				 
public int size() {
 return set.size();
}
			
/**
 *Realises abstract method SetOp.calculate(). 
 *Returns itself. 
 *@return this.
 *
 */		 	 	
	public AbstractSet calculate() {
		return (AbstractSet)set.clone();	
};	

/**
 *Clones this MathsSet. 
 *Returns clone of this MathsSet. 
 *@return clone of this MathsSet.
 *
 */	
public Object clone() {
		MathsSet res = (MathsSet) super.clone();
		if(set!=null)
			res.set = (HashSet) (set.clone());
		return res;
	} 
}
