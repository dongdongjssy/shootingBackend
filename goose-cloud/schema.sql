
DROP TABLE IF EXISTS `client_role`;

CREATE TABLE `client_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nameZh` varchar(11) NOT NULL DEFAULT '' COMMENT '角色名称：管理员；用户',
  `name` varchar(11) DEFAULT NULL COMMENT '角色：ROLE_ADMIN;ROLE_USER',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `client_role` (`id`, `nameZh`, `name`)
VALUES
  (1,'用户','ROLE_USER'),
  (2,'管理员','ROLE_ADMIN');



DROP TABLE IF EXISTS `client_user_role`;

CREATE TABLE `client_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `client_user`;

CREATE TABLE `client_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) NOT NULL DEFAULT '',
  `nickname` varchar(11) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL DEFAULT '',
  `mobile`   varchar(11) 	DEFAULT '',	
  `email`    varchar(50)    DEFAULT '',
  `password_reset_date`  DATETIME  NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

