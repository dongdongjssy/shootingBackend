--INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '9', '关联动态', '9', 'shooting_carousel_type', '', '', 'N', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '轮播图类别-关联动态');

INSERT INTO `shooting`.`sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (null, '10', '动态', '10', 'shooting_carousel_on_page', '', '', 'N', '0', 'admin', '2021-01-18 16:14:49', 'admin', '2021-01-18 16:15:26', '所在页面-动态');


insert into sys_dict_type values(null,  '支付类型', 'payment_type',     '0', 'admin', now(), null, null, '支付类型');
insert into sys_dict_data values(null,  1,  '培训',  '1',  'payment_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '支付类型-培训');
insert into sys_dict_data values(null,  2,  '赛事',  '2',  'payment_type',   '',   '',   'N', '0', 'admin', now(), null, null, '支付类型-赛事');
insert into sys_dict_data values(null,  3,  '俱乐部赛事',  '3',  'payment_type',   '',   '',   'N', '0', 'admin', now(), null, null, '支付类型-俱乐部赛事');
insert into sys_dict_data values(null,  4,  '气枪会员续费',  '4',  'payment_type',   '',   '',   'N', '0', 'admin', now(), null, null, '支付类型-气枪会员续费');
insert into sys_dict_data values(null,  5,  '实弹会员续费',  '5',  'payment_type',   '',   '',   'N', '0', 'admin', now(), null, null, '支付类型-实弹会员续费');


ALTER TABLE shooting_training add payment_code_id bigint(16);
ALTER TABLE shooting_contest add payment_code_id bigint(16);
ALTER TABLE shooting_club_activity add payment_code_id bigint(16);
ALTER TABLE client_user add payment_code_id bigint(16);


--alter table shooting_training add draft tinyint(1) ;
--alter table shooting_training add recommend tinyint(1) ;
alter table shooting_contest add draft tinyint(1) ;
alter table shooting_contest add recommend tinyint(1) ;
alter table shooting_club_activity add draft tinyint(1) ;
alter table shooting_club_activity add recommend tinyint(1) ;
alter table shooting_club_post add draft tinyint(1) ;
alter table shooting_carousel add draft tinyint(1) ;
alter table shooting_recommend add draft tinyint(1) ;
alter table shooting_recommend_coach add draft tinyint(1) ;
alter table shooting_recommend_judge add draft tinyint(1) ;





insert into sys_dict_type values(null,  '草稿箱', 'draft_type',     '0', 'admin', now(), null, null, '草稿箱');
insert into sys_dict_data values(null,  1,  '否',  '0',  'draft_type',   '',   '',   'Y', '0', 'admin', now(), null, null, '草稿箱-否');
insert into sys_dict_data values(null,  2,  '是',  '1',  'draft_type',   '',   '',   'N', '0', 'admin', now(), null, null, '草稿箱-是');




