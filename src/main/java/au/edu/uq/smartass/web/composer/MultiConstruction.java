/* This file is part of SmartAss and contains the MultiConstruction class that 
 * represents the %%MULTI part from "%%MULTI ... %%CHOICE ... %%ENDMULTI" construction
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

import java.util.Vector;

import au.edu.uq.smartass.templates.texparser.ASTMulti;

/**
 * The MultiConstruction class represents the %%MULTI part from 
 * "%%MULTI ... %%CHOICE ... %%ENDMULTI" construction in the SmartAss web site 
 * assignment editor.   
 *
 */
public class MultiConstruction extends AbstractTemplateConstruction {
	MultiEndConstruction end;
	Vector<MultiChoiceConstruction> choices = new Vector<MultiChoiceConstruction>();;

	/**
	 * Creates new {@link MultiConstruction}
	 */
	public MultiConstruction() {
		node = new ASTMulti(0);
		end = new MultiEndConstruction(this);
	}

	/**
	 * Creates new {@link MultiConstruction} from the node from the parsed assignment constructions tree
	 * 
	 */
	public MultiConstruction(ASTMulti node) {
		super(node);
		end = new MultiEndConstruction(this);
	}

	/**
	 * Returns HTML representation of this construction
	 */
	public String getHtml() {
		return "MULTI " + ((ASTMulti)node).getChoicesCount();
	}

	/**
	 * Returns the kind of the construction as {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "multi";
	}

	/**
	 * Can this construction be a parent for other constructions 
	 */
	@Override
	public boolean canParent() {
		return true;
	}
	
	/**
	 * Adds the child to this {@link MultiConstruction}
	 */
	@Override
	public void addChild(AbstractTemplateConstruction child) {
		if (child instanceof MultiChoiceConstruction) 
			choices.add((MultiChoiceConstruction) child);
		else
			super.addChild(child);
	}
	
	/**
	 * Actions executed when construction is removed from it's parent
	 */
	@Override
	public void onRemove() {
		for(MultiChoiceConstruction o : choices)
			assignment.removeRow(o);
		assignment.removeRow(end);
		super.onRemove();
	}
	
	/**
	 * Sets parent of this construction
	 */
	@Override
	public void setParent(AbstractTemplateConstruction parent) {
		super.setParent(parent);
		parent.addChild(end);
	}

	@Override
	public void setAssignment(AssignmentConstruct assignment) {
		super.setAssignment(assignment);
		if(assignment!=null) 
			assignment.addRow(end, parent, assignment.getSelectedIndex()+1);
		end.setParent(parent);
	}
	
	/**
	 * The getter for the count of {@link MultiChoiceConstruction} (e.g. %%CHOICE) those belongs to this 
	 * {@link MultiConstruction}
	 */
	public int getChoicesCount() {
		return ((ASTMulti)node).getChoicesCount();
	}
	
	/**
	 * The getter for the count of {@link MultiChoiceConstruction} (e.g. %%CHOICE) those belongs to this 
	 * {@link MultiConstruction}
	 */
	public void setChoicesCount(int choices_count) {
		((ASTMulti)node).setChoicesCount(choices_count);
	}
}
