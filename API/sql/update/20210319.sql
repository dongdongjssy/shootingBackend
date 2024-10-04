ALTER TABLE shooting_message add is_important TINYINT(1) NULL DEFAULT 2 COMMENT '{"title": "是否重要通知"}';

-- 按钮父菜单ID
SELECT @parentId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品订单管理',  @parentId, '5', '/shooting/goodsOrder', 'C', '0', 'shooting:goodsOrder:view', '#', 'admin', now(), null, null, '商品订单管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品订单查询', @parentId, '1',  '#',  'F', '0', 'shooting:goodsOrder:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品订单新增', @parentId, '2',  '#',  'F', '0', 'shooting:goodsOrder:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品订单修改', @parentId, '3',  '#',  'F', '0', 'shooting:goodsOrder:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品订单删除', @parentId, '4',  '#',  'F', '0', 'shooting:goodsOrder:remove',       '#', 'admin', now(), null, null, '');


insert into sys_dict_type values(null,  '订单状态', 'goods_order_status',     '0', 'admin', now(), null, null, '订单状态');
insert into sys_dict_data values(null,  1,  '待支付',  '1',  'goods_order_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '订单状态-待支付');
insert into sys_dict_data values(null,  2,  '支付待确认',  '2',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-支付待确认');
insert into sys_dict_data values(null,  3,  '待发货',  '3',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-待发货');
insert into sys_dict_data values(null,  4,  '已发货',  '4',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-已发货');
insert into sys_dict_data values(null,  5,  '已收货',  '5',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-已收货');
insert into sys_dict_data values(null,  6,  '已评价',  '6',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-已评价');
insert into sys_dict_data values(null,  7,  '订单完成',  '7',  'goods_order_status',   '',   '',   'N', '0', 'admin', now(), null, null, '订单状态-订单完成');



DROP TABLE IF EXISTS `shooting_goods_shops`;
CREATE TABLE `shooting_goods_shops` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(255) NULL DEFAULT '' COMMENT '{"title": "商铺名称"}',
	`star` TINYINT(2) NULL DEFAULT 1 COMMENT '{"title": "商铺等级"}',
  `comment` longtext COMMENT '{"title": "详情","textSearchOpt":"1", "richEditor":"1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商铺表';

