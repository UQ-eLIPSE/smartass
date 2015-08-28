/* This file is part of SmartAss and contains the IntIdItemModel class - 
 * the parent class for all data models of entities with integer id.
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
 * The parent class for all data models of entities with integer id.
 */
public class IntIdItemModel implements Serializable  {
	/** The item id */
	protected int id;

	/** The getter for the item id */
	public int getId() {
		return id;
	}
	
	/** The setter for the item id */
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	/**
	 * Two IntIdItemModel objects is equal if they have same ids (primary keys).
	 * 
	 * @param obj 	the object to be compared with this object 
	 */
	public boolean equals(Object obj) {
		return id!=0 && //zero id items cannot be compared
			(obj instanceof IntIdItemModel) &&   	  
			((IntIdItemModel)obj).id==id;
	}
	
}
