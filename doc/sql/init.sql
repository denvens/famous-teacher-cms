-- CREATE DATABASES -------------------------------

CREATE DATABASE `msyb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE DATABASE `msyb_resource` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE DATABASE `msyb_quartz` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

CREATE DATABASE `msyb_statistic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

-- CREATE TABLES -------------------------------
-- 1.msyb-----------------
use `msyb`; 

CREATE TABLE `ent_pay_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `invitation_open_id` varchar(64) NOT NULL COMMENT '邀请人',
  `purchase_open_id` varchar(64) NOT NULL COMMENT '被邀请人',
  `level_id` int(11) NOT NULL COMMENT 'levelId',
  `order_id` varchar(36) NOT NULL COMMENT 'orderId',
  `amount` decimal(6,2) NOT NULL COMMENT '金额',
  `open_id` varchar(30) DEFAULT NULL COMMENT 'openId',
  `partner_trade_no` varchar(32) NOT NULL COMMENT '商户订单号',
  `payment_no` varchar(64) DEFAULT NULL COMMENT '微信付款单号',
  `payment_time` datetime DEFAULT NULL COMMENT '付款成功时间',
  `spbill_create_ip` varchar(32) NOT NULL COMMENT 'Ip地址',
  `status` varchar(16) NOT NULL COMMENT '交易是否成功：SUCCESS/FAIL',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

CREATE TABLE `ent_pay_order_scholarship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scholarship_open_id` varchar(64) NOT NULL COMMENT '申请人openId',
  `begin_at` date NOT NULL COMMENT '开课批次',
  `level_id` int(11) NOT NULL COMMENT 'levelId',
  `amount` decimal(6,2) NOT NULL COMMENT '金额',
  `order_id` varchar(36) DEFAULT NULL COMMENT 'orderId',
  `open_id` varchar(30) DEFAULT NULL COMMENT 'openId',
  `partner_trade_no` varchar(32) DEFAULT NULL COMMENT '商户订单号',
  `payment_no` varchar(64) DEFAULT NULL COMMENT '微信付款单号',
  `payment_time` datetime DEFAULT NULL COMMENT '付款成功时间',
  `spbill_create_ip` varchar(32) DEFAULT NULL COMMENT 'Ip地址',
  `status` varchar(16) DEFAULT NULL COMMENT '交易是否成功：SUCCESS/FAIL',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间,奖学金申请时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间,奖学金发放时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

CREATE TABLE `invitation_purchase_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invitation_type` int(2) NOT NULL DEFAULT '0' COMMENT '邀请类型，0:邀请送券,1:邀请送现金',
  `invitationUserId` int(11) NOT NULL COMMENT '邀请人userId',
  `purchaseUserId` int(11) NOT NULL COMMENT '购买人userId',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `levelId` int(11) NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '状态[0:邀请成功，已支付状态,会员, 1:已退款]',
  `is_distributor` int(2) NOT NULL DEFAULT '0' COMMENT '邀请人身份，0：普通会员，1：班长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000580 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payment_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refundFee` varchar(11) DEFAULT NULL COMMENT '退款金额',
  `totalFee` varchar(11) DEFAULT NULL COMMENT '订单金额',
  `refundReason` varchar(256) DEFAULT NULL COMMENT '退款金额',
  `outTradeNo` varchar(128) DEFAULT NULL COMMENT '商户单号',
  `refundMode` varchar(11) DEFAULT NULL COMMENT '退款形式',
  `wechatTransactionId` varchar(64) DEFAULT NULL COMMENT '微信交易单号',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000360 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `payment_transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wechatTransactionId` varchar(128) NOT NULL COMMENT '微信交易单号',
  `bigbayTranctionId` varchar(128) NOT NULL COMMENT '商户单号，海湾的单号',
  `merchantionId` varchar(128) NOT NULL COMMENT '商户ID',
  `appId` varchar(128) NOT NULL COMMENT '微信绑定的公众号Appid',
  `totalFee` varchar(128) NOT NULL COMMENT '支付金额',
  `tradeType` varchar(128) NOT NULL COMMENT '交易类型',
  `bankType` varchar(128) NOT NULL COMMENT '银行类型',
  `openId` varchar(128) NOT NULL COMMENT '微信openId',
  `feeType` varchar(128) NOT NULL COMMENT '费用类型，货币类型',
  `clientIp` varchar(64) NOT NULL COMMENT '付款机ip',
  `sellPageItemConfig` varchar(128) NOT NULL COMMENT '购买信息{"beginAt":"2019-05-12","levelId":1000006,"vipBeginTime":"2019-05-12","vipEndTime":"2019-07-30"}',
  `timeEnd` varchar(64) NOT NULL COMMENT '支付订单关闭的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001795 DEFAULT CHARSET=utf8;

