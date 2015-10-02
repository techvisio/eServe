SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS AcademicDetail;
DROP TABLE IF EXISTS AddressDetail;
DROP TABLE IF EXISTS AdmissionDiscountDtl;
DROP TABLE IF EXISTS AdmissionInquiry;
DROP TABLE IF EXISTS BranchPreference;
DROP TABLE IF EXISTS QualificationSubjectDtl;
DROP TABLE IF EXISTS StudentDetail;
DROP TABLE IF EXISTS CasteCategoryMaster;
DROP TABLE IF EXISTS ConsultantDetail;
DROP TABLE IF EXISTS ConsultantMaster;
DROP TABLE IF EXISTS ConsultantPaymentDetail;
DROP TABLE IF EXISTS CounsellingMaster;
DROP TABLE IF EXISTS CourseBranchMaster;
DROP TABLE IF EXISTS CourseMaster;
DROP TABLE IF EXISTS FeeHeadMaster;
DROP TABLE IF EXISTS QualificationMaster;
DROP TABLE IF EXISTS QuotaCodeMaster;
DROP TABLE IF EXISTS StateMaster;
DROP TABLE IF EXISTS SubjectMaster;




/* Create Tables */

CREATE TABLE AcademicDetail
(
	University varchar(100),
	College_Name varchar(100),
	Passing_Year varchar(20),
	Percentage decimal,
	Roll_No varchar(50),
	Qualification_Id int NOT NULL,
	File_No varchar(100) NOT NULL,
	PRIMARY KEY (Qualification_Id, File_No)
);


CREATE TABLE AddressDetail
(
	House_No int,
	Locality varchar(50),
	Landmark varchar(50),
	District varchar(50),
	City varchar(50),
	Pincode int,
	Address_Type char(1),
	State_Id int NOT NULL,
	File_No varchar(100) NOT NULL,
	PRIMARY KEY (State_Id, File_No)
);


CREATE TABLE AdmissionDiscountDtl
(
	FeeHead_Id int NOT NULL,
	Amount decimal,
	Percent decimal,
	File_No varchar(100) NOT NULL,
	PRIMARY KEY (FeeHead_Id, File_No)
);


CREATE TABLE AdmissionInquiry
(
	Inquiry_Id int NOT NULL,
	Name varchar(100),
	Father_Name varchar(100),
	DOB date,
	Contact_No varchar(20),
	Application_Status varchar(50),
	Due_Date date,
	Created_By varchar(100),
	Created_On date,
	Updated_By varchar(100),
	Updated_On date,
	Intrested_Branch_Id int NOT NULL,
	FollowUp_Rquired bit(1),
	PRIMARY KEY (Inquiry_Id)
);


CREATE TABLE BranchPreference
(
	Branch_Preference_Id int,
	File_No varchar(100) NOT NULL,
	Branch_Id int NOT NULL,
	PRIMARY KEY (File_No)
);


