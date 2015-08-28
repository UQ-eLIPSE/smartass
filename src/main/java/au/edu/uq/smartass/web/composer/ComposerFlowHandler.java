/* This file is part of SmartAss and contains the ComposerFlowHandler class - The Spring flow handler for
 * the composer flow 
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

/**
 * The ComposerFlowHandler class is the Spring flow handler for the composer flow
 * (see Spring Webflow manual for details about the flow management) 
 *
 */
public class ComposerFlowHandler extends AbstractFlowHandler {
	
	@Override
	public String handleException(FlowException e, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getFlowId() {
		return "composer";
	}
	
	@Override
	/**
	 * Redirects to the SmartAss homepage after flow is finished
	 */
	public String handleExecutionOutcome(FlowExecutionOutcome outcome,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer url = request.getRequestURL();
		url.delete(url.lastIndexOf("/"), url.length());
		url.append("/index.htm");
		return url.toString();
	}
}
