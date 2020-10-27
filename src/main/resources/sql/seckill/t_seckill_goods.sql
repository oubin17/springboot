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

 Date: 27/10/2020 11:04:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `goods_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `on_sale_time` bigint(20) NULL DEFAULT NULL,
  `expire_time` bigint(20) NULL DEFAULT NULL,
  `version` int(11) NULL DEFAULT NULL,
  `create_at` bigint(15) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_modified_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_modified_at` bigint(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES ('42f3f33e-04dc-4f24-aecb-29460fb128d3', '商品:并发测试', 1575874468282, 1575960868282, 0, 1575874468282, '171913', '171913', 1575874468282);
INSERT INTO `t_seckill_goods` VALUES ('ff60a25a-6842-43a8-ac20-8d441657f8f5', '商品:并发测试', 1576141519194, 1576227919198, 0, 1576141519204, '171913', '171913', 1576141519204);

SET FOREIGN_KEY_CHECKS = 1;
