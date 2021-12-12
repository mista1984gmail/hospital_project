create database hospital_test4;
create table hospital_test4.user
(
    id              bigint primary key auto_increment,
    first_name      varchar(255) not null,
    last_name       varchar(255) not null,
    birthday 		date,
    address			varchar(255) not null,
    email			varchar(255) not null,
    telephone		varchar(255) not null,
    name_user			varchar(128) not null,
    password		varchar(128) not null
);

create table hospital_test4.role
(
    id              bigint primary key auto_increment,
    name     		varchar(255) not null
);

create table hospital_test4.user_roles
(
    user_id         bigint,
    roles_id     	bigint,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
        REFERENCES user(id),
    CONSTRAINT fk_role FOREIGN KEY (roles_id)
        REFERENCES role(id)
);
INSERT INTO `hospital_test4`.`role` (`name`) VALUES ('ROLE_USER');
INSERT INTO `hospital_test4`.`role` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `hospital_test4`.`role` (`name`) VALUES ('ROLE_DOCTOR');
INSERT INTO `hospital_test4`.`role` (`name`) VALUES ('ROLE_NURSE');

create table hospital_test4.category
(
    id        bigint primary key auto_increment,
    name      varchar(255) not null
);
create table hospital_test4.type
(
    id        bigint primary key auto_increment,
    name      varchar(255) not null,
    category_id  bigint,
    CONSTRAINT fk_category FOREIGN KEY (category_id)
        REFERENCES category(id)
);


