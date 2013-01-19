package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.Department;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the Department JDBC implementation testing class. 
 * 
 * Creation date: Jan. 18, 2013
 * Last modify date: Jan. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

@SuppressWarnings("deprecation")
public class JdbcDepartmentDaoTests  extends AbstractTransactionalDataSourceSpringContextTests  {

	private DepartmentDao departmentDao;
	public void setRequestDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
	}

	public void testFindDepartments() {
		// Test find all Departments
		List<Department> allDepts = departmentDao.findAllSiteDepartments(1);
		assertNotNull(allDepts);
		assertTrue(allDepts.size() >= 9);
		
		// Test find one specific Department
		Department dept = departmentDao.findDepartmentById(1);
		assertNotNull(dept);
		assertEquals(dept.getId(), 1);
	}
	
	public void testAddUpdateDepartment() {
		// Create Department
		Department dept = new Department("LPMINI", "Develop LPMINI App", 1, 1, 1);
		Department retDept = null;
		try {
			int retId = departmentDao.addDepartment(dept);
			assertTrue(retId > 0);
			retDept = departmentDao.findDepartmentById(retId);
			assertNotNull(retDept);
			assertEquals(retDept.getName(), "LPMINI");
			assertEquals(retDept.getDescription(), "Develop LPMINI App");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Update RequestCategoryArea
		retDept.setName("LPMINI2");
		retDept.setDescription("Develop LPMINI App2");
		retDept.setDeptHead(2);
		try {
			int numRecUpdated = departmentDao.saveDepartment(retDept);
			assertEquals(numRecUpdated, 1);
			Department retDeptUpd = departmentDao.findDepartmentById(retDept.getId());
			assertNotNull(retDeptUpd);
			assertEquals(retDeptUpd.getName(), "LPMINI2");
			assertEquals(retDeptUpd.getDescription(), "Develop LPMINI App2");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Delete Department
		try {
			int numRecDeleted = departmentDao.deleteDepartment(1, retDept.getId());
			assertEquals(numRecDeleted, 1);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
