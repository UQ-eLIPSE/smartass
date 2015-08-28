/* This file is part of SmartAss and contains the UserItemModel class that is 
 * the model (as in Model-View-Controller) class for the user of the SmartAss web site.
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
import java.util.Date;

/**
 * The UserItemModel class is the model (as in Model-View-Controller) class 
 * for the user of the SmartAss web site.
 */
public class UserItemModel extends IntIdItemModel implements Serializable {
	//possible user rights
	private final static String ANONYMOUS_NAME = "ANONYMOUS";
	public final static int RIGHT_EDITASSIGNMENT = 1;
	public final static int RIGHT_EDITREPOSITORY = 2;
	public final static int RIGHT_ADMIN = 4;
	
	String name = "";
	String password = ""; //MD5 encoded!
	String fullname = "";
	String description = "";
	String place = "";
	String email = "";
	Date date_registered;
	int assignments_num;
	int rights = 1;
	int rowsPerPage = 20;
	
	public UserItemModel() {}
	
	/**
	 * Creates an empty user that is treated by SmartAss web site engine as an anonymous user with 
	 * the minimal set of the rights.
	 */
	public static UserItemModel getAnonymousUser() {
		UserItemModel anonymous = new UserItemModel();
		//anonymous.setName(ANONYMOUS_NAME);
		return anonymous;
	}
	
	/**
	 * The getter for the user's e-mail field
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * The setter for the user's e-mail field
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * The getter for the user's full name (his human name, not the name that is used for authentication 
	 * at his log in to the SmartAss site).
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * The setter for the user's full name (his human name, not the name that is used for authentication 
	 * at his log in to the SmartAss site).
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * The getter for the user's name (the name that is used for authentication 
	 * at the user log in to the SmartAss site, not his human name).
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter for the user's name (the name that is used for authentication 
	 * at the user log in to the SmartAss site, not his human name).
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The getter for the user's password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * The setter for the user's password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * The getter for the number of assignments created by this user.
	 */
	public int getAssignments_num() {
		return assignments_num;
	}

	/**
	 * The setter for the number of assignments created by this user.
	 */
	public void setAssignments_num(int assignments_num) {
		this.assignments_num = assignments_num;
	}

	/**
	 * The getter for the date when this user has registered.
	 */
	public Date getDate_registered() {
		return date_registered;
	}

	/**
	 * The setter for the date when this user has registered.
	 */
	public void setDate_registered(Date date_registered) {
		this.date_registered = date_registered;
	}

	/**
	 * The getter for the user's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The setter for the user's description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The getter for the place the user belongs to. This can be an university or country or some other
	 * thing the user wants to tell about him.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * The setter for the place the user belongs to. This can be an university or country or some other
	 * thing the user wants to tell about him.
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * The getter for the user's rights. The "rights" is the field of int type each bit of which
	 * represents some right. At this moment only lowest 3 bits is significant: 1 is the right to edit an assignment,
  	 * 2 is the right to edit the repository and 4 is the right to the administrative actions - to edit user, 
  	 * to backup/restore the repository etc.
	 */
	public int getRights() {
		return rights;
	}

	/**
	 * The setter for the user's rights. The "rights" is the field of int type each bit of which
	 * represents some right. At this moment only lowest 3 bits is significant: 1 is the right to edit an assignment,
  	 * 2 is the right to edit the repository and 4 is the right to the administrative actions - to edit user, 
  	 * to backup/restore the repository etc.
	 */
	public void setRights(int rights) {
		this.rights = rights;
	}
	
	/**
	 * The getter for single "edit an assignment" right. Returns the right as the boolean field. 
	 */
	public boolean getEditAssignmentsRight() {
		return (rights & RIGHT_EDITASSIGNMENT) != 0;
	}
	
	/**
	 * The setter for single "edit an assignment" right. Sets the right as the boolean field. 
	 */
	public void setEditAssignmentsRight(boolean value) {
		if(value)
			rights |= RIGHT_EDITASSIGNMENT;
		else
			rights &= ~RIGHT_EDITASSIGNMENT;
	}
	
	/**
	 * The getter for single "edit the repository" right. Returns the right as the boolean field. 
	 */
	public boolean getEditRepositoryRight() {
		return (rights & RIGHT_EDITREPOSITORY) != 0;
	}
	
	/**
	 * The setter for single "edit the repository" right. Sets the right as the boolean field. 
	 */
	public void setEditRepositoryRight(boolean value) {
		if(value)
			rights |=  RIGHT_EDITREPOSITORY;
		else
			rights &= ~RIGHT_EDITREPOSITORY;
	}
	
	/**
	 * The getter for single "administrative actions" right. Returns the right as the boolean field. 
	 */
	public boolean getAdminRight() {
		return (rights & RIGHT_ADMIN) != 0;
	}
	
	/**
	 * The setter for single "administrative actions" right. Sets the right as the boolean field. 
	 */
	public void setAdminRight(boolean value) {
		if(value)
			rights |= RIGHT_ADMIN;
		else
			rights &= ~RIGHT_ADMIN;
	}
	
	/**
	 * The getter for rows per page property. All lists of items at the SmartAss web site will be
	 * split to set of pages with given rows number at the page.   
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
	/**
	 * The setter for rows per page property. All lists of items at the SmartAss web site will be
	 * split to set of pages with given rows number at the page.   
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}
}
