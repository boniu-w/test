/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.5-local
 Source Server Type    : MySQL
 Source Server Version : 50555
 Source Host           : localhost:3306
 Source Schema         : wg

 Target Server Type    : MySQL
 Target Server Version : 50555
 File Encoding         : 65001

 Date: 07/09/2020 16:59:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wordbook
-- ----------------------------
DROP TABLE IF EXISTS `wordbook`;
CREATE TABLE `wordbook`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `field_name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字段名',
  `field_code` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` int(3) NULL DEFAULT NULL COMMENT '属性'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wordbook
-- ----------------------------
INSERT INTO `wordbook` VALUES ('ba5deb451cb111ea978a6c2b59f99e59', '交易主体', 'transaction_subject', NULL, NULL, 1);
INSERT INTO `wordbook` VALUES ('ba5df13a1cb111ea978a6c2b59f99e59', '交易主体账号', 'account_subject', NULL, NULL, 2);
INSERT INTO `wordbook` VALUES ('ba5df3c01cb111ea978a6c2b59f99e59', '交易主体卡号', 'card_entity', NULL, NULL, 3);
INSERT INTO `wordbook` VALUES ('ba5df6351cb111ea978a6c2b59f99e59', '收付标志', 'recovery_mark', NULL, NULL, 4);
INSERT INTO `wordbook` VALUES ('ba5df8131cb111ea978a6c2b59f99e59', '交易日期', 'transaction_date', NULL, NULL, 5);
INSERT INTO `wordbook` VALUES ('ba5df9f51cb111ea978a6c2b59f99e59', '交易对手', 'counter_party', NULL, NULL, 6);
INSERT INTO `wordbook` VALUES ('ba5dfbfe1cb111ea978a6c2b59f99e59', '交易对手账号', 'account_counterparty', NULL, NULL, 7);
INSERT INTO `wordbook` VALUES ('ba5dfdd01cb111ea978a6c2b59f99e59', '交易对手卡号', 'card_counterparty', NULL, NULL, 8);
INSERT INTO `wordbook` VALUES ('ba5dffa81cb111ea978a6c2b59f99e59', '交易金额', 'transaction_amount', NULL, NULL, 9);
INSERT INTO `wordbook` VALUES ('ba5e01721cb111ea978a6c2b59f99e59', '摘要', 'abstract_content', NULL, NULL, 11);
INSERT INTO `wordbook` VALUES ('ba5e034e1cb111ea978a6c2b59f99e59', '交易后余额', 'balance_transaction', NULL, NULL, 10);
INSERT INTO `wordbook` VALUES ('ba5e05001cb111ea978a6c2b59f99e59', '交易主体归属行', 'transaction_bank', NULL, NULL, 12);
INSERT INTO `wordbook` VALUES ('ba5e06ac1cb111ea978a6c2b59f99e59', '交易对手归属行', 'counterparty_bank', NULL, NULL, 13);
INSERT INTO `wordbook` VALUES ('ba5e085c1cb111ea978a6c2b59f99e59', '交易地点', 'place_transaction', NULL, NULL, 14);
INSERT INTO `wordbook` VALUES ('ba5e0a021cb111ea978a6c2b59f99e59', '交易方式', 'trading_place', NULL, NULL, 15);
INSERT INTO `wordbook` VALUES ('ba5e0ba71cb111ea978a6c2b59f99e59', '交易流水号', 'transaction_number', NULL, NULL, 16);
INSERT INTO `wordbook` VALUES ('ba5e0d561cb111ea978a6c2b59f99e59', 'MAC地址', 'mac', NULL, NULL, 17);
INSERT INTO `wordbook` VALUES ('ba5e0eff1cb111ea978a6c2b59f99e59', 'IP地址', 'ip', NULL, NULL, 18);
INSERT INTO `wordbook` VALUES ('ba5e10a51cb111ea978a6c2b59f99e59', '币种', 'currency', NULL, NULL, 19);
INSERT INTO `wordbook` VALUES ('ba5e129e1cb111ea978a6c2b59f99e59', '备注', 'temarks', NULL, NULL, 20);
INSERT INTO `wordbook` VALUES ('ba5e14521cb111ea978a6c2b59f99e59', '交易机构号', 'trading_no', NULL, NULL, 21);
INSERT INTO `wordbook` VALUES ('ba5e15fe1cb111ea978a6c2b59f99e59', '柜员号', 'teller_number', NULL, NULL, 22);
INSERT INTO `wordbook` VALUES ('ba5e17a61cb111ea978a6c2b59f99e59', '对方机构号', 'institution_party', NULL, NULL, 23);
INSERT INTO `wordbook` VALUES ('ba5e17c61cb111ea978a6c2b59f99e59', '交易是否成功', 'successful_not', NULL, NULL, 24);
INSERT INTO `wordbook` VALUES ('639560096e5711eab67900ff1c286400', '日志号', 'log_number', NULL, NULL, 25);
INSERT INTO `wordbook` VALUES ('235763546e5c11eab67900ff1c286400', '客户代码', 'customer_code', NULL, NULL, 26);
INSERT INTO `wordbook` VALUES ('10b753976e5e11eab67900ff1c286400', 'APSH地点线索', 'apsh_place', NULL, NULL, 27);
INSERT INTO `wordbook` VALUES ('97edad996e5e11eab67900ff1c286400', '交易对手客户代码', 'matcher_code', NULL, NULL, 28);
INSERT INTO `wordbook` VALUES ('30adbefa6e5f11eab67900ff1c286400', '对手交易后余额', 'matcher_balance', NULL, NULL, 29);
INSERT INTO `wordbook` VALUES ('123', '交易主体证件号', 'subject_credentials', NULL, NULL, 30);
INSERT INTO `wordbook` VALUES ('1234', '交易对手证件号', 'adversary_credentials', NULL, NULL, 31);
INSERT INTO `wordbook` VALUES ('2223', '交易记录ID', 'transaction_records_id', NULL, NULL, 32);
INSERT INTO `wordbook` VALUES ('3322', '报告机构', 'report_organization', NULL, NULL, 33);
INSERT INTO `wordbook` VALUES ('4422', '涉外收支分类', 'she_wai_fen_lei', NULL, NULL, 34);
INSERT INTO `wordbook` VALUES ('445566', '代办人名称', 'agent_name', NULL, NULL, 35);
INSERT INTO `wordbook` VALUES ('88777', '代办人证件号码', 'agent_credentials', NULL, NULL, 36);
INSERT INTO `wordbook` VALUES ('2222222222', '凭证号码', 'voucher_number', NULL, NULL, 37);
INSERT INTO `wordbook` VALUES ('333333333', '凭证类型', 'voucher_type', NULL, NULL, 38);

SET FOREIGN_KEY_CHECKS = 1;
