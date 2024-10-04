-- 用户认证状态 --
-- 1、未认证     有认证按钮
-- 2、审核中     没有认证按钮
-- 3、已认证     没有认证按钮
-- 4、审核驳回     有认证按钮
-- 5、待续费     没有认证按钮  有缴费按钮
-- 6、认证失效   没有认证按钮  这个由他们人工判断谁失效

insert into sys_dict_data values(null,  4,  '审核驳回',  '3',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-审核驳回');
insert into sys_dict_data values(null,  5,  '待续费',  '4',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-待续费');
insert into sys_dict_data values(null,  6,  '认证失效',  '5',  'client_user_status',   '',   '',   'N', '0', 'admin', now(), null, null, '认证状态-认证失效');
