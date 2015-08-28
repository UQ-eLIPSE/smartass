/* This file is part of SmartAss and contains the SectionEndConstruction class that 
 * represents the %%END part from the "%%SECTION name ... %%END name" construction
 * in the SmartAss web site assignment editor. 
 * This construction is used to divide assignment to a set of files such as questions, solutions, shortanswers 
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

import au.edu.uq.smartass.templates.texparser.ASTSection;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The SectionEndConstruction class  
 * represents the %%END part from the "%%SECTION name ... %%END name" construction
 * in the SmartAss web site assignment editor. 
 * This construction is used to divide assignment to a set of files such as questions, solutions, shortanswers *
 */
public class SectionEndConstruction extends AbstractTemplateConstruction {
	SectionConstruction begin;

	/**
	 * Creates new {@link SectionEndConstruction}
	 * 
	 * @param begin		the {@link SectionConstruction} to which this {@link SectionEndConstruction} is related
	 */
	public SectionEndConstruction(SectionConstruction begin) {
		this.begin = begin;
		setNode(new SimpleNode(0));
	}
	
	/**
	 * Returns the HTML representation of this construction
	 */
	public String getHtml() {
		return "END " + ((ASTSection)begin.getNode()).getName();
	}

	/**
	 * Returns the kind of the construction as the {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "section-end";
	}
	
	/**
	 * Returns he {@link SectionConstruction} to which this {@link SectionEndConstruction} is related
	 */
	public SectionConstruction getBegin() {
		return begin;
	}

	/**
	 * Actions executed when the construction is removed from it's parent
	 */
	@Override
	public void onRemove() {
		assignment.removeRow(begin);
		super.onRemove();
	}
}
