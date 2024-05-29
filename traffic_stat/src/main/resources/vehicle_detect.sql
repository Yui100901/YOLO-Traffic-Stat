

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base
-- ----------------------------
DROP TABLE IF EXISTS `base`;
CREATE TABLE `base`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vd_device
-- ----------------------------
DROP TABLE IF EXISTS `vd_device`;
CREATE TABLE `vd_device`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ip',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备详细地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vd_record
-- ----------------------------
DROP TABLE IF EXISTS `vd_record`;
CREATE TABLE `vd_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  `device_id` bigint NULL DEFAULT NULL COMMENT '检测设备的id',
  `vehicle_type` int NULL DEFAULT NULL COMMENT '车辆类型（与检测系统类型对应）',
  `vehicle_in_number` int NULL DEFAULT NULL COMMENT '车辆in数量',
  `vehicle_out_number` int NULL DEFAULT NULL COMMENT '车辆out数量',
  `granularity` int NULL DEFAULT NULL COMMENT '统计的时间粒度（秒）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `main`(`id`) USING BTREE,
  INDEX `time`(`created`) USING BTREE,
  INDEX `device`(`device_id`) USING BTREE,
  INDEX `type`(`vehicle_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4643983 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vd_user
-- ----------------------------
DROP TABLE IF EXISTS `vd_user`;
CREATE TABLE `vd_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `modifier` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `created` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modified` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
