SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS HostelAllocation;
DROP TABLE IF EXISTS HostelReservation;
DROP TABLE IF EXISTS RoomTypeDetail;
DROP TABLE IF EXISTS HostelInventoryMaster;




/* Create Tables */

CREATE TABLE HostelAllocation
(
	Wing varchar(200),
	Floor varchar(100),
	Block varchar(100),
	Name varchar(200),
	File_No varchar(200) NOT NULL,
	Room_No varchar(200) NOT NULL,
	PRIMARY KEY (File_No)
);


CREATE TABLE HostelInventory
(
	Type_Code varchar(200) NOT NULL,
	Description varchar(200),
	Threshold int,
	Price double,
	Room_Capacity int,
	PRIMARY KEY (Type_Code)
);


CREATE TABLE HostelReservation
(
	File_No varchar(200) NOT NULL,
	Fee_Paid double,
	Type_Code varchar(200) NOT NULL,
	PRIMARY KEY (File_No)
);


CREATE TABLE RoomTypeDetail
(
	Room_No varchar(200) NOT NULL,
	Type_Code varchar(200) NOT NULL,
	PRIMARY KEY (Room_No)
);



/* Create Foreign Keys */

ALTER TABLE HostelReservation
	ADD FOREIGN KEY (Type_Code)
	REFERENCES HostelInventory(Type_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RoomTypeDetail
	ADD FOREIGN KEY (Type_Code)
	REFERENCES HostelInventory(Type_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HostelAllocation
	ADD FOREIGN KEY (Room_No)
	REFERENCES RoomTypeDetail (Room_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;






/* Create Foreign Keys */
ALTER TABLE hostelallocation
ADD FOREIGN KEY (File_No)
REFERENCES studentdetail(File_No)
;


ALTER TABLE HostelReservation
	
  ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
;

ALTER TABLE HOSTELRESERVATION MODIFY COLUMN Fee_Paid BIT;

ALTER TABLE hostelallocation
ADD FOREIGN KEY (ROOM_NO)
REFERENCES roomtypedetail (ROOM_NO);



/* Alter primary key*/
ALTER TABLE  HostelAllocation ADD PRIMARY KEY(File_No);



ALTER TABLE  HOSTELRESERVATION ADD PRIMARY KEY(File_No);

ALTER TABLE  ROOMTYPEDETAIL ADD PRIMARY KEY(ROOM_NO);

/*#########################################################################*/


/*11-feb-2015 add new column(Allocation_Status) in hostelReservation/also done in transport table manually*/

alter table hostelreservation add column (Allocation_Status varchar(220));


/* add new column(Is_Active bit) in hostelReservation/also done in transport table manually*/

alter table hostelreservation add column (Is_Active bit);


 
/*#####################################################################################*/

/*11-feb-2015 room avalable in hostel*/
SELECT HI.PRICE,HI.THRESHOLD,HI.TYPE_CODE,COUNT(HR.TYPE_CODE )AS RESERVED_ROOM,HI.THRESHOLD-COUNT(HR.TYPE_CODE ) AS AVAILABLE FROM hostelinventory HI JOIN hostelreservation HR ON HI.Type_Code = HR.Type_Code GROUP BY HR.Type_Code;

/* ###############################################################################################*/

/*11-feb-2015 ModuleLog */

CREATE TABLE MODULELOG 
(WORK_FLOW_OPERATION VARCHAR(200),
USER_ID INTEGER,
OPERATION VARCHAR(100),
ERROR_MESSAGE VARCHAR(2000),
ENTITY_ID INTEGER,
DATE DATE);

/* #########################################################################################*/

/*11-feb-2015 TaskAndFollowup*/

CREATE TABLE taskandfollowup 
(USER_ID VARCHAR(220), 
TASK_ID INTEGER, 
TASK_ENTRY VARCHAR(220), 
STATUS VARCHAR(220), 
ROLE VARCHAR(220), 
REMARK VARCHAR(500), 
PARENTTASK_ID INTEGER, 
DUE_DATE DATE);

/*11-feb-2015 Add primary key in taskandfollowup table*/
ALTER TABLE taskandfollowup ADD PRIMARY KEY (TASK_ID);

/* #########################################################################################*/

/*11-feb-2015 WorkFlowFieldMapping */

CREATE TABLE workflowfieldmapping 
(WORK_FLOW_STEP_ID VARCHAR(200),
 WORK_FLOW_ID VARCHAR(200),
 VISIBLE BIT,
 VALID_VALUE VARCHAR(200),
 TYPE VARCHAR(200),
 TITLE VARCHAR(200),
 MASTER_DATA_CODE VARCHAR(200),
 MANDATORY_IND BIT,
 FIELD_DESC_ID VARCHAR(200));
