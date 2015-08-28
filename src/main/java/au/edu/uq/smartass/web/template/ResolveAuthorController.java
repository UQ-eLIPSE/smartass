/* This file is part of SmartAss and contains the ResolveAuthorController class that 
 * is used to "resolve" author - e.g. to connect author name from the metadata of template importing 
 * to the corresponding author record in the smartass database or create a new one if such record 
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

import au.edu.uq.smartass.web.AuthorsItemModel;

/**
 * The ResolveAuthorController class is used to "resolve" author - 
 * e.g. to connect author name from the metadata of template importing 
 * to the corresponding author record in the smartass database or create a new one if such record 
 * does not exists.
 */
public class ResolveAuthorController extends AbstractResolveAuthorController{
	@Override
	protected void setAuthor(TemplateImportModel template, AuthorsItemModel author) {
		template.setAuthor(author);
	}

	@Override
	protected AuthorsItemModel initAuthor(TemplateImportModel template) {
		return template.getAuthor();
	}
	
	@Override
	protected boolean needImport(TemplateImportModel template) {
		return template.importAuthor;
	}

}
