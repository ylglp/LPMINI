-- Create Table: ListOfValue
CREATE TABLE ListOfValue (
	LOVType			varchar(30) NOT NULL,
	LocaleString	varchar(10) NOT NULL,
	Key				varchar(64) NOT NULL,
	StringValue		varchar(256),
	IntValue		int,
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
	
INSERT INTO ListOfValue(LOVType, LocaleString, Key, IntValue)
	VALUES('TestLOVType2', 'en_us', '1', 1);
INSERT INTO ListOfValue(LOVType, LocaleString, Key, IntValue)
	VALUES('TestLOVType2', 'en_us', '2', 2);
INSERT INTO ListOfValue(LOVType, LocaleString, Key, IntValue)
	VALUES('TestLOVType2', 'en_us', '3', 3);
