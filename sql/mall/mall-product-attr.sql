/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云数据库
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : rm-j6cxl87683w973f78ho.mysql.rds.aliyuncs.com:3306
 Source Schema         : ruoyi-vue-pro

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/01/2022 16:07:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_product_attr_key
-- ----------------------------
DROP TABLE IF EXISTS `mall_product_attr_key`;
CREATE TABLE `mall_product_attr_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '规格键编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '规格键名称',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态\n     *\n     * 1-开启\n     * 2-禁用',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格键';

-- ----------------------------
-- Records of mall_product_attr_key
-- ----------------------------
BEGIN;
INSERT INTO `mall_product_attr_key` VALUES (1, '手机型号2', 0, '1', '2022-01-02 17:27:54', '1', '2022-01-02 17:28:01', b'1');
INSERT INTO `mall_product_attr_key` VALUES (2, '手机型号', 0, '1', '2022-01-02 17:28:08', '1', '2022-01-02 19:01:34', b'0');
INSERT INTO `mall_product_attr_key` VALUES (3, '衬衫', 0, '1', '2022-01-02 19:03:00', '1', '2022-01-02 19:03:00', b'0');
INSERT INTO `mall_product_attr_key` VALUES (4, '无用的', 0, '1', '2022-01-03 15:50:08', '1', '2022-01-03 15:50:11', b'1');
COMMIT;

-- ----------------------------
-- Table structure for mall_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `mall_product_attr_value`;
CREATE TABLE `mall_product_attr_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '规格值编号',
  `attr_key_id` bigint(20) NOT NULL COMMENT '规格键编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '规格值名字',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格值';

-- ----------------------------
-- Records of mall_product_attr_value
-- ----------------------------
BEGIN;
INSERT INTO `mall_product_attr_value` VALUES (1, 2, 'iPhone 13 Pro', 0, '1', '2022-01-03 11:13:11', '1', '2022-01-03 11:13:37', b'0');
INSERT INTO `mall_product_attr_value` VALUES (2, 2, 'iPhone 13', 0, '1', '2022-01-03 11:13:46', '1', '2022-01-03 11:56:36', b'0');
INSERT INTO `mall_product_attr_value` VALUES (3, 2, 'iPhone 13 Pro Max', 0, '1', '2022-01-03 11:13:58', '1', '2022-01-03 14:55:56', b'0');
INSERT INTO `mall_product_attr_value` VALUES (4, 3, '连衣裙', 0, '1', '2022-01-03 11:51:42', '1', '2022-01-03 11:51:42', b'0');
INSERT INTO `mall_product_attr_value` VALUES (5, 2, '皮衣', 0, '1', '2022-01-03 11:56:53', '1', '2022-01-03 11:57:05', b'1');
INSERT INTO `mall_product_attr_value` VALUES (6, 3, '大一', 0, '1', '2022-01-03 11:57:14', '1', '2022-01-03 11:57:14', b'0');
INSERT INTO `mall_product_attr_value` VALUES (7, 3, '紫色', 1, '1', '2022-01-03 11:57:24', '1', '2022-01-03 15:38:36', b'0');
INSERT INTO `mall_product_attr_value` VALUES (8, 2, 'XiaoMi 12 Pro', 0, '1', '2022-01-03 11:57:34', '1', '2022-01-03 14:50:36', b'0');
INSERT INTO `mall_product_attr_value` VALUES (9, 3, 'BUG了', 0, '1', '2022-01-03 11:57:53', '1', '2022-01-03 14:55:32', b'0');
INSERT INTO `mall_product_attr_value` VALUES (10, 3, '123', 0, '1', '2022-01-03 14:00:34', '1', '2022-01-03 14:04:33', b'1');
INSERT INTO `mall_product_attr_value` VALUES (11, 2, '123', 1, '1', '2022-01-03 14:00:57', '1', '2022-01-03 14:50:41', b'1');
INSERT INTO `mall_product_attr_value` VALUES (12, 3, '毛衣', 0, '1', '2022-01-03 14:55:21', '1', '2022-01-03 15:38:33', b'0');
INSERT INTO `mall_product_attr_value` VALUES (13, 2, '乌鱼子', 1, '1', '2022-01-03 14:55:43', '1', '2022-01-03 14:55:43', b'0');
INSERT INTO `mall_product_attr_value` VALUES (14, 2, 'vivo 12', 0, '1', '2022-01-03 14:57:49', '1', '2022-01-03 14:57:49', b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1185, '商品管理', '', 1, 12, 0, '/mall', 'shopping', NULL, 0, '1', '2022-01-02 17:12:50', '1', '2022-01-02 17:12:50', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1186, '商品规格键管理', '', 2, 0, 1185, 'product-attr-key', '', 'mall/product/attr/index', 0, '', '2022-01-02 17:13:21', '1', '2022-01-02 17:14:56', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1187, '商品规格键查询', 'mall:product-attr-key:query', 3, 1, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '', '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1188, '商品规格键创建', 'mall:product-attr-key:create', 3, 2, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '', '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1189, '商品规格键更新', 'mall:product-attr-key:update', 3, 3, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '', '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1190, '商品规格键删除', 'mall:product-attr-key:delete', 3, 4, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '', '2022-01-02 17:13:21', b'0', 0);
INSERT INTO `sys_menu`(`id`, `name`, `permission`, `menu_type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES (1191, '商品规格键导出', 'mall:product-attr-key:export', 3, 5, 1186, '', '', '', 0, '', '2022-01-02 17:13:21', '', '2022-01-02 17:13:21', b'0', 0);
