/* This file is part of SmartAss and contains the DownloadAssignmentController class that 
 * prepares and sends to the user's browser the content of the assignment that is edited in the composer.   
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;
import org.springframework.webflow.context.servlet.ServletExternalContext;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.FlowExecutionKey;
import org.springframework.webflow.execution.repository.BadlyFormattedFlowExecutionKeyException;
import org.springframework.webflow.execution.repository.FlowExecutionRepository;
import org.springframework.webflow.executor.FlowExecutorImpl;

/**
 * The DownloadAssignmentController class  prepares and sends to the user's browser 
 * the content of the assignment that is edited in the composer.
 *
 */
public class DownloadAssignmentController extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationContext appctx = getApplicationContext();
		
		ComposerFlowHandler controller = (ComposerFlowHandler)appctx.getBean("composerFlowHandler");
		FlowExecutionRepository repository = ((FlowExecutorImpl) appctx.getBean("flowExecutor")).getExecutionRepository();
		ExternalContext externalContext = new ServletExternalContext(getServletContext(), request, response);
		ExternalContextHolder.setExternalContext(externalContext);

//		Logger log = Logger.getLogger(getClass());
		String executionKeyStr = request.getParameter("execution");
//		log.debug(executionKeyStr);
		String content_type = getServletContext().getMimeType("1.tex");
		if(content_type==null)
			content_type = "text/plain";
		response.setContentType(content_type);
		response.setHeader("Content-Disposition", "attachment; filename=\"assignment.tex\"");
		
		try {
			FlowExecutionKey flowExecutionKey = 
				repository.parseFlowExecutionKey(executionKeyStr);
//		log.debug(flowExecutionKey);
//		log.debug(flowExecutionKey.toString());
			flowExecutionKey.toString();
			FlowExecution flowExecution = repository.getFlowExecution(flowExecutionKey);
			AssignmentConstruct assignment = (AssignmentConstruct) flowExecution.getActiveSession().getScope().get("template");
			InputStream in = new ByteArrayInputStream(assignment.getCode().getBytes("UTF-8"));
			FileCopyUtils.copy(in, response.getOutputStream());
			in.close();
		} catch (BadlyFormattedFlowExecutionKeyException e) {
			response.getOutputStream().print("Error when retrieving assignment content.");
		}
		
		return null;
	}

}
