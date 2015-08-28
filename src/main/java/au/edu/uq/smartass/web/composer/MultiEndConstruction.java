/* This file is part of SmartAss and contains the MultiEndConstruction class that 
 * represents the %%ENDMULTI part from "%%MULTI ... %%CHOICE ... %%ENDMULTI" construction
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

import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The MultiEndConstruction class represents the %%ENDMULTI part from 
 * "%%MULTI ... %%CHOICE ... %%ENDMULTI" construction in the SmartAss web site 
 * assignment editor.   
 *
 */
public class MultiEndConstruction extends AbstractTemplateConstruction {
	MultiConstruction begin;

	/**
	 * Creates new {@link MultiConstruction}
	 * 
	 * @param begin		{@link MultiConstruction} to which this {@link MultiEndConstruction} is related
	 */
	public MultiEndConstruction(MultiConstruction begin) {
		this.begin = begin;
		setNode(new SimpleNode(0));
	}

	/**
	 * Creates new {@link MultiEndConstruction} from the node from the parsed assignment constructions tree
	 * 
	 */
	public MultiEndConstruction(SimpleNode node) {
		super(node);
	}

	/**
	 * Returns HTML representation of this construction
	 */
	public String getHtml() {
		return "ENDMULTI";
	}

	/**
	 * Returns the kind of the construction as {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "multi-end";
	}
	
	/**
	 * Returns {@link MultiConstruction} to which this {@link MultiEndConstruction} is related   
	 */
	public MultiConstruction getBegin() {
		return begin;
	}

	/**
	 * Actions executed when this construction is removed from it's parent
	 */
	@Override
	public void onRemove() {
		assignment.removeRow(begin);
		super.onRemove();
	}
}
