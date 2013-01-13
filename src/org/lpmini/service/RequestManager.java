package org.lpmini.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.lpmini.domain.RequestCategoryArea;
import org.springframework.dao.DuplicateKeyException;

/**
 * RequestManager is the interface for all request related objects, such as RequestCategoryArea and Request
 * 
 * Creation date:Jan. 13, 2013
 * Last modify date: Jan. 13, 2013
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
public interface RequestManager {
	////////////////////////////////////////////
	// RequestCategoryArea related methods
	////////////////////////////////////////////
	
	// get all RequestCategoryArea owned by a specific account id
	public List<RequestCategoryArea> findAllSiteRequestCategoryAreas(int ownerAccountId);
	
	// get a specific RequestCategoryArea by a given id
	public RequestCategoryArea findRequestCategoryAreaById(int id);

	// Create services
	public RequestCategoryArea createRequestCategoryArea(UUID userId, int userOwnerAccountId, RequestCategoryArea reqCategoryAreaData) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public RequestCategoryArea updateRequestCategoryArea(UUID userId, RequestCategoryArea reqCategoryAreaData) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public void deleteRequestCategoryArea(UUID userId, int ownerAccountId, int reqCategoryAreaId)
			throws MissingRequiredDataException, Exception;
	
	// Export/import to/from CSV file
	public void exportReqCategoryAreasToCSV(List<RequestCategoryArea> reqCategoryAreas, OutputStream os)
			throws Exception;
	public List<RequestCategoryArea> importReqCategoryAreasFromCSV(UUID userId, int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;
}
