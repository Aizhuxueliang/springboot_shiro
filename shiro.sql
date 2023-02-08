-- test.permission definition

CREATE TABLE `permission` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
    `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='权限';


-- test.`role` definition

CREATE TABLE `role` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
    `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COMMENT='角色';


-- test.role_permission definition

CREATE TABLE `role_permission` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` int DEFAULT NULL COMMENT '角色id',
    `permission_id` int DEFAULT NULL COMMENT '权限id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb3 COMMENT='角色-权限';


-- test.`user` definition

CREATE TABLE `user` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
    `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
    `reserve` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '保留字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8mb3 COMMENT='用户';


-- test.user_role definition

CREATE TABLE `user_role` (
    `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` int DEFAULT NULL COMMENT '角色id',
    `user_id` int DEFAULT NULL COMMENT '用户id',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb3 COMMENT='用户-角色';


INSERT INTO test.permission (name,description) VALUES
('query_user','权限1、用户查询'),
('add_user','权限2、新建用户_用户查询'),
('remove_user','权限3、删除用户_用户查询'),
('allot_roles','权限4、角色查询_分配角色_用户查询'),
('query_role','权限5、查询角色'),
('add_role','权限6、新建角色_角色查询'),
('remove_role','权限7、删除角色_角色查询'),
('allot_permission','权限8、权限查询_分配权限_角色查询');

INSERT INTO test.`role` (name,description) VALUES
('admin','系统管理员'),
('root','超级管理员'),
('user','普通用户'),
('role1','角色1'),
('role2','角色2'),
('role3','角色3'),
('role4','角色4'),
('role5','角色5');

INSERT INTO test.role_permission (role_id,permission_id) VALUES
(1,7),
(1,6),
(1,9),
(3,6),
(8,13),
(2,9),
(2,7),
(2,6),
(2,10),
(2,8);

INSERT INTO test.role_permission (role_id,permission_id) VALUES
(2,11),
(2,13),
(2,12),
(10,7),
(10,8),
(10,6),
(11,8),
(4,13),
(4,11);

INSERT INTO test.`user` (username,password,reserve) VALUES
('laoer','123','老二'),
('laowang','laowang',''),
('laozhang','123',''),
('laozhu','123',''),
('laowu','123',''),
('laoliu','123',''),
('laoqi','123',''),
('dana','123',''),
('laopi','123','');

INSERT INTO test.user_role (role_id,user_id,remark) VALUES
(11,120,'laoer是角色5'),
(2,174,'dana是超级管理员'),
(2,158,'laozhu是超级管理员'),
(4,161,'laoqi是角色1'),
(1,184,'laopi是系统管理员'),
(9,159,'laowu是角色3'),
(10,160,'laoliu是角色4'),
(4,152,'laowang是角色1');