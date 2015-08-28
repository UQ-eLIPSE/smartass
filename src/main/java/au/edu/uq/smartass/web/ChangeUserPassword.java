/* @(#)ChangeUserPassword.java
 *
 * This file is part of SpringAss and describes ChangeUserPassword class.  
 * This class represents model (as in Model-View-Controller) for change user password form data. 
 * 
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package au.edu.uq.smartass.web;

/**
 * The ChangeUserPassword class represents model (as in Model-View-Controller) 
 * for change user password form data.
 *
 */
public class ChangeUserPassword {
	String oldPassword;
	String newPassword1;
	String newPassword2;
	
	/**
	 * The getter for old password (entered to confirm rights to change password for the user)
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	
	/**
	 * The setter for old password (entered to confirm rights to change password for the user)
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	/**
	 * The getter for first instance of new password 
	 */
	public String getNewPassword1() {
		return newPassword1;
	}
	
	/**
	 * The setter for first instance of new password 
	 */
	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}
	
	/**
	 * The getter for second instance of new password (use it to check possible misspelling in the new password) 
	 */
	public String getNewPassword2() {
		return newPassword2;
	}
	
	/**
	 * The setter for second instance of new password (use it to check possible misspelling in the new password) 
	 */
	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}
}
