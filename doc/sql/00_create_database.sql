/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : jmetrics

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2015-10-12 20:22:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
`job_id`  bigint(20) NOT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`desciption`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  tinyint(4) NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`job_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact

;

-- ----------------------------
-- Records of job
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
`desciption`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`schedule_rate`  int(11) NOT NULL ,
`schedule_unit`  tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度周期的时间单位：0:秒, 1:分, 2:时, 3:日, 4:月, 5:周' ,
`scheduled_count`  int(11) NOT NULL DEFAULT 0 ,
`status`  tinyint(4) NULL DEFAULT NULL ,
`create_time`  datetime NULL DEFAULT NULL ,
`update_time`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`task_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Compact

;

-- ----------------------------
-- Records of task
-- ----------------------------
BEGIN;
COMMIT;
