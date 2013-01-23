package org.lpmini.web;

import java.util.List;
import java.util.UUID;

import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.lpmini.domain.Department;
import org.lpmini.service.DepartmentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * It is a controller class for Department related actions
 * 
 * Creation date: Jan. 18, 2013
 * Last modify date: Jan. 18, 2013
 * 
 * @author  J. Stephen Yu
 * @version 1.0
 */
@Controller
@RequestMapping("/department/*")
public class DepartmentController {
	
	private DepartmentManager departmentManager;	
	@Autowired
	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}


	/**
	 * List all Departments owned by the company
	 * @param model
	 */
	@RequestMapping(value = "listall", method = RequestMethod.GET)
	public String listAllSiteDepartments(Model model) {
		
		/*
		String permissionCheckResult = checkPermission("ListAllSiteDepartments");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		// retrieve Departments owned by this site
		int useOwnerAccountId = 1;
		List<Department> depts = departmentManager.findAllSiteDepartments(useOwnerAccountId);
		
		model.addAttribute("departments", depts);
		
		return "department_listall";
	}
	
	/**
	 * Add new Department form
	 * @param Department: dept
	 * @param bindResult
	 * @param model
	 */
	@RequestMapping(value = "addform", method = RequestMethod.GET)
	public String addDepartmentForm(
			@ModelAttribute("department") Department dept,
			BindingResult bindResult,
			Model model) {

		/*
		String permissionCheckResult = checkPermission("addDepartmentForm");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		//addDepartmentFormAttributes(model);
		return "department_addform";
	}
	
	/**
	 * Add new Department action
	 * 
	 * @param Department: dept
	 * @param bindResult: holds Department data validation errors
	 * @param model: holds the Department created
	 * @return Success or error view
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addDepartment(
			@ModelAttribute("department") Department dept,
			BindingResult bindResult,
			Model model) {
			
		final String funcName = "DepartmentController.addDepartment";
			
		/*
		String permissionCheckResult = checkPermission("addDepartment");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		boolean isFailed = false;
		Department createdDept = null;
		
		// Create Department object
		try {
			//UUID currentUserUUID = userSessionData.getUseruuid();
			//int currentUserOwnerAccountId = userSessionData.getOwnerAccountId();
			
			// Note the UUID is not needed - it's a dummy
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc3a0");
			int currentUserOwnerAccountId = 1;
			
			createdDept = departmentManager.createDepartment(
					currentUserUUID, currentUserOwnerAccountId, dept);
			if (dept == null) {
				throw new Exception("Fail to create Department object");
			}
		}
		catch (MissingRequiredDataException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			if (dept.getName() == null || dept.getName().isEmpty())
				bindResult.rejectValue("Name", "error.not-specified", null, "Value is not specified");
			else if (dept.getDeptHead() <= 0)
				bindResult.rejectValue("DeptHead", "error.not-specified", null, "Value is less than or equal to zero");
			else if (dept.getParentDeptId() <= 0)
				bindResult.rejectValue("ParentDeptId", "error.not-specified", null, "Value is less than or equal to zero");
			else if (dept.getOwnerAccountId() <= 0)
				bindResult.rejectValue("OwnerAccountId", "error.not-specified", null, "Value is less than or equal to zero");
			else
				bindResult.rejectValue("id", "error.not-specified", null, "Value is not specified");
			isFailed = true;
		}
		catch (InvalidDataValueException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.invalidinput", new Object[]{e.getMessage()}, "Invalid input");
			isFailed = true;			
		}
		catch (DuplicateKeyException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.dup", null, "This department name already exists");
			isFailed = true;			
		}
		catch (Exception e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.failcreateobj", new Object[]{e.getMessage()}, "Fail to Create Object");
			isFailed = true;
		}
		if (!isFailed && createdDept != null) {
			return "redirect:listall";		// goto view ./listall
		}
		else {
			return "department_addform";	// goto JPS view department_addform.jsp
		}
	}
	
	/**
	 * Edit Department action
	 * 
	 * @param id: id of the Department
	 * @param Department: dept
	 * @param bindResult: holds Department data validation errors
	 * @param model: holds the Department created
	 * @return Success or error view
	 */
	@RequestMapping(value = "editform", method = RequestMethod.POST)
	public String editDepartmentForm(
			@RequestParam("id") int id,
			@ModelAttribute("department") Department dept,
			BindingResult bindResult,
			Model model) {
		
		final String funcName = "DepartmentController.editDepartmentForm";
		
		/*
		String permissionCheckResult = checkPermission("editDepartmentForm");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		try {
			dept = departmentManager.findDepartmentById(id);
			if (dept == null)
				throw new Exception("Not able to find Department with id " + id);
			
			/*
			if (!checkAuthorization(dept.getOwnerAccountId())) {				
				logger.error(funcName + ": user " + userSessionData.getUsername() + " from ownerAccountId " + 
						userSessionData.getOwnerAccountId() + " attempts to edit Department with OwnerAccountId " + 
						dept.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			
			model.addAttribute("department", dept);	
			// addDepartmentFormAttributes(model);			
		}
		catch (Exception e) {
			//logger.error(funcName + ": " + e.getMessage());
		}
		return "department_editform";
	}
	
	/**
	 * Edit Product action
	 * 
	 * @param Department: dept
	 * @param bindResult: holds Product data validation errors
	 * @param model: holds the Product created
	 * @return Success or error view
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editProduct(
			@ModelAttribute("department") Department dept,
			BindingResult bindResult,
			Model model) {
		
		final String funcName = "DepartmentController.editDepartment";
		
		/*
		String permissionCheckResult = checkPermission("EditDepartment");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		boolean isFailed = false;
		Department updatedDept = null;
		
		// Update Department object
		try {
			Department existingDept = departmentManager.findDepartmentById(dept.getId());
			if (existingDept == null)
				throw new Exception("Not able to find Department with id " + dept.getId());
			
			/*
			if (!checkAuthorization(existingDept.getOwnerAccountId())) {				
				logger.error(funcName + ": user " + userSessionData.getUsername() + 
						" from ownerAccountId " + userSessionData.getOwnerAccountId() + 
						" attempts to modify Department with OwnerAccountId " + existingDept.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc3a0");
			int currentUserOwnerAccountId = 1;
			updatedDept = departmentManager.updateDepartment(currentUserUUID, dept);
			if (updatedDept == null) {
				throw new Exception("Fail to update Department");
			}
		}
		catch (MissingRequiredDataException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			if (dept.getName() == null || dept.getName().isEmpty())
				bindResult.rejectValue("Name", "error.not-specified", null, "Value is not specified");
			else if (dept.getDeptHead() <= 0)
				bindResult.rejectValue("DeptHead", "error.not-specified", null, "Value is less than or equal to zero");
			else if (dept.getParentDeptId() <= 0)
				bindResult.rejectValue("ParentDeptId", "error.not-specified", null, "Value is less than or equal to zero");
			else if (dept.getOwnerAccountId() <= 0)
				bindResult.rejectValue("OwnerAccountId", "error.not-specified", null, "Value is less than or equal to zero");
			else
				bindResult.rejectValue("id", "error.not-specified", null, "Value is not specified");
			isFailed = true;
		}
		catch (InvalidDataValueException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.invalidinput", new Object[]{e.getMessage()}, "Invalid input");
			isFailed = true;			
		}
		catch (DuplicateKeyException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.dup", null, "This department name already exists");
			isFailed = true;			
		}
		catch (Exception e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.failcreateobj", new Object[]{e.getMessage()}, "Fail to Create Object");
			isFailed = true;
		}
		if (!isFailed && updatedDept != null) {
			return "redirect:listall";		// goto view ./listall
		}
		else {
			return "department_editform";		// goto JPS view department_editform.jsp
		}
	}
	
	/**
	 * Delete a Department 
	 * @param id: id of the Department 
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public String deleteDepartment(@RequestParam("id") int id) {
		
		/*
		String permissionCheckResult = checkPermission("deleteDepartment");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		try {
			Department dept = departmentManager.findDepartmentById(id);
			if (dept == null)
				throw new Exception("Not able to find Department with id " + id);
			
			/*
			if (!checkAuthorization(dept.getOwnerAccountId())) {				
				logger.error("ProductController.delete: user " + userSessionData.getUsername() + " from ownerAccountId " + 
						userSessionData.getOwnerAccountId() + " attempts to delete Department with OwnerAccountId " + 
						dept.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc3a0");
			int currentUserOwnerAccountId = 1;
			departmentManager.deleteDepartment(currentUserUUID, currentUserOwnerAccountId, id);

			return "redirect:listall";
		}
		catch (Exception e) {
			//logger.error("RequestController.delete: " + e.getMessage());
			return "redirect:listall";
		}
	}

	/**
	 * List Department by parent department action
	 * 
	 * @param parentId: id of the parent department
	 * @param model: holds the Department created
	 * @return Success or error view
	 */
	@RequestMapping(value = "listbyparent", method = RequestMethod.POST)
	public String listDepartmentsbyParentDept(
			@RequestParam("parentId") int parentId,
			Model model) {
		
		/*
		String permissionCheckResult = checkPermission("listDepartmentsbyParentDept");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		// retrieve Departments owned by this site and have the same parent department
		int useOwnerAccountId = 1;
		List<Department> depts = departmentManager.findAllSiteDepartmentsByParentDept(useOwnerAccountId, parentId);

		model.addAttribute("departments", depts);
		
		return "department_listbyparent";
	}

}
