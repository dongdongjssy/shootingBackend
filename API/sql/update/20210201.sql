DROP TABLE IF EXISTS `shooting_honor`;
CREATE TABLE `shooting_honor` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `title` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "标题", "textSearchOpt":"1"}',
  `picture_url` varchar(250) NOT NULL COMMENT '{"title": "图片", "uploadPic":"1"}',
  `sort` int COMMENT '{"title": "排序"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='荣誉榜管理';


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('荣誉榜管理', '7', '21', '/shooting//honor', 'C', '0', 'shooting:honor:view', '#', 'admin', now(), null, null, '荣誉榜管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('荣誉榜管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:honor:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('荣誉榜管理新增', @parentId, '2',  '#',  'F', '0', 'shooting:honor:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('荣誉榜管理修改', @parentId, '3',  '#',  'F', '0', 'shooting:honor:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('荣誉榜管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:honor:remove',       '#', 'admin', now(), null, null, '');


alter table client_role add picture_url varchar (255) ;
alter table client_role add sort int(16) ;



DROP TABLE IF EXISTS `shooting_training_intention`;
CREATE TABLE `shooting_training_intention` (
		`id`                  BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '培训意向id',
    `email`               VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "邮箱", "searchable": "1", "textSearchOpt":"1"}',
    `phone`               VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "手机号", "searchable": "1", "textSearchOpt":"1"}',
    `open_id`             VARCHAR(50)                                             DEFAULT NULL COMMENT '{"title": "用户微信", "searchable": "1"}',
    `city`                VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "城市", "searchable": "1"}',
    `id_number`           VARCHAR(20)                                             DEFAULT NULL COMMENT '{"title": "身份证号", "searchable": "1"}',
    `real_name`           VARCHAR(20)                                             DEFAULT NULL COMMENT '{"title": "真实姓名", "searchable": "1"}',
    `age`                 INT(3)                                                  DEFAULT NULL COMMENT '{"title": "年龄"}',
    `training_type`                TINYINT(1)                                              DEFAULT NULL COMMENT '{"title": "培训类型", "searchable": "1"}',
    `training_time`                TINYINT(1)                                              DEFAULT NULL COMMENT '{"title": "培训时间", "searchable": "1"}',
    `remark`              VARCHAR(3)                                              DEFAULT NULL COMMENT '{"title": "备注"}',

  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='培训意向管理';


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训意向管理', '7', '22', '/shooting/trainingIntention', 'C', '0', 'shooting:trainingIntention:view', '#', 'admin', now(), null, null, '培训意向管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训意向管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:trainingIntention:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训意向管理修改', @parentId, '3',  '#',  'F', '0', 'shooting:trainingIntention:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训意向管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:trainingIntention:remove',       '#', 'admin', now(), null, null, '');


insert into sys_dict_type values(null,  '培训类型', 'training_type',     '0', 'admin', now(), null, null, '培训类型');
insert into sys_dict_data values(null,  1,  '气枪',  '0',  'training_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '培训类型-气枪');
insert into sys_dict_data values(null,  2,  '实弹',  '1',  'training_type',   '',   '',   'N', '0', 'admin', now(), null, null, '培训类型-实弹');


insert into sys_dict_type values(null,  '培训时间', 'training_time',     '0', 'admin', now(), null, null, '培训时间');
insert into sys_dict_data values(null,  1,  '一个月内',  '0',  'training_time',   '',   '',   'Y', '0', 'admin', now(), null, null, '培训时间-一个月内');
insert into sys_dict_data values(null,  2,  '三个月内',  '1',  'training_time',   '',   '',   'N', '0', 'admin', now(), null, null, '培训时间-三个月内');
insert into sys_dict_data values(null,  3,  '半年内',  '2',  'training_time',   '',   '',   'N', '0', 'admin', now(), null, null, '培训时间-半年内');


alter table shooting_training_intention add client_user_id BIGINT(16) ;
