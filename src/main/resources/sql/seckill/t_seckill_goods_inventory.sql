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

 Date: 27/10/2020 11:04:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_seckill_goods_inventory
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods_inventory`;
CREATE TABLE `t_seckill_goods_inventory`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remaining_quantity` int(11) NOT NULL,
  `goods_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_seckill_goods_inventory
-- ----------------------------
INSERT INTO `t_seckill_goods_inventory` VALUES ('5e158d55-a36d-4790-8f77-e0acfe3d7779', 0, 'ff60a25a-6842-43a8-ac20-8d441657f8f5');
INSERT INTO `t_seckill_goods_inventory` VALUES ('9a62ff18-024d-4155-8219-8a639959dc7d', 0, '42f3f33e-04dc-4f24-aecb-29460fb128d3');

SET FOREIGN_KEY_CHECKS = 1;
