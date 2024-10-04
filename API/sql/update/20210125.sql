alter table shooting_club_activity add release_status        TINYINT(1) ;
alter table shooting_contest add release_status        TINYINT(1) ;
alter table shooting_training add release_status        TINYINT(1) ;


insert into sys_dict_type values(null,  '俱乐部赛事发布状态', 'club_release_status',     '0', 'admin', now(), null, null, '俱乐部赛事发布状态');
insert into sys_dict_data values(null,  1,  '审核中',  '1',  'club_release_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '俱乐部赛事发布状态-审核中');
insert into sys_dict_data values(null,  2,  '待支付',  '2',  'club_release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '俱乐部赛事发布状态-待支付');
insert into sys_dict_data values(null,  3,  '支付待确认',  '3',  'club_release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '俱乐部赛事发布状态-支付待确认');
insert into sys_dict_data values(null,  4,  '报名通过',  '4',  'club_release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '俱乐部赛事发布状态-报名通过');
insert into sys_dict_data values(null,  5,  '报名失败',  '5',  'club_release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '俱乐部赛事发布状态-报名失败');


insert into sys_dict_type values(null,  '赛事发布状态', 'release_status',     '0', 'admin', now(), null, null, '赛事发布状态');
insert into sys_dict_data values(null,  1,  '审核中',  '1',  'release_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '赛事发布状态-审核中');
insert into sys_dict_data values(null,  2,  '待支付',  '2',  'release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '赛事发布状态-待支付');
insert into sys_dict_data values(null,  3,  '支付待确认',  '3',  'release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '赛事发布状态-支付待确认');
insert into sys_dict_data values(null,  4,  '报名通过',  '4',  'release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '赛事发布状态-报名通过');
insert into sys_dict_data values(null,  5,  '报名失败',  '5',  'release_status',   '',   '',   'N', '0', 'admin', now(), null, null, '赛事发布状态-报名失败');


insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('临期管理', '6', '3', '/client/clientUser/dateRemind', 'C', '0', 'client:clientUserDateRemind:view', '#', 'admin', now(), null, null, '临期管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('临期管理查询', @parentId, '1',  '#',  'F', '0', 'client:clientUserDateRemind:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('临期管理新增', @parentId, '2',  '#',  'F', '0', 'client:clientUserDateRemind:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('临期管理修改', @parentId, '3',  '#',  'F', '0', 'client:clientUserDateRemind:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('临期管理删除', @parentId, '4',  '#',  'F', '0', 'client:clientUserDateRemind:remove',       '#', 'admin', now(), null, null, '');


INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '7', '推荐', '7', 'shooting_carousel_on_page', '', '', 'N', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '所在页面-推荐');
--INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '8', '俱乐部动态', '8', 'shooting_carousel_on_page', '', '', 'N', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '所在页面-俱乐部动态');
--INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '9', '俱乐部赛事', '9', 'shooting_carousel_on_page', '', '', 'N', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '所在页面-俱乐部赛事');


ALTER TABLE shooting_carousel add recommend_id bigint(16) NOT NULL;
ALTER TABLE shooting_carousel add club_post_id bigint(16) NOT NULL;
ALTER TABLE shooting_carousel add club_activity_id bigint(16) NOT NULL;