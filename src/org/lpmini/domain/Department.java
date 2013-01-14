package org.lpmini.domain;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 * Department is a domain object
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

public class Department implements Serializable {

	private static final long serialVersionUID = 4211824501050120552L;
	private int 		id; 	// database generated id
	private String 		name;
	private String 		description;
	private int			parentDeptId;
	private int 		ownerAccountId;
	private DateTime 	createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	
	/*
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	Name                    varchar(255) NOT NULL,
	Description             varchar(512),
	DeptHead                uuid REFERENCES Contact (UniqueId),
	ParentDeptId            int REFERENCES Department (Id),
	OwnerAccountId          int REFERENCES Account (Id),
	*/
	
	// Constructor
	public Department() {
	}
	
	// Constructor
	public Department(String name, String description, int parentDeptId, int ownerAccountId) {
		this.name = name;
		this.setDescription(description);
		this.parentDeptId = parentDeptId;
		this.ownerAccountId = ownerAccountId;
	}	
	
	public int getId() {
		return id;
	}
	public int setId(int id) {
		return this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getParentDeptId() {
		return parentDeptId;
	}
	public void setParentDeptId(int parentDeptId) {
		this.parentDeptId = parentDeptId;
	}
	public int getOwnerAccountId() {
		return ownerAccountId;
	}
	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}
	
	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(DateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	// TODO: generate getting and setting for the remaining fields.
	// TIP: right click the class name in the project explorer panel, invoke menu source -> generate getting and setting ...
}
