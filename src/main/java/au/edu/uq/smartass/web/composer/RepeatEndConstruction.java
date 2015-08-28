/* This file is part of SmartAss and contains the RepeatEndConstruction class that 
 * represents the %%ENDREPEAT part from the "%%REPEAT ... %%ENDREPEAT" construction
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

import au.edu.uq.smartass.templates.texparser.ASTAnyText;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The RepeatEndConstruction class represents the %%ENDREPEAT part from the "%%REPEAT ... %%ENDREPEAT" 
 * construction in the SmartAss web site assignment editor.
 */
public class RepeatEndConstruction extends AbstractTemplateConstruction {
	RepeatConstruction begin;

	/**
	 * Creates new {@link RepeatEndConstruction} 
	 * 
	 * @param begin		{@link RepeatConstruction} to which this {@link RepeatEndConstruction} is related
	 */
	public RepeatEndConstruction(RepeatConstruction begin) {
		this.begin = begin;
		setNode(new SimpleNode(0));
	}

	
	/**
	 * Returns HTML representation of this construction
	 */
	public String getHtml() {
		return "ENDREPEAT";
	}

	/**
	 * Returns the kind of the construction as {@link String}.
	 * This value is used in .jsp to make some construction-specific actions
	 */  
	public String getKind() {
		return "repeat-end";
	}

	/**
	 * Returns the {@link RepeatConstruction} to which this {@link RepeatEndConstruction} is related
	 */
	public RepeatConstruction getBegin() {
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
