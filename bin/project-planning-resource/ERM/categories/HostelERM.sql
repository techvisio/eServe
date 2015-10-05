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


CREATE TABLE HostelInventoryMaster
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
	REFERENCES HostelInventoryMaster (Type_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE RoomTypeDetail
	ADD FOREIGN KEY (Type_Code)
	REFERENCES HostelInventoryMaster (Type_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE HostelAllocation
	ADD FOREIGN KEY (Room_No)
	REFERENCES RoomTypeDetail (Room_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



