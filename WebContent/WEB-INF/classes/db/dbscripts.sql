------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: ListOfValue
CREATE TABLE ListOfValue (
	LOVType			varchar(30) NOT NULL,
	LocaleString	varchar(10) NOT NULL,
	Key				varchar(64) NOT NULL,
	StringValue		varchar(256),
	DecimalValue	decimal(19,4),
	DisplayOrder	int,
	Notes			varchar(128),
	UNIQUE (LOVType, LocaleString, Key)
);
CREATE INDEX LOV_TYPE_indx ON ListOfValue(LOVType);

-- Load testing data: 
INSERT INTO ListOfValue(LOVType, LocaleString, Key, StringValue)
	VALUES('TestLOVType1', 'en_us', '1', 'One');
INSERT INTO ListOfValue(LOVType, LocaleString, Key, StringValue)
	VALUES('TestLOVType1', 'en_us', '2', 'Two');
INSERT INTO ListOfValue(LOVType, LocaleString, Key, StringValue)
	VALUES('TestLOVType1', 'en_us', '3', 'Three');
	
INSERT INTO ListOfValue(LOVType, LocaleString, Key, DecimalValue)
	VALUES('TestLOVType2', 'en_us', '1', 1);
INSERT INTO ListOfValue(LOVType, LocaleString, Key, DecimalValue)
	VALUES('TestLOVType2', 'en_us', '2', 2);
INSERT INTO ListOfValue(LOVType, LocaleString, Key, DecimalValue)
	VALUES('TestLOVType2', 'en_us', '3', 3);

-- Create Table: Account
CREATE TABLE Account (
	Id 					serial NOT NULL PRIMARY KEY UNIQUE,
	UniqueId 			uuid NOT NULL UNIQUE,
	Name				varchar(255) NOT NULL
);	
	
-- Create Table: Contact
CREATE TABLE Contact (
	UniqueId 			uuid NOT NULL PRIMARY KEY,
	FirstName			varchar(80) NOT NULL,
	LastName			varchar(80) NOT NULL,
	Email				varchar(255) NOT NULL
);
	
-- Create Table: RequestCategoryArea
CREATE TABLE RequestCategoryArea (
	Id 							serial NOT NULL PRIMARY KEY UNIQUE,
	CategoryName				varchar(128) NOT NULL,	-- OwnerAccountSpecific value such as Hardware, Enterprise Application, Customer Software 1, Network
	FunctionalAreaName			varchar(128) NOT NULL,	
	Description					varchar(512),
	OwnerAccountId				int,
	UNIQUE (OwnerAccountId, CategoryName, FunctionalAreaName)
);

-- Load testing data: 
INSERT INTO RequestCategoryArea(CategoryName, FunctionalAreaName, Description, OwnerAccountId)
			VALUES('Enterprise Application', 'ERP', 'ERP application', 1);
INSERT INTO RequestCategoryArea(CategoryName, FunctionalAreaName, Description, OwnerAccountId)
			VALUES('Enterprise Application', 'CRM', 'CRM application', 1);
INSERT INTO RequestCategoryArea(CategoryName, FunctionalAreaName, Description, OwnerAccountId)
			VALUES('Network', 'Email', 'Email System', 1);

-- Create Table: Request
CREATE TABLE Request (
	Id 						bigserial NOT NULL PRIMARY KEY UNIQUE,
	RequestNumber			bigint NOT NULL,
	Status					varchar(30) NOT NULL default 'Created', -- LOV: such as Created, Open, Closed
	Type					varchar(30) NOT NULL, -- LOV: such as Problem, Suggestion, Question
	ProductId 				int, -- REFERENCES Product (Id),
	CategoryAreaId			int REFERENCES RequestCategoryArea (Id),
	OperatingSystem			varchar(50), -- LOV: such as Windows XP, Windows 7, MacOS, iOS
	Browser					varchar(50), -- LOV: such as Internet Explorer 9, Safari 2
	Severity				char(1),
	Probability				char(1),
	RequestSource			varchar(30), -- LOV: such as Web, Email, Phone, Conference, Demo
	Subject					varchar(128) NOT NULL,
	Description				varchar(1024) NOT NULL,
	Solution				varchar(1024),
	Notes					varchar(1024),
	KnowledgeId				int, -- REFERENCES Knowledge (Id),
	ChangeRequestId			int, -- REFERENCES ChangeRequest (Id),
	ContactId				int, -- REFERENCES Contact (Id),
	AltContactId			int, -- REFERENCES Contact (Id),
	CreatedDateTime 		timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LastModifiedDateTime	timestamp with time zone,
	ClosedDateTime			timestamp with time zone,	
	OwnerAccountId			int,
	UNIQUE (OwnerAccountId, RequestNumber)
);
CREATE INDEX Req_ReqNum_indx ON Request(OwnerAccountId, RequestNumber);
CREATE INDEX Req_ProdId_indx ON Request(OwnerAccountId, ProductId);
CREATE INDEX Req_ContactId_indx ON Request(OwnerAccountId, ContactId);

-- Load testing data: 
INSERT INTO Request(RequestNumber, Status, Type, CategoryAreaId, OperatingSystem, Browser, Severity, Subject, Description, ContactId, CreatedDateTime, OwnerAccountId)
			VALUES(1, 'Created', 'Problem', 
					(SELECT Id FROM RequestCategoryArea WHERE OwnerAccountId=1 AND CategoryName='Enterprise Application' AND FunctionalAreaName='ERP'),
					'Windows 7', 'Internet Explorer 9', 2,
					'Fail to create purchase order',
					'I am not able to create purchase order.',
					10, CURRENT_TIMESTAMP - interval '2 day',
					1);
INSERT INTO Request(RequestNumber, Status, Type, CategoryAreaId, OperatingSystem, Browser, Severity, Subject, Description, ContactId, CreatedDateTime, OwnerAccountId)
			VALUES(2, 'Created', 'Problem', 
					(SELECT Id FROM RequestCategoryArea WHERE OwnerAccountId=1 AND CategoryName='Enterprise Application' AND FunctionalAreaName='CRM'),
					'Windows 7', 'Internet Explorer 9', 2,
					'System cannot send out email',
					'I created a service request, however, I havenot received the confirmation email yet.',
					10, CURRENT_TIMESTAMP - interval '1 day',
					1);
INSERT INTO Request(RequestNumber, Status, Type, CategoryAreaId, OperatingSystem, Browser, Severity, Subject, Description, ContactId, CreatedDateTime, OwnerAccountId)
			VALUES(3, 'Created', 'Suggestion', 
					(SELECT Id FROM RequestCategoryArea WHERE OwnerAccountId=1 AND CategoryName='Enterprise Application' AND FunctionalAreaName='ERP'),
					null, null, 4,
					'ROI analysis',
					'I believe ROI calculation and analysis feature is critical to a business.',
					20, CURRENT_TIMESTAMP - interval '7 day',
					1);

					
------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
--DROP TABLE IF EXISTS Request;
--DROP TABLE IF EXISTS RequestCategoryArea;
--DROP TABLE IF EXISTS Department;
--DROP TABLE IF EXISTS ListOfValue;

					