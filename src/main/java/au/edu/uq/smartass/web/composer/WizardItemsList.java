/* This file is part of SmartAss and contains the WizardItemsList class that 
 * represents the list of AssignmentWizardItem objects. 
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

import java.io.Serializable;
import java.util.List;

/**
 * The WizardItemsList class represents the list of AssignmentWizardItem objects.
 *
 */
public class WizardItemsList implements Serializable {
	private List<AssignmentWizardItem> items;
	
	public WizardItemsList(List<AssignmentWizardItem> items) {
		this.items = items;
	}
	
	public List<AssignmentWizardItem> getItems() {
		return items;
	}
}
