package org.lpmini.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.lpmini.domain.Request;
import org.lpmini.domain.RequestCategoryArea;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 * JdbcRequestDao is the JDBC implementation of the RequestDao for Request related entity's persistence layer
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

@Repository
public class JdbcRequestDao implements RequestDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertRequestCategoryArea;
	@SuppressWarnings("unused")
	private SimpleJdbcInsert insertRequest;

	public void setDataSource(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		insertRequestCategoryArea = new SimpleJdbcInsert(dataSource).withTableName("RequestCategoryArea").usingGeneratedKeyColumns("id");
		insertRequest = new SimpleJdbcInsert(dataSource).withTableName("Request").usingGeneratedKeyColumns("id");
	}
	
	// o: the main object: this RequestCategoryArea; 
	protected final static String fieldSelectionForReadRequestCategoryArea =
			"o.Id,o.CategoryName,o.FunctionalAreaName,o.Description,o.OwnerAccountId";

	protected final static String fieldSetForUpdateRequestCategoryArea = 
			"CategoryName=:CategoryName,FunctionalAreaName=:FunctionalAreaName,Description=:Description";
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// RequestCategoryArea related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private static class RequestCategoryAreaMapper implements RowMapper<RequestCategoryArea> {
		
		public RequestCategoryArea mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			RequestCategoryArea area = new RequestCategoryArea();
			
			area.setId(rs.getInt("Id"));
			area.setCategoryName(rs.getString("CategoryName"));
			area.setFunctionalAreaName(rs.getString("FunctionalAreaName"));
			area.setDescription(rs.getString("Description"));
			area.setOwnerAccountId(rs.getInt("OwnerAccountId"));
			
			return area;
		}
	}
	
	// get all RequestCategoryArea owned by a specific account id
	@Override
	public List<RequestCategoryArea> findAllSiteRequestCategoryAreas(int ownerAccountId) {
		
		try {
			String strQuery = "select " + fieldSelectionForReadRequestCategoryArea + 
					" from RequestCategoryArea as o where OwnerAccountId=:OwnerAccountId " + 
					" order by o.CategoryName, o.FunctionalAreaName";
			List<RequestCategoryArea> areas = namedParameterJdbcTemplate.query(
					strQuery,
					new MapSqlParameterSource().addValue("OwnerAccountId", ownerAccountId),
					new RequestCategoryAreaMapper());				
			return areas;
		}
		catch (Exception e) {
			System.out.println("JdbcRequestDao.findAllSiteRequestCategoryAreas Exception: " + e.getMessage());
			return null;
		}
	}

	// get a specific RequestCategoryArea by a given id
	@Override
	public RequestCategoryArea findRequestCategoryAreaById(int id) {
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
	}

	/**
	 * Set SQL Parameters used for creating PurchaseOrder header and updating PurchaseOrder
	 * @param purchaseOrder
	 * @param bNew
	 * @return
	 */
	private MapSqlParameterSource getRequestCategoryAreaMapSqlParameterSource(RequestCategoryArea area, boolean bNew) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();

		if (!bNew)
			parameters.addValue("Id", area.getId());	// auto generated when insert a RequestCategoryArea, use it as the primary key when update it
		parameters.addValue("CategoryName", area.getCategoryName());
		parameters.addValue("FunctionalAreaName", area.getFunctionalAreaName());
		parameters.addValue("Description", area.getDescription());
		parameters.addValue("OwnerAccountId", area.getOwnerAccountId());	// auto generated when insert a RequestCategoryArea, use it as the primary key when update it
		return parameters;
	}
	
	// Add a RequestCategoryArea. Return the generated id
	@Override
	public int addRequestCategoryArea(RequestCategoryArea reqArea)
			throws DuplicateKeyException, Exception {
		
		if (reqArea == null)
			throw new Exception("Missing input reqArea");
		
		MapSqlParameterSource parameters = this.getRequestCategoryAreaMapSqlParameterSource(reqArea, true);	
		try {
			// insert RequestCategoryArea record
			int retId = insertRequestCategoryArea.executeAndReturnKey(parameters).intValue();
			reqArea.setId(retId);
			return retId;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRequestDao.addRequestCategoryArea Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRequestDao.addRequestCategoryArea Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Save a the changes of an existing RequestCategoryArea object. Return the # of record updated
	@Override
	public int saveRequestCategoryArea(RequestCategoryArea reqArea)
			throws DuplicateKeyException, Exception {
		if (reqArea == null)
			throw new Exception("Missing input reqArea");
		try {
			int numRecUpdated = namedParameterJdbcTemplate.update(
					"update RequestCategoryArea set " + fieldSetForUpdateRequestCategoryArea + " where Id=:Id;",
					getRequestCategoryAreaMapSqlParameterSource(reqArea, false));
			return numRecUpdated;
		}
		catch (DuplicateKeyException e1) {
			System.out.println("JdbcRequestDao.saveRequestCategoryArea Exception: " + e1.getMessage());
			throw e1;
		}
		catch (Exception e2) {
			System.out.println("JdbcRequestDao.saveRequestCategoryArea Exception: " + e2.getMessage());
			throw e2;
		}
	}

	// Delete a RequestCategoryArea object. Return the # of record deleted
	@Override
	public int deleteRequestCategoryArea(int ownerAccountId, int id)
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

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Request related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	// get all Request owned by a specific account id
	@Override
	public List<Request> findAllSiteRequests(int ownerAccountId) {
		// TODO Auto-generated method stub
		return null;
	}

	// get a specific RequestCategoryArea by a given id
	@Override
	public Request findRequestById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	// Add a Request. Return the generated id
	@Override
	public long addRequest(Request req) throws DuplicateKeyException, Exception {
		// TODO Auto-generated method stub
		// 	long retId = insertRequest.executeAndReturnKey(parameters).longValue();

		return 0;
	}

	// Save a the changes of an existing Request object. Return the # of record updated
	@Override
	public int saveRequest(Request req) throws DuplicateKeyException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	// Delete a Request object. Return the # of record deleted
	@Override
	public int deleteRequest(int ownerAccountId, long id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
