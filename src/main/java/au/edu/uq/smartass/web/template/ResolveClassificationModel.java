/* This file is part of SmartAss and contains the ResolveClassificationModel class that is
 * used to resolve the classification from the metadata found in the template. 
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
package au.edu.uq.smartass.web.template;


/**
 * The ResolveClassificationModel class is used to resolve the classification from the
 * metadata from the template.
 */
public class ResolveClassificationModel extends ResolveItemModel {
	int parentId;
	
	/**
	 * Creates new {@link ResolveClassificationModel} with old_model as a parent
	 */
	public ResolveClassificationModel(ResolveClassificationModel old_model) {
		if(old_model!=null) {
			parentId = old_model.parentId;
			setSearch(old_model.getSearch());
		}
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getParentid() {
		return parentId;
	}
	
	public void setParentid(int parentId) {
		this.parentId = parentId;
	}
}
