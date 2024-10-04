DROP TABLE IF EXISTS `shooting_goods`;
CREATE TABLE `shooting_goods` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR(255) NOT NULL COMMENT '{"title": "商品名称", "searchable": "1"}',
  `description` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "商品描述", "textSearchOpt":"1"}',
  `picture_url1` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片1", "uploadPic":"1"}',
	`picture_url2` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片2", "uploadPic":"1"}',
	`picture_url3` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片3", "uploadPic":"1"}',
	`picture_url4` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片4", "uploadPic":"1"}',
	`picture_url5` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片5", "uploadPic":"1"}',
	`picture_url6` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片6", "uploadPic":"1"}',
	`picture_url7` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片7", "uploadPic":"1"}',
	`picture_url8` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片8", "uploadPic":"1"}',
	`picture_url9` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "商品图片9", "uploadPic":"1"}',
  `price` varchar(250) COMMENT '{"title": "价格"}',
	`member_price` varchar(250) COMMENT '{"title": "会员价格"}',
	`goods_type_id` bigint(16) COMMENT '{"title": "商品类型" ,"fkCreateAssoc": "1", "fkTable":"shooting_goods_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}}',
	`mail_method` int COMMENT '{"title": "邮寄方式" , "type": "dict", "value": "goods_mail_method", "data":{"1":"包邮", "2":"不包邮"}}',
	`mail_price` int COMMENT '{"title": "邮费"}',
	`invoice` int COMMENT '{"title": "是否提供发票" , "type": "dict", "value": "goods_invoice_status", "data":{"1":"是", "2":"否"}}',
	`comments`   longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "详情描述", "textSearchOpt":"1", "richEditor":"1"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品表';




