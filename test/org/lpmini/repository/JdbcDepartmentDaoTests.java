package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.RequestCategoryArea;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the Department JDBC implementation testing class. 
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcDepartmentDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private RequestDao requestDao;
	public void setRequestDao(RequestDao requestDao) {
		this.requestDao = requestDao;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindRequestCategoryAreas() {
		// Test find all RequestCategoryAreas
		List<RequestCategoryArea> allAreas = requestDao.findAllSiteRequestCategoryAreas(1);
		assertNotNull(allAreas);
		assertTrue(allAreas.size() >= 3);
		
		// Test find one specific RequestCategoryArea
		RequestCategoryArea area = requestDao.findRequestCategoryAreaById(1);
		assertNotNull(area);
		assertEquals(area.getId(), 1);
	}
	
	public void testAddUpdateRequestCategoryArea() {
		// Create RequestCategoryArea
		RequestCategoryArea area = new RequestCategoryArea(1, "Network", "Internet Access", "Internet access from internal network");
		RequestCategoryArea retArea = null;
		try {
			int retId = requestDao.addRequestCategoryArea(area);
			assertTrue(retId > 0);
			retArea = requestDao.findRequestCategoryAreaById(retId);
			assertNotNull(retArea);
			assertEquals(retArea.getCategoryName(), "Network");
			assertEquals(retArea.getFunctionalAreaName(), "Internet Access");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Update RequestCategoryArea
		retArea.setCategoryName("Network Ext");
		retArea.setFunctionalAreaName("External Internet Access");
		retArea.setDescription("Acess external internet from intranet");
		try {
			int numRecUpdated = requestDao.saveRequestCategoryArea(retArea);
			assertEquals(numRecUpdated, 1);
			RequestCategoryArea retAreaUpd = requestDao.findRequestCategoryAreaById(retArea.getId());
			assertNotNull(retAreaUpd);
			assertEquals(retAreaUpd.getCategoryName(), "Network Ext");
			assertEquals(retAreaUpd.getFunctionalAreaName(), "External Internet Access");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Delete RequestCategoryArea
		try {
			int numRecDeleted = requestDao.deleteRequestCategoryArea(1, retArea.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
