/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : edu_javaweb

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 30/06/2025 22:54:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户姓名',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `address` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '地址',
  `company` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `contact_person` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `customer_type` tinyint(4) NULL DEFAULT 1 COMMENT '客户类型：1-个人，2-企业',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-活跃，0-停用',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO `customers` VALUES (1, '张三', 'zhangsan@email.com', '13800138001', '北京市朝阳区建国路1号', '', '', 1, 1, '2025-06-29 21:42:33', '2025-06-29 21:52:12');
INSERT INTO `customers` VALUES (2, '李四', 'lisi@email.com', '13800138002', '上海市浦东新区陆家嘴金融中心', NULL, NULL, 1, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (4, '阿里巴巴集团', 'contact@alibaba.com', '0571-85022088', '浙江省杭州市余杭区文一西路969号', '阿里巴巴集团控股有限公司', '马云', 2, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (5, '腾讯科技', 'contact@tencent.com', '0755-86013388', '广东省深圳市南山区科技园', '深圳市腾讯计算机系统有限公司', '马化腾', 2, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (6, '百度公司', 'contact@baidu.com', '010-59928888', '北京市海淀区上地十街10号', '北京百度网讯科技有限公司', '李彦宏', 2, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (7, '赵六', 'zhaoliu@email.com', '13800138006', '成都市高新区天府大道', NULL, NULL, 1, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (8, '钱七', 'qianqi@email.com', '13800138007', '杭州市西湖区文三路', NULL, NULL, 1, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (9, '华为技术', 'contact@huawei.com', '0755-28780808', '广东省深圳市龙岗区坂田华为基地', '华为技术有限公司', '任正非', 2, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `customers` VALUES (10, '小米科技', 'contact@xiaomi.com', '010-60606666', '北京市海淀区清河中街68号', '小米科技有限责任公司', '雷军', 2, 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键（员工编号）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工性别',
  `salary` int(10) NULL DEFAULT NULL COMMENT '员工薪资',
  `join_date` date NULL DEFAULT NULL COMMENT '入职日期',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '行秋', 'xingqiu', '123456', '1', 5000, '2023-11-01', NULL);
INSERT INTO `employee` VALUES (2, '班尼特', '班尼特', '123456', '1', 6000, '2023-09-13', NULL);
INSERT INTO `employee` VALUES (3, '砂糖', '砂糖', '123456', '0', 7000, '2023-06-13', NULL);
INSERT INTO `employee` VALUES (4, '菲谢尔', '菲谢尔', '123456', '0', 8000, '2023-04-19', NULL);
INSERT INTO `employee` VALUES (5, '凯亚', '凯亚', '123456', '1', 9000, '2023-03-08', NULL);
INSERT INTO `employee` VALUES (6, '莫娜', '莫娜', '123456', '0', 10000, '2020-06-24', NULL);
INSERT INTO `employee` VALUES (7, '丽莎', '丽莎', '123456', '0', 8000, '2019-02-20', NULL);
INSERT INTO `employee` VALUES (8, '迪卢克', '迪卢克', '123456', '1', 15000, '2000-10-19', NULL);
INSERT INTO `employee` VALUES (9, '刻晴', '刻晴', '123456', '0', 13000, '2005-06-25', NULL);
INSERT INTO `employee` VALUES (10, '香菱', '香菱', '123456', '0', 8000, '2015-02-28', NULL);
INSERT INTO `employee` VALUES (11, '雷泽', '雷泽', '123456', '1', 9000, '2018-09-21', NULL);
INSERT INTO `employee` VALUES (15, '塞维亚', '塞维亚', '123456', '2', 10000, '2025-03-28', 1);
INSERT INTO `employee` VALUES (16, '管理员', 'admin', '123456', '1', 99999, '2025-05-27', NULL);
INSERT INTO `employee` VALUES (19, 'mxz', NULL, '123456', '1', 1000000, '2025-06-26', 1);
INSERT INTO `employee` VALUES (20, 'xzm', 'xzm', '123456', '1', 11111, '2025-06-29', NULL);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名称',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品描述',
  `price` decimal(10, 2) NOT NULL COMMENT '产品价格',
  `stock_quantity` int(11) NULL DEFAULT 0 COMMENT '库存数量',
  `category` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品分类',
  `brand` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态：1-上架，0-下架',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'iPhone 15 Pro', '苹果最新旗舰手机，搭载A17 Pro芯片', 7999.00, 50, '手机', 'Apple', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (2, 'MacBook Air M2', '轻薄便携笔记本电脑，13.6英寸', 8999.00, 30, '笔记本', 'Apple', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (3, '小米13 Ultra', '徕卡影像旗舰手机', 5999.00, 80, '手机', '小米', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (4, '华为MateBook X Pro', '高性能商务笔记本', 9999.00, 25, '笔记本', '华为', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (5, 'iPad Pro 12.9', '专业级平板电脑', 6999.00, 40, '平板', 'Apple', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (6, 'Surface Pro 9', '微软二合一平板电脑', 7299.00, 35, '平板', 'Microsoft', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (7, 'AirPods Pro 2', '主动降噪无线耳机', 1899.00, 100, '耳机', 'Apple', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (8, 'Sony WH-1000XM5', '头戴式降噪耳机', 2399.00, 60, '耳机', 'Sony', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (9, 'Dell XPS 13', '超薄商务笔记本', 8499.00, 20, '笔记本', 'Dell', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');
INSERT INTO `products` VALUES (10, 'Samsung Galaxy S23', '三星旗舰手机', 5499.00, 70, '手机', 'Samsung', 1, '2025-06-29 21:42:33', '2025-06-29 21:42:33');

SET FOREIGN_KEY_CHECKS = 1;
