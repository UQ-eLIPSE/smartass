/* This file is part of SmartAss and contains the CallConstruction class that is
 *  the assignment construction that represents %%CALL construction from template language   
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

import au.edu.uq.smartass.templates.texparser.ASTCall;

/**
 * The CallConstruction class is the assignment construction that represents 
 * %%CALL construction from template language
 *
 */
public class CallConstruction extends AbstractTemplateConstruction {
	/** %%CALL node from template tree classes */
	ASTCall call;

	/**
	 * Creates new {@link CallConstruction} with given template name (which one may contain path to template in name)
	 * 
	 * @param templateName		the name of template that %%CALL refers to
	 */
	public CallConstruction(String templateName) {
		call = new ASTCall(0);
		call.setFilename(templateName);
		setNode(call);
		
	}
	
	/**
	 * Creates new {@link CallConstruction} from existing {@link ASTCall} node 
	 * 
	 * @param node	
	 */
	public CallConstruction(ASTCall node) {
		call = node;
		setNode(node);
	}

	/**
	 * Gets HTML representation of this assignment construction
	 */
	public String getHtml() {
		return "CALL " + call.getFilename();
	}

	/**
	 * Returns the kind of this assignment construction 
	 */
	public String getKind() {
		return "call";
	}

}
