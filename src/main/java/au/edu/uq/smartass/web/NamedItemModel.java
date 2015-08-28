/** This file is part of SmartAss and contains the {@link NamedItemModel} class that is 
 * the parent class for all dictionary data models that have the "Name" field. This class extends
 * {@link IntIdItemModel} so each {@link NamedItemModel} instance has its own unique id. 
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
 * The NamedItemModel class is the parent class for all dictionary data models that have the "Name" field. 
 * This class extends {@link IntIdItemModel} so each {@link NamedItemModel} instance has its own unique id.
 *
 */
public class NamedItemModel extends IntIdItemModel {
	private String name = "";

	/**
	 * The getter for the item's name
	 *   
	 * @return	author's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter for the item's name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
