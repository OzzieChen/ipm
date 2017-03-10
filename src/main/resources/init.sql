#
# Structure for table "resource"
#

DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `res_key` varchar(20) DEFAULT NULL,
  `parent_ids` varchar(40) DEFAULT NULL,
  `res_url` varchar(100) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `type` varchar(10) NOT NULL DEFAULT 'button',
  `openWay` varchar(1) NOT NULL DEFAULT 'I',
  `ordr` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `res_res_key` (`res_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "resource"
#

INSERT INTO `resource` VALUES (1,'扫描控制',0,NULL,'1',NULL,NULL,NULL,'menu','D',1000),(11,'自动机列表',1,'','1,11','','','','menu','D',1),(12,'添加自动机',1,'','1,12','','','','menu','D',2),(13,'任务列表',1,'','1,13','','','','menu','D',3);

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `credentials_salt` varchar(100) NOT NULL,
  `create_time` date DEFAULT NULL,
  `status` varchar(1) NOT NULL DEFAULT 'E' COMMENT '逻辑删除状态E:存在D:删除',
  `sex` varchar(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_test_user1_email` (`email`),
  UNIQUE KEY `uk_test_user1_phone` (`phone`),
  UNIQUE KEY `uk_test_user1_un` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

#
# Data for table "user"
#
