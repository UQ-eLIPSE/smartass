/* This file is part of SmartAss and contains the UserLoginModel class that is 
 * the model (as in Model-View-Controller) class for the user authentication data 
 * that is entered when the user logs in to the SmartAss web site.
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
 * The UserLoginModel class is the model (as in Model-View-Controller) class for the user authentication data 
 * that is entered when the user logs in to the SmartAss web site.
 *
 */
public class UserLoginModel {
	String name = "";
	String password = ""; //MD5 encoded!
	UserItemModel user;

	/**
	 * The getter for the user name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The setter for the user name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The getter for the user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * The setter for the user password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * The setter for the user data
	 */
	public void setUser(UserItemModel user) {
		this.user = user;
	}
	
	/**
	 * The getter for the user data
	 */
	public UserItemModel getUser() {
		return user;
	}
}
