
-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `url` varchar(255) DEFAULT NULL COMMENT '路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, 'video_update', '/api/video/update');
INSERT INTO `permission` VALUES (2, 'video_delete', '/api/video/delete');
INSERT INTO `permission` VALUES (3, 'video_add', '/api/video/add');
INSERT INTO `permission` VALUES (4, 'order_list', '/api/order/list');
INSERT INTO `permission` VALUES (5, 'user_list', '/api/user/list');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'admin', '系统管理员');
INSERT INTO `role` VALUES (2, 'root', '超级管理员');
INSERT INTO `role` VALUES (3, 'user', '普通用户');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色-权限';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 1, 2);
INSERT INTO `role_permission` VALUES (3, 2, 1);
INSERT INTO `role_permission` VALUES (4, 2, 2);
INSERT INTO `role_permission` VALUES (5, 2, 3);
INSERT INTO `role_permission` VALUES (6, 2, 4);
INSERT INTO `role_permission` VALUES (7, 2, 5);
INSERT INTO `role_permission` VALUES (8, 3, 5);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `salt` varchar(255) DEFAULT NULL COMMENT '加盐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'laochen', '123', NULL, NULL);
INSERT INTO `user` VALUES (2, 'laowang', '456', NULL, NULL);
INSERT INTO `user` VALUES (3, 'laoli', '789', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色-用户关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, 1, 1, 'laochen是系统管理员');
INSERT INTO `user_role` VALUES (2, 2, 2, 'laowang是超级管理员');
INSERT INTO `user_role` VALUES (3, 3, 3, 'laoli是普通用户');
INSERT INTO `user_role` VALUES (4, 1, 2, 'laowang是系统管理员');
COMMIT;