DROP TABLE IF EXISTS `shooting_goods_attribute`;
CREATE TABLE `shooting_goods_attribute` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL COMMENT '{"title": "属性名称", "searchable": "1"}',
  `parent_id` bigint(16) NULL COMMENT '{"title": "上级属性", "type": "dict", "value": "goods_parent_attributes", "data":{"1":"颜色", "2":"尺寸"}}',
	`goods_id` bigint(16) NULL COMMENT '{"title": "商品","fkCreateAssoc": "1", "fkTable":"shooting_goods", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`goods_type_id` bigint(16) NULL COMMENT '{"title": "商品类型","fkCreateAssoc": "1", "fkTable":"shooting_goods_type", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='属性表';



DROP TABLE IF EXISTS `shooting_gt`;
CREATE TABLE `shooting_gt` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `attribute_id1` bigint(16) NULL COMMENT '{"title": "属性1","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id2` bigint(16) NULL COMMENT '{"title": "属性2","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id3` bigint(16) NULL COMMENT '{"title": "属性3","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id4` bigint(16) NULL COMMENT '{"title": "属性4","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id5` bigint(16) NULL COMMENT '{"title": "属性5","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id6` bigint(16) NULL COMMENT '{"title": "属性6","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id7` bigint(16) NULL COMMENT '{"title": "属性7","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id8` bigint(16) NULL COMMENT '{"title": "属性8","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`attribute_id9` bigint(16) NULL COMMENT '{"title": "属性9","fkCreateAssoc": "1", "fkTable":"shooting_goods_attribute", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
  `goods_id` bigint(16) NOT NULL COMMENT '{"title": "商品id"}',
  `num` int COMMENT '{"title": "商品库存"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品属性关联表';






DROP TABLE IF EXISTS `shooting_goods_evaluation`;
CREATE TABLE `shooting_goods_evaluation` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_order_id` bigint(16) NOT NULL COMMENT '{"title": "订单id"}',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `comments`   longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "评价", "textSearchOpt":"1", "richEditor":"1"}',
  `star`    INT NULL DEFAULT 5 COMMENT '{"title": "星级"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品评价表';



DROP TABLE IF EXISTS `shooting_goods_order`;
CREATE TABLE `shooting_goods_order` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "订单编号", "searchable": "1", "textSearchOpt":"1"}',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户","fkCreateAssoc": "1", "fkTable":"client_user", "fkColumn":"id", "fkDisplayColumn":"real_name", "fkInputMethod":"select"}',
  `invoice_id` bigint(16) NULL COMMENT '{"title": "发票","fkCreateAssoc": "1", "fkTable":"shooting_goods_invoice", "fkColumn":"id", "fkDisplayColumn":"head", "fkInputMethod":"select"}',
  `goods_id` bigint(16) NOT NULL COMMENT '{"title": "商品","fkCreateAssoc": "1", "fkTable":"shooting_goods", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
	`goods_address_id` bigint(16) NOT NULL COMMENT '{"title": "收货地址","fkCreateAssoc": "1", "fkTable":"shooting_goods_address", "fkColumn":"id", "fkDisplayColumn":"name", "fkInputMethod":"select"}',
  `name` bigint(16) NOT NULL COMMENT '{"title": "商品名称"}',
  `num` int COMMENT '{"title": "商品数量"}',
  `price` int COMMENT '{"title": "价格"}',
	`postage` DECIMAL(5,2) COMMENT '{"title": "邮费"}',
  `description` VARCHAR(255)  NULL DEFAULT '' COMMENT '{"title": "备注"}',
	`courier_company` VARCHAR(255) NULL DEFAULT '' COMMENT '{"title": "快递公司"}',
  `courier_number` VARCHAR(255) NULL DEFAULT '' COMMENT '{"title": "快递单号"}',
	`place_time` datetime DEFAULT NULL COMMENT '{"title": "下单时间", "type": "date"}',
	`cancel_time` datetime DEFAULT NULL COMMENT '{"title": "取消时间", "type": "date"}',
	`pay_time` datetime DEFAULT NULL COMMENT '{"title": "付款时间", "type": "date"}',
	`delivery_time` datetime DEFAULT NULL COMMENT '{"title": "发货时间", "type": "date"}',
	`goods_time` datetime DEFAULT NULL COMMENT '{"title": "收货时间", "type": "date"}',
	`ungoods_time` datetime DEFAULT NULL COMMENT '{"title": "退货时间", "type": "date"}',
	`unpay_time` datetime DEFAULT NULL COMMENT '{"title": "退款时间", "type": "date"}',
	`goods_order_status` tinyint(1) DEFAULT '0' COMMENT '{"title": "订单状态", "type": "dict", "value": "goods_order_status", "data":{"0":"待支付", "1":"支付待确认", "2":"待发货", "3":"已发货", "3":"已收货"}, "searchable": "1"}',
	`status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品订单表';


DROP TABLE IF EXISTS `shooting_order`;
CREATE TABLE `shooting_order` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "订单编号", "searchable": "1", "textSearchOpt":"1"}',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `price` int COMMENT '{"title": "总价格"}',
  `picture_url` varchar(250)  NULL  DEFAULT '' COMMENT '{"title": "付款码", "uploadPic":"1"}',
  `goods_order_status` tinyint(1) DEFAULT '0' COMMENT '{"title": "支付状态", "type": "dict", "value": "goods_order_status", "data":{"0":"待支付", "1":"支付待确认", "2":"已支付", "3":"取消订单"}, "searchable": "1"}',
  `pay_time` datetime DEFAULT NULL COMMENT '{"title": "支付时间", "type": "date"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='订单表';


DROP TABLE IF EXISTS `shooting_goods_invoice`;
CREATE TABLE `shooting_goods_invoice` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `invoice_type` tinyint(1) DEFAULT '0' COMMENT '{"title": "发票类型", "type": "dict", "value": "goods_invoice_type", "data":{"0":"个人", "1":"企业"}, "searchable": "1"}',
  `head` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "发票抬头"}',
  `code` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "发票税号"}',
  `bank` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "开户行"}',
  `bank_account` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "银行账号"}',
  `address` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "企业地址"}',
  `phone` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "企业电话"}',
  `default_invoice` tinyint(1) DEFAULT '0' COMMENT '{"title": "是否默认发票", "type": "dict", "value": "goods_invoice_default", "data":{"0":"是", "1":"否"}}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='发票表';


DROP TABLE IF EXISTS `shooting_goods_address`;
CREATE TABLE `shooting_goods_address` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_user_id` bigint(16) NOT NULL COMMENT '{"title": "用户id"}',
  `name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "收货人"}',
  `phone` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "手机号"}',
  `street` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "街道"}',
  `address` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '{"title": "收货地址"}',
  `status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='收货地址表';



DROP TABLE IF EXISTS `shooting_goods_carousel`;
CREATE TABLE `shooting_goods_carousel`
(
    `id`          BIGINT(16)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
		`connect_goods`TINYINT(1)                                              NULL DEFAULT 1 COMMENT '{"title": "是否关联商品", "type": "dict", "value": "shooting_connect_goods", "data":{"1":"自定义", "2":"关联商品"}, "searchable": "1"}',
		`goods_id` BIGINT(16)                                                 NULL COMMENT '{"title": "关联的商品", "searchable": "1", "fkCreateAssoc": "1", "fkTable":"shooting_goods", "fkColumn":"id", "fkDisplayColumn":"title", "fkInputMethod":"select"}',
    `title`       VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT '' COMMENT '{"title": "标题", "searchable": "0", "textSearchOpt":"1", "richEditor":"0"}',
    `sub_title`   longtext CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '{"title": "描述", "searchable": "0", "textSearchOpt":"1", "richEditor":"1"}',
    `media_url`   VARCHAR(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '{"title": "上传图片", "uploadPic":"1"}',
    `status`      TINYINT(1)                                              NULL DEFAULT 0 COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
    `create_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "创建者", "searchable": "0", "systemField":"1"}',
    `create_time` DATETIME(0)                                             NULL COMMENT '{"title": "创建时间", "searchable": "0", "type": "date", "systemField":"1"}',
    `update_by`   VARCHAR(64)                                             NULL DEFAULT '' COMMENT '{"title": "更新者", "searchable": "0", "systemField":"1"}',
    `update_time` DATETIME(0)                                             NULL COMMENT '{"title": "更新时间", "searchable": "0", "type": "date", "systemField":"1"}',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '商城轮播图'
  ROW_FORMAT = DYNAMIC;



  DROP TABLE IF EXISTS `shooting_goods_type`;
CREATE TABLE `shooting_goods_type` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '{"title": "类型名称", "searchable": "1"}',
`status` tinyint(1) DEFAULT '0' COMMENT '{"title": "状态", "type": "dict", "value": "sys_common_status", "data":{"0":"正常", "1":"停用"}, "searchable": "1"}',
  `create_by` varchar(64) DEFAULT '' COMMENT '{"title": "创建者", "systemField":"1"}',
  `create_time` datetime DEFAULT NULL COMMENT '{"title": "创建时间", "type": "date", "systemField":"1"}',
  `update_by` varchar(64) DEFAULT '' COMMENT '{"title": "更新者", "systemField":"1"}',
  `update_time` datetime DEFAULT NULL COMMENT '{"title": "更新时间", "type": "date", "systemField":"1"}',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品类型表';

