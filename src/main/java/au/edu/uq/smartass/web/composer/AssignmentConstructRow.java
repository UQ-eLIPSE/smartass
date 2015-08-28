/* This file is part of SmartAss and contains the AssignmentConstructRow interface - the abstract base for 
 * all assignment constructions classes   
 * 
 * Copyright (C) 2008 The University of Queensland
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
package au.edu.uq.smartass.web.composer;

/**
 * The AssignmentConstructRow interface is base for all assignment constructions.
 * Assignment construction is a one of template language control statements, for example
 * "%%REPEAT ... %%ENDREPEAT" or "%%CALL SomeTemplate.tex". See SmartAss user guide for 
 * full list of template language constructions.
 * Classes that implements this interface are used for interactive composition of assignment through 
 * SmartAss web site.
 *
 */
public interface AssignmentConstructRow {
	/**
	 * Returns the kind of the construction as String.
	 * This is used in jsp code to distinguish different types of template language constructions.
	 */
	public String getKind();
	
	/**
	 * Returns the HTML representation of a template language construction.
	 */
	public String getHtml();
	
	/**
	 * The getter for the assignment property - the assignment this construction belongs to.
	 */
	public AssignmentConstruct getAssignment();
	
	/**
	 * The setter for the assignment property - the assignment this construction belongs to.
	 */
	public void setAssignment(AssignmentConstruct assignment);
	
	/**
	 * This function executed by SmartAss web site engine on construction removal
	 */
	public void onRemove();
	
	/**
	 * Can this construct be a parent of some other construct?
	 */
	public boolean canParent();
}
