ALTER TABLE shooting_goods_attribute ADD   `create_by` varchar(64) DEFAULT '' ;
ALTER TABLE shooting_gt ADD   `create_by` varchar(64) DEFAULT '' ;
ALTER TABLE shooting_goods_order ADD attributes VARCHAR(255) ;
ALTER TABLE shooting_goods_order ADD remark VARCHAR(255) ;


-- 按钮父菜单ID
SELECT @parentId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论管理',  @parentId, '6', '/shooting/goodsEvaluation', 'C', '0', 'shooting:goodsEvaluation:view', '#', 'admin', now(), null, null, '评论管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:goodsEvaluation:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:goodsEvaluation:remove',       '#', 'admin', now(), null, null, '');


