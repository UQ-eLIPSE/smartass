/* This file is part of SmartAss and contains the RepeatConstruction class that 
 * represents the %%REPEAT part from the "%%REPEAT ... %%ENDREPEAT" construction
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


import au.edu.uq.smartass.templates.texparser.ASTRepeat;

/**
 * The RepeatConstruction class represents the %%REPEAT part 
 * from the "%%REPEAT ... %%ENDREPEAT" construction
 * in the SmartAss web site assignment editor. *
 */
public class RepeatConstruction extends AbstractTemplateConstruction {
	RepeatEndConstruction end;

	/**
	 * Creates new {@link RepeatConstruction}
	 */
	public RepeatConstruction() {
		setNode(new ASTRepeat(0));
		end = new RepeatEndConstruction(this);
	}

	/**
	 * Creates new {@link RepeatConstruction} from the node from the parsed assignment constructions tree
	 */
	public RepeatConstruction(ASTRepeat node) {
		setNode(node);
		end = new RepeatEndConstruction(this);
	}

	/**
	 * Returns the HTML representation of this construction
	 */
	public String getHtml() {
		return "REPEAT " + ((ASTRepeat)node).getRepeatsNum();
	}

	/**
	 * Returns the kind of the construction as the {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "repeat";
	}

	@Override
	/**
	 * Can this construction be a parent for other constructions? 
	 */
	public boolean canParent() {
		return true;
	}
	
	@Override
	/**
	 * Sets parent of this construction
	 */
	public void setParent(AbstractTemplateConstruction parent) {
		super.setParent(parent);
		parent.addChild(end);
	}
		
	@Override
	/**
	 * Actions executed when construction is removed from it's parent
	 */
	public void onRemove() {
		assignment.removeRow(end);
		super.onRemove();
	}
		
	/**
	 * The setter for the number of {@link RepeatConstruction} loops 
	 */
	public void setRepeatsNum(int repeats) {
		((ASTRepeat)node).setRepeatsNum(repeats);
	}
	
	/**
	 * The getter for the number of {@link RepeatConstruction} loops 
	 */
	public int getRepeatsNum() {
		return ((ASTRepeat)node).getRepeatsNum();
	}
	
	@Override
	public void setAssignment(AssignmentConstruct assignment) {
		super.setAssignment(assignment);
		if(assignment!=null) 
			assignment.addRow(end, parent, assignment.getSelectedIndex() + 1);
	}
	
}
