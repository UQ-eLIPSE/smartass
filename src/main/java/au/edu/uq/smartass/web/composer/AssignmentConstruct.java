/* This file is part of SmartAss and contains the AssignmentConstruct class that is the Spring bean 
 * that represents the main object of assignment composer. 
 * This object stores assignment constructions and provides an assignment editing related functionality.   
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;
import java.util.prefs.Preferences;

import au.edu.uq.smartass.engine.Engine;
import au.edu.uq.smartass.templates.TexReader;
import au.edu.uq.smartass.templates.texparser.ASTAnyText;
import au.edu.uq.smartass.templates.texparser.ASTCall;
import au.edu.uq.smartass.templates.texparser.ASTDocument;
import au.edu.uq.smartass.templates.texparser.ASTMulti;
import au.edu.uq.smartass.templates.texparser.ASTMultiChoice;
import au.edu.uq.smartass.templates.texparser.ASTRepeat;
import au.edu.uq.smartass.templates.texparser.ASTSection;
import au.edu.uq.smartass.templates.texparser.ASTTemplate;
import au.edu.uq.smartass.templates.texparser.ParseException;
import au.edu.uq.smartass.templates.texparser.SimpleNode;
import au.edu.uq.smartass.web.AssignmentsItemModel;

/**
 * The AssignmentConstruct class is the Spring bean that represents 
 * the main object of assignment composer. This object stores assignment constructions and provides
 * an assignment editing related functionality.
 *
 */
public class AssignmentConstruct extends AssignmentsItemModel implements Serializable {
	
	/** Root node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	ASTTemplate template_node;
	/** Document body node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	ASTDocument doc_node;
	/** Document header node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	ASTAnyText header_node;
	/** Assignment constructions collection */
	Vector<AbstractTemplateConstruction> rows = new Vector<AbstractTemplateConstruction>();
	int selectedRowIndex;
	boolean decorateWithLatex;

	/** Default constructor */
	public AssignmentConstruct() {
		init();
	}
	
	private void init() {
		initTemplate();
		//ensure that we have newline after \\begin{document}
		ASTAnyText enter = new ASTAnyText(0);
		enter.setText("\n");
		doc_node.jjtAddChild(enter, 0);
	}
	
	/**
	 * Creates empty assignment and sets its initial and default data
	 *
	 */
	private void initTemplate() {
		template_node = new ASTTemplate(0);
		doc_node = new ASTDocument(1);
		header_node = new ASTAnyText(0);
		Preferences prefs = Preferences.userRoot().node("au/edu/uq/smartass");
		header_node.setText(prefs.get("templateHeader", "\\input{smartass.tex}"));
		template_node.jjtAddChild(header_node, 0);
		template_node.jjtAddChild(doc_node, 1);
		template_node.init();
	}
	
	/**
	 * Removes an assignment construction construct_row from assignment
	 * 
	 * @param construct_row	assignment construction to remove
	 */
	public void removeRow(AbstractTemplateConstruction construct_row) {
		if(rows.indexOf(construct_row)>=0) {
			rows.remove(construct_row);
			construct_row.onRemove();
		}
	}
	
	
	/**
	 * Add new assignment construction to assignment
	 * 
	 * @param construct_row		assignment construction to add
	 * @param parent_row		parent assignment construction (null if we add top-level construction)
	 * @param row_index			index of assignment row after wich new construction will be inserted 
	 */
	public void addRow(AbstractTemplateConstruction construct_row, AbstractTemplateConstruction parent_row, int row_index) {
		if(row_index<rows.size()-1) 
			rows.insertElementAt(construct_row, row_index+1);
		else
			rows.add(construct_row);

		//compose hierarchy of control structures
		if(parent_row!=null)
			if(parent_row.canParent()) {
				parent_row.addChild(construct_row);
				insertNode(row_index, construct_row.getNode(), parent_row.getNode());
			} else
				if(parent_row.getParent()!=null) { 
					parent_row.getParent().addChild(construct_row);
					insertNode(row_index, construct_row.getNode(), parent_row.getParent().getNode());
				} else 
					insertNode(row_index, construct_row.getNode(), doc_node);
		else
			insertNode(-1, construct_row.getNode(), doc_node);
			
		construct_row.setAssignment(this);
	}
	
	/**
	 * Add new assignment construction after current selected row position
	 * 
	 * @param construct_row		assignment construction to add
	 */
	public void addRow(AbstractTemplateConstruction construct_row) {
		boolean is_empty = rows.size()==0; 
		AbstractTemplateConstruction sr = null;
		if(selectedRowIndex<rows.size()) 
			 sr = rows.get(selectedRowIndex);
		
		addRow(construct_row, sr, selectedRowIndex);
		if(!is_empty)
			selectedRowIndex++;
	}
	
	/**
	 * Get an assignment construction at index position 
	 * 
	 */
	public AbstractTemplateConstruction getRow(int index) {
		if(index>=0 && index<rows.size())
			return rows.get(index);
		return null;
	}

	/**
	 * Returns number of rows in the assignment 
	 */
	public int getRowCount() {
		return rows.size();
	}
	
	/**
	 * Returns the assignment constructions list
	 */
	public List<AbstractTemplateConstruction> getRows() {
		return rows;
	}
	
	/**
	 * The setter for the selected index
	 */
	public void setSelectedIndex(int selectedRow) {
		this.selectedRowIndex = selectedRow;
	}
	
	/**
	 * The getter for the selected index
	 */
	public int getSelectedIndex() {
		return selectedRowIndex;
	}
	
	public boolean getDecorateWithLatex() {
		return decorateWithLatex;
	}
	
	public void setDecorateWithLatex(boolean decorateWithLatex) {
		this.decorateWithLatex = decorateWithLatex;
	}
	
