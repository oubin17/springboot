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

 Date: 27/10/2020 11:04:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` bigint(15) NOT NULL,
  `last_modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_modified_time` bigint(15) NOT NULL,
  `goods_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  `create_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `last_modified_at` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('71ddf495-7d7d-420e-9124-3b1be594a1d0', '171913', 1569377064800, '171913', 1569377287998, '5250b2d0-0cdc-4bfe-99e7-eb908e9ba070', '171913', '171913', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('bd5cdbdc-fbde-4f21-9927-1ad2e51c0477', '4556', 1569392253055, '4556', 1569393182897, '22cf8f24-a197-49f0-bf67-e4775bad1938', '4556', '4556', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('c43dfac5-ec58-40c0-994b-909f0cee458d', '8626', 1569392233804, '8626', 1569393182825, '22cf8f24-a197-49f0-bf67-e4775bad1938', '8626', '8626', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('c5fa107f-b62b-4b65-8007-b92f3a703b63', '1273', 1569392253113, '1273', 1569393182938, '22cf8f24-a197-49f0-bf67-e4775bad1938', '1273', '1273', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('d4d5a8bb-ee39-42e9-bd74-f3db73e6f54d', '171913', 1569377856754, '171913', 1569377874058, '5250b2d0-0cdc-4bfe-99e7-eb908e9ba070', '171913', '171913', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('dc395e54-1625-44ae-be99-1a34a8789553', '1267', 1569392253278, '1267', 1569393183047, '22cf8f24-a197-49f0-bf67-e4775bad1938', '1267', '1267', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');
INSERT INTO `t_order` VALUES ('f43ff161-b42d-4de8-9394-45d4f54088ac', '9551', 1569392253249, '9551', 1569393182997, '22cf8f24-a197-49f0-bf67-e4775bad1938', '9551', '9551', 2, '2019-10-24 11:45:16', '0000-00-00 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
