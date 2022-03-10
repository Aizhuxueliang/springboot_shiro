-- sys.`user` definition
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int NOT NULL COMMENT '用户ID',
  `userPwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
  `userName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `userRole` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户角色',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


INSERT INTO sys.`user`
(userId, userPwd, userName, userRole)
VALUES(15, 'qwert1234', '王老五', 'AAA');
INSERT INTO sys.`user`
(userId, userPwd, userName, userRole)
VALUES(16, 'qwert1234', '张小四', 'BBB');
INSERT INTO sys.`user`
(userId, userPwd, userName, userRole)
VALUES(59, 'qwert1234', '李小三', 'BBB');
INSERT INTO sys.`user`
(userId, userPwd, userName, userRole)
VALUES(70, 'qwert1234', '马大帅', 'BBB');