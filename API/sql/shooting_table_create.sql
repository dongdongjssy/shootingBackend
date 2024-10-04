-- Comment JSON definition for code generator
-- {
-- "title": "后台, 列表显示的头部名称",
-- "searchable": "后台, 是否支持搜索, 0或默认:不搜索, 1：搜索",
-- "type": "后台, 字段输入类型, dict:数据字典类型(后台使用下拉框选择), date:时间类型(后台使用日期控件选择), 其他类型不必设置",
-- "value": "后台, type 为dict时, 指定数据字典类型名称",
-- "data": "后台, type 为dict时,指定数据字典数值（用于生成数据字典sql),如不设置data, 需要手动编辑",
-- "checkUnique": "后台及API, 是否生成唯一性检查接口, 0或默认：不生成, 1:生成",
-- "richEditor": "后台, 文本编辑类型, 0或默认：单行普通文本输入, 1：富文本输入, 2：多行普通文本输入",
-- "fkCreateAssoc, fkTable, fkColumn, fkDisplayColumn, fkInputMethod": "domain类外连接对象, 是否通过外连接注入外键domain class",
-- "uploadPic", "后台及API, 是否生成上传图片代码, 0或默认：不上传, 1：上传"
-- "cachable": "service, 是否缓存, 建议设置到主键comment",
-- "packageName" : "domain/api/controller, package名称覆盖",
-- "className" : "domain/api/controller, class名称覆盖 例如client_role 默认可以设置为{"packageName":"com.goose.system", "className":"ClientRole"}",
-- "ellipsisText": "后台, 列表显示缩略文本.",
-- "textSearchOpt": "1, 匹配搜索"
-- }

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
SET SQL_SAFE_UPDATES = 0;

-- ------------------------------------------------------------------------------
-- Table structure for client_role
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `client_role`;
CREATE TABLE `client_role`
(
    `id`          INT(11) UNSIGNED                                        NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50)                                                  DEFAULT NULL COMMENT '{"title": "角色", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}', /*'角色：ROLE_ADMIN;ROLE_USER'*/
    `description` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "描述", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='客户端角色';

