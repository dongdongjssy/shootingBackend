alter table shooting_honor add member_id varchar (255) ;

insert into sys_dict_data values(null,  3,  '驳回',  '2',  'shooting_club_join_application_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-驳回');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商城管理', '0', '3', '#', 'M', '0', '', 'fa fa-bar-chart', 'admin', now(), null, null, '商城管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品类型管理',  @parentId, '1', '/shooting/goodsType', 'C', '0', 'shooting:goodsType:view', '#', 'admin', now(), null, null, '商品类型管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品类型管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:goodsType:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品类型管理新增', @parentId, '2',  '#',  'F', '0', 'shooting:goodsType:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品类型管理修改', @parentId, '3',  '#',  'F', '0', 'shooting:goodsType:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品类型管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:goodsType:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '邮寄方式', 'goods_mail_method',     '0', 'admin', now(), null, null, '邮寄方式');
insert into sys_dict_data values(null,  1,  '包邮',  '1',  'goods_mail_method',   '',   '',   'Y', '0', 'admin', now(), null, null, '邮寄方式-包邮');
insert into sys_dict_data values(null,  2,  '不包邮',  '2',  'goods_mail_method',   '',   '',   'N', '0', 'admin', now(), null, null, '邮寄方式-不包邮');


insert into sys_dict_type values(null,  '是否提供发票', 'goods_invoice_status',     '0', 'admin', now(), null, null, '是否提供发票');
insert into sys_dict_data values(null,  1,  '否',  '1',  'goods_invoice_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '是否提供发票-否');
insert into sys_dict_data values(null,  2,  '是',  '2',  'goods_invoice_status',   '',   '',   'N', '0', 'admin', now(), null, null, '是否提供发票-是');


insert into sys_dict_type values(null,  '是否默认地址', 'default_address',     '0', 'admin', now(), null, null, '是否默认地址');
insert into sys_dict_data values(null,  1,  '否',  '0',  'default_address',   '',   '',   'Y', '0', 'admin', now(), null, null, '是否默认地址-是');
insert into sys_dict_data values(null,  2,  '是',  '1',  'default_address',   '',   '',   'N', '0', 'admin', now(), null, null, '是否默认地址-否');


alter table shooting_goods_address add default_address tinyint (1) ;
alter table shooting_goods_address add province varchar (255)  DEFAULT NULL COMMENT '{"title": "省份"}';
alter table shooting_goods_address add city varchar (255)  DEFAULT NULL COMMENT '{"title": "市"}';
alter table shooting_goods_address add area varchar (255) DEFAULT NULL COMMENT '{"title": "区"}';
alter table shooting_goods_address add area_number varchar (255)  DEFAULT NULL COMMENT '{"title": "区号"}';

alter table shooting_goods_evaluation add goods_id bigint (16) DEFAULT NULL COMMENT '{"title": "商品"}';

-- 按钮父菜单ID
SELECT @parentId := menu_id from sys_menu where menu_name = '商城管理';

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理',  @parentId, '3', '/shooting/goodsAttribute', 'C', '0', 'shooting:goodsAttribute:view', '#', 'admin', now(), null, null, '商品属性管理');


-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();


-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:goodsType:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理新增', @parentId, '2',  '#',  'F', '0', 'shooting:goodsType:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理修改', @parentId, '3',  '#',  'F', '0', 'shooting:goodsType:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:goodsType:remove',       '#', 'admin', now(), null, null, '');


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('商品属性管理',  @parentId, '3', '/shooting/goodsAttribute', 'C', '0', 'shooting:goodsAttribute:view', '#', 'admin', now(), null, null, '商品属性管理');