CREATE TABLE `prohibit_temp_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `squirrelUserId` int(11) DEFAULT NULL,
  `levelId` int(11) DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `scholarship_apply_for` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ent_pay_order_scholarship_id` bigint(20) DEFAULT '0',
  `scholarship_open_id` varchar(64) NOT NULL COMMENT '申请人openId',
  `begin_at` date NOT NULL COMMENT '开课批次',
  `level_id` int(11) NOT NULL COMMENT 'levelId',
  `status` int(2) NOT NULL COMMENT '奖学金申请状态: 0,已申请; 1,发放成功; 2,发放失败;7,预置特殊奖学金',
  `operation_status` int(2) DEFAULT '0' COMMENT '运营手动发放状态: 0,未发放; 1,已手动发放; ',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `description` varchar(60) DEFAULT NULL COMMENT 'description,特殊奖学金情况说明',
  `scholarship_type` int(2) NOT NULL DEFAULT '0' COMMENT '奖学金类型: 0,普通奖学金; 1,特殊奖学金; ',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间,奖学金申请时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间,奖学金发放时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_patch_vouchers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `squirrelUserId` int(11) NOT NULL,
  `levelId` int(11) NOT NULL,
  `createdAt` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL COMMENT '1为使用，0为未使用',
  `useTime` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `isOpen` int(2) NOT NULL DEFAULT '1' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000157 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_users` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) NOT NULL,
  `unionId` varchar(64) NOT NULL COMMENT '微信开放平台id',
  `nickName` varchar(64) DEFAULT '',
  `sex` int(1) DEFAULT '0',
  `headImgUrl` varchar(1024) DEFAULT '',
  `createdAt` datetime DEFAULT NULL,
  `isVip` int(2) DEFAULT NULL COMMENT '会员身份 0非 1会员',
  `subscribe` int(2) DEFAULT '0' COMMENT '是否订阅 0未订阅 1已订阅',
  `bgmStatus` int(2) NOT NULL DEFAULT '1' COMMENT 'bgm是否开启，1为开启，0为关闭',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openId` (`openId`),
  UNIQUE KEY `unionId` (`unionId`)
) ENGINE=InnoDB AUTO_INCREMENT=100660 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_behavior` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) NOT NULL,
  `channelCode` varchar(32) DEFAULT NULL COMMENT 'channel唯一标识',
  `subscribe` int(2) DEFAULT '0' COMMENT '订阅标识,1:订阅；0：未订阅',
  `createdAt` datetime NOT NULL COMMENT '创建时间',
  `updatedAt` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '行为类型:subscribe:关注',
  `note` varchar(128) DEFAULT NULL COMMENT '埋点信息',
  `levelId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000083 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_conversion_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) NOT NULL,
  `flag` int(2) NOT NULL COMMENT '1:关注未进购买页2:进入购买页未点击购买3:点击购买未完成支付',
  `levelId` int(11) DEFAULT NULL,
  `isSend` int(2) DEFAULT NULL COMMENT '0:未发送 1:已发送',
  `isPurchase` int(2) DEFAULT '0' COMMENT '0:未购买 1:已购买',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000212 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `squirrelUserId` int(11) NOT NULL,
  `levelId` int(11) NOT NULL,
  `transactionId` int(11) NOT NULL,
  `delKey` varchar(32) DEFAULT '0',
  `createdAt` datetime(6) DEFAULT NULL,
  `beginAt` date NOT NULL,
  `openId` varchar(64) DEFAULT NULL,
  `vipBeginTime` date NOT NULL,
  `vipEndTime` date NOT NULL,
  `sendLessonDays` int(11) DEFAULT NULL COMMENT '推课天数',
  `channelCode` varchar(32) DEFAULT NULL COMMENT '渠道二维码',
  PRIMARY KEY (`id`),
  KEY `user_levels_user_id` (`squirrelUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=1808 DEFAULT CHARSET=utf8;

CREATE TABLE `users_lesson_remind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `openId` varchar(64) NOT NULL,
  `levelId` int(11) NOT NULL,
  `remindTime` varchar(32) DEFAULT NULL COMMENT '推送时间',
  `remindRate` varchar(32) DEFAULT NULL COMMENT '推送频率',
  `isOpen` int(2) DEFAULT '1' COMMENT '是否开启 0 关闭 1 开启',
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1003068 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `voucher_oper_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `voucherId` int(11) NOT NULL,
  `type` int(2) NOT NULL COMMENT '操作类型',
  `adminId` int(11) NOT NULL COMMENT '操作人id',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000248 DEFAULT CHARSET=utf8mb4;

-- 2.msyb_quartz
use `msyb_quartz`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(120) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(120) NOT NULL,
  `JOB_GROUP` varchar(120) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(120) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2.msyb_quartz -------------------
use `msyb_quartz`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(120) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(120) NOT NULL,
  `JOB_GROUP` varchar(120) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(120) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3.msyb_resource ----------------------------
use `msyb_resource`;

CREATE TABLE `lessons_mid_picturebooks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `picId` int(11) DEFAULT NULL,
  `lessonId` int(11) DEFAULT NULL,
  `part` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000336 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `lessons_mid_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lessonId` int(11) DEFAULT NULL,
  `wordId` int(11) DEFAULT NULL,
  `isKey` int(2) DEFAULT '0' COMMENT '0为普通单词，1为关键单词',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001133 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_bigbay` (
  `bigbayAppId` varchar(64) NOT NULL,
  `bigbaySignKey` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`bigbayAppId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `validity` varchar(128) DEFAULT NULL COMMENT '有效期',
  `createTime` varchar(64) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(64) DEFAULT NULL COMMENT '修改时间',
  `createBy` varchar(128) DEFAULT NULL COMMENT '添加人',
  `updateBy` varchar(128) DEFAULT NULL COMMENT '修改人',
  `type` varchar(128) DEFAULT NULL COMMENT '推送类型',
  `messages` longtext CHARACTER SET utf8mb4 COMMENT '消息，多消息用&&&&分割',
  `sendTime` varchar(512) DEFAULT NULL COMMENT '延时推送时间，多时间用&&&&分割',
  `ticket` varchar(256) DEFAULT NULL COMMENT '微信换取二维码票据',
  `expireSeconds` varchar(64) DEFAULT NULL COMMENT '二维码实际有效期',
  `url` varchar(512) DEFAULT NULL,
  `delKey` varchar(32) DEFAULT NULL COMMENT '逻辑删除标记',
  `code` varchar(32) DEFAULT NULL COMMENT '识别码',
  `site` varchar(256) DEFAULT NULL COMMENT 'oss地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000130 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_cms_admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `loginName` varchar(128) DEFAULT NULL COMMENT '登陆账号',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `userName` varchar(64) DEFAULT NULL COMMENT '用户名',
  `plain` varchar(32) DEFAULT NULL COMMENT '明文',
  `roleName` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000066 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_conversion_push` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) DEFAULT NULL,
  `pushTime` varchar(32) NOT NULL COMMENT '延时推送时间',
  `customId` int(11) DEFAULT NULL,
  `isOpen` int(2) DEFAULT NULL COMMENT '开关',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `scope` int(2) DEFAULT NULL COMMENT '1：关注未进入购买页2：进入购买页未点击购买3:点击购买未付款',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000025 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_invitation_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(256) DEFAULT NULL COMMENT '补卡图片',
  `rule` text COMMENT '活动规则',
  `validDays` int(4) NOT NULL DEFAULT '10' COMMENT '可补天数',
  `invitation_type` int(2) NOT NULL DEFAULT '0' COMMENT '邀请类型，0:邀请送券,1:邀请送现金',
  `bonus_amount` decimal(6,2) DEFAULT NULL COMMENT '奖金金额',
  `bonus_img` varchar(256) DEFAULT NULL COMMENT '奖金图片',
  `offer_amount` decimal(6,2) DEFAULT NULL COMMENT '优惠金额',
  `offer_img` varchar(256) DEFAULT NULL COMMENT '奖金图片',
  `shareId` int(11) DEFAULT NULL COMMENT '关联的分享页id',
  `templateId` int(11) DEFAULT NULL COMMENT '绑定模版消息',
  `customId` int(11) DEFAULT NULL COMMENT '绑定客服消息',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `levelId` int(11) DEFAULT NULL COMMENT '绑定levelId',
  `isOpen` int(2) NOT NULL DEFAULT '0' COMMENT '1为生效 0位失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000074 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_lesson_remind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level_id` int(11) DEFAULT '0' COMMENT 'level ID',
  `remindRate` varchar(64) NOT NULL COMMENT '提醒频率',
  `firstRemind` varchar(64) NOT NULL COMMENT '第一次提醒时间',
  `secondRemind` varchar(64) NOT NULL COMMENT '第二次提醒时间',
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000068 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_lessons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `levelId` int(11) DEFAULT NULL COMMENT '父级id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `order` int(11) DEFAULT NULL COMMENT '排序列',
  `lessonKey` varchar(256) DEFAULT NULL COMMENT '课程标识',
  `star` int(2) DEFAULT '0' COMMENT '子级unit数量',
  `audition` int(2) DEFAULT '0' COMMENT '试听：1为试听课',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  `isOpen` int(2) DEFAULT '0' COMMENT '0为关闭，1为打开',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `updateDate` date DEFAULT NULL COMMENT '最后更新时间',
  `image` varchar(128) DEFAULT NULL,
  `relation` int(2) NOT NULL DEFAULT '0' COMMENT '是否关联绘本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000534 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_levels` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `subjectId` int(11) DEFAULT NULL COMMENT '父级id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `order` int(11) DEFAULT NULL COMMENT '排序列',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  `minWord` int(11) DEFAULT NULL COMMENT '最少词汇量',
  `maxWord` int(11) DEFAULT NULL COMMENT '最大词汇量',
  `isOpen` int(2) DEFAULT '0' COMMENT '1为打开，0为关闭',
  `updateDate` date DEFAULT NULL,
  `image` varchar(128) DEFAULT NULL,
  `buySite` varchar(256) DEFAULT NULL COMMENT '购买页地址',
  `shareId` int(11) DEFAULT NULL COMMENT '分享页关联',
  `skin` varchar(32) DEFAULT NULL COMMENT '皮肤样式',
  `lessonDay` int(3) DEFAULT '120' COMMENT '课程天数-用于k值统计',
  `channelId` int(11) DEFAULT NULL COMMENT '关联的渠道qr-id',
  `isShow` int(2) DEFAULT NULL COMMENT '是否在前台展示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000042 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_picturebook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `part` int(11) DEFAULT NULL,
  `image` varchar(256) DEFAULT NULL,
  `delKey` varchar(64) DEFAULT '0',
  `updateTime` varchar(64) DEFAULT '0',
  `levelId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000319 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `unitId` int(11) DEFAULT 0 COMMENT '父级id',
  `questionType` varchar(128) DEFAULT NULL COMMENT '题型类型',
  `questionData` longtext COMMENT '题型数据：json串',
  `order` int(11) DEFAULT NULL COMMENT '排序列',
  `questionKey` varchar(256) DEFAULT NULL COMMENT '题型标识',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001792 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_scholarship_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '奖学金规则id',
  `name` varchar(25) DEFAULT NULL COMMENT '奖学金名称',
  `level_id` int(11) DEFAULT NULL COMMENT '频道id',
  `buy_start_time` datetime NOT NULL COMMENT '购买开始日期',
  `buy_end_time` datetime NOT NULL COMMENT '购买结束日期',
  `cash_back_type` int(2) NOT NULL DEFAULT '0' COMMENT '返现类型：0,按固定金额; 1,按比例',
  `amount` decimal(6,2) DEFAULT NULL COMMENT '奖学金金额',
  `ratio` decimal(6,2) DEFAULT NULL COMMENT '奖学金比例',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_subjects` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '顶级名称',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_units` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `lessonId` int(11) DEFAULT NULL COMMENT '父级id',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `order` int(11) DEFAULT NULL COMMENT '排序列',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000891 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `word` varchar(128) DEFAULT NULL COMMENT '单词',
  `translation` varchar(256) DEFAULT NULL COMMENT '释义',
  `voice` varchar(512) DEFAULT NULL COMMENT '音频路径',
  `keyImage` varchar(512) DEFAULT NULL COMMENT '唯一图片',
  `baseImage` text COMMENT '基础图片：路径不唯一，用英文逗号分隔',
  `confusionImage` varchar(512) DEFAULT NULL COMMENT '混淆图片',
  `delKey` varchar(64) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000028 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `squirrel_wx_custom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) DEFAULT NULL COMMENT '客服类型',
  `level_id` int(11) DEFAULT '0' COMMENT 'level ID',
  `content` varchar(1024) DEFAULT NULL COMMENT '消息内容',
  `isOpen` int(2) DEFAULT NULL COMMENT '是否启用 0否 1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000058 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_wx_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT '自定义url',
  `spaceTitle` varchar(1024) DEFAULT NULL COMMENT '微信分享朋友圈标题',
  `freTitle` varchar(1024) DEFAULT NULL COMMENT '微信分享好友标题',
  `content` varchar(1024) DEFAULT NULL COMMENT '微信分享好友描述',
  `img` varchar(256) DEFAULT NULL COMMENT '自定义图标',
  `delKey` varchar(32) DEFAULT NULL COMMENT '逻辑删除标记',
  `channelQr` varchar(128) DEFAULT NULL COMMENT 'qr地址',
  `channelId` int(11) DEFAULT NULL COMMENT 'qr-id',
  `type` varchar(64) DEFAULT NULL COMMENT '分享打卡页类型',
  `shareContent` longtext COMMENT '个别分享样式的文本内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000161 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_wx_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) DEFAULT NULL COMMENT '模版类型',
  `level_id` int(11) DEFAULT '0' COMMENT 'level ID',
  `content` varchar(1024) DEFAULT NULL COMMENT '模板内容，json格式',
  `isOpen` int(2) DEFAULT NULL COMMENT '是否启用 0否 1是',
  `url` varchar(512) DEFAULT NULL COMMENT '跳转链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000040 DEFAULT CHARSET=utf8;

