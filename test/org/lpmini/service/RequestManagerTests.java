package org.lpmini.service;

import java.util.UUID;

import org.lpmini.domain.RequestCategoryArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the RequestManager junit testing class. 
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Jan. 13, 2013
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class RequestManagerTests extends AbstractTransactionalDataSourceSpringContextTests {
	private UUID currentUserId;
	private int  currentUserOwnerAccountId;
	
	private RequestManager requestManager;
	@Autowired
	public void setRequestManager(RequestManager requestManager) {
		this.requestManager = requestManager;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		currentUserId = UUID.fromString("86291951-969e-4b5b-ab37-29a5749544dd"); // sysadmin@logixpath.com, used as the object owner id
		currentUserOwnerAccountId = 1; 	// LogixPath Company, used as the object owner account id
	}
	
	public void testAddAndUpdateRequestCategoryArea() {
		
		RequestCategoryArea reqCategoryAreaData = new RequestCategoryArea();
		reqCategoryAreaData.setCategoryName("Hardware");
		reqCategoryAreaData.setFunctionalAreaName("Server");
		
		// Create the RequestCategoryArea
		RequestCategoryArea retReqCategoryArea = null;
		try {
			retReqCategoryArea = requestManager.createRequestCategoryArea(
					currentUserId, currentUserOwnerAccountId, reqCategoryAreaData);
			assertNotNull(retReqCategoryArea);
			assertEquals(retReqCategoryArea.getCategoryName(), "Hardware");
			assertEquals(retReqCategoryArea.getFunctionalAreaName(), "Server");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Update the RequestCategoryArea
		try {
			retReqCategoryArea.setDescription("Server machines hosting email and file changes");
			
			RequestCategoryArea retReqCategoryAreaUpd = requestManager.updateRequestCategoryArea(
					currentUserId, retReqCategoryArea);
			assertNotNull(retReqCategoryAreaUpd);
			assertEquals(retReqCategoryAreaUpd.getDescription(), "Server machines hosting email and file changes");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Delete the RequestCategoryArea
		try {
			requestManager.deleteRequestCategoryArea(currentUserId, currentUserOwnerAccountId, retReqCategoryArea.getId());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
