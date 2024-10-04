ALTER TABLE shooting_goods_shopping_cart ADD attributes VARCHAR(255) NOT NULL DEFAULT '' COMMENT '{"title": "选择的商品属性"}';


DROP TABLE IF EXISTS `shooting_goods_shopping_cart`;
CREATE TABLE `shooting_goods_shopping_cart` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `goods_id` bigint(16) NOT NULL COMMENT '{"title": "商品id"}',
  `nums` int NULL DEFAULT 1 COMMENT '{"title": "数量"}',
  `inventory` int NULL DEFAULT 1 COMMENT '{"title": "库存"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='购物车表';


insert into sys_dict_type values(null,  '商品父属性', 'goods_parent_attributes',     '0', 'admin', now(), null, null, '商品父属性');
insert into sys_dict_data values(null,  1,  '颜色',  '1',  'goods_parent_attributes',   '',   '',   'Y', '0', 'admin', now(), null, null, '颜色');
insert into sys_dict_data values(null,  2,  '形状',  '2',  'goods_parent_attributes',   '',   '',   'N', '0', 'admin', now(), null, null, '形状');

-- 按钮父菜单ID
SELECT @parentId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品库存管理',  @parentId, '4', '/shooting/gt', 'C', '0', 'shooting:goodsType:view', '#', 'admin', now(), null, null, '商品库存管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品库存查询', @parentId, '1',  '#',  'F', '0', 'shooting:gt:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品库存新增', @parentId, '2',  '#',  'F', '0', 'shooting:gt:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品库存修改', @parentId, '3',  '#',  'F', '0', 'shooting:gt:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品库存删除', @parentId, '4',  '#',  'F', '0', 'shooting:gt:remove',       '#', 'admin', now(), null, null, '');
