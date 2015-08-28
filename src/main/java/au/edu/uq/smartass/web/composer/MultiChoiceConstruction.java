/* This file is part of SmartAss and contains the MultiChoiceConstruction class that 
 * represents the %%CHOICE construction from "%%MULTI ... %%CHOICE ... %%ENDMULTI"
 * in the SmartAss web site assignment editor.   
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

import au.edu.uq.smartass.templates.texparser.ASTMultiChoice;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The MultiChoiceConstruction class 
 * represents the %%CHOICE construction from "%%MULTI ... %%CHOICE ... %%ENDMULTI"
 * in the SmartAss web site assignment editor
 *
 */
public class MultiChoiceConstruction extends AbstractTemplateConstruction {
	MultiConstruction multi;

	/**
	 * Creates new {@link MultiChoiceConstruction} as a child of given {@link MultiConstruction}
	 * 
	 * @param multi		parent {@link MultiConstruction}
	 */
	public MultiChoiceConstruction(MultiConstruction multi) {
		this.multi = multi;
		setParent(multi);
		setNode(new ASTMultiChoice(0));
	}

	/**
	 * Creates new {@link MultiChoiceConstruction} from the node from the parsed assignment constructions tree
	 * 
	 * @param node 
	 */
	public MultiChoiceConstruction(SimpleNode node) {
		super(node);
	}

	/**
	 * Returns HTML representation of this construction
	 */
	public String getHtml() {
		return "CHOICE";
	}

	/**
	 * Returns the kind of the construction as {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "choice";
	}
}
