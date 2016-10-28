/* This file is part of SmartAss and contains the AssignmentWizardController class that 
 * provides a kind of "wizard" to decorate the assignment with some
 * titles, descriptions etc.   
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import au.edu.uq.smartass.templates.texparser.ParseException;


/**
 * The AssignmentWizardController class provides a kind of "wizard" 
 * to decorate the assignment with some titles, descriptions etc.   
 */
public class AssignmentWizardController {

    /** Class logger. */
    private static final Logger LOG = LoggerFactory.getLogger( AssignmentWizardController.class );

    AssignmentTemplatesStorage templates;

    public void setTemplates(AssignmentTemplatesStorage templates) { 
        LOG.info("::setTemplates()[ AssignmentTemplatesStorage=>{} ]", templates.getPath());
        this.templates = templates; 
    }

    /** Stores the assignment, for changing the title after creation **/
    private AssignmentConstruct assignment;

    /** Store the template name **/
    String templateName;

    /**
     * Parses the fields description of assignment template. The format of this description is quite simple:<br>
     * %%%Some comment<br>
     * %%fieldname,fieldtype<br>
     * %field description line<br>
     * %next field description line<br>
     * %...<br>
     * 
     * where fieldname is any string (though a name that consist of letters and digits only without blanks is recommended), 
     * fieldtype is one of I, L, T chars where I is for Integer, L is one-line text, T is multiline text.
     * Any line not beginning with '%' means the end of fields definition section 
     * 
     * @param templateName
     * @return	items that will be displayed at the wizard page
     * @throws IOException
     */
    public Object prepareWizard(String templateName) throws IOException {
        LOG.info("::prepareWizard()[ templateName:String=>{} ]", templateName);

        List<AssignmentWizardItem> items = new ArrayList<AssignmentWizardItem>();

        BufferedReader br = new BufferedReader(new InputStreamReader(templates.getFile(templateName)));
        String s;
        AssignmentWizardItem it = null;
        while((s=br.readLine())!=null) {
            if(s.indexOf("%%%")==0) {
                continue;
            }
            if(s.indexOf("%%")==0) {
                it = new AssignmentWizardItem();
                String[] ss = s.substring(2).split(",");
                it.setName(ss[0].trim());
                if(ss.length>1)
                    it.setType(ss[1].trim());
                if(ss.length>2)
                    try {
                        it.setMaxLength(Integer.parseInt(ss[2].trim()));
                    } catch (Exception e) {	} //Third parameter should be a number, but when it isn't just leave maxLength = 0... 
                items.add(it);
                continue;
            }
            //Field description should be next after the definition of field name and type.
            // Otherwise just drop the text and go to next line
            if(it!=null && s.indexOf("%")==0) { 
                it.setTitle(it.getTitle() + s.substring(1));
                continue;
            }
            break; //any line not beginning with '%' means the end of fields definition section
        }

        return new WizardItemsList(items);
    }

    /**
     * Parses the fields description of assignment template "default.tex". 
     * The format of this description is quite simple:<br>
     * %%%Some comment<br>
     * %%fieldname,fieldtype<br>
     * %field description line<br>
     * %next field description line<br>
     * %...<br>
     * 
     * where fieldname is any string (though a name that consist of letters and digits only without blanks is recommended), 
     * fieldtype is one of I, L, T chars where I is for Integer, L is one-line text, T is multiline text.
     * Any line not beginning with '%' means the end of fields definition section 
     * 
     * @return	items that will be displayed at the wizard page
     * @throws IOException
     */
    public Object prepareWizard() throws IOException {
        return prepareWizard("default.tex");
    }

