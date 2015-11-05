/* This file is part of SmartAss and contains the TextConstruction class that 
 * represents the block of the unstructured text in the SmartAss web site assignment editor. 
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
 * The TextConstruction class that 
 * represents the block of the unstructured text in the SmartAss web site assignment editor.
 *
 */
public class TextConstruction extends AbstractTemplateConstruction {
	
	public TextConstruction() {	
		setNode(new ASTAnyText(0));
	}
	
	public TextConstruction(ASTAnyText node) {
		super(node);
	}
	
	@Override
	public String toString() {
		return ((ASTAnyText)node).getCode();
	}
	
	/**
	 * Returns the kind of the construction as the {@link String}.
	 * This value is used in .jsp to make some construction-specific actions  
	 */
	public String getKind() { return "text"; }
	
	/** Returns the HTML representation of this construction */
	public String getHtml() { return getText(); }
	
	/**
	 * The getter for the content of this text block 
	 * Prepares the content to be displayed in the editor
	 */
	public String getText() {
		String s = node.getCode();
		if(s.length()>0) {
			int nl_pos =  s.lastIndexOf("\n");
			if(nl_pos>=0)
				return s.substring(0, nl_pos);
		}
		return s;
	}
	
	/** The setter for the content of this text block */
	public void setText(String new_text) { node.setText(new_text+"\n"); }
}
