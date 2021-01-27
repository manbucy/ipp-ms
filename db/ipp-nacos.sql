/*
 Navicat Premium Data Transfer

 Source Server         : local-docker
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 192.168.169.2:33306
 Source Schema         : ipp-nacos

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 26/12/2020 16:36:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (5, 'ipp-auth-server-dev.yml', 'DEFAULT_GROUP', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', '4aad5e665203a6e7a5a62852015093bd', '2020-12-24 09:12:18', '2020-12-24 09:12:18', 'nacos', '192.168.169.1', '', 'ipp-dev', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (6, 'ipp-product-server-dev.yml', 'DEFAULT_GROUP', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '99bfaabb9c6adeba1f98e7882b5eb7cc', '2020-12-24 09:12:38', '2020-12-24 09:12:38', 'nacos', '192.168.169.1', '', 'ipp-dev', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (7, 'ipp-gateway-server-dev.yml', 'DEFAULT_GROUP', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug', '35c88fe610cf46eb0f2d9a485ce7347a', '2020-12-24 09:12:55', '2020-12-25 07:56:19', 'nacos-ipp', '192.168.169.1', '', 'ipp-dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'ipp-auth-server-sit.yml', 'DEFAULT_GROUP', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 172.20.1.20\r\n        port: 6379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://172.20.1.10:3306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', 'dc7df035932d7715b9ed9c306a9babd9', '2020-12-24 09:14:05', '2020-12-24 09:15:50', 'nacos', '192.168.169.1', '', 'ipp-sit', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (9, 'ipp-product-server-sit.yml', 'DEFAULT_GROUP', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 172.20.1.20\r\n        port: 6379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://172.20.1.10:3306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '0c7840a60d7879c145fd604c2959cb73', '2020-12-24 09:14:27', '2020-12-24 09:16:12', 'nacos', '192.168.169.1', '', 'ipp-sit', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (10, 'ipp-gateway-server-sit.yml', 'DEFAULT_GROUP', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 172.20.1.20\r\n      port: 6379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug', '46e9bcbdfe9974a8bef154f99c482e4b', '2020-12-24 09:14:50', '2020-12-25 07:56:35', 'nacos-ipp', '192.168.169.1', '', 'ipp-sit', '', '', '', 'yaml', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'ipp-auth-server-dev.yml', 'DEFAULT_GROUP', '', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', '4aad5e665203a6e7a5a62852015093bd', '2010-05-05 00:00:00', '2020-12-24 09:12:18', 'nacos', '192.168.169.1', 'I', 'ipp-dev');
INSERT INTO `his_config_info` VALUES (0, 2, 'ipp-product-server-dev.yml', 'DEFAULT_GROUP', '', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '99bfaabb9c6adeba1f98e7882b5eb7cc', '2010-05-05 00:00:00', '2020-12-24 09:12:38', 'nacos', '192.168.169.1', 'I', 'ipp-dev');
INSERT INTO `his_config_info` VALUES (0, 3, 'ipp-gateway-server-dev.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', '0e1b3c5ab0123ca1b84f61cf66be08e5', '2010-05-05 00:00:00', '2020-12-24 09:12:55', 'nacos', '192.168.169.1', 'I', 'ipp-dev');
INSERT INTO `his_config_info` VALUES (0, 4, 'ipp-auth-server-sit.yml', 'DEFAULT_GROUP', '', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', '4aad5e665203a6e7a5a62852015093bd', '2010-05-05 00:00:00', '2020-12-24 09:14:05', 'nacos', '192.168.169.1', 'I', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (0, 5, 'ipp-product-server-sit.yml', 'DEFAULT_GROUP', '', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '99bfaabb9c6adeba1f98e7882b5eb7cc', '2010-05-05 00:00:00', '2020-12-24 09:14:27', 'nacos', '192.168.169.1', 'I', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (0, 6, 'ipp-gateway-server-sit.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', '0e1b3c5ab0123ca1b84f61cf66be08e5', '2010-05-05 00:00:00', '2020-12-24 09:14:50', 'nacos', '192.168.169.1', 'I', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (8, 7, 'ipp-auth-server-sit.yml', 'DEFAULT_GROUP', '', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', '4aad5e665203a6e7a5a62852015093bd', '2010-05-05 00:00:00', '2020-12-24 09:15:50', 'nacos', '192.168.169.1', 'U', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (9, 8, 'ipp-product-server-sit.yml', 'DEFAULT_GROUP', '', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '99bfaabb9c6adeba1f98e7882b5eb7cc', '2010-05-05 00:00:00', '2020-12-24 09:16:12', 'nacos', '192.168.169.1', 'U', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (10, 9, 'ipp-gateway-server-sit.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', '0e1b3c5ab0123ca1b84f61cf66be08e5', '2010-05-05 00:00:00', '2020-12-24 09:16:28', 'nacos', '192.168.169.1', 'U', 'ipp-sit');
INSERT INTO `his_config_info` VALUES (1, 10, 'ipp-auth-server-dev.yml', 'DEFAULT_GROUP', '', 'hello: world\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nlogging:\r\n    level:\r\n        net.manbucy: debug', '4aad5e665203a6e7a5a62852015093bd', '2010-05-05 00:00:00', '2020-12-24 09:16:37', 'nacos', '192.168.169.1', 'D', '');
INSERT INTO `his_config_info` VALUES (3, 11, 'ipp-product-server-dev.yml', 'DEFAULT_GROUP', '', 'product: \r\n    name: 福寿连连\r\n    code: 4045\r\nspring:\r\n    redis:\r\n        database: 0\r\n        host: 192.168.169.2\r\n        port: 36379\r\n    datasource:\r\n        type: com.alibaba.druid.pool.DruidDataSource\r\n        driver-class-name: com.mysql.cj.jdbc.Driver\r\n        username: ipp-cover\r\n        password: cover#123\r\n        url: jdbc:mysql://192.168.169.2:33306/ipp-cover-auth?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n        druid:\r\n            initial-size: 2\r\n            max-active: 30\r\n            min-idle: 2\r\n            max-wait: 1234\r\n            pool-prepared-statements: true\r\n            max-pool-prepared-statement-per-connection-size: 5\r\n            validation-query: select 1\r\n            validation-query-timeout: 1\r\n            test-on-borrow: true\r\n            test-on-return: true\r\n            test-while-idle: true\r\n            time-between-eviction-runs-millis: 10000\r\n            min-evictable-idle-time-millis: 30001\r\n            async-close-connection-enable: true\r\nloggin:\r\n    level:\r\n        net.manbucy: debug', '99bfaabb9c6adeba1f98e7882b5eb7cc', '2010-05-05 00:00:00', '2020-12-24 09:16:41', 'nacos', '192.168.169.1', 'D', '');
INSERT INTO `his_config_info` VALUES (4, 12, 'ipp-gateway-server-dev.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', '0e1b3c5ab0123ca1b84f61cf66be08e5', '2010-05-05 00:00:00', '2020-12-24 09:16:44', 'nacos', '192.168.169.1', 'D', '');
INSERT INTO `his_config_info` VALUES (7, 13, 'ipp-gateway-server-dev.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 192.168.169.2\r\n      port: 36379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', '0e1b3c5ab0123ca1b84f61cf66be08e5', '2010-05-05 00:00:00', '2020-12-25 07:56:19', 'nacos-ipp', '192.168.169.1', 'U', 'ipp-dev');
INSERT INTO `his_config_info` VALUES (10, 14, 'ipp-gateway-server-sit.yml', 'DEFAULT_GROUP', '', 'spring:\r\n    redis:\r\n      database: 0\r\n      host: 172.20.1.20\r\n      port: 6379\r\n    cloud:\r\n        gateway:\r\n            routes:\r\n              - id: ipp-auth-server\r\n                uri: lb://ipp-auth-server\r\n                predicates:\r\n                  - Path=/auth/**\r\n                filters:\r\n                  - RewritePath=/auth(?<segment>.*), $\\{segment}\r\n              - id: ipp-product-server\r\n                uri: lb://ipp-product-server\r\n                predicates:\r\n                  - Path=/product/**\r\n                filters:\r\n                  - RewritePath=/product(?<segment>.*), $\\{segment}  \r\nlogging:\r\n    level:\r\n      net.manbucy: debug\r\n      org.springframework: debug', 'fa5fb428b8bab9b594c71b88a59fc660', '2010-05-05 00:00:00', '2020-12-25 07:56:35', 'nacos-ipp', '192.168.169.1', 'U', 'ipp-sit');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------
INSERT INTO `permissions` VALUES ('ROLE_ADMIN', ':*:*', 'rw');
INSERT INTO `permissions` VALUES ('ROLE_IPP', 'ipp-dev:*:*', 'rw');
INSERT INTO `permissions` VALUES ('ROLE_IPP', 'ipp-sit:*:*', 'rw');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_username_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');
INSERT INTO `roles` VALUES ('nacos-ipp', 'ROLE_IPP');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (4, '1', 'ipp-dev', 'ipp-dev', 'ipp-dev', 'nacos', 1608799238842, 1608799238842);
INSERT INTO `tenant_info` VALUES (5, '1', 'ipp-sit', 'ipp-sit', 'ipp-sit', 'nacos', 1608799249289, 1608799249289);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
INSERT INTO `users` VALUES ('nacos-ipp', '$2a$10$zzuoSDooZQpJVaABHfH8h.Y06.aRwhPdOURwpwC5xWPmqiC.24zVa', 1);

SET FOREIGN_KEY_CHECKS = 1;
