/*
Navicat MySQL Data Transfer

Source Server         : test-local
Source Server Version : 50505
Source Host           : 192.168.206.233:3306
Source Database       : bitcoin

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-03-01 16:10:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `my_wallet_transaction`
-- ----------------------------
DROP TABLE IF EXISTS `my_wallet_transaction`;
CREATE TABLE `my_wallet_transaction` (
  `hash` varchar(64) NOT NULL,
  `value` bigint(20) DEFAULT NULL,
  `value_str` varchar(32) DEFAULT NULL,
  `fee` bigint(20) DEFAULT NULL,
  `fee_str` varchar(32) DEFAULT NULL,
  `version` bigint(11) DEFAULT NULL,
  `depth` int(11) DEFAULT NULL,
  `from_address` varchar(128) DEFAULT NULL,
  `to_address` varchar(128) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of my_wallet_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for `watch_address`
-- ----------------------------
DROP TABLE IF EXISTS `watch_address`;
CREATE TABLE `watch_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(64) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of watch_address
-- ----------------------------
