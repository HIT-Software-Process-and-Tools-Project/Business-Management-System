/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 19/11/2022 23:19:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brand
-- ----------------------------
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '品牌名',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '电话',
  `remark` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of brand
-- ----------------------------
INSERT INTO `brand` VALUES (1, 'AAA', '86734535', NULL);
INSERT INTO `brand` VALUES (2, 'DDD', '86893745', NULL);
INSERT INTO `brand` VALUES (3, 'SSS', '86839644', NULL);
INSERT INTO `brand` VALUES (4, 'ZZZ', '86453453', NULL);
INSERT INTO `brand` VALUES (5, '晨光', '86435884', NULL);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货名',
  `brand` int NULL DEFAULT NULL COMMENT '品牌',
  `storage` int NOT NULL COMMENT '仓库',
  `goodsType` int NOT NULL COMMENT '分类',
  `purchasePrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '进货价',
  `wholesalePrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '批发价',
  `retailPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '零售价',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `remark` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '铅笔', 1, 1, 1, 1.00, 2.00, 3.00, 54, 'nice');
INSERT INTO `goods` VALUES (2, '积木', 2, 2, 2, 9.00, 10.00, 15.00, 255, NULL);
INSERT INTO `goods` VALUES (3, '苹果', 3, 1, 7, 4.00, 5.00, 7.50, 48, NULL);
INSERT INTO `goods` VALUES (4, '牙刷', 4, 1, 5, 2.00, 3.00, 5.00, 6, NULL);
INSERT INTO `goods` VALUES (5, '香蕉', 5, 2, 7, 3.00, 4.00, 7.00, 67, NULL);

-- ----------------------------
-- Table structure for goodstype
-- ----------------------------
DROP TABLE IF EXISTS `goodstype`;
CREATE TABLE `goodstype`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名',
  `remark` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goodstype
-- ----------------------------
INSERT INTO `goodstype` VALUES (1, '文具', NULL);
INSERT INTO `goodstype` VALUES (2, '玩具', NULL);
INSERT INTO `goodstype` VALUES (3, '服装', NULL);
INSERT INTO `goodstype` VALUES (4, '鞋帽', NULL);
INSERT INTO `goodstype` VALUES (5, '日杂', NULL);
INSERT INTO `goodstype` VALUES (6, '百货', NULL);
INSERT INTO `goodstype` VALUES (7, '食品', NULL);
INSERT INTO `goodstype` VALUES (8, '建材', NULL);
INSERT INTO `goodstype` VALUES (9, '其它', NULL);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL,
  `menuCode` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `menuIcon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '管理员管理', '1', NULL, 'Admin', '0', 'admin/AdminManage.vue', 'el-icon-s-custom');
