/* This file is part of SmartAss and contains the ResolveModuleController class that
 * is used to "resolve" module - e.g. to connect module name from the metadata of template that is being imported 
 * to the corresponding record in the smartass database or to create a new one if such record 
 * does not exists.
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

import java.util.List;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import au.edu.uq.smartass.web.ModulesItemModel;
import au.edu.uq.smartass.web.TemplatesItemModel;
import au.edu.uq.smartass.web.jdbc.ModulesDao;

/**
 * The ResolveModuleController class
 * is used to "resolve" module - e.g. to connect module name from the metadata of template that is being imported 
 * to the corresponding record in the smartass database or to create a new one if such record 
 * does not exists.
 */
public class ResolveModuleController extends AbstractResolveController {
	ModulesDao dao;
	
	/**
	 * Analyzes the module name and returns the list of suggestions about which
	 * records in the repository can correspond to this name 
	 * 
	 * @param moduleName	the module name
	 * @return				the list of possible modules from the local repository
	 */
	public List<ModulesItemModel> getSuggestions(String moduleName) {
		String[] parts = moduleName.split("\\.");
		return dao.getItems("%" + parts[parts.length-1] + "%");
	}
	
	/**
	 * Searches through modules in the repository by the substring from the module name. 
	 * Returns the list of modules found.
	 */
	public List<ModulesItemModel> search(String filter) {
		return dao.getItems("%" + filter + "%");
	}
	
	/**
	 * Searches through modules in the repository by the substring from the module name. 
	 * Returns the list of modules found. This function takes care about some additional information
	 * that web site engine needs to break list of the records to a set of pages.
	 */
	public List<ModulesItemModel> search(ResolveModuleModel rm) {
		rm.setRowsNum(dao.countRows("%" + rm.getSearch() + "%"));
		return dao.getItems("%" + rm.getSearch() + "%", rm.getPageNo()*rm.getRowsPerPage(), rm.getRowsPerPage());
	}
	
	/**
	 * Evaluates the solution that user has selected, checks for its consistency
	 * and adds the module to the modules property of the {@link TemplatesItemModel}
	 *   
	 * @param template		the template to be imported or edited
	 * @param rm			the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */

	public boolean resolve(TemplatesItemModel template, ResolveModuleModel rm, int itemNo, MessageContext mcontext) {
		if(!validate(template, rm, mcontext))
			return false;
		
/*		if(itemNo==-1) {
			itemNo = template.getModules().size();
			template.getModules().add(null);
		}*/

		if(rm.getSolution()==0) { 
			if(rm.getModule().getName().length()==0) {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"\"Module name\" field is requiered!").build());
				return false;
			}
			if(dao.getItem(rm.getModule().getName(), rm.getModule().getModulePackage())!=null) {
				if(rm.getModule().getModulePackage().length()==0)
					mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
							"Module " + rm.getModule().getName() + " already exists in the database!").build());
				else
					mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
							"Module " + rm.getModule().getModulePackage() +"." + rm.getModule().getName()
							+ " already exists in the database!").build());
				return false;
			}
			
			if(itemNo==-1) 
				template.getModules().add(rm.getModule());
			else
				template.getModules().set(itemNo, rm.getModule());
		} else {
			ModulesItemModel module = dao.getItem(rm.getSolution());
			if(module!=null) { //there is a VERY little probability that module was 
				 //deleted while request was processed
				 //otherwise it HAS to be in the database
				if(itemNo==-1) 
					template.getModules().add(module);
				else
					template.getModules().set(itemNo, module);
			} else {
				mcontext.addMessage(new MessageBuilder().error().source("solution").defaultText(
					"Module does not found in the database!").build());
				return false;
				}
		}
		return true;
	}
	
	/**
	 * Evaluates the solution that user has selected for the current module resolving 
	 * from the {@link TemplateImportModel}, checks for its consistency and adds the module to the modules property
	 *   
	 * @param template		the template to be imported
	 * @param rm				the resolve information
	 * @param mcontext		context from the Spring Webflow
	 *  
	 * @return				true if solution is successful
	 */
	public boolean resolve(TemplateImportModel template, ResolveModuleModel rm, MessageContext mcontext) {
		return resolve(template, rm, template.getModulesResolver().getCurrentItemNo(), mcontext);
	}
	
	
	/**
	 * Creates new {@link ResolveModuleModel} 
	 */
	public ResolveModuleModel newModel() {
		return new ResolveModuleModel(new ModulesItemModel());
	}
	
	/**
	 * Creates new {@link ResolveModuleModel} for the current importing item from the 
	 * {@link TemplateImportModel}
	 */
	public ResolveModuleModel prepareModel(TemplateImportModel template) {
		ModulesItemModel module = new ModulesItemModel();
		if(template.getModulesResolver().getNeedImport()) {
			ModulesItemModel imp = template.getModules().get(template.getModulesResolver().getCurrentItemNo()); 
			module.setName(imp.getName());
			module.setModulePackage(imp.getModulePackage());
			module.setDescription(imp.getDescription());
			module.setParameters(imp.getParameters());
		}
		return new ResolveModuleModel(module);
	}
	
	public void setDao(ModulesDao dao) {
		this.dao = dao;
	}

}
