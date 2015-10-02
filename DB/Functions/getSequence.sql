create table seq (name varchar(20), val int);

INSERT INTO einstitution.seq(
   name
  ,val
) VALUES (
   'Admission'  -- name - IN varchar(20)
  ,100   -- val - IN int(11)
)



DROP FUNCTION IF EXISTS einstitution.getSequence;
CREATE FUNCTION einstitution.`getSequence`(seq_name char (20)) RETURNS int(11)
begin
 update seq set val=last_insert_id(val+1) where name=seq_name;
 return last_insert_id();
end;
