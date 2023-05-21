-- ----------------------------
-- heidisql 生成的
-- ----------------------------
CREATE TABLE `file_my` (
	`id` BIGINT(19) UNSIGNED NOT NULL COMMENT 'id',
	`file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '文件名' COLLATE 'utf8mb4_0900_ai_ci',
	`suffix` VARCHAR(255) NULL DEFAULT NULL COMMENT '文件后缀' COLLATE 'utf8mb4_0900_ai_ci',
	`absolute_path` VARCHAR(255) NULL DEFAULT NULL COMMENT '文件绝对路径' COLLATE 'utf8mb4_0900_ai_ci',
	`length` BIGINT(19) NULL DEFAULT NULL COMMENT '文件大小(k)',
	`sha256` VARCHAR(255) NULL DEFAULT NULL COMMENT 'sha256值' COLLATE 'utf8mb4_0900_ai_ci',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`del_flag` TINYINT(1) NULL DEFAULT '0' COMMENT '删除标志位'
)
COMMENT='存放文件的'
COLLATE='utf8mb4_0900_ai_ci'
ENGINE=InnoDB
;
