/* This file is part of SmartAss and contains the TemplateFlowHandler class that
 * hooks to some important points of Spring webflow executions 
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
package au.edu.uq.smartass.web.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.webflow.execution.FlowExecutionOutcome;
import org.springframework.webflow.mvc.servlet.AbstractFlowHandler;

/**
 * The TemplateEditFlowHandler class 
 * hooks to some important points of Spring webflow executions
 */
public class TemplateFlowHandler extends AbstractFlowHandler {
	@Override
	/**
	 * Returns the custom flow id
	 */
	public String getFlowId() {
		return "template";
	}

	@Override
	/**
	 * Redirects to the repository page on flow finish 
	 */
	public String handleExecutionOutcome(FlowExecutionOutcome outcome,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer url = request.getRequestURL();
		url.delete(url.lastIndexOf("/"), url.length());
		if(outcome.getId().equals("finish") || outcome.getId().equals("finishWithCancel"))
			url.append("/repository.htm?classid=" + outcome.getOutput().get("classId"));
		else
			url.append("/repository-template-edit.htm?id=" + outcome.getOutput().get("templateId"));
		return url.toString();

	}
}
