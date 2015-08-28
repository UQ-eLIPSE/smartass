/* This file is part of SmartAss and contains the FilesDao class - the object-relational mapping class 
 * that maps the FilesItemModel to the database table(s).
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
package au.edu.uq.smartass.web.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import au.edu.uq.smartass.web.FilesItemModel;
import au.edu.uq.smartass.web.NamedItemModel;

/**
 * The FilesDao class is the object-relational mapping class 
 * that maps the FilesItemModel to the databas
 */
public class FilesDao extends DictionaryDao {
	ItemRowMapper mapper;

	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link FilesItemModel} object
	 */
	class ItemRowMapper extends DictionaryItemRowMapper {
		@Override
		protected NamedItemModel createObject() {
			return new FilesItemModel();
		}
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	FilesItemModel item = (FilesItemModel) super.mapRow(rs, rowNum);
	    	item.setDescription(rs.getString("description"));
	    	return item;
		}
	}
	
	@Override
	protected String getTableName() {
		return "files";
	}
	
	@Override
	protected RowMapper getRowMapper() {
		return new ItemRowMapper();
	}
	
	@Override
	protected String getInsertSql() {
		return "insert into files (name, description) values (?, ?)";
	}
	
	@Override
	protected void setInsertParams(PreparedStatement st, NamedItemModel item)
			throws SQLException {
		super.setInsertParams(st, item);
		st.setString(2, ((FilesItemModel)item).getDescription());
	}
	
	@Override
	protected String getUpdateSql() {
		return "update files set name=?, description=? where id=?";
	}
	
	@Override
	Object[] getUpdateParams(NamedItemModel it) {
		FilesItemModel item = (FilesItemModel) it;
		return new Object[]{item.getName(), item.getDescription(), item.getId()};
	}
	
	@Override
	/**
	 * Get the FilesItemModel object by its id
	 * 
	 * @param id	the object's id
	 * @return		the object
	 */
	public FilesItemModel getItem(int id) {
		return (FilesItemModel) super.getItem(id);
	}
	
	@Override
	/**
	 * Get the FilesItemModel object by its name
	 * 
	 * @param name	the object's name
	 * @return		the object
	 */
	public FilesItemModel getItem(String name) {
		return (FilesItemModel) super.getItem(name);
	}
	
	@Override
	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the object to be deleted
	 */
	public void deleteItem(int id) throws Exception {
		int templ_count = getJdbcTemplate().queryForInt("select count(*) from templates_files where file_id="+id);
		if(templ_count>0)
			throw new Exception("Can not delete this file! There are " + templ_count + 
					" template(s) linked to it!");

		super.deleteItem(id);
	}
}
