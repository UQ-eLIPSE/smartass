/** @(#)HelpPageItem.java
 *
 * This file is the part of SpringAss and describes the RepositoryImportFileModel class  
 * that represents the model (as in Model-View-Control) for the file upload form.
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
 * The RepositoryImportFileModel class represents the model (as in Model-View-Control) for the file upload form.
 *
 */
public class RepositoryImportFileModel {
	private byte[] file;

	/**
	 * The setter for byte array with the file data
	 */
	public void setFile(byte[] file) {
		this.file = file;
	}
	
	/**
	 * The getter for byte array with the file data
	 */
	public byte[] getFile() {
		return file;
	}
}
