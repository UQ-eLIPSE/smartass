/* This file is part of SmartAss and contains the ModulesItemModel class, 
 * the model (as in Model-View-Controller) class for the SmartAss module.
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
 * The ModulesItemModel class represents the model (as in Model-View-Controller) class for the smartass module.
 * Module is the java class that generates specific content (e.g. parts of question, solution and short answer) for the template.
 *
 */
public class ModulesItemModel extends NamedItemModel {
	/** The module package */
	protected String module_package = "";
	/** Parameters that module can take when it is created */ 
	protected String parameters;
	/** The module description */
	protected String description;
	
	// Gettets and setters for model fields
	/**
	 * The getter for the module description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * The setter for the module description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The getter for the module package
	 */
	public String getModulePackage() {
		return module_package;
	}

	/**
	 * The setter for the module package
	 */
	public void setModulePackage(String module_package) {
		this.module_package = module_package;
	}

	/**
	 * The setter for the description of parameters that module can take when it is created
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * The getter for the description of parameters that module can take when it is created
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * This method returns the string composed from the package name and module name  
	 */
	public String getFullName() {
		if(module_package!=null && module_package.length()>0)
			return module_package + "." + getName();
		return getName();
	}
}
