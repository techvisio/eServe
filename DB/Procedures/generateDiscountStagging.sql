CREATE DEFINER = 'root'@'localhost'
PROCEDURE einstitution.generateDiscountStagging(In v_file_no varchar(100))
BEGIN

declare v_fee_head	int;
declare v_amount	decimal;
declare v_percent	decimal;
declare v_parent_head_id	int;
declare done int;
declare v_parent_amount int;

declare cur1 cursor for select FeeHead_Id,ifnull(Amount,0),ifnull(Percent,0) from admissiondiscountdtl where file_no=v_file_no;
declare continue handler for not found set done=1;

    set done = 0;
    open cur1;
    igmLoop: loop
        fetch cur1 into v_fee_head,v_amount,v_percent;
        if done = 1 then leave igmLoop; end if;
        if(v_amount>0) THEN
        insert into studentfeestaging(FILE_NO,
Amount,
Created_By,
created_date,
Feehead_Id,
Approved,
Is_Reoccuring,
Is_conditional)
select sd.File_No,v_amount,'SYSTEM',now(),v_fee_head,0,0,0 from studentdetail sd 
where sd.File_No=v_file_no;

elseIf (v_percent>0) then  


select FDH.parent_type_id into v_parent_head_id from FeeDiscountHeadMaster FDH where FDH.Head_Id = v_fee_head;

select ifnull(fd.FEE_AMOUNT,0) into v_parent_amount from feedetailmaster fd join studentDetail sd on sd.session_id=fd.session_id and sd.centre_id=fd.centre_id and sd.shift_id=fd.shift_id and sd.course_id=fd.course and sd.branch_id=fd.branch where sd.file_No=v_file_no and fd.fee_head_id=v_parent_head_id;

set v_amount=(v_parent_amount*v_percent)/100;

insert into studentfeestaging(FILE_NO,
Amount,
Created_By,
created_date,
Feehead_Id,
Approved,
Is_Reoccuring,
Is_conditional)
select sd.File_No,v_amount,'SYSTEM',now(),v_fee_head,0,0,0 from studentdetail sd 
where sd.File_No=v_file_no;

end if;
           end loop igmLoop;
    close cur1;
END