INSERT INTO `menu` VALUES (2, '002', '客户管理', '1', NULL, 'User', '0,1', 'user/UserManage.vue', 'el-icon-user-solid');
INSERT INTO `menu` VALUES (3, '003', '货品管理 ', '1', NULL, 'Goods', '0,1,2', 'goods/GoodsManage', 'el-icon-s-management');
INSERT INTO `menu` VALUES (4, '004', '库存管理', '1', NULL, 'Storage', '0,1', 'storage/StorageManage', 'el-icon-office-building');
INSERT INTO `menu` VALUES (5, '005', '销售管理', '1', NULL, 'Record', '0,1,2', 'record/RecordManage', 'el-icon-s-order');
INSERT INTO `menu` VALUES (6, '006', '收银台', '1', NULL, 'Pos', '0,1,2', 'pos/PosManage', 'el-icon-s-order');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parentid` int NULL DEFAULT NULL COMMENT '父订单ID',
  `iswholesale` int NULL DEFAULT NULL COMMENT '是否为批发订单 1为是，0为否',
  `goods` int NOT NULL COMMENT '货品id',
  `userId` int NULL DEFAULT NULL COMMENT '取货人/补货人',
  `admin_id` int NULL DEFAULT NULL COMMENT '操作人',
  `count` int NULL DEFAULT NULL COMMENT '数量',
  `state` int NULL DEFAULT NULL COMMENT '订单状态 0保存，1已审核，2已收款，3已退货，4已进货',
  `createtime` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `remark` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `totalprice` decimal(10, 2) NULL DEFAULT NULL COMMENT '总金额',
  `profit` decimal(10, 2) NULL DEFAULT NULL COMMENT '毛利润',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 142524521, 1, 4, 6, 1, 2, 1, '2022-11-12 15:14:51', NULL, NULL, NULL);
INSERT INTO `record` VALUES (2, 321545112, 1, 2, 7, 1, 10, 3, '2022-11-02 15:16:21', NULL, NULL, NULL);
INSERT INTO `record` VALUES (3, 654584758, 0, 3, 4, 1, 3, 2, '2022-11-17 16:27:05', NULL, NULL, NULL);
INSERT INTO `record` VALUES (4, 568475867, 1, 3, 9, 1, 4, 0, '2022-11-16 16:41:31', NULL, NULL, NULL);
INSERT INTO `record` VALUES (6, 678678565, 1, 2, 8, 1, 222, 1, '2022-11-19 12:52:05', '', NULL, NULL);
INSERT INTO `record` VALUES (8, 567856956, 1, 1, 5, 1, 1, 3, '2022-11-19 14:11:30', '', NULL, NULL);
INSERT INTO `record` VALUES (9, 739658546, 1, 2, 5, 1, 1, 4, '2022-11-19 14:34:15', '', NULL, NULL);
INSERT INTO `record` VALUES (10, 354784676, 1, 1, 5, 1, 1, 0, '2022-11-19 14:36:38', '', NULL, NULL);
INSERT INTO `record` VALUES (11, 792386765, 1, 1, 5, 1, 5, 2, '2022-11-19 15:16:31', '', NULL, NULL);
INSERT INTO `record` VALUES (12, 739658546, 1, 1, 5, 1, 1, 3, '2022-11-19 15:59:31', '', NULL, NULL);
INSERT INTO `record` VALUES (13, 739658546, 1, 4, 5, 1, 4, 1, '2022-11-19 16:56:39', '', NULL, NULL);
INSERT INTO `record` VALUES (14, NULL, 0, 1, 4, 1, 2, 2, '2022-11-19 22:39:43', '', 6.00, 4.00);
INSERT INTO `record` VALUES (15, NULL, 0, 3, 4, 1, 4, 2, '2022-11-19 22:39:53', '', 30.00, 14.00);

-- ----------------------------
-- Table structure for storage
-- ----------------------------
DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '仓库名',
  `remark` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO `storage` VALUES (1, '门店库房', NULL);
INSERT INTO `storage` VALUES (2, '大库', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '账号',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '电话',
  `role_id` int NULL DEFAULT NULL COMMENT '⻆⾊ 0经理，1仓库管理员，2售货员，3批发客户，4零售客户',
  `isValid` varchar(4) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'Y' COMMENT '是否有效，Y有效，其他⽆效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'sa', '经理', '18', 56, 1, '13123453456', 0, 'Y');
INSERT INTO `user` VALUES (2, 'user_a', '仓库管理员', '123', 30, 1, '13342345667', 1, 'Y');
INSERT INTO `user` VALUES (3, '1234', '售货员', '1111', 23, 2, '13345567833', 2, 'Y');
INSERT INTO `user` VALUES (4, 'sdfg', '零售用户', '1234', 23, 1, '13333333333', 4, 'Y');
INSERT INTO `user` VALUES (5, 'asd', 'Alice', '1234', 34, 2, '14444444444', 3, 'Y');
INSERT INTO `user` VALUES (6, 'werstg', 'Cedar', '3457', 45, 1, '13567845678', 3, 'Y');
INSERT INTO `user` VALUES (7, 'sdrg', 'Venu', '2345', 56, 1, '12345655343', 3, 'Y');
INSERT INTO `user` VALUES (8, 'sdfa', 'Muiden', '4567', 36, 1, '15675784544', 3, 'Y');
INSERT INTO `user` VALUES (9, 'sdf', 'Salachi', '34533', 52, 1, '15789045675', 3, 'Y');
INSERT INTO `user` VALUES (10, 'sdfh', 'Krikavova', '2352', 62, 2, '14567455555', 3, 'Y');
INSERT INTO `user` VALUES (11, 'fgyj', 'Dena', '3461', 42, 2, '14679656755', 3, 'Y');
INSERT INTO `user` VALUES (12, 'fgjh', 'Balinho', '4567', 19, 1, '15637456455', 3, 'Y');

SET FOREIGN_KEY_CHECKS = 1;
