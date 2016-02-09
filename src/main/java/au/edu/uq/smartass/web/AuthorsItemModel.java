/* This file is part of SmartAss and contains class AuthorsItemModel - the model class for author of
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

/**
 * The AuthorsItemModel class represents the model (as in Model-View-Controller) for the author of
 * smartass templates 
 * 
 */
public class AuthorsItemModel extends NamedItemModel {
	
	private String description;

	/** The getter for author's description 
	 */ 
	public String getDescription() {
		return description;
	}

	/** The setter for author's description 
	 * 
	 */ 
	public void setDescription(String description) {
		this.description = description;
	}
}
