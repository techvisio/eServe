
    alter table TB_ADDRESS_DETAIL 
        drop 
        foreign key FK_n1rjk60p7ry8qr8ybvjm5hkiu;

    alter table TB_CUSTOMER_DETAIL 
        drop 
        foreign key FK_e24t54mumxq3mjg93u949e0xt;

    alter table TB_DESIGNATION_MASTER 
        drop 
        foreign key FK_5r7fpc7unr9h7ns4ej5higa3t;

    alter table TB_EQUIPMENT_DETAIL 
        drop 
        foreign key FK_7w64i0k83gm1a6be95vhiuosn;

    alter table TB_EQUIPMENT_DETAIL 
        drop 
        foreign key FK_dah665usvho3uqvx45o16efod;

    alter table TB_UNIT_DETAIL 
        drop 
        foreign key FK_7ed5lwvs0ccedyqcgd8in768e;

    alter table TB_UNIT_DETAIL 
        drop 
        foreign key FK_c25p14a5xbrp4jghdoapqxbbs;

    alter table TB_USER 
        drop 
        foreign key FK_33emh5hhr8suqbuw2vsjupele;

    alter table TB_USER 
        drop 
        foreign key FK_603cy11rbd337u0jo1ip7uhgq;

    alter table TB_USER_PRIVILEGE 
        drop 
        foreign key FK_dn5ngd9tmi377n3befgo2let7;

    alter table TB_USER_PRIVILEGE 
        drop 
        foreign key FK_ma8cl0rlt088t91qbrblofkf6;

    alter table TB_USER_ROLE 
        drop 
        foreign key FK_eh3apv2dninoymosdqfycw161;

    alter table TB_USER_ROLE 
        drop 
        foreign key FK_5fq3f28sy9i37l92iuqvrwn5a;

    drop table if exists TB_ADDRESS_DETAIL;

    drop table if exists TB_CLIENT_MASTER;

    drop table if exists TB_CUSTOMER_DETAIL;

    drop table if exists TB_DEPARTMENT_MASTER;

    drop table if exists TB_DESIGNATION_MASTER;

    drop table if exists TB_EQUIPMENT_DETAIL;

    drop table if exists TB_EQUIPMENT_MASTER;

    drop table if exists TB_MODULE_LOG;

    drop table if exists TB_PRIVILEGE;

    drop table if exists TB_QUESTION_MASTER;

    drop table if exists TB_ROLE_MASTER;

    drop table if exists TB_SECURITY_QUESTION;

    drop table if exists TB_STATE_MASTER;

    drop table if exists TB_UNIT_DETAIL;

    drop table if exists TB_USER;

    drop table if exists TB_USER_PRIVILEGE;

    drop table if exists TB_USER_ROLE;

    create table TB_ADDRESS_DETAIL (
        Address_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Address_1 varchar(255),
        Address_2 varchar(255),
        Address_Type varchar(255),
        City varchar(255),
        Mobile varchar(255),
        Pincode integer,
        Telephone varchar(255),
        State_Id bigint,
        primary key (Address_Id)
    );

    create table TB_CLIENT_MASTER (
        Client_Id bigint not null auto_increment,
        Client varchar(255),
        Client_Code varchar(255),
        primary key (Client_Id)
    );

    create table TB_CUSTOMER_DETAIL (
        Customer_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Contact_No varchar(255),
        Customer_Name varchar(255),
        Customer_Type varchar(255),
        Email_Id varchar(255),
        Address_Id bigint,
        primary key (Customer_Id)
    );

    create table TB_DEPARTMENT_MASTER (
        Department_Id bigint not null,
        Department varchar(255),
        primary key (Department_Id)
    );

    create table TB_DESIGNATION_MASTER (
        Designation_Id bigint not null,
        Designation varchar(255),
        Department_Id bigint,
        primary key (Designation_Id)
    );

    create table TB_EQUIPMENT_DETAIL (
        Equipment_Dtl_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Invoice_No bigint,
        Serial_No varchar(255),
        Type varchar(255),
        Unit_Id bigint,
        Warranty_Under varchar(255),
        Equipment_Id bigint,
        primary key (Equipment_Dtl_Id)
    );

    create table TB_EQUIPMENT_MASTER (
        Equipment_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Brand varchar(255),
        Capacity varchar(255),
        Category varchar(255),
        Model varchar(255),
        Price double precision,
        primary key (Equipment_Id)
    );

    create table TB_MODULE_LOG (
        id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        date date,
        entityId integer not null,
        errorMessage varchar(255),
        operation varchar(255),
        userId integer not null,
        workFlowOperation varchar(255),
        primary key (id)
    );

    create table TB_PRIVILEGE (
        Privilege_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Description varchar(255),
        Privilege varchar(255),
        Type varchar(255),
        primary key (Privilege_Id)
    );

    create table TB_QUESTION_MASTER (
        Question varchar(255) not null,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        primary key (Question)
    );

    create table TB_ROLE_MASTER (
        Role_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Description varchar(255),
        Role_Name varchar(255),
        primary key (Role_Id)
    );

    create table TB_SECURITY_QUESTION (
        USER_ID bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Answer varchar(255),
        Is_Custom_question bit,
        Question varchar(255),
        primary key (USER_ID)
    );

    create table TB_STATE_MASTER (
        State_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        State_Name varchar(255),
        primary key (State_Id)
    );

    create table TB_UNIT_DETAIL (
        Unit_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        Customer_Id bigint,
        External_Id varchar(255),
        Height bigint,
        Length bigint,
        Service_Category varchar(255),
        Service_Party varchar(255),
        Unit_Category varchar(255),
        Width bigint,
        Address_Id bigint,
        primary key (Unit_Id)
    );

    create table TB_USER (
        User_Id bigint not null auto_increment,
        Client_Id bigint,
        Created_By varchar(255),
        Created_On datetime,
        Updated_By varchar(255),
        Updated_On datetime,
        DOB date,
        IS_ACTIVE bit,
        Email_Id varchar(255),
        First_Name varchar(255),
        Force_Password_Change bit,
        Last_Name varchar(255),
        Password varchar(255),
        User_Name varchar(255),
        Department_Id bigint,
        Designation_Id bigint,
        primary key (User_Id)
    );

    create table TB_USER_PRIVILEGE (
        USER_ID bigint not null,
        Privilege_Id bigint not null
    );

    create table TB_USER_ROLE (
        User_Id bigint not null,
        Role_Id bigint not null
    );

    alter table TB_ADDRESS_DETAIL 
        add constraint FK_n1rjk60p7ry8qr8ybvjm5hkiu 
        foreign key (State_Id) 
        references TB_STATE_MASTER (State_Id);

    alter table TB_CUSTOMER_DETAIL 
        add constraint FK_e24t54mumxq3mjg93u949e0xt 
        foreign key (Address_Id) 
        references TB_ADDRESS_DETAIL (Address_Id);

    alter table TB_DESIGNATION_MASTER 
        add constraint FK_5r7fpc7unr9h7ns4ej5higa3t 
        foreign key (Department_Id) 
        references TB_DEPARTMENT_MASTER (Department_Id);

    alter table TB_EQUIPMENT_DETAIL 
        add constraint FK_7w64i0k83gm1a6be95vhiuosn 
        foreign key (Equipment_Id) 
        references TB_EQUIPMENT_MASTER (Equipment_Id);

    alter table TB_EQUIPMENT_DETAIL 
        add constraint FK_dah665usvho3uqvx45o16efod 
        foreign key (Unit_Id) 
        references TB_UNIT_DETAIL (Unit_Id);

    alter table TB_UNIT_DETAIL 
        add constraint FK_7ed5lwvs0ccedyqcgd8in768e 
        foreign key (Address_Id) 
        references TB_ADDRESS_DETAIL (Address_Id);

    alter table TB_UNIT_DETAIL 
        add constraint FK_c25p14a5xbrp4jghdoapqxbbs 
        foreign key (Customer_Id) 
        references TB_CUSTOMER_DETAIL (Customer_Id);

    alter table TB_USER 
        add constraint FK_33emh5hhr8suqbuw2vsjupele 
        foreign key (Department_Id) 
        references TB_DEPARTMENT_MASTER (Department_Id);

    alter table TB_USER 
        add constraint FK_603cy11rbd337u0jo1ip7uhgq 
        foreign key (Designation_Id) 
        references TB_DESIGNATION_MASTER (Designation_Id);

    alter table TB_USER_PRIVILEGE 
        add constraint FK_dn5ngd9tmi377n3befgo2let7 
        foreign key (Privilege_Id) 
        references TB_PRIVILEGE (Privilege_Id);

    alter table TB_USER_PRIVILEGE 
        add constraint FK_ma8cl0rlt088t91qbrblofkf6 
        foreign key (USER_ID) 
        references TB_USER (User_Id);

    alter table TB_USER_ROLE 
        add constraint FK_eh3apv2dninoymosdqfycw161 
        foreign key (Role_Id) 
        references TB_ROLE_MASTER (Role_Id);

    alter table TB_USER_ROLE 
        add constraint FK_5fq3f28sy9i37l92iuqvrwn5a 
        foreign key (User_Id) 
        references TB_USER (User_Id);
