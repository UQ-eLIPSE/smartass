/* This file is part of SmartAss and contains the ResolveClassificationController class that is
 * used to resolve the classification from the metadata found in the template. 
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

import org.springframework.binding.message.MessageContext;
import org.springframework.webflow.execution.RequestContext;

/**
 * The ResolveClassificationController class is
 * used to resolve the classification from the metadata found in the template.
 */
public class ResolveClassificationController extends AbstractResolveController {
	public void setParent(ResolveClassificationModel item, RequestContext flowRequestContext) {
		item.setParentId(Integer.parseInt(flowRequestContext.getRequestParameters().get("parentid")));
	}
	
	public boolean validate(ResolveClassificationModel r, MessageContext mcontext) {
		return super.validate(null, r, mcontext);
	}
}
