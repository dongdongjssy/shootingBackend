# Added this table to allow training or contest to have multiple (start date, end date) ranges2020
create table `shooting_schedule`
(
    `id`         bigint(16)  not null auto_increment comment '主键',
    `fk_id`      bigint(16)  not null comment '{"title": "关联项目主键", "searchable": "0"}',
    `type`       varchar(50) not null default '' comment '{"title": "关联类型", "searchable": "0"}',
    `start_date` datetime(0) not null comment '{"title": "开始日期", "searchable":"0", "type": "date"}',
    `end_date`   datetime(0) not null comment '{"title": "结束日期", "searchable":"0", "type": "date"}',
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  comment '培训/赛事开始结束时间';

insert into shooting_schedule (fk_id, start_date, end_date, type)
select id, start_date, end_date, 'training'
from shooting_training;

insert into shooting_schedule (fk_id, start_date, end_date, type)
select id, start_date, end_date, 'contest'
from shooting.shooting_contest;

insert into shooting_schedule (fk_id, start_date, end_date, type)
select id, start_date, end_date, 'clubActivity'
from shooting.shooting_club_activity;
