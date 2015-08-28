/* This file is part of SmartAss and contains the DictionaryDataModel class that 
 * is the ancestor for all dictionary models and contains some dictionary-specific fields
 * and methods. 
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
package au.edu.uq.smartass.repository.data;

import java.sql.SQLException;

import javax.sql.RowSet;

import au.edu.uq.smartass.repository.LookupTableModel;

import com.sun.rowset.JdbcRowSetImpl;

/**
 * The DictionaryDataModel class is the ancestor for all dictionary models 
 * and contains some dictionary-specific fields and methods. 
 */
abstract public class DictionaryDataModel extends IntIdDataModel {

	public class DictionaryRowSetTableModel extends RowSetTableModel {

		public DictionaryRowSetTableModel(RowSet rowset) {
			super(rowset);
		}

		public DictionaryRowSetTableModel(RowSet rowset, boolean readonly) {
			super(rowset, readonly);
		}
		
		@Override
		protected void beforeSetValue(String columnName, Object newValue) {
			if(columnName.equals("name")) {
				int id = 0;
				try {
					id = rowSet.getInt("id");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				IntIdItemModel it = (IntIdItemModel) getItem("name", newValue); 
				if(it!=null && it.getId()!=id)
					throw new DataValidationException("The item with name \"" + newValue + "\" already exists in the dictionary!");
			}
		}
		
		@Override
		protected void checkRowValid() throws DataValidationException {
			try {
				String name = rowSet.getString("name");
				if(name==null || name.length()==0) 
						throw new DataValidationException("Name can not be empty!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public DictionaryDataModel(Database data) {
		super(data);
	}

	public LookupTableModel getLookupModel() {
    	RowSet rs;
		try {
			rs = new JdbcRowSetImpl(data.getConnection());
			rs.setCommand(composeRowsetSql(null));
			rs.execute();
			return new LookupTableModel(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