CREATE TABLE CasteCategoryMaster
(
	Id int NOT NULL,
	Category varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE ConsultantDetail
(
	File_No varchar(100) NOT NULL,
	Consultant_Id bigint NOT NULL,
	Consultancy_Agreed varchar(10),
	Payment_Mode varchar(50),
	Amount_To_Pay decimal,
	Due_Date date,
	PRIMARY KEY (File_No, Consultant_Id)
);


CREATE TABLE ConsultantMaster
(
	Id bigint NOT NULL,
	Name varchar(50),
	Primary_Contact_No varchar(50),
	Secondary_contact_No varchar(50),
	Address varchar(200),
	Email_Id varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE ConsultantPaymentDetail
(
	Amount decimal,
	Pay_Date date,
	File_No varchar(100) NOT NULL,
	PRIMARY KEY (File_No)
);


CREATE TABLE CounsellingMaster
(
	Id bigint NOT NULL,
	Counselling_Body varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE CourseBranchMaster
(
	Course_Id int NOT NULL,
	Id int NOT NULL,
	Branch varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE CourseMaster
(
	Id int NOT NULL,
	Course varchar(50),
	Course_Type varchar(50),
	PRIMARY KEY (Id)
);


CREATE TABLE FeeHeadMaster
(
	Id int NOT NULL,
	Fee_Head varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE QualificationMaster
(
	Id int NOT NULL,
	QualifyingExam varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE QualificationSubjectDtl
(
	Subject_Id int NOT NULL,
	Qualification_Id int NOT NULL,
	Marks_Obtained decimal,
	Max_Marks decimal,
	File_No varchar(100) NOT NULL,
	PRIMARY KEY (Subject_Id, File_No)
);


CREATE TABLE QuotaCodeMaster
(
	Id int NOT NULL,
	Code varchar(50),
	Description varchar(200),
	PRIMARY KEY (Id)
);


CREATE TABLE StateMaster
(
	Id int NOT NULL,
	State varchar(100),
	PRIMARY KEY (Id)
);


CREATE TABLE StudentDetail
(
	File_No varchar(100) NOT NULL,
	Enroll_No varchar(50) NOT NULL,
	Uni_Enroll_No varchar(50),
	Photo blob,
	First_Name varchar(50),
	Last_Name varchar(50),
	Father_Name varchar(50),
	Mother_Name varchar(50),
	Gender varchar(20),
	DOB varchar(30),
	Blood_Group varchar(20),
	Father_Occupation varchar(50),
	FixedLine_No varchar(50),
	Self_Mobile_No varchar(20),
	Parent_Mobile_No varchar(20),
	Gaurdian_Mobile_No varchar(50),
	Email_Id varchar(50),
	Gaurdian_Email_Id varchar(50),
	Hostel bit(1),
	Transportation bit(1),
	Academic_Year int,
	Semester int,
	Management_Approval bit(1),
	Fee_Paid bit(1),
	Category_Id int NOT NULL,
	Branch_Id int NOT NULL,
	Created_By varchar(100),
	Created_On date,
	Updated_By varchar(100),
	Updated_On date,
	Domicile_State_Id int NOT NULL,
	Scholarship bit(1),
	Remarks varchar(500),
	Course_Id int NOT NULL,
	PRIMARY KEY (File_No),
	UNIQUE (Uni_Enroll_No)
);


CREATE TABLE SubjectMaster
(
	Subject_Id int NOT NULL,
	Subject varchar(50),
	PRIMARY KEY (Subject_Id)
);



/* Create Foreign Keys */

ALTER TABLE StudentDetail
	ADD FOREIGN KEY (Category_Id)
	REFERENCES CasteCategoryMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ConsultantDetail
	ADD FOREIGN KEY (Consultant_Id)
	REFERENCES ConsultantMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BranchPreference
	ADD FOREIGN KEY (Branch_Id)
	REFERENCES CourseBranchMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AdmissionInquiry
	ADD FOREIGN KEY (Intrested_Branch_Id)
	REFERENCES CourseBranchMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE StudentDetail
	ADD FOREIGN KEY (Branch_Id)
	REFERENCES CourseBranchMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE CourseBranchMaster
	ADD FOREIGN KEY (Course_Id)
	REFERENCES CourseMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE StudentDetail
	ADD FOREIGN KEY (Course_Id)
	REFERENCES CourseMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE QualificationSubjectDtl
	ADD FOREIGN KEY (Qualification_Id)
	REFERENCES QualificationMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE StudentDetail
	ADD FOREIGN KEY (Domicile_State_Id)
	REFERENCES StateMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AddressDetail
	ADD FOREIGN KEY (State_Id)
	REFERENCES StateMaster (Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AdmissionDiscountDtl
	ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE BranchPreference
	ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE QualificationSubjectDtl
	ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AddressDetail
	ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AcademicDetail
	ADD FOREIGN KEY (File_No)
	REFERENCES StudentDetail (File_No)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE QualificationSubjectDtl
	ADD FOREIGN KEY (Subject_Id)
	REFERENCES SubjectMaster (Subject_Id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



