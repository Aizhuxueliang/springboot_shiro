-- test.permission definition

CREATE TABLE permission (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COMMENT='权限';


-- test.`role` definition

CREATE TABLE role (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='角色';


-- test.role_permission definition

CREATE TABLE role_permission (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `permission_id` int DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COMMENT='角色-权限';


-- test.`user` definition

CREATE TABLE user (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `reserve` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='用户';


-- test.user_role definition

CREATE TABLE user_role (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COMMENT='用户-角色';

INSERT INTO permission (name,description) VALUES
	 ('video_update','/api/video/update'),
	 ('video_delete','/api/video/delete'),
	 ('video_add','/api/video/add'),
	 ('order_list','/api/order/list'),
	 ('user_list','/api/user/list');
INSERT INTO role (name,description) VALUES
	 ('admin','系统管理员'),
	 ('root','超级管理员'),
	 ('user','普通用户'),
	 ('role1','角色1');
INSERT INTO role_permission (role_id,permission_id) VALUES
	 (1,1),
	 (1,2),
	 (2,1),
	 (2,2),
	 (2,3),
	 (2,4),
	 (2,5),
	 (3,5),
	 (3,4);
INSERT INTO user (username,password,reserve) VALUES
	 ('laochen','123',NULL),
	 ('laowang','456',NULL),
	 ('laoli','789',NULL);
INSERT INTO user_role (role_id,user_id,remark) VALUES
	 (1,1,'laochen是系统管理员'),
	 (3,3,'laoli是普通用户'),
	 (2,2,'laowang是超级管理员'),
	 (1,2,'laowang是系统管理员');