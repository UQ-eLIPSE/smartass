/* This file is part of SmartAss and contains the UsersDao class - the object-relational mapping class 
 * that maps the UsersItemModel to the database table(s).
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import au.edu.uq.smartass.web.UserItemModel;

/**
 * The UsersDao class - the object-relational mapping class 
 * that maps the UsersItemModel to the database table(s).
 */
public class UsersDao extends JdbcDaoSupport {
	
	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link FilesItemModel} object
	 */
	class ItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserItemModel item = new UserItemModel();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setFullname(rs.getString("fullname"));
			item.setPassword(rs.getString("password"));
			item.setPlace(rs.getString("place"));
			item.setDescription(rs.getString("description"));
			item.setEmail(rs.getString("email"));
			item.setDate_registered(rs.getDate("date_registered"));
			item.setAssignments_num(rs.getInt("assignments_num"));
			item.setRights(rs.getInt("rights"));
			item.setRowsPerPage(rs.getInt("rowsperpage"));
			
			return item;
		}
	}
	
	/**
	 * Get the UserItemModel object by its id
	 * 
	 * @param id	the object's id
	 * @return		the object
	 */
	public UserItemModel getItem(int id) {
		List<UserItemModel> items = 
			getJdbcTemplate().query("select * from web_users where id=?", new Integer[]{id}, new ItemRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	/**
	 * Get the UserItemModel object by its name
	 * 
	 * @param name	the object's name
	 * @return		the object
	 */
	public UserItemModel getItem(String name) {
		List<UserItemModel> items = 
			getJdbcTemplate().query("select * from web_users where name=?", new String[]{name}, new ItemRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	/**
	 * Get the FilesItemModel object by its name and password (used for user authentication)
	 * 
	 * @param name	the user's name
	 * @param passwd	the user's password
	 * @return		the object
	 */
	public UserItemModel getItem(String name, String passwd) {
		List<UserItemModel> items = 
			getJdbcTemplate().query("select * from web_users where name=? and password=?", new String[]{name, passwd}, new ItemRowMapper());
		if(items.size()>0)
			return items.get(0);
		return null;
	}
	
	/**
	 * Returns the list of UserItemModel objects filtered by user's name
	 * 
	 * @param name	the users's name
	 * @return		the list of UserItemModel objects filtered by user's name
	 */
	public List<UserItemModel> getItems(String name) {
		return getJdbcTemplate().query("select * from web_users where name like ? order by name", new String[]{"%"+name+"%"}, new ItemRowMapper());
	}

	/**
	 * Returns the list of UserItemModel object filtered by user's name
	 * 
	 * @param name	the users's name
	 * @param skip	the number of records to skip
	 * @param show	the number of records to show
	 * @return		the list of UserItemModel objects
	 */
	public List<UserItemModel> getItems(String name, int skip, int show ) {
		return getJdbcTemplate().query("select * skip " + skip + " next " + show +  
				"from web_users where name like ? order by name", new String[]{"%"+name+"%"}, new ItemRowMapper());
	}
	
	/**
	 * Adds the new user to the database
	 * 
	 * @param user
	 */
	public void addUser(UserItemModel user) {
		getJdbcTemplate().update("insert into web_users (name, fullname, description, place, date_registered, assignments_num, password, email, rights, rowsperpage) " +
				"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				new Object[]{user.getName(), user.getFullname(), user.getDescription(), user.getPlace(), user.getDate_registered(),
				user.getAssignments_num(), user.getPassword(), user.getEmail(), user.getRights(), user.getRowsPerPage()});
		
	}

	/**
	 * Updates the data in the database by the data of {@link UserItemModel} object
	 * @param user
	 */
	public void updateUser(UserItemModel user) {
		getJdbcTemplate().update("update web_users set name=?, fullname=?, description=?, place=?, " +
				"date_registered=?, assignments_num=?, password=?, email=?, rights=?, rowsperpage=? where id=? ", 
				new Object[]{user.getName(), user.getFullname(), user.getDescription(), user.getPlace(), user.getDate_registered(),
				user.getAssignments_num(), user.getPassword(), user.getEmail(), user.getRights(), user.getRowsPerPage(),
				user.getId()});
		user.setId(getJdbcTemplate().queryForInt("select id from web_users where name=?", new Object[]{user.getName()}));
	}
	
	/**
	 * Deletes the record from the database
	 * 
	 * @param id	id of the object to be deleted
	 */
	public void deleteUser(int id) {
		getJdbcTemplate().update("delete from web_users where id=? ", new Object[]{id});
	}
}
