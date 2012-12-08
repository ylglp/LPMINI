package org.lpmini.domain;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 * Request is a domain object
 * 
 * Creation date: Dec. 7, 2012
 * Last modify date: Dec. 7, 2012
 * 
 * @author  Yan Linda Guo
 * @version 1.0
 */

public class Request  implements Serializable {

	private static final long serialVersionUID = -3628614813003991805L;
	private long 		id; 	// database generated id
	private long 		requestNumber;
	private String 		status;
	private String		type;
	private String 		subject;
	private String 		description;
	private DateTime 	createdDateTime;	// NOTE: we will learn the Joda datetime manipulation in the future
	
	private RequestCategoryArea area;
	
	// TODO: define class member fields for the remaining fields
	/*
	ProductId 				int, -- REFERENCES Product (Id),
	CategoryAreaId			int REFERENCES RequestCategoryArea (Id),
	OperatingSystem			varchar(50), -- LOV: such as Windows XP, Windows 7, MacOS, iOS
	Browser					varchar(50), -- LOV: such as Internet Explorer 9, Safari 2
	Severity				char(1),
	Probability				char(1),
	RequestSource			varchar(30), -- LOV: such as Web, Email, Phone, Conference, Demo
	Solution				varchar(1024),
	Notes					varchar(1024),
	KnowledgeId				int, -- REFERENCES Knowledge (Id),
	ChangeRequestId			int, -- REFERENCES ChangeRequest (Id),
	ContactId				int, -- REFERENCES Contact (Id),
	AltContactId			int, -- REFERENCES Contact (Id),
	CreatedDateTime 		timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LastModifiedDateTime	timestamp with time zone,
	ClosedDateTime			timestamp with time zone,	
	*/
	private int 	ownerAccountId;
	
	// Constructor
	public Request() {
		area = new RequestCategoryArea();
	}
	
	// TODO: add more input parameters which make sense to a newly created request
	public Request(int ownerAccountId, String status, String type, RequestCategoryArea area,
				String subject, String description) {
		this.setOwnerAccountId(ownerAccountId);
		this.status = status;
		this.type = type;
		this.subject = subject;
		this.description = description;
		this.area = area;
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(DateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	public RequestCategoryArea getArea() {
		return area;
	}
	public void setArea(RequestCategoryArea area) {
		this.area = area;
	}

	public int getOwnerAccountId() {
		return ownerAccountId;
	}

	public void setOwnerAccountId(int ownerAccountId) {
		this.ownerAccountId = ownerAccountId;
	}

	// TODO: generate getting and setting for the remaining fields.
	// TIP: right click the class name in the project explorer panel, invoke menu source -> generate getting and setting ...
}
