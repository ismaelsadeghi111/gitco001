
   --
 create table address (
        address_id integer not null auto_increment,
        address_city varchar(255),
        address_country varchar(255),
        address_creation_date datetime,
        address_number varchar(255),
        address_street varchar(255),
        customer_id integer,
        primary key (address_id)
    );

    create table customer (
        customer_id integer not null auto_increment,
        customer_date datetime,
        customer_email varchar(255),
        customer_password varchar(255),
        customer_user_name varchar(255),
        customer_firstname varchar(255),
        customer_lastname varchar(255),
        customer_mobilenumber varchar(255),
        customer_status varchar(255),
        primary key (customer_id)
    );

    create table invoice (
        invoice_id integer not null auto_increment,
        invoice_creation_date datetime,
        invoice_number integer,
        customer_id integer,
        primary key (invoice_id)
    );

    create table news (
        news_id integer not null auto_increment,
        body text,
        category varchar(255),
        creation_date datetime,
        status varchar(255),
        title varchar(255),
        primary key (news_id)
    );

    create table newshistory (
        news_history_id integer not null auto_increment,
        action varchar(255),
        administrator_id integer,
        body text,
        category varchar(255),
        creation_date datetime,
        ip varchar(255),
        news_id integer,
        status varchar(255),
        title varchar(255),
        primary key (news_history_id)
    );

     CREATE TABLE rights (
       right_id  int(11) NOT NULL auto_increment,
       right_action  varchar(255) default NULL,
       right_url  varchar(255) default NULL,
       description text default NULL,
       creation_date datetime,
     PRIMARY KEY  ( right_id )
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    CREATE TABLE  role  (
      role_id  int(11) NOT NULL auto_increment,
      creation_date  datetime default NULL,
      role_name  varchar(255) default NULL,
      PRIMARY KEY  ( role_id )
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    create table user (
        user_id integer not null auto_increment,
        user_email varchar(255),
        user_mobilenumber varchar(255),
        user_password varchar(255),
        user_status varchar(255),
        user_name varchar(255),
        user_role varchar(255),
        creation_date datetime,
        primary key (user_id)
    );
    
    CREATE TABLE  user_role  (
     user_role_id  int(11) NOT NULL auto_increment,
     user_id  int(11) default NULL,
     role_id  int(11) default NULL,
     creation_date datetime,
     PRIMARY KEY  ( user_role_id ),
     KEY  FK143BF46A18461549  ( role_id ),
     KEY  FK143BF46ABD70D929  ( user_id ),
     CONSTRAINT  FK143BF46A18461549  FOREIGN KEY ( role_id ) REFERENCES  role  ( role_id ),
     CONSTRAINT  FK143BF46ABD70D929  FOREIGN KEY ( user_id ) REFERENCES  user  ( user_id )
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


   CREATE TABLE  role_right  (
     role_right_id  int(11) NOT NULL auto_increment,
     role_id  int(11) NOT NULL,
     right_id  int(11) NOT NULL,
     creation_date datetime,
     PRIMARY KEY  ( role_right_id ),
     KEY  FK6CC8F51318461549  ( role_id ),
     KEY  FK6CC8F513AABFD6CB  ( right_id ),
    CONSTRAINT  FK6CC8F513AABFD6CB  FOREIGN KEY ( right_id ) REFERENCES  rights  ( right_id ),
    CONSTRAINT  FK6CC8F51318461549  FOREIGN KEY ( role_id ) REFERENCES  role  ( role_id )
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


   CREATE TABLE permission (
     permission_id int(11) NOT NULL auto_increment,
     creation_date datetime default NULL,
     permission_name varchar(255) default NULL,
     permission_value int(11) default NULL,
     PRIMARY KEY  (permission_id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

   CREATE TABLE permission_right (
    permission_right_id int(11) NOT NULL auto_increment,
    creation_date datetime default NULL,
    right_id int(11) NOT NULL,
    permission_id int(11) NOT NULL,
    PRIMARY KEY  (permission_right_id),
    KEY FK4BBC13AC35D7369 (permission_id),
    KEY FK4BBC13ACAABFD6CB (right_id),
    CONSTRAINT FK4BBC13ACAABFD6CB FOREIGN KEY (right_id) REFERENCES rights (right_id),
    CONSTRAINT FK4BBC13AC35D7369 FOREIGN KEY (permission_id) REFERENCES permission (permission_id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    CREATE TABLE userhistory (
        user_history_id int(11) NOT NULL auto_increment,
        doer_ip varchar(255) default NULL,
        doer_ip_city varchar(255) default NULL,
        doer_ip_country varchar(255) default NULL,
        user_history_email varchar(255) default NULL,
        user_history_password varchar(255) default NULL,
        status varchar(255) default NULL,
        user_histories_status varchar(255) default NULL,
        user_id int(11) default NULL,
        user_history_username varchar(255) default NULL,
        PRIMARY KEY  (user_history_id)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE systemactivity (
       systemactivity_id int(11) NOT NULL auto_increment,
       activity varchar(255) default NULL,
       activity_date datetime default NULL,
       activity_object varchar(255) default NULL,
       activity_desc  text,
       doer_id  int(11) default NULL,
       doer_ip  varchar(255) default NULL,
       doer_ip_country  varchar(255) default NULL,
       doer_ip_city  varchar(255) default NULL,
       doer_type  varchar(255) default NULL,
       incoming_url  text,
       activity_object_id  varchar(255) default NULL,
       outgoing_url  text,
       query text,
       reference_1 int(11) default NULL,
       reference_2 int(11) default NULL,
       reference_3 int(11) default NULL,
       reference_4 int(11) default NULL,
       activity_type varchar(255) default NULL,
        PRIMARY KEY  (systemactivity_id)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

      CREATE TABLE transaction (
        transaction_id int(11) NOT NULL auto_increment,
        amount double default NULL,
        creation_date datetime default NULL,
        description text,
        refrence_number varchar(255) default NULL,
        reversed_description text,
        reversed_refrence_number varchar(255) default NULL,
        status varchar(255) default NULL,
        verified_amount double default NULL,
        verify_retry_number int(11) default NULL,
        customer_id int(11) default NULL,
        user_id int(11) default NULL,
        PRIMARY KEY  (transaction_id),
        KEY FK7FA0D2DEBD70D929 (user_id),
        KEY FK7FA0D2DE5F42BCC9 (customer_id),
        CONSTRAINT FK7FA0D2DE5F42BCC9 FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
        CONSTRAINT FK7FA0D2DEBD70D929 FOREIGN KEY (user_id) REFERENCES user (user_id)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

       CREATE TABLE transactionactivity (
        transactionactivity_id int(11) NOT NULL auto_increment,
        creation_date datetime default NULL,
        refNum varchar(255) default NULL,
        result_code int(11) default NULL,
        result_description varchar(255) default NULL,
        state varchar(255) default NULL,
        type varchar(255) default NULL,
        transaction_id int(11) default NULL,
        PRIMARY KEY  (transactionactivity_id),
        KEY FKCD8CAB50364E16CB (transaction_id),
        CONSTRAINT FKCD8CAB50364E16CB FOREIGN KEY (transaction_id) REFERENCES transaction (transaction_id)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    alter table address
        add index FK34207BA25F42BCC9 (customer_id),
        add constraint FK34207BA25F42BCC9
        foreign key (customer_id)
        references customer (customer_id);

    alter table invoice
        add index FK25F222E65F42BCC9 (customer_id),
        add constraint FK25F222E65F42BCC9
        foreign key (customer_id)
        references customer (customer_id);
