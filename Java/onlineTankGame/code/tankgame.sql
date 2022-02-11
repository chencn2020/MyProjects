/*
 Navicat Premium Data Transfer

 Source Server         : tankGame
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : tankgame

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 24/12/2019 23:53:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for rankinformation
-- ----------------------------
DROP TABLE IF EXISTS `rankinformation`;
CREATE TABLE `rankinformation`  (
  `UserName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Score` int(255) NULL DEFAULT NULL,
  `Time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MAX` int(255) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rankinformation
-- ----------------------------
INSERT INTO `rankinformation` VALUES ('Chen', 320, '2019-12-24 12:09:43', 1);
INSERT INTO `rankinformation` VALUES ('KDH', 180, '2019-12-24 23:30:59', 5);
INSERT INTO `rankinformation` VALUES ('CLH', 180, '2015-3-16 00:28:11', 6);
INSERT INTO `rankinformation` VALUES ('FGD', 300, '2004-3-17 13:09:05', 4);
INSERT INTO `rankinformation` VALUES ('JYC', 0, '2002-6-9 18:45:28', 8);
INSERT INTO `rankinformation` VALUES ('BJTU', 310, '2013-1-11 08:27:13', 3);
INSERT INTO `rankinformation` VALUES ('BJ', 160, '2013-1-11 08:27:13', 7);
INSERT INTO `rankinformation` VALUES ('BJTU', 310, '2019-12-24 12:09:45', 3);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `UserName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Competitor` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `YourScore` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CompetitorScore` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `GameTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT ''
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('Chen', 'FGD', '240', '300', '2004-5-18 23:47:49');
INSERT INTO `record` VALUES ('FGD', 'Chen', '300', '240', '2004-5-18 23:47:49');
INSERT INTO `record` VALUES ('CLH', 'KDH', '180', '20', '2019-4-8 02:13:36');
INSERT INTO `record` VALUES ('KDH', 'CLH', '20', '180', '2019-4-8 02:13:36');
INSERT INTO `record` VALUES ('BJ', 'FGD', '160', '200', '2018-4-28 02:38:05');
INSERT INTO `record` VALUES ('FGD', 'BJ', '200', '160', '2018-4-28 02:38:05');
INSERT INTO `record` VALUES ('CLH', 'KDH', '90', '20', '2020-7-20 23:05:21');
INSERT INTO `record` VALUES ('KDH', 'CLH', '20', '90', '2020-7-20 23:05:21');
INSERT INTO `record` VALUES ('Chen', 'CZW', '320', '350', '2019-12-23 09:41:03');
INSERT INTO `record` VALUES ('CZW', 'Chen', '350', '320', '2019-12-23 09:41:03');
INSERT INTO `record` VALUES ('Chen', 'KDH', '200', '50', '2019-12-23 10:34:00');
INSERT INTO `record` VALUES ('KDH', 'Chen', '50', '200', '2019-12-23 10:34:00');
INSERT INTO `record` VALUES ('BJTU', 'Chen', '310', '200', '2019-12-24 12:09:18');
INSERT INTO `record` VALUES ('Chen', 'BJTU', '200', '310', '2019-12-24 12:09:43');
INSERT INTO `record` VALUES ('Chen', 'KDH', '180', '10', '2019-12-24 12:22:30');
INSERT INTO `record` VALUES ('KDH', 'Chen', '10', '180', '2019-12-24 12:22:30');
INSERT INTO `record` VALUES ('KDH', 'Chen', '180', '50', '2019-12-24 23:30:59');
INSERT INTO `record` VALUES ('Chen', 'KDH', '50', '180', '2019-12-24 23:30:59');

-- ----------------------------
-- Table structure for userinformation
-- ----------------------------
DROP TABLE IF EXISTS `userinformation`;
CREATE TABLE `userinformation`  (
  `UserName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TimeCreated` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Number` int(15) NULL DEFAULT NULL,
  `PhoneNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinformation
-- ----------------------------
INSERT INTO `userinformation` VALUES ('Chen', '12345', '2019-12-04 14:40:35', 11, '18674859684');
INSERT INTO `userinformation` VALUES ('KDH', '12', '2019-12-10 10:59:58', 11, '12343234321');
INSERT INTO `userinformation` VALUES ('CLH', '257', '2019-12-12 10:54:58', 2, '18750385064');
INSERT INTO `userinformation` VALUES ('FGD', '345', '2019-12-14 12:40:35', 2, '18847593845');
INSERT INTO `userinformation` VALUES ('BJ', '542', '2018-12-1 10:54:58', 1, '12345678910');
INSERT INTO `userinformation` VALUES ('JYC', '213', '2018-10-1 10:54:58', 0, '12312312312');
INSERT INTO `userinformation` VALUES ('BJTU', '2580', '2019-12-24 12:03:47', 1, '15907685733');

SET FOREIGN_KEY_CHECKS = 1;
