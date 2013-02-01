------------------------------------------------------------------------------
-- DROP TABLEs
------------------------------------------------------------------------------
DROP TABLE IF EXISTS AccessControlList;
DROP TABLE IF EXISTS OperationRight;
DROP TABLE IF EXISTS LoginUserRoles;
DROP TABLE IF EXISTS RoleHierarchy;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS LoginUser;

------------------------------------------------------------------------------
-- Create TABLEs
------------------------------------------------------------------------------
-- Create Table: LoginUser
CREATE TABLE LoginUser (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	UserName                        varchar(255) NOT NULL UNIQUE,
	Password                        varchar(512) NOT NULL,
	SysGenedPwd                     decimal(1) default 1,
	ContactUuid                     uuid REFERENCES Contact(UniqueId) NOT NULL UNIQUE,
	Enabled                         decimal(1) default 1,
	SecurityQuestion1               varchar(255),					
	SecurityAnswer1                 varchar(255),					
	SecurityQuestion2               varchar(255),					
	SecurityAnswer2                 varchar(255),					
	SecurityQuestion3               varchar(255),					
	SecurityAnswer3                 varchar(255),					
	CreatedDate                     timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	CreatedById                     uuid REFERENCES Contact(UniqueId), 
	LastModifiedDate                timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
	LastModifiedById                uuid REFERENCES Contact(UniqueId)
);
CREATE INDEX LU_UN_indx ON LoginUser(UserName);
CREATE INDEX LU_CID_indx ON LoginUser(ContactUuid);

-- Create Table: Role
CREATE TABLE Role (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	RoleName                        varchar(255) NOT NULL UNIQUE,
	Description                     varchar(512),
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, RoleName)	
);
CREATE INDEX ROLE_OAID_indx ON Role(OwnerAccountId NULLS FIRST);

-- Create Table: RoleHierarchy
CREATE TABLE RoleHierarchy (
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	IncludedRoleId                  int REFERENCES Role(Id) NOT NULL,
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (RoleId, IncludedRoleId)	
);

-- Create Table: LoginUserRoles
CREATE TABLE LoginUserRoles (
	LoginUserId                     int REFERENCES LoginUser(Id) NOT NULL,
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	UNIQUE (LoginUserId, RoleId)	
);

-- Create Table: OperationRight
CREATE TABLE OperationRight (
	Id                              serial NOT NULL PRIMARY KEY UNIQUE,
	OperationName                   varchar(255) NOT NULL,
	Description                     varchar(512),
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, OperationName)	
);
CREATE INDEX RIGHT_OAID_indx ON OperationRight(OwnerAccountId NULLS FIRST);

-- Create Table: AccessControlList
CREATE TABLE AccessControlList (
	RoleId                          int REFERENCES Role(Id) NOT NULL,
	ObjectName                      varchar(255) NOT NULL,
	OperationRightId                int REFERENCES OperationRight(Id) NOT NULL,
	OwnerAccountId                  int REFERENCES Account (Id),
	UNIQUE (OwnerAccountId, RoleId, ObjectName, OperationRightId)	
);
CREATE INDEX ACL_OAID_indx ON AccessControlList(OwnerAccountId NULLS FIRST);

------------------------------------------------------------------------------
-- Load testing data
------------------------------------------------------------------------------

------------------------------------------------------------------------------
-- Load LoginUser
------------------------------------------------------------------------------
-- Load LoginUser
INSERT INTO LoginUser(UserName, ContactUuid, Password)
	VALUES('aalpha', '07771AE4-236A-49d3-A49E-B1F9E1934D20', 'AllenPassword');
INSERT INTO LoginUser(UserName, ContactUuid, Password)
	VALUES('bbeta',  '07771AE4-236A-49d3-A49E-B1F9E1934D30', 'BobbyPassword');
INSERT INTO LoginUser(UserName, ContactUuid, Password)
	VALUES('cgamma', '07771AE4-236A-49d3-A49E-B1F9E1934D40', 'CindyPassword');

------------------------------------------------------------------------------
-- Load Role for LogixPath (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SUPERADMIN',
	       'LP super system admin role',
               1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_LP_SYSADMIN',
	       'LP system admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_SITE_ADMIN',
	       'LP system site admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_SITE_USERADMIN',
	       'LP system site user admin role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_SITE_SUPERVISOR',
	       'LP system site supervisor role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_SITE_USER',
	       'LP system site user role',
	       1);
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_SITE_PARTNER',
	       'LP system site partnet role',
	       1);

-- Load Role
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_COM_SITE_ADMIN',
	       'Allen company system site admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_COM_SITE_USERADMIN',
	       'Allen company site user admin role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_COM_SITE_SUPERVISOR',
	       'Allen company site supervisor role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_COM_SITE_USER',
	       'Allen company site user role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_ALLEN_COM_SITE_PARTNER',
	       'Allen company site partnet role',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_COM_SITE_SUPERVISOR',
	       'Bobby company site supervisor role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_COM_SITE_USER',
	       'Bobby company site user role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO Role(RoleName, Description, OwnerAccountId)
	VALUES('ROLE_BOBBY_COM_SITE_PARTNER',
	       'Bobby company site partnet role',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));

------------------------------------------------------------------------------
-- Load RoleHierarchy
------------------------------------------------------------------------------

------------------------------------------------------------------------------
-- Load OperationalRight for LogixPath (OwnerAccountId = 1)
------------------------------------------------------------------------------
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for home page access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for performing search',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for read access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for write access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Delete',
	       'Right for delete access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for clone access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for export access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for import access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminAll',
	       'Admin right for all access',
	       1);
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('AdminSiteAll',
	       'Admin right for all site access',
	       1);

-- Load OperationalRight
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('HomePage',
	       'Right for Allen company home page access',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Search',
	       'Right for performing Allen company search',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Create',
	       'Right for performing Allen company create',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Read',
	       'Right for performing Allen company read',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Write',
	       'Right for performing Allen company write',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for performing Allen company clone',
	       (SELECT Id FROM Account WHERE Name='Allen Company'));

INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Clone',
	       'Right for performing Bobby company clone',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Export',
	       'Right for performing Bobby company export',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
INSERT INTO OperationRight(OperationName, Description, OwnerAccountId)
	VALUES('Import',
	       'Right for performing Bobby company import',
	       (SELECT Id FROM Account WHERE Name='Bobby Company'));
