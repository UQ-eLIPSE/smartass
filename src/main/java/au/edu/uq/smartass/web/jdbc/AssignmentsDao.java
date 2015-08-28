/* This file is part of SmartAss and contains the AssignmentsDao class - the database access class for an assignment.
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
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import au.edu.uq.smartass.web.AssignmentsItemModel;
import au.edu.uq.smartass.web.composer.AssignmentConstruct;

/**
 * The AssignmentsDao class is the database access class for an assignment.
 *
 */
public class AssignmentsDao extends JdbcDaoSupport {
	ItemRowMapper mapper;
	UsersDao usersDao;
	
	/**
	 * The ItemRowMapper class is the service class that maps data stored in the 
	 * relational database table(s) to the {@link AssignmentsItemModel} object
	 */
	class ItemRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			AssignmentsItemModel item = new AssignmentsItemModel();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setDtcreated(rs.getDate("dtcreated"));
			item.setUser(usersDao.getItem(rs.getInt("userid")));
			return item;
		}
	}
	
	private List<AssignmentsItemModel> select(String sql, Object[] params, int skip_num, int rows_num) {
		if(mapper==null)
			mapper = new ItemRowMapper();
		return (List<AssignmentsItemModel>) getJdbcTemplate().query(sql + " LIMIT " + skip_num + "," + rows_num,
				params, (RowMapper) mapper);
	}
	
	private List<AssignmentsItemModel> select(String sql, Object[] params) {
		if(mapper==null)
			mapper = new ItemRowMapper();
		return (List<AssignmentsItemModel>) getJdbcTemplate().query(sql, params, (RowMapper) mapper);
	}
	
	/**
	 * Returns the list of latest added to the repository assignment.
	 *   
	 * @param number		the number of assignments that should be selected
	 * @param skip_num		the number of assignments to skip (usually used to divide list of assignments to 
	 * 						a set of pages in the web interface)  
	 * @return				the list of latest added to the repository assignments
	 */
	public List<AssignmentsItemModel> selectLatest(int number, int skip_num) {
		return select("select * from web_assignments order by id desc ", null,  skip_num, number);
	}
	
	/**
	 * Returns the total number of assignments in the repository
	 */
	public int countRows() {
		return getJdbcTemplate().queryForInt("select count(*) from web_assignments");
	}
	
	
	/**
	 * Returns the list of assignments that conforms to the filter defined by values passed to this function
	 * 
	 * @param byname	the substring from an assignment's name 
	 * @param byuser	the substring from a users's name
	 * @param bytags	search an assignment by tags 
	 * @param skip_num	the number of assignments to skip (usually used to divide list of assignments to a set of pages in the web interface)
	 * @param rows_num	the number of assignments that should be selected
	 * 
	 * @return			the list of assignments that conforms to the filter
	 */
	public List<AssignmentsItemModel> select(String byname, String byuser, String bytags, int skip_num, int rows_num) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = composeSql("*", byname, byuser, bytags, params) + " order by a.name";
		if(skip_num>=0)
			return select(sql, params.toArray(), skip_num, rows_num);
		return select(sql, params.toArray());
	}
	
	/**
	 * Returns the number of assignments in the repository that conforms to the filter defined by values passed to this function
	 * 
	 * @param byname	the substring from an assignment's name 
	 * @param byuser	the substring from a users's name
	 * @param bytags	search an assignment by tags
	 */
	public int countRows(String byname, String byuser, String bytags) {
		ArrayList<Object> params = new ArrayList<Object>();
		String sql = composeSql("count(*)", byname, byuser, bytags, params) + " order by a.name";
		return getJdbcTemplate().queryForInt(sql, params.toArray());
	}
	
	/**
	 * Composes SQL for given filter conditions
	 *  
	 * @param select	the basic part of SELECT query
	 * @param byname	the substring from an assignment's name 
	 * @param byuser	the substring from a users's name
	 * @param bytags	search an assignment by tags
	 * @param params	other parameters
	 * @return
	 */
	private String composeSql(String select, String byname, String byuser, String bytags, List<Object> params) {
		String where = "";
		String tables = " web_assignments a ";
		//= "select * from web_assignments order by name ";
		if(byname!=null && byname.length()>0) {
			where = where + " a.name like ? ";
			params.add("%" + byname + "%");
		}
		if(byuser!=null && byuser.length()>0) {
			tables = tables +", web_users u";
			if(where.length()>0)
				where = where + " and ";
			where = where + " a.userid=u.id and u.name like ? ";
			params.add("%" + byuser + "%");
		}
		if(bytags!=null && bytags.length()>0) {
			//TODO: tags parsing and querying
		}
		String sql = "select " + select + " from " + tables;
		if(where.length()>0)
			sql = sql + " where " + where;
		return sql;
	}
	
	/**
	 * Returns the list of assignments filtered by user
	 * 
	 * @param userid		user's id
	 * @param skip_num	the number of assignments to skip (usually used to divide list of assignments to the substring from an assignment's name
	 * @param rows_num	the number of assignments that should be selected
	 * 
	 * @return	the list of assignments filtered by user
	 */
	public List<AssignmentsItemModel> selectByUser(int userid, int skip_num, int rows_num) {
		return select("select * from web_assignments where userid=? order by name limit " + skip_num + "," + rows_num, 
				new Object[]{userid});
	}
	
	/**
	 * Returns the number of assignments in the repository that belongs to the user
	 *  
	 * @param userid	user's id
	 * @return	the number of assignments in the repository that belongs to the user
	 */
	public int countRows(int userid) {
		return getJdbcTemplate().queryForInt("select count(*) from web_assignments where userid=?", 
				new Object[]{userid});
	}
	
	/**
	 * Returns the single {@link AssignmentsItemModel} selected by its id
	 * 
	 * @param id	assignment's id
	 * 
	 * @return	the assignment 
	 */
	public AssignmentsItemModel getItem(int id) {
		List<AssignmentsItemModel> items = select("select * from web_assignments where id=?",
				new Object[]{id});
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	/**
	 * Returns the single assignment selected by name
	 *  
	 * @param name		the assignment's name
	 * @return			the assignment
	 */
	public AssignmentsItemModel getItem(String name) {
		List<AssignmentsItemModel> items = select("select * from web_assignments where name=?",
				new Object[]{name});
		if(items.size()>0)
			return items.get(0);
		return null;
	}

	/**
	 * Updates data in the database by data from the given {@link AssignmentsItemModel} object.
	 * If there is no record for this assignment in the database the new record will be created.
	 * 
	 * @param item	assignment to be updated
	 */
	public void updateItem(AssignmentsItemModel item) {
		if(item.getId()==0) {
			try {
				//If module is new we need to retrieve autogenerated key and put it to id field  
				Connection conn = getJdbcTemplate().getDataSource().getConnection();
				PreparedStatement st = conn.prepareStatement(
						"insert into web_assignments (name, description, userid, dtcreated) values (?, ?, ?, CURDATE())");
				st.setString(1, item.getName());
				st.setString(2, item.getDescription());
				st.setInt(3, item.getUser().getId());
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
			getJdbcTemplate().update("update web_assignments set name=?, description=?, userid=? where id=?",
					new Object[]{item.getName(), item.getDescription(), item.getUser().getId(), item.getId()});
	}
	
	/**
	 * Deletes assignment record from the database
	 * 
	 * @param id	id of assignment to be deleted
	 */
	public void deleteItem(int id) {
		getJdbcTemplate().update("delete from web_assignments where id=?", new Object[]{id});
	}
	
	/**
	 * The setter for the usersDao
	 */
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
}
