/* This file is part of SmartAss and contains the ListResolverModel class that is
 * used when import template to the repository to resolve list of items from the metadata
 * received as plain strings to the objects from the repository. 
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The ListResolverModel class is
 * used when import template to the repository to resolve list of items from the metadata
 * received as plain strings to the objects from the repository.
 */
@SuppressWarnings("unchecked")
public class ListResolverModel implements Serializable {
	int itemsToImportNo;
	int currentItemNo;
	int resolvePosition;
	
	/**
	 * The BooleanList class is used to connect checkboxes on web page view to boolean values of the model.
	 * In the context of {@link ListResolverModel} it means that corresponding list element is selected (or not selected)
	 * to be resolved and imported into the repository.
	 */
	public class BooleanList extends ArrayList {
		@Override
		public Boolean set(int index, Object element) {
			if(element instanceof String)
				element = Boolean.valueOf((String) element);
			return (Boolean) super.set(index, element);
		}
		
		@Override
		public Boolean get(int index) {
			Object o = super.get(index);
			if(o instanceof String)
				return Boolean.valueOf((String)o);
			return (Boolean) o;
		}
	}

	BooleanList imports = new BooleanList();
	BooleanList founds = new BooleanList();

	boolean required;
	
	/**
	 * Creates new {@link ListResolverModel} 
	 */
	public ListResolverModel(boolean requiered) {
		this.required = requiered;
		if(requiered)
			itemsToImportNo = imports.size();
		else
			for(Object o : imports) {
				boolean b = (Boolean) o;
				if(b)
					itemsToImportNo++;
			}
		currentItemNo = -1; //we need to call advance() at least before any actual resolving activity
	}
	
	/**
	 * Initializes data to be imported 
	 */
	public void initImports() {
		imports.clear();
		for(Object b: founds)
			imports.add(b);
		currentItemNo = -1;
	}
	
	/**
	 * Set the next element in the list as the object to be imported at the next step.
	 * 
	 * @return		is the next element needs to be resolved
	 */
	public boolean advance() {
/*		Logger log = Logger.getLogger(getClass());
		log.debug("imports size: "+imports.size());
		log.debug("starting currentItemNo: "+currentItemNo);
		log.debug("required: "+required);*/
		while(++currentItemNo<imports.size()) {
/*			log.debug("currentItemNo: "+currentItemNo +
					"\nimports.get(currentItemNo): " + imports.get(currentItemNo) +
					"\nfounds.get(currentItemNo): " + founds.get(currentItemNo) +
					"\nimports.get(currentItemNo): " + imports.get(currentItemNo));*/
			if(willImport(currentItemNo))
				break;
		}
		return getNeedResolving();
	}
	
	/**
	 * Clears service info
	 */
	public void clear() {
		imports.clear();
		founds.clear();
	}
	
	/**
	 * Returns true if current element needs resolution 
	 */
	public boolean getNeedResolving() {
		return currentItemNo<imports.size();
	}
	
	/**
	 * Returns true if current element needs to be imported
	 */
	public boolean getNeedImport() {
		return imports.get(currentItemNo);
	}
	
	private boolean willImport(int itemNo) {
		return (imports.get(itemNo) && !founds.get(itemNo)) || (required && !imports.get(itemNo));
	}
	
	/**
	 * Returns the position of current element in the list of elements to be imported 
	 */
	public int getImportingItemNo() {
		int result = 0;
		for(int i=0;i<=currentItemNo;i++)
			if(willImport(i)) result++;
		return result;
	}
	
	/**
	 * Returns the total count of elements selected to be imported
	 */
	public int getImportingItemCount() {
		int result = 0;
		for(int i=0;i<imports.size();i++)
			if(willImport(i)) result++;
		return result;
	}
	
	/**
	 * Returns total number of elements
	 */
	public int getItemsTotal() {
		return imports.size();
	}
	
	/**
	 * Returns the position of current element in the list of elements 
	 */
	public int getCurrentItemNo() {
		return currentItemNo;
	}
	
	/**
	 * Sets the position of current element in the list of elements 
	 */
	public void setCurrentItemNo(int currentItemNo) {
		this.currentItemNo = currentItemNo;
	}

	public BooleanList getImports() { return imports; }

	public List<Boolean> getFounds() { return founds; }

}