	/**
	 * Returns the selected assignment construction
	 */
	public AbstractTemplateConstruction getSelectedRow() {
		if(rows.size()==0)
			return null;
		if(selectedRowIndex<rows.size())
			return rows.get(selectedRowIndex);
		else
			return rows.get(rows.size()-1);
	}
	
	/**
	 * Removes the selected assignment construction
	 */
	public void removeSelectedRow() {
		if(selectedRowIndex>=0 && selectedRowIndex<rows.size()) 
			rows.remove(selectedRowIndex).onRemove();
	}
	
	/**
	 * Gets the template code as {@link String}
	 */
	public String getCode() {
		return template_node.getCode();
	}
	
	public boolean isContentEmpty() {
		boolean can = false;
		//Logger l = Logger.getLogger(getClass());
		//l.debug("rows:" + rows.size());
		if(rows.size()==0)
			return true;
		for(AbstractTemplateConstruction it: rows) {
			//l.debug(it.toString() + "\ncode: " + it.getNode().getCode());
			can = can || ((it instanceof TextConstruction) && it.getNode().getCode().trim().length()!=0)
					|| (it instanceof CallConstruction);
			if(can)
				return false;
		}
		//l.debug("empty! ");
		return true;
	}
	
	/**
	 * Sets and parse template code
	 *  
	 * @param code	code to parse
	 * 
	 * @throws ParseException	
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void setCode(String code) throws ParseException, UnsupportedEncodingException, IOException {
		rows.clear();
		initTemplate();
		selectedRowIndex = -1; //Position to insert new node/construction

		if(code!=null && code.length()>0) {
			Engine engine = new Engine();
			TexReader tr = new TexReader(engine);
			InputStream in = new ByteArrayInputStream(code.getBytes("UTF-8"));
			tr.loadTemplate(in);
			ASTTemplate tmp_node = (ASTTemplate) tr.getRootNode();
			in.close();
			analyseNode(tmp_node.getDocument(), null);
		}
		if(rows.size()==0)
			selectedRowIndex = 0;
		else
			selectedRowIndex = rows.size() - 1; 
	}

	/**
	 * Transforms the template nodes tree created after code parsing 
	 * 
	 * @param node	node to transform
	 * @param parent	parent assignment construction
	 */
	protected void analyseNode(SimpleNode node, AbstractTemplateConstruction parent) {
		//Logger log = Logger.getLogger(getClass());
		for(int i=0; i<node.jjtGetNumChildren();i++) {
			//log.debug(""+i+"/"+node.jjtGetNumChildren());
			//log.debug(node);
			//log.debug(parent);
			SimpleNode child = (SimpleNode) node.jjtGetChild(i); 
			//log.debug(child);
			if(child instanceof ASTAnyText) {
				addRow(new TextConstruction((ASTAnyText)child), parent, selectedRowIndex++);
			} else if(child instanceof ASTCall) {
				((ASTCall)child).setEngine(null);
				addRow(new CallConstruction((ASTCall)child), parent, selectedRowIndex++);
			} else if(child instanceof ASTRepeat) {
				ASTRepeat rn = new ASTRepeat(0); //We can't just insert a node that is container in new template code tree
												 //So the new fresh node object has to be created.  
				rn.setRepeatsNum(((ASTRepeat) child).getRepeatsNum());
				RepeatConstruction repeat = new RepeatConstruction(rn);
				addRow(repeat, parent, selectedRowIndex);
				selectedRowIndex++;
				analyseNode(child, repeat);
				selectedRowIndex++;
			} else if(child instanceof ASTMulti) {
				ASTMulti mn = new ASTMulti(0);	//We can't just insert a node that is container in new template code tree
												//So the new fresh node object has to be created.  
				mn.setChoicesCount(((ASTMulti) child).getChoicesCount());
				MultiConstruction multi = new MultiConstruction(mn);
				multi.setChoicesCount(mn.getChoicesCount());
				addRow(multi, parent, selectedRowIndex);
				selectedRowIndex++;
				analyseNode(child, multi);
				selectedRowIndex++;
			} else if(child instanceof ASTMultiChoice) {
				MultiChoiceConstruction choice = new MultiChoiceConstruction((MultiConstruction) parent);
				addRow(choice, parent, selectedRowIndex++);
				analyseNode(child, parent);
			} else if(child instanceof ASTSection) {
				ASTSection asc = new ASTSection(0);
				asc.setName(((ASTSection)child).getName());
				SectionConstruction sc = new SectionConstruction(asc);
				addRow(sc, parent, selectedRowIndex);
				selectedRowIndex++;
				analyseNode(child, sc);
				selectedRowIndex++;
			}
		}
			
	}

	/**
	 * 	Inserts the template code node into tempalte nodes hierarchy
	 * 
	 * @param row	the position of parent in assignment constructor editor
	 * @param node	the node to be inserted (the node is an one of the SimpleNode descendants)
	 * @param parent_node	the node to be the parent of new node
	 */
	protected void insertNode(int row, SimpleNode node, SimpleNode parent_node) {
		if(node==null)
			return;
		if(row==-1 || row>rows.size()) {
			parent_node.jjtAddChild(node, parent_node.jjtGetNumChildren());
		} else {
			SimpleNode sel_node = ((AbstractTemplateConstruction)rows.get(row)).getNode();
			if(parent_node==sel_node)
				parent_node.insertNode(node, 0);
			else
				for(int i=0;i<parent_node.jjtGetNumChildren();i++) {
					if(sel_node==parent_node.jjtGetChild(i)) {
						parent_node.insertNode(node, i+1);
						break;
					}
				}
		}
		node.jjtSetParent(parent_node);
	}
	
}
