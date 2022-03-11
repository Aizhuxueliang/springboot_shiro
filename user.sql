-- sys.`user` definition
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `user_id` int NOT NULL COMMENT '用户ID',
    `user_pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
    `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
    `user_role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户角色',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


INSERT INTO sys.`user`
(user_id, user_pwd, user_name, user_role)
VALUES(15, 'qwert1234', '王老三', 'AAA');
INSERT INTO sys.`user`
(user_id, user_pwd, user_name, user_role)
VALUES(16, 'qwert1234', '张小四', 'BBB');
INSERT INTO sys.`user`
(user_id, user_pwd, user_name, user_role)
VALUES(59, 'qwert1234', '反对', 'BBB');
INSERT INTO sys.`user`
(user_id, user_pwd, user_name, user_role)
VALUES(70, 'qwert1234', '张小子', 'BBB');
