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
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

@Repository
public class JdbcDepartmentDao implements DepartmentDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertDepartment;
	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertDepartment = new SimpleJdbcInsert(dataSource).withTableName("RequestCategoryArea").usingGeneratedKeyColumns("id");
	}
	
	// o: the main object: this RequestCategoryArea; 
	protected final static String fieldSelectionForReadRequestCategoryArea =
			"o.Id,o.CategoryName,o.FunctionalAreaName,o.Description,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRequestCategoryArea = 
			"CategoryName=:CategoryName,FunctionalAreaName=:FunctionalAreaName,Description=:Description";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Department related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class DepartmentMapper implements RowMapper<Department> {
		
		public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Department dept = new Department();
			
			dept.setId(rs.getInt("Id"));
			dept.setName(rs.getString("Name"));
			dept.setDescription(rs.getString("Description"));
			dept.setParentDeptId(rs.getInt("ParentDeptId"));
			dept.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			
			return dept;
		}
	}
	
	// get all Departments owned by a specific account id
	@Override
	public List<Department> findAllSiteDepartments(int ownerAccountId) {

		/*
		try {
			String strQuery = "select " + fieldSelectionForReadRequestCategoryArea + 
					" from RequestCategoryArea as o where OwnerAccountId=:OwnerAccountId " + 
					" order by o.CategoryName, o.FunctionalAreaName";
			List<RequestCategoryArea> depts = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new RequestCategoryAreaMapper());
			return depts;
		}
		catch (Exception e) {
			System.out.println("JdbcRequestDao.findAllSiteRequestCategoryAreas Exception: " + e.getMessage());
			return null;
		}
		*/
		return null;
	}

	// get a specific Department by a given id
	@Override
	public Department findDepartmentById(int id) {
		/*
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadRequestCategoryArea);
			sbQuery.append(" from RequestCategoryArea as o");
			sbQuery.append(" WHERE o.Id = :Id;");

			RequestCategoryArea area = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("Id", id),
					new RequestCategoryAreaMapper()); 
			
			return area;
		} 
		catch (Exception e) {
			System.out.println("JdbcRequestDao.findRequestCategoryAreaById Exception: " + e.getMessage());
			return null;
		}
		*/
		return null;
	}

	// get a specific Department by a given name
	@Override
	public Department findDepartmentByName(int ownerAccountId, String name) {
		/*
		try {
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ");
			sbQuery.append(fieldSelectionForReadRequestCategoryArea);
			sbQuery.append(" from RequestCategoryArea as o");
			sbQuery.append(" WHERE o.Id = :Id;");

			RequestCategoryArea area = namedParameterJdbcTemplate.queryForObject(
					sbQuery.toString(),
					new MapSqlParameterSource().addValue("Id", id),
					new RequestCategoryAreaMapper()); 
			
			return area;
		} 
		catch (Exception e) {
			System.out.println("JdbcRequestDao.findRequestCategoryAreaById Exception: " + e.getMessage());
			return null;
		}
		*/
		return null;
	}
	
	// get all Departments owned by a parent Department
	@Override
	public List<Department> findDepartmentsByParent(int parentDeptId) {

		/*
		try {
			String strQuery = "select " + fieldSelectionForReadRequestCategoryArea + 
					" from RequestCategoryArea as o where OwnerAccountId=:OwnerAccountId " + 
					" order by o.CategoryName, o.FunctionalAreaName";
			List<RequestCategoryArea> depts = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new RequestCategoryAreaMapper());
			return depts;
		}
		catch (Exception e) {
			System.out.println("JdbcRequestDao.findAllSiteRequestCategoryAreas Exception: " + e.getMessage());
			return null;
		}
		*/
		return null;
	}

	/**
	 * Set SQL Parameters used for creating PurchaseOrder header and updating PurchaseOrder
	 * @param purchaseOrder
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getRequestCategoryAreaMapSqlParameterSource(Department dept, boolean bNew) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		if (!bNew)
			parameters.addValue("Id", dept.getId());	// auto generated when insert a RequestCategoryArea, use it as the primary key when update it
		parameters.addValue("Name", dept.getName());
		parameters.addValue("Description", dept.getDescription());
		parameters.addValue("ParentDeptId", dept.getParentDeptId());
		parameters.addValue("OwnerAccountId", dept.getOwnerAccountId());	// auto generated when insert a RequestCategoryArea, use it as the primary key when update it
		return parameters;
	}
	
	// Add a Department. Return the generated id
	@Override
	public long addDepartment(Department dept) 
			throws DuplicateKeyException, Exception {
		
		if (dept == null)
			throw new Exception("Missing input dept");
		
		MapSqlParameterSource parameters = this.getRequestCategoryAreaMapSqlParameterSource(dept, true);	
		try {
			// insert RequestCategoryArea record
			int retId = insertDepartment.executeAndReturnKey(parameters).intValue();
			dept.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRequestDao.addDepartment Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRequestDao.addDepartment Exception: " + e2.getMessage());
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
					"update RequestCategoryArea set " + fieldSetForUpdateRequestCategoryArea + " where Id=:Id;",
					getRequestCategoryAreaMapSqlParameterSource(dept, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRequestDao.saveDepartment Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRequestDao.saveDepartment Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a Department object. Return the # of record deleted
	@Override
	public int deleteDepartment(int ownerAccountId, int id)	
			throws Exception {
		if (id <= 0 || ownerAccountId < 0)
			return 0;
		try {
			int numRecDeleted = namedParameterJdbcTemplate.update(
					"delete from RequestCategoryArea where Id=:Id and OwnerAccountId=:OwnerAccountId", 
					new MapSqlParameterSource().addValue("Id", id).addValue("OwnerAccountId", ownerAccountId));
			return numRecDeleted;
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
