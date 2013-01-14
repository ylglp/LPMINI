------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Contact;
DROP TABLE IF EXISTS Account;

------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: Account
CREATE TABLE Account (
	Id                     serial NOT NULL PRIMARY KEY UNIQUE,
	UniqueId               uuid NOT NULL UNIQUE,
	Name                   varchar(255) NOT NULL
);	
	
-- Create Table: Contact
CREATE TABLE Contact (
	UniqueId               uuid NOT NULL PRIMARY KEY,
	FirstName              varchar(80) NOT NULL,
	LastName               varchar(80) NOT NULL,
	Email                  varchar(255) NOT NULL
);

-- Create Table: Department
CREATE TABLE Department (
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	Name                    varchar(255) NOT NULL,
	Description             varchar(512),
	DeptHead                uuid REFERENCES Contact (UniqueId),
	ParentDeptId            int REFERENCES Department (Id),
	OwnerAccountId          int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, Name)
);

------------------------------------------------------------------------------
-- Load testing data
------------------------------------------------------------------------------
-- Load Account
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D10', 'Allen Company');
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D11', 'Bobby Company');
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D12', 'Cindy Company');

-- Load Contact
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D20', 'Allen', 'Alaph', 'allen.alpha@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D21', 'Bobby', 'Beta', 'bobby.beta@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D22', 'Cindy', 'Gamma', 'cindy.gamma@lptest.com');

-- Load Department
INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Higher Education',
               'Design university curricular',
               (SELECT UniqueId FROM Contact WHERE Email='allen.alpha@lptest.com'),
               null,
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Secondary Education',
               'Design middle school curricular',
               (SELECT UniqueId FROM Contact WHERE Email='bobby.beta@lptest.com'),
               (SELECT Id FROM Department WHERE Name='Higher Education'),
               (SELECT Id FROM Account WHERE Name='Bobby Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Elementary Edcuation',
               'Design primary school curricular',
               (SELECT UniqueId FROM Contact WHERE Email='cindy.gamma@lptest.com'),
               (SELECT Id FROM Department WHERE Name='Secondary Education'),
               (SELECT Id FROM Account WHERE Name='Cindy Company'));
