package org.lpmini.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpmini.domain.Department;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcDepartmentDao is the JDBC implementation of the DepartmentDao for Department related entity's persistence layer
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Jan. 22, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@Repository
public class JdbcDepartmentDao implements DepartmentDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertDepartment;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertDepartment = new SimpleJdbcInsert(dataSource).withTableName("Department").usingGeneratedKeyColumns("id");
	}
	
	// o: the main object: this Department; 
	protected final static String fieldSelectionForReadDepartment =
			"o.Id,o.Name,o.Description,o.DeptHead,o.ParentDeptId,o.OwnerAccountId";

	protected final static String fieldSetForUpdateDepartment = 
			"Name=:Name,Description=:Description,DeptHead=:DeptHead,ParentDeptId=:ParentDeptId,OwnerAccountId=:OwnerAccountId";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Department related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class DepartmentMapper implements RowMapper<Department> {
		
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Department dept = new Department();
			
			dept.setId(rs.getInt("Id"));
			dept.setName(rs.getString("Name"));
			dept.setDescription(rs.getString("Description"));
			dept.setDeptHead(rs.getInt("DeptHead"));
			dept.setParentDeptId(rs.getInt("ParentDeptId"));
			dept.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			
			return dept;
		}
	}
	
	// get all Departments owned by a specific account id
	@Override
	public List<Department> findAllSiteDepartments(int ownerAccountId) {

		try {
			String strQuery = "select " + fieldSelectionForReadDepartment + 
					" from Department as o where OwnerAccountId=:OwnerAccountId " + 
					" order by o.Id";
			List<Department> depts = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new DepartmentMapper());
			return depts;
		}
		catch (Exception e) {
			System.out.println("JdbcDepartmentDao.findAllSiteDepartments Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Department by a given id
	@Override
	public Department findDepartmentById(int id) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadDepartment);
			sbQuery.append(" from Department as o");
			sbQuery.append(" WHERE o.Id = :Id;");

			Department dept = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("Id", id),
					new DepartmentMapper());
			
			return dept;
		} 
		catch (Exception e) {
			System.out.println("JdbcDepartmentDao.findDepartmentById Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific Department by a given name
	@Override
	public Department findDepartmentByName(int ownerAccountId, String name) {
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadDepartment);
			sbQuery.append(" from Department as o");
			sbQuery.append(" WHERE o.OwnerAccountId = :OwnerAccountId and o.Name = :Name order by o.Id;");

			Department dept = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("Name", name),
					new DepartmentMapper());
			
			return dept;
		} 
		catch (Exception e) {
			System.out.println("JdbcDepartmentDao.findDepartmentByName Exception: " + e.getMessage());
			return null;
		}
	}
	
	// get all Departments owned by a parent Department
	@Override
	public List<Department> findDepartmentsByParent(int ownerAccountId, int parentDeptId) {

		try {
			String strQuery = "select " + fieldSelectionForReadDepartment + 
					" from Department as o where o.OwnerAccountId = :OwnerAccountId and o.ParentDeptId = :ParentDeptId " + 
					" order by o.Id";
			List<Department> depts = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId).addValue("ParentDeptId", parentDeptId),
					new DepartmentMapper());
			return depts;
		}
		catch (Exception e) {
			System.out.println("JdbcDepartmentDao.findDepartmentsByParent Exception: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Set SQL Parameters used for creating Department
	 * @param dept
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getDepartmentMapSqlParameterSource(Department dept, boolean bNew) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		if (!bNew)
			parameters.addValue("Id", dept.getId());	// auto generated when insert a Department, use it as the primary key when update it
		parameters.addValue("Name", dept.getName());
		parameters.addValue("Description", dept.getDescription());
		parameters.addValue("DeptHead", dept.getDeptHead());
		parameters.addValue("ParentDeptId", dept.getParentDeptId());
		parameters.addValue("OwnerAccountId", dept.getOwnerAccountId());
		return parameters;
	}
	
	// Add a Department. Return the generated id
	@Override
	public int addDepartment(Department dept) 
			throws DuplicateKeyException, Exception {
		
		if (dept == null)
			throw new Exception("Missing input dept");
		
		MapSqlParameterSource parameters = this.getDepartmentMapSqlParameterSource(dept, true);	
		try {
			// insert Department record
			int retId = insertDepartment.executeAndReturnKey(parameters).intValue();
			dept.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcDepartmentDao.addDepartment Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcDepartmentDao.addDepartment Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save the changes of an existing Department object. Return the # of record updated
	@Override
	public int saveDepartment(Department dept) 
			throws DuplicateKeyException, Exception {
		if (dept == null)
			throw new Exception("Missing input dept");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update Department set " + fieldSetForUpdateDepartment + " where Id=:Id;",
					getDepartmentMapSqlParameterSource(dept, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcDepartmentDao.saveDepartment Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcDepartmentDao.saveDepartment Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a Department object. Return the # of record deleted
	@Override
	public int deleteDepartment(int ownerAccountId, int id)	
			throws Exception {
		if (ownerAccountId < 0 || id <= 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from Department where Id=:Id and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
