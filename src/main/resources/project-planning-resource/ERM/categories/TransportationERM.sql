SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS TransportAllocation;
DROP TABLE IF EXISTS VehicleDetail;
DROP TABLE IF EXISTS TransportReservation;
DROP TABLE IF EXISTS TransportMaster;




/* Create Tables */

CREATE TABLE TransportAllocation
(
	File_No varchar(200) NOT NULL,
	Vehicle_Id varchar(100) NOT NULL
);


CREATE TABLE TransportMaster
(
	Route_Code varchar(100) NOT NULL,
	Description varchar(200),
	Threshold varchar(200),
	Price double,
	PRIMARY KEY (Route_Code)
);


CREATE TABLE TransportReservation
(
	File_No varchar(100) NOT NULL,
	Fee_Paid bit(1),
	Route_Code varchar(100) NOT NULL
);


CREATE TABLE VehicleDetail
(
	Type varchar(100),
	Capacity varchar(200),
	Vehicle_No varchar(200),
	Route_Code varchar(100) NOT NULL,
	Vehicle_Id varchar(100) NOT NULL,
	PRIMARY KEY (Vehicle_Id)
);



/* Create Foreign Keys */

ALTER TABLE VehicleDetail
	ADD FOREIGN KEY (Route_Code)
	REFERENCES TransportMaster (Route_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TransportReservation
	ADD FOREIGN KEY (Route_Code)
	REFERENCES TransportMaster (Route_Code)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE TransportAllocation
	ADD FOREIGN KEY (Vehicle_Id)
	REFERENCES VehicleDetail (Vehicle_Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



