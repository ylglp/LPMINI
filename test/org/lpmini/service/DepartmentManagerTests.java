package org.lpmini.service;

import java.util.UUID;

import org.lpmini.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * It is the DepartmentManager junit testing class. 
 * 
 * Creation date: Jan. 18, 2013
 * Last modify date: Jan. 18, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class DepartmentManagerTests extends AbstractTransactionalDataSourceSpringContextTests {
	private UUID currentUserId;
	private int  currentUserOwnerAccountId;
	
	private DepartmentManager departmentManager;
	@Autowired
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] {"classpath:test-context.xml"};
	}
	
	@Override
	protected void onSetUpInTransaction() throws Exception {
		currentUserId = UUID.fromString("86291951-969e-4b5b-ab37-29a5749544ed"); // sysadmin@logixpath.com, used as the object owner id
		currentUserOwnerAccountId = 1; 	// LogixPath Company, used as the object owner account id
	}
	
	public void testAddAndUpdateDepartment() {
		
		Department dept = new Department();
		dept.setName("LPMINI3");
		dept.setDescription("Develop LPMINI3 App");
		dept.setDeptHead(3);
		dept.setParentDeptId(1);
		dept.setOwnerAccountId(1);
		
		// Create the Department
		Department retDept = null;
		try {
			retDept = departmentManager.createDepartment(
					currentUserId, currentUserOwnerAccountId, dept);
			assertNotNull(retDept);
			assertEquals(retDept.getName(), "LPMINI3");
			assertEquals(retDept.getDescription(), "Develop LPMINI3 App");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Update the RequestCategoryArea
		try {
			retDept.setDescription("Develop LPMINI3 schedule change");
			
			Department retDeptUpd = departmentManager.updateDepartment(
					currentUserId, retDept);
			assertNotNull(retDeptUpd);
			assertEquals(retDeptUpd.getDescription(), "Develop LPMINI3 schedule change");
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
		
		// Delete the RequestCategoryArea
		try {
			departmentManager.deleteDepartment(currentUserId, currentUserOwnerAccountId, retDept.getId());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
}
