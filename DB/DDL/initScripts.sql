INSERT INTO einstitution.seq(
   name
  ,val
) VALUES (
   'Admission'  -- name - IN varchar(20)
  ,100   -- val - IN int(11)
);


INSERT INTO einstitution.feediscountheadmaster(
   Head_Id
  ,Head
  ,Transaction_Type
  ,Parent_type_id
  ,isReoccurring
  ,Refund_Type
) VALUES (
   9994 -- Head_Id - IN int(11)
  ,'CHEQUE DEPOSITE'  -- Head - IN varchar(100)
  ,'C'  -- Transaction_Type - IN char(1)
  ,0   -- Parent_type_id - IN int(11)
  ,0   -- isReoccurring - IN bit(1)
  ,'N'  -- Refund_Type - IN varchar(100)
);

INSERT INTO einstitution.feediscountheadmaster(
   Head_Id
  ,Head
  ,Transaction_Type
  ,Parent_type_id
  ,isReoccurring
  ,Refund_Type
) VALUES (
   9995 -- Head_Id - IN int(11)
  ,'DEMAND DRAFT'  -- Head - IN varchar(100)
  ,'C'  -- Transaction_Type - IN char(1)
  ,0   -- Parent_type_id - IN int(11)
  ,0   -- isReoccurring - IN bit(1)
  ,'N'  -- Refund_Type - IN varchar(100)
);

INSERT INTO einstitution.feediscountheadmaster(
   Head_Id
  ,Head
  ,Transaction_Type
  ,Parent_type_id
  ,isReoccurring
  ,Refund_Type
) VALUES (
   9996 -- Head_Id - IN int(11)
  ,'CASH DEPOSITE'  -- Head - IN varchar(100)
  ,'C'  -- Transaction_Type - IN char(1)
  ,0   -- Parent_type_id - IN int(11)
  ,0   -- isReoccurring - IN bit(1)
  ,'N'  -- Refund_Type - IN varchar(100)
);

INSERT INTO einstitution.feediscountheadmaster(
   Head_Id
  ,Head
  ,Transaction_Type
  ,Parent_type_id
  ,isReoccurring
  ,Refund_Type
) VALUES (
   9999 -- Head_Id - IN int(11)
  ,'HOSTEL FEE'  -- Head - IN varchar(100)
  ,'D'  -- Transaction_Type - IN char(1)
  ,0   -- Parent_type_id - IN int(11)
  ,1   -- isReoccurring - IN bit(1)
  ,'N'  -- Refund_Type - IN varchar(100)
);

INSERT INTO einstitution.feediscountheadmaster(
   Head_Id
  ,Head
  ,Transaction_Type
  ,Parent_type_id
  ,isReoccurring
  ,Refund_Type
) VALUES (
   9998 -- Head_Id - IN int(11)
  ,'TRANSPORTATION FEE'  -- Head - IN varchar(100)
  ,'D'  -- Transaction_Type - IN char(1)
  ,0   -- Parent_type_id - IN int(11)
  ,1   -- isReoccurring - IN bit(1)
  ,'N'  -- Refund_Type - IN varchar(100)
);
