drop table if exists ims_menu;

drop table if exists ims_operation;

drop table if exists ims_operation_log;

drop table if exists ims_role;

drop table if exists ims_user;

drop table if exists ims_relation_role_menu;

drop table if exists ims_relation_role_operation;

drop table if exists ims_relation_user_role;

CREATE TABLE `ims_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `p_id` bigint(20) DEFAULT NULL COMMENT '父菜单id',
  `name` varchar(30) DEFAULT NULL COMMENT '菜单名',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单标识',
  `status` varchar(16) DEFAULT NULL COMMENT '菜单状态',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `ims_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  `name` varchar(64) DEFAULT NULL COMMENT '操作名称',
  `ant_url` varchar(255) DEFAULT NULL COMMENT '拦截地址',
  `type` varchar(30) DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能操作表';

CREATE TABLE `ims_operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) DEFAULT NULL COMMENT '操作内容',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(20) DEFAULT NULL COMMENT '操作ip',
  `description` varchar(50) DEFAULT NULL COMMENT '操作描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作表日志表';

CREATE TABLE `ims_relation_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色与菜单关联表';
CREATE TABLE `ims_relation_role_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `operation_id` bigint(20) DEFAULT NULL COMMENT '操作id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与操作关联表';

CREATE TABLE `ims_relation_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户与角色关联表';

CREATE TABLE `ims_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `status` varchar(16) DEFAULT NULL COMMENT '角色状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `ims_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `account` varchar(16) DEFAULT NULL COMMENT '账号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `tel` varchar(16) DEFAULT NULL COMMENT '电话',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像url',
  `status` varchar(16) DEFAULT NULL COMMENT '用户状态',
  `create_at` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_at` datetime DEFAULT NULL COMMENT '最后登录时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';
