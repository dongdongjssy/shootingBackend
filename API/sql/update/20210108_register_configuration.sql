# Added this table to allow manually config the training register form
create table `shooting_register_config`
(
    `id`                bigint(16)   not null auto_increment comment '主键',
    `fk_id`             bigint(16)   not null comment '{"title": "关联项目主键", "searchable": "0"}',
    `register_type`     varchar(50)  not null default '' comment '{"title": "报名类型", "searchable": "0"}',
    `is_unauth_allowed` boolean      not null default false comment '{"title": "是否允许未认证用户报名", "searchable": "0"}',
    `title`             varchar(100) not null default '' comment '{"title": "配置的名称", "searchable": "0"}',
    primary key (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    comment '报名表配置';

create table `shooting_register_config_personal_info`
(
    `id`                 bigint(16)  not null auto_increment comment '主键',
    `register_config_id` bigint(16)  not null comment '{"title": "关联配置的主键", "searchable": "0"}',
    `info_name`          varchar(50) not null comment '{"title": "项目名称", "searchable":"0"}',
    `info_text`          varchar(50) not null comment '{"title": "项目页面显示名称", "searchable":"0"}',
    `is_required`        boolean     not null default false comment '{"title": "是否必须", "searchable":"0"}',
    primary key (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    comment '报名表配置-个人信息配置项';

create table `shooting_register_config_role_info`
(
    `id`                 bigint(16)  not null auto_increment comment '主键',
    `register_config_id` bigint(16)  not null comment '{"title": "关联配置的主键", "searchable": "0"}',
    `role_name`          varchar(50) not null comment '{"title": "角色名称", "searchable":"0"}',
    `role_id`            bigint(16) comment '{"title": "角色主键", "searchable":"0"}',
    primary key (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    comment '报名表配置-角色配置项';

create table `shooting_register_config_customized_info`
(
    `id`                 bigint(16)  not null auto_increment comment '主键',
    `register_config_id` bigint(16)  not null comment '{"title": "关联配置的主键", "searchable": "0"}',
    `item_name`          varchar(50) not null comment '{"title": "项目名称", "searchable":"0"}',
    `item_type`          varchar(50) not null comment '{"title": "项目类型", "searchable":"0"}',
    `is_required`        boolean     not null default false comment '{"title": "是否必须", "searchable":"0"}',
    `is_remark_display`  boolean     not null default false comment '{"title": "是否显示备注", "searchable":"0"}',
    `item_remark`        varchar(500) comment '{"title": "项目备注", "searchable":"0"}',
    primary key (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    comment '报名表配置-自定义项目';

create table `shooting_register_form_item`
(
    `id`          bigint(16)   not null auto_increment comment '主键',
    `register_id` bigint(16)   not null comment '{"title": "关联shooting_register表的主键", "searchable": "0"}',
    `name`        varchar(50)  not null default '' comment '{"title": "报名表项目", "searchable": "0"}',
    `category`    varchar(50)  not null default '' comment '{"title": "报名表项目类别(个人信息或自定义信息)", "searchable": "0"}',
    `type`        varchar(50)  not null default '' comment '{"title": "项目类型", "searchable":"0"}',
    `value`       varchar(500) not null default '' comment '{"title": "报名表项目值", "searchable": "0"}',
    `remark`      varchar(500) not null default '' comment '{"title": "备注", "searchable": "0"}',
    primary key (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8
    comment '报名表项目';

alter table shooting.shooting_register
    add column `source`    varchar(100) default '' comment '{"title": "获得报名渠道", "searchable": "0"}',
    add column `schedules` varchar(500) default '' comment '{"title": "报名参赛的日期", "searchable": "0"}';