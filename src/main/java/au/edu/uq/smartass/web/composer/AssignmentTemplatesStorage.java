/* This file is part of SmartAss and contains the AssignmentTemplatesStrorage class that 
 * represents the storage of assignment templates (e.g. the file that used as the template for newly 
 * created assignment) 
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
package au.edu.uq.smartass.web.composer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * The AssignmentTemplatesStrorage class represents the storage of assignment templates 
 * (e.g. the file that used as the template for newly created assignment) 
 */
public class AssignmentTemplatesStorage {
	private String path;
	
	/**
	 * Returns the {@link InputStream} for the file with the given name
	 */
	public InputStream getFile(String name) {
		try {
			return new FileInputStream(new File(new File(path),  name));
		} catch (FileNotFoundException e) {}
		return null;
	}
	
	/**
	 * The getter for the path to the directory with the assignment templates
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * The setter for the path to the directory with the assignment templates
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
