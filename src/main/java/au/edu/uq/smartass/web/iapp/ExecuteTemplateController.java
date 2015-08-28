/* This file is part of SmartAss and contains the ExecuteTemplateController class. 
 * The au.edu.uq.smartass.web.iapp package contains server-side
 * backend classes for the interactive assignment editor applet.
 * The ExecuteTemplateController class contains assignment execution related routines.
 *   
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
package au.edu.uq.smartass.web.iapp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import au.edu.uq.smartass.app.RSectionsTextNode;
import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.ASTCall;
import au.edu.uq.smartass.templates.texparser.ASTDocument;
import au.edu.uq.smartass.templates.texparser.ASTSection;
import au.edu.uq.smartass.templates.texparser.RAnyText;
import au.edu.uq.smartass.templates.texparser.RCall;
import au.edu.uq.smartass.templates.texparser.RComplexNode;
import au.edu.uq.smartass.templates.texparser.RSection;
import au.edu.uq.smartass.templates.texparser.ResultNode;
import au.edu.uq.smartass.templates.texparser.SimpleNode;

/**
 * The ExecuteTemplateController class is a part of the server-side
 * backend for the interactive assignment editor applet and contains assignment execution related routines.
 * 
 */
public class ExecuteTemplateController extends TemplateAwareController {

