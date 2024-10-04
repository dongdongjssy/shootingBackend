ALTER TABLE shooting_goods_order add gt_id bigint(16);
ALTER TABLE shooting_goods_order add order_id bigint(16);
ALTER TABLE shooting_order add comment varchar(255);
ALTER TABLE shooting_order add picture varchar(255);

-- 按钮父菜单ID
SELECT @parentId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('购买订单管理',  @parentId, '5', '/shooting/order', 'C', '0', 'shooting:order:view', '#', 'admin', now(), null, null, '购买订单管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('购买订单查询', @parentId, '1',  '#',  'F', '0', 'shooting:order:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('购买订单新增', @parentId, '2',  '#',  'F', '0', 'shooting:order:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('购买订单修改', @parentId, '3',  '#',  'F', '0', 'shooting:order:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('购买订单删除', @parentId, '4',  '#',  'F', '0', 'shooting:order:remove',       '#', 'admin', now(), null, null, '');

