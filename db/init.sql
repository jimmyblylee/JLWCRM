/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50626
Source Host           : 192.168.2.3:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-05-03 17:19:00
*/

SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `sys_att_ftp_conf` VALUES ('172.25.67.215', '21', 'jimmyblylee@outlook.com', 'TmJ82h34', 'file/upload');

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
  `DEPT_PARENT_ID` int(11) DEFAULT NULL COMMENT '上级组织机构ID',
  `DEPT_NAME` varchar(200) NOT NULL COMMENT '名称',
  `DEPT_TEL` varchar(20) DEFAULT NULL COMMENT '电话',
  `DEPT_FAX` varchar(20) DEFAULT NULL COMMENT '传真',
  `DEPT_EMAIL` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `DEPT_URL` varchar(100) DEFAULT NULL COMMENT '主页',
  `DEPT_ADDRESS` varchar(128) DEFAULT NULL COMMENT '邮寄地址',
  `DEPT_POSTALCODE` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `IS_ENABLED` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `DEPT_ORDER_BY` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`DEPT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('-1', '3', '总机构', null, null, null, null, null, null, '1', null);
INSERT INTO `sys_dept` VALUES ('1', null, '总机构', '', '010-8817881', '453@qq.com', null, '中国北京', '10000', '1', '1');
INSERT INTO `sys_dept` VALUES ('2', '1', '分支机构', '18712347788', '010-8817881', '11@qq.com', null, '中国北京', '1', '1', '2');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `DICT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DICT_PARENT_ID` int(11) DEFAULT NULL COMMENT '父级编号',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('-10060003', '-10060001', '0', 'WHETHER_TYPE', 'false', '否', '是否可用', 'fou', 'f', null, '3', '4', '1');
INSERT INTO `sys_dict` VALUES ('-10060002', '-10060001', '0', 'WHETHER_TYPE', 'true', '是', '是否可用', 'shi', 's', null, '2', null, '1');
INSERT INTO `sys_dict` VALUES ('-10060001', '1', '1', 'WHETHER_TYPE', 'WHETHER_CODE', '是否可用', '是否可用', 'shifoukeyong', 'sfky', '', '1', '', '1');
INSERT INTO `sys_dict` VALUES ('-10050003', '-10050001', '0', 'STATUS_TYPE', 'true', '可用', '状态类型', 'keyong', 'ky', null, '1', '111', '1');
INSERT INTO `sys_dict` VALUES ('-10050002', '-10050001', '0', 'STATUS_TYPE', 'false', '禁用', '状态类型', 'jinyong', 'jy', null, '1', null, '1');
INSERT INTO `sys_dict` VALUES ('-10050001', '1', '1', 'STATUS_TYPE', 'STATUS_CODE', '状态类型', '状态类型', 'zhuangtaileixing', 'ztlx', null, '1', null, '1');
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

-- ----------------------------
-- Table structure for sys_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_func`;
CREATE TABLE `sys_func` (
  `FUNC_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `FUNC_PARENT_ID` int(11) DEFAULT NULL COMMENT '功能ID',
  `FUNC_CODE` varchar(30) DEFAULT NULL COMMENT '功能编码',
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
INSERT INTO `sys_func` VALUES ('-1990900', '-1990000', 'pages.account', '个人资料', '9', 'FEATURE', null, '个人资料', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990800', '-1990000', 'pages.icon', '个人头像', '8', 'FEATURE', null, '个人头像', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990700', '-1990000', 'pages.blank', '空页面', '7', 'FEATURE', null, '空页面', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990600', '-1990000', 'pages.500', '500', '6', 'FEATURE', null, '500', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990500', '-1990000', 'pages.417', '417', '5', 'FEATURE', null, '417', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990400', '-1990000', 'pages.403', '403', '4', 'FEATURE', null, '403', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990300', '-1990000', 'pages.404', '404', '3', 'FEATURE', null, '404', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990200', '-1990000', 'pages.faq', '系统帮助', '2', 'FEATURE', null, '系统帮助', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990100', '-1990000', 'pages.login', '系统登录', '1', 'FEATURE', null, '系统登录', 'icon-info', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1990000', '-1000000', 'pages', '通用页面', '99', 'MENU', null, '通用页面功能菜单', 'icon-home', '0', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300800', '-1300000', 'sys.dept.mgmt', '单位管理', '8', 'FEATURE', null, '所有单位的信息管理', 'icon-home', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300700', '-1300000', 'sys.user.self', '用户管理', '7', 'FEATURE', null, '管理所在单位的用户', 'icon-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300600', '-1300000', 'sys.user', '用户管理', '6', 'FEATURE', null, '管理所有单位的用户', 'icon-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300500', '-1300000', 'sys.role.group', '角色管理', '5', 'FEATURE', null, '角色组管理', 'icon-user-following', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300400', '-1300000', 'sys.role', '角色管理', '4', 'FEATURE', null, '角色管理', 'icon-share', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300300', '-1300000', 'sys.menu', '菜单管理', '3', 'FEATURE', null, '菜单管理', 'icon-compass', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300200', '-1300000', 'sys.dict', '字典管理', '2', 'FEATURE', null, '字典管理', 'icon-info', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1300000', '-1000000', 'sys', '系统管理', '30', 'MENU', null, '系统管理功能菜单', 'icon-power', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1230400', '-1230000', 'impl.employee', '实施人员管理', '4', 'FEATURE', null, '实施人员管理', 'icon-users', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1230300', '-1230000', 'impl.files', '文档管理', '3', 'FEATURE', null, '文档管理', 'icon-book-open', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1230200', '-1230000', 'impl.process', '进度管理', '2', 'FEATURE', null, '进度管理', 'icon-hourglass', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1230100', '-1230000', 'impl.project', '项目管理', '1', 'FEATURE', null, '项目管理', 'icon-social-dropbox', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1230000', '-1000000', 'impl', '实施管理', '23', 'MENU', null, '实施管理', 'icon-cup', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1221000', '-1220000', 'sales.archives', '方案管理', '10', 'FEATURE', null, '方案管理', 'icon-doc', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220900', '-1220000', 'sales.compact', '合同管理', '9', 'FEATURE', null, '合同管理', 'icon-layers', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220800', '-1220000', 'sales.opponent', '竞争对手管理', '8', 'FEATURE', null, '竞争对手管理', 'icon-ghost', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220700', '-1220000', 'sales.activity', '活动管理', '7', 'FEATURE', null, '活动管理', 'icon-social-dribbble', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220600', '-1220000', 'sales.salespersion', '销售人员管理', '6', 'FEATURE', null, '销售人员管理', 'icon-user', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220500', '-1220000', 'sales.biz.opportunity', '商机管理', '5', 'FEATURE', null, '商机管理', 'icon-hourglass', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220400', '-1220000', 'sales.cule', '线索管理', '4', 'FEATURE', null, '线索管理', 'icon-call-in', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220300', '-1220000', 'sales.linkman', '联系人管理', '3', 'FEATURE', null, '联系人管理', 'icon-notebook', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220100', '-1220000', 'sales.customer', '客户管理', '1', 'FEATURE', null, '客户管理', 'icon-user-following', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1220000', '-1000000', 'sales', '营销管理', '22', 'MENU', null, '营销管理', 'icon-basket', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1214030', '-1214000', 'manage.risk.project', '项目', '30', 'FEATURE', null, '项目', 'icon-social-dropbox', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1214020', '-1214000', 'manage.risk.sales', '销售', '20', 'FEATURE', null, '销售', 'icon-basket', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1214010', '-1214000', 'manage.risk.mgmt', '经营', '10', 'FEATURE', null, '经营', 'icon-briefcase', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1214000', '-1210000', 'manage.risk.menu', '风险管理', '40', 'MENU', null, '风险管理', 'icon-fire', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1213030', '-1213000', 'manage.cost.travel', '差旅', '30', 'FEATURE', null, '差旅', 'icon-plane', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1213020', '-1213000', 'manage.cost.impl', '实施', '20', 'FEATURE', null, '实施', 'icon-cup', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1213010', '-1213000', 'manage.cost.sales', '销售', '10', 'FEATURE', null, '销售', 'icon-basket', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1213000', '-1210000', 'manage.cost.menu', '成本管理', '30', 'MENU', null, '成本管理', 'icon-pencil', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1212000', '-1210000', 'manage.received.payments', '回款', '20', 'FEATURE', null, '回款', 'icon-magnet', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1211030', '-1211000', 'manage.budget.impl', '实施', '30', 'FEATURE', null, '实施', 'icon-cup', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1211020', '-1211000', 'manage.budget.sales', '营销', '20', 'FEATURE', null, '营销', 'icon-basket', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1211010', '-1211000', 'manage.budget.mgmt', '经营', '10', 'FEATURE', null, '经营', 'icon-briefcase', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1211000', '-1210000', 'manage.budget.menu', '预算管理', '10', 'MENU', null, '预算管理', 'icon-calculator', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1210000', '-1000000', 'manage', '经营管理', '21', 'MENU', null, '经营管理', 'icon-briefcase', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1100000', '-1000000', 'home', '首页', '10', 'FEATURE', null, '首页面', 'icon-home', '1', '1', '1');
INSERT INTO `sys_func` VALUES ('-1000000', '0', 'root', '功能管理', '0', 'MENU', null, '根菜单，不会被显示', 'icon-home', '1', '1', '1');

-- ----------------------------
-- Table structure for sys_global_variable
-- ----------------------------
DROP TABLE IF EXISTS `sys_global_variable`;
CREATE TABLE `sys_global_variable` (
  `VARIABLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '全局变量ID',
  `VARIABLE_NAME` varchar(50) NOT NULL COMMENT '全局变量名',
  `VARIABLE_VALUE` varchar(1000) NOT NULL COMMENT '全局变量值',
  `VARIABLE_DESCRIBE` varchar(1000) NOT NULL COMMENT '全局变量描述',
  `IS_ENABLED` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  PRIMARY KEY (`VARIABLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='全局变量存储查询表';

-- ----------------------------
-- Records of sys_global_variable
-- ----------------------------
INSERT INTO `sys_global_variable` VALUES ('1', '10123', '系统名称', '中国移动健康医疗大数据中心', '1');
INSERT INTO `sys_global_variable` VALUES ('2', '10124', '服务协议', '', '0');
INSERT INTO `sys_global_variable` VALUES ('3', '10125', '隐私条款', '', '0');
INSERT INTO `sys_global_variable` VALUES ('4', '10126', '验证码', '验证码是否有效', '0');
INSERT INTO `sys_global_variable` VALUES ('5', '10000', '系统颜色', 'default', '1');
INSERT INTO `sys_global_variable` VALUES ('6', '10001', '导航条位置', 'left', '1');
INSERT INTO `sys_global_variable` VALUES ('7', '10002', '导航left模板文件夹', 'default', '1');
INSERT INTO `sys_global_variable` VALUES ('8', '10003', '导航top模板文件夹', 'horizontalMenu', '1');
INSERT INTO `sys_global_variable` VALUES ('9', '10004', '导航right模板文件夹', 'default', '1');
INSERT INTO `sys_global_variable` VALUES ('10', '10010', '按钮圆角or方角', 'components-rounded', '1');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组表';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('-102', '经理', '负责集团业务报表的配置管理工作', '1');
INSERT INTO `sys_group` VALUES ('-101', '超级管理员', '负责系统维护', '1');

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户组，角色关联表';

-- ----------------------------
-- Records of sys_group_role
-- ----------------------------
INSERT INTO `sys_group_role` VALUES ('1', '-101', '-101');
INSERT INTO `sys_group_role` VALUES ('2', '-101', '-102');
INSERT INTO `sys_group_role` VALUES ('3', '-101', '-103');
INSERT INTO `sys_group_role` VALUES ('4', '-102', '-101');
INSERT INTO `sys_group_role` VALUES ('5', '-102', '-103');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('-103', '经营管理者', '拥有所有业务功能权限', '1', '1');
INSERT INTO `sys_role` VALUES ('-102', '超级管理', '拥有除了业务以外的权限', '1', '1');
INSERT INTO `sys_role` VALUES ('-101', '基本角色', '基本用户，拥有首页、个人信息维护等权限', '1', '1');

-- ----------------------------
-- Table structure for sys_role_func
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_func`;
CREATE TABLE `sys_role_func` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `FUNC_ID` int(11) NOT NULL COMMENT '功能ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='功能，角色关联表';

-- ----------------------------
-- Records of sys_role_func
-- ----------------------------
INSERT INTO `sys_role_func` VALUES ('1', '-1100000', '-101');
INSERT INTO `sys_role_func` VALUES ('2', '-1990000', '-101');
INSERT INTO `sys_role_func` VALUES ('3', '-1990100', '-101');
INSERT INTO `sys_role_func` VALUES ('4', '-1990200', '-101');
INSERT INTO `sys_role_func` VALUES ('5', '-1990300', '-101');
INSERT INTO `sys_role_func` VALUES ('6', '-1990400', '-101');
INSERT INTO `sys_role_func` VALUES ('7', '-1990500', '-101');
INSERT INTO `sys_role_func` VALUES ('8', '-1990600', '-101');
INSERT INTO `sys_role_func` VALUES ('9', '-1990700', '-101');
INSERT INTO `sys_role_func` VALUES ('10', '-1990800', '-101');
INSERT INTO `sys_role_func` VALUES ('11', '-1990900', '-101');
INSERT INTO `sys_role_func` VALUES ('12', '-1300000', '-102');
INSERT INTO `sys_role_func` VALUES ('13', '-1300100', '-102');
INSERT INTO `sys_role_func` VALUES ('14', '-1300200', '-102');
INSERT INTO `sys_role_func` VALUES ('15', '-1300300', '-102');
INSERT INTO `sys_role_func` VALUES ('16', '-1300400', '-102');
INSERT INTO `sys_role_func` VALUES ('17', '-1300500', '-102');
INSERT INTO `sys_role_func` VALUES ('18', '-1300600', '-102');
INSERT INTO `sys_role_func` VALUES ('19', '-1300800', '-102');
INSERT INTO `sys_role_func` VALUES ('20', '-1300900', '-102');
INSERT INTO `sys_role_func` VALUES ('21', '-1210000', '-103');
INSERT INTO `sys_role_func` VALUES ('22', '-1211000', '-103');
INSERT INTO `sys_role_func` VALUES ('23', '-1211010', '-103');
INSERT INTO `sys_role_func` VALUES ('24', '-1211020', '-103');
INSERT INTO `sys_role_func` VALUES ('25', '-1211030', '-103');
INSERT INTO `sys_role_func` VALUES ('26', '-1212000', '-103');
INSERT INTO `sys_role_func` VALUES ('27', '-1213000', '-103');
INSERT INTO `sys_role_func` VALUES ('28', '-1213010', '-103');
INSERT INTO `sys_role_func` VALUES ('29', '-1213020', '-103');
INSERT INTO `sys_role_func` VALUES ('30', '-1213030', '-103');
INSERT INTO `sys_role_func` VALUES ('31', '-1214000', '-103');
INSERT INTO `sys_role_func` VALUES ('32', '-1214010', '-103');
INSERT INTO `sys_role_func` VALUES ('33', '-1214020', '-103');
INSERT INTO `sys_role_func` VALUES ('34', '-1214030', '-103');
INSERT INTO `sys_role_func` VALUES ('35', '-1220000', '-103');
INSERT INTO `sys_role_func` VALUES ('36', '-1220100', '-103');
INSERT INTO `sys_role_func` VALUES ('37', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('38', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('39', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('40', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('41', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('42', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('43', '-1220200', '-103');
INSERT INTO `sys_role_func` VALUES ('44', '-1220300', '-103');
INSERT INTO `sys_role_func` VALUES ('45', '-1230000', '-103');
INSERT INTO `sys_role_func` VALUES ('46', '-1230100', '-103');
INSERT INTO `sys_role_func` VALUES ('47', '-1230200', '-103');
INSERT INTO `sys_role_func` VALUES ('48', '-1230300', '-103');
INSERT INTO `sys_role_func` VALUES ('49', '-1230400', '-103');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `DEPT_ID` int(11) NOT NULL COMMENT '组织机构ID',
  `USER_NAME` varchar(100) NOT NULL COMMENT '用户名',
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
  `USER_PHOTO` mediumblob COMMENT '照片，Base64编码',
  `USER_TYPE` varchar(50) NOT NULL COMMENT '用户类型，字典项，NATURE=USER_TYPE',
  `IS_ENABLED` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效标记,1:有效,others:无效',
  `USER_REMARK` varchar(200) DEFAULT NULL,
  `LAST_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `LOGIN_ACCOUNT` (`LOGIN_ACCOUNT`),
  KEY `SYS_USER_LOGIN_ACCOUNT` (`LOGIN_ACCOUNT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('-2', '1', '超管', 'super', 'c4ca4238a0b923820dcc509a6f75849b', null, '2016-12-28', '13782781870', '010-88178831', '453099975@qq.com', null, '', null, '0', null, null, 'ADMIN', '1', '123', '2017-05-03 13:35:16');
INSERT INTO `sys_user` VALUES ('-1', '2', '匿名用户', 'Anonymous', 'c4ca4238a0b923820dcc509a6f75849b', '男', null, '13782781877', '8817882', '15936216273@163.com', null, null, null, null, null, null, 'ANONYMOUS', '1', null, null);

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `GROUP_ID` int(11) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (`REL_ID`),
  KEY `SYS_USER_GROUP_USER_ID` (`USER_ID`) USING BTREE,
  KEY `SYS_USER_GROUP_GROUP_ID` (`GROUP_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户，用户组关联表';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES ('1', '-2', '-101');
INSERT INTO `sys_user_group` VALUES ('2', '-2', '-102');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `REL_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联ID, 为辨识变更记录使用',
  `USER_ID` int(11) NOT NULL COMMENT '用户ID',
  `ROLE_ID` int(11) NOT NULL COMMENT '  角色ID',
  PRIMARY KEY (`REL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户，角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
