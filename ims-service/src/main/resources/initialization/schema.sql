drop table if exists mngr_auth_menu;

drop table if exists mngr_auth_operation;

drop table if exists mngr_auth_role;

drop table if exists mngr_auth_user;

drop table if exists relation_role_menu;

drop table if exists relation_role_operation;

drop table if exists relation_user_role;

create table mngr_auth_menu
(
  id                   bigint not null,
  p_id                 bigint,
  name                 varchar(16),
  url                  varchar(255),
  status               varchar(16),
  sort                 int,
  icon                 varchar(255),
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table mngr_auth_operation
(
  id                   bigint not null,
  menu_id              bigint not null,
  name                 varchar(64),
  ant_url              varchar(255),
  type                 varchar(16),
  sort                 int,
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table mngr_auth_role
(
  id                   bigint not null,
  p_id                 bigint,
  name                 varchar(64),
  status               varchar(16),
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table mngr_auth_user
(
  id                   bigint not null,
  name                 varchar(32),
  account              varchar(16),
  password             varchar(64),
  tel                  varchar(16),
  avatar_url           varchar(255),
  status               varchar(16),
  create_at            datetime,
  last_login_at        datetime,
  remark               varchar(255),
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table relation_role_menu
(
  id                   bigint not null,
  role_id              bigint not null,
  menu_id              bigint,
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table relation_role_operation
(
  id                   bigint not null,
  role_id              bigint,
  op_id                bigint not null,
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

create table relation_user_role
(
  id                   bigint not null,
  user_id              bigint not null,
  role_id              bigint not null,
  primary key (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

INSERT INTO `leon_test`.`mngr_auth_user` (`id`, `name`, `account`, `password`, `tel`, `avatar_url`, `status`, `create_at`, `last_login_at`, `remark`) VALUES ('1', 'leon', 'leon_test', 'eeafb716f93fa090d7716749a6eefa72', NULL, NULL, 'AVAILABLE', NULL, NULL, NULL);
