/* This file is part of SmartAss and contains the SectionConstruction class that 
 * represents the %%SECTION part from the "%%SECTION name ... %%END name" construction
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The SectionConstruction class
 * represents the %%SECTION part from the "%%SECTION name ... %%END name" construction
 * in the SmartAss web site assignment editor. 
 * This construction is used to divide assignment to a set of files such as questions, solutions, answers.
 *
 */
public class SectionConstruction extends AbstractTemplateConstruction {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( SectionConstruction.class );


        /** */
	private SectionEndConstruction end;
        {
                end = new SectionEndConstruction(this);
        }

        /**
         * Set the 'Assignment Editor' visibity for this component.
         * The super class method is overridden because this component has a corresponding closing element.
         *
         * @param visible A flag indicating if the component should be shown in the 'Assignment Editor'.
         */
        @Override public void setVisible(boolean visible) {
                super.setVisible(visible);
                end.setVisible(visible);
        }

	/**
	 * Creates new {@link SectionConstruction}
	 */
	public SectionConstruction() {
		setNode(new ASTSection(0));
	}
	
	/**
	 * Creates new {@link SectionConstruction} from the node from the parsed assignment constructions tree
	 */
	public SectionConstruction(ASTSection node) {
		setNode(node);
	}
	
	/**
	 * Returns the HTML representation of this construction
	 */
	public String getHtml() {
		return "SECTION " + ((ASTSection)node).getName();
	}

	/**
	 * Returns the kind of the construction as the {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() {
		return "section";
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
		
	/**
	 * Actions executed when the construction is removed from it's parent
	 */
	@Override
	public void onRemove() {
		assignment.removeRow(end);
		super.onRemove();
	}

	/**
	 * The setter for the section name
	 */
	public void setSectionName(String name) {
		((ASTSection)node).setName(name);
	}
	
	/**
	 * The getter for the section name
	 */
	public String getSectionName() {
		return ((ASTSection)node).getName();
	}

	@Override
	/**
	 * The setter for the assignment that is edited
	 */
	public void setAssignment(AssignmentConstruct assignment) {
                LOG.info("::setAssignment()[ assignment=>{} ]");
		super.setAssignment(assignment);
		if(assignment!=null) 
                        LOG.info("::setAssignment()[ end_component=>{} ]", end.isVisible() ? "VISIBLE" : "HIDDEN" );
			assignment.addRow(end, parent, assignment.getSelectedIndex() + 1);
	}
}