CREATE TABLE `test_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(32) DEFAULT NULL,
  `trans` varchar(32) DEFAULT NULL,
  `level` int(2) DEFAULT NULL,
  `ex1` varchar(32) DEFAULT NULL,
  `ex2` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000034 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `wx_share_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `shareContent` text,
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000014 DEFAULT CHARSET=utf8mb4;

-- 4.msyb_statistic --------------------
use `msyb_statistic`;

CREATE TABLE `batches_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beginDate` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '开课日期',
  `levelId` int(11) NOT NULL,
  `currentDate` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '当前日期',
  `beginCount` int(11) NOT NULL DEFAULT '0' COMMENT '开课人数',
  `studyCount` int(11) NOT NULL DEFAULT '0' COMMENT '学习人数',
  `finishCount` int(11) NOT NULL DEFAULT '0' COMMENT '学完人数',
  `shareCount` int(11) NOT NULL DEFAULT '0' COMMENT '分享人数',
  `beginDay` int(5) NOT NULL DEFAULT '0' COMMENT '开课天数',
  `createdAt` datetime DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1050287 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `batches_temp_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `beginDate` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `levelId` int(11) DEFAULT NULL,
  `openId` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  `currentDate` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106827 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `bz_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000011 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `follow_up_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) NOT NULL,
  `openId` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `clickWordUp` int(2) DEFAULT '0' COMMENT '点击跟读按钮',
  `clickPicbookUp` int(2) DEFAULT '0',
  `finishWordUp` int(2) DEFAULT '0' COMMENT '完成单词跟读',
  `finishPicbookUp` int(2) DEFAULT '0',
  `shareWordUp` int(2) DEFAULT '0' COMMENT '分享单词跟读',
  `sharePicbookUp` int(2) DEFAULT '0',
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000210 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `follow_up_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `levelId` int(11) NOT NULL,
  `onRead` int(11) DEFAULT '0' COMMENT '在读人数',
  `clickWordUp` int(11) DEFAULT '0',
  `clickPicbookUp` int(11) DEFAULT '0',
  `finishWordUp` int(11) DEFAULT '0',
  `finishPicbookUp` int(11) DEFAULT '0',
  `shareWordUp` int(11) DEFAULT '0',
  `sharePicbookUp` int(11) DEFAULT '0',
  `entryShare` int(11) DEFAULT '0' COMMENT '进入分享页人数',
  `createdAt` datetime DEFAULT NULL,
  `finishAllUp` int(11) DEFAULT '0',
  `purchaseCount` int(11) DEFAULT '0' COMMENT '购买人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `invitation_send_cash_buried_point` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_class_date` date NOT NULL,
  `level_Id` int(11) NOT NULL COMMENT 'levelId',
  `invitation_user_id` varchar(64) NOT NULL COMMENT '邀请人userId',
  `purchase_open_id` varchar(64) DEFAULT '0' COMMENT '被邀请人openId',
  `into_send_cash_page_count` int(11) DEFAULT '0' COMMENT '进入邀请送现金人数',
  `send_invitation_count` int(11) DEFAULT '0' COMMENT '发送邀请人数',
  `goto_buy_page_count` int(11) DEFAULT '0' COMMENT '进入购买页人数',
  `click_buy_count` int(11) DEFAULT '0' COMMENT '点击"立即购买"人数',
  `purchase_count` int(11) DEFAULT '0' COMMENT '完成购买人数',
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=517 DEFAULT CHARSET=utf8;

