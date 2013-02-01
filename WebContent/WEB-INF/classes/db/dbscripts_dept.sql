------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Contact;
DROP TABLE IF EXISTS Account;

------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: Account
CREATE TABLE Account (
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	UniqueId                uuid NOT NULL UNIQUE,
	Name                    varchar(255) NOT NULL
);	

-- Create Table: Contact
CREATE TABLE Contact (
	UniqueId                uuid NOT NULL PRIMARY KEY,
	FirstName               varchar(80) NOT NULL,
	LastName                varchar(80) NOT NULL,
	Email                   varchar(255) NOT NULL
);

-- Create Table: Employee
CREATE TABLE Employee (
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	EmployeeNumber          int NOT NULL,
	ContactUuid             uuid NOT NULL REFERENCES Contact (UniqueId)
);	

-- Create Table: Department
CREATE TABLE Department (
	Id                      serial NOT NULL PRIMARY KEY UNIQUE,
	Name                    varchar(255) NOT NULL,
	Description             varchar(512),
	DeptHead                int REFERENCES Employee (Id),
	ParentDeptId            int REFERENCES Department (Id),
	OwnerAccountId          int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, Name)
);

------------------------------------------------------------------------------
-- Load testing data
------------------------------------------------------------------------------
-- Load Account
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D10', 'LogixPath LLP');
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D11', 'Allen Company');
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D12', 'Bobby Company');
INSERT INTO Account(UniqueId, Name)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D13', 'Cindy Company');

-- Load Contact
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D20', 'Allen', 'Alaph', 'allen.alpha@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D21', 'Adam', 'Alaph', 'adam.alpha@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D22', 'Alex', 'Alaph', 'alex.alpha@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D30', 'Bobby', 'Beta', 'bobby.beta@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D31', 'Bill', 'Beta', 'bill.beta@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D32', 'Beth', 'Beta', 'beth.beta@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D40', 'Cindy', 'Gamma', 'cindy.gamma@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D41', 'Carl', 'Gamma', 'carl.gamma@lptest.com');
INSERT INTO Contact(UniqueId, FirstName, LastName, Email)
	VALUES('07771AE4-236A-49d3-A49E-B1F9E1934D42', 'Cate', 'Gamma', 'cate.gamma@lptest.com');

-- Load Employee
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('100', '07771AE4-236A-49d3-A49E-B1F9E1934D20');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('101', '07771AE4-236A-49d3-A49E-B1F9E1934D21');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('102', '07771AE4-236A-49d3-A49E-B1F9E1934D22');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('200', '07771AE4-236A-49d3-A49E-B1F9E1934D30');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('201', '07771AE4-236A-49d3-A49E-B1F9E1934D31');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('202', '07771AE4-236A-49d3-A49E-B1F9E1934D32');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('300', '07771AE4-236A-49d3-A49E-B1F9E1934D40');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('301', '07771AE4-236A-49d3-A49E-B1F9E1934D41');
INSERT INTO Employee(EmployeeNumber, ContactUuid)
	VALUES('302', '07771AE4-236A-49d3-A49E-B1F9E1934D42');

-- Load Department
INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Higher Education',
               'Design university curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='100'),
               null,
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Higher Education Support',
               'Support the design of university curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='101'),
               (SELECT Id FROM Department WHERE Name='Higher Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Higher Education Logistics',
               'Support the logistics of university curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='102'),
               (SELECT Id FROM Department WHERE Name='Higher Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Secondary Education',
               'Design middle school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='200'),
               (SELECT Id FROM Department WHERE Name='Higher Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Secondary Education Support',
               'Support the design middle school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='201'),
               (SELECT Id FROM Department WHERE Name='Secondary Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Secondary Education Logistics',
               'Support the logistics of middle school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='202'),
               (SELECT Id FROM Department WHERE Name='Secondary Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Elementary Edcuation',
               'Design primary school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='300'),
               (SELECT Id FROM Department WHERE Name='Secondary Education'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Elementary Edcuation Support',
               'Support the design primary school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='301'),
               (SELECT Id FROM Department WHERE Name='Elementary Edcuation'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Department (Name, Description, DeptHead, ParentDeptId, OwnerAccountId)
	VALUES('Elementary Edcuation Logistics',
               'Support the logistics of primary school curricular',
               (SELECT Id FROM Employee WHERE EmployeeNumber='302'),
               (SELECT Id FROM Department WHERE Name='Elementary Edcuation'),
               (SELECT Id FROM Account WHERE Name='Allen Company'));
