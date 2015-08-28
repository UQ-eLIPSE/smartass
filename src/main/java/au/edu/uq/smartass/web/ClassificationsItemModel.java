/* This file is part of SpringAss and describes the ClassificationsItemModel class,
 * the model (as in Model-View-Controller) class for classification of
 * smartass templates.
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

package au.edu.uq.smartass.web;

import java.io.Serializable;

/**
 * The ClassificationsItemModel class represents the model (as in Model-View-Controller) class 
 * for classification of smartass templates. 
 * The repository has up to two-level classification - e.g. "Mathematics" or "Mathematics/Geometry" 
 *
 */
public class ClassificationsItemModel extends NamedItemModel implements Serializable {
	String description; 
	/** The link to the parent classification model (null if this is a top level classification) */
	ClassificationsItemModel parentModel;
	
	/**
	 * The getter for the parent classification model
	 * 
	 * @return	parent classification model
	 */
	public ClassificationsItemModel getParentModel() {
		return parentModel;
	}
	/**
	 * The setter for the parent classification model
	 * 
	 * @param parent_model	parent model
	 */
	public void setParentModel(ClassificationsItemModel parent_model) {
		this.parentModel = parent_model;
	}
	
	/**
	 * This method composes full name from the parent and this classification names.
	 * If this classification has no parent, getFullName returns the same value as getName() 
	 * 
	 * @return	full name composed from the parent and this classification names.
	 */
	public String getFullName() {
		if(parentModel==null)
			return getName();
		else
			return parentModel.getName() + "/" + getName(); 
	}
	
	/**
	 * The getter for the classification description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * The setter for the classification description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
