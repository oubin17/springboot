/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50639
 Source Host           : localhost:3306
 Source Schema         : spring_boot

 Target Server Type    : MySQL
 Target Server Version : 50639
 File Encoding         : 65001

 Date: 27/10/2020 11:03:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` bigint(15) NULL DEFAULT NULL,
  `last_modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_modified_time` bigint(15) NULL DEFAULT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remaining_quantity` int(11) NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `expire_time` bigint(20) NULL DEFAULT NULL,
  `on_sale_time` bigint(20) NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  `create_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `last_modified_at` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_goods
-- ----------------------------
INSERT INTO `t_goods` VALUES ('22cf8f24-a197-49f0-bf67-e4775bad1938', '171913', 1569376619993, '171913', 1569405679929, 'pro 13', 0, 38, 1569463019981, 1569376619978, 1, '2019-10-24 11:45:15', '0000-00-00 00:00:00');
INSERT INTO `t_goods` VALUES ('5250b2d0-0cdc-4bfe-99e7-eb908e9ba070', '171913', 1569377046409, '171913', 1569571491825, '华为magic book pro', 98, 156, 1569571488454, 1569377046393, 1, '2020-09-16 10:07:25', '0000-00-00 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
