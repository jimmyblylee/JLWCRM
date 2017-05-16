/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : 192.168.2.3:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-05-16 12:52:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for log_mod_entity
-- ----------------------------
DROP TABLE IF EXISTS `log_mod_entity`;
CREATE TABLE `log_mod_entity` (
  `MOD_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '变更ID',
  `REV_ID` int(11) NOT NULL COMMENT '版本号，关联 LOG_REV_INFO.REV_ID',
  `ENTITY_NAME` varchar(255) DEFAULT NULL COMMENT '变更实体类',
  `ENTITY_ID` int(11) DEFAULT NULL COMMENT '变更实体ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT '变更类型 0:Add, 1:update, 2:delete',
  PRIMARY KEY (`MOD_ID`),
  KEY `LOG_MOD_ENTITY_MOD_ID` (`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志模型变更表';

-- ----------------------------
-- Records of log_mod_entity
-- ----------------------------

-- ----------------------------
-- Table structure for log_rev_info
-- ----------------------------
DROP TABLE IF EXISTS `log_rev_info`;
CREATE TABLE `log_rev_info` (
  `REV_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '版本号',
  `REV_TIMESTMP` bigint(20) DEFAULT NULL COMMENT '时间戳',
  `FEATURE_CODE` varchar(64) DEFAULT NULL COMMENT '模块编码，比如字典维护对应的编码',
  `FUNCTION_CODE` varchar(64) DEFAULT NULL COMMENT '操作行为编码，比如字典维护中权限设置的“增加字典”、”修改字典“等',
  `OPERATOR_ID` int(11) DEFAULT NULL COMMENT '操作人ID',
  `OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '操作人名称',
  `OPERATOR_IP` varchar(64) DEFAULT NULL COMMENT '操作人客户端IP',
  PRIMARY KEY (`REV_ID`),
  KEY `LOG_REV_INFO_REV_TIMESTMP` (`REV_TIMESTMP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志主表';

-- ----------------------------
-- Records of log_rev_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_att_ftp_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_att_ftp_conf`;
CREATE TABLE `sys_att_ftp_conf` (
  `FTP_SERV_IP` varchar(50) NOT NULL COMMENT '服务器ip',
  `FTP_SERV_PORT` varchar(50) NOT NULL COMMENT '服务器端口',
  `FTP_SERV_USER` varchar(50) NOT NULL COMMENT '服务器用户名',
  `FTP_SERV_PWD` varchar(50) NOT NULL COMMENT '服务器密码',
  `FTP_SERV_BASE_PATH` varchar(50) NOT NULL COMMENT '服务器配置的根路径（相对）',
  PRIMARY KEY (`FTP_SERV_IP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP服务器配置信息表';

-- ----------------------------
-- Records of sys_att_ftp_conf
-- ----------------------------
INSERT INTO `sys_att_ftp_conf` VALUES ('172.25.78.213', '21', 'ftp', 'ASDCftp1234', 'file/upload');

-- ----------------------------
-- Table structure for sys_att_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_att_info`;
CREATE TABLE `sys_att_info` (
  `ATT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `ATT_NATURE` varchar(50) DEFAULT NULL COMMENT '模块表',
  `ATT_REF_ID` int(11) DEFAULT NULL COMMENT '业务模块ID',
  `ATT_ORI_NAME` varchar(50) NOT NULL COMMENT '原始文件名，不含路径',
  `ATT_URI` varchar(150) NOT NULL COMMENT '文件路径信息，相对于基础路径。',
  `ATT_DESC` varchar(50) DEFAULT NULL COMMENT '文件描述',
  `ATT_OP_ID` int(11) NOT NULL COMMENT '操作用户id',
  `ATT_OP_NAME` varchar(50) NOT NULL COMMENT '操作用户名',
  `ATT_OP_TIME` date NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`ATT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件管理基本信息表';

-- ----------------------------
-- Records of sys_att_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `DEPT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DEPT_PARENT_ID` int(11) DEFAULT '0' COMMENT '上级组织机构ID',
  `DEPT_NAME` varchar(32) NOT NULL COMMENT '名称',
  `DEPT_TEL` varchar(20) DEFAULT NULL COMMENT '电话',
  `DEPT_FAX` varchar(20) DEFAULT NULL COMMENT '传真',
  `DEPT_EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `DEPT_URL` varchar(100) DEFAULT NULL COMMENT '主页',
  `DEPT_ADDRESS` varchar(128) DEFAULT NULL COMMENT '邮寄地址',
  `DEPT_POSTALCODE` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `DEPT_ORDER_BY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DEPT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', null, '总公司', '8817883', null, '453@qq.com', 'www.asdc.com', '中国北京1', null, '1', null);
INSERT INTO `sys_dept` VALUES ('2', '1', '财务部', '8817881', '010-8817881', '453@qq.com', null, '朝阳区', '10000', '0', null);
INSERT INTO `sys_dept` VALUES ('3', '1', '技术部', '8817882', '010-8817882', '453@qq.com', null, '昌平区', '10000', '0', null);
INSERT INTO `sys_dept` VALUES ('4', '1', '市场部', '8817884', '010-8817884', '433@qq.com', null, '海淀区', '10000', '0', null);
INSERT INTO `sys_dept` VALUES ('11', '1', '招聘中心', null, null, null, null, '北京', null, '1', '1');
INSERT INTO `sys_dept` VALUES ('12', '1', '第二事业部', null, null, null, null, '北京', null, '1', '2');
INSERT INTO `sys_dept` VALUES ('13', '12', '移动通讯部', null, null, null, null, '北京', null, '0', '1');
INSERT INTO `sys_dept` VALUES ('14', '11', '招聘三组', null, null, null, null, '北京', null, '1', '3');
INSERT INTO `sys_dept` VALUES ('15', '11', '招聘一组', null, null, null, null, '北京', null, '1', '1');
INSERT INTO `sys_dept` VALUES ('16', '11', '招聘二组', null, null, null, null, '北京', null, '1', '2');
INSERT INTO `sys_dept` VALUES ('17', '11', '招聘四组', null, null, null, null, '上海', null, '1', '4');

-- ----------------------------
-- Table structure for sys_dept_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_aud`;
CREATE TABLE `sys_dept_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `DEPT_ID` int(11) NOT NULL COMMENT 'ID',
  `DEPT_PARENT_ID` int(11) DEFAULT '0' COMMENT '上级组织机构ID',
  `DEPT_NAME` varchar(32) NOT NULL COMMENT '名称',
  `DEPT_TEL` varchar(20) DEFAULT NULL COMMENT '电话',
  `DEPT_FAX` varchar(20) DEFAULT NULL COMMENT '传真',
  `DEPT_EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `DEPT_URL` varchar(100) DEFAULT NULL COMMENT '主页',
  `DEPT_ADDRESS` varchar(128) DEFAULT NULL COMMENT '邮寄地址',
  `DEPT_POSTALCODE` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `DEPT_ORDER_BY` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DEPT_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表变更记录表';

-- ----------------------------
-- Records of sys_dept_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `DICT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DICT_PARENT_ID` int(11) DEFAULT '0' COMMENT '父级编号',
  `IS_NATURE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为字典类型而非字典选项,1: yes, others: no',
  `DICT_NATURE` varchar(50) NOT NULL COMMENT '类型',
  `DICT_CODE` varchar(50) NOT NULL COMMENT '编码',
  `DICT_VALUE` varchar(50) NOT NULL COMMENT '数据值',
  `DICT_LABEL` varchar(100) NOT NULL COMMENT '标签名',
  `DICT_PINYIN` varchar(100) NOT NULL COMMENT '数据值拼音',
  `DICT_PINYIN_SHORT` varchar(16) NOT NULL COMMENT '数据值拼音缩写',
  `DICT_DESCRIPTION` varchar(150) DEFAULT NULL COMMENT '描述',
  `DICT_ORDER` int(5) NOT NULL COMMENT '排序（升序）',
  `REMARKS` varchar(150) DEFAULT NULL COMMENT '备注信息',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`DICT_ID`),
  KEY `SYS_DICT_DICT_NATURE` (`DICT_NATURE`),
  KEY `SYS_DICT_DICT_CODE` (`DICT_CODE`)
) ENGINE=InnoDB AUTO_INCREMENT=424 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('-10120016', '-10120001', '1', 'RECRUITMENT_STATUS', '430', '已入职', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120015', '-10120001', '1', 'RECRUITMENT_STATUS', '420', '放弃入职', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120014', '-10120001', '1', 'RECRUITMENT_STATUS', '410', '待入职', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120013', '-10120001', '1', 'RECRUITMENT_STATUS', '330', '接受Offer', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120012', '-10120001', '1', 'RECRUITMENT_STATUS', '320', '放弃Offer', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120011', '-10120001', '1', 'RECRUITMENT_STATUS', '310', '谈Offer', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120010', '-10120001', '1', 'RECRUITMENT_STATUS', '250', '面试通过', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120009', '-10120001', '1', 'RECRUITMENT_STATUS', '240', '面试未通过', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120007', '-10120001', '1', 'RECRUITMENT_STATUS', '230', '没来面试', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120006', '-10120001', '1', 'RECRUITMENT_STATUS', '220', '已预约面试', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120005', '-10120001', '1', 'RECRUITMENT_STATUS', '210', '尚未预约面试', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120004', '-10120001', '1', 'RECRUITMENT_STATUS', '130', '简历通过', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120003', '-10120001', '1', 'RECRUITMENT_STATUS', '120', '简历未通过', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120002', '-10120001', '1', 'RECRUITMENT_STATUS', '110', '待反馈', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('-10120001', '1', '0', 'RECRUITMENT_STATUS', 'NATURE', '招聘状态', '招聘状态', 'zhaopinzhuangtai', 'zpzt', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10110005', '-10110001', '1', 'RECRUITMENT_PHASE', '4', '入职', '招聘阶段', 'ruzhi', 'rz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10110004', '-10110001', '1', 'RECRUITMENT_PHASE', '3', 'Offer', '招聘阶段', 'offer', 'offer', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10110003', '-10110001', '1', 'RECRUITMENT_PHASE', '2', '面试', '招聘阶段', 'mianshi', 'ms', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10110002', '-10110001', '1', 'RECRUITMENT_PHASE', '1', '推荐反馈', '招聘阶段', 'tuijianfankui', 'tjfk', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10110001', '1', '0', 'RECRUITMENT_PHASE', 'NATURE', '招聘阶段', '招聘阶段', 'zhaopinjieduan', 'zpjd', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('-10100003', '-10100001', '0', 'STATUS_TYPE', 'false', '禁用', '状态类型', 'jinyong', 'jy', '', '1', '', '1');
INSERT INTO `sys_dict` VALUES ('-10100002', '-10100001', '0', 'STATUS_TYPE', 'true', '可用', '状态类型', 'keyong', 'ky', '', '2', '', '1');
INSERT INTO `sys_dict` VALUES ('-10100001', '1', '1', 'STATUS_TYPE', 'STATUS_CODE', '状态类型', '状态类型', 'zhuangtaileixing', 'ztlx', '', '1', '', '1');
INSERT INTO `sys_dict` VALUES ('-10090006', '-10090001', '0', 'EDUCATION', '5', '研究生', '学历', 'yanjiusheng', 'yjs', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10090005', '-10090001', '0', 'EDUCATION', '4', '本科', '学历', 'benke', 'bk', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10090004', '-10090001', '0', 'EDUCATION', '3', '大专', '学历', 'dazhuan', 'dz', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10090003', '-10090001', '0', 'EDUCATION', '2', '中专', '学历', 'zhongzhuan', 'zz', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10090002', '-10090001', '0', 'EDUCATION', '1', '不限', '学历', 'buxian', 'bx', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10090001', '1', '1', 'EDUCATION', 'NATURE', '学历', '学历', 'xueli', 'xl', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10080003', '-10080001', '0', 'REQUIREMENT_TYPE', '2', '打标需求', '需求类型', 'dbxq', 'dbxq', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10080002', '-10080001', '0', 'REQUIREMENT_TYPE', '1', '正常需求', '需求类型', 'zcxq', 'zcxq', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10080001', '1', '1', 'REQUIREMENT_TYPE', 'NATURE', '需求类型', '需求类型', 'xqlx', 'xqlx', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10070002', '-10070001', '0', 'CHANNEL_STATUS', '1', '正常', 'st', 'zc', 'zc', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10070001', '1', '1', 'CHANNEL_STATUS', 'NATURE', 'st', 'st', 'st', 'st', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10060010', '-10060001', '0', 'POSITION_STATUS', '9', '修改待确认未通过', '职位状态', 'xiugaibeibohui', 'xgbvh', '', '41', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060009', '-10060001', '0', 'POSITION_STATUS', '8', '已完成', '职位状态', 'yiwancheng', 'ywc', '', '41', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060008', '-10060001', '0', 'POSITION_STATUS', '7', '修改待审批', '职位状态', 'xiugaidaishenpi', 'xgdsp', '', '31', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060007', '-10060001', '0', 'POSITION_STATUS', '6', '取消', '职位状态', 'quxiao', 'qx', '', '31', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060006', '-10060001', '0', 'POSITION_STATUS', '5', '已停用', '职位状态', 'yitingyong', 'yty', '', '31', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060005', '-10060001', '0', 'POSITION_STATUS', '4', '通过', '职位状态', 'tongguo', 'tg', '', '21', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060004', '-10060001', '0', 'POSITION_STATUS', '3', '未通过', '职位状态', 'weitongguo', 'wtg', '', '11', '', '1');
INSERT INTO `sys_dict` VALUES ('-10060003', '-10060001', '0', 'POSITION_STATUS', '2', '待审批', '职位状态', 'new', 'new', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10060002', '-10060001', '0', 'POSITION_STATUS', '1', '新建', '职位状态', 'xinjian', 'xj', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10060001', '1', '1', 'POSITION_STATUS', 'NATURE', '职位状态', '职位状态', 'zhiweizhuangtai', 'zwzt', null, '6', null, '1');
INSERT INTO `sys_dict` VALUES ('-10050003', '-10050001', '0', 'WHETHER_TYPE', 'FALSE', '否', '是否可用', 'fou', 'f', '', '3', '', '1');
INSERT INTO `sys_dict` VALUES ('-10050002', '-10050001', '0', 'WHETHER_TYPE', 'TRUE', '是', '是否可用', 'shi', 's', '', '2', '', '1');
INSERT INTO `sys_dict` VALUES ('-10050001', '1', '1', 'WHETHER_TYPE', 'WHETHER_CODE', '是否可用', '是否可用', 'shifoukeyong', 'sfky', '', '1', '', '1');
INSERT INTO `sys_dict` VALUES ('-10040005', '-10040001', '0', 'FUNC_TYPE', 'ACTION', '功能', '功能类型', 'gongneng', 'gn', null, '4', null, '1');
INSERT INTO `sys_dict` VALUES ('-10040004', '-10040001', '0', 'FUNC_TYPE', 'FEATURE', '模块', '功能类型', 'mokuai', 'mk', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10040003', '-10040001', '0', 'FUNC_TYPE', 'MENU', '菜单', '功能类型', 'caidan', 'cd', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10040002', '-10040001', '0', 'FUNC_TYPE', 'LINK', '链接', '功能类型', 'lianjie', 'lj', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10040001', '1', '1', 'FUNC_TYPE', 'NATURE', '功能类型', '功能类型', 'xingbie', 'xb', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10030004', '-10030001', '0', 'USER_ID_TYPE', '2', '军官证', '证件类型', 'junguanzheng', 'jgz', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10030003', '-10030001', '0', 'USER_ID_TYPE', '1', '护照', '证件类型', 'hz', 'hz', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10030002', '-10030001', '0', 'USER_ID_TYPE', '0', '身份证', '证件类型', 'shenfenzheng', 'sfz', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10030001', '1', '1', 'USER_ID_TYPE', 'NATURE', '证件类型', '证件类型', 'zhengjianleixing', 'zjlx', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10020004', '-10020001', '0', 'SEX', '2', '未知', '性别', 'weizhi', 'wz', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10020003', '-10020001', '0', 'SEX', '1', '女', '性别', 'nv', 'n', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10020002', '-10020001', '0', 'SEX', '0', '男', '性别', 'nan', 'n', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10020001', '1', '1', 'SEX', 'NATURE', '性别', '性别', 'xingbie', 'xb', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10010004', '-10010001', '0', 'USER_TYPE', 'ADMIN', '超级用户', '用户类型', 'chaojiyonghu', 'cjyh', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('-10010003', '-10010001', '0', 'USER_TYPE', 'NORMAL', '普通用户', '用户类型', 'putongyonghu', 'ptyh', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10010002', '-10010001', '0', 'USER_TYPE', 'ANONYMOUS', '匿名用户', '用户类型', 'nimingyonghu', 'nmyh', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10010001', '1', '1', 'USER_TYPE', 'NATURE', '用户类型', '用户类型', 'yonghuleixing', 'yhlx', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('1', null, '1', 'Dict_Manage', 'DICT', '字典管理', '字典管理', 'zidianguanli', 'zdgl', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('2', '1', '1', 'CUSTOMER_DEPARTMENT', 'NATURE', '内部部门', '内部部门', 'neibubumen', 'nbbm', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('3', '2', '1', 'CUSTOMER_DEPARTMENT', '1', '开发一部', '内部部门', 'kaifayibu', 'kfyb', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('4', '2', '1', 'CUSTOMER_DEPARTMENT', '2', '开发二部', '内部部门', 'kaifaerbu', 'kfeb', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('8', '1', '1', 'SC_Sex', '-1', '外部职位性别', '外部职位性别', 'waibuzhiweixingbie', 'wbzwxb', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('9', '8', '1', 'SC_Sex', '0', '不限', '外部职位性别', 'buxian', 'bx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('10', '8', '1', 'SC_Sex', '1', '男', '外部职位性别', 'nan', 'n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('11', '8', '1', 'SC_Sex', '2', '女', '外部职位性别', 'nv', 'n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('12', '1', '1', 'SC_Education', '-1', '外部职位学历', '外部职位学历', 'waibuzhiweixueli', 'wbzwxl', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('13', '12', '1', 'SC_Education', '0', '不限', '外部职位学历', 'buxian', 'bx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('14', '12', '1', 'SC_Education', '1', '初中', '外部职位学历', 'chuzhong', 'cz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('15', '12', '1', 'SC_Education', '2', '中技', '外部职位学历', 'zhongji', 'zj', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('16', '12', '1', 'SC_Education', '3', '高中', '外部职位学历', 'gaozhong', 'gz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('17', '12', '1', 'SC_Education', '4', '中专', '外部职位学历', 'zhongzhuan', 'zz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('18', '12', '1', 'SC_Education', '5', '大专', '外部职位学历', 'dazhuan', 'dz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('19', '12', '1', 'SC_Education', '6', '本科', '外部职位学历', 'benke', 'bk', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('20', '12', '1', 'SC_Education', '7', '硕士', '外部职位学历', 'shuoshi', 'ss', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('21', '12', '1', 'SC_Education', '8', 'MBA', '外部职位学历', 'MBA', 'MBA', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('22', '12', '1', 'SC_Education', '9', 'EMBA', '外部职位学历', 'EMBA', 'EMBA', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('23', '12', '1', 'SC_Education', '10', '博士', '外部职位学历', 'boshi', 'bs', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('24', '1', '1', 'SC_WorkYear', '-1', '外部工作年限', '外部工作年限', 'waibugongznianxian', 'wbgznx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('25', '24', '1', 'SC_WorkYear', '0', '不限', '外部工作年限', 'buxian', 'bx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('26', '24', '1', 'SC_WorkYear', '1', '1～2年', '外部工作年限', '1,2', '1,2', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('27', '24', '1', 'SC_WorkYear', '2', '3~4年', '外部工作年限', '3,4', '3,4', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('28', '24', '1', 'SC_WorkYear', '3', '5~7年', '外部工作年限', '5,7', '5,7', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('29', '24', '1', 'SC_WorkYear', '4', '8~9年', '外部工作年限', '8,9', '8,9', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('30', '1', '1', 'SC_UpdateTime', '-1', '更新时间', '更新时间', 'gengxinshijian', 'gxsj', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('31', '30', '1', 'SC_UpdateTime', '0', '不限', '跟新时间', 'buxian', 'bx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('32', '30', '1', 'SC_UpdateTime', '1', '一周内', '更新时间', 'yizhounei', 'yzn', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('33', '30', '1', 'SC_UpdateTime', '2', '二周内', '更新时间', 'erzhounei', 'ezn', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('34', '30', '1', 'SC_UpdateTime', '3', '一月内', '更新时间', 'yiyuenei', 'yyn', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('35', '30', '1', 'SC_UpdateTime', '4', '二月内', '更新时间', 'eryuenei', 'eyn', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('36', '30', '1', 'SC_UpdateTime', '5', '六月内', '更新时间', 'liuyuenei', 'lyn', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('37', '1', '1', 'SC_ExpectedSalary', '-1', '期望月薪', '期望月薪', 'qiwangyuexin', 'qwyx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('38', '37', '1', 'SC_ExpectedSalary', '0', '不限', '期望月薪', 'buxian', 'bx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('39', '37', '1', 'SC_ExpectedSalary', '1', '2000以下/月', '期望月薪', '2000', '2000', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('40', '37', '1', 'SC_ExpectedSalary', '2', '2001-4000/月', '期望月薪', '2001', '2001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('41', '37', '1', 'SC_ExpectedSalary', '3', '4001-6000/月', '期望月薪', '4001', '4001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('42', '37', '1', 'SC_ExpectedSalary', '4', '6001-8000/月', '期望月薪', '6001', '6001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('43', '37', '1', 'SC_ExpectedSalary', '5', '8001-10000/月', '期望月薪', '8001', '8001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('44', '37', '1', 'SC_ExpectedSalary', '6', '10001-15000/月', '期望月薪', '10001', '10001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('45', '37', '1', 'SC_ExpectedSalary', '7', '15001-25000/月', '期望月薪', '15001', '15001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('46', '37', '1', 'SC_ExpectedSalary', '8', '25001以上/月', '期望月薪', '25001', '25001', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('47', '1', '1', 'SC_City', '0', '热门城市', '热门城市', 'remenchengshi', 'rmcs', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('48', '47', '1', 'SC_HotCity', '1', '北京', '热门城市', 'remenchengshi', 'rmcs', '', '10', null, '1');
INSERT INTO `sys_dict` VALUES ('49', '47', '1', 'SC_HotCity', '3', '广州', '热门城市', 'remenchengshi', 'rmcs', '', '11', null, '1');
INSERT INTO `sys_dict` VALUES ('50', '47', '1', 'SC_HotCity', '2', '上海', '热门城市', 'remenchengshi', 'rmcs', '', '12', null, '1');
INSERT INTO `sys_dict` VALUES ('51', '47', '1', 'SC_HotCity', '4', '深圳', '热门城市', 'remenchengshi', 'rmcs', '', '13', null, '1');
INSERT INTO `sys_dict` VALUES ('52', '47', '1', 'SC_HotCity', '5', '天津', '热门城市', 'remenchengshi', 'rmcs', '', '14', null, '1');
INSERT INTO `sys_dict` VALUES ('53', '47', '1', 'SC_HotCity', '6', '武汉', '热门城市', 'remenchengshi', 'rmcs', '', '15', null, '1');
INSERT INTO `sys_dict` VALUES ('54', '47', '1', 'SC_HotCity', '7', '西安', '热门城市', 'remenchengshi', 'rmcs', '', '16', null, '1');
INSERT INTO `sys_dict` VALUES ('55', '47', '1', 'SC_HotCity', '8', '成都', '热门城市', 'remenchengshi', 'rmcs', '', '17', null, '1');
INSERT INTO `sys_dict` VALUES ('56', '47', '1', 'SC_HotCity', '9', '大连', '热门城市', 'remenchengshi', 'rmcs', '', '18', null, '1');
INSERT INTO `sys_dict` VALUES ('57', '47', '1', 'SC_HotCity', '10', '长春', '热门城市', 'remenchengshi', 'rmcs', '', '19', null, '1');
INSERT INTO `sys_dict` VALUES ('58', '47', '1', 'SC_HotCity', '11', '沈阳', '热门城市', 'remenchengshi', 'rmcs', '', '20', null, '1');
INSERT INTO `sys_dict` VALUES ('59', '47', '1', 'SC_HotCity', '12', '南京', '热门城市', 'remenchengshi', 'rmcs', '', '21', null, '1');
INSERT INTO `sys_dict` VALUES ('60', '47', '1', 'SC_HotCity', '13', '济南', '热门城市', 'remenchengshi', 'rmcs', '', '22', null, '1');
INSERT INTO `sys_dict` VALUES ('61', '47', '1', 'SC_HotCity', '14', '青岛', '热门城市', 'remenchengshi', 'rmcs', '', '23', null, '1');
INSERT INTO `sys_dict` VALUES ('62', '47', '1', 'SC_HotCity', '15', '杭州', '热门城市', 'remenchengshi', 'rmcs', '', '24', null, '1');
INSERT INTO `sys_dict` VALUES ('63', '47', '1', 'SC_HotCity', '16', '苏州', '热门城市', 'remenchengshi', 'rmcs', '', '25', null, '1');
INSERT INTO `sys_dict` VALUES ('64', '47', '1', 'SC_HotCity', '17', '无锡', '热门城市', 'remenchengshi', 'rmcs', '', '26', null, '1');
INSERT INTO `sys_dict` VALUES ('65', '47', '1', 'SC_HotCity', '18', '宁波', '热门城市', 'remenchengshi', 'rmcs', '', '27', null, '1');
INSERT INTO `sys_dict` VALUES ('66', '47', '1', 'SC_HotCity', '19', '重庆', '热门城市', 'remenchengshi', 'rmcs', '', '28', null, '1');
INSERT INTO `sys_dict` VALUES ('67', '47', '1', 'SC_HotCity', '20', '郑州', '热门城市', 'remenchengshi', 'rmcs', '', '29', null, '1');
INSERT INTO `sys_dict` VALUES ('68', '47', '1', 'SC_HotCity', '21', '长沙', '热门城市', 'remenchengshi', 'rmcs', '', '30', null, '1');
INSERT INTO `sys_dict` VALUES ('69', '47', '1', 'SC_HotCity', '22', '福州', '热门城市', 'remenchengshi', 'rmcs', '', '31', null, '1');
INSERT INTO `sys_dict` VALUES ('70', '47', '1', 'SC_HotCity', '23', '哈尔滨', '热门城市', 'remenchengshi', 'rmcs', '', '32', null, '1');
INSERT INTO `sys_dict` VALUES ('71', '47', '1', 'SC_HotCity', '24', '合肥', '热门城市', 'remenchengshi', 'rmcs', '', '33', null, '1');
INSERT INTO `sys_dict` VALUES ('72', '47', '1', 'SC_HotCity', '25', '昆明', '热门城市', 'remenchengshi', 'rmcs', '', '34', null, '1');
INSERT INTO `sys_dict` VALUES ('73', '47', '1', 'SC_HotCity', '26', '佛山', '热门城市', 'remenchengshi', 'rmcs', '', '35', null, '1');
INSERT INTO `sys_dict` VALUES ('74', '47', '1', 'SC_HotCity', '27', '南昌', '热门城市', 'remenchengshi', 'rmcs', '', '36', null, '1');
INSERT INTO `sys_dict` VALUES ('75', '1', '1', 'SC_City', '-1', '省份', '省份', 'shengfen', 'sf', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('76', '75', '1', 'SC_City', '101', '广东', '省份', 'shengfen', 'sf', '', '10', null, '1');
INSERT INTO `sys_dict` VALUES ('77', '75', '1', 'SC_City', '102', '湖北', '省份', 'shengfen', 'sf', '', '11', null, '1');
INSERT INTO `sys_dict` VALUES ('78', '75', '1', 'SC_City', '103', '陕西', '省份', 'shengfen', 'sf', '', '12', null, '1');
INSERT INTO `sys_dict` VALUES ('79', '75', '1', 'SC_City', '104', '四川', '省份', 'shengfen', 'sf', '', '13', null, '1');
INSERT INTO `sys_dict` VALUES ('80', '75', '1', 'SC_City', '105', '辽宁', '省份', 'shengfen', 'sf', '', '14', null, '1');
INSERT INTO `sys_dict` VALUES ('81', '75', '1', 'SC_City', '106', '吉林', '省份', 'shengfen', 'sf', '', '15', null, '1');
INSERT INTO `sys_dict` VALUES ('82', '75', '1', 'SC_City', '107', '江苏', '省份', 'shengfen', 'sf', '', '16', null, '1');
INSERT INTO `sys_dict` VALUES ('83', '75', '1', 'SC_City', '108', '山东', '省份', 'shengfen', 'sf', '', '17', null, '1');
INSERT INTO `sys_dict` VALUES ('84', '75', '1', 'SC_City', '109', '浙江', '省份', 'shengfen', 'sf', '', '18', null, '1');
INSERT INTO `sys_dict` VALUES ('85', '75', '1', 'SC_City', '110', '广西', '省份', 'shengfen', 'sf', '', '19', null, '1');
INSERT INTO `sys_dict` VALUES ('86', '75', '1', 'SC_City', '111', '安徽', '省份', 'shengfen', 'sf', '', '20', null, '1');
INSERT INTO `sys_dict` VALUES ('87', '75', '1', 'SC_City', '112', '河北', '省份', 'shengfen', 'sf', '', '21', null, '1');
INSERT INTO `sys_dict` VALUES ('88', '75', '1', 'SC_City', '113', '山西', '省份', 'shengfen', 'sf', '', '22', null, '1');
INSERT INTO `sys_dict` VALUES ('89', '75', '1', 'SC_City', '114', '内蒙古', '省份', 'shengfen', 'sf', '', '23', null, '1');
INSERT INTO `sys_dict` VALUES ('90', '75', '1', 'SC_City', '115', '黑龙江', '省份', 'shengfen', 'sf', '', '24', null, '1');
INSERT INTO `sys_dict` VALUES ('91', '75', '1', 'SC_City', '116', '福建', '省份', 'shengfen', 'sf', '', '25', null, '1');
INSERT INTO `sys_dict` VALUES ('92', '75', '1', 'SC_City', '117', '江西', '省份', 'shengfen', 'sf', '', '26', null, '1');
INSERT INTO `sys_dict` VALUES ('93', '75', '1', 'SC_City', '118', '河南', '省份', 'shengfen', 'sf', '', '27', null, '1');
INSERT INTO `sys_dict` VALUES ('94', '75', '1', 'SC_City', '119', '湖南', '省份', 'shengfen', 'sf', '', '28', null, '1');
INSERT INTO `sys_dict` VALUES ('95', '75', '1', 'SC_City', '120', '海南', '省份', 'shengfen', 'sf', '', '29', null, '1');
INSERT INTO `sys_dict` VALUES ('96', '75', '1', 'SC_City', '121', '贵州', '省份', 'shengfen', 'sf', '', '30', null, '1');
INSERT INTO `sys_dict` VALUES ('97', '75', '1', 'SC_City', '122', '云南', '省份', 'shengfen', 'sf', '', '31', null, '1');
INSERT INTO `sys_dict` VALUES ('98', '75', '1', 'SC_City', '123', '西藏', '省份', 'shengfen', 'sf', '', '32', null, '1');
INSERT INTO `sys_dict` VALUES ('99', '75', '1', 'SC_City', '124', '甘肃', '省份', 'shengfen', 'sf', '', '33', null, '1');
INSERT INTO `sys_dict` VALUES ('100', '75', '1', 'SC_City', '125', '青海', '省份', 'shengfen', 'sf', '', '34', null, '1');
INSERT INTO `sys_dict` VALUES ('101', '75', '1', 'SC_City', '126', '宁夏', '省份', 'shengfen', 'sf', '', '35', null, '1');
INSERT INTO `sys_dict` VALUES ('102', '75', '1', 'SC_City', '127', '新疆', '省份', 'shengfen', 'sf', '', '36', null, '1');
INSERT INTO `sys_dict` VALUES ('103', '1', '1', 'SC_JobTypeMain', '0', '职位分类', '职位分类', 'zhiweifenlei', 'zwfl', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('104', '103', '1', 'SC_JobTypeMain', '1', '软件/互联网开发/系统集成', '职位分类', 'ruanjian/hulianwangkaifa/xitongjicheng', 'rj/hlwkf/xtjc', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('105', '103', '1', 'SC_JobTypeMain', '2', 'IT运维/技术支持', '职位分类', 'ITyunwei/jishuzhichi', 'ITyw/jszc', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('106', '1', '1', 'SC_SubJobTypeMain', '0', '职位子分类', '职位子分类', 'zhiweizifenlei', 'zwzfl', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('107', '104', '1', 'SC_SubJobTypeMain', '23', '互联网软件工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('108', '104', '1', 'SC_SubJobTypeMain', '29', '用户体验设计', '职位子分类', 'yonghutiyansheji', 'yhtysj', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('109', '105', '1', 'SC_SubJobTypeMain', '3', 'IT技术支持/维护工程师', '职位子分类', 'ITjishuzhichi/weihugongchengshi', 'ITjszc/whgcs', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('110', '105', '1', 'SC_SubJobTypeMain', '4', '大堂经理', '职位子分类', 'datangjingli', 'dtjl', null, '10', null, '0');
INSERT INTO `sys_dict` VALUES ('111', '1', '1', 'SC_EmploymentType', '0', '职位类型', '职位类型', 'zhiweileixing', 'zwlx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('112', '111', '1', 'SC_EmploymentType', '1', '全职', '职位类型', 'quanzhi', 'qz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('113', '111', '1', 'SC_EmploymentType', '2', '兼职', '职位类型', 'jianzhi', 'jz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('114', '1', '1', 'POSITION_PRIORITY', '0', '优先级', '优先级', 'youxianxi', 'yxj', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('125', '114', '1', 'POSITION_PRIORITY', '1', '优先', '优先级', 'youxian', 'yx', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('126', '114', '1', 'POSITION_PRIORITY', '2', '正常', '优先级', 'zhengchang', 'zc', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('127', '1', '1', 'GIVEUPREASON_OFFER', '0', '放弃offer原因', '放弃offer原因', 'fangqiofferyuanyin', 'fqofferyy', null, '11', null, '1');
INSERT INTO `sys_dict` VALUES ('128', '127', '1', 'GIVEUPREASON_OFFER', '1', '薪资', '放弃offer原因', 'xinzi', 'xz', null, '21', null, '1');
INSERT INTO `sys_dict` VALUES ('129', '127', '1', 'GIVEUPREASON_OFFER', '2', '其他', '放弃offer原因', 'qita', 'qt', null, '31', null, '1');
INSERT INTO `sys_dict` VALUES ('130', '1', '1', 'GIVEUPREASON_JOIN', '0', '放弃入职原因', '放弃入职原因', 'fangqiruzhiyuanyin', 'fqrzyy', null, '11', null, '1');
INSERT INTO `sys_dict` VALUES ('131', '130', '1', 'GIVEUPREASON_JOIN', '1', '薪资', '放弃入职原因', 'xinzi', 'xz', null, '21', null, '1');
INSERT INTO `sys_dict` VALUES ('132', '130', '1', 'GIVEUPREASON_JOIN', '2', '其他', '放弃入职原因', 'qita', 'qt', null, '21', null, '1');
INSERT INTO `sys_dict` VALUES ('133', '1', '1', 'RESUME_TYPE', '0', '简历类型', '简历类型', 'jianlileixing', 'jllx', null, '11', null, '1');
INSERT INTO `sys_dict` VALUES ('134', '133', '1', 'RESUME_TYPE', '1', '主动投递', '简历类型', 'zhudongtoudi', 'zdtd', null, '21', null, '1');
INSERT INTO `sys_dict` VALUES ('135', '133', '1', 'RESUME_TYPE', '2', '爬虫爬取', '简历类型', 'pachongpaqu', 'pcpq', null, '31', null, '1');
INSERT INTO `sys_dict` VALUES ('136', '133', '1', 'RESUME_TYPE', '3', '手工录入', '简历类型', 'shougongluru', 'sglr', null, '41', null, '1');
INSERT INTO `sys_dict` VALUES ('137', '133', '1', 'RESUME_TYPE', '4', '主动录入-手工修改', '简历类型', 'zhudongluru-shougongxiugai', 'zdlr-sgxg', null, '51', null, '1');
INSERT INTO `sys_dict` VALUES ('138', '133', '1', 'RESUME_TYPE', '5', '爬虫爬取-手工修改', '简历类型', 'pachongpaqu-shougongxiugai', 'pcpq-sgxg', null, '61', null, '1');
INSERT INTO `sys_dict` VALUES ('139', '1', '1', 'ExpectedSalary', '-1', '查询简历月薪', '查询简历月薪', 'qiwangyuexin', 'qwyx', '', '0', '', '1');
INSERT INTO `sys_dict` VALUES ('140', '139', '1', 'ExpectedSalary', '0', '不限', '查询简历月薪', 'buxian', 'bx', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('141', '139', '1', 'ExpectedSalary', '1', '2000以下/月', '查询简历月薪', '2000', '2000', '', '11', '', '1');
INSERT INTO `sys_dict` VALUES ('142', '139', '1', 'ExpectedSalary', '2', '2001-4000/月', '查询简历月薪', '2001', '2001', '', '12', '', '1');
INSERT INTO `sys_dict` VALUES ('143', '139', '1', 'ExpectedSalary', '3', '4001-6000/月', '查询简历月薪', '4001', '4001', '', '13', '', '1');
INSERT INTO `sys_dict` VALUES ('144', '139', '1', 'ExpectedSalary', '4', '6001-8000/月', '查询简历月薪', '6001', '6001', '', '14', '', '1');
INSERT INTO `sys_dict` VALUES ('145', '139', '1', 'ExpectedSalary', '5', '8001-10000/月', '查询简历月薪', '8001', '8001', '', '15', '', '1');
INSERT INTO `sys_dict` VALUES ('146', '139', '1', 'ExpectedSalary', '6', '10001-15000/月', '查询简历月薪', '10001', '10001', '', '16', '', '1');
INSERT INTO `sys_dict` VALUES ('147', '139', '1', 'ExpectedSalary', '7', '15001-25000/月', '查询简历月薪', '15001', '15001', '', '17', '', '1');
INSERT INTO `sys_dict` VALUES ('148', '139', '1', 'ExpectedSalary', '8', '25001以上/月', '查询简历月薪', '25001', '25001', '', '18', '', '1');
INSERT INTO `sys_dict` VALUES ('149', '139', '1', 'ExpectedSalary', '9', '面议', '查询简历月薪', 'mianyi', 'my', '', '19', '', '1');
INSERT INTO `sys_dict` VALUES ('150', '139', '1', 'ExpectedSalary', '10', '其他', '查询简历月薪', 'qita', 'qt', '', '20', '', '1');
INSERT INTO `sys_dict` VALUES ('151', '103', '1', 'SC_JobTypeMain', '3', '客服/售前/售后技术支持', '职位分类', 'kefu/shouqian/shouhoujishuzhichi', 'kf/sq/shjszc', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('152', '151', '1', 'SC_SubJobTypeMain', '4', '售前/售后技术支持工程师', '职位分类', 'shouqian/shouhoujishuzhichigongchengshi', 'sq/shjszcgcs', null, '20', null, '1');
INSERT INTO `sys_dict` VALUES ('153', '1', '1', 'PS_EmploymentType', '-1', '外部职位职位类型', '外部职位职位类型', 'waibuzhiweizhiweileixing', 'wbzwzwlx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('154', '153', '1', 'PS_EmploymentType', '1', '兼职', '外部职位职位类型', 'jianzhi', 'jz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('155', '153', '1', 'PS_EmploymentType', '2', '全职', '外部职位职位类型', 'quanzhi', 'qz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('156', '1', '1', 'PS_EducationLevel', '-1', '外部职位学历', '外部职位学历', 'waibuzhiweixueli', 'wbzwxl', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('157', '156', '1', 'PS_EducationLevel', '1', '初中及以下', '外部职位学历', 'chuzhongjiyixia', 'czjyx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('158', '156', '1', 'PS_EducationLevel', '2', '高中', '外部职位学历', 'gaozhong', 'gz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('159', '156', '1', 'PS_EducationLevel', '3', '中技', '外部职位学历', 'zhongji', 'zj', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('160', '156', '1', 'PS_EducationLevel', '4', '中专', '外部职位学历', 'zhongzhuan', 'zz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('161', '156', '1', 'PS_EducationLevel', '5', '大专', '外部职位学历', 'dazhuan', 'dz', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('162', '156', '1', 'PS_EducationLevel', '6', '本科', '外部职位学历', 'benke', 'bk', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('163', '156', '1', 'PS_EducationLevel', '7', '硕士', '外部职位学历', 'shuoshi', 'ss', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('164', '156', '1', 'PS_EducationLevel', '8', '博士', '外部职位学历', 'boshi', 'bs', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('165', '1', '1', 'PS_WorkYears', '-1', '外部职位工作年限', '外部职位工作年限', 'waibuzhiweigongzuonianxian', 'wbzwgznx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('166', '165', '1', 'PS_WorkYears', '1', '在读学生', '外部职位工作年限', 'zaiduxuesheng', 'zdxs', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('167', '165', '1', 'PS_WorkYears', '2', '应届毕业生', '外部职位工作年限', 'yingjiebiyesheng', 'yjbys', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('168', '165', '1', 'PS_WorkYears', '3', '1年', '外部职位工作年限', '1nian', '1n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('169', '165', '1', 'PS_WorkYears', '4', '2年', '外部职位工作年限', '2nian', '2n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('170', '165', '1', 'PS_WorkYears', '5', '3-4年', '外部职位工作年限', '3-4nian', '3-4n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('171', '165', '1', 'PS_WorkYears', '6', '5-7年', '外部职位工作年限', '5-7nian', '5-7n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('172', '165', '1', 'PS_WorkYears', '7', '8-9年', '外部职位工作年限', '8-9nian', '8-9n', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('173', '165', '1', 'PS_WorkYears', '8', '10年以上', '外部职位工作年限', '10nianyishang', '10nys', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('174', '1', '1', 'PS_MonthlyPay', '-1', '外部职位月薪', '外部职位月薪', 'waibuzhiweiyuexin', 'wbzwyx', null, '0', null, '1');
INSERT INTO `sys_dict` VALUES ('175', '174', '1', 'PS_MonthlyPay', '1', '1500以下', '外部职位月薪', '1500yixia', '1500yx', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('176', '174', '1', 'PS_MonthlyPay', '2', '1500-1999', '外部职位月薪', '1500-1999', '1500-1999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('177', '174', '1', 'PS_MonthlyPay', '3', '2000-2999', '外部职位月薪', '2000-2999', '2000-2999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('178', '174', '1', 'PS_MonthlyPay', '4', '3000-4499', '外部职位月薪', '3000-4499', '3000-4499', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('179', '174', '1', 'PS_MonthlyPay', '5', '4500-5999', '外部职位月薪', '4500-5999', '4500-5999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('180', '174', '1', 'PS_MonthlyPay', '6', '6000-7999', '外部职位月薪', '6000-7999', '6000-7999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('181', '174', '1', 'PS_MonthlyPay', '7', '8000-9999', '外部职位月薪', '8000-9999', '8000-9999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('182', '174', '1', 'PS_MonthlyPay', '8', '10000-14999', '外部职位月薪', '10000-14999', '10000-14999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('183', '174', '1', 'PS_MonthlyPay', '9', '15000-19999', '外部职位月薪', '15000-19999', '15000-19999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('184', '174', '1', 'PS_MonthlyPay', '10', '20000-24999', '外部职位月薪', '20000-24999', '20000-24999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('185', '174', '1', 'PS_MonthlyPay', '11', '25000-29999', '外部职位月薪', '25000-29999', '25000-29999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('186', '174', '1', 'PS_MonthlyPay', '12', '30000-39999', '外部职位月薪', '30000-39999', '30000-39999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('187', '174', '1', 'PS_MonthlyPay', '13', '40000-49999', '外部职位月薪', '40000-49999', '40000-49999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('188', '174', '1', 'PS_MonthlyPay', '14', '50000-69999', '外部职位月薪', '50000-69999', '50000-69999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('189', '174', '1', 'PS_MonthlyPay', '15', '70000-99999', '外部职位月薪', '70000-99999', '70000-99999', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('190', '174', '1', 'PS_MonthlyPay', '16', '100000及以上', '外部职位月薪', '100000jiyishang', '100000jys', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('196', '-10080001', '0', 'REQUIREMENT_TYPE', '3', '新客户需求', '需求类型', 'xinkehuxuqiu', 'xkhxq', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('197', '-10080001', '0', 'REQUIREMENT_TYPE', '4', '独家需求', '需求类型', 'dujiaxuqiu', 'djxq', null, '100', null, '1');
INSERT INTO `sys_dict` VALUES ('198', '1', '1', 'profit', '-1', '利润率', '利润率', 'lirunlv', 'lrl', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('199', '198', '1', 'profit', '20', '大于15%', '利润率', 'dayu15%', 'dy15%', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('200', '198', '1', 'profit', '15', '大于10%', '利润率', 'dayu10%', 'dy10%', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('201', '198', '1', 'profit', '10', '大于8%', '利润率', 'dayu8%', 'dy8%', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('202', '198', '1', 'profit', '1', '小于8%', '利润率', 'xiaoyu8%', 'xy8%', null, '9', null, '1');
INSERT INTO `sys_dict` VALUES ('203', '1', '1', 'Difficulty', '-1', '难易度', '难易度', 'nanyidu', 'nyd', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('204', '203', '1', 'Difficulty', '20', '薪水高于市场价', '难易度', 'xinshuigaoyushichangjia', 'xsgyscj', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('205', '203', '1', 'Difficulty', '10', '薪水与市场价持平', '难易度', 'xinshuiyushichangjiachiping', 'xsyscjcp', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('206', '203', '1', 'Difficulty', '1', '薪水低于市场价', '难易度', 'xinshuidiyushichangjia', 'xsdyscj', null, '4', null, '1');
INSERT INTO `sys_dict` VALUES ('207', '1', '1', 'demand', '-1', '需求明确性', '需求明确性', 'xuqiumingquexing', 'xqmqx', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('208', '207', '1', 'demand', '20', '需求各类信息齐全明确', '需求明确性', 'xuqiugeleixinxiqiquanmingque', 'xqglxxqqmq', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('209', '207', '1', 'demand', '10', '需求信息部分明确', '需求明确性', 'xuqiuxinxibufenmingque', 'xqxxbfmq', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('210', '207', '1', 'demand', '1', '需求信息多数不明确', '需求明确性', 'xuqiuxinxiduoshubumingque', 'xqxxdsbmq', null, '4', null, '1');
INSERT INTO `sys_dict` VALUES ('211', '1', '1', 'feedback', '-1', '反馈周期', '反馈周期', 'fankuizhouqi', 'fkzq', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('212', '211', '1', 'feedback', '20', '面试反馈周期小于一周', '反馈周期', 'mianshifankuizhouqixiaoyuyizhou', 'msfkzqxyyz', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('213', '211', '1', 'feedback', '10', '面试反馈周期小于两周', '反馈周期', 'mianshifankuizhouqixiaoyuliangzhou', 'msfkzqxylz', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('214', '211', '1', 'feedback', '1', '面试反馈周期大于两周', '反馈周期', 'mianshifankuizhouqidayuliangzhou', 'msfkzqdylz', null, '4', null, '1');
INSERT INTO `sys_dict` VALUES ('215', '1', '1', 'strategic', '-1', '战略意义', '战略意义', 'zhanlveyiyi', 'zlyy', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('216', '215', '1', 'strategic', '20', '重要客户长期职位', '战略意义', 'zhongyaokehuzhangqizhiwei', 'zykhzqzw', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('217', '215', '1', 'strategic', '10', '重短/非重长', '战略意义', 'zhongduan/feizhongzhang', 'zd/fzz', null, '4', null, '1');
INSERT INTO `sys_dict` VALUES ('218', '215', '1', 'strategic', '15', '打标职位', '战略意义', 'dabiaozhiwei', 'dbzw', null, '3', null, '1');
INSERT INTO `sys_dict` VALUES ('219', '215', '1', 'strategic', '1', '非重要客户短期职位', '战略意义', 'feizhongyaokehuduanqizhiwei', 'fzykhdqzw', null, '9', null, '1');
INSERT INTO `sys_dict` VALUES ('220', '103', '1', 'SC_JobTypeMain', '4', '硬件开发', '职位分类', 'kefujizhichi', 'kfjzc', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('221', '103', '1', 'SC_JobTypeMain', '10', '财务/审计/税务', '职位分类', 'IT-pinguanjishuzhichi', 'IT-pgjszc', null, '10', null, '1');
INSERT INTO `sys_dict` VALUES ('222', '104', '1', 'SC_SubJobTypeMain', '32', '游戏策划', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('223', '104', '1', 'SC_SubJobTypeMain', '28', '用户界面设计', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('224', '104', '1', 'SC_SubJobTypeMain', '34', '系统集成工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('225', '104', '1', 'SC_SubJobTypeMain', '31', '游戏设计/开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('226', '104', '1', 'SC_SubJobTypeMain', '38', '网站架构设计师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('227', '104', '1', 'SC_SubJobTypeMain', '17', '软件研发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('228', '104', '1', 'SC_SubJobTypeMain', '45', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('229', '104', '1', 'SC_SubJobTypeMain', '24', '手机软件开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('230', '104', '1', 'SC_SubJobTypeMain', '22', 'ERP技术/开发应用', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('231', '104', '1', 'SC_SubJobTypeMain', '30', '网页设计/制作/美工', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('232', '104', '1', 'SC_SubJobTypeMain', '26', '移动互联网开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('233', '104', '1', 'SC_SubJobTypeMain', '21', '数据库开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('234', '104', '1', 'SC_SubJobTypeMain', '44', '脚本开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('235', '104', '1', 'SC_SubJobTypeMain', '33', '游戏界面设计', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('236', '104', '1', 'SC_SubJobTypeMain', '19', '系统架构设计师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('237', '104', '1', 'SC_SubJobTypeMain', '2', 'WEB前端开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('238', '104', '1', 'SC_SubJobTypeMain', '20', '系统分析员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('239', '104', '1', 'SC_SubJobTypeMain', '1', '软件工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('240', '104', '1', 'SC_SubJobTypeMain', '35', '算法工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('241', '104', '1', 'SC_SubJobTypeMain', '27', '语音/视频/图形开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('242', '104', '1', 'SC_SubJobTypeMain', '39', 'IOS开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('243', '104', '1', 'SC_SubJobTypeMain', '42', 'PHP开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('244', '104', '1', 'SC_SubJobTypeMain', '37', '计算机辅助设计师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('245', '104', '1', 'SC_SubJobTypeMain', '18', '需求工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('246', '104', '1', 'SC_SubJobTypeMain', '43', 'C语言开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('247', '104', '1', 'SC_SubJobTypeMain', '41', 'Java开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('248', '104', '1', 'SC_SubJobTypeMain', '36', '仿真应用工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('249', '104', '1', 'SC_SubJobTypeMain', '25', '嵌入式软件开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('250', '104', '1', 'SC_SubJobTypeMain', '16', '高级软件工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('251', '104', '1', 'SC_SubJobTypeMain', '40', 'Android开发工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('252', '105', '1', 'SC_SubJobTypeMain', '59', 'Helpdesk', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('253', '105', '1', 'SC_SubJobTypeMain', '47', '信息技术专员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('254', '105', '1', 'SC_SubJobTypeMain', '49', '系统工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('255', '105', '1', 'SC_SubJobTypeMain', '53', '网络与信息安全工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('256', '105', '1', 'SC_SubJobTypeMain', '52', '网络管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('257', '105', '1', 'SC_SubJobTypeMain', '50', '系统管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('258', '105', '1', 'SC_SubJobTypeMain', '54', '数据库管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('259', '105', '1', 'SC_SubJobTypeMain', '55', '计算机硬件维护工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('260', '105', '1', 'SC_SubJobTypeMain', '60', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('261', '105', '1', 'SC_SubJobTypeMain', '56', 'ERP实施顾问', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('262', '105', '1', 'SC_SubJobTypeMain', '48', 'IT技术支持/维护经理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('263', '105', '1', 'SC_SubJobTypeMain', '3', 'IT技术支持/维护工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('264', '105', '1', 'SC_SubJobTypeMain', '57', 'IT技术文员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('265', '105', '1', 'SC_SubJobTypeMain', '51', '网络工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('266', '105', '1', 'SC_SubJobTypeMain', '58', 'IT文档工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('267', '105', '1', 'SC_SubJobTypeMain', '46', '信息技术经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('268', '151', '1', 'SC_SubJobTypeMain', '6', '客户服务经理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('269', '151', '1', 'SC_SubJobTypeMain', '8', '客户服务专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('270', '151', '1', 'SC_SubJobTypeMain', '12', '售前/售后技术支持管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('271', '151', '1', 'SC_SubJobTypeMain', '7', '客户服务主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('272', '151', '1', 'SC_SubJobTypeMain', '10', '客户咨询热线/呼叫中心人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('273', '151', '1', 'SC_SubJobTypeMain', '4', '售前/售后技术支持工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('274', '151', '1', 'SC_SubJobTypeMain', '15', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('275', '151', '1', 'SC_SubJobTypeMain', '9', '客户关系/投诉协调人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('276', '151', '1', 'SC_SubJobTypeMain', '5', '客户服务总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('277', '151', '1', 'SC_SubJobTypeMain', '11', '网络/在线客服', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('278', '151', '1', 'SC_SubJobTypeMain', '13', 'VIP专员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('279', '151', '1', 'SC_SubJobTypeMain', '14', '呼叫中心客服', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('280', '220', '1', 'SC_SubJobTypeMain', '62', '硬件工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('281', '220', '1', 'SC_SubJobTypeMain', '61', '高级硬件工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('282', '220', '1', 'SC_SubJobTypeMain', '63', '嵌入式硬件开发', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('283', '220', '1', 'SC_SubJobTypeMain', '64', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('284', '103', '1', 'SC_JobTypeMain', '7', '项目管理/项目协调', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('285', '103', '1', 'SC_JobTypeMain', '8', '行政/后勤/文秘', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('286', '103', '1', 'SC_JobTypeMain', '5', '其他', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('289', '103', '1', 'SC_JobTypeMain', '6', '实习生/培训生/储备干部', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('290', '103', '1', 'SC_JobTypeMain', '11', 'IT管理/项目协调', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('292', '103', '1', 'SC_JobTypeMain', '9', '人力资源', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('293', '103', '1', 'SC_JobTypeMain', '13', '互联网产品/运营管理', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('294', '103', '1', 'SC_JobTypeMain', '12', 'IT质量管理/测试/配置管理', '职位分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('295', '286', '1', 'SC_SubJobTypeMain', '65', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('296', '289', '1', 'SC_SubJobTypeMain', '66', '实习生', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('297', '289', '1', 'SC_SubJobTypeMain', '67', '培训生', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('298', '289', '1', 'SC_SubJobTypeMain', '69', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('299', '289', '1', 'SC_SubJobTypeMain', '68', '储备干部', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('300', '284', '1', 'SC_SubJobTypeMain', '184', '医药项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('301', '284', '1', 'SC_SubJobTypeMain', '176', '房地产项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('302', '284', '1', 'SC_SubJobTypeMain', '167', '项目总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('303', '284', '1', 'SC_SubJobTypeMain', '185', '化工项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('304', '284', '1', 'SC_SubJobTypeMain', '177', '证券/投资项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('305', '284', '1', 'SC_SubJobTypeMain', '189', '项目计划合约专员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('306', '284', '1', 'SC_SubJobTypeMain', '174', '通信项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('307', '284', '1', 'SC_SubJobTypeMain', '175', '房地产项目配套工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('308', '284', '1', 'SC_SubJobTypeMain', '187', '咨询项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('309', '284', '1', 'SC_SubJobTypeMain', '186', '物流/仓储项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('310', '284', '1', 'SC_SubJobTypeMain', '191', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('311', '284', '1', 'SC_SubJobTypeMain', '188', '能源/矿产项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('312', '284', '1', 'SC_SubJobTypeMain', '169', '项目专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('313', '284', '1', 'SC_SubJobTypeMain', '183', '服装/纺织/皮革项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('314', '284', '1', 'SC_SubJobTypeMain', '173', 'IT项目执行/协调人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('315', '284', '1', 'SC_SubJobTypeMain', '178', '保险项目经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('316', '284', '1', 'SC_SubJobTypeMain', '168', '项目经理/项目主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('317', '284', '1', 'SC_SubJobTypeMain', '190', '项目招投标', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('318', '284', '1', 'SC_SubJobTypeMain', '171', 'IT项目总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('319', '284', '1', 'SC_SubJobTypeMain', '179', '生产项目经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('320', '284', '1', 'SC_SubJobTypeMain', '172', 'IT项目经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('321', '284', '1', 'SC_SubJobTypeMain', '182', '电子/电器项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('322', '284', '1', 'SC_SubJobTypeMain', '170', '广告/会展项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('323', '284', '1', 'SC_SubJobTypeMain', '180', '生产项目工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('324', '284', '1', 'SC_SubJobTypeMain', '181', '汽车工程项目管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('325', '285', '1', 'SC_SubJobTypeMain', '162', '后勤人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('326', '285', '1', 'SC_SubJobTypeMain', '163', '党工团干事', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('327', '285', '1', 'SC_SubJobTypeMain', '156', '行政经理/主管/办公室主任', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('328', '285', '1', 'SC_SubJobTypeMain', '160', '文档/资料管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('329', '285', '1', 'SC_SubJobTypeMain', '155', '行政总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('330', '285', '1', 'SC_SubJobTypeMain', '164', '图书管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('331', '285', '1', 'SC_SubJobTypeMain', '161', '电脑操作/打字/录入员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('332', '285', '1', 'SC_SubJobTypeMain', '165', '内勤人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('333', '285', '1', 'SC_SubJobTypeMain', '157', '行政专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('334', '285', '1', 'SC_SubJobTypeMain', '158', '助理/秘书/文员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('335', '285', '1', 'SC_SubJobTypeMain', '166', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('336', '285', '1', 'SC_SubJobTypeMain', '159', '前台/总机/接待', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('337', '290', '1', 'SC_SubJobTypeMain', '110', 'IT项目总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('338', '290', '1', 'SC_SubJobTypeMain', '107', 'CTO/CIO', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('339', '290', '1', 'SC_SubJobTypeMain', '109', 'IT技术/研发经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('340', '290', '1', 'SC_SubJobTypeMain', '113', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('341', '290', '1', 'SC_SubJobTypeMain', '111', 'IT项目经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('342', '290', '1', 'SC_SubJobTypeMain', '112', 'IT项目执行/协调人员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('343', '290', '1', 'SC_SubJobTypeMain', '108', 'IT技术/研发总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('344', '292', '1', 'SC_SubJobTypeMain', '138', '人力资源总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('345', '292', '1', 'SC_SubJobTypeMain', '145', '招聘专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('346', '292', '1', 'SC_SubJobTypeMain', '147', '薪酬福利专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('347', '292', '1', 'SC_SubJobTypeMain', '154', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('348', '292', '1', 'SC_SubJobTypeMain', '143', '培训专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('349', '292', '1', 'SC_SubJobTypeMain', '140', '人力资源主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('350', '292', '1', 'SC_SubJobTypeMain', '139', '人力资源经理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('351', '292', '1', 'SC_SubJobTypeMain', '148', '绩效考核经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('352', '292', '1', 'SC_SubJobTypeMain', '153', '猎头顾问/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('353', '292', '1', 'SC_SubJobTypeMain', '149', '绩效考核专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('354', '292', '1', 'SC_SubJobTypeMain', '146', '薪酬福利经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('355', '292', '1', 'SC_SubJobTypeMain', '144', '招聘经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('356', '292', '1', 'SC_SubJobTypeMain', '151', '企业培训师/讲师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('357', '292', '1', 'SC_SubJobTypeMain', '142', '培训经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('358', '292', '1', 'SC_SubJobTypeMain', '152', '人事信息系统(HRIS)管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('359', '292', '1', 'SC_SubJobTypeMain', '141', '人力资源专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('360', '292', '1', 'SC_SubJobTypeMain', '150', '员工关系/企业文化/工会', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('361', '293', '1', 'SC_SubJobTypeMain', '86', '网店运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('362', '293', '1', 'SC_SubJobTypeMain', '70', '互联网产品经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('363', '293', '1', 'SC_SubJobTypeMain', '71', '互联网产品专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('364', '293', '1', 'SC_SubJobTypeMain', '81', '电子商务总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('365', '293', '1', 'SC_SubJobTypeMain', '85', '网店客服', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('366', '293', '1', 'SC_SubJobTypeMain', '80', '网站运营总监/经理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('367', '293', '1', 'SC_SubJobTypeMain', '83', '网店店长', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('368', '293', '1', 'SC_SubJobTypeMain', '94', '内容运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('369', '293', '1', 'SC_SubJobTypeMain', '74', '网络运营管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('370', '293', '1', 'SC_SubJobTypeMain', '73', '电子商务专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('371', '293', '1', 'SC_SubJobTypeMain', '91', '产品运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('372', '293', '1', 'SC_SubJobTypeMain', '90', '淘宝/微信运营专员/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('373', '293', '1', 'SC_SubJobTypeMain', '72', '电子商务经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('374', '293', '1', 'SC_SubJobTypeMain', '76', '网站编辑', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('375', '293', '1', 'SC_SubJobTypeMain', '75', '网络运营专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('376', '293', '1', 'SC_SubJobTypeMain', '89', '微信推广', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('377', '293', '1', 'SC_SubJobTypeMain', '84', '网店推广', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('378', '293', '1', 'SC_SubJobTypeMain', '79', '运营总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('379', '293', '1', 'SC_SubJobTypeMain', '93', '市场运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('380', '293', '1', 'SC_SubJobTypeMain', '77', 'SEO/SEM', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('381', '293', '1', 'SC_SubJobTypeMain', '87', '网店管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('382', '293', '1', 'SC_SubJobTypeMain', '88', '运营主管/专员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('383', '293', '1', 'SC_SubJobTypeMain', '95', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('384', '293', '1', 'SC_SubJobTypeMain', '92', '数据运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('385', '293', '1', 'SC_SubJobTypeMain', '82', '新媒体运营', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('386', '293', '1', 'SC_SubJobTypeMain', '78', '产品总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('387', '294', '1', 'SC_SubJobTypeMain', '98', '系统测试', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('388', '294', '1', 'SC_SubJobTypeMain', '100', '硬件测试', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('389', '294', '1', 'SC_SubJobTypeMain', '101', '配置管理工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('390', '294', '1', 'SC_SubJobTypeMain', '102', '信息技术标准化工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('391', '294', '1', 'SC_SubJobTypeMain', '104', '游戏测试', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('392', '294', '1', 'SC_SubJobTypeMain', '106', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('393', '294', '1', 'SC_SubJobTypeMain', '96', 'IT质量管理经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('394', '294', '1', 'SC_SubJobTypeMain', '99', '软件测试', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('395', '294', '1', 'SC_SubJobTypeMain', '105', '手机维修', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('396', '294', '1', 'SC_SubJobTypeMain', '97', 'IT质量管理工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('397', '294', '1', 'SC_SubJobTypeMain', '103', '标准化工程师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('398', '221', '1', 'SC_SubJobTypeMain', '128', '税务经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('399', '221', '1', 'SC_SubJobTypeMain', '125', '出纳员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('400', '221', '1', 'SC_SubJobTypeMain', '132', '资产/资金管理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('401', '221', '1', 'SC_SubJobTypeMain', '114', '首席财务官CFO', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('402', '221', '1', 'SC_SubJobTypeMain', '130', '成本经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('403', '221', '1', 'SC_SubJobTypeMain', '121', '财务分析员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('404', '221', '1', 'SC_SubJobTypeMain', '118', '财务顾问', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('405', '221', '1', 'SC_SubJobTypeMain', '119', '财务助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('406', '221', '1', 'SC_SubJobTypeMain', '129', '税务专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('407', '221', '1', 'SC_SubJobTypeMain', '115', '财务总监', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('408', '221', '1', 'SC_SubJobTypeMain', '120', '财务分析经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('409', '221', '1', 'SC_SubJobTypeMain', '117', '财务主管/总帐主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('410', '221', '1', 'SC_SubJobTypeMain', '123', '会计/会计师', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('411', '221', '1', 'SC_SubJobTypeMain', '124', '会计助理/文员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('412', '221', '1', 'SC_SubJobTypeMain', '126', '审计经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('413', '221', '1', 'SC_SubJobTypeMain', '116', '财务经理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('414', '221', '1', 'SC_SubJobTypeMain', '127', '审计专员/助理', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('415', '221', '1', 'SC_SubJobTypeMain', '134', '统计员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('416', '221', '1', 'SC_SubJobTypeMain', '133', '资金专员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('417', '221', '1', 'SC_SubJobTypeMain', '131', '成本会计', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('418', '221', '1', 'SC_SubJobTypeMain', '137', '其他', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '100', '', '1');
INSERT INTO `sys_dict` VALUES ('419', '221', '1', 'SC_SubJobTypeMain', '135', '固定资产会计', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('420', '221', '1', 'SC_SubJobTypeMain', '136', '成本管理员', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('421', '221', '1', 'SC_SubJobTypeMain', '122', '会计经理/主管', '职位子分类', 'hulianwangruanjiangongchengshi', 'hlwrjgcs', '', '10', '', '1');
INSERT INTO `sys_dict` VALUES ('422', '2', '1', 'CUSTOMER_DEPARTMENT', '3', '第二事业部', '内部部门', 'diershiyebu', 'desyb', null, '30', null, '1');
INSERT INTO `sys_dict` VALUES ('423', '2', '1', 'CUSTOMER_DEPARTMENT', '4', '第一事业部', '内部部门', 'diyishiyebu', 'dysyb', null, '40', null, '1');

-- ----------------------------
-- Table structure for sys_dict_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_aud`;
CREATE TABLE `sys_dict_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `DICT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DICT_PARENT_ID` int(11) DEFAULT '0' COMMENT '父级编号',
  `IS_NATURE` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为字典类型而非字典选项,1: yes, others: no',
  `DICT_NATURE` varchar(50) NOT NULL COMMENT '类型',
  `DICT_CODE` varchar(50) NOT NULL COMMENT '编码',
  `DICT_VALUE` varchar(50) NOT NULL COMMENT '数据值',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`DICT_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表变更记录表';

-- ----------------------------
-- Records of sys_dict_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func` (
  `FUNC_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `FUNC_PARENT_ID` int(11) DEFAULT '0' COMMENT '功能ID',
  `FUNC_CODE` varchar(30) NOT NULL COMMENT '功能编码',
  `FUNC_NAME` varchar(15) NOT NULL COMMENT '功能名称',
  `FUNC_ORDER` int(2) DEFAULT NULL COMMENT '排序',
  `FUNC_TYPE` varchar(50) NOT NULL COMMENT '功能类型；MENU,FUNC,LINK',
  `FUNC_URL` varchar(150) DEFAULT NULL COMMENT '超链接，如果功能类型为LINK',
  `FUNC_DESC` varchar(128) DEFAULT NULL COMMENT '功能描述',
  `FUNC_ICON` varchar(64) DEFAULT NULL COMMENT '图标样式',
  `IS_VISIBLE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在菜单中可见,1:是,others:否',
  `IS_BASE_FUNC` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为基础功能,1:是,others:否',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`FUNC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能表';

-- ----------------------------
-- Records of sys_func
-- ----------------------------
INSERT INTO `sys_func` VALUES ('-1040700', '-1040100', 'sys.func', '功能管理', '2', 'FEATURE', null, null, 'glyphicon glyphicon-th-large', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040600', '-1040100', 'sys.role', '角色管理', '6', 'FEATURE', null, null, 'glyphicon glyphicon-heart', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040500', '-1040100', 'sys.group', '用户组管理', '4', 'FEATURE', null, null, 'glyphicon glyphicon-gift', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040400', '-1040100', 'sys.dept', '机构管理', '1', 'FEATURE', null, null, 'glyphicon glyphicon-home', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040300', '-1040100', 'sys.user', '用户管理', '5', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040200', '-1040100', 'sys.dict', '字典管理', '3', 'FEATURE', null, null, 'glyphicon glyphicon-book', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1040100', '0', 'sys', '系统管理', '4', 'MENU', null, null, 'glyphicon glyphicon-cog', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030800', '-1040100', 'sys.global', '全局参数管理', '14', 'FEATURE', null, null, 'glyphicon glyphicon-magnet', '1', '0', '1');
INSERT INTO `sys_func` VALUES ('-1030700', '-1030100', 'demos.tree', 'Tree', '5', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030600', '-1030100', 'demos.wizard', 'Wizard', '5', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030500', '-1030100', 'demos.table', 'Table', '4', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030400', '-1030100', 'demos.typhoghray', 'Typhoghray', '3', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030300', '-1030100', 'demos.button', 'Button', '2', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030200', '-1030100', 'demos.element', 'Element', '1', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1030100', '0', 'demos', 'Demo', '5', 'MENU', null, null, 'fa fa-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020900', '-1020100', 'pages.blank', '500', '6', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020800', '-1020100', 'pages.500', '500', '5', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020700', '-1020100', 'pages.404', '404', '4', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020600', '-1020100', 'pages.403', '403', '3', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020500', '-1020100', 'pages.faq', 'FAQ', '2', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020400', '-1020200', 'pages.login.dev', '开发者登录', '2', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020300', '-1020200', 'pages.login.sys', '系统用户登录', '1', 'FEATURE', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020200', '-1020100', 'pages.login', '登录', '7', 'MENU', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1020100', '0', 'pages', '通用页面', '6', 'MENU', null, null, 'fa fa-user', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1010100', '0', 'home.home', '首页', '1', 'FEATURE', null, null, 'fa fa-user', '1', '1', '1');

-- ----------------------------
-- Table structure for sys_func_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_func_aud`;
CREATE TABLE `sys_func_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `FUNC_ID` int(11) NOT NULL COMMENT 'ID',
  `FUNC_PARENT_ID` int(11) DEFAULT '0' COMMENT '功能ID',
  `FUNC_CODE` varchar(30) NOT NULL COMMENT '功能编码',
  `FUNC_NAME` varchar(15) NOT NULL COMMENT '功能名称',
  `FUNC_ORDER` int(2) DEFAULT NULL COMMENT '排序',
  `FUNC_TYPE` varchar(50) NOT NULL COMMENT '功能类型；MENU,FUNC,LINK',
  `FUNC_URL` varchar(150) DEFAULT NULL COMMENT '超链接，如果功能类型为LINK',
  `IS_VISIBLE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在菜单中可见,1:是,others:否',
  `IS_BASE_FUNC` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为基础功能,1:是,others:否',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`FUNC_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能表变更记录表';

-- ----------------------------
-- Records of sys_func_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_global_variable
-- ----------------------------
DROP TABLE IF EXISTS `sys_global_variable`;
CREATE TABLE `sys_global_variable` (
  `VARIABLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '全局变量ID',
  `VARIABLE_NAME` varchar(50) NOT NULL COMMENT '全局变量名',
  `VARIABLE_VALUE` varchar(255) NOT NULL COMMENT '全局变量值',
  `VARIABLE_DESCRIBE` varchar(1000) NOT NULL COMMENT '全局变量描述',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`VARIABLE_ID`),
  UNIQUE KEY `VARIABLE_NAME` (`VARIABLE_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='全局变量存储查询表';

-- ----------------------------
-- Records of sys_global_variable
-- ----------------------------
INSERT INTO `sys_global_variable` VALUES ('1', '10123', '系统名称', '张忠博毕业设计CRM系统', '1');
INSERT INTO `sys_global_variable` VALUES ('2', '10124', '服务协议', '', '0');
INSERT INTO `sys_global_variable` VALUES ('3', '10125', '隐私条款', '', '0');
INSERT INTO `sys_global_variable` VALUES ('4', '10126', '验证码', '验证码是否有效', '0');
INSERT INTO `sys_global_variable` VALUES ('5', '10000', '系统颜色', 'light2', '1');
INSERT INTO `sys_global_variable` VALUES ('6', '10001', '导航条位置', 'left', '1');
INSERT INTO `sys_global_variable` VALUES ('7', '10002', '导航left模板文件夹', 'default', '1');
INSERT INTO `sys_global_variable` VALUES ('8', '10003', '导航top模板文件夹', 'horizontalMenu', '1');
INSERT INTO `sys_global_variable` VALUES ('9', '10004', '导航right模板文件夹', 'default', '1');
INSERT INTO `sys_global_variable` VALUES ('10', '10010', '按钮圆角or方角', 'components-md', '1');
INSERT INTO `sys_global_variable` VALUES ('11', '10011', '布局', 'fluid', '1');
INSERT INTO `sys_global_variable` VALUES ('12', '10012', '头部位置', 'fixed', '1');
INSERT INTO `sys_global_variable` VALUES ('13', '10013', '头像下拉颜色', 'dark', '1');
INSERT INTO `sys_global_variable` VALUES ('14', '10014', '侧边栏模式', 'fixed', '1');
INSERT INTO `sys_global_variable` VALUES ('15', '10015', '默认是否收起侧边栏', 'false', '1');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `GROUP_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `GROUP_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
  `GROUP_DESC` varchar(64) DEFAULT NULL COMMENT '描述',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户组表';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('-1', '超级管理员组', '超级管理员组', '1');
INSERT INTO `sys_group` VALUES ('1', '招聘主管组', '招聘主管组', '1');
INSERT INTO `sys_group` VALUES ('6', '客户经理组', '客户经理组', '1');
INSERT INTO `sys_group` VALUES ('7', '招聘专员组', '招聘专员组', '1');
INSERT INTO `sys_group` VALUES ('8', '基本角色组', '基本角色组', '1');

-- ----------------------------
-- Table structure for sys_group_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_aud`;
CREATE TABLE `sys_group_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `GROUP_ID` int(11) NOT NULL COMMENT 'ID',
  `GROUP_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`GROUP_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组表变更记录表';

-- ----------------------------
-- Records of sys_group_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`),
  KEY `SYS_GROUP_ROLE_GROUP_ID` (`GROUP_ID`),
  KEY `SYS_GROUP_ROLE_ROLE_ID` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组，角色关联表';

-- ----------------------------
-- Records of sys_group_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group_role_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role_aud`;
CREATE TABLE `sys_group_role_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `REL_ID` int(11) NOT NULL COMMENT '关联ID, 为辨识变更记录使用',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`REL_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组，角色关联表变更记录表';

-- ----------------------------
-- Records of sys_group_role_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ROLE_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
  `ROLE_DESC` varchar(64) DEFAULT NULL COMMENT '描述',
  `IS_BASE_ROLE` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为基础角色,1:是,others:否',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('-3', '开发者', null, '1', '0');
INSERT INTO `sys_role` VALUES ('-2', '系统配置管理员', '系统配置管理员', '1', '1');
INSERT INTO `sys_role` VALUES ('-1', '基本角色', '基本角色', '1', '1');
INSERT INTO `sys_role` VALUES ('10', '招聘专员', '招聘专员', '1', '1');
INSERT INTO `sys_role` VALUES ('11', '招聘主管', '招聘主管', '1', '1');
INSERT INTO `sys_role` VALUES ('45', '客户经理', '客户经理', '0', '1');
INSERT INTO `sys_role` VALUES ('46', '高级招聘', '高级招聘', '0', '1');
INSERT INTO `sys_role` VALUES ('47', '招聘助理', '招聘助理', '0', '1');

-- ----------------------------
-- Table structure for sys_role_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_aud`;
CREATE TABLE `sys_role_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `ROLE_ID` int(11) NOT NULL COMMENT 'ID',
  `ROLE_NAME` varchar(32) DEFAULT NULL COMMENT '名称',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  PRIMARY KEY (`ROLE_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表变更记录表';

-- ----------------------------
-- Records of sys_role_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_func`;
CREATE TABLE `sys_role_func` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `FUNC_ID` int(11) NOT NULL COMMENT '功能ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`),
  KEY `SYS_ROLE_FUNC_FUNC_ID` (`FUNC_ID`),
  KEY `SYS_ROLE_FUNC_ROLE_ID` (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=545 DEFAULT CHARSET=utf8 COMMENT='功能，角色关联表';

-- ----------------------------
-- Records of sys_role_func
-- ----------------------------
INSERT INTO `sys_role_func` VALUES ('-1031030700', '-1030700', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030600', '-1030600', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030500', '-1030500', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030400', '-1030400', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030300', '-1030300', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030200', '-1030200', '-3');
INSERT INTO `sys_role_func` VALUES ('-1031030100', '-1030100', '-3');
INSERT INTO `sys_role_func` VALUES ('53', '-1030100', '26');
INSERT INTO `sys_role_func` VALUES ('54', '-1020100', '26');
INSERT INTO `sys_role_func` VALUES ('55', '-1010100', '26');
INSERT INTO `sys_role_func` VALUES ('58', '-1020100', '28');
INSERT INTO `sys_role_func` VALUES ('59', '-1030100', '28');
INSERT INTO `sys_role_func` VALUES ('60', '-1040100', '28');
INSERT INTO `sys_role_func` VALUES ('267', '-1010100', '-1');
INSERT INTO `sys_role_func` VALUES ('351', '-1040100', '-2');
INSERT INTO `sys_role_func` VALUES ('352', '-1040700', '-2');
INSERT INTO `sys_role_func` VALUES ('353', '-1040600', '-2');
INSERT INTO `sys_role_func` VALUES ('354', '-1040500', '-2');
INSERT INTO `sys_role_func` VALUES ('355', '-1040400', '-2');
INSERT INTO `sys_role_func` VALUES ('356', '-1040300', '-2');
INSERT INTO `sys_role_func` VALUES ('357', '-1040200', '-2');
INSERT INTO `sys_role_func` VALUES ('359', '14', '-2');
INSERT INTO `sys_role_func` VALUES ('418', '-1040100', '45');
INSERT INTO `sys_role_func` VALUES ('459', '-1040100', '11');
INSERT INTO `sys_role_func` VALUES ('460', '-1040300', '11');
INSERT INTO `sys_role_func` VALUES ('476', '-1040100', '10');
INSERT INTO `sys_role_func` VALUES ('537', '-1010100', '46');
INSERT INTO `sys_role_func` VALUES ('544', '-1040100', '47');

-- ----------------------------
-- Table structure for sys_role_func_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_func_aud`;
CREATE TABLE `sys_role_func_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `REL_ID` int(11) NOT NULL COMMENT '关联ID, 为辨识变更记录使用',
  `FUNC_ID` int(11) NOT NULL COMMENT '功能ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`REL_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能，角色关联表变更记录表';

-- ----------------------------
-- Records of sys_role_func_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DEPT_ID` int(11) DEFAULT NULL COMMENT '组织机构ID',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `LOGIN_ACCOUNT` varchar(32) NOT NULL COMMENT '登录帐号',
  `LOGIN_PWD` varchar(64) NOT NULL DEFAULT '807ff71b3301262e222ad05e5b7c4325' COMMENT '登录密码,默认"ChangeMe"',
  `USER_SEX` varchar(50) DEFAULT NULL COMMENT '性别，字典项，NATURE=SEX',
  `USER_BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `USER_TEL` varchar(20) DEFAULT NULL COMMENT '电话',
  `USER_FIXED_TEL` varchar(20) DEFAULT NULL COMMENT '固话',
  `USER_EMAIL` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `USER_MAILING_ADDRESS` varchar(128) DEFAULT NULL COMMENT '邮寄地址',
  `USER_POSTALCODE` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `USER_TITLE` varchar(20) DEFAULT NULL COMMENT '用户头衔，职位',
  `USER_ID_TYPE` varchar(50) DEFAULT NULL COMMENT '证件类型',
  `USER_ID_NUMBER` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `USER_PHOTO` blob COMMENT '照片，Base64编码',
  `USER_TYPE` varchar(50) NOT NULL COMMENT '用户类型，字典项，NATURE=USER_TYPE',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `USER_REMARK` varchar(255) DEFAULT NULL,
  `LAST_TIME` timestamp NULL DEFAULT '2016-05-28 00:00:00',
  PRIMARY KEY (`USER_ID`),
  KEY `SYS_USER_LOGIN_ACCOUNT` (`LOGIN_ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('-2', '1', '超级用户', 'admin', '807ff71b3301262e222ad05e5b7c4325', '0', null, null, null, null, null, null, null, '1', '37012451412554585852', 0x646174613A696D6167652F6A7065673B6261736536342C2F396A2F34414151536B5A4A52674142416741415A41426B4141442F374141525248566A61336B414151414541414141504141412F2B3441446B466B62324A6C414754414141414141662F624149514142675145424155454267554642676B474251594A437767474267674C44416F4B43776F4B4442414D4441774D44417751444134504541384F44424D54464251544578776247787363487838664878386648783866487745484277634E44413059454241594768555246526F66487838664878386648783866487838664878386648783866487838664878386648783866487838664878386648783866487838664878386648783866487838662F3841414551674179414449417745524141495241514D5241662F45414A304141514143417745424151414141414141414141414141414642674D45427749424341454241414D424151454141414141414141414141414141414944424145464268414141674544416751454177594541676B46414141414151494441424545495155785152494755574569453345794234475273554A53464B4842497856696374487867704B794D304E6A4A4F476955334D57455141434167454541514D454167454641514141414141414152454341794578456752425553494659584742457A4A436B6248423056497A46502F61414177444151414345514D52414438412F564E414B4155416F4251436746414B4155416F44446B5A57506A524762496C57474A666D647946412B303147316C565333434F3171336F6974377239522B334D4B4E54424C2B2F646942614167714C383263365669796649343637653431592B6E65322B6872776655375A354756546A5A41354F564374302B504D615657766B36656A4A766F573955534D50666661383062756D656C30462B672F4D62654146367472386A686639697439504B76426B777538646B7A4A52466A53744C4A30395442464A413141312B2B724D666270662B4C6B6866725872755330576269537430527A787534347147556B6645413165736C587379703061384763564D694B4155416F4251436746414B4155416F4251436746414B4155416F4251486961614B474D7953754552666D5A6A5944373634374A4B57645362324B4A335039574E7032794A3177454F584D7036666349496A756548445531355762354E625931503138472F4630487664776332332F756E6564336D5239776A6E6E314A57474E44374B676556776F2B4E65626D76624A725A796238574F744E456A526D7A524569506B4B7172663071535541386746366179326C6C364D612B314A43346A505242497042426D365151336D62363053736734495674756D327556486A6A4C7858754768657A45633773392B6F6A794E4735656F53676C4D4C7543584B563473484C4D543273385535397551654B6C5874634733454775773139446A686D786839314C7432534779636367526B4D73305474484C473434686C2B56366E69796350456B4D6C6552324C596671487373754D677963785775716C4A446F77754C6B4F504C78476872336348667874617338724C314C546F6934592B52426B77704E4249736B546936757075434B3331736D70526A6161305A6C71527755416F4251436746414B4155416F4251436746414B41586F434A332F754C43326541764C3635625857496548695479465A657A3271346C4C334C384742354839446A2F41487639514E7A7A53596D5951782F39504751324A763841714A345634505937643872313072364872347576584874717A6E6D36626A6C34654E2B356554716C6B753552656F6A707659446C656F59396F4A32666B31735436686E32466A3973534870366D5A6E414E7A77354C5676366443437947636235744753444E6D5937495748546459336D592B4E6A6668556631736C795271356D35624D774F50444449712B494873734435433276777272786857473353593866754B4D34354D446D30754B53556D5478394C38534B6A62464F354A58672B376768676B575553706C34716E52675036714C627738755971447042325A4E7A44334F47647632306C6947547267646D76484B6734694E395370586A593179324E705364566B7A66684377394255534352435868634D4F6F486D742F4879497168797453534F676654587652635579593254506646636C6F38676139446E694A452F54384F46656830653577664732332B686B3758583571567564697863704D694A5A4649395142425533556A78556A694B2B69725A4E53654E6173476570455251436746414B4155416F4251436746414B4155426879736D4846676B794A6D43525241733748774651765A565576596C57727334527850764866354D6965544D646971797945775245384F514A2F77416F3443766C2B7A6D64375365396878716C594F623565576935674A4A6D7957755170476B616E6A493550356A79384B7156644378733149737463735A4F4E6C4D42367A37456E454C34664563717369434F35457A344D754C4730545171444F68364A625856676F75746D4850797131586B67364562744F345A75486C794A417742304341366452494845664C6572624A4D7271326D57694C64646C7A52486A37746978346D537874446B78475245592B66454B33694B34314A4F6655317437776F344E5A59476B7341566C425677592F316F34433371757161334A4E49694D62466D79506554446D614F564C4451326358315567583953747776343163362B7570556E364770466B5A324B3857623074493862734A6F3130366D5835756B63412F2F454B6E577162686E485A376C676B332B4A5938664E67646E776E73596E42315666304D447942754638447077716D2F58384669796553534F584A7465344A6E593639554F51424C4B454E757053506E433235382F4F736C715038414B4C6C59363532503356504B7345554F583765464D5165725430736564396444773463654E624F6A324C4A385739444E32634B6135527164676A4E305533766F4E6647766F6B654B7A315854676F4251436746414B4155416F4251436746415562366F626A50384173386661635A7A472B5533584E4C7A4555657242664F764C2B54796531553954304F685456324F58647842446968346C756B432B3347527277477476506C586A4F736E70315A536354484A7863687051446C43374F764D73545A46762F456972485852484565386662474751737655506243644A4E7239524F70503331786B6B6A516B784D7063764939767245415471614C5567324E2B71336A584B374231504F4C73474C484F494D355237557044777972653639576F7634387176717974314A4C4D58626F6F2F326D5A6A726C786B584F70367942775A47745A6C2B4F6F7166466542444B356D78376E6A794E4C732B5532586872655439712B6A6F4F6467666D486A62377136326E6F7948467256455668376F6A5368356278426D4256677756343738514C6355622B46575652573262347A385A74366B786332794A754A4353794C36515A762B6E4F76365378306133503431313062576878573149764A4D6D446C5A574A4F6F6B7858444D7741344F506D2F774234636134314B43634669323763317A646B6832746A2F7743546A456E436C49314B44314244385277716E4C55746F7979646F5A2B62693551394C4A6A6A3168534C426C4A755174394E4F4A465A6E53484A6172483653374B3373376C7467535533794D6179506669562F4B61392F705A7564496536504837574C6A62545A6C6872595A5251436746414B4155416F4251436746414B41356E39514A5A527663557654315278776B65665532763871385476742F73583250573661696A4B49512B5474474736523344463164536261735459362F6A574E4C51314A6B4C6A375154687A4969335057306A6B2B51767258494A49334E74322B475A2F61364C79526E7731415958417274616F73535A36477A7A4E6B6C32684F683647305043784333486E55713479636147504C37567932655033497864425A573846484B70757246617277502F414D75346B5543786A5858555679475468486E4D375277705266327748477175756A4B3369434B363964797669696D35765A676B7951306F3670515372714643334934507058466143447753563775507458637A684B72442F774172474C4E464B6D6E6F34714C6558437457504C444D2B544179466E7963764A6D785A57306C796B454F514278456E53517266614E4B6E43314D376C457074356D787354476B516444526C43784770735341514C384F4E5A372B533270304F65664967326F2B3146453850563733584D434F67742B63456372692B745A6F6E5175386E512F6F6E7638414D323474695475584C6F455679654B456455642F4D61697450782B546A6B6A314D2F63704E4A3944746C6538654F4B4155416F4251436746414B4155416F4251484E66714B5375356C526F544547516B6164514E2F77414C313548645876384177656E3158375079564F543259582F614251736379454E2F684457594565485362566C347259307079653464724F4C6876496E726456627152754441722B4E4C556A5575536B2B397662544A45375A4C36686A5A535271552F4C667A74584D644E5A4C3239494C62436B53786B39494C485735486872572B75694D6C32327A517A2F59612F5350567749743456566B615A646A6E795273694B69363839526171594C54566C6A556B4574707A714D457049334B6A6775785966627A714C676D69743777523073654934654E37634B684967704F543270506B5A7357546778743762534C377759577459395631747731465855755A4D324C795451376456326977485432354A6C646D6B48494C5A74504C5771377372716A7A766162684D597347473759697A6B4B74756248554668617746723272696941397A7333306F375A534C4C69794C676D434D4B2F6E62682F3771306448444E2B586F5A2B356B696B4858713977386755416F4251436746414B4155416F425143674F642F557846584A44473935635A6C467552424F7465623346376A6631586F55444741794D334864797A526C464D35356B723656436A7A724C56616D79706275685646694C58463765486C586247696D78366A7346747731304663524E6D327657597A344468562F67706535463572456B323076784655584E4E434C6D6C6451567672626A564C5A6243504B7839536E7131306F675275364A3049536774343147794A314B766E5376316E545331375658354F575057446C6F7752564251717776626D5361767262517A3257705A70746E584F67475769685A34464A7343516245616B2F7A4657634F57706D73344B2F68724C446B506A504152497039353359586462582B2B395674526F636B3762394A3846346533526B796C6A4A6B4F5353334777502B6D7654364F4F4B7A366E6D39793032677539626A474B4155416F4251436746414B4155416F425146412B7236482B7A517949534A43355157346B47784E596536746A5A303371796D64715971506B52724C367454497750432F494434566A787255334D7332387A3475506A4E4C495169727A715757496B7478544A554A4F2B747068627039624F474173525956545673304F424839574F33495A4F69594D7048465352623750477231663646466C506B32662F414E2F3274754C33676B5A6269366C6C7472345679316B2F424F6B6F7A7646484C454A496A634D626A34565536462F4D3977342B683675412B79696F48636A4E34794E7678343261655A59776F315A6A77466364417368536430336E61584634703049504F34717030483745526D46754D596D4477534C4941626D32746353614F636B7A715062446A4A68575A6A716F364F6E34316F7861737A35566F654D7261336A33583354316B4F76747343774F70734C446E61753370724A52573268326A5938464D48616358465557455561672F45366D76587856343153504979573557624E367243416F4251436746414B4155416F42514367423430427A507674636E633872495648627078474B4B6E4B316872393965543257375766305063362B465678702F774459673967686B683356496A6F46554B7649364457395634397779795A323334383866396441343542746175346F34725077564C65637A61647636312F626F536F764A4979674B6F3564546566674E6167377061463672704C5A514F35742B326543614B475462794A4D6D5551784E2B7A364661526C3677716C79726172367546584C72354770534B58324D61634E6D625A4732755352494D7245396B6B676F335355735477754E5257647470777A54564A7155644B774E75397646574F4D6B3949757454497952473937316A6263705849667049346971335A45326E456C443362752F5A4E7A7950326B47484E6D3544637736785271504573774E68556C425261576132333766326C6A7A752B584B765739766354486D6A6B4B38374D70477454637277513470374D76477A39683975376B49706361557648494F7041326E33727058613156794E376343315975777073716E48562B744C67692F45654E764B755878384764706B35486A4847504A7572504E6F424A47354A4E7756367549382F5477726B70765572736E423162457973664B675362476353517638414B36384E4E4B3953746B314B504B7456316350637A3149694B4155416F4251436746414B4155416F416141357276475363484E33544A6E4A4D45637A75417475706751505472356731343252745774397A365844586E6A7056623855612B324B6D546E592B5A4370574B65503344314378414B33415033306F705A526B72786C5077622B524D736B6855453247677154744C46616145586C375274733742356C764944314B3761324938504375704B5A4A7A623845443356323968626E6C525A7334456D564259787945426D756F3656626E36674E4F726A576E2F36625655496F58566F33716A57327A6135477A456C794265474E51696F56587036523548554557304E5A585A76566D757446565256466F697965694D395044586A7874554F5A5A5847636E2B6F44537A3568612B6E685643743769334C54326B423264426A596D2F78354533524A474C2F414E47566570574C4331323148432B6C616357534759636D4C6B6F4C456531385041336E4A33614634355A4D7434336B39346A70487441424272706251583862567474323555515A4B394A4A7A4A76626276637532377443697A78354F45736E7645593436466764785A6B6A49305A5045655043736472777A5858464B2F354F6D6A6334647A6853534C56574173644C36564F393162597070523133492B524778634358506C753536725259393958652F5371663564626D714C57343135476A723476325A4655366E732B4763506263624859336445485752595859367364504F7657773034315350443747546E6B646C366D375670534B4155416F4251436746414B4155416F4251484B507148486B4B4D734B4430724B7A535734394A31422B3431346E625454663350712F69326D712F596D396E62446C78596E773244514848547074346743344E6161745056656868374E6256733162666B6149516954316362316E53314E4536474C50784764626A5438616E65704C466445572B4D772B5A69414B71686C38706D30754F6F78683044314E566A5768577265344D69724577356766787145466B797A6E472B3436356D586B512F395149536E6D52725643334C4D6D694B7A744D51664A73665249703050343371535A56435A5A777A684F687747486E58655233676A7849596D36556A5564523543317134324F4D462F374F69764773624379686574764941584A712F45594D786C3248653858756953534A635A59516D5444374B5875796F5A4C734C664252632B4E5272645A4E50716A546B777672367A2F567A2F41494F756756375A387566614155416F4251436746414B4155416F425143674B39335274526C417A596C366967744F673572795032633679646E46507550523650596A3276384542732B466934655A4D324F354D65534F72322F79715646744B793471717230386D3773354C5871703371664A3462544D655636355A61696C744430714C4C45566B4E7248537261704E4274706B446E795252544142726A6B70716979686D716C705275525366304C4733472F543456335A4555705A6A6E78703269646B4149436C6D4639625646305A5A533654676F57546A763841332B4C70516B6458727350792B645A307663583564616C63337A472F59373238714C3034383747574138756B6E556659616C65734D6F707353735756464C417042463756457352753750744530383463727A304654725357565A6369534F677871327737466C5A375265374F55397247684846705A66516F2B47747A576E2F7A713259714C39755256386566736A42394A2B314D6E437A355A387A702F63513261645631434D7775735A503674626B63716830634C3574767761666C2B326E6A53586B367A5873487A496F4251436746414B4155416F4251436746414B412B455838776141722B563239486A504E6C7779644D594250733247684F6C676643736C7343724C5236466534374A5661313953416E6E4334306862356C4E59336251324B7570485A45337334736D56492F52456939585566433136677277704C6C713449486263504C7A4D722B345A6E394B493677514835756B3847627776536962315A6261336845374A476B6B52574A7A464952365A567463483761766152464E6F6A4A357632692B334C6B533544766535737059664333545558424E50796B6144647A375A744B794B63564A4152655A3577786E6C4E74456A3654306F6F356B36313274713152586B78327531722F6A597076634C343238534C6B772F77446A49414678384A53584553635464794231466A71616F7644324C464B33312B70733976625A43346A41627246777265527143314F3274434F6E397537484643344C616744714138664F74754C48423575624E4A507A4C755751304D4F32786F3339514357615141694E663167486E34565A626B394B6C6448537375376A2F63736D32625A693764696A48783173747937747A5A324E325A6A7A4A72565447717145596332613253307332366D56436746414B4155416F4251436746414B4155416F425147444E5471784A6C38564E52767379654E785A464333424C426C746F3165546B52376D4A7952473459625A473178343546776A6F5A5638593059456A3752564C724E55693150566B526D6274764F506D7A4F32322B2F67337573384C2B7052347368356643724B5763366C744B4C314D755033566A74365A59576A484A6A66542B4657315A4F32416A737675484245786E58554C6F3455673250774E527376517472696C5151473562706A37704D4934346D3978766C43692B70386872566254624F326F712B535A58742F61396A326638417576636D5A466777472F5147617A6E775649783633592B4146614B34595532504E7964695846545237652F615A45734F5A742F756A486B6C45515756656C6D522B6F71656B2B4241497248704F68616D34314F677762677A6478376A687774654C4567786F4849355374643348324169746E4E38326C364978635677543957792B6476776C634E6E2F5733452B517262685768352F5965704B31635A7851436746414B4155416F4251436746414B4155416F4251474C4A4E7365512B436E384B6A62596C54644649334644634D426457314665626B5237474A6D69716951454867654E557053587651394A6A4B73646877476C716D6B6456694E6C326149546539476F4276646B35564B714C763375494B35766D464130354837645448652F5459476C747A5869756F315A586C37556E3362636F6F343854324D6444655755576A30484C7134317969625A48735A71317276715357386467625558516B43544A58307779456C68456C3732462B4A706C71594B5A704C463239746D4A73754132644D50654F4E6438614938576B41734B356A6F717157565A636A7334586B6C75783969796362426D7A4D3575765033435A736E4A502B4A7A65772B4657344D62536C37746C5859794A754673744470754642374F4C48487A41312B4A3172304B714565546479327A505569496F4251436746414B4155416F4251436746414B4155416F44446D47324C4D66384466685562374D6E6A2F6B696C71566B694D54635631552F79727A71755642363931446B31456A4B503861723477576370526C5A62362B64366D6B64546778757761366E69616B536772323959696444587463382B64716862517578366B4E427570773239736979386A55466B67356B7853666637774D6D63574E37666D70624A4A562B75436378554F544E464578366F6B735350506A5571367371766F69376253384837334778482B64315A316948365548452B56363230616C49775A452B4C5A6136306D4955416F4251436746414B4155416F4251436746414B4155416F434C3333637366466753427A2F577A43596F6B3538435366734171764B347179334257624970364F5249663431356C5432374C5139535464427332714867336761734B5950496C5739373648543736496C4A696B594662333170424B7469497951586C594D66534F46567330317356486656496D754E5365414655575262795547783239746D525049485A534676384168553659327A4C6C79704678696B783974696162492B5961724834322F6C576D74654F706B74626C73594F302B374D4A4F355A4D37633578464649505953512F4B725345424235445375594D6B354A5937574B4D63493632434341516267384458706E6B48326746414B4155416F4251436746414B4155416F425143674D65524F6B4D5A647A6F4F41385458557042785876507536562F71727347316872675235453869636769784D50384169503841437366637443505136564A5A5A55634D774934654E596B656B5A6E4163644A47687177714E4B584779492F5841656F662F476635476B44636A4D7A64765A50544B444734354D4C666A52734A455A2F64596D596E71366A65397169536B38346D4A6A3557555A4D686755343246647052547163795A48476875376A33663239744558376645644A736C4238696D36723573522B46584F3653304D79783274755537634F36704D316D6435656F736454576139354E5750484256653774316B545A5978444A304F637A48494950494D527239395277667A2F444F352F346E364A2B6B6E64693778734565484E4A664C7842307263334C526A682F75384B3954446556423432616B4F532B316355436746414B4155416F4251436746414B4155416F447A4A496B616C6D4E674B4167632F4D61642F4242386F7254536B45477A38353934376F754C396574726D6C4E6B3967515838424D5A422B4A72794F37364873644853476467685830676A6E6F6179314E724E6B4471494249476C5749725944576B734F663356314477664D6948486E51704B697350427743503431316F6A4D46633344746A61354F70784837504F36656E577133556D724850653473566F58614F50496B5A422B587249427143624C644949415264497462374255794A37396B394E37577456544C4343377A566B32435A6C2B644752782F7374657059644C6F703747744764502B6D32345A4F446A594F3659354B6964456D587739514678384458706672645572486C327372614836483272636F4E78776F387145364F5055764E573569726B354D6A5547355854676F4251436746414B4155416F42514744497A6361442F6D503675536A552F6458556752473664786674635354494E6F6F6B424E32507149485077417179754F546A6345527375566E356D482F634D356A37325A3634346A776A6850794C62784939527137696C6F69456D322B7453534F4835612B74717A596E3146584D5532756B547848774B4D5166343134336372373265763148374564773753336D4C64646C7863745463756939512F78573172485133334A7347787656714B3247427543503841585851597058307472356D6753496A64383070694F6948556A6A7A71466D547055356E75534D7A6C6D4E7A66536F564C47614D474F586531744C366D704D346A5A6C786771676336676B536B726E65575066614A55356B565A56616C57542B4A314C365A62563776303832575268643278563666396B6B667972364774466245767366505875316B5A642B3039386B32374A654A3774456448546866394C433965642F466D69793549766D3137317475354B546979677568365A49573064534F494B6D7071306C4C55472F556A676F4251436746414B41775A4762424144314E6476306A6A58557042445A65387A794D55512B3276677648375456696F636B30444D74797850567A622F58552B4A795453796F56334244424D6F614B59326B546C30445731577051514A515741414173426F4150415677474E7A61704934666E48362F346F2F75574A6C4C7965534D6E342B736667613876757233487139522B7733766F33334C37634A322B56765370756C2F4271387A5A6E7072564859784B6A4C314455486E56736C6348315844456975706E494D4F5461783130413174526E557976626A5A67664C6856624C55796C62736E395733696149367A3768346F57507259656F384B34326352346E346B48537056444B2F334E474877354278754B7353314B3737485966706B3061665433743946414C2F742B48685A3247746651595034492B647A72337379376D72592B615A56344B335352354855666A57547355386C324B336730747A7A636A4379636650784C67796E32356970745A775053667434566C5263574C5A76714A6C51414A6C45354935784E704B763238366B724E454C55544C747448636D31626F674D4576544C7A686652682F702B797071794B33566F6C623149694B41554242355737795358436568427948452F45316171484A4975584B48754244717831747946574B6846324D4571735270397448526A6B6A784C4942426267547848777156546A4D6D334871526D5035644B6D524E7747674D4F53624966473164514F4766576E454D2B306D65332F4C6C69652F784C49662B4B76503779386E70644A3651633137567A704D484E6A6E553256445A764D477649736A3163624F2F6476373675526A4B474E7752584B324A5771544D4F5236726E516341616D6D51614D65584B53756A57484F314471494C506151666D342F6855575452584A3856704A62366B333046636B365A386C524643414F4E7258726B68496A4D676449763438717352786B46764872786E484F7245563252306A364E5A306B33617134726A31594F51384B6E2F74762F4146462B346B31376655764F4F5051384C7555343550755776653441797944387A4B47413830302F6E55386C4A544B6362686B49565762454B5038414B773657386A795032563531716D744D726B6A54724B304C44715A44594838796E785538525845644A54626B33594F6A6D63644B38576B30596A2F4D7474613432684264646D373679384E6C697A2F2F41434D5561653644314D672F473378715662466471463977647777383641543473717978747A5538506A56695A57314273563034553234565465745352426B626B534F732F75446966777161496D35444F736936485563616B6A6A457351636542727348457A597834766269566566452F4531456B5A6B556B324770506851466633767650744862584D47667657486A7A38444530796C78385174366C444F484F76714A4A742B34396E354756687A783557504C477A5254524D47552B32397A714F5950477366656F3074546230726534347A746B6638415563416354657644737A327149364632747530754F79517564423870387172544C30692F51376C6F43547972764934364779636F797063486A2B57705351346D684F486B4A7370726A5A4A492B5162655165707134434E3365497249736472453871354A4A496A4D714D39424A4672446855307A6A715175546A466F484A352B4E574B7857366C302B6A55633053626D7A4E6248563467716E6831324E7A39316576384148505378342F795368314C6C33336C62707433627556756D3234795A655469526D5177534567474D66387A356462394F74622B4D765538354D6F5859333145324C75456A41652B4275546A3034307241712F384139556D6E5638447257624C316F32324C71354A334A624F786C687A576154306B364E38562F7744537344586730706D6C6D622F4242364977585067547872717049646A4E743236706B703771446F646448536A72427953773758756B32464B732B4C4D594A42784131552B544C774E4E5563616B756D463337464B565365425935447A44656C76387245572B77314C6D516444412B6F48345636426D4E4C4E5676624A5561697567773473362B33344E7A46534F456A44494A4371486A7A7270772B3776764733625067766D37684D4959563463325A76306F764D3179745733434F7477635337312B6F48636E63426B784D4F5239753274765437454C455379442F755344585877476C6263574F7466755558625A7A664A2B6E753754457A51516E3161395234316F7457724931794F7030665A4E695A6670566B5963716D43644A58615545584B764C364A4C44394C575671382F77435178636B6C2B4458307373582B6853594E687A4D4B53523546456B4C57365A55314676506D4B2B57375858766A33576E71665364664E572B7A314A534946424779614D756F4E594F5A745643305262675A4D644776714236716C7A48456B384C4E5A6742666C705531596930544F42464A4D774146396247704B57567545543477496F495357487141314E577573497154625A554D6C424A6D504B3431765A5235566D356D7A67615758694D794E306A51384252584471524752685474455543394A38616C7A314938446F483033324C49322F61354A4D725273742B73526B63455564497638612B6A2B4F7832726A6C2F325A3833386C6C72624A432F71542F642B6442676472626A6B54573642413861716562534B55565239396230705A35386E354E7939716E784857614B34364347566C304B6B6167676A777179364657644E376637796E333774646D7A5736743132786C69794A4F426C694F6953664558733165666E7777353954546A76704272703179794632766171336F5357707634575138453355756C2B497174366B796567794A4849593641384258654A78736B494D715349366344784234476F576F6454502F5A, 'ADMIN', '1', null, '2017-05-16 12:47:44');

-- ----------------------------
-- Table structure for sys_user_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_aud`;
CREATE TABLE `sys_user_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `USER_ID` int(11) NOT NULL COMMENT 'ID',
  `DEPT_ID` int(11) DEFAULT NULL COMMENT '组织机构ID',
  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名',
  `LOGIN_ACCOUNT` varchar(32) NOT NULL COMMENT '登录帐号',
  `USER_SEX` varchar(50) DEFAULT NULL COMMENT '性别，字典项，NATURE=SEX',
  `USER_BIRTHDAY` date DEFAULT NULL COMMENT '生日',
  `USER_TEL` varchar(20) DEFAULT NULL COMMENT '电话',
  `USER_FIXED_TEL` varchar(20) DEFAULT NULL COMMENT '固话',
  `USER_EMAIL` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `USER_MAILING_ADDRESS` varchar(128) DEFAULT NULL COMMENT '邮寄地址',
  `USER_POSTALCODE` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `USER_TITLE` varchar(20) DEFAULT NULL COMMENT '用户头衔，职位',
  `USER_ID_TYPE` varchar(50) DEFAULT NULL COMMENT '证件类型',
  `USER_ID_NUMBER` varchar(30) DEFAULT NULL COMMENT '证件号码',
  `USER_TYPE` varchar(50) NOT NULL COMMENT '用户类型，字典项，NATURE=USER_TYPE',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `USER_REMARK` varchar(200) DEFAULT NULL,
  `LAST_TIME` timestamp NULL DEFAULT '2016-05-28 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表变更记录表';

-- ----------------------------
-- Records of sys_user_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (`REL_ID`),
  KEY `SYS_USER_GROUP_USER_ID` (`USER_ID`),
  KEY `SYS_USER_GROUP_GROUP_ID` (`GROUP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户，用户组关联表';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES ('1', '-2', '-1');

-- ----------------------------
-- Table structure for sys_user_group_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group_aud`;
CREATE TABLE `sys_user_group_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `REL_ID` int(11) NOT NULL COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (`REL_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户，用户组关联表变更记录表';

-- ----------------------------
-- Records of sys_user_group_aud
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`),
  KEY `SYS_USER_ROLE_USER_ID` (`USER_ID`),
  KEY `SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户，角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '-2', '-2');

-- ----------------------------
-- Table structure for sys_user_role_aud
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_aud`;
CREATE TABLE `sys_user_role_aud` (
  `REV_ID` int(11) NOT NULL COMMENT 'REVISION ID',
  `REV_TYPE` tinyint(4) DEFAULT NULL COMMENT 'REVTYPE 0:ADD, 1:UPDATE, 2:DELETE',
  `REL_ID` int(11) NOT NULL COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`REL_ID`,`REV_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户，角色关联表变更记录表';

-- ----------------------------
-- Records of sys_user_role_aud
-- ----------------------------
