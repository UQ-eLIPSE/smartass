/* This file is part of SmartAss and contains the UpdatesItemModel class - the model (as in Model-View-Controller) class for the template update.
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
package au.edu.uq.smartass.web;

import java.util.Date;

/**
 * The UpdatesItemModel class is the model (as in Model-View-Controller) class for the template update
 * (e.g. the information about changes made ion the template by some author at some moment of time).
 *
 */
public class UpdatesItemModel extends IntIdItemModel {
	/** The template id */
	int templateId;
	/** The author who edits the template */
	AuthorsItemModel author;
	/** The comment to the update */
	String comment;
	/** The date of the update */
	Date updateDate;
	
	/**
	 * The getter for the author who edits the template
	 */
	public AuthorsItemModel getAuthor() {
		return author;
	}

	/**
	 * The setter for the author who edits the template
	 */
	public void setAuthor(AuthorsItemModel author) {
		this.author = author;
	}
	
	/**
	 * The getter for the comment to the update 
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * The setter for the comment to the update 
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * The getter for the template id
	 */
	public int getTemplateId() {
		return templateId;
	}

	/**
	 * The setter for the template id
	 */
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	
	/**
	 * The getter for the update date
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * The setter for the update date
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
