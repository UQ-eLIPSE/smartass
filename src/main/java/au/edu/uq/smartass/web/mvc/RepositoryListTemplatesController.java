/* This file is part of SmartAss and contains the RepositoryListTemplatesController class that 
 * lists templates.
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
package au.edu.uq.smartass.web.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import au.edu.uq.smartass.web.ClassificationsItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.UserItemModel;
import au.edu.uq.smartass.web.jdbc.ClassificationsDao;
import au.edu.uq.smartass.web.jdbc.TemplatesDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The RepositoryListTemplatesController class lists templates.
 */
public class RepositoryListTemplatesController extends AbstractController {

    /** Class logger. */
    private static final Logger LOG = LoggerFactory.getLogger( RepositoryListTemplatesController.class );

    private static int ROWS_PER_PAGE=20 ; //temporary! move this to application settings!  

    protected TemplatesDao templatesDao; 

    protected ClassificationsDao classificationsDao;
    

    /**
     * This function is called by Spring framework on HTTP request from the browser. 
     * Lists templates depending on search conditions.
     */
    @Override
    protected ModelAndView handleRequestInternal(
            HttpServletRequest request, HttpServletResponse response
        ) throws Exception 
    {

        LOG.info( "::handleRequest()[ \n*** request=>\n{}, \n***response=>\n{} \n] ", request, response );

        int classid = 0;
        response.addHeader("Expires",  "Thu, 01 Jan 1981 01:00:00 GM");

        ClassificationsItemModel selected_class = null;
        ClassificationsItemModel selected_top_class = null;
        List<ClassificationsItemModel> sub_classs = null;

        try {
            classid = Integer.parseInt(request.getParameter("classid"));
            selected_class = classificationsDao.getItem(classid);

            LOG.info( "::handleRequest()[ classid=>{}, selected_class=>{} ] ", classid, selected_class );

            if (selected_class != null && selected_class.getParentModel() != null)
                    selected_top_class = selected_class.getParentModel();
            else
                    selected_top_class = selected_class;

            if( selected_top_class != null)
                    sub_classs = classificationsDao.getItems(selected_top_class.getId(), "%");

        } catch (Exception e) {
            LOG.error( "::handleRequest()[ Exception Caught => {} ] ", e.toString() );
        }
        
        int rowsPerPage = ROWS_PER_PAGE;
        UserItemModel user = (UserItemModel) request.getSession().getAttribute("user");
        if(user!=null && user.getRowsPerPage()>0) 
            rowsPerPage = user.getRowsPerPage();
        
        int page_no = 1;
        String spage_no = request.getParameter("page");
        if(spage_no!=null && spage_no.length()>0) 
            try {
                page_no = Integer.parseInt(spage_no);
            } catch (NumberFormatException e) {}

        List<TemplatesItemModel> templates = templatesDao.select(
                request.getParameter("templ_filter"), 
                request.getParameter("keyword_filter"), 
                classid, 
                (page_no - 1) * rowsPerPage, 
                rowsPerPage, 
                null
            );
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("templ_filter", request.getParameter("templ_filter"));
        model.put("keyword_filter", request.getParameter("keyword_filter"));
        model.put(
                "page_num", 
                templatesDao.countRows(request.getParameter("templ_filter"), request.getParameter("keyword_filter"), classid) / rowsPerPage + 1
            );
        model.put("page_no", page_no);
        model.put("templates", templates);
        model.put("classs", classificationsDao.select(0));
        model.put("sub_classs", sub_classs);
        model.put("selected_class", selected_class);
        model.put("selected_top_class", selected_top_class);

        return new ModelAndView("repository_browse", model);
    }

    
    /**
     *
     */
    public void setTemplatesDao(TemplatesDao templatesDao) { this.templatesDao = templatesDao; }
    
    /**
     *
     */
    public void setClassificationsDao(ClassificationsDao classificationsDao) { this.classificationsDao = classificationsDao; }
}
