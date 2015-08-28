/* This file is part of SmartAss and contains the ResolveItemModel class that is
 * the ancestor for all specific metadata objects resolving models. "Resolving" means
 * to connect plaintext metadata from the template importing  
 * to the corresponding object from the smartass database or create a new one if such record 
 * does not exists. 
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

/**
 * The ResolveItemModel class is
 * the ancestor for all specific metadata objects resolving models. "Resolving" means
 * to connect plaintext metadata from the template importing  
 * to the corresponding object from the smartass database or create a new one if such record 
 * does not exists. 
 */
public class ResolveItemModel  implements Serializable {
	public final int CREATE_NEW = 0;
	private int solution = -1;
	private String search = "";

	//multipage list browsing related data 
	protected int pageNum;
	protected int pageNo;
	protected int rowsPerPage=20;
	protected int rowsNum;
	
	
	/**
	 * The setter for the "solution" which can be one of
	 * 		-1 - nothing was selected,
	 * 		0 - add new object with properties from the imported template,
	 * 		some other integer - the id of object from the repository
	 *  
	 * @param solution
	 */
	public void setSolution(int solution) {
		this.solution = solution;
	}
	
	/**
	 * The getter for the "solution" which can be one of
	 * 		-1 - nothing was selected,
	 * 		0 - add new object with properties from the imported template,
	 * 		some other integer - the id of object from the repository
	 */
	public int getSolution() {
		return solution;
	}
	
	/**
	 * The setter for the string that will be used to search objects in the repository
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	
	/**
	 * The getter of the string that is used to search objects in the repository
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * The getter for the page count property - the number of pages that SmartAss website 
	 * interface shows to user
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * The getter for the page count property - the number of pages that SmartAss website 
	 * interface shows to user
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * The getter for the number of the current page property e.g. the page that SmartAss website 
	 * currently displays to the user
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * The setter for the number of the current page property e.g. the page that SmartAss website 
	 * currently displays to the user
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * The getter for the number of rows per page
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * The setter for the number of rows per page
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * The getter for the total number of rows found
	 */
	public int getRowsNum() {
		return rowsNum;
	}

	/**
	 * The setter for the total number of rows found
	 */
	public void setRowsNum(int rowsNum) {
		this.rowsNum = rowsNum;
		pageNum = (rowsNum-1) / rowsPerPage;
	}
}
