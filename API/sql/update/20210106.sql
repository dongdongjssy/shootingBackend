-- 广告表 - shooting_advertisement  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('启动广告', '7', '18', '/shooting/startAdvertisement', 'C', '0', 'shooting:startAdvertisement:view', '#', 'admin', now(), null, null, '启动广告菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('启动广告查询', @parentId, '1',  '#',  'F', '0', 'shooting:startAdvertisement:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('启动广告新增', @parentId, '2',  '#',  'F', '0', 'shooting:startAdvertisement:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('启动广告修改', @parentId, '3',  '#',  'F', '0', 'shooting:startAdvertisement:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('启动广告删除', @parentId, '4',  '#',  'F', '0', 'shooting:startAdvertisement:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '广告状态', 'start_advertisement_status',     '0', 'admin', now(), null, null, '广告状态');
insert into sys_dict_data values(null,  1,  '待生效',  '0',  'start_advertisement_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '广告状态-待生效');
insert into sys_dict_data values(null,  2,  '生效中',  '1',  'start_advertisement_status',   '',   '',   'N', '0', 'admin', now(), null, null, '广告状态-生效中');
insert into sys_dict_data values(null,  3,  '已结束',  '2',  'start_advertisement_status',   '',   '',   'N', '0', 'admin', now(), null, null, '广告状态-已结束');
insert into sys_dict_data values(null,  4,  '停用',  '3',  'start_advertisement_status',   '',   '',   'N', '0', 'admin', now(), null, null, '广告状态-停用');

insert into sys_dict_type values(null,  '广告类型', 'shooting_advertisement_type',     '0', 'admin', now(), null, null, '广告类型');
insert into sys_dict_data values(null,  1,  '俱乐部广告',  '1',  'shooting_advertisement_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '广告类型-俱乐部广告');

insert into sys_dict_type values(null,  '是否跳转', 'start_advertisement_jump',     '0', 'admin', now(), null, null, '是否跳转');
insert into sys_dict_data values(null,  1,  '是',  '1',  'start_advertisement_jump',   '',   '',   'Y', '0', 'admin', now(), null, null, '是否跳转-是');
insert into sys_dict_data values(null,  2,  '否',  '2',  'start_advertisement_jump',   '',   '',   'N', '0', 'admin', now(), null, null, '是否跳转-否');




insert into sys_dict_type values(null,  '跳转类型', 'advertisement_jump_type',     '0', 'admin', now(), null, null, '跳转类型');
insert into sys_dict_data values(null,  1,  '内部关联',  '1',  'advertisement_jump_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '跳转类型-内部关联');
insert into sys_dict_data values(null,  2,  '外部链接',  '2',  'advertisement_jump_type',   '',   '',   'N', '0', 'admin', now(), null, null, '跳转类型-外部链接');

insert into sys_dict_data values(null,  6,  '关联总会推荐',  '6',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联总会推荐');
--insert into sys_dict_data values(null,  7,  '关联俱乐部动态',  '7',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联俱乐部动态');
insert into sys_dict_data values(null,  8,  '关联俱乐部赛事',  '8',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联俱乐部赛事');

insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩说明', '7', '19', '/shooting/rankInstructions', 'C', '0', 'shooting:rankInstructions:view', '#', 'admin', now(), null, null, '成绩说明菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩说明查询', @parentId, '1',  '#',  'F', '0', 'shooting:rankInstructions:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩说明新增', @parentId, '2',  '#',  'F', '0', 'shooting:rankInstructions:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩说明修改', @parentId, '3',  '#',  'F', '0', 'shooting:rankInstructions:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩说明删除', @parentId, '4',  '#',  'F', '0', 'shooting:rankInstructions:remove',       '#', 'admin', now(), null, null, '');