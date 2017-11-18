-- password: password

INSERT INTO  user (user_id , user_email , user_password, user_mobilenumber, user_status, user_name, creation_date
)
VALUES
(1, 'admin@sefr-yek.com', '5f4dcc3b5aa765d61d8327deb882cf99', '', 'ACTIVE', 'administrator', now()),
(2, 'superuser@sefr-yek.com', '5f4dcc3b5aa765d61d8327deb882cf99','', 'ACTIVE', 'superUser', now()),
(3, 'user1@sefr-yek.com', '5f4dcc3b5aa765d61d8327deb882cf99', '','ACTIVE', 'user1', now()),
(4, 'user2@sefr-yek.com', '5f4dcc3b5aa765d61d8327deb882cf99','09129999999', 'ACTIVE', 'user2', now()),
(5, 'user3@sefr-yek.com', '5f4dcc3b5aa765d61d8327deb882cf99', '','ACTIVE', 'user3', now())
;

INSERT INTO  role (role_id , role_name, creation_date) VALUES
(1, 'ROLE_ADMIN', now()),
(2, 'ROLE_SUPERUSER', now()),
(3, 'ROLE_USER', now()), -- ADD
(4, 'ROLE_OPERATOR', now()),
(5, 'ROLE_USER_POWERFULL', now());
--ADD/EDIT

INSERT INTO  user_role (user_role_id, user_id, role_id, creation_date ) VALUES
(1, 1, 1, now()),
(2, 2, 2, now()),
(3, 3, 3, now()),
(4, 4, 1, now()),
(5, 4, 2, now()),
(6, 4, 3, now()),
(7, 4, 4, now()),
(9, 5, 1, now()),
(10, 5, 2, now());

INSERT INTO  rights (right_id , description, right_action , right_url, creation_date) VALUES
(1, 'user', '', '/en/user.do*', now()),
(2, 'super user', '','/en/superUserAuthentication.do*', now()),
(3, 'super user ALL rights', '','/en/superUser.do*', now()),
(4, 'admin ALL rights','', '/en/admin.do*', now()),
(5, 'admin-superuser', '','/en/adminAuthentication.do*', now()),
(6, 'customer invoice','', '/customer/invoice.do*', now()),
(7, 'role','', '/en/role.do*', now()),
(8, 'right','', '/en/right.do*', now());


INSERT INTO  role_right (role_right_id, role_id, right_id, creation_date ) VALUES
(1, 1, 1, now()),
(2, 2, 1, now()),
(3, 2, 2, now()),
(4, 2, 3, now()),
(5, 4, 1, now()),
(6, 4, 2, now()),
(7, 4, 3, now()),
(8, 3, 6, now()),
(9, 2, 7, now()),
(10, 2, 8, now());

INSERT INTO  permission (permission_id, permission_name, permission_value, creation_date) VALUES
(1, 'ADMINISTRATION', 1, now()),
(2, 'READ', 2, now()),
(3, 'WRITE', 4, now()),
(4, 'DELETE', 6, now()),
(5, 'CREATE', 8, now());

INSERT INTO  permission_right (permission_right_id, right_id, permission_id, creation_date ) VALUES
(1, 1, 1, now()),
(2, 1, 2, now()),
(3, 8, 1, now());

