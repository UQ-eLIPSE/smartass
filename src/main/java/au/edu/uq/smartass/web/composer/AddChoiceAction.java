/* This file is part of SmartAss and contains the AddChoiceAction class that is
 * the Spring bean that executes an action "Add %%CHOICE construction to assignment"   
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

import org.springframework.webflow.execution.Action;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * The AddChoiceAction class the Spring bean that executes an action 
 * "Add %%CHOICE construction to assignment"
 */
public class AddChoiceAction implements Action {

	/**
	 * Executes action. This method adds %%CHOICE contriction to the assignment
	 * object
	 * 
	 * @param context	{@link RequestContext} from the Spring framework
	 */
	public Event execute(RequestContext context) throws Exception {
		AssignmentConstruct template =  (AssignmentConstruct) context.getFlowScope().get("template");
		AbstractTemplateConstruction cons = template.getRow(template.getSelectedIndex());
		if(cons!=null && !cons.canParent())
			cons = cons.getParent();
		if(cons!=null && cons instanceof MultiConstruction) {
			template.addRow(new MultiChoiceConstruction((MultiConstruction) cons));
			
			return new Event(this, "ok");
		}
		return new Event(this, "cancel");
	}

}
