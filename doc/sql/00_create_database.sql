/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : jmetrics

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2015-10-13 17:56:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `execution`
-- ----------------------------
DROP TABLE IF EXISTS `execution`;
CREATE TABLE `execution` (
`execution_id`  bigint(20) NOT NULL COMMENT '执行ID' ,
`job_id`  bigint(20) NOT NULL ,
`task_id`  bigint(20) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行者名称，如“$节点名称+$线程前缀+$线程名称”' ,
`status`  tinyint(4) NULL DEFAULT NULL COMMENT '执行状态，0:执行中，1:执行成功，2:执行失败' ,
`start_time`  datetime NOT NULL ,
`end_time`  datetime NOT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`update_time`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`execution_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='任务执行表'

;

-- ----------------------------
-- Records of execution
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
`job_id`  bigint(20) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  tinyint(4) NULL DEFAULT NULL ,
`system_id`  bigint(20) NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`job_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `metric`
-- ----------------------------
DROP TABLE IF EXISTS `metric`;
CREATE TABLE `metric` (
`metric_id`  bigint(20) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`system_id`  bigint(20) NULL DEFAULT NULL ,
`schema`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`field_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`field_type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`update_time`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`metric_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of metric
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `monitored_system`
-- ----------------------------
DROP TABLE IF EXISTS `monitored_system`;
CREATE TABLE `monitored_system` (
`system_id`  bigint(20) NOT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`db_product`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库产品：mysql、oracle' ,
`db_ip`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`db_port`  smallint(5) UNSIGNED NULL DEFAULT NULL ,
`db_user`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`db_pwd`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`schema`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`table`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`update_time`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`system_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of monitored_system
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
`task_id`  bigint(20) NOT NULL ,
`job_id`  bigint(20) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`description`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`schedule_rate`  int(11) NOT NULL COMMENT '调度周期的时间数值' ,
`schedule_unit`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度周期的时间单位：0:秒, 1:分, 2:时, 3:日, 4:月, 5:周' ,
`scheduled_count`  int(11) NOT NULL DEFAULT 0 COMMENT '已被调度的次数' ,
`status`  tinyint(4) NULL DEFAULT NULL ,
`create_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`task_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of task
-- ----------------------------
BEGIN;
COMMIT;
