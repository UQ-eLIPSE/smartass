/* This file is part of SmartAss and contains the ResolveUpdateController class that is
 * is used to "resolve" update - e.g. to connect update metadata of template 
 * that is being imported to the corresponding record in the smartass database 
 * or to create a new one if such record does not exists.
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

import au.edu.uq.smartass.web.UpdatesItemModel;

public class ResolveUpdateController {

	/**
	 * Creates new {@link ResolveUpdateModel} for the current importing item from the 
	 * {@link TemplateImportModel}
	 */
	public ResolveUpdateModel prepareModel(TemplateImportModel template) {
		UpdatesItemModel update = new UpdatesItemModel();
		UpdatesItemModel imp = template.getUpdates().get(template.getUpdatesResolver().getCurrentItemNo());
		update.setUpdateDate(imp.getUpdateDate());
		update.setComment(imp.getComment());
		update.setAuthor(template.getUpdAuthors().get(imp.getAuthor().getName()));
		
		return new ResolveUpdateModel(update);
	}
}
