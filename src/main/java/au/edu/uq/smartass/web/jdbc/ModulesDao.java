/* This file is part of SmartAss and contains the ModulesDao class - the object-relational mapping class 
 * that maps the ModulesItemModel to the database table(s).
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
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import au.edu.uq.smartass.web.ModulesItemModel;
import au.edu.uq.smartass.web.NamedItemModel;

/**
 * The ModulesDao class is the object-relational mapping class 
 * that maps the ModulesItemModel to the database table(s).
 */
@SuppressWarnings("unchecked")
public class ModulesDao extends DictionaryDao {

	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link ModulesItemModel} object
	 */
	class ItemRowMapper extends DictionaryItemRowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	ModulesItemModel item = (ModulesItemModel) super.mapRow(rs, rowNum);
	    	item.setDescription(rs.getString("description"));
	    	item.setModulePackage(rs.getString("package"));
	    	item.setParameters(rs.getString("parameters"));
	    	return item;
		}
		
		@Override
		protected NamedItemModel createObject() {
			return new ModulesItemModel();
		}
	}
	
	@Override
	protected RowMapper getRowMapper() {
		return new ItemRowMapper();
	}
	
	@Override
	protected String getInsertSql() {
		return "insert into modules (name, package, parameters, description) values (?, ?, ?, ?)";
	}
	
	@Override
	protected void setInsertParams(PreparedStatement st, NamedItemModel item)
			throws SQLException {
		super.setInsertParams(st, item);
		
		st.setString(2, ((ModulesItemModel) item).getModulePackage());
		st.setString(3, ((ModulesItemModel) item).getParameters());
		st.setString(4, ((ModulesItemModel) item).getDescription());
	}
	
	@Override
	protected String getUpdateSql() {
		return "update modules set name=?, package=?, parameters=?, description=? where id=?";
	}
	
	@Override
	Object[] getUpdateParams(NamedItemModel it) {
		ModulesItemModel item = (ModulesItemModel) it;
		return new Object[]{item.getName(), item.getModulePackage(), item.getParameters(), item.getDescription(), item.getId()};
	}

	@Override
	protected String getTableName() {
		return "modules";
	}
	
	@Override
	/**
	 * Get the ModulesItemModel object by its id
	 * 
	 * @param id	the object's id
	 * @return		the object
	 */
	public ModulesItemModel getItem(int id) {
		return (ModulesItemModel) super.getItem(id);
	}
	
	@Override
	/**
	 * Get the ModulesItemModel object by its name
	 * 
	 * @param name	the object's name
	 * @return		the object
	 */
	public ModulesItemModel getItem(String name) {
		return (ModulesItemModel) super.getItem(name);
	}

	/**
	 * Get the FilesItemModel object by its name and java package
	 * 
	 * @param name				the object's name
	 * @param modulePackage 	the object's package
	 * @return					the object
	 */
	public ModulesItemModel getItem(String name, String modulePackage) {
		List<ModulesItemModel> items = getJdbcTemplate().query("select * from modules where name=? and package=?", 
				new Object[]{name, modulePackage}, getRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}
	
	@Override
	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the object to be deleted
	 */
	public void deleteItem(int id) throws Exception {
		int templ_count = getJdbcTemplate().queryForInt("select count(*) from templates_modules where module_id="+id);
		if(templ_count>0)
			throw new Exception("Can not delete this module! There are " + templ_count + 
					" template(s) linked to it!");
	
		super.deleteItem(id);
	}
	
	@Override
	protected String getOrder() {
		return "package, name";
	}
}
