/*
Navicat MySQL Data Transfer

Source Server         : node0数据库
Source Server Version : 50642
Source Host           : node0:3306
Source Database       : retry

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-02-20 23:41:23
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `lid` int(10) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(20) NOT NULL,
  `updateTime` varchar(20) NOT NULL,
  `message` varchar(100) NOT NULL,
  PRIMARY KEY (`lid`),
  UNIQUE KEY `orderNo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for `OrderInfo`
-- ----------------------------
DROP TABLE IF EXISTS `OrderInfo`;
CREATE TABLE `OrderInfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(20) NOT NULL,
  `status` varchar(1) NOT NULL DEFAULT '0',
  `createTime` varchar(20) NOT NULL,
  `paySuccessTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderNo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of OrderInfo
-- ----------------------------

-- ----------------------------
-- Table structure for `Transaction`
-- ----------------------------
DROP TABLE IF EXISTS `Transaction`;
CREATE TABLE `Transaction` (
  `tid` int(10) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(20) NOT NULL,
  `notifyCount` int(1) NOT NULL DEFAULT '0',
  `notifyStatus` varchar(1) NOT NULL DEFAULT '0',
  `notifySuccessTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Transaction
-- ----------------------------
