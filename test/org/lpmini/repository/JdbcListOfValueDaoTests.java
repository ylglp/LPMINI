package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.ListOfValue;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the JdbcListOfValueDao JDBC implementation testing class. 
 * 
 * Creation date: Nov. 14, 2012
 * Last modify date: Nov. 14, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

@SuppressWarnings("deprecation") 
public class JdbcListOfValueDaoTests extends AbstractTransactionalDataSourceSpringContextTests  {
	
	private ListOfValueDao listOfValueDao;
	public void setListOfValueDao(ListOfValueDao listOfValueDao) {
		this.listOfValueDao = listOfValueDao;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testGetLOVs() {
		// Test get all LOVs
		List<ListOfValue> allLOVs = listOfValueDao.getAllListOfValues();
		assertNotNull(allLOVs);
		assertTrue(allLOVs.size() >= 6);	// we inserted at least 6 values
	}
}
