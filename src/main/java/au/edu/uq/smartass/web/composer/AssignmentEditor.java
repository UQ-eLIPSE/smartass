/* This file is part of SmartAss and contains the AssignmentEditor class that contains a set 
 * of functions for interactive compositions of assignments through the SmartAss web site.   
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;

import au.edu.uq.smartass.templates.texparser.ParseException;
import au.edu.uq.smartass.web.AssignmentsItemModel;
import au.edu.uq.smartass.web.RepositoryStorage;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.AssignmentsDao;

/**
 * The AssignmentEditor class contains a set of functions that is used by 
 * SmartAss web site related controller classes to perform assignment edit operations.
 */
public class AssignmentEditor {

    AssignmentsDao dao;
    RepositoryStorage storage;
    
    /**
     * Prepares assignment to be edited in the composer. Lookups assignment stored in the site
     * repository and initializes {@link AssignmentConstruct} with the data from those assignment, if found.
     * 
     * @param assignment    {@link AssignmentConstruct} to be initialized
     * @param id            assignment id
     * @param mode          1 - edit the assignment with given id, 2 - create a copy of the assignment with given id then edit 
     * @param user          current site user
     * 
     * @throws IOException
     * @throws ParseException
     */
    public void initAssignment(AssignmentConstruct assignment, Integer id, Integer mode, UserItemModel user) throws IOException, ParseException {
        if(id!=null && id>0) {
            AssignmentsItemModel it = dao.getItem(id);
            if(it!=null) {
                if(mode!=null && mode==1 && user!=null) {
                    assignment.setId(it.getId());
                    assignment.setName(it.getName());
                    assignment.setDescription(it.getDescription());
                    assignment.setDtcreated(it.getDtcreated());
                    assignment.setUser(it.getUser());
                } else {
                    assignment.setName(it.getName()+"Copy");
                    assignment.setUser(user);
                }
                InputStream in = storage.getFile(0, Integer.toString(it.getUser().getId()), Integer.toString(it.getId()));
                if(in!=null) {
                    StringBuffer fileData = new StringBuffer(1000);
                    InputStreamReader reader = new InputStreamReader(in);
                    char[] buf = new char[1024];
                    int numRead=0;
                    while((numRead=reader.read(buf)) != -1){
                        String readData = String.valueOf(buf, 0, numRead);
                        fileData.append(readData);
                        buf = new char[1024];
                      }
                      reader.close();
                      assignment.setCode(fileData.toString());
                }
            }
        }
    }
    
    /**
     * Saves the assignment that is edited to the site repository
     * 
     * @param assignment    {@link AssignmentConstruct} to be saved
     * @param user          current site user
     * @param mcontext      Spring WebFlow MessageContext to return error messages if there are some
     * 
     * @return              true if everything is OK or false otherwise
     *  
     * @throws Exception
     */
    public boolean saveAssignment(AssignmentConstruct assignment, UserItemModel user, MessageContext mcontext) throws Exception {
        assignment.setName(assignment.getName().trim());
        if(user==null) {
            mcontext.addMessage(new MessageBuilder().error().source("save").defaultText(
                "To save an assignment you need to log in as a user with appropriate rights!").build());
            return false;
        }
        if(assignment.getName().length()==0) {
            mcontext.addMessage(new MessageBuilder().error().source("save").defaultText(
                "\"Assignment name\" field is requiered!").build());
            return false;
        }
        AssignmentsItemModel it = dao.getItem(assignment.getName());
        if(it!=null && assignment.getId()!=it.getId()) {
            mcontext.addMessage(new MessageBuilder().error().source("save").defaultText(
                "The assignment with the name \"" + it.getName() + "\" is already exists!").build());
            return false;
        }

        assignment.setUser(user);
        dao.updateItem(assignment);
        storage.setFile(0, Integer.toString(user.getId()), Integer.toString(assignment.getId()), 
                new ByteArrayInputStream(assignment.getCode().getBytes()) );
        return true;
    }

    /**
     * Makes some LaTeX decorations to assignment constructions (enumeration etc)  
     * 
     * @param assignment    {@link AssignmentConstruct} to edit
     * 
     * @return              decorated assignment code as a {@link String}
     * 
     * @throws IOException
     */
    public String preprocessCode(AssignmentConstruct assignment) throws IOException {
        int enum_level = 0;
        int repeat_level = 0;
        int repeat_pos = -1;
        int call_count = 0;
        int call_pos = -1;
        if(assignment.getDecorateWithLatex()) {
            BufferedReader br = new BufferedReader(new StringReader(assignment.getCode()));
            StringBuffer buff = new StringBuffer();
            String s;
            while((s=br.readLine())!=null) {
                if(s.indexOf("\\begin{enumerate}")>=0) {
                    buff.append("\\begin{enumerate}\n");
                    enum_level++;
                } else if(s.indexOf("%%CALL")==0 || s.indexOf("%%MULTI")==0) {
                    if(enum_level==0) {
                        buff.append("\\begin{enumerate}\n");
                        enum_level++;
                    }
                    if(call_count==0)
                        call_pos = buff.length();
                    if(s.indexOf("%%CALL")==0)
                        buff.append("\\item\n");
                    call_count++;
                    if(call_count==1 && repeat_pos>=0) {
                        buff.insert(call_pos, "\\begin{enumerate}\n");
                        buff.insert(repeat_pos, "\\item\n");
                        enum_level++;
                    }
                        
                } else if(s.indexOf("%%REPEAT")==0) {
                    if(enum_level<=repeat_level && repeat_level==0) {
                        buff.append("\\begin{enumerate}\n");
                        enum_level++;
                        call_count = 0;
                    }
                    repeat_level++;
                } else if(s.indexOf("\\end{document}")==0) 
                    while(enum_level>0) {
                        buff.append("\\end{enumerate}\n");
                        enum_level--;
                    }
                 else if(s.indexOf("%%ENDREPEAT")==0) {
                        if(repeat_level<enum_level) {
                            buff.append("\\end{enumerate}\n");
                            enum_level--;
                            repeat_pos = -1;
                        }
                        repeat_level--;
                    }
                
                
                buff.append(s);
                buff.append("\n");

                if(repeat_level==1 && s.indexOf("%%REPEAT")==0) 
                    repeat_pos = buff.length();
                else if(s.indexOf("%%ENDREPEAT")==0) {
                    if(repeat_level==0) {
                        buff.append("\\end{enumerate}\n");
                        enum_level--;
                    }
                }
            }
            return buff.toString();
        } 
        return assignment.getCode();
    }
    
    /**
     * The setter for the assignment DAO (data access object)
     */
    public void setDao(AssignmentsDao dao) {
        this.dao = dao;
    }

    /**
     * The setter for the assignment storage
     */
    public void setStorage(RepositoryStorage storage) {
        this.storage = storage;
    }
}
