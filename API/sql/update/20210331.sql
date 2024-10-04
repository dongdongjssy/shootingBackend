DROP TABLE IF EXISTS `shooting_user_integral`;
CREATE TABLE `shooting_user_integral` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50)   NOT NULL DEFAULT'' COMMENT '{"title": "姓名", "searchable": "1"}',
  `member` varchar(50) NOT NULL DEFAULT'' COMMENT '{"title": "射手卡编号", "searchable": "1"}',
  `age_group` varchar(50) COMMENT '{"title": "年龄组"}',
  `gun_group` varchar(50) COMMENT '{"title": "枪组"}',
  `total_score` varchar(50) COMMENT '{"title": "总积分"}',
  `team_no` varchar(50) COMMENT '{"title": "团队编号"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户电子积分卡表';


DROP TABLE IF EXISTS `shooting_user_integral_detail`;
CREATE TABLE `shooting_user_integral_detail` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50)   NOT NULL DEFAULT'' COMMENT '{"title": "姓名", "searchable": "1"}',
  `member` varchar(50) NOT NULL DEFAULT'' COMMENT '{"title": "射手卡编号", "searchable": "1"}',
  `contest` varchar(50) COMMENT '{"title": "赛事"}',
  `ranking` varchar(50) COMMENT '{"title": "名称"}',
  `score` varchar(50) COMMENT '{"title": "积分"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户电子积分卡明细表';


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡管理', '0', '94', '#', 'M', '0', '', 'fa fa-bar-chart', 'admin', now(), null, null, '积分卡管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡用户',  @parentId, '1', '/shooting/userIntegral', 'C', '0', 'shooting:userIntegral:view', '#', 'admin', now(), null, null, '积分卡用户');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡用户查询', @parentId, '1',  '#',  'F', '0', 'shooting:userIntegral:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡用户新增', @parentId, '2',  '#',  'F', '0', 'shooting:userIntegral:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡用户修改', @parentId, '3',  '#',  'F', '0', 'shooting:userIntegral:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡用户删除', @parentId, '4',  '#',  'F', '0', 'shooting:userIntegral:remove',       '#', 'admin', now(), null, null, '');





-- 按钮父菜单ID
SELECT @parentMenuId := menu_id from sys_menu where menu_name = '积分卡管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡明细',  @parentId, '2', '/shooting/UserIntegralDetail', 'C', '0', 'shooting:UserIntegralDetail:view', '#', 'admin', now(), null, null, '积分卡明细');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡明细查询', @parentId, '1',  '#',  'F', '0', 'shooting:UserIntegralDetail:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡明细新增', @parentId, '2',  '#',  'F', '0', 'shooting:UserIntegralDetail:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡明细修改', @parentId, '3',  '#',  'F', '0', 'shooting:UserIntegralDetail:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分卡明细删除', @parentId, '4',  '#',  'F', '0', 'shooting:UserIntegralDetail:remove',       '#', 'admin', now(), null, null, '');




-- 按钮父菜单ID
SELECT @parentMenuId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品轮播图',  @parentId, '6', '/shooting/goodsCarousel', 'C', '0', 'shooting:goodsCarousel:view', '#', 'admin', now(), null, null, '商品轮播图');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品轮播图查询', @parentId, '1',  '#',  'F', '0', 'shooting:goodsCarousel:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品轮播图新增', @parentId, '2',  '#',  'F', '0', 'shooting:goodsCarousel:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品轮播图修改', @parentId, '3',  '#',  'F', '0', 'shooting:goodsCarousel:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品轮播图删除', @parentId, '4',  '#',  'F', '0', 'shooting:goodsCarousel:remove',       '#', 'admin', now(), null, null, '');

