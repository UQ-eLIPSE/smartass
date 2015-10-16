/* This file is part of SmartAss and contains the AuthorsDao class - the object-relational mapper class 
 * for the AuthorsItemModel.
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

import au.edu.uq.smartass.web.AssignmentsItemModel;
import au.edu.uq.smartass.web.AuthorsItemModel;
import au.edu.uq.smartass.web.NamedItemModel;


/**
 * The AuthorsDao class is the object-relational mapper class for the AuthorsItemModel.
 *
 */
@SuppressWarnings("unchecked")
public class AuthorsDao extends DictionaryDao {

	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to {@link AuthorsItemModel} object
	 */
	class ItemRowMapper extends DictionaryItemRowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	AuthorsItemModel item = (AuthorsItemModel) super.mapRow(rs, rowNum);
	    	item.setDescription(rs.getString("description"));
	    	return item;
		}
		
		@Override
		protected NamedItemModel createObject() {
			return new AuthorsItemModel();
		}
	}
	
	@Override
	protected RowMapper getRowMapper() {
		return new ItemRowMapper();
	}
	
	@Override
	protected String getInsertSql() {
		return "insert into authors (name, description) values (?, ?)";
	}
	
	@Override
	protected void setInsertParams(PreparedStatement st, NamedItemModel item)
			throws SQLException {
		super.setInsertParams(st, item);
		st.setString(2, ((AuthorsItemModel)item).getDescription());
	}
	
	@Override
	protected String getUpdateSql() {
		return "update authors set name=?, description=? where id=?";
	}
	
	@Override
	Object[] getUpdateParams(NamedItemModel it) {
		AuthorsItemModel item = (AuthorsItemModel) it;
		return new Object[]{item.getName(), item.getDescription(), item.getId()};
	}

	@Override
	protected String getTableName() {
		return "authors";
	}
	
	@Override
	/**
	 * Returns the single {@link AuthorsItemModel} selected by its id
	 * 
	 * @param id	author's id
	 * 
	 * @return	the AuthorsItemModel 
	 */
	public AuthorsItemModel getItem(int id) {
		return (AuthorsItemModel) super.getItem(id);
	}
	
	@Override
	public AuthorsItemModel getItem(String name) {
		return (AuthorsItemModel) super.getItem(name);
	}
	
	/**
	 * Returns the {@link AuthorsItemModel} selected by name
	 *  
	 * @param filter	the author's name substring
	 * @return			the assignment
	 */
	public List<AuthorsItemModel> getAuthors(String filter) {
		return (List<AuthorsItemModel>)(List) super.getItems(filter);
	}
	
	@Override
	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the author to be deleted
	 */
	public void deleteItem(int id) throws Exception {
		int templ_count = getJdbcTemplate().queryForInt("select count(*) from templates where author_id="+id);
		if(templ_count>0)
			throw new Exception("Can not delete this author! There are " + templ_count + 
					" template(s) linked to it!");

		super.deleteItem(id);
	}
}
