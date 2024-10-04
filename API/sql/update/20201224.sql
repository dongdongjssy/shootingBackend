-- 创建访客表 --
CREATE TABLE `shooting_visitor` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(20) DEFAULT NULL COMMENT '{"title": "邮箱", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
  `captcha` varchar(10) DEFAULT NULL COMMENT '{"title": "验证码", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `login_time` datetime DEFAULT NULL COMMENT '{"title": "登陆时间", "searchable": "0", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='访客表';

-- 访客表 - shooting_visitor  开始
-- 菜单 SQL
SELECT @parentMenuId := menu_id from sys_menu where menu_name = '客户端用户管理';

insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('访客', @parentMenuId, '1', '/shooting/visitor', 'C', '0', 'shooting:visitor:view', '#', 'admin', now(), null, null, '访客菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('访客查询', @parentId, '1',  '#',  'F', '0', 'shooting:visitor:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('访客新增', @parentId, '2',  '#',  'F', '0', 'shooting:visitor:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('访客修改', @parentId, '3',  '#',  'F', '0', 'shooting:visitor:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('访客删除', @parentId, '4',  '#',  'F', '0', 'shooting:visitor:remove',       '#', 'admin', now(), null, null, '');



