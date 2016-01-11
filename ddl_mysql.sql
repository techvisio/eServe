
    alter table TB_ADDRESS_DETAIL 
        drop 
        foreign key FK_a96hlh8586s53ujx0mwpp2pno;

    alter table TB_ADDRESS_DETAIL 
        drop 
        foreign key FK_im3tux72fl67al6v1h9uatcl3;

    alter table TB_COMPLAINT_ASSIGNMENT 
        drop 
        foreign key FK_efy1rj6xyhvfpslvetcmak7cv;

    alter table TB_COMPLAINT_ASSIGNMENT 
        drop 
        foreign key FK_j3o67txjumuedjphkx9f7xlf2;

    alter table TB_COMPLAINT_ASSIGNMENT 
        drop 
        foreign key FK_nuwbb7pohotx6gbai57ly13f8;

    alter table TB_COMPLAINT_RESOLUTION 
        drop 
        foreign key FK_b7c34492wmue9o8hovw0y4361;

    alter table TB_COMPLAINT_RESOLUTION 
        drop 
        foreign key FK_d7cm5awc490s4lnensj6uvdid;

    alter table TB_COMPLAINT_RESOLUTION 
        drop 
        foreign key FK_n33dxsve4d1x71cfm46fxjjl1;

    alter table TB_CONFIG 
        drop 
        foreign key FK_bu8i693dhiy4sbl9tucpiwfht;

    alter table TB_CUSTOMER_COMPLAINT 
        drop 
        foreign key FK_n124pnn94fdds0dcr8faj05aw;

    alter table TB_CUSTOMER_COMPLAINT 
        drop 
        foreign key FK_ivbs4lvovumsgq6p8xi6eokpj;

    alter table TB_CUSTOMER_COMPLAINT 
        drop 
        foreign key FK_5swkjwe8343mp2aghxtbj9hvs;

    alter table TB_CUSTOMER_DETAIL 
        drop 
        foreign key FK_tljo0rst9x21wgr6b980g1vuf;

    alter table TB_CUSTOMER_DETAIL 
        drop 
        foreign key FK_apd6c41p5jgd3v12ifa3q5won;

    alter table TB_DEPARTMENT_MASTER 
        drop 
        foreign key FK_jlqewk2d5mjj5th8lhjnhkt31;

    alter table TB_DESIGNATION_MASTER 
        drop 
        foreign key FK_2cukmf7ss9s9vb7hl1ekh7539;

    alter table TB_EQUIPMENT_DETAIL 
        drop 
        foreign key FK_3duvxcybmp3reaecywsyksbbn;

    alter table TB_EQUIPMENT_DETAIL 
        drop 
        foreign key FK_p6xth0elmsal9cjr2t6kojel1;

    alter table TB_EQUIPMENT_DETAIL 
        drop 
        foreign key FK_mpnueniqq8mq7w53mgsbg12fe;

    alter table TB_EQUIPMENT_MASTER 
        drop 
        foreign key FK_g2knoota7mm923vp006iw706l;

    alter table TB_ISSUE_MASTER 
        drop 
        foreign key FK_38gjt49t15rjbd8mby5pvoyl9;

    alter table TB_MODULE_LOG 
        drop 
        foreign key FK_2c65ta99wws5dp03l51wpklon;

    alter table TB_PRIVILEGE 
        drop 
        foreign key FK_s1rhf50ehwwjplbbe76d2ic88;

    alter table TB_QUESTION_MASTER 
        drop 
        foreign key FK_oqwkem6mbs8r60b513ankmkw2;

    alter table TB_RESOLUTION_MASTER 
        drop 
        foreign key FK_1p9loturrda0tvuv78wl084ef;

    alter table TB_ROLE_MASTER 
        drop 
        foreign key FK_5561j3i7v37giyd9wdtpqo322;

    alter table TB_SECURITY_QUESTION 
        drop 
        foreign key FK_c35mkkq60ro92y0p6n6l7kyp5;

    alter table TB_STATE_MASTER 
        drop 
        foreign key FK_4rry69rrjrqbsk9ty7imwwm95;

    alter table TB_UNIT_DETAIL 
        drop 
        foreign key FK_6tjg6w8bg9abmuc2hjv06gs28;

    alter table TB_UNIT_DETAIL 
        drop 
        foreign key FK_t1kal148ybl9hd9qoj9l0p6ay;

    alter table TB_UNIT_DETAIL 
        drop 
        foreign key FK_gbe6skygwysmueutotfpdfu5m;

    alter table TB_USER 
        drop 
        foreign key FK_gu03pv7i6qh7ghnfa0qpx1hc2;

    alter table TB_USER 
        drop 
        foreign key FK_mhpb2sfs9sfu9he0dbcxvytbe;

    alter table TB_USER 
        drop 
        foreign key FK_35bcb8byobtamb86us8hxa2d3;

    alter table TB_USER_PRIVILEGE 
        drop 
        foreign key FK_krcwh4jxl0p82snjqkhpf5vwt;

    alter table TB_USER_PRIVILEGE 
        drop 
        foreign key FK_msf5555mxhr9f4o1eak4dkvsq;

    alter table TB_USER_PRIVILEGE 
        drop 
        foreign key FK_ma8cl0rlt088t91qbrblofkf6;

    alter table TB_USER_ROLE 
        drop 
        foreign key FK_8skir5otaupso5m1djsxg9nin;

    alter table TB_USER_ROLE 
        drop 
        foreign key FK_6eiqgtui5tbfbpyfgl667x22h;

    drop table if exists TB_ADDRESS_DETAIL;

    drop table if exists TB_CLIENT_MASTER;

    drop table if exists TB_COMPLAINT_ASSIGNMENT;

    drop table if exists TB_COMPLAINT_RESOLUTION;

    drop table if exists TB_CONFIG;

    drop table if exists TB_CUSTOMER_COMPLAINT;

    drop table if exists TB_CUSTOMER_DETAIL;

    drop table if exists TB_DEPARTMENT_MASTER;

    drop table if exists TB_DESIGNATION_MASTER;

    drop table if exists TB_EQUIPMENT_DETAIL;

    drop table if exists TB_EQUIPMENT_MASTER;

    drop table if exists TB_ISSUE_MASTER;

    drop table if exists TB_MODULE_LOG;

    drop table if exists TB_PRIVILEGE;

    drop table if exists TB_QUESTION_MASTER;

    drop table if exists TB_RESOLUTION_MASTER;

    drop table if exists TB_ROLE_MASTER;

    drop table if exists TB_SECURITY_QUESTION;

    drop table if exists TB_STATE_MASTER;

    drop table if exists TB_UNIT_DETAIL;

    drop table if exists TB_USER;

    drop table if exists TB_USER_PRIVILEGE;

    drop table if exists TB_USER_ROLE;

    create table TB_ADDRESS_DETAIL (
        ADDRESS_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        ADDRESS varchar(255),
        CITY varchar(255),
        CONTACT_NO varchar(255),
        PINCODE integer,
        Client_Id bigint,
        STATE_ID bigint,
        primary key (ADDRESS_ID)
    );

    create table TB_CLIENT_MASTER (
        CLIENT_ID bigint not null auto_increment,
        CLIENT varchar(255),
        CLIENT_CODE varchar(255),
        primary key (CLIENT_ID)
    );

    create table TB_COMPLAINT_ASSIGNMENT (
        COMPLAINT_ID bigint not null,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        Client_Id bigint,
        USER_ID bigint,
        primary key (COMPLAINT_ID)
    );

    create table TB_COMPLAINT_RESOLUTION (
        COMPLAINT_ID bigint not null,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DESCRIPTION varchar(255),
        Client_Id bigint,
        RESOLUTION_ID bigint,
        primary key (COMPLAINT_ID)
    );

    create table TB_CONFIG (
        CONFIG_TB_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        PROPERTY varchar(255),
        VALUE varchar(255),
        Client_Id bigint,
        primary key (CONFIG_TB_ID)
    );

    create table TB_CUSTOMER_COMPLAINT (
        COMPLAINT_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        COMPLAINT_CODE varchar(255),
        CONTACT_NO varchar(255),
        CUSTOMER_CODE varchar(255),
        CUSTOMER_ID bigint,
        CUSTOMER_NAME varchar(255),
        DESCRIPTION varchar(255),
        EMAIL_ID varchar(255),
        PARENT_COMPLAINT_ID bigint,
        PRIORITY varchar(255),
        SLA_DATE datetime,
        STATUS varchar(255),
        Client_Id bigint,
        ISSUE_ID bigint,
        UNIT_ID bigint,
        primary key (COMPLAINT_ID)
    );

    create table TB_CUSTOMER_DETAIL (
        CUSTOMER_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        CONTACT_NO varchar(255),
        CUSTOMER_CODE varchar(255),
        CUSTOMER_NAME varchar(255),
        CUSTOMER_TYPE varchar(255),
        EMAIL_ID varchar(255),
        Client_Id bigint,
        ADDRESS_ID bigint,
        primary key (CUSTOMER_ID)
    );

    create table TB_DEPARTMENT_MASTER (
        DEPARTMENT_ID bigint not null,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DEPARTMENT varchar(255),
        Client_Id bigint,
        primary key (DEPARTMENT_ID)
    );

    create table TB_DESIGNATION_MASTER (
        DESIGNATION_ID bigint not null,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DESIGNATION varchar(255),
        Client_Id bigint,
        primary key (DESIGNATION_ID)
    );

    create table TB_EQUIPMENT_DETAIL (
        EQUIPMENT_DTL_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        INVOICE_NO bigint,
        SERIAL_NO varchar(255),
        TYPE varchar(255),
        IS_UNDER_WARRANTY bit,
        UNIT_ID bigint,
        WARRANTY_UNDER varchar(255),
        Client_Id bigint,
        EQUIPMENT_ID bigint,
        primary key (EQUIPMENT_DTL_ID)
    );

    create table TB_EQUIPMENT_MASTER (
        EQUIPMENT_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        BRAND varchar(255),
        CAPACITY varchar(255),
        CATEGORY varchar(255),
        MODEL varchar(255),
        PRICE double precision,
        Client_Id bigint,
        primary key (EQUIPMENT_ID)
    );

    create table TB_ISSUE_MASTER (
        ISSUE_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        VALUE varchar(255),
        Client_Id bigint,
        primary key (ISSUE_ID)
    );

    create table TB_MODULE_LOG (
        id bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        date date,
        entityId integer not null,
        errorMessage varchar(255),
        operation varchar(255),
        userId integer not null,
        workFlowOperation varchar(255),
        Client_Id bigint,
        primary key (id)
    );

    create table TB_PRIVILEGE (
        PRIVILEGE_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DESCRIPTION varchar(255),
        PRIVILEGE varchar(255),
        TYPE varchar(255),
        Client_Id bigint,
        primary key (PRIVILEGE_ID)
    );

    create table TB_QUESTION_MASTER (
        QUESTION varchar(255) not null,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        Client_Id bigint,
        primary key (QUESTION)
    );

    create table TB_RESOLUTION_MASTER (
        RESOLUTION_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        VALUE varchar(255),
        Client_Id bigint,
        primary key (RESOLUTION_ID)
    );

    create table TB_ROLE_MASTER (
        ROLE_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DESCRIPTION varchar(255),
        ROLE_NAME varchar(255),
        Client_Id bigint,
        primary key (ROLE_ID)
    );

    create table TB_SECURITY_QUESTION (
        USER_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        ANSWER varchar(255),
        IS_CUSTOM_QUESTION bit,
        QUESTION varchar(255),
        Client_Id bigint,
        primary key (USER_ID)
    );

    create table TB_STATE_MASTER (
        STATE_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        STATE_NAME varchar(255),
        Client_Id bigint,
        primary key (STATE_ID)
    );

    create table TB_UNIT_DETAIL (
        UNIT_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        CONTRACT_EXPIRE_ON date,
        CONTRACT_START_ON date,
        CUSTOMER_ID bigint,
        EXTERNAL_ID varchar(255),
        HEIGHT bigint,
        LENGTH bigint,
        SERVICE_CATEGORY varchar(255),
        SERVICE_PARTY varchar(255),
        UNIT_CATEGORY varchar(255),
        UNIT_CODE varchar(255),
        WIDTH bigint,
        Client_Id bigint,
        ADDRESS_ID bigint,
        primary key (UNIT_ID)
    );

    create table TB_USER (
        USER_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        DOB date,
        IS_ACTIVE bit,
        EMAIL_ID varchar(255),
        FIRST_NAME varchar(255),
        FORCE_PASSWORD_CHANGE bit,
        LAST_NAME varchar(255),
        PASSWORD varchar(255),
        USER_NAME varchar(255),
        Client_Id bigint,
        DEPARTMENT_ID bigint,
        DESIGNATION_ID bigint,
        primary key (USER_ID)
    );

    create table TB_USER_PRIVILEGE (
        USER_PRVLG_ID bigint not null auto_increment,
        CREATED_BY varchar(255),
        CREATED_ON datetime,
        UPDATED_BY varchar(255),
        UPDATED_ON datetime,
        USER_ID bigint,
        Client_Id bigint,
        PRIVILEGE_ID bigint,
        primary key (USER_PRVLG_ID)
    );

    create table TB_USER_ROLE (
        USER_ID bigint not null,
        ROLE_ID bigint not null
    );

    alter table TB_ADDRESS_DETAIL 
        add constraint FK_a96hlh8586s53ujx0mwpp2pno 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_ADDRESS_DETAIL 
        add constraint FK_im3tux72fl67al6v1h9uatcl3 
        foreign key (STATE_ID) 
        references TB_STATE_MASTER (STATE_ID);

    alter table TB_COMPLAINT_ASSIGNMENT 
        add constraint FK_efy1rj6xyhvfpslvetcmak7cv 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_COMPLAINT_ASSIGNMENT 
        add constraint FK_j3o67txjumuedjphkx9f7xlf2 
        foreign key (USER_ID) 
        references TB_USER (USER_ID);

    alter table TB_COMPLAINT_ASSIGNMENT 
        add constraint FK_nuwbb7pohotx6gbai57ly13f8 
        foreign key (COMPLAINT_ID) 
        references TB_CUSTOMER_COMPLAINT (COMPLAINT_ID);

    alter table TB_COMPLAINT_RESOLUTION 
        add constraint FK_b7c34492wmue9o8hovw0y4361 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_COMPLAINT_RESOLUTION 
        add constraint FK_d7cm5awc490s4lnensj6uvdid 
        foreign key (RESOLUTION_ID) 
        references TB_RESOLUTION_MASTER (RESOLUTION_ID);

    alter table TB_COMPLAINT_RESOLUTION 
        add constraint FK_n33dxsve4d1x71cfm46fxjjl1 
        foreign key (COMPLAINT_ID) 
        references TB_CUSTOMER_COMPLAINT (COMPLAINT_ID);

    alter table TB_CONFIG 
        add constraint FK_bu8i693dhiy4sbl9tucpiwfht 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_CUSTOMER_COMPLAINT 
        add constraint FK_n124pnn94fdds0dcr8faj05aw 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_CUSTOMER_COMPLAINT 
        add constraint FK_ivbs4lvovumsgq6p8xi6eokpj 
        foreign key (ISSUE_ID) 
        references TB_ISSUE_MASTER (ISSUE_ID);

    alter table TB_CUSTOMER_COMPLAINT 
        add constraint FK_5swkjwe8343mp2aghxtbj9hvs 
        foreign key (UNIT_ID) 
        references TB_UNIT_DETAIL (UNIT_ID);

    alter table TB_CUSTOMER_DETAIL 
        add constraint FK_tljo0rst9x21wgr6b980g1vuf 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_CUSTOMER_DETAIL 
        add constraint FK_apd6c41p5jgd3v12ifa3q5won 
        foreign key (ADDRESS_ID) 
        references TB_ADDRESS_DETAIL (ADDRESS_ID);

    alter table TB_DEPARTMENT_MASTER 
        add constraint FK_jlqewk2d5mjj5th8lhjnhkt31 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_DESIGNATION_MASTER 
        add constraint FK_2cukmf7ss9s9vb7hl1ekh7539 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_EQUIPMENT_DETAIL 
        add constraint FK_3duvxcybmp3reaecywsyksbbn 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_EQUIPMENT_DETAIL 
        add constraint FK_p6xth0elmsal9cjr2t6kojel1 
        foreign key (EQUIPMENT_ID) 
        references TB_EQUIPMENT_MASTER (EQUIPMENT_ID);

    alter table TB_EQUIPMENT_DETAIL 
        add constraint FK_mpnueniqq8mq7w53mgsbg12fe 
        foreign key (UNIT_ID) 
        references TB_UNIT_DETAIL (UNIT_ID);

    alter table TB_EQUIPMENT_MASTER 
        add constraint FK_g2knoota7mm923vp006iw706l 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_ISSUE_MASTER 
        add constraint FK_38gjt49t15rjbd8mby5pvoyl9 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_MODULE_LOG 
        add constraint FK_2c65ta99wws5dp03l51wpklon 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_PRIVILEGE 
        add constraint FK_s1rhf50ehwwjplbbe76d2ic88 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_QUESTION_MASTER 
        add constraint FK_oqwkem6mbs8r60b513ankmkw2 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_RESOLUTION_MASTER 
        add constraint FK_1p9loturrda0tvuv78wl084ef 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_ROLE_MASTER 
        add constraint FK_5561j3i7v37giyd9wdtpqo322 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_SECURITY_QUESTION 
        add constraint FK_c35mkkq60ro92y0p6n6l7kyp5 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_STATE_MASTER 
        add constraint FK_4rry69rrjrqbsk9ty7imwwm95 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_UNIT_DETAIL 
        add constraint FK_6tjg6w8bg9abmuc2hjv06gs28 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_UNIT_DETAIL 
        add constraint FK_t1kal148ybl9hd9qoj9l0p6ay 
        foreign key (ADDRESS_ID) 
        references TB_ADDRESS_DETAIL (ADDRESS_ID);

    alter table TB_UNIT_DETAIL 
        add constraint FK_gbe6skygwysmueutotfpdfu5m 
        foreign key (CUSTOMER_ID) 
        references TB_CUSTOMER_DETAIL (CUSTOMER_ID);

    alter table TB_USER 
        add constraint FK_gu03pv7i6qh7ghnfa0qpx1hc2 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_USER 
        add constraint FK_mhpb2sfs9sfu9he0dbcxvytbe 
        foreign key (DEPARTMENT_ID) 
        references TB_DEPARTMENT_MASTER (DEPARTMENT_ID);

    alter table TB_USER 
        add constraint FK_35bcb8byobtamb86us8hxa2d3 
        foreign key (DESIGNATION_ID) 
        references TB_DESIGNATION_MASTER (DESIGNATION_ID);

    alter table TB_USER_PRIVILEGE 
        add constraint FK_krcwh4jxl0p82snjqkhpf5vwt 
        foreign key (Client_Id) 
        references TB_CLIENT_MASTER (CLIENT_ID);

    alter table TB_USER_PRIVILEGE 
        add constraint FK_msf5555mxhr9f4o1eak4dkvsq 
        foreign key (PRIVILEGE_ID) 
        references TB_PRIVILEGE (PRIVILEGE_ID);

    alter table TB_USER_PRIVILEGE 
        add constraint FK_ma8cl0rlt088t91qbrblofkf6 
        foreign key (USER_ID) 
        references TB_USER (USER_ID);

    alter table TB_USER_ROLE 
        add constraint FK_8skir5otaupso5m1djsxg9nin 
        foreign key (ROLE_ID) 
        references TB_ROLE_MASTER (ROLE_ID);

    alter table TB_USER_ROLE 
        add constraint FK_6eiqgtui5tbfbpyfgl667x22h 
        foreign key (USER_ID) 
        references TB_USER (USER_ID);
