package org.lpmini.web;

import java.util.List;
import java.util.UUID;

import org.lpmini.util.InvalidDataValueException;
import org.lpmini.util.MissingRequiredDataException;
import org.lpmini.domain.RequestCategoryArea;
import org.lpmini.service.RequestManager;
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
 * It is a controller class for Request related actions
 * 
 * Creation date: Jan. 13, 2013
 * Last modify date: Jan. 13, 2013
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */
@Controller
@RequestMapping("/request/*")
public class RequestController {
	
	private RequestManager requestManager;	
	@Autowired
	public void setRequestManager(RequestManager requestManager) {
		this.requestManager = requestManager;
	}

	/**
	 * List all RequestCategoryAreas owned by my company
	 * @param model
	 */
	@RequestMapping(value = "catarea/listallsp", method = RequestMethod.GET)
	public String listAllSiteRequestCategoryAreas(Model model) {
		
		/*
		String permissionCheckResult = checkPermission("ListAllSiteRequestCategoryAreas");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		// retrieve RequestCategoryAreas owned by this site
		int useOwnerAccountId = 1;
		List<RequestCategoryArea> reqCategoryAreas = requestManager.findAllSiteRequestCategoryAreas(useOwnerAccountId);
		
		model.addAttribute("requestCategoryAreas", reqCategoryAreas);		
		
		return "reqcatarea_listall";
	}
	
	/**
	 * Add New RequestCategoryArea form
	 * @param RequestCategoryArea: reqCatArea
	 * @param bindResult
	 * @param model
	 */
	@RequestMapping(value = "catarea/addform", method = RequestMethod.GET)
	public String addRequestCategoryAreaForm(
			@ModelAttribute("reqCatArea") RequestCategoryArea reqCatArea,
			BindingResult bindResult,
			Model model) {
		
		/*
		String permissionCheckResult = checkPermission("AddRequestCategoryArea");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		//addRequestCategoryAreaFormAttributes(model);		
		return "reqcatarea_addform";		
	}
	
	/**
	 * Add New RequestCategoryArea action
	 * 
	 * @param RequestCategoryArea: reqCatArea
	 * @param bindResult: holds RequestCategoryArea data validation errors
	 * @param model: holds the RequestCategoryArea created
	 * @return Success or error view
	 */
	@RequestMapping(value = "catarea/add", method = RequestMethod.POST)
	public String addRequestCategoryArea(
			@ModelAttribute("reqCatArea") RequestCategoryArea reqCatArea,
			BindingResult bindResult,
			Model model) {
			
		final String funcName = "RequestController.addRequestCategoryArea";
			
		/*
		String permissionCheckResult = checkPermission("AddRequestCategoryArea");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		boolean isFailed = false;
		RequestCategoryArea createdReqCatArea = null;
		
		// Create RequestCategoryArea object
		try {
			//UUID currentUserUUID = userSessionData.getUseruuid();
			//int currentUserOwnerAccountId = userSessionData.getOwnerAccountId();
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc390");
			int currentUserOwnerAccountId = 1;
			
			createdReqCatArea = requestManager.createRequestCategoryArea(
					currentUserUUID, currentUserOwnerAccountId, reqCatArea);
			if (createdReqCatArea == null) {
				throw new Exception("Fail to create RequestCategoryArea object");
			}
		}
		catch (MissingRequiredDataException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			if (reqCatArea.getCategoryName() == null || reqCatArea.getCategoryName().isEmpty())
				bindResult.rejectValue("categoryName", "error.not-specified", null, "Value is not specified");
			else if (reqCatArea.getFunctionalAreaName() == null || reqCatArea.getFunctionalAreaName().isEmpty())
				bindResult.rejectValue("categoryName", "error.not-specified", null, "Value is not specified");
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
			bindResult.rejectValue("id", "error.dup", null, "This category name and functional area name already exists");
			isFailed = true;			
		}
		catch (Exception e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.failcreateobj", new Object[]{e.getMessage()}, "Fail to Create Object");
			isFailed = true;
		}
		if (!isFailed && createdReqCatArea != null) {
			return "redirect:listallsp";		// goto view ./listallsp
		}
		else {
			return "reqcatarea_addform";		// goto JPS view reqcatarea_addform.jsp
		}
	}
	
	/**
	 * Edit RequestCategoryArea action
	 * 
	 * @param id: id of the RequestCategoryArea
	 * @param RequestCategoryArea: reqCatArea
	 * @param bindResult: holds RequestCategoryArea data validation errors
	 * @param model: holds the RequestCategoryArea created
	 * @return Success or error view
	 */
	@RequestMapping(value = "catarea/editform", method = RequestMethod.POST)
	public String editRequestCategoryAreaForm(
			@RequestParam("id") int id,
			@ModelAttribute("reqCatArea") RequestCategoryArea reqCatArea,
			BindingResult bindResult,
			Model model) {
		
		final String funcName = "RequestController.editRequestCategoryAreaForm";
		
		/*
		String permissionCheckResult = checkPermission("EditRequestCategoryArea");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		
		try {
			reqCatArea = requestManager.findRequestCategoryAreaById(id);
			if (reqCatArea == null)
				throw new Exception("Not about to find RequestCategoryArea with id " + id);
			
			/*
			if (!checkAuthorization(reqCategoryArea.getOwnerAccountId())) {				
				logger.error(funcName + ": user " + userSessionData.getUsername() + " from ownerAccountId " + 
						userSessionData.getOwnerAccountId() + " attempts to edit RequestCategoryArea with OwnerAccountId " + 
						reqCategoryArea.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			
			model.addAttribute("reqCatArea", reqCatArea);	
			// addRequestCategoryAreaFormAttributes(model);			
		}
		catch (Exception e) {
			//logger.error(funcName + ": " + e.getMessage());
		}
		return "reqcatarea_editform";
	}
	
	/**
	 * Edit Product action
	 * 
	 * @param RequestCategoryArea: reqCatArea
	 * @param bindResult: holds Product data validation errors
	 * @param model: holds the Product created
	 * @return Success or error view
	 */
	@RequestMapping(value = "catarea/edit", method = RequestMethod.POST)
	public String editProduct(
			@ModelAttribute("reqCatArea") RequestCategoryArea reqCatArea,
			BindingResult bindResult,
			Model model) {
		
		final String funcName = "RequestController.editRequestCategoryArea";
		
		/*
		String permissionCheckResult = checkPermission("EditRequestCategoryArea");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		boolean isFailed = false;
		RequestCategoryArea updatedReqCatArea = null;
		
		// Update RequestCategoryArea object
		try {
			RequestCategoryArea existingReqCatArea = requestManager.findRequestCategoryAreaById(reqCatArea.getId());
			if (existingReqCatArea == null)
				throw new Exception("Not about to find RequestCategoryArea with id " + reqCatArea.getId());
			
			/*
			if (!checkAuthorization(existingReqCatArea.getOwnerAccountId())) {				
				logger.error(funcName + ": user " + userSessionData.getUsername() + 
						" from ownerAccountId " + userSessionData.getOwnerAccountId() + 
						" attempts to modify RequestCategoryArea with OwnerAccountId " + existingReqCatArea.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc390");
			int currentUserOwnerAccountId = 1;
			updatedReqCatArea = requestManager.updateRequestCategoryArea(currentUserUUID, reqCatArea);
			if (updatedReqCatArea == null) {
				throw new Exception("Fail to update RequestCategoryArea");
			}
		}
		catch (MissingRequiredDataException e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			if (reqCatArea.getCategoryName() == null || reqCatArea.getCategoryName().isEmpty())
				bindResult.rejectValue("categoryName", "error.not-specified", null, "Value is not specified");
			else if (reqCatArea.getFunctionalAreaName() == null || reqCatArea.getFunctionalAreaName().isEmpty())
				bindResult.rejectValue("categoryName", "error.not-specified", null, "Value is not specified");
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
			bindResult.rejectValue("id", "error.dup", null, "This category name and functional area name already exists");
			isFailed = true;			
		}
		catch (Exception e) {
			System.out.println(funcName + ": Exception - " + e.getMessage());
			bindResult.rejectValue("id", "error.failcreateobj", new Object[]{e.getMessage()}, "Fail to Create Object");
			isFailed = true;
		}
		if (!isFailed && updatedReqCatArea != null) {
			return "redirect:listallsp";		// goto view ./listallsp
		}
		else {
			return "reqcatarea_editform";		// goto JPS view reqcatarea_addform.jsp
		}
	}
	
	
	
	/**
	 * Delete a RequestCategoryArea 
	 * @param id: id of the product 
	 */
	@RequestMapping(value = "catarea/del", method = RequestMethod.POST)
	public String deleteRequestCategoryArea(@RequestParam("id") int id) {
		
		/*
		String permissionCheckResult = checkPermission("DeleteRequestCategoryArea");
		if (permissionCheckResult != null)
			return permissionCheckResult;
		*/
		try {
			RequestCategoryArea reqCategoryArea = requestManager.findRequestCategoryAreaById(id);
			if (reqCategoryArea == null)
				throw new Exception("Not about to find RequestCategoryArea with id " + id);
			
			/*
			if (!checkAuthorization(reqCategoryArea.getOwnerAccountId())) {				
				logger.error("ProductController.delete: user " + userSessionData.getUsername() + " from ownerAccountId " + 
						userSessionData.getOwnerAccountId() + " attempts to delete RequestCategoryArea with OwnerAccountId " + 
						reqCategoryArea.getOwnerAccountId());
				return "notauthorized";
			}
			*/
			
			UUID currentUserUUID = UUID.fromString("8f095c83-52bf-4e12-bf4f-0dfaca1cc390");
			int currentUserOwnerAccountId = 1;
			requestManager.deleteRequestCategoryArea(currentUserUUID, currentUserOwnerAccountId, id);
						
			return "redirect:listallsp";
		}
		catch (Exception e) {
			//logger.error("RequestController.delete: " + e.getMessage());
			return "redirect:listallsp";
		}
	}		
	
	
}