	@Override
	/**
	 * This function is called by Spring framework on HTTP request from the
	 * interactive editor applet. It retrieves assignment code from session parameter,
	 * executes it and return execution result as JSON tree that the applet can understand.  
	 */
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/json");
		String name = request.getParameter("name");
		String node_name = request.getParameter("node");
		StringBuffer json = new StringBuffer();
		json.append("{\n" + 
				"label: 'name',\n" +
				"identifier: 'id',\n" +
				"master: '0',\n" +
				"items: [\n");
		if(name!=null) {
			TexReader tr = getTemplate(name, request.getSession(), false);
			String sRefresh = request.getParameter("refresh");
			boolean refresh = (sRefresh!=null && !sRefresh.equals(""));
			
			if(refresh || !tr.isExecuted()) 
				tr.execute();
			else if(node_name!=null && node_name.length()>0) {
				if(node_name.equals("0"))
					tr.execute();
				else {
					String[] path = node_name.split("_");
					ResultNode node = tr.getResultNode();
					for(int i=1;i<path.length;i++) { 
						node = ((RComplexNode) node).getChild(Integer.parseInt(path[i]));
					}
					node.replaceSelf(node.getMasterCopy().execute(new HashSet<String>(tr.getSectionNames()), tr.getScript()));	
				}
			}

			Vector<String> scs = new Vector<String>();
			String sections = "\"QUESTION\", \"SOLUTION\",\"SHORTANSWER\"";
			scs.add("QUESTION");
			scs.add("SOLUTION");
			scs.add("SHORTANSWER");
			for(int i=0;i<tr.getSectionNames().size();i++) {
				if(scs.indexOf(tr.getSectionNames().get(i))==-1) {
					sections = sections + ",\"" + tr.getSectionNames().get(i) + "\"";
					scs.add(tr.getSectionNames().get(i));
				}
			}

			resultToTree((ResultNode) tr.getResultNode(), json, "0", scs);
			json.append("],\nsections: [");
			json.append(sections);
		}
		json.append("]\n}"); 
		response.getOutputStream().print(json.toString());
		return null;
	}
	
	/**
	 * Composes an unique id for master (e.g. code that was a source for execution result) node.
	 *  
	 * @param masterNode 		the node from the parsed code tree
	 * @return					the id for the given node
	 */
	private String composeMasterId(SimpleNode masterNode) {
		if(masterNode==null)
			return "0";
		SimpleNode parent = (SimpleNode) masterNode.jjtGetParent(); 
		if(parent!=null) {
			for(int i=0;i<parent.jjtGetNumChildren();i++) {
				if(parent.jjtGetChild(i)==masterNode) 
					return composeMasterId(parent)+ "_" + i;
			}
		} 
		return "0";
	}
	
	/**
	 * Extracts node text for each assignment section of the given {@link ResultNode} and put it into JSON 
	 * object that will be sent to the applet. Calls resultToTree to extract child nodes data if
	 * the node is an complex node.  
	 * 
	 * @param node				one of {@link ResultNode} descendants
	 * @param json				{@link StringBuffer} that is container for JSON code
	 * @param nodeId			an unique id for the {@link ResultNode}
	 * @param sectionNames		the list of sections (questions, solutions etc)
	 */
	private void nodeToJson(ResultNode node, StringBuffer json, String nodeId, Vector<String> sectionNames) {
		json.append("{ name: '");

		if(node instanceof RSection)
			json.append("Section " + ((ASTSection)node.getMasterCopy()).getName());
		else if(node instanceof RAnyText)
			json.append("Text");
		else if(node instanceof RCall)
			json.append("Call " + ((ASTCall)node.getMasterCopy()).getFilename());
		else if(node.getMasterCopy() instanceof ASTDocument)
			json.append("Document body");
		else 
			json.append(node.toString());
		json.append("', id: '");
		json.append(nodeId);
		json.append("', type:'category'");
		
		if(node instanceof RSection || 
				node instanceof RAnyText ||
				node instanceof RCall ||
				node instanceof RSectionsTextNode) {
			json.append(",\n code: [\n");
			for(int i=0; i<sectionNames.size();i++) {
				if(i==0)
					json.append("\"");
				else
					json.append(",\n\"");
				json.append(node.getSection(sectionNames.get(i))
						.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace("\r", ""));
				json.append("\"");
			}
/*					if(sectionNames.size()>0)
					json.append(",\n");
				json.append("\"").append(node.getMasterCopy().getCode()
						.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace("\r", ""))
						.append("\"");*/
/*					log.debug("node:"+node);
				log.debug("master:"+node.getMasterCopy());
				log.debug("master parent:"+node.getMasterCopy().jjtGetParent());*/
		} else if(node instanceof RComplexNode) {
			if(node.getMasterCopy()!=null && node.getMasterCopy() instanceof ASTDocument) {
				json.append(",\n code: [\n");
				for(int i=0; i<sectionNames.size();i++) {
					if(i==0)
						json.append("\"");
					else
						json.append(",\n\"");
					json.append("\\\\begin{document}\"");
				}
				json.append("],\n codetail: [\n");
				for(int i=0; i<sectionNames.size();i++) {
					if(i==0)
						json.append("\"");
					else
						json.append(",\n\"");
					json.append("\\\\end{document}\"");
				}
				json.append("]\n");
			}
			
			json.append(",\n items: [\n");
			resultToTree((RComplexNode) node, json, nodeId, sectionNames);
/*					json.append("master: \"").append(composeMasterId(node.getMasterCopy())).append("\"\n");
				json.append("]\n");*/
		} /*else {
			json.append(",\n code: [\n");
			for(int i=0; i<sectionNames.size();i++) {
				if(i==0)
					json.append("\"");
				else
					json.append(",\n\"");
				json.append(node.getSection(sectionNames.get(i))
						.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\""));
				json.append("\"");
			}
			json.append("]\n");
		}*/
		json.append("],\n");
		json.append("master: \"").append(composeMasterId(node.getMasterCopy())).append("\"\n}");
	}
	
	/**
	 * Extracts node text for child nodes of the given {@link ResultNode} and put it into JSON 
	 * object that will be sent to the applet. 
	 * 
	 * @param node				one of {@link ResultNode} descendants
	 * @param json				{@link StringBuffer} that is container for JSON code
	 * @param parentId			an unique id for the {@link ResultNode}
	 * @param sectionNames		the list of sections (questions, solutions etc)
	 */
	private void resultToTree(ResultNode node, StringBuffer json, String parentId, Vector<String> sectionNames) {
		int n = 0; 
		//Logger log = Logger.getLogger(getClass());
		if(node instanceof RComplexNode) {
		
			for(Iterator<ResultNode> it = ((RComplexNode)node).iterateResultNodes();it.hasNext();) {
				ResultNode child = it.next(); 
				if(child!=null) {
					nodeToJson(child, json, parentId+"_"+n, sectionNames);
					
//				{
//					json.append("{ name: '");
//	
//					if(child instanceof RSection)
//						json.append("Section " + ((ASTSection)child.getMasterCopy()).getName());
//					else if(child instanceof RAnyText)
//						json.append("Text");
//					else if(child instanceof RCall)
//						json.append("Call " + ((ASTCall)child.getMasterCopy()).getFilename());
//					else if(child.getMasterCopy() instanceof ASTDocument)
//						json.append("Document body");
//					else 
//						json.append(child.toString());
//					json.append("', id: '");
//					json.append(parentId + "_" + n);
//					json.append("', type:'category'");
//					
//					if(child instanceof RSection || 
//							child instanceof RAnyText ||
//							child instanceof RCall ||
//							child instanceof RSectionsTextNode) {
//						json.append(",\n code: [\n");
//						for(int i=0; i<sectionNames.size();i++) {
//							if(i==0)
//								json.append("\"");
//							else
//								json.append(",\n\"");
//							json.append(child.getSection(sectionNames.get(i))
//									.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace("\r", ""));
//							json.append("\"");
//						}
//	/*					if(sectionNames.size()>0)
//							json.append(",\n");
//						json.append("\"").append(child.getMasterCopy().getCode()
//								.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\"").replace("\r", ""))
//								.append("\"");*/
//	/*					log.debug("child:"+child);
//						log.debug("master:"+child.getMasterCopy());
//						log.debug("master parent:"+child.getMasterCopy().jjtGetParent());*/
//					} else if(child instanceof RComplexNode) {
//						json.append(",\n items: [\n");
//						resultToTree((RComplexNode) child, json, parentId+"_"+n, sectionNames);
//	/*					json.append("master: \"").append(composeMasterId(child.getMasterCopy())).append("\"\n");
//						json.append("]\n");*/
//					} /*else {
//						json.append(",\n code: [\n");
//						for(int i=0; i<sectionNames.size();i++) {
//							if(i==0)
//								json.append("\"");
//							else
//								json.append(",\n\"");
//							json.append(child.getSection(sectionNames.get(i))
//									.replace("\\", "\\\\").replace("\n", "\\n").replace("\"", "\\\""));
//							json.append("\"");
//						}
//						json.append("]\n");
//					}*/
//					json.append("],\n");
//					json.append("master: \"").append(composeMasterId(child.getMasterCopy())).append("\"\n");
//	
//					if(n<((RComplexNode)node).getChildrenCount()-1)
//						json.append("},\n");
//					else
//						json.append("}\n");

					if(n<((RComplexNode)node).getChildrenCount()-1)
						json.append(",\n");
					else
						json.append("\n");
				}
				n++;
			} 
		} else
			nodeToJson(node, json, parentId, sectionNames);
	}
}
