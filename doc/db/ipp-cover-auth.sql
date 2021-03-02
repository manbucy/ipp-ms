/*
 Navicat Premium Data Transfer

 Source Server         : local-docker
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 192.168.169.2:33306
 Source Schema         : ipp-cover-auth

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 26/12/2020 16:36:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (1, 'client1', NULL, '{bcrypt}$2a$10$lGENvnRuW0NGq/pRMVns7..PNOWImk9Fkagl8vYFkuCX/fHJZp086', 'server', 'password,authorization_code,client_credentials,refresh_token,implicit', 'http://localhost:48000/home', NULL, NULL, NULL, NULL, '1');

-- ----------------------------
-- Table structure for s_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_permission`;
CREATE TABLE `s_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '许可代码',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_enabled` tinyint(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否可用',
  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统许可表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_permission
-- ----------------------------
INSERT INTO `s_permission` VALUES (1, '查看所有用户', NULL, 'api:find:all:users', NULL, 1, '0', '2020-11-07 18:48:49', '0', '2020-11-07 18:48:49', 0, 0);
INSERT INTO `s_permission` VALUES (2, '修改所有用户', NULL, 'api:update:all:users', NULL, 1, '0', '2020-11-07 18:48:49', '0', '2020-11-07 18:48:49', 0, 0);

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色代码',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_role
-- ----------------------------
INSERT INTO `s_role` VALUES (1, '系统管理员', 'SYSTEM_ADMIN', '当前系统的管理员', '0', '2020-11-07 18:46:09', '0', '2020-11-07 18:46:09', 0, 0);

-- ----------------------------
-- Table structure for s_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `s_role_permission`;
CREATE TABLE `s_role_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) UNSIGNED NOT NULL COMMENT '许可id',
  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色许可表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_role_permission
-- ----------------------------
INSERT INTO `s_role_permission` VALUES (1, 1, 1, '0', '2020-11-07 18:48:49', '0', '2020-11-07 18:48:49', 0, 0);
INSERT INTO `s_role_permission` VALUES (2, 1, 2, '0', '2020-11-07 18:48:49', '0', '2020-11-07 18:48:49', 0, 0);

-- ----------------------------
-- Table structure for s_user
-- ----------------------------
DROP TABLE IF EXISTS `s_user`;
CREATE TABLE `s_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `is_locked` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否禁用',
  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_user
-- ----------------------------
INSERT INTO `s_user` VALUES (1, 'admin', '{bcrypt}$2a$10$YdwZ5NrB1qpLh9XGVP.POueMPqG2lQYwQQX6vgzNF9rYfFctD553G', '管理员', '18255447841', 'http://www.baidu.com/', 0, '0', '2020-10-29 17:01:26', '0', '2020-10-29 17:01:26', 0, 0);

-- ----------------------------
-- Table structure for s_user_role
-- ----------------------------
DROP TABLE IF EXISTS `s_user_role`;
CREATE TABLE `s_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint(20) UNSIGNED NOT NULL COMMENT '角色id',
  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '修改人',
  `update_time` datetime(0) NOT NULL COMMENT '修改时间',
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '版本',
  `is_deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of s_user_role
-- ----------------------------
INSERT INTO `s_user_role` VALUES (1, 1, 1, '0', '2020-11-07 18:48:49', '0', '2020-11-07 18:48:49', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