-- ------------------------------------------------------------------------------
-- Table structure for client_user
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user`
(
    `id`                  BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    `nickname`            VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "用户昵称", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `user_name`           VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "用户名", "searchable": "1", "textSearchOpt":"1", "richEditor":"0", "checkUnique":"1"}',
    `email`               VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "邮箱", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `phone`               VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "手机号", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `password`            VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "密码", "searchable": "0", "textSearchOpt":"0", "richEditor":"0", "systemField":"1"}',
    `user_type`           VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "用户类型", "type": "dict", "value": "client_user_user_type", "data":{"0":"管理员", "1":"普通用户"}, "searchable": "0"}',
    `open_id`             VARCHAR(50)                                             DEFAULT NULL COMMENT '{"title": "用户微信", "searchable": "1", "textSearchOpt":"0", "richEditor":"0"}',
    `avatar`              VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '{"title": "用户头像", "uploadPic":"1"}',
    `city`                VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "城市", "searchable": "1", "textSearchOpt":"0", "richEditor":"0"}',
    `address`             VARCHAR(255)                                            DEFAULT NULL COMMENT '{"title": "地址", "searchable": "0", "textSearchOpt":"0", "richEditor":"0"}',
    `password_reset_date` DATETIME                                                DEFAULT NULL COMMENT '{"title": "密码重置时间", "searchable": "0", "textSearchOpt":"0", "type": "date", "systemField":"1"}',
    `status`              TINYINT(1) NULL                                         DEFAULT 0 COMMENT '{"title": "认证状态", "type": "dict", "value": "client_user_status", "data":{"0":"未认证", "1":"审核中", "2":"已认证"}, "searchable": "1"}',
    `id_front`            VARCHAR(200)                                            DEFAULT NULL COMMENT '{"title": "身份证正面", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `id_back`             VARCHAR(200)                                            DEFAULT NULL COMMENT '{"title": "身份证背面", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `id_number`           VARCHAR(20)                                             DEFAULT NULL COMMENT '{"title": "身份证号", "searchable": "1", "textSearchOpt": "0", "richEditor": "0"}',
    `real_name`           VARCHAR(20)                                             DEFAULT NULL COMMENT '{"title": "真实姓名", "searchable": "1", "textSearchOpt": "0", "richEditor": "0"}',
    `jg_username`         VARCHAR(50)                                             DEFAULT NULL COMMENT '{"title": "极光用户名", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `member_id`           VARCHAR(50)                                             DEFAULT NULL COMMENT '{"title": "会员号", "searchable": "1", "textSearchOpt": "0", "richEditor": "0"}',
    `english_name`        VARCHAR(50)                                             DEFAULT NULL COMMENT '{"title": "英文名", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `cert_expire_date`    DATETIME                                                DEFAULT NULL COMMENT '{"title": "认证有效期", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `selfie_image`        VARCHAR(200)                                            DEFAULT NULL COMMENT '{"title": "证件照片", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `graduate_date`       DATETIME                                                DEFAULT NULL COMMENT '{"title": "结业日期", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `sex`                 INT(1)                                                  DEFAULT 0 COMMENT '{"title": "性别", "type": "dict", "value": "client_user_gender", "data":{"0":"-", "1":"男", "2":"女"}, "searchable": "0"}',
    `passport_no`         VARCHAR(20)                                             DEFAULT NULL COMMENT '{"title": "护照号码", "searchable": "1", "textSearchOpt": "0", "richEditor": "0"}',
    `role_ids`            VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '{"title": "多角色"}',
    `blood_type` int(3) DEFAULT NULL COMMENT '{"title": "血型", "type": "dict", "value": "blood_type", "data":{"1":"A", "2":"B", "3":"AB","4":"O"}, "searchable": "0"}',
    `introduction` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '{"title": "个人简介"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8 COMMENT ='客户端用户';

-- ------------------------------------------------------------------------------
-- Table structure for client_user_role
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `client_user_role`;
CREATE TABLE `client_user_role`
(
    `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`  BIGINT(16)       NOT NULL COMMENT '{"title": "用户", "searchable": "0", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `role_id`  INT(11)          NOT NULL COMMENT '{"title": "角色", "searchable": "0", "fkCreateAssoc": "1", "fkTable":"client_role", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `role_ids` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '{"title": "多角色"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8 COMMENT ='客户端用户角色关联';

-- ------------------------------------------------------------------------------
-- Table structure for shooting_carousel
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_carousel`;
CREATE TABLE `shooting_carousel`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `club_id`     BIGINT(16)                                              NOT NULL COMMENT '{"title": "所属俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `contest_id`  BIGINT(16)                                              NOT NULL COMMENT '{"title": "关联的赛事", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_contest", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `training_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "关联的培训", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_training", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `coach_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "关联的教官", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_recommend_coach", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `judge_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "关联的裁判", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_recommend_judge", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `on_page`     TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "所在页面", "type": "dict", "value": "shooting_carousel_on_page", "data":{"1":"首页", "2":"培训", "3":"俱乐部", ""}, "searchable": "1"}',
    `title`       VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '{"title": "标题", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `sub_title`   longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "描述", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `media_url`   VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "上传图片", "uploadPic":"1"}',
    `media_type`  TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "轮播图类别", "type": "dict", "value": "shooting_carousel_type", "data":{"1":"自定义", "2":"关联赛事", "3":"关联培训", "4":"关联教官", "5": "关联裁判"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '轮播媒体'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_recommend
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_recommend`;
CREATE TABLE `shooting_recommend`
(
    `id`             BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "发布者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `category`       TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "推荐类别", "type": "dict", "value": "shooting_recommend_category", "data":{"1":"公告", "2":"资讯", "3":"培训", "4":"比赛"}, "searchable": "1"}',
    `title`          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `video`          VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `like_count`     INT                                                     NULL COMMENT '{"title": "点赞数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `comment_count`  INT                                                     NULL COMMENT '{"title": "评论数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `read_count`     INT                                                     NULL COMMENT '{"title": "浏览数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `on_top`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "置顶", "type": "dict", "value": "recommend_on_top", "data":{"0":"不", "1":"是"}, "searchable": "1"}',
    `rankings`       INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `ad_title`       VARCHAR(100)                                                 DEFAULT NULL COMMENT '{"title": "广告标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `ad_content`     longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "广告详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `ad_media_url`   VARCHAR(250)                                                 DEFAULT NULL COMMENT '{"title": "广告媒体网址", "uploadPic":"1"}',
    `content_short`  VARCHAR(200)                                                 DEFAULT '' COMMENT '内容缩略',
    `video_cover`    VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频封面", "uploadPic":"0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '推荐'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_post
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_post`;
CREATE TABLE `shooting_post`
(
    `id`             BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "发布者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `title`          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `video`          VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `like_count`     INT                                                     NULL COMMENT '{"title": "点赞数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `comment_count`  INT                                                     NULL COMMENT '{"title": "评论数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `read_count`     INT                                                     NULL COMMENT '{"title": "浏览数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `on_top`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "置顶", "type": "dict", "value": "recommend_on_top", "data":{"0":"不", "1":"是"}, "searchable": "0"}',
    `rankings`       INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "shooting_post_status", "data":{"-1":"未通过", "0":"审核中", "1":"已发布"}, "searchable": "1"}',
    `create_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `content_short`  VARCHAR(200)                                                 DEFAULT '' COMMENT '内容缩略',
    `video_cover`    VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频封面", "uploadPic":"0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '动态'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_advertisement
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_advertisement`;
CREATE TABLE `shooting_advertisement`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `detail`      longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `media_url`   VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "媒体网址", "uploadPic":"1"}',
    `media_type`  TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "媒体类别", "type": "dict", "value": "shooting_carousel_category", "data":{"1":"图片", "2":"视频"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '广告'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_coach
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_coach`;
CREATE TABLE `shooting_coach`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nickname`    VARCHAR(64)                                             NOT NULL COMMENT '{"title": "昵称", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `name`        VARCHAR(64)                                             NOT NULL COMMENT '{"title": "姓名", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `avatar`      VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci      DEFAULT NULL COMMENT '{"title": "头像", "uploadPic":"1"}',
    `contact`     VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "联系", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `category`    TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "教官类别", "type": "dict", "value": "shooting_coach_category", "data":{"1":"IPSC", "2":"IDPA"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '教官'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_judge
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_judge`;
CREATE TABLE `shooting_judge`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `nickname`    VARCHAR(64)                                             NOT NULL COMMENT '{"title": "昵称", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `name`        VARCHAR(64)                                             NOT NULL COMMENT '{"title": "姓名", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `avatar`      VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci      DEFAULT NULL COMMENT '{"title": "头像", "uploadPic":"1"}',
    `contact`     VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "联系", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `category`    TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "裁判类别", "type": "dict", "value": "shooting_judge_category", "data":{"1":"IPSC", "2":"IDPA"}, "searchable": "1"}',
    `nationality` TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "国籍", "type": "dict", "value": "shooting_judge_nationality", "data":{"1":"国内", "2":"国际"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '裁判'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club`;
CREATE TABLE `shooting_club`
(
    `id`            BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `area_id`       BIGINT(16)                                              NOT NULL COMMENT '{"title": "地区", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_area", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `title`         VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "名称", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `profile`       longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "简介", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `avatar`        VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci      DEFAULT NULL COMMENT '{"title": "俱乐部LOGO", "uploadPic":"1"}',
    `image`         VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "图片网址", "uploadPic":"1"}',
    `coach_count`   INT                                                     NULL COMMENT '{"title": "教官数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `student_count` INT                                                     NULL COMMENT '{"title": "学员数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `fans_count`    INT                                                     NULL COMMENT '{"title": "粉丝数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `category`      TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "俱乐部类别", "type": "dict", "value": "shooting_club_category", "data":{"1":"IPSA"}, "searchable": "1"}',
    `rankings`      INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`        TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`     VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time`   DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`     VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time`   DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `jg_username`   VARCHAR(50)                                                  DEFAULT NULL COMMENT '{"title": "极光管理员和群组用户名", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    `jg_groupId`    VARCHAR(50)                                                  DEFAULT NULL COMMENT '{"title": "极光群组ID", "searchable": "0", "textSearchOpt": "0", "richEditor": "0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '俱乐部'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club_coach
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club_coach`;
CREATE TABLE `shooting_club_coach`
(
    `id`       BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `club_id`  BIGINT(16) NOT NULL COMMENT '{"title": "俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `coach_id` BIGINT(16) NOT NULL COMMENT '{"title": "教官", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_coach", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '俱乐部教官'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_comment
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_comment`;
CREATE TABLE `shooting_comment`
(
    `id`             BIGINT(16)  NOT NULL AUTO_INCREMENT COMMENT '{"title": "主键"}',
    `fk_id`          BIGINT(16)  NOT NULL COMMENT '{"title": "外键"}',
    `fk_table`       TINYINT(1)  NOT NULL DEFAULT 1 COMMENT '{"title": "外键表", "type": "dict", "value": "shooting_comment_fk_table", "data":{"1":"推荐", "2":"动态", "3":"俱乐部动态"}, "searchable": "1"}',
    `club_id`        BIGINT(16)  NOT NULL COMMENT '{"title": "所属俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `client_user_id` BIGINT(16)  NOT NULL COMMENT '{"title": "评论者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `content`        VARCHAR(1000)        DEFAULT '' COMMENT '{"title": "评论内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `rankings`       INT         NULL     DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)  NULL     DEFAULT 1 COMMENT '{"title": "状态", "type": "dict", "value": "shooting_comment_status", "data":{"1":"原始", "2":"启用", "3":"弃用", "4":"删除"}, "searchable": "1"}',
    `create_by`      VARCHAR(64) NULL     DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0) NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64) NULL     DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0) NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论';

-- ------------------------------------------------------------------------------
-- Table structure for shooting_comment_feedback
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_comment_feedback`;
CREATE TABLE `shooting_comment_feedback`
(
    `id`             BIGINT(16)  NOT NULL AUTO_INCREMENT COMMENT '{"title": "主键"}',
    `comment_id`     BIGINT(16)  NOT NULL COMMENT '{"title": "所属评论主键", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_comment", "fkColumn":"id", "fkInputMethod":"select"}',
    `club_id`        BIGINT(16)  NOT NULL COMMENT '{"title": "所属俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `client_user_id` BIGINT(16)  NOT NULL COMMENT '{"title": "回复者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `content`        VARCHAR(1000)    DEFAULT '' COMMENT '{"title": "回复内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `rankings`       INT         NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)  NULL DEFAULT 1 COMMENT '{"title": "状态", "type": "dict", "value": "shooting_comment_status", "data":{"1":"原始", "2":"启用", "3":"弃用", "4":"删除"}, "searchable": "1"}',
    `create_by`      VARCHAR(64) NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0) NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64) NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0) NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='回复';

-- ------------------------------------------------------------------------------
-- Table structure for shooting_area
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_area`;
CREATE TABLE `shooting_area`
(
    `id`   BIGINT(16)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(128) NOT NULL COMMENT '{"title": "名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}', /* e.g. 天津， 上海 */
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '地区'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_course
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_course`;
CREATE TABLE `shooting_course`
(
    `id`   BIGINT(16)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(64) NOT NULL COMMENT '{"title": "名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}', /* e.g. IPSC， IDPA */
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '科目'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_type
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_type`;
CREATE TABLE `shooting_type`
(
    `id`   BIGINT(16)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` VARCHAR(64) NOT NULL COMMENT '{"title": "类别", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}', /* e.g. 实弹， 气枪 */
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '类别'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_time_range
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_time_range`;
CREATE TABLE `shooting_time_range`
(
    `id`          BIGINT(16)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        VARCHAR(64) NOT NULL COMMENT '{"title": "时间段名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}', /* e.g. 一个月内， 三个月内 */
    `month_count` INT         NOT NULL COMMENT '{"title": "多少月以内"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '时间段'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club_post
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club_post`;
CREATE TABLE `shooting_club_post`
(
    `id`             BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `club_id`        BIGINT(16)                                              NULL DEFAULT NULL COMMENT '{"title": "所属俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}', /* NULL=射击主页动态 */
    `client_user_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "发布者", "searchable": "0", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `title`          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `video`          VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `like_count`     INT                                                     NULL COMMENT '{"title": "点赞数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `comment_count`  INT                                                     NULL COMMENT '{"title": "评论数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `read_count`     INT                                                     NULL COMMENT '{"title": "浏览数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `on_top`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "置顶", "type": "dict", "value": "recommend_on_top", "data":{"0":"不", "1":"是"}, "searchable": "0"}',
    `rankings`       INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `content_short`  VARCHAR(200)                                                 DEFAULT '' COMMENT '内容缩略',
    `video_cover`    VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频封面", "uploadPic":"0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '俱乐部动态'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club_activity
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club_activity`;
CREATE TABLE `shooting_club_activity`
(
    `id`              BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `club_id`         BIGINT(16)                                              NULL DEFAULT NULL COMMENT '{"title": "所属俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}', /* NULL=射击主页动态 */
    `area_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "地区", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_area", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `course_id`       BIGINT(16)                                              NOT NULL COMMENT '{"title": "科目", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_course", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `type_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "射击类别", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `title`           VARCHAR(64)                                             NOT NULL COMMENT '{"title": "活动名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}',
    `image_url`       VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "图片网址", "uploadPic":"1"}',
    `start_date`      DATETIME(0)                                             NOT NULL COMMENT '{"title": "开始日期", "searchable":"1", "type": "date"}',
    `end_date`        DATETIME(0)                                             NOT NULL COMMENT '{"title": "结束日期", "searchable":"1", "type": "date"}',
    `price`           VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '{"title": "价格", "searchable": "0", "textSearchOpt":"1"}',
    `address`         VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "地址", "searchable": "0", "textSearchOpt":"1"}',
    `content`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "活动内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `rankings`        INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名", "searchable": "0"}',
    `status`          TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time`     DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time`     DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `enroll_deadline` DATETIME(0)                                             NULL COMMENT '{"title": "报名截止时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '俱乐部活动'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_training
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_training`;
CREATE TABLE `shooting_training`
(
    `id`              BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `area_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "地区", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_area", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `course_id`       BIGINT(16)                                              NOT NULL COMMENT '{"title": "科目", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_course", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `type_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "射击类别", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `title`           VARCHAR(64)                                             NOT NULL COMMENT '{"title": "培训名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}',
    `image_url`       VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "图片网址", "uploadPic":"1"}',
    `start_date`      DATETIME(0)                                             NOT NULL COMMENT '{"title": "开始日期", "searchable":"1", "type": "date"}',
    `end_date`        DATETIME(0)                                             NOT NULL COMMENT '{"title": "结束日期", "searchable":"1", "type": "date"}',
    `price`           VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '{"title": "价格", "searchable": "0", "textSearchOpt":"1"}',
    `address`         VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "地址", "searchable": "0", "textSearchOpt":"1"}',
    `content`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "活动内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `rankings`        INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名", "searchable": "0"}',
    `status`          TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time`     DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time`     DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `enroll_deadline` DATETIME(0)                                             NULL COMMENT '{"title": "报名截止时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '培训'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_contest
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_contest`;
CREATE TABLE `shooting_contest`
(
    `id`              BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `area_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "地区", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_area", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `course_id`       BIGINT(16)                                              NOT NULL COMMENT '{"title": "科目", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_course", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `type_id`         BIGINT(16)                                              NOT NULL COMMENT '{"title": "射击类别", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `level_id`        BIGINT(16)                                              NOT NULL COMMENT '{"title": "赛事级别ID", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_contest_level_coeff", "fkColumn":"id", "fkDisplayColumn":"level_name", "fkInputMethod":"select"}',
    `title`           VARCHAR(64)                                             NOT NULL COMMENT '{"title": "赛事名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}',
    `image_url`       VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "图片网址", "uploadPic":"1"}',
    `start_date`      DATETIME(0)                                             NOT NULL COMMENT '{"title": "开始日期", "searchable":"1", "type": "date"}',
    `end_date`        DATETIME(0)                                             NOT NULL COMMENT '{"title": "结束日期", "searchable":"1", "type": "date"}',
    `price`           VARCHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '{"title": "价格", "searchable": "0", "textSearchOpt":"1"}',
    `address`         VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "地址", "searchable": "0", "textSearchOpt":"1"}',
    `content`         longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "活动内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `rankings`        INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名", "searchable": "0"}',
    `status`          TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time`     DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `enroll_deadline` DATETIME(0)                                             NULL COMMENT '{"title": "报名截止时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`       VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time`     DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '赛事'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_register
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_register`;
CREATE TABLE `shooting_register`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `fk_id`          BIGINT(16) NOT NULL COMMENT '{"title": "外键"}',
    `fk_table`       TINYINT(1) NOT NULL DEFAULT 1 COMMENT '{"title": "外键表", "type": "dict", "value": "shooting_register_fk_table", "data":{"1":"培训", "2":"赛事", "3":"俱乐部活动"}, "searchable": "1"}',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "报名者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `status`         TINYINT(1) NULL     DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "shooting_register_status", "data":{"0":"报名", "1":"通过"}, "searchable": "1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '报名'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_mark
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_mark`;
CREATE TABLE `shooting_mark`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `mark`           INT        NOT NULL COMMENT '{"title": "成绩"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '成绩'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_my_club
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_my_club`;
CREATE TABLE `shooting_my_club`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "关注者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `club_id`        BIGINT(16) NOT NULL COMMENT '{"title": "关注的俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '我关注的俱乐部'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club_image
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club_image`;
CREATE TABLE `shooting_club_image`
(
    `id`      BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `club_id` BIGINT(16) NOT NULL COMMENT '{"title": "俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `image1`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`  VARCHAR(1000) DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '俱乐部图片'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_publish_with_image
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_publish_with_image`;
CREATE TABLE `shooting_publish_with_image`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `rankings`       INT        NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1) NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '图片发布'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_publish_with_video
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_publish_with_video`;
CREATE TABLE `shooting_publish_with_video`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `video`          VARCHAR(1000)   DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `rankings`       INT        NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1) NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '视频发布'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_user_like
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_user_like`;
CREATE TABLE `shooting_user_like`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '点赞者',
    `like_id`        BIGINT(16) NOT NULL COMMENT '点赞的对象主键',
    `like_type`      VARCHAR(32) DEFAULT '' COMMENT '点赞对象类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户点赞表'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_user_collection
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_user_collection`;
CREATE TABLE `shooting_user_collection`
(
    `id`              BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id`  BIGINT(16) NOT NULL COMMENT '收藏者',
    `collection_id`   BIGINT(16) NOT NULL COMMENT '收藏的对象主键',
    `collection_type` VARCHAR(32) DEFAULT '' COMMENT '收藏对象类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户收藏表'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_user_follow
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_user_follow`;
CREATE TABLE `shooting_user_follow`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '关注者',
    `follow_id`      BIGINT(16) NOT NULL COMMENT '关注对象主键',
    `follow_type`    VARCHAR(32) DEFAULT '' COMMENT '关注对象类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户关注表'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_user_read
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_user_read`;
CREATE TABLE `shooting_user_read`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '阅读者',
    `read_id`        BIGINT(16) NOT NULL COMMENT '阅读对象主键',
    `read_type`      VARCHAR(32) DEFAULT '' COMMENT '阅读对象类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户阅读表'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_club_application
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_club_join_application`;
CREATE TABLE `shooting_club_join_application`
(
    `id`             BIGINT(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16) NOT NULL COMMENT '{"title": "申请者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `club_id`        BIGINT(16) NOT NULL COMMENT '{"title": "申请加入的俱乐部", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_club", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `status`         TINYINT(1) NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "shooting_club_join_application_status", "data":{"0":"审核", "1":"通过"}, "searchable": "1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '入群申请'
  ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for shooting_contest_contest_ranking
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_contest_ranking`;
CREATE TABLE `shooting_contest_contest_ranking`
(
    `id`             bigint(16)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` bigint(16)  NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `contest_id`     bigint(16)  NOT NULL COMMENT '{"title": "赛事ID", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_contest", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `group_id`       bigint(16)           DEFAULT NULL COMMENT '{"title": "组别ID", "searchable":"1", "fkCreateAssoc": "1", "fkTable":"shooting_contest_group", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `cpsa_rank`      int(10)              DEFAULT '1' COMMENT '{"title": "CPSA名次", "searchable": "1"}',
    `total_rank`     int(10)              DEFAULT '1' COMMENT '{"title": "总名次", "searchable": "1"}',
    `score`          double(10, 2)        DEFAULT '0.00' COMMENT '{"title": "成绩", "searchable": "1"}',
    `percentage`     int(10)              DEFAULT '100' COMMENT '{"title": "百分比", "searchable": "1"}',
    `avg_coeff`      double(10, 2)        DEFAULT '1.00' COMMENT '{"title": "平均系数", "searchable": "1"}',
    `avg_time`       datetime             DEFAULT NULL COMMENT '{"title": "平均时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `avg_score`      double(10, 2)        DEFAULT '1.00' COMMENT '{"title": "平均得分", "searchable": "1"}',
    `age_group`      tinyint(1)           DEFAULT '1' COMMENT '{"title": "年龄组别", "type": "dict", "value": "age_group", "data":{"1":"成年组", "2":"老年组","3":"超老年组","4":"青年组","5":"超青年组","6":"女子组"}, "searchable": "1"}',
    `is_dq`          tinyint(1)           DEFAULT '0' COMMENT '{"title": "是否DQ", "type": "dict", "value": "is_dq", "data":{"0":"否", "1":"是"}, "searchable": "1"}',
    `note`           varchar(500)         DEFAULT '' COMMENT '{"title": "备注", "searchable": "0", "systemField":"1"}',
    `import_name`    varchar(32)          DEFAULT NULL COMMENT '{"title": "导入姓名", "searchable": "0", "systemField":"1"}',
    `status`         tinyint(1)           DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      varchar(64) NOT NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time`    datetime             DEFAULT NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      varchar(64)          DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time`    datetime             DEFAULT NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='成绩';



-- ----------------------------
-- Table structure for shooting_contest_contest_ranking_coeff
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_contest_ranking_coeff`;
CREATE TABLE `shooting_contest_contest_ranking_coeff`
(
    `id`             bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `mark_id`        bigint(16)    DEFAULT NULL COMMENT '{"title": "成绩ID", "searchable": "1"}',
    `coefficient`    double(10, 2) DEFAULT NULL COMMENT '{"title": "系数", "searchable": "1"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 141
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='赛事具体成绩表';



-- ----------------------------
-- Table structure for shooting_contest_group
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_group`;
CREATE TABLE `shooting_contest_group`
(
    `id`   bigint(16)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(32) NOT NULL COMMENT '{"title": "组别名称", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='组别表';


-- ----------------------------
-- Table structure for shooting_contest_level_coeff
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_level_coeff`;
CREATE TABLE `shooting_contest_level_coeff`
(
    `id`          bigint(16)    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `level_name`  varchar(32)   NOT NULL COMMENT '{"title": "级别名称", "searchable":"1", "textSearchOpt":"1", "richEditor":"0"}',
    `level_coeff` double(10, 0) NOT NULL COMMENT '{"title": "级别系数", "searchable": "0"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='级别系数表';


-- ----------------------------
-- Table structure for shooting_contest_ranking_coeff
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_ranking_coeff`;
CREATE TABLE `shooting_contest_ranking_coeff`
(
    `id`         bigint(16)    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `rank`       int(11)       NOT NULL COMMENT '{"title": "名次", "searchable": "0"}',
    `rank_coeff` double(10, 2) NOT NULL COMMENT '{"title": "名次系数", "searchable": "0"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='名次系数表';



-- ----------------------------
-- Table structure for shooting_contest_stats
-- ----------------------------
DROP TABLE IF EXISTS `shooting_contest_stats`;
CREATE TABLE `shooting_contest_stats`
(
    `id`               bigint(16)                     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id`   bigint(16)                     NOT NULL COMMENT '{"title": "用户", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `type_id`          bigint(16)                     NOT NULL COMMENT '{"title": "射击类别", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `course_id`        bigint(16)                     NOT NULL COMMENT '{"title": "科目", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_course", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `contest_group_id` bigint(16)                     NOT NULL COMMENT '{"title": "组别ID", "searchable":"1", "fkCreateAssoc": "1", "fkTable":"shooting_contest_group", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
    `age_group`        tinyint(1)                     NOT NULL DEFAULT '1' COMMENT '{"title": "年龄组别", "type": "dict", "value": "age_group", "data":{"1":"成年组", "2":"老年组","3":"超老年组","4":"青年组","5":"超青年组","6":"女子组"}, "searchable": "1"}',
    `year`             int(10)                        NOT NULL COMMENT '{"title": "年份", "searchable": "1"}',
    `rank`             int(10)                                 DEFAULT NULL COMMENT '{"title": "排名"}',
    `point`            double(10, 2)                           DEFAULT NULL COMMENT '{"title": "积分 "}',
    `total_score`      double(10, 2)                           DEFAULT '0.00' COMMENT '{"title": "总分"}',
    `total_count`      int(10)                                 DEFAULT '0' COMMENT '{"title": "总数"}',
    `total_avg_score`  double(10, 2)                           DEFAULT NULL COMMENT '{"title": "总平均分"}',
    `best_score`       double(10, 2)                           DEFAULT '0.00' COMMENT '{"title": "最高分数"}',
    `best_count`       int(10)                                 DEFAULT '0' COMMENT '{"title": "最大总数"}',
    `best_avg_score`   double(10, 2)                           DEFAULT '0.00' COMMENT '{"title": "最高平均分"}',
    `note`             varchar(255) CHARACTER SET utf8         DEFAULT NULL COMMENT '{"title": "备注"}',
    `status`           tinyint(1)                              DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`        varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time`      datetime                                DEFAULT NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`        varchar(64) CHARACTER SET utf8          DEFAULT NULL COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time`      datetime                                DEFAULT NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = latin1 COMMENT ='选手排名表';



-- ----------------------------
-- Table structure for shooting_message
-- ----------------------------
DROP TABLE IF EXISTS `shooting_message`;
CREATE TABLE `shooting_message`
(
    `id`          bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '1' COMMENT '标题',
    `content`     longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '消息内容',
    `type`        tinyint(4)                                             DEFAULT '0' COMMENT '接收类型   1.全部用户 2指定用户',
    `status`      tinyint(4)                                             DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time` datetime                                               DEFAULT NULL COMMENT '{"title": "创建时间", "searchable": "1", "type": "date", "systemField":"1"}',
    `update_by`   varchar(64)                                            DEFAULT NULL COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time` datetime                                               DEFAULT NULL COMMENT '{"title": "更新时间", "searchable": "1", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 28
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='消息表';



-- ----------------------------
-- Table structure for shooting_message_user
-- ----------------------------
DROP TABLE IF EXISTS `shooting_message_user`;
CREATE TABLE `shooting_message_user`
(
    `id`             bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `message_id`     bigint(20)                                             DEFAULT NULL COMMENT '消息ID',
    `client_user_id` bigint(20)                                             DEFAULT NULL,
    `status`         tinyint(4)                                             DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `create_time`    datetime                                               DEFAULT NULL COMMENT '{"title": "创建时间", "searchable": "1", "type": "date", "systemField":"1"}',
    `update_by`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    datetime                                               DEFAULT NULL COMMENT '{"title": "更新时间", "searchable": "1", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 25
  DEFAULT CHARSET = utf8 COMMENT ='用户消息关联表';

-- ------------------------------------------------------------------------------
-- Table structure for shooting_advertisement
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_advertisement`;
CREATE TABLE `shooting_advertisement`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `detail`      longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `media_url`   VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "媒体网址", "uploadPic":"1"}',
    `media_type`  TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "媒体类别", "type": "dict", "value": "shooting_carousel_category", "data":{"1":"图片", "2":"视频"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '广告'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_business_investment
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_business_investment`;
CREATE TABLE `shooting_business_investment`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `detail`      longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "招商详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `media_url`   VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "招商图片", "uploadPic":"1"}',
    `media_type`  TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "媒体类别", "type": "dict", "value": "shooting_carousel_category", "data":{"1":"图片", "2":"视频"}, "searchable": "1"}',
    `rankings`    INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "1", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "1", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '招商'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_recommend_coach
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_recommend_coach`;
CREATE TABLE `shooting_recommend_coach`
(
    `id`             BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "发布者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `category`       TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "类别", "type": "dict", "value": "shooting_recommend_coach_category", "data":{"1":"-", "2":"培训", "3":"置顶"}, "searchable": "1"}',
    `title`          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `video`          VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `like_count`     INT                                                     NULL COMMENT '{"title": "点赞数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `comment_count`  INT                                                     NULL COMMENT '{"title": "评论数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `read_count`     INT                                                     NULL COMMENT '{"title": "浏览数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `on_top`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "置顶", "type": "dict", "value": "recommend_on_top", "data":{"0":"不", "1":"是"}, "searchable": "1"}',
    `rankings`       INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `ad_title`       VARCHAR(100)                                                 DEFAULT NULL COMMENT '{"title": "广告标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `ad_content`     longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "广告详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `ad_media_url`   VARCHAR(250)                                                 DEFAULT NULL COMMENT '{"title": "广告媒体网址", "uploadPic":"1"}',
    `content_short`  VARCHAR(200)                                                 DEFAULT '' COMMENT '内容缩略',
    `video_cover`    VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频封面", "uploadPic":"0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '首页教官'
  ROW_FORMAT = DYNAMIC;

-- ------------------------------------------------------------------------------
-- Table structure for shooting_recommend_judge`
-- ------------------------------------------------------------------------------
DROP TABLE IF EXISTS `shooting_recommend_judge`;
CREATE TABLE `shooting_recommend_judge`
(
    `id`             BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_user_id` BIGINT(16)                                              NOT NULL COMMENT '{"title": "发布者", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"nickname", "fkInputMethod":"select"}',
    `category`       TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "类别", "type": "dict", "value": "shooting_recommend_judge_category", "data":{"1":"-", "2":"培训", "3":"置顶"}, "searchable": "1"}',
    `title`          VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `content`        longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "内容", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `image1`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接1", "uploadPic":"1"}',
    `image2`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接2", "uploadPic":"1"}',
    `image3`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接3", "uploadPic":"1"}',
    `image4`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接4", "uploadPic":"1"}',
    `image5`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接5", "uploadPic":"1"}',
    `image6`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接6", "uploadPic":"1"}',
    `image7`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接7", "uploadPic":"1"}',
    `image8`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接8", "uploadPic":"1"}',
    `image9`         VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "图集链接9", "uploadPic":"1"}',
    `video`          VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频", "uploadPic":"1"}',
    `like_count`     INT                                                     NULL COMMENT '{"title": "点赞数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `comment_count`  INT                                                     NULL COMMENT '{"title": "评论数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `read_count`     INT                                                     NULL COMMENT '{"title": "浏览数", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `on_top`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "置顶", "type": "dict", "value": "recommend_on_top", "data":{"0":"不", "1":"是"}, "searchable": "1"}',
    `rankings`       INT                                                     NULL DEFAULT 100000 COMMENT '{"title": "排名"}',
    `status`         TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable":"1", "systemField":"1"}',
    `create_time`    DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`      VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable":"1", "systemField":"1"}',
    `update_time`    DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `ad_title`       VARCHAR(100)                                                 DEFAULT NULL COMMENT '{"title": "广告标题", "searchable": "1", "textSearchOpt":"1", "richEditor":"0"}',
    `ad_content`     longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "广告详情", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `ad_media_url`   VARCHAR(250)                                                 DEFAULT NULL COMMENT '{"title": "广告媒体网址", "uploadPic":"1"}',
    `content_short`  VARCHAR(200)                                                 DEFAULT '' COMMENT '内容缩略',
    `video_cover`    VARCHAR(1000)                                                DEFAULT '' COMMENT '{"title": "视频封面", "uploadPic":"0"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '首页裁判'
  ROW_FORMAT = DYNAMIC;