CREATE TABLE `invitation_send_cash_statistic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `current_statistic_date` varchar(15) NOT NULL DEFAULT '0',
  `start_class_date` varchar(15) NOT NULL DEFAULT '0',
  `level_id` int(11) NOT NULL DEFAULT '0' COMMENT 'levelId',
  `begin_days` int(11) NOT NULL DEFAULT '0' COMMENT 'levelId',
  `begin_peoples` int(11) NOT NULL DEFAULT '0' COMMENT 'levelId',
  `into_send_cash_page_count` int(11) DEFAULT '0' COMMENT '进入邀请送现金人数',
  `send_invitation_count` int(11) DEFAULT '0' COMMENT '发送邀请人数',
  `goto_buy_page_count` int(11) DEFAULT '0' COMMENT '进入购买页人数',
  `click_buy_count` int(11) DEFAULT '0' COMMENT '点击"立即购买"人数',
  `purchase_count` int(11) DEFAULT '0' COMMENT '完成购买人数',
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=454 DEFAULT CHARSET=utf8;

CREATE TABLE `squirrel_kvalue_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `onRead` int(10) DEFAULT '0',
  `share` int(10) DEFAULT '0',
  `goShare` int(10) DEFAULT '0',
  `care` int(10) DEFAULT '0',
  `init` int(10) DEFAULT '0',
  `buy` int(10) DEFAULT '0',
  `levelId` int(11) DEFAULT NULL,
  `buySuccess` int(10) DEFAULT '0',
  `createAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `clickAudition` int(8) DEFAULT '0',
  `learnAudition` int(8) DEFAULT '0',
  `clickBuyAtAudition` int(8) DEFAULT '0',
  `learnFinish` int(8) DEFAULT '0',
  `learnAndBuy` int(8) DEFAULT '0',
  `auditionInit` int(8) DEFAULT NULL,
  `auditionClick` int(8) DEFAULT NULL,
  `auditionBuySuccess` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1319 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_action_del_backups` (
  `id` int(11) NOT NULL,
  `openId` varchar(64) NOT NULL,
  `createAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(2) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL COMMENT '埋点信息',
  `levelId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `user_actions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(64) NOT NULL,
  `createAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(2) DEFAULT NULL,
  `note` varchar(128) DEFAULT NULL COMMENT '埋点信息',
  `levelId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2183 DEFAULT CHARSET=utf8mb4;
-- --------------------------
