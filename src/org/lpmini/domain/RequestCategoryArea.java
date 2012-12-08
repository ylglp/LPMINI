package org.lpmini.domain;

import java.io.Serializable;

/**
 * RequestCategoryArea is a domain object
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

public class RequestCategoryArea  implements Serializable {
	
	private static final long serialVersionUID = 1673765446128452395L;
	private int 	id;		// database generated id
	private String 	categoryName;
	private String 	functionalAreaName;
	private String 	description;
	private int 	ownerAccountId;
	
	// Constructor
	public RequestCategoryArea() {		
	}
	
	public RequestCategoryArea(int ownerAccountId, String categoryName, String functionalAreaName, String description) {
		this.ownerAccountId = ownerAccountId;
		this.categoryName = categoryName;
		this.functionalAreaName = functionalAreaName;
		this.description = description;
	}	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getFunctionalAreaName() {
		return functionalAreaName;
	}
	public void setFunctionalAreaName(String functionalAreaName) {
		this.functionalAreaName = functionalAreaName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOwnerAccountId() {
		return ownerAccountId;
	}
	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}
	
}
