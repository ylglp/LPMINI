package org.lpmini.repository;

import java.util.List;

import org.lpmini.domain.Department;
import org.springframework.dao.DuplicateKeyException;

/**
 * DepartmentDao is the interface for Department related entity's persistence layer
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Jan. 22, 2013
 * 
 * @author  J Stephen Yu
 * @version 1.0
 */

public interface DepartmentDao {

	///////////////////////////////////////////////////////////////////////////////////////////////////
	// Department related methods
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	// get all Department owned by a specific account id
	public List<Department> findAllSiteDepartments(int ownerAccountId);
	
	// get a specific Department by a given id
	public Department findDepartmentById(int id);
	
	// get a specific Department by a given name
	public Department findDepartmentByName(int ownerAccountId, String name);

	// get all Department owned by the same parent department
	public List<Department> findDepartmentsByParent(int ownerAccountId, int parentDeptId);
	
	// Add a Department. Return the generated id
	public int addDepartment(Department dept) 
			throws DuplicateKeyException, Exception;
	
	// Save a the changes of an existing Department object. Return the # of record updated
	public int saveDepartment(Department dept) 
			throws DuplicateKeyException, Exception;

	// Delete a Department object. Return the # of record deleted
	public int deleteDepartment(int ownerAccountId, int id) throws Exception;
	
}
