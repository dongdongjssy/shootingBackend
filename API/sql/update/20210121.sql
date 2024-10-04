insert into sys_menu (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('付款码管理', '7', '20', '/shooting/paymentCode', 'C', '0', 'shooting:paymentCode:view', '#', 'admin', now(), null, null, '付款码管理');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('付款码管理查询', @parentId, '1',  '#',  'F', '0', 'shooting:paymentCode:list',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('付款码管理新增', @parentId, '2',  '#',  'F', '0', 'shooting:paymentCode:add',          '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url, menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('付款码管理修改', @parentId, '3',  '#',  'F', '0', 'shooting:paymentCode:edit',         '#', 'admin', now(), null, null, '');

insert into sys_menu  (menu_name, parent_id, order_num, url,menu_type, visible, perms, icon, create_by, create_time, update_by, update_time, remark)
values('付款码管理删除', @parentId, '4',  '#',  'F', '0', 'shooting:paymentCode:remove',       '#', 'admin', now(), null, null, '');



/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : shooting

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2021-01-21 17:00:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for shooting_club_judge
-- ----------------------------
DROP TABLE IF EXISTS `shooting_club_judge`;
CREATE TABLE `shooting_club_judge` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `club_id` bigint(16) NOT NULL COMMENT '{"title": "俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `judge_id` bigint(16) NOT NULL COMMENT '{"title": "裁判", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_judge", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='俱乐部裁判';

-- ----------------------------
-- Table structure for shooting_rank_instructions
-- ----------------------------
DROP TABLE IF EXISTS `shooting_rank_instructions`;
CREATE TABLE `shooting_rank_instructions` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `my_rank` longtext COMMENT '{"title": "我的成绩","richEditor":"1"}',
  `match_rank` longtext COMMENT '{"title": "赛事成绩","richEditor":"1"}',
  `annual_integral` longtext COMMENT '{"title": "年度积分","richEditor":"1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='成绩说明';

-- ----------------------------
-- Table structure for shooting_start_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `shooting_start_advertisement`;
CREATE TABLE `shooting_start_advertisement` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `describes` longtext COMMENT '{"title": "描述", "searchable": "1", "textSearchOpt":"1", "richEditor":"1"}',
  `advertisement_type` tinyint(1) DEFAULT '1' COMMENT '{"title": "广告类型", "type": "dict", "value": "shooting_advertisement_type", "data":{"1":"图片", "2":"动态图片"}, "searchable": "1"}',
  `upload_time` datetime DEFAULT NULL COMMENT '{"title": "上传时间", "searchable": "1", "type": "date"}',
  `effect_time` datetime DEFAULT NULL COMMENT '{"title": "生效时间", "searchable": "1", "type": "date"}',
  `failure_time` datetime DEFAULT NULL COMMENT '{"title": "失效时间", "searchable": "1", "type": "date"}',
  `advertisement_status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "start_advertisement_status", "data":{"0":"待生效", "1":"生效中","2":"已结束","4":"停用"},"searchable": "1",}',
  `jump` tinyint(1) DEFAULT '2' COMMENT '{"title": "是否跳转", "type": "dict", "value": "start_advertisement_jump", "data":{"1":"是", "2":"否"}}',
  `jump_type` tinyint(1) DEFAULT '1' COMMENT '{"title": "跳转类型", "type": "dict", "value": "advertisement_jump_type", "data":{"1":"内部关联", "2":"外部链接"}}',
  `connect_type` tinyint(1) DEFAULT '1' COMMENT '{"title": "内部关联", "type": "dict", "value": "shooting_carousel_type", "data":{"1":"自定义", "2":"关联赛事", "3":"关联培训", "4":"关联教官", "5": "关联裁判", "6": "关联总会推荐", "7": "关联俱乐部动态", "8": "关联俱乐部赛事"}}',
  `contest_id` bigint(16) NOT NULL COMMENT '{"title": "关联的赛事","fkCreateAssoc": "1", "fkTable":"shooting_contest", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `training_id` bigint(16) NOT NULL COMMENT '{"title": "关联的培训","fkCreateAssoc": "1", "fkTable":"shooting_training", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `coach_id` bigint(16) NOT NULL COMMENT '{"title": "关联的教官","fkCreateAssoc": "1", "fkTable":"shooting_recommend_coach", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `judge_id` bigint(16) NOT NULL COMMENT '{"title": "关联的裁判","fkCreateAssoc": "1", "fkTable":"shooting_recommend_judge", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `recommend_id` bigint(16) NOT NULL COMMENT '{"title": "关联的总会推荐","fkCreateAssoc": "1", "fkTable":"shooting_recommend", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `club_post_id` bigint(16) NOT NULL COMMENT '{"title": "关联的俱乐部动态","fkCreateAssoc": "1", "fkTable":"shooting_club_post", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `club_activity_id` bigint(16) NOT NULL COMMENT '{"title": "关联的俱乐部赛事","fkCreateAssoc": "1", "fkTable":"shooting_club_activity", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
  `media_url` varchar(250) DEFAULT '' COMMENT '{"title": "外部链接"}',
  `detail` longtext COMMENT '{"title": "广告详情","textSearchOpt":"1", "richEditor":"1"}',
  `picture_url` varchar(250) NOT NULL COMMENT '{"title": "图片网址", "uploadPic":"1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='启动页广告';


-- ----------------------------
-- Table structure for shooting_payment_code
-- ----------------------------
DROP TABLE IF EXISTS `shooting_payment_code`;
CREATE TABLE `shooting_payment_code` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "标题", "textSearchOpt":"1"}',
  `picture_url` varchar(250) NOT NULL COMMENT '{"title": "二维码图片", "uploadPic":"1"}',
  `detail` longtext COMMENT '{"title": "描述","textSearchOpt":"1", "richEditor":"1"}',
  `type` tinyint(1) DEFAULT '0' COMMENT '{"title": "类型", "type": "dict", "value": "payment_type", "data":{"1":"培训", "2":"赛事", "3":"俱乐部赛事", "4":"气枪会员续费","5":"实弹会员续费"}, "searchable": "1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='付款码管理';








