DROP TABLE fm_reservations;
DROP TABLE fm_feature_sets;
DROP TABLE fm_rooms;
DROP TABLE fm_employees;
DROP TABLE fm_features;
DROP TABLE fm_locations;
DROP SEQUENCE location_ids;
DROP SEQUENCE feature_ids;
DROP SEQUENCE room_ids;
DROP SEQUENCE reservation_ids;

create sequence feature_ids
start with 1
increment by 1;

create sequence location_ids
start with 1
increment by 1;

create sequence room_ids
start with 1
increment by 1;

create sequence reservation_ids
start with 1
increment by 1;

create sequence reservation_group_ids
start with 1
increment by 1;

create table fm_features(
  fid numeric primary key,
  fdesc varchar(20) not null unique
);

create table fm_locations(
  loc_id numeric primary key,
  loc_street varchar(64) not null,
  loc_city varchar(25) not null,
  loc_state char(2),
  loc_country char(3) not null
);

create table fm_employees(
  eid numeric primary key,
  ename varchar(40) not null,
  email varchar(60) not null unique,
  eauthtype char(1) not null,
  e_home_loc references fm_locations(loc_id)
);

create table fm_rooms(
  room_id numeric primary key,
  room_name varchar(16) not null,
  room_desc varchar(128) not null,
  room_category char(1) not null,
  room_photo_url varchar(128),
  room_loc not null references fm_locations(loc_id)
);

create table fm_feature_sets(
  fid references fm_features(fid),
  room_id references fm_rooms(room_id),
  primary key (fid, room_id),
  qty numeric not null
);

create table fm_reservations (
  res_id numeric primary key,
  res_request_time timestamp not null,
  res_start timestamp not null,
  res_end timestamp not null,
  res_purpose varchar(64) not null,
  res_comment varchar(256),
  res_rating numeric,
  res_status char(1),
  res_group numeric not null,
  res_room numeric not null references fm_rooms(room_id),
  res_emp numeric not null references fm_employees(eid)
);


INSERT INTO fm_locations VALUES(location_ids.nextval, '255 Schilling Boulevard Suite 101', 'Collierville', 'TN', 'USA');
INSERT INTO fm_locations VALUES(location_ids.nextval, '205 Reidhurst Ave #200', 'Nashville', 'TN', 'USA');
INSERT INTO fm_locations VALUES(location_ids.nextval, '2902 West Agua Fria Freeway Suite 1020', 'Phoenix', 'AZ', 'USA');
INSERT INTO fm_locations VALUES(location_ids.nextval, '2nd Floor, 145 St Vincent Street','Glasgow',null,'UK');

INSERT INTO fm_employees VALUES(100, 'Gabriel', 'gabriel_torres2@syntelinc.com','U', 1);
INSERT INTO fm_employees VALUES(101, 'John', 'haohua_yu@syntelinc.com','U', 1);
INSERT INTO fm_employees VALUES(102, 'Andrew', 'andrew_thomas@syntelinc.com','A', 3);
INSERT INTO fm_employees VALUES(103, 'Jose', 'jose_villa@syntelinc.com','U', 3);
INSERT INTO fm_employees VALUES(104, 'Chris', 'christopher_peterson@syntelinc.com','U', 3);
INSERT INTO fm_employees VALUES(105, 'Mahesh', 'mahesh_rajput@syntelinc.com','A', 1);

INSERT INTO fm_features VALUES(feature_ids.nextval,'CHAIR');
INSERT INTO fm_features VALUES(feature_ids.nextval,'PROJECTOR');
INSERT INTO fm_features VALUES(feature_ids.nextval,'WHITEBOARD');
INSERT INTO fm_features VALUES(feature_ids.nextval,'DESKTOP');
INSERT INTO fm_features VALUES(feature_ids.nextval,'TELEVISION');

commit;