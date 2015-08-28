/** @(#)HelpPageItem.java
 *
 * This file is part of SpringAss and describes the HelpPageItem class.  
 * This class represents the model (as in Model-View-Control) for the help page
 * 
 * Copyright (C) 2006 Department of Mathematics, The University of Queensland
 * SmartAss is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2, or
 * (at your option) any later version.
 * GNU program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with program;
 * see the file COPYING. If not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package au.edu.uq.smartass.web;

/**
 * The HelpPageItem class represents the model (as in Model-View-Control) for the help page.
 *
 */
public class HelpPageItem {
	String context;
	String text;
	String title;
	
	/**
	 * The getter for the "context" field.
	 */
	public String getContext() {
		return context;
	}

	/**
	 * The setter for the "context" field.
	 */
	public void setContext(String context) {
		this.context = context;
	}
	
	/**
	 * The getter for the "text" field.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * The setter for the "text" field.
	 */
	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
