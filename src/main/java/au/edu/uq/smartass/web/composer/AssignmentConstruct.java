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
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The AssignmentConstruct class is the Spring bean that represents 
 * the main object of assignment composer. This object stores assignment constructions and provides
 * an assignment editing related functionality.
 */
public class AssignmentConstruct extends AssignmentsItemModel implements Serializable {

	/** Class logger. */
	private static final Logger LOG = LoggerFactory.getLogger( AssignmentConstruct.class );

	
	/** Root node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	private ASTTemplate template_node;
	/** Document body node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	private ASTDocument doc_node;
	/** Document header node of the assignment template (see au.edu.uq.smartass.templates.texparser for details) */
	private ASTAnyText header_node;

	/** Assignment constructions collection */
	private List<AbstractTemplateConstruction> components = new ArrayList<AbstractTemplateConstruction>();

        /**
         * The position of the currently selected component. 
         * This value is set by the JSP form, but also causes the correct item to be selected when the form is rendered.
         * It is manipulated in the back end as components are added and removed from the assignment.
         */
	private int selectedIndex;
	public void setSelectedIndex(int selectedIndex) { 
                LOG.debug( "::setSelectedIndex( {} )[ {} => {} ]", selectedIndex, this.selectedIndex, selectedIndex );
                this.selectedIndex = selectedIndex; 
        }
	public int getSelectedIndex() { return selectedIndex; }
        public int incrementSelectedIndex() {
		int result = selectedIndex;
                setSelectedIndex( selectedIndex + 1 );
                return result;
        }

        /** @TODO: Use a unique identifier (HashCode?) for selected object. */
        /*
        private int selectedIdentity;
        public int getSelectedIdentity() { return selectedIdentity; }
        public void setSelectedIdentity(int selectedIdentity) { this.selectedIdentity = selectedIdentity; }
        */
	

        /** */
	private boolean decorateWithLatex;


	/** Default constructor */
	public AssignmentConstruct() { init(); }
	

	private void init() {
		initTemplate();
		//ensure that we have newline after \\begin{document}
		ASTAnyText enter = new ASTAnyText(0);
		enter.setText("\n");
		doc_node.jjtAddChild(enter, 0);
	}
	

	/**
	 * Creates empty assignment and sets its initial and default data
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
		if(components.indexOf(construct_row)>=0) {
			components.remove(construct_row);
			construct_row.onRemove();
		}
	}
	
	
        public void addNonVisibleComponent(
                        AbstractTemplateConstruction construct_row, 
                        AbstractTemplateConstruction parent_row, 
                        int row_index
        ) {
                LOG.info( "::addNonVisibleComponent()[ component=>{} ]", construct_row.getNode().getCode() );
                construct_row.setVisible(false);                // Do first !! Used to set visibility of 'SectionEnd'
                addRow(construct_row, parent_row, row_index);
        }


	/**
	 * Add new assignment construction to assignment
	 * 
	 * @param construct_row		assignment construction to add
	 * @param parent_row		parent assignment construction (null if we add top-level construction)
	 * @param row_index		index of assignment row after wich new construction will be inserted 
	 */
	public void addRow(AbstractTemplateConstruction construct_row, AbstractTemplateConstruction parent_row, int row_index) {
                LOG.info(
                                "::addRow()[ construct_row=>{}, parent_row=>{}, row_index=>{} ]", 
                                construct_row.toString(), 
                                (null == parent_row) ? "NULL" : parent_row.toString(), 
                                row_index 
                        );
                //LOG.info( "StackTrace:\n{}", Arrays.toString( Thread.currentThread().getStackTrace() ) );

                int insertionPoint = row_index + 1;
                construct_row.setIndex(insertionPoint);
		components.add(insertionPoint, construct_row);

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

                /*
                SimpleNode new_node;
                SimpleNode parent_node;
                int index;
                insertNode(index, new_node, parent_node);
                */
	}
	
	/**
	 * Add new assignment construction after current selected row position
	 * 
	 * @param construct_row		assignment construction to add
	 */
	public void addRow(AbstractTemplateConstruction construct_row) {
		addRow(
                                construct_row, 
                                components.get(getSelectedIndex()),
                                getSelectedIndex()
                        );
		incrementSelectedIndex();
	}
	
	/**
	 * Get an assignment construction at index position 
	 * 
	 */
	public AbstractTemplateConstruction getRow(int index) {
		if(index>=0 && index<components.size())
			return components.get(index);
		return null;
	}

	/** Returns number of components in the assignment */
	public int getRowCount() { return components.size(); }

