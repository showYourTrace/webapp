CREATE TABLE  USER_OBJECT (
 user_id BIGSERIAL NOT NULL,
 name VARCHAR(255) DEFAULT NULL,
 second_name VARCHAR(255) DEFAULT NULL,
 login VARCHAR(255) NOT NULL,
 pwd VARCHAR(512) NOT NULL,
 email VARCHAR(254) NOT NULL,
 receive_promo INT DEFAULT 0 NOT NULL,
 is_admin INT DEFAULT 0 NOT NULL,
 registered_user INT DEFAULT 0 NOT NULL,
 confirm_id VARCHAR(254),
 confirm_id_created_at TIMESTAMP ,
 created_by VARCHAR(255)NOT NULL,
 created_date TIMESTAMP  NOT NULL,
 changed_by VARCHAR(255) DEFAULT NULL,
 changed_date TIMESTAMP  DEFAULT NULL,
 deleted_by VARCHAR(255)DEFAULT NULL,
 deleted_date TIMESTAMP  DEFAULT NULL,
 is_deleted INT DEFAULT 0,
 PRIMARY KEY(user_id)
);
ALTER TABLE USER_OBJECT ADD COLUMN ACCESS_TOKEN VARCHAR (50);

ALTER TABLE USER_OBJECT ADD CONSTRAINT USER_LOGIN_UNQ UNIQUE (login);
ALTER TABLE USER_OBJECT ADD CONSTRAINT USER_EMAIL_UNQ UNIQUE (email);

INSERT INTO USER_OBJECT(user_id, name, login, pwd, email, created_by, created_date, is_admin, registered_user) VALUES(1, 'superuser', 'root', '$2a$10$uE7JqzeeFcQpWBToloc4DOYwF2oOYOF0IGFKZzfM/Lk3vr66W148K', 'cat_dz@mail.ru', 'ROOT', current_date, 1, 1);


CREATE TABLE  MAILING (
  mailing_id BIGSERIAL NOT NULL,
  subject VARCHAR(78) NOT NULL,
  body VARCHAR(2000) NOT NULL,
  created_date TIMESTAMP NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  PRIMARY KEY(mailing_id)
);

CREATE TABLE  MAILING_ATTACHMENT (
  attachment_id BIGSERIAL NOT NULL,
  mailing_id BIGINT NOT NULL,
  file_url VARCHAR(2000) NOT NULL,
  PRIMARY KEY(attachment_id),
  FOREIGN KEY (mailing_id) REFERENCES    MAILING(mailing_id)
);

CREATE TABLE FILE_OBJECT (
  file_id BIGSERIAL NOT NULL,
  name VARCHAR(255) NULL,
  url VARCHAR(255) NULL,
  PRIMARY KEY (file_id));
