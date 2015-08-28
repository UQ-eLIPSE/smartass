/* This file is part of SmartAss and contains the DictionaryDao class - the object-relational mapping class 
 * that is ancestor for all dictionary DAO classes.
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import au.edu.uq.smartass.web.NamedItemModel;

/**
 * The DictionaryDao class - the object-relational mapper class 
 * that is ancestor for all dictionary DAO classes. Every dictionary object has at least
 * "id" and "name" fields.
 */
public abstract class DictionaryDao extends JdbcDaoSupport {
	
	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the corresponding model object
	 */
	protected abstract class DictionaryItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			NamedItemModel item = createObject();
	    	item.setId(rs.getInt("id"));
	    	item.setName(rs.getString("name"));
			return item;
		}
		
		protected abstract NamedItemModel createObject();
	}
	
	/**
	 * Override this function in descendants to return a correct database table name
	 */
	protected abstract String getTableName();
	
	/**
	 * Override this function in descendants to return the specific object's RowMapper object
	 */
	protected abstract RowMapper getRowMapper();
	
	/**
	 * Composes default insert SQL with the minimal set of fields for a dictionary object.
	 * Override this function in the descendant if it has some other fields  
	 */
	protected String getInsertSql() {
		return "insert into " + getTableName() + " (name) values (?)";
	}
	
	/**
	 * Sets the minimal set of parameters for a dictionary object. 
	 * Override this function in the descendant if it has some other fields to insert.
	 *   
	 * @param st		prepared SQL query statement
	 * @param item		dictionary object 
	 * @throws SQLException
	 */
	protected void setInsertParams(PreparedStatement st, NamedItemModel item) throws SQLException {
		st.setString(1, item.getName());
	}
	
	/**
	 * Composes the default update SQL with the minimal set of fields for a dictionary object.
	 * Override this function in the descendant if it has some other fields  
	 */
	protected String getUpdateSql() {
		return "update  " + getTableName() + " set name=? where id=?";
	}
	
	/**
	 * Returns the minimal set of parameters for an dictionary object update. 
	 * Override this function in the descendant if it has some other fields to update.
	 */
	Object[] getUpdateParams(NamedItemModel item) {
		return new Object[]{item.getName(), item.getId()}; 
	}

	/**
	 * Get the dictionary object by its id
	 * 
	 * @param id	the objects id
	 * @return		the object
	 */
	public NamedItemModel getItem(int id) {
		List<NamedItemModel>items = getJdbcTemplate().query("select * from " + getTableName() + " where id=?", new Integer[]{id}, getRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}
	
	/**
	 * Get the dictionary object by its name
	 * 
	 * @param name	the object's name
	 * @return		the object
	 */
	public NamedItemModel getItem(String name) {
		List<NamedItemModel>items = getJdbcTemplate().query("select * from " + getTableName() + " where name=?", 
				new String[]{name}, getRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}
	
	/**
	 * Returns the field (or the list of fields divided by comma) 
	 */
	protected String getOrder() {
		return "name";
	}
	
	/**
	 * Returns the list of objects selected by the substring from the object's name 
	 */
	public List getItems(String filter) {
		return getJdbcTemplate().query("select * from " + getTableName() + " where name like ? order by " + getOrder() + " ", new String[]{filter}, 
				getRowMapper());
	}

	/**
	 * Returns the given number of objects selected by the substring from the object's name
	 * 
	 * @param filter		the substring from the object's name
	 * @param skip_num	the number of records to skip (usually used to divide list of objects to the set of pages)
	 * @param rows_num	the number of assignments that should be selected
	 */
	public List getItems(String filter, int skip_num, int rows_num) {
		return getJdbcTemplate().query("select * from " + getTableName() + " where name like ? order by " + getOrder() + " limit "+skip_num+","+rows_num, 
				new String[]{filter}, 
				getRowMapper());
	}
	
	/**
	 * Returns the number of objects in the repository that conforms to the given filter.
	 */
	public int countRows(String filter) {
		return getJdbcTemplate().queryForInt("select count(*) from " + getTableName() + " where name like ?", 
				new String[]{filter});
	}

	/**
	 * Updates the data in the database by data from the given dictionary object.
	 * If there is no record for this object in the database the new record will be created.
	 * 
	 * @param item	object to be updated
	 */
	public void updateItem(NamedItemModel item) {
		if(item.getId()==0) {
			try {
				//If the item is new we need to retrieve the autogenerated key and put it to id field  
				Connection conn = getJdbcTemplate().getDataSource().getConnection();
				PreparedStatement st = conn.prepareStatement(getInsertSql());
				setInsertParams(st, item);
				st.execute();
				ResultSet ks = st.getGeneratedKeys();
				ks.next();
				item.setId(ks.getInt(1));
				ks.close();
				st.close();
				conn.close();
			} catch (SQLException e) {				
			}
		} else
			getJdbcTemplate().update(getUpdateSql(), getUpdateParams(item));
	}

	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the object to be deleted
	 */
	public void deleteItem(int id) throws Exception {
		getJdbcTemplate().update("delete from " + getTableName() + " where id=?",
				new Object[]{id});
	}
}
