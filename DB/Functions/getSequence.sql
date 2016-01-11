create table TB_SEQUENCE (name varchar(20), val int);

INSERT INTO eserve.TB_SEQUENCE(
   name
  ,val
) VALUES (
   'HW/CST'  -- name - IN varchar(20)
  ,0   -- val - IN int(11)
)



DROP FUNCTION IF EXISTS eserve.getSequence;
CREATE FUNCTION eserve.`getSequence`(seq_name char (20)) RETURNS int(11)
begin
 update TB_SEQUENCE set val=last_insert_id(val+1) where name=seq_name;
 return last_insert_id();
end;
