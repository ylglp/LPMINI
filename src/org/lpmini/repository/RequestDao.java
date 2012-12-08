package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.Request;
import org.lpmini.domain.RequestCategoryArea;
import org.springframework.dao.DuplicateKeyException;

/**
 * RequestDao is the interface for Request related entity's persistence layer
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

public interface RequestDao {
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// RequestCategoryArea related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////

	// get all RequestCategoryArea owned by a specific account id
	public List<RequestCategoryArea> findAllSiteRequestCategoryAreas(int ownerAccountId);
	
	// get a specific RequestCategoryArea by a given id
	public RequestCategoryArea findRequestCategoryAreaById(int id);

	// Add a RequestCategoryArea. Return the generated id
	public int addRequestCategoryArea(RequestCategoryArea reqArea) 
			throws DuplicateKeyException, Exception;
	
	// Save a the changes of an existing RequestCategoryArea object. Return the # of record updated
	public int saveRequestCategoryArea(RequestCategoryArea reqArea) 
			throws DuplicateKeyException, Exception;

	// Delete a RequestCategoryArea object. Return the # of record deleted
	public int deleteRequestCategoryArea(int ownerAccountId, int id) throws Exception;	
	

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Request related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	// get all Request owned by a specific account id
	public List<Request> findAllSiteRequests(int ownerAccountId);
	
	// get a specific RequestCategoryArea by a given id
	public Request findRequestById(long id);

	// Add a Request. Return the generated id
	public long addRequest(Request req) 
			throws DuplicateKeyException, Exception;
	
	// Save a the changes of an existing Request object. Return the # of record updated
	public int saveRequest(Request req) 
			throws DuplicateKeyException, Exception;

	// Delete a Request object. Return the # of record deleted
	public int deleteRequest(int ownerAccountId, long id) throws Exception;	
	
}
