/* This file is part of SmartAss and contains the AbstractResolveController class that is
 * an ancestor for all controllers that resolve some specific object from its metadata from the 
 * template.  
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

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import au.edu.uq.smartass.web.TemplatesItemModel;

/**
 * The AbstractResolveController class is
 * an ancestor for all controllers that resolve some specific object from its metadata from the 
 * template.
 * 
 */
public class AbstractResolveController {
	
	protected boolean validate(TemplatesItemModel template, ResolveItemModel rm, MessageContext  mcontext) {
		if(rm.getSolution()<0) {
			mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
            		"You have to select one of proposed solution variants!").build());
			return false;
		}
		return true;
	}

}
