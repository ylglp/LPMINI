package org.lpmini.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpmini.domain.RequestCategoryArea;
import org.lpmini.repository.RequestDao;
import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * RequestManagerImpl is the implementation of the interface RequestManager for all request related objects, 
 * such as RequestCategoryArea and Request
 * It provides Request CRUD services.
 * CRUD: Create, Read, Update, Delete
 * 
 * Creation date:Jan. 13, 2013
 * Last modify date: Jan. 13, 2013
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
public class RequestManagerImpl implements RequestManager {

	// Logger for this class and subclasses
	//protected final Log logger = LogFactory.getLog(getClass());

	private RequestDao requestDao;	
	@Autowired
	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}
	
	////////////////////////////////////////////
	// RequestCategoryArea related methods
	////////////////////////////////////////////
	
	@Override
	// get all RequestCategoryArea owned by a specific account id
	public List<RequestCategoryArea> findAllSiteRequestCategoryAreas(int ownerAccountId) {
		List<RequestCategoryArea> reqCategoryAreas = requestDao.findAllSiteRequestCategoryAreas(ownerAccountId);
		if (reqCategoryAreas != null && reqCategoryAreas.size() == 0)
			reqCategoryAreas = null;
		return reqCategoryAreas;
	}

	@Override
	// get a specific RequestCategoryArea by a given id
	public RequestCategoryArea findRequestCategoryAreaById(int id) {
		return requestDao.findRequestCategoryAreaById(id);
	}

	// Create services

	private void validateRequestCategoryAreaData(RequestCategoryArea reqCategoryAreaData)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (reqCategoryAreaData.getCategoryName() == null || reqCategoryAreaData.getCategoryName().isEmpty())
			throw new Exception("Missing required CategoryName");
		if (reqCategoryAreaData.getFunctionalAreaName() == null || reqCategoryAreaData.getFunctionalAreaName().isEmpty())
			throw new Exception("Missing required FunctionalAreaName");		
	}
	
	@Override
	public RequestCategoryArea createRequestCategoryArea(UUID userId, int userOwnerAccountId, RequestCategoryArea reqCategoryAreaData)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (userId == null)
			throw new MissingRequiredDataException("Missing userId");
		if (userOwnerAccountId == 0)
			throw new MissingRequiredDataException("Missing userOwnerAccountId");
		
		this.validateRequestCategoryAreaData(reqCategoryAreaData);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		
		// Create the RequestCategoryArea
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);
		reqCategoryAreaData.setOwnerAccountId(userOwnerAccountId);
		
		// Persist the RequestCategoryArea object
		try {
			int retId = requestDao.addRequestCategoryArea(reqCategoryAreaData);
			reqCategoryAreaData.setId(retId);
			
			// retrieve the new data back
			RequestCategoryArea retReqCategoryArea= this.findRequestCategoryAreaById(retId);
			
			return retReqCategoryArea;
		}
		catch (Exception e) {
			//logger.info("RequestManagerImpl.createRequestCategoryArea: fail to create RequestCategoryArea. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	// Update services

	@Override
	public RequestCategoryArea updateRequestCategoryArea(UUID userId, RequestCategoryArea reqCategoryAreaData)
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		if (userId == null)
			throw new MissingRequiredDataException("Missing userId");
		
		this.validateRequestCategoryAreaData(reqCategoryAreaData);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the RequestCategoryArea changes
			//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
			//reqCategoryAreaData.setLastModifiedById(userId);
			
			@SuppressWarnings("unused")
			int numRecordUpdated = requestDao.saveRequestCategoryArea(reqCategoryAreaData);
			
			// retrieve the new data back
			RequestCategoryArea retReqCategoryArea = this.findRequestCategoryAreaById(reqCategoryAreaData.getId());
			
			return retReqCategoryArea;
		}
		catch (Exception e) {
			//logger.info("RequestManagerImpl.updateRequestCategoryArea: fail to update RequestCategoryArea. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	@Override
	// Delete services
	public void deleteRequestCategoryArea(UUID userId, int ownerAccountId, int reqCategoryAreaId) 
			throws MissingRequiredDataException, Exception {
		if (userId == null)
			throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0)
			throw new MissingRequiredDataException("Missing ownerAccountId");
		if (reqCategoryAreaId <= 0)
			throw new MissingRequiredDataException("Missing reqCategoryAreaId");

		int numRecordDeleted = requestDao.deleteRequestCategoryArea(ownerAccountId, reqCategoryAreaId);
		if (numRecordDeleted == 0)
			throw new Exception("Fail to delete RequestCategoryArea: " + reqCategoryAreaId);
	}

	@Override
	// Export/import to/from CSV file
	public void exportReqCategoryAreasToCSV(
			List<RequestCategoryArea> reqCategoryAreas, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<RequestCategoryArea> importReqCategoryAreasFromCSV(UUID userId,
			int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
