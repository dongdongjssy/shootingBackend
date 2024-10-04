SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 事先添加APP管理员作为推荐内容的默认发布者。这个管理员在client_user表里，和系统管理员（在sys_user）对应。
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into client_user (id, nickname, user_name, status) values (1, '国际实用射击中国总会（CPSA）', 'cpsa', 2);



-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 登录页管理主菜单
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6, '登录页管理', '0', '90', '#', 'M', '0', '', 'fa fa-bullseye', 'admin', now(), null, null, '登录页管理目录');



-- 客户端角色表 - client_role  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端角色', '6', '1', '/client/role', 'C', '0', 'client:role:view', '#', 'admin', now(), null, null, '客户端角色菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端角色查询', @parentId, '1',  '#',  'F', '0', 'client:role:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端角色新增', @parentId, '2',  '#',  'F', '0', 'client:role:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端角色修改', @parentId, '3',  '#',  'F', '0', 'client:role:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端角色删除', @parentId, '4',  '#',  'F', '0', 'client:role:remove',       '#', 'admin', now(), null, null, '');



-- 客户端用户表 - client_user  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户', '6', '2', '/client/clientUser', 'C', '0', 'client:clientUser:view', '#', 'admin', now(), null, null, '客户端用户菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户查询', @parentId, '1',  '#',  'F', '0', 'client:clientUser:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户新增', @parentId, '2',  '#',  'F', '0', 'client:clientUser:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户修改', @parentId, '3',  '#',  'F', '0', 'client:clientUser:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户删除', @parentId, '4',  '#',  'F', '0', 'client:clientUser:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '用户类型', 'client_user_user_type',     '0', 'admin', now(), null, null, '用户类型');
insert into sys_dict_data values(null,  1,  '管理员',  '0',  'client_user_user_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '用户类型-管理员');
insert into sys_dict_data values(null,  2,  '普通用户',  '1',  'client_user_user_type',   '',   '',   'N', '0', 'admin', now(), null, null, '用户类型-普通用户');

insert into sys_dict_type values(null,  '认证状态', 'client_user_status',     '0', 'admin', now(), null, null, '认证状态');
insert into sys_dict_data values(null,  1,  '未认证',  '0',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-未认证');
insert into sys_dict_data values(null,  2,  '审核中',  '1',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-审核中');
insert into sys_dict_data values(null,  3,  '已认证',  '2',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-已认证');

insert into sys_dict_type values(null,  '性别', 'client_user_gender',     '0', 'admin', now(), null, null, '性别');
insert into sys_dict_data values(null,  1,  '-',  '0',  'client_user_gender',   '',   '',   'N', '0', 'admin', now(), null, null, '性别-不确定');
insert into sys_dict_data values(null,  2,  '男',      '1',  'client_user_gender',   '',   '',   'N', '0', 'admin', now(), null, null, '性别-男');
insert into sys_dict_data values(null,  3,  '女',      '2',  'client_user_gender',   '',   '',   'N', '0', 'admin', now(), null, null, '性别-女');



-- 客户端用户角色关联表 - client_user_role  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户角色关联', '6', '3', '/client/userRole', 'C', '1', 'client:userRole:view', '#', 'admin', now(), null, null, '客户端用户角色关联菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户角色关联查询', @parentId, '1',  '#',  'F', '0', 'client:userRole:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户角色关联新增', @parentId, '2',  '#',  'F', '0', 'client:userRole:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户角色关联修改', @parentId, '3',  '#',  'F', '0', 'client:userRole:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('客户端用户角色关联删除', @parentId, '4',  '#',  'F', '0', 'client:userRole:remove',       '#', 'admin', now(), null, null, '');



-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 首页管理主菜单
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7, '首页管理', '0', '91', '#', 'M', '0', '', 'fa fa-bullseye', 'admin', now(), null, null, '首页管理目录');



-- 地区表 - shooting_area  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('地区', '7', '1', '/shooting/area', 'C', '0', 'shooting:area:view', '#', 'admin', now(), null, null, '地区菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('地区查询', @parentId, '1',  '#',  'F', '0', 'shooting:area:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('地区新增', @parentId, '2',  '#',  'F', '0', 'shooting:area:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('地区修改', @parentId, '3',  '#',  'F', '0', 'shooting:area:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('地区删除', @parentId, '4',  '#',  'F', '0', 'shooting:area:remove',       '#', 'admin', now(), null, null, '');



-- 科目表 - shooting_course  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('科目', '7', '2', '/shooting/course', 'C', '0', 'shooting:course:view', '#', 'admin', now(), null, null, '科目菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('科目查询', @parentId, '1',  '#',  'F', '0', 'shooting:course:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('科目新增', @parentId, '2',  '#',  'F', '0', 'shooting:course:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('科目修改', @parentId, '3',  '#',  'F', '0', 'shooting:course:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('科目删除', @parentId, '4',  '#',  'F', '0', 'shooting:course:remove',       '#', 'admin', now(), null, null, '');



-- 时间段表 - shooting_time_range  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('时间段', '7', '3', '/shooting/timeRange', 'C', '1', 'shooting:timeRange:view', '#', 'admin', now(), null, null, '时间段菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('时间段查询', @parentId, '1',  '#',  'F', '0', 'shooting:timeRange:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('时间段新增', @parentId, '2',  '#',  'F', '0', 'shooting:timeRange:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('时间段修改', @parentId, '3',  '#',  'F', '0', 'shooting:timeRange:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('时间段删除', @parentId, '4',  '#',  'F', '0', 'shooting:timeRange:remove',       '#', 'admin', now(), null, null, '');



-- 类别表 - shooting_type  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('类别', '7', '4', '/shooting/type', 'C', '0', 'shooting:type:view', '#', 'admin', now(), null, null, '类别菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('类别查询', @parentId, '1',  '#',  'F', '0', 'shooting:type:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('类别新增', @parentId, '2',  '#',  'F', '0', 'shooting:type:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('类别修改', @parentId, '3',  '#',  'F', '0', 'shooting:type:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('类别删除', @parentId, '4',  '#',  'F', '0', 'shooting:type:remove',       '#', 'admin', now(), null, null, '');



-- 轮播图表 - shooting_carousel  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('轮播图', '7', '5', '/shooting/carousel', 'C', '0', 'shooting:carousel:view', '#', 'admin', now(), null, null, '轮播图菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('轮播图查询', @parentId, '1',  '#',  'F', '0', 'shooting:carousel:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('轮播图新增', @parentId, '2',  '#',  'F', '0', 'shooting:carousel:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('轮播图修改', @parentId, '3',  '#',  'F', '0', 'shooting:carousel:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('轮播图删除', @parentId, '4',  '#',  'F', '0', 'shooting:carousel:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '所在页面', 'shooting_carousel_on_page',     '0', 'admin', now(), null, null, '所在页面');
insert into sys_dict_data values(null,  1,  '首页',  '1',  'shooting_carousel_on_page',   '',   '',   'Y', '0', 'admin', now(), null, null, '所在页面-首页');
insert into sys_dict_data values(null,  2,  '培训',  '2',  'shooting_carousel_on_page',   '',   '',   'N', '0', 'admin', now(), null, null, '所在页面-培训');
insert into sys_dict_data values(null,  3,  '赛事',  '3',  'shooting_carousel_on_page',   '',   '',   'N', '0', 'admin', now(), null, null, '所在页面-赛事');
insert into sys_dict_data values(null,  4,  '俱乐部',  '4',  'shooting_carousel_on_page',   '',   '',   'N', '0', 'admin', now(), null, null, '所在页面-俱乐部');
insert into sys_dict_data values(null,  5,  '教官',  '5',  'shooting_carousel_on_page',   '',   '',   'N', '0', 'admin', now(), null, null, '所在页面-教官');
insert into sys_dict_data values(null,  6,  '裁判',  '6',  'shooting_carousel_on_page',   '',   '',   'N', '0', 'admin', now(), null, null, '所在页面-裁判');

insert into sys_dict_type values(null,  '轮播图类别', 'shooting_carousel_type',     '0', 'admin', now(), null, null, '轮播图类别');
insert into sys_dict_data values(null,  1,  '自定义',  '1',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-自定义');
insert into sys_dict_data values(null,  2,  '关联赛事',  '2',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联赛事');
insert into sys_dict_data values(null,  3,  '关联培训',  '3',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联培训');
insert into sys_dict_data values(null,  4,  '关联教官',  '4',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联教官');
insert into sys_dict_data values(null,  5,  '关联裁判',  '5',  'shooting_carousel_type',   '',   '',   'N', '0', 'admin', now(), null, null, '轮播图类别-关联裁判');

-- 推荐表 - shooting_recommend  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('推荐', '7', '6', '/shooting/recommend', 'C', '0', 'shooting:recommend:view', '#', 'admin', now(), null, null, '推荐菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('推荐查询', @parentId, '1',  '#',  'F', '0', 'shooting:recommend:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('推荐新增', @parentId, '2',  '#',  'F', '0', 'shooting:recommend:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('推荐修改', @parentId, '3',  '#',  'F', '0', 'shooting:recommend:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('推荐删除', @parentId, '4',  '#',  'F', '0', 'shooting:recommend:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '推荐类别', 'shooting_recommend_category',     '0', 'admin', now(), null, null, '推荐类别');
insert into sys_dict_data values(null,  0,  '',  '0',  'shooting_recommend_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '推荐类别-');
insert into sys_dict_data values(null,  1,  '公告',  '1',  'shooting_recommend_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '推荐类别-公告');
insert into sys_dict_data values(null,  2,  '资讯',  '2',  'shooting_recommend_category',   '',   '',   'N', '0', 'admin', now(), null, null, '推荐类别-资讯');
insert into sys_dict_data values(null,  3,  '培训',  '3',  'shooting_recommend_category',   '',   '',   'N', '0', 'admin', now(), null, null, '推荐类别-培训');
insert into sys_dict_data values(null,  4,  '比赛',  '4',  'shooting_recommend_category',   '',   '',   'N', '0', 'admin', now(), null, null, '推荐类别-比赛');

insert into sys_dict_type values(null,  '置顶', 'recommend_on_top',     '0', 'admin', now(), null, null, '置顶');
insert into sys_dict_data values(null,  1,  '不',  '0',  'recommend_on_top',   '',   '',   'Y', '0', 'admin', now(), null, null, '置顶-不');
insert into sys_dict_data values(null,  2,  '是',  '1',  'recommend_on_top',   '',   '',   'N', '0', 'admin', now(), null, null, '置顶-是');



-- 广告表 - shooting_advertisement  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('广告', '7', '7', '/shooting/advertisement', 'C', '0', 'shooting:advertisement:view', '#', 'admin', now(), null, null, '广告菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('广告查询', @parentId, '1',  '#',  'F', '0', 'shooting:advertisement:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('广告新增', @parentId, '2',  '#',  'F', '0', 'shooting:advertisement:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('广告修改', @parentId, '3',  '#',  'F', '0', 'shooting:advertisement:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('广告删除', @parentId, '4',  '#',  'F', '0', 'shooting:advertisement:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '媒体类别', 'shooting_carousel_category',     '0', 'admin', now(), null, null, '媒体类别');
insert into sys_dict_data values(null,  1,  '图片',  '1',  'shooting_carousel_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '媒体类别-图片');
insert into sys_dict_data values(null,  2,  '视频',  '2',  'shooting_carousel_category',   '',   '',   'N', '0', 'admin', now(), null, null, '媒体类别-视频');



-- 动态表 - shooting_post  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('动态', '7', '8', '/shooting/post', 'C', '0', 'shooting:post:view', '#', 'admin', now(), null, null, '动态菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('动态查询', @parentId, '1',  '#',  'F', '0', 'shooting:post:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('动态新增', @parentId, '2',  '#',  'F', '0', 'shooting:post:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('动态修改', @parentId, '3',  '#',  'F', '0', 'shooting:post:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('动态删除', @parentId, '4',  '#',  'F', '0', 'shooting:post:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '状态', 'shooting_post_status',     '0', 'admin', now(), null, null, '状态');
insert into sys_dict_data values(null,  1,  '未通过',  '-1',  'shooting_post_status',   '',   'danger',   'N', '0', 'admin', now(), null, null, '状态-未通过');
insert into sys_dict_data values(null,  2,  '审核中',  '0',  'shooting_post_status',   '',   'warning',   'N', '0', 'admin', now(), null, null, '状态-审核中');
insert into sys_dict_data values(null,  3,  '已发布',  '1',  'shooting_post_status',   '',   'primary',   'N', '0', 'admin', now(), null, null, '状态-已发布');



-- 评论表 - shooting_comment  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论', '7', '9', '/shooting/comment', 'C', '0', 'shooting:comment:view', '#', 'admin', now(), null, null, '评论菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论查询', @parentId, '1',  '#',  'F', '0', 'shooting:comment:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论新增', @parentId, '2',  '#',  'F', '0', 'shooting:comment:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论修改', @parentId, '3',  '#',  'F', '0', 'shooting:comment:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('评论删除', @parentId, '4',  '#',  'F', '0', 'shooting:comment:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '外键表', 'shooting_comment_fk_table',     '0', 'admin', now(), null, null, '外键表');
insert into sys_dict_data values(null,  1,  '推荐',  '1',  'shooting_comment_fk_table',   '',   '',   'Y', '0', 'admin', now(), null, null, '外键表-推荐');
insert into sys_dict_data values(null,  2,  '动态',  '2',  'shooting_comment_fk_table',   '',   '',   'N', '0', 'admin', now(), null, null, '外键表-动态');
insert into sys_dict_data values(null,  3,  '俱乐部动态',  '3',  'shooting_comment_fk_table',   '',   '',   'N', '0', 'admin', now(), null, null, '外键表-俱乐部动态');

insert into sys_dict_type values(null,  '状态', 'shooting_comment_status',     '0', 'admin', now(), null, null, '状态');
insert into sys_dict_data values(null,  1,  '原始',  '1',  'shooting_comment_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-原始');
insert into sys_dict_data values(null,  2,  '启用',  '2',  'shooting_comment_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-启用');
insert into sys_dict_data values(null,  3,  '弃用',  '3',  'shooting_comment_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-弃用');
insert into sys_dict_data values(null,  4,  '删除',  '4',  'shooting_comment_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-删除');



-- 回复表 - shooting_comment_feedback  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('回复', '7', '10', '/shooting/commentFeedback', 'C', '0', 'shooting:commentFeedback:view', '#', 'admin', now(), null, null, '回复菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('回复查询', @parentId, '1',  '#',  'F', '0', 'shooting:commentFeedback:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('回复新增', @parentId, '2',  '#',  'F', '0', 'shooting:commentFeedback:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('回复修改', @parentId, '3',  '#',  'F', '0', 'shooting:commentFeedback:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('回复删除', @parentId, '4',  '#',  'F', '0', 'shooting:commentFeedback:remove',       '#', 'admin', now(), null, null, '');



-- 成绩表 - shooting_mark  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩', '7', '11', '/shooting/mark', 'C', '1', 'shooting:mark:view', '#', 'admin', now(), null, null, '成绩菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩查询', @parentId, '1',  '#',  'F', '0', 'shooting:mark:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩新增', @parentId, '2',  '#',  'F', '0', 'shooting:mark:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩修改', @parentId, '3',  '#',  'F', '0', 'shooting:mark:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩删除', @parentId, '4',  '#',  'F', '0', 'shooting:mark:remove',       '#', 'admin', now(), null, null, '');



-- 培训表 - shooting_training  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训', '7', '12', '/shooting/training', 'C', '0', 'shooting:training:view', '#', 'admin', now(), null, null, '培训菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训查询', @parentId, '1',  '#',  'F', '0', 'shooting:training:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训新增', @parentId, '2',  '#',  'F', '0', 'shooting:training:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训修改', @parentId, '3',  '#',  'F', '0', 'shooting:training:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('培训删除', @parentId, '4',  '#',  'F', '0', 'shooting:training:remove',       '#', 'admin', now(), null, null, '');



-- 赛事表 - shooting_contest  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛事', '7', '13', '/shooting/contest', 'C', '0', 'shooting:contest:view', '#', 'admin', now(), null, null, '赛事菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛事查询', @parentId, '1',  '#',  'F', '0', 'shooting:contest:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛事新增', @parentId, '2',  '#',  'F', '0', 'shooting:contest:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛事修改', @parentId, '3',  '#',  'F', '0', 'shooting:contest:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛事删除', @parentId, '4',  '#',  'F', '0', 'shooting:contest:remove',       '#', 'admin', now(), null, null, '');


-- 报名表 - shooting_register  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名', '7', '14', '/shooting/register', 'C', '0', 'shooting:register:view', '#', 'admin', now(), null, null, '报名菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名查询', @parentId, '1',  '#',  'F', '0', 'shooting:register:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名新增', @parentId, '2',  '#',  'F', '0', 'shooting:register:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名修改', @parentId, '3',  '#',  'F', '0', 'shooting:register:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名删除', @parentId, '4',  '#',  'F', '0', 'shooting:register:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '外键表', 'shooting_register_fk_table',     '0', 'admin', now(), null, null, '外键表');
insert into sys_dict_data values(null,  1,  '培训',  '1',  'shooting_register_fk_table',   '',   '',   'Y', '0', 'admin', now(), null, null, '外键表-培训');
insert into sys_dict_data values(null,  2,  '赛事',  '2',  'shooting_register_fk_table',   '',   '',   'N', '0', 'admin', now(), null, null, '外键表-赛事');
insert into sys_dict_data values(null,  3,  '俱乐部活动',  '3',  'shooting_register_fk_table',   '',   '',   'N', '0', 'admin', now(), null, null, '外键表-俱乐部活动');

insert into sys_dict_type values(null,  '状态', 'shooting_register_status',     '0', 'admin', now(), null, null, '状态');
insert into sys_dict_data values(null,  1,  '报名',  '0',  'shooting_register_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-报名');
insert into sys_dict_data values(null,  2,  '通过',  '1',  'shooting_register_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-通过');


-- 招商表 - shooting_business_investment  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('招商', '7', '15', '/shooting/businessInvestment', 'C', '0', 'shooting:businessInvestment:view', '#', 'admin', now(), null, null, '招商菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('招商查询', @parentId, '1',  '#',  'F', '0', 'shooting:businessInvestment:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('招商新增', @parentId, '2',  '#',  'F', '0', 'shooting:businessInvestment:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('招商修改', @parentId, '3',  '#',  'F', '0', 'shooting:businessInvestment:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('招商删除', @parentId, '4',  '#',  'F', '0', 'shooting:businessInvestment:remove',       '#', 'admin', now(), null, null, '');


-- 首页教官表 - shooting_recommend_coach  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官', '7', '16', '/shooting/recommendCoach', 'C', '0', 'shooting:recommendCoach:view', '#', 'admin', now(), null, null, '首页教官菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页教官查询', @parentId, '1',  '#',  'F', '0', 'shooting:recommendCoach:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页教官新增', @parentId, '2',  '#',  'F', '0', 'shooting:recommendCoach:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页教官修改', @parentId, '3',  '#',  'F', '0', 'shooting:recommendCoach:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页教官删除', @parentId, '4',  '#',  'F', '0', 'shooting:recommendCoach:remove',       '#', 'admin', now(), null, null, '');


insert into sys_dict_type values(null,  '类别', 'shooting_recommend_coach_category',     '0', 'admin', now(), null, null, '类别');
insert into sys_dict_data values(null,  1,  '-',  '1',  'shooting_recommend_coach_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '类别--');
insert into sys_dict_data values(null,  2,  '培训',  '2',  'shooting_recommend_coach_category',   '',   '',   'N', '0', 'admin', now(), null, null, '类别-培训');
insert into sys_dict_data values(null,  3,  '置顶',  '3',  'shooting_recommend_coach_category',   '',   '',   'N', '0', 'admin', now(), null, null, '类别-置顶');


-- 首页裁判表 - shooting_recommend_judge  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判', '7', '17', '/shooting/recommendJudge', 'C', '0', 'shooting:recommendJudge:view', '#', 'admin', now(), null, null, '首页裁判菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页裁判查询', @parentId, '1',  '#',  'F', '0', 'shooting:recommendJudge:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页裁判新增', @parentId, '2',  '#',  'F', '0', 'shooting:recommendJudge:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页裁判修改', @parentId, '3',  '#',  'F', '0', 'shooting:recommendJudge:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('首页裁判删除', @parentId, '4',  '#',  'F', '0', 'shooting:recommendJudge:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '类别', 'shooting_recommend_judge_category',     '0', 'admin', now(), null, null, '类别');
insert into sys_dict_data values(null,  1,  '-',  '1',  'shooting_recommend_judge_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '类别--');
insert into sys_dict_data values(null,  2,  '培训',  '2',  'shooting_recommend_judge_category',   '',   '',   'N', '0', 'admin', now(), null, null, '类别-培训');
insert into sys_dict_data values(null,  3,  '置顶',  '3',  'shooting_recommend_judge_category',   '',   '',   'N', '0', 'admin', now(), null, null, '类别-置顶');

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 俱乐部管理主菜单
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(8, '俱乐部管理', '0', '92', '#', 'M', '0', '', 'fa fa-bullseye', 'admin', now(), null, null, '俱乐部管理目录');



-- 俱乐部表 - shooting_club  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部', '8', '1', '/shooting/club', 'C', '0', 'shooting:club:view', '#', 'admin', now(), null, null, '俱乐部菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部查询', @parentId, '1',  '#',  'F', '0', 'shooting:club:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部新增', @parentId, '2',  '#',  'F', '0', 'shooting:club:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部修改', @parentId, '3',  '#',  'F', '0', 'shooting:club:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部删除', @parentId, '4',  '#',  'F', '0', 'shooting:club:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '俱乐部类别', 'shooting_club_category',     '0', 'admin', now(), null, null, '俱乐部类别');
insert into sys_dict_data values(null,  1,  'IPSA',  '1',  'shooting_club_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '俱乐部类别-IPSA');



-- 俱乐部图片表 - shooting_club_image  开始
-- 菜单 SQL
# insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
# values('俱乐部图片', '8', '2', '/shooting/clubImage', 'C', '0', 'shooting:clubImage:view', '#', 'admin', now(), null, null, '俱乐部图片菜单');

-- 按钮父菜单ID
# SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
# insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
# values('俱乐部图片查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubImage:list',         '#', 'admin', now(), null, null, '');
#
# insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
# values('俱乐部图片新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubImage:add',          '#', 'admin', now(), null, null, '');
#
# insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
# values('俱乐部图片修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubImage:edit',         '#', 'admin', now(), null, null, '');
#
# insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
# values('俱乐部图片删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubImage:remove',       '#', 'admin', now(), null, null, '');



-- 教官表 - shooting_coach  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官', '8', '3', '/shooting/coach', 'C', '0', 'shooting:coach:view', '#', 'admin', now(), null, null, '教官菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官查询', @parentId, '1',  '#',  'F', '0', 'shooting:coach:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官新增', @parentId, '2',  '#',  'F', '0', 'shooting:coach:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官修改', @parentId, '3',  '#',  'F', '0', 'shooting:coach:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('教官删除', @parentId, '4',  '#',  'F', '0', 'shooting:coach:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '教官类别', 'shooting_coach_category',     '0', 'admin', now(), null, null, '教官类别');
insert into sys_dict_data values(null,  1,  'IPSC',  '1',  'shooting_coach_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '教官类别-IPSC');
insert into sys_dict_data values(null,  2,  'IDPA',  '2',  'shooting_coach_category',   '',   '',   'N', '0', 'admin', now(), null, null, '教官类别-IDPA');



-- 俱乐部教官表 - shooting_club_coach  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部教官', '8', '4', '/shooting/clubCoach', 'C', '0', 'shooting:clubCoach:view', '#', 'admin', now(), null, null, '俱乐部教官菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部教官查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubCoach:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部教官新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubCoach:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部教官修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubCoach:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部教官删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubCoach:remove',       '#', 'admin', now(), null, null, '');



-- 俱乐部动态表 - shooting_club_post  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部动态', '8', '5', '/shooting/clubPost', 'C', '0', 'shooting:clubPost:view', '#', 'admin', now(), null, null, '俱乐部动态菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部动态查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubPost:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部动态新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubPost:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部动态修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubPost:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部动态删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubPost:remove',       '#', 'admin', now(), null, null, '');



-- 俱乐部活动表 - shooting_club_activity  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部活动', '8', '6', '/shooting/clubActivity', 'C', '0', 'shooting:clubActivity:view', '#', 'admin', now(), null, null, '俱乐部活动菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部活动查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubActivity:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部活动新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubActivity:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部活动修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubActivity:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('俱乐部活动删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubActivity:remove',       '#', 'admin', now(), null, null, '');



-- 裁判表 - shooting_judge  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判', '8', '7', '/shooting/judge', 'C', '0', 'shooting:judge:view', '#', 'admin', now(), null, null, '裁判菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判查询', @parentId, '1',  '#',  'F', '0', 'shooting:judge:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判新增', @parentId, '2',  '#',  'F', '0', 'shooting:judge:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判修改', @parentId, '3',  '#',  'F', '0', 'shooting:judge:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('裁判删除', @parentId, '4',  '#',  'F', '0', 'shooting:judge:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '裁判类别', 'shooting_judge_category',     '0', 'admin', now(), null, null, '裁判类别');
insert into sys_dict_data values(null,  1,  'IPSC',  '1',  'shooting_judge_category',   '',   '',   'Y', '0', 'admin', now(), null, null, '裁判类别-IPSC');
insert into sys_dict_data values(null,  2,  'IDPA',  '2',  'shooting_judge_category',   '',   '',   'N', '0', 'admin', now(), null, null, '裁判类别-IDPA');

insert into sys_dict_type values(null,  '国籍', 'shooting_judge_nationality',     '0', 'admin', now(), null, null, '国籍');
insert into sys_dict_data values(null,  1,  '国内',  '1',  'shooting_judge_nationality',   '',   '',   'N', '0', 'admin', now(), null, null, '国籍-国内');
insert into sys_dict_data values(null,  2,  '国际',  '2',  'shooting_judge_nationality',   '',   '',   'N', '0', 'admin', now(), null, null, '国籍-国际');



-- 我的俱乐部表 - shooting_my_club  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的俱乐部', '8', '8', '/shooting/myClub', 'C', '0', 'shooting:myClub:view', '#', 'admin', now(), null, null, '我的俱乐部菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的俱乐部查询', @parentId, '1',  '#',  'F', '0', 'shooting:myClub:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的俱乐部新增', @parentId, '2',  '#',  'F', '0', 'shooting:myClub:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的俱乐部修改', @parentId, '3',  '#',  'F', '0', 'shooting:myClub:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的俱乐部删除', @parentId, '4',  '#',  'F', '0', 'shooting:myClub:remove',       '#', 'admin', now(), null, null, '');


-- 入群申请表 - shooting_club_join_application  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入群申请', '8', '9', '/shooting/clubJoinApplication', 'C', '0', 'shooting:clubJoinApplication:view', '#', 'admin', now(), null, null, '入群申请菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入群申请查询', @parentId, '1',  '#',  'F', '0', 'shooting:clubJoinApplication:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入群申请新增', @parentId, '2',  '#',  'F', '0', 'shooting:clubJoinApplication:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入群申请修改', @parentId, '3',  '#',  'F', '0', 'shooting:clubJoinApplication:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('入群申请删除', @parentId, '4',  '#',  'F', '0', 'shooting:clubJoinApplication:remove',       '#', 'admin', now(), null, null, '');

insert into sys_dict_type values(null,  '状态', 'shooting_club_join_application_status',     '0', 'admin', now(), null, null, '状态');
insert into sys_dict_data values(null,  1,  '审核',  '0',  'shooting_club_join_application_status',   '',   '',   'Y', '0', 'admin', now(), null, null, '状态-审核');
insert into sys_dict_data values(null,  2,  '通过',  '1',  'shooting_club_join_application_status',   '',   '',   'N', '0', 'admin', now(), null, null, '状态-通过');

-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 发布管理主菜单
-- -----------------------------------------------------------------------------------------------------------------------------------------------------------
insert into sys_menu (menu_id, menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values(9, '发布管理', '0', '93', '#', 'M', '1', '', 'fa fa-bullseye', 'admin', now(), null, null, '发布管理目录');



-- 图片发布表 - shooting_publish_with_image  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片发布', '9', '1', '/shooting/publishWithImage', 'C', '0', 'shooting:publishWithImage:view', '#', 'admin', now(), null, null, '图片发布菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片发布查询', @parentId, '1',  '#',  'F', '0', 'shooting:publishWithImage:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片发布新增', @parentId, '2',  '#',  'F', '0', 'shooting:publishWithImage:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片发布修改', @parentId, '3',  '#',  'F', '0', 'shooting:publishWithImage:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('图片发布删除', @parentId, '4',  '#',  'F', '0', 'shooting:publishWithImage:remove',       '#', 'admin', now(), null, null, '');



-- 视频发布表 - shooting_publish_with_video  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频发布', '9', '2', '/shooting/publishWithVideo', 'C', '0', 'shooting:publishWithVideo:view', '#', 'admin', now(), null, null, '视频发布菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频发布查询', @parentId, '1',  '#',  'F', '0', 'shooting:publishWithVideo:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频发布新增', @parentId, '2',  '#',  'F', '0', 'shooting:publishWithVideo:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频发布修改', @parentId, '3',  '#',  'F', '0', 'shooting:publishWithVideo:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('视频发布删除', @parentId, '4',  '#',  'F', '0', 'shooting:publishWithVideo:remove',       '#', 'admin', now(), null, null, '');






insert into sys_menu values('10', '成绩管理', '0', '103', '#', 'M', '0', '', 'fa fa-bar-chart',         'admin', '2018-03-16 11-33-00', 'eee', '2018-03-16 11-33-00', '成绩管理目录');



-- 成绩表 - shooting_contest_contest_ranking  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩', '10', '1', '/shooting/contestContestRanking', 'C', '0', 'shooting:contestContestRanking:view', '#', 'admin', now(), null, null, '成绩菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩查询', @parentId, '1',  '#',  'F', '0', 'shooting:contestContestRanking:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩新增', @parentId, '2',  '#',  'F', '0', 'shooting:contestContestRanking:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩修改', @parentId, '3',  '#',  'F', '0', 'shooting:contestContestRanking:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('成绩删除', @parentId, '4',  '#',  'F', '0', 'shooting:contestContestRanking:remove',       '#', 'admin', now(), null, null, '');


insert into sys_dict_type values(null,  '年龄组别', 'age_group',     '0', 'admin', now(), null, null, '年龄组别');
insert into sys_dict_data values(null,  1,  '成年组',  '1',  'age_group',   '',   '',   'Y', '0', 'admin', now(), null, null, '年龄组别-成年组');
insert into sys_dict_data values(null,  2,  '老年组',  '2',  'age_group',   '',   '',   'N', '0', 'admin', now(), null, null, '年龄组别-老年组');
insert into sys_dict_data values(null,  3,  '超老年组',  '3',  'age_group',   '',   '',   'N', '0', 'admin', now(), null, null, '年龄组别-超老年组');
insert into sys_dict_data values(null,  4,  '青年组',  '4',  'age_group',   '',   '',   'N', '0', 'admin', now(), null, null, '年龄组别-青年组');
insert into sys_dict_data values(null,  5,  '超青年组',  '5',  'age_group',   '',   '',   'N', '0', 'admin', now(), null, null, '年龄组别-超青年组');
insert into sys_dict_data values(null,  6,  '女子组',  '6',  'age_group',   '',   '',   'N', '0', 'admin', now(), null, null, '年龄组别-女子组');

insert into sys_dict_type values(null,  '是否DQ', 'is_dq',     '0', 'admin', now(), null, null, '是否DQ');
insert into sys_dict_data values(null,  1,  '否',  '0',  'is_dq',   '',   '',   'N', '0', 'admin', now(), null, null, '是否DQ-否');
insert into sys_dict_data values(null,  2,  '是',  '1',  'is_dq',   '',   '',   'N', '0', 'admin', now(), null, null, '是否DQ-是');

-- 级别系数表 - shooting_contest_level_coeff  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('级别系数', '10', '1', '/shooting/contestLevelCoeff', 'C', '0', 'shooting:contestLevelCoeff:view', '#', 'admin', now(), null, null, '级别系数菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('级别系数查询', @parentId, '1',  '#',  'F', '0', 'shooting:contestLevelCoeff:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('级别系数新增', @parentId, '2',  '#',  'F', '0', 'shooting:contestLevelCoeff:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('级别系数修改', @parentId, '3',  '#',  'F', '0', 'shooting:contestLevelCoeff:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('级别系数删除', @parentId, '4',  '#',  'F', '0', 'shooting:contestLevelCoeff:remove',       '#', 'admin', now(), null, null, '');


-- 名次系数表 - shooting_contest_ranking_coeff  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('名次系数', '10', '1', '/shooting/contestRankingCoeff', 'C', '0', 'shooting:contestRankingCoeff:view', '#', 'admin', now(), null, null, '名次系数菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('名次系数查询', @parentId, '1',  '#',  'F', '0', 'shooting:contestRankingCoeff:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('名次系数新增', @parentId, '2',  '#',  'F', '0', 'shooting:contestRankingCoeff:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('名次系数修改', @parentId, '3',  '#',  'F', '0', 'shooting:contestRankingCoeff:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('名次系数删除', @parentId, '4',  '#',  'F', '0', 'shooting:contestRankingCoeff:remove',       '#', 'admin', now(), null, null, '');


-- 选手排名表 - shooting_contest_stats  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('选手排名', '10', '1', '/shooting/contestStats', 'C', '0', 'shooting:contestStats:view', '#', 'admin', now(), null, null, '选手排名菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('选手排名查询', @parentId, '1',  '#',  'F', '0', 'shooting:contestStats:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('选手排名新增', @parentId, '2',  '#',  'F', '0', 'shooting:contestStats:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('选手排名修改', @parentId, '3',  '#',  'F', '0', 'shooting:contestStats:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('选手排名删除', @parentId, '4',  '#',  'F', '0', 'shooting:contestStats:remove',       '#', 'admin', now(), null, null, '');


-- 组别表 - shooting_contest_group  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('组别', '10', '1', '/shooting/contestGroup', 'C', '0', 'shooting:contestGroup:view', '#', 'admin', now(), null, null, '组别菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('组别查询', @parentId, '1',  '#',  'F', '0', 'shooting:contestGroup:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('组别新增', @parentId, '2',  '#',  'F', '0', 'shooting:contestGroup:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('组别修改', @parentId, '3',  '#',  'F', '0', 'shooting:contestGroup:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('组别删除', @parentId, '4',  '#',  'F', '0', 'shooting:contestGroup:remove',       '#', 'admin', now(), null, null, '');





-- 通告表 - shooting_message  开始
-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('通告', '6', '1', '/shooting/message', 'C', '0', 'shooting:message:view', '#', 'admin', now(), null, null, '通告菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('通告查询', @parentId, '1',  '#',  'F', '0', 'shooting:message:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('通告新增', @parentId, '2',  '#',  'F', '0', 'shooting:message:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('通告修改', @parentId, '3',  '#',  'F', '0', 'shooting:message:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('通告删除', @parentId, '4',  '#',  'F', '0', 'shooting:message:remove',       '#', 'admin', now(), null, null, '');

