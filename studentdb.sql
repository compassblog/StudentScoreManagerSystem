/*
Navicat MySQL Data Transfer

Source Server         : studyMySQL
Source Server Version : 50173
Source Host           : localhost:3306
Source Database       : studentdb

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-06-22 22:04:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for loginusers
-- ----------------------------
DROP TABLE IF EXISTS `loginusers`;
CREATE TABLE `loginusers` (
  `adminid` int(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of loginusers
-- ----------------------------
INSERT INTO `loginusers` VALUES ('1', 'admin', 'admin');
INSERT INTO `loginusers` VALUES ('2', 'kth', '123');

-- ----------------------------
-- Table structure for studentmanager
-- ----------------------------
DROP TABLE IF EXISTS `studentmanager`;
CREATE TABLE `studentmanager` (
  `ID` varchar(255) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `数学` int(255) DEFAULT NULL,
  `英语` int(255) DEFAULT NULL,
  `语文` int(255) DEFAULT NULL,
  `物理` int(255) DEFAULT NULL,
  `生物` int(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studentmanager
-- ----------------------------
INSERT INTO `studentmanager` VALUES ('1234', 'hello', '0', '0', '0', '0', '0');
INSERT INTO `studentmanager` VALUES ('2017031', 'student1', '100', '100', '33', '33', '33');
INSERT INTO `studentmanager` VALUES ('2017032', '孔潭活', '66', '100', '66', '66', '66');
INSERT INTO `studentmanager` VALUES ('2017033', 'student2', '55', '80', '100', '55', '55');
INSERT INTO `studentmanager` VALUES ('2017034', 'student3', '99', '80', '100', '100', '44');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `userid` varchar(255) NOT NULL DEFAULT '0',
  `username` varchar(255) DEFAULT NULL,
  `userpwd` varchar(255) DEFAULT NULL,
  `sexy` varchar(255) DEFAULT NULL,
  `classgrade` varchar(255) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  `type` int(255) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1200101', 'teacher1', '1200101', '男', '1班', '教师', '1');
INSERT INTO `users` VALUES ('1200102', 'teacher2', '1200102', '女', '2班', '教师', '0');
INSERT INTO `users` VALUES ('1200103', 'teacher3', '1200103', '女', '3班', '教师', '0');
INSERT INTO `users` VALUES ('1234', 'hello', '123456', '男', '2班', '学生', '0');
INSERT INTO `users` VALUES ('2017031', 'student1', '2017031', '男', '1班', '学生', '0');
INSERT INTO `users` VALUES ('2017032', '孔潭活', '2017032', '女', '1班', '学生', '0');
INSERT INTO `users` VALUES ('2017033', 'student2', '2017033', '女', '2班', '学生', '0');
INSERT INTO `users` VALUES ('2017034', 'student3', '2017034', '女', '3班', '学生', '0');
