package org.lpmini.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.lpmini.domain.Department;
import org.springframework.dao.DuplicateKeyException;

/**
 * DepartmentManager is the interface for all Department related objects
 * 
 * Creation date:Jan. 13, 2013
 * Last modify date: Jan. 13, 2013
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
public interface DepartmentManager {
	////////////////////////////////////////////
	// Department related methods
	////////////////////////////////////////////
	
	// get all Departments owned by a specific account id
	public List<Department> findAllSiteDepartments(int ownerAccountId);
	
	// get a specific Department by a given id
	public Department findDepartmentById(int id);

	// Create services
	public Department createDepartment(UUID userId, int userOwnerAccountId, Department dept) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Update services
	public Department updateDepartment(UUID userId, Department dept) 
			throws MissingRequiredDataException, InvalidDataValueException, DuplicateKeyException, Exception;

	// Delete services
	public void deleteDepartment(UUID userId, int ownerAccountId, int deptId)
			throws MissingRequiredDataException, Exception;
	
	// Export/import to/from CSV file
	public void exportDepartmentsToCSV(List<Department> depts, OutputStream os)
			throws Exception;
	public List<Department> importDepartmentsFromCSV(UUID userId, int userOwnerAccountId, InputStream is, boolean bOverrideDup)
			throws Exception;
}
