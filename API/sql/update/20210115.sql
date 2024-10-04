-- 俱乐部裁判表 - shooting_club_judge  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部裁判', '8', '10', '/shooting/clubJudge', 'C', '0', 'shooting:clubJudge:view', '#', 'admin', now(), null, null, '俱乐部裁判菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部裁判查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubJudge:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部裁判新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubJudge:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部裁判修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubJudge:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部裁判删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubJudge:remove',       '#', 'admin', now(), null, null, '');


INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '4', '教官', '4', 'shooting_comment_fk_table', '', '', 'Y', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '外键表-教官');
INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '5', '裁判', '5', 'shooting_comment_fk_table', '', '', 'Y', '0', 'admin', '2021-01-18 16:15:17', '', NULL, '外键表-裁判');
