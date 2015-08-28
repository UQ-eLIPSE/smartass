/* This file is part of SmartAss and contains class AssignmentsItemModel - the model class for an assignment.
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

import java.util.Date;

/**
 * The AssignmentsItemModel class represents a model (as in Model-View-Controller) for an assignment. 
 *
 */
public class AssignmentsItemModel extends NamedItemModel {
	UserItemModel user;
	String description;
	Date dtcreated;
	String tags;
	
	/**
	 * Returns the user who is the author of this assignment
	 * 
	 * @return	user who is the author of this assignment
	 */
	public UserItemModel getUser() {
		return user;
	}
	
	/**
	 * Sets the user who is the author of this assignment
	 * 
	 * @param user
	 */
	public void setUser(UserItemModel user) {
		this.user = user;
	}
	
	/**
	 * Returns the description of the assignment
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the assignment
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the date when the assignment was created 
	 */
	public Date getDtcreated() {
		return dtcreated;
	}
	
	/**
	 * Sets the date when the assignment was created
	 */
	public void setDtcreated(Date dtcreated) {
		this.dtcreated = dtcreated;
	}
	
	/**
	 * Sets tags given by user to this assignment
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	/**
	 * Returns tags given by user to this assignment
	 */
	public String getTags() {
		return tags;
	}
}
