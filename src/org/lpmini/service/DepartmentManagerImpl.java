package org.lpmini.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.lpmini.domain.Department;
import org.lpmini.repository.DepartmentDao;
import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

/**
 * DepartmentManagerImpl is the implementation of the interface DepartmentManager for all Department related objects. 
 * It provides Request CRUD services.
 * CRUD: Create, Read, Update, Delete
 * 
 * Creation date:Jan. 13, 2013
 * Last modify date: Jan. 22, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */
public class DepartmentManagerImpl implements DepartmentManager {

	// Logger for this class and subclasses
	//protected final Log logger = LogFactory.getLog(getClass());

	private DepartmentDao departmentDao;
	@Autowired
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	////////////////////////////////////////////
	// Department related methods
	////////////////////////////////////////////
	
	@Override
	// get all Departments owned by a specific account id
	public List<Department> findAllSiteDepartments(int ownerAccountId) {
		List<Department> depts = departmentDao.findAllSiteDepartments(ownerAccountId);
		if (depts != null && depts.size() == 0)
			depts = null;
		return depts;
	}

	@Override
	// get a specific Department by a given id
	public Department findDepartmentById(int id) {
		return departmentDao.findDepartmentById(id);
	}

	@Override
	// get a specific Department by a given name
	public Department findDepartmentByName(int ownerAccountId, String name) {
		return departmentDao.findDepartmentByName(ownerAccountId, name);
	}

	@Override
	// get all Departments owned by a specific account id and have the same parent department
	public List<Department> findAllSiteDepartmentsByParentDept(int ownerAccountId, int parentDeptId) {
		List<Department> depts = departmentDao.findDepartmentsByParent(ownerAccountId, parentDeptId);
		if (depts != null && depts.size() == 0)
			depts = null;
		return depts;
	}

	// Create services

	private void validateDepartmentData(Department dept)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		if (dept.getName() == null || dept.getName().isEmpty())
			throw new Exception("Missing required Name");
	}
	
	@Override
	public Department createDepartment(UUID userId, int userOwnerAccountId, Department dept)
			throws MissingRequiredDataException, InvalidDataValueException,
					DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (userOwnerAccountId == 0)
			throw new MissingRequiredDataException("Missing userOwnerAccountId");
		
		this.validateDepartmentData(dept);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		
		// Create the Department
		//reqCategoryAreaData.setCreatedDate(currentDateTime);
		//reqCategoryAreaData.setCreatedById(userId);
		//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
		//reqCategoryAreaData.setLastModifiedById(userId);
		//reqCategoryAreaData.setOwnerId(userId);
		dept.setOwnerAccountId(userOwnerAccountId);
		
		// Persist the Department object
		try {
			int retId = departmentDao.addDepartment(dept);
			dept.setId(retId);
			
			// retrieve the new data back
			Department retDept = this.findDepartmentById(retId);
			
			return retDept;
		}
		catch (Exception e) {
			//logger.info("DepartmentManagerImpl.createDepartment: fail to create Department. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	// Update services

	@Override
	public Department updateDepartment(UUID userId, Department dept)
			throws MissingRequiredDataException, InvalidDataValueException,
				DuplicateKeyException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		
		this.validateDepartmentData(dept);
		
		// Good to go
		//DateTime currentDateTime = DateTime.now();		
		try {
			// Persist the Department changes
			//reqCategoryAreaData.setLastModifiedDate(currentDateTime);
			//reqCategoryAreaData.setLastModifiedById(userId);
			
			@SuppressWarnings("unused")
			int numRecordUpdated = departmentDao.saveDepartment(dept);
			
			// retrieve the new data back
			Department retDept = this.findDepartmentById(dept.getId());
			
			return retDept;
		}
		catch (Exception e) {
			//logger.info("DepartmentManagerImpl.updateDepartment: fail to update Department. Exception: " + e.getMessage());
			throw e;
		}
		
	}

	// Delete services

	@Override
	public void deleteDepartment(UUID userId, int ownerAccountId, int deptId) 
			throws MissingRequiredDataException, Exception {
		//if (userId == null)
		//	throw new MissingRequiredDataException("Missing userId");
		if (ownerAccountId <= 0)
			throw new MissingRequiredDataException("Missing ownerAccountId");
		if (deptId <= 0)
			throw new MissingRequiredDataException("Missing deptId");

		int numRecordDeleted = departmentDao.deleteDepartment(ownerAccountId, deptId);
		if (numRecordDeleted == 0)
			throw new Exception("Fail to delete Department: " + deptId);
	}

	// Export/import to/from CSV file

	@Override
	public void exportDepartmentsToCSV(List<Department> depts, OutputStream os)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Department> importDepartmentsFromCSV(UUID userId,
			int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