    /**
     * Creates an assignment using data entered at wizard execution.
     * 
     * @param assignment		{@link AssignmentConstruct} that is to be built on the wizard data  
     * @param templateName		assignment template name
     * @param items				data filled through the wizard 
     * @param mcontext			context for error messages
     * 
     * @return					true if everything is OK, false otherwise
     * @throws IOException
     * @throws ParseException
     */
    public boolean createAssignment(
            AssignmentConstruct assignment, 
            String templateName, 
            List<AssignmentWizardItem> items, 
            MessageContext mcontext
            ) throws IOException, ParseException {

        // Store the assignment instance
        this.assignment = assignment;

        // Store the template name
        this.templateName = templateName;

        LOG.info(
                "::createAssignment()[ assignment=>{}, templateName=>{}, mcontext=>{} ]", 
                assignment.toString(), 
                templateName, 
                mcontext.toString()
                );

        // If the items are set
        if (items != null) {
            for(AssignmentWizardItem it : items) {
                if (it.getMaxLength()>0 && it.value.length()>it.getMaxLength()) {
                    mcontext.addMessage(
                            new MessageBuilder().error().source(it.name).defaultText(
                                "Field '"+it.getTitle()+"' length should not exceed "+it.getMaxLength()+" characters!"
                                ).build()
                            );
                    return false;
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(templates.getFile(templateName)));
            String s;
            while ( (s=br.readLine()) != null && s.indexOf("%")==0 );

            StringBuffer sa = new StringBuffer();
            while ( (s=br.readLine()) != null ) { 
                if (s.indexOf("%%") >= 0) 
                    for(AssignmentWizardItem it : items) {
                        s = s.replaceAll("%%"+it.name+"%%", it.value);
                    }
                sa.append(s);
                sa.append("\n");
            }

            assignment.setCode(sa.toString());
        }
        assignment.setDecorateWithLatex(true);

        return true;
    }

    /**
     * Sets the title of the assignment
     * @param title The title of the assignment
     */
    public void setTitle(String title) throws IOException, ParseException {

        LOG.info("::::::::::::::::::: SETTING TITLE TO " + title);

        BufferedReader br = new BufferedReader(new InputStreamReader(templates.getFile(templateName)));
        String s;
        while ( (s=br.readLine()) != null && s.indexOf("%")==0 );

        StringBuffer sa = new StringBuffer();
        while ( (s=br.readLine()) != null ) { 
            if (s.indexOf("%%") >= 0) {
                    s = s.replaceAll("%%title%%", title);
            }
            sa.append(s);
            sa.append("\n");
        }

        assignment.setCode(sa.toString());
    
    }

    /**
     * Prepares wizard for the %%REPEAT ... %%ENDREPEAT construction
     * 
     * @param repeat	repeat construction
     */
    public void prepareNewRepeat(RepeatConstruction repeat) {
        List<AssignmentWizardItem> addons = new ArrayList<AssignmentWizardItem>();
        AssignmentWizardItem it = new AssignmentWizardItem();
        it.setName("QUESTION");
        it.setTitle("Questions sub-header");
        it.setType("T");
        it.setValue("Answer each of the following questions, showing all working:");
        addons.add(it);
        it = new AssignmentWizardItem();
        it.setName("SOLUTION");
        it.setTitle("Solutions sub-header");
        it.setType("T");
        addons.add(it);
        it = new AssignmentWizardItem();
        it.setName("SHORTANSWER");
        it.setTitle("Short answers sub-header");
        it.setType("T");
        addons.add(it);
        repeat.setAddons(addons);
    }

    /**
     * Decorates %%REPEAT ... %%ENDREPEAT construction with the data entered through the wizard
     * 
     * @param assignment
     * @param addons		wizard data
     */
    public void afterAddNewRepeat(AssignmentConstruct assignment, List<AssignmentWizardItem> addons) {
        LOG.info("::afterAddNewRepeat()[ assignment=>{}, addons=>{} ]", "-", "-");

        if(addons!=null) {
            for(AssignmentWizardItem it: addons) {
                if(it.value.length()>0) {
                    SectionConstruction sec = new SectionConstruction();
                    sec.setSectionName(it.name);
                    assignment.addRow(sec);
                    TextConstruction text = new TextConstruction();
                    text.setText(it.getValue());
                    assignment.addRow(text);
                    assignment.setSelectedIndex(assignment.getSelectedIndex()+1);
                }

            }
        }
    }
}
