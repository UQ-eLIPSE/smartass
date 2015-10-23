/* This file is part of SmartAss and contains the AbstractTemplateConstruction class 
 * that is the base class for all assignment constructions
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

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import au.edu.uq.smartass.templates.texparser.SimpleNode;
/**
 * The AbstractTemplateConstruction class is base class for all assignment constructions.
 * Assignment construction is a one of template language control statements, for example
 * "%%REPEAT 5 ... %%ENDREPEAT" or "%%CALL SomeTemplate.tex". See SmartAss user guide for 
 * full list of template language constructions.
 * Descendants of this class are used for interactive composition of assignment through 
 * SmartAss web site.
 */
public abstract class AbstractTemplateConstruction implements AssignmentConstructRow, Serializable {

	/** The template node this construction represents */
	SimpleNode node;
	/** Parent construction */
	AbstractTemplateConstruction parent;
	/** Child constructions */
	Vector<AbstractTemplateConstruction> children = new Vector<AbstractTemplateConstruction>();
	List<AssignmentWizardItem> addons;
	
	/**  */
	int level;
	/** The link to assignment edited */
	AssignmentConstruct assignment;
	
	public AbstractTemplateConstruction() {}
	
	public AbstractTemplateConstruction(SimpleNode node) {
		setNode(node);
	}
	

	/**
	 * The getter for the node this construction represents 
	 */
	public SimpleNode getNode() {
		return node;
	}
	
	/**
	 * The setter for the node this construction represents 
	 */
	protected void setNode(SimpleNode node) {
		this.node = node;
	}
	
	/**
	 * Returns true if this construction can be a parent for other constructions 
	 */
	public boolean canParent() {
		return false; 
	}
	
	/**
	 * The getter for the parent construction 
	 */
	public AbstractTemplateConstruction getParent() {
		return parent;
	}
	
	/**
	 * The setter for the parent construction	 
	 */
	public void setParent(AbstractTemplateConstruction parent) {
		this.parent = parent;
		if(parent!=null) {
			level = parent.getLevel()+1;
		} else 
			level = 0;
	}
	
	/**
	 * Adds a child construction
	 */
	public void addChild(AbstractTemplateConstruction child) {
		if(!children.contains(child)) {
			if(child.getParent()!=null)
				child.getParent().removeChild(child);
			children.add(child);
			child.setParent(this);
		}
	}
	
	/**
	 * Removes given child construction from children collection
	 */
	public void removeChild(AbstractTemplateConstruction child) {
		children.remove(child);
		if(node!=null && child.getNode()!=null)
			node.removeNode(child.getNode());
	}
	
	/**
	 * This function fires when construction to be deleted from its parent children collection
	 */
	public void onRemove() {
		if(node!=null)
			node.removeSelf();
		for(AbstractTemplateConstruction o : children)
			assignment.removeRow(o);
		setAssignment(null);
	}
	
	/** The getter for construction level */
	public int getLevel() { return level; }
	
	/**
	 * The getter for the list of add-ons. 
	 * An add-on is some additional information (header, footer, etc)
	 * that does not belongs to the construction itself but will be inserted into the assignment
	 * usually as the {@link TextConstruction} item.
	 */
	public List<AssignmentWizardItem> getAddons() {
		return addons;
	}
	
	/**
	 * The setter for the list of add-ons. 
	 * An add-on is some additional information (header, footer, etc)
	 * that does not belongs to the construction itself but will be inserted into the assignment
	 * usually as the {@link TextConstruction} item.
	 */
	public void setAddons(List<AssignmentWizardItem> addons) {
		this.addons = addons;
	}
	
	/**
	 * The getter for assignment that is edited
	 */
	public AssignmentConstruct getAssignment() {
		return assignment;
	}
	
	/**
	 * The setter for assignment that is edited
	 */
	public void setAssignment(AssignmentConstruct assignment) {
		this.assignment = assignment;
	}
	
	/**
	 * Returns HTML representation of this construction 
	 */
	@Override
	public String toString() {
		return getHtml();
	}
	
}