	/** 
         * Retrieve the list of assignment components visible in the editor.
         * Each components position in the list is updated to reflect changes in the collection ordering.
         *
         * @return List of Assignment components for the Editor.
         */
	public List<AbstractTemplateConstruction> getRows() { 
                LOG.info("::getRows() [\n{}\n]", components.toString());
                List<AbstractTemplateConstruction> items = new ArrayList<AbstractTemplateConstruction>();

                ListIterator<AbstractTemplateConstruction> it = components.listIterator(); 
                while (it.hasNext()) {
                        int idx = it.nextIndex();
                        AbstractTemplateConstruction item = it.next();
                        item.setIndex(idx);
                        if (item.isVisible()) items.add(item);
                }

                LOG.info("::getRows() [\n{}\n]", items.toString());
                return items; 
        }
	
	/** */
	public boolean getDecorateWithLatex() { return decorateWithLatex; }
	/** */
	public void setDecorateWithLatex(boolean decorateWithLatex) { this.decorateWithLatex = decorateWithLatex; }
	
	/** Returns the selected assignment construction */
	public AbstractTemplateConstruction getSelectedRow() { return components.get(getSelectedIndex()); }

	/**
	 * Removes the selected assignment construction
	 */
	public void removeSelectedRow() {
                LOG.info( "::removeSelectedRow()[ classs selectedIndex => {} ]", getSelectedIndex() );
                components.remove(getSelectedIndex()).onRemove();
	}
	
	/**
	 * Gets the template code as {@link String}
	 */
	public String getCode() { return template_node.getCode(); }
	
        /**
         * Test for 'Executable' content.
         * If the content does not contain <code>CallConstruction</code> or non-empty <code>TextConstruction</code>
         * then the content is considered 'Non-Executable' and therefore effectively 'Empty'.
         */
	public boolean isContentEmpty() {
                for (AbstractTemplateConstruction it : components) {
                        if ( 
                                it instanceof CallConstruction || 
                                ( it instanceof TextConstruction && !(it.getNode().getCode().trim().isEmpty()) ) 
                        )
                                return false;
                }
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
                LOG.info("::setCode()[ code=>\n{}\n]", code);

		components.clear();
		initTemplate();
		setSelectedIndex(-1);     // Next component will be inserted after this position (ie @[0] for empty component list.

		if(code!=null && code.length()>0) {
			Engine engine = Engine.getInstance();
			TexReader tr = new TexReader(engine);
			InputStream in = new ByteArrayInputStream(code.getBytes("UTF-8"));
			tr.loadTemplate(in);
			ASTTemplate tmp_node = (ASTTemplate) tr.getRootNode();
			in.close();
			analyseNode(tmp_node.getDocument(), null);
		}

                setSelectedIndex( components.size() - 1 ); // Select last component.
	}

	/**
	 * Transforms the template nodes tree created after code parsing 
	 * 
	 * @param node	node to transform
	 * @param parent	parent assignment construction
	 */
	protected void analyseNode(SimpleNode node, AbstractTemplateConstruction parent) {
                LOG.info( 
                                "::analyseNode()[ node=>{}, parent=>{} ]", 
                                node.toString(), 
                                null == parent ? "NULL" : parent.toString() 
                        );
		for (int i=0; i<node.jjtGetNumChildren();i++) {
			SimpleNode child = (SimpleNode) node.jjtGetChild(i); 

			if(child instanceof ASTAnyText) {
				addNonVisibleComponent(new TextConstruction((ASTAnyText)child), parent, incrementSelectedIndex());

			} else if(child instanceof ASTCall) {
				((ASTCall)child).setEngine(null);
				addNonVisibleComponent(new CallConstruction((ASTCall)child), parent, incrementSelectedIndex());

			} else if(child instanceof ASTRepeat) {
				ASTRepeat rn = new ASTRepeat(0); //We can't just insert a node that is container in new template code tree
												 //So the new fresh node object has to be created.  
				rn.setRepeatsNum(((ASTRepeat) child).getRepeatsNum());
				RepeatConstruction repeat = new RepeatConstruction(rn);
				addNonVisibleComponent(repeat, parent, getSelectedIndex());
				incrementSelectedIndex();
				analyseNode(child, repeat);
				incrementSelectedIndex();

			} else if(child instanceof ASTSection) {
				ASTSection asc = new ASTSection(0);
				asc.setName(((ASTSection)child).getName());
				SectionConstruction sc = new SectionConstruction(asc);
				addNonVisibleComponent(sc, parent, getSelectedIndex());
				incrementSelectedIndex();
				analyseNode(child, sc);
				incrementSelectedIndex();

			} else {
                                LOG.warn( "::analyseNode()[ {} NOT recognised - Ignoring ... ]", node.getCode() );
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
		if(row==-1 || row>components.size()) {
			parent_node.jjtAddChild(node, parent_node.jjtGetNumChildren());
		} else {
			SimpleNode sel_node = ((AbstractTemplateConstruction)components.get(row)).getNode();
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
