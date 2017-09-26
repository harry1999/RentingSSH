-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: renting-ssh
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (120),(120);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company` (
  `id` int(11) NOT NULL,
  `cellphone` varchar(11) NOT NULL,
  `contact` varchar(20) DEFAULT NULL,
  `hotLine` varchar(13) DEFAULT NULL,
  `innerNo` varchar(4) DEFAULT NULL,
  `isVerify` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r99lfqfbjul3wm3nw9dwh4ypg` (`innerNo`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--

LOCK TABLES `tb_company` WRITE;
/*!40000 ALTER TABLE `tb_company` DISABLE KEYS */;
INSERT INTO `tb_company` VALUES (1,'13122223333','tom','010-11112222','1001',1,'北京自如房地产经纪公司'),(92,'13122223333','tom','010-11112222','1002',1,'北京自如房地产经纪公司');
/*!40000 ALTER TABLE `tb_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_department`
--

DROP TABLE IF EXISTS `tb_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_department` (
  `deptno` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `isValid` int(1) NOT NULL DEFAULT '1',
  `parentId` int(11) DEFAULT '0',
  PRIMARY KEY (`deptno`),
  KEY `FKcoov3kf5h833rd8ee66la870y` (`parentId`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_department`
--

LOCK TABLES `tb_department` WRITE;
/*!40000 ALTER TABLE `tb_department` DISABLE KEYS */;
INSERT INTO `tb_department` VALUES (2,'业务部',NULL,1,1),(3,'业务部深圳分部',NULL,1,2),(4,'业务部广州分部',NULL,1,2),(5,'研发部',NULL,1,1),(6,'非内部员工',NULL,1,1),(1,'总经办',NULL,1,NULL);
/*!40000 ALTER TABLE `tb_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_district`
--

DROP TABLE IF EXISTS `tb_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_district`
--

LOCK TABLES `tb_district` WRITE;
/*!40000 ALTER TABLE `tb_district` DISABLE KEYS */;
INSERT INTO `tb_district` VALUES (1,'海淀区'),(2,'朝阳区'),(3,'丰台区');
/*!40000 ALTER TABLE `tb_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_house`
--

DROP TABLE IF EXISTS `tb_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `area` double NOT NULL,
  `price` double NOT NULL,
  `tel` varchar(11) NOT NULL,
  `issueDate` datetime DEFAULT NULL,
  `isValid` int(1) NOT NULL DEFAULT '1',
  `description` varchar(200) DEFAULT '暂时没有关于该房屋的信息',
  `userId` int(11) NOT NULL,
  `subDistrictId` int(11) NOT NULL,
  `houseTypeId` int(11) NOT NULL,
  `bookingUserId` int(11) DEFAULT '0',
  `issued` int(1) DEFAULT '0',
  `deposit` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9bd3ol491wivrqm6cv2wfmg25` (`subDistrictId`),
  KEY `FKp3s1nhhcn9pxikpmahhk4bbr` (`houseTypeId`),
  KEY `FKdmqd5c7u7a7m9lmfjal78sear` (`userId`),
  KEY `FK5oj5cp14vt7k70nhbkk911sew` (`bookingUserId`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_house`
--

LOCK TABLES `tb_house` WRITE;
/*!40000 ALTER TABLE `tb_house` DISABLE KEYS */;
INSERT INTO `tb_house` VALUES (38,'丽泽景园 崭新两居出租 精致装修 随时看房 此房业主长期出租',90,5600,'15966665555','2017-09-26 03:00:13',1,'',34,6,4,35,1,800),(37,'定福庄 传媒大学地铁口 福慧嘉苑 正规次卧出租 3家合租',18,1900,'13155556666','2017-09-26 02:54:31',1,'',34,12,1,0,1,300),(33,'苏州桥 北外理工厂洼小区 正规两居 家具家电齐全',54,6500,'15933333333','2017-09-26 02:34:32',1,'',34,8,3,0,1,800),(36,'望京 来广营 孙河康营家园精装一居 位置好干净整洁拎包入住.',60,4500,'15988886666','2017-09-26 02:49:59',1,'',34,11,1,0,1,500),(35,'自如友家 品牌家居 全新装修 拎包入住 一千米车道沟站',51,5600,'18822223333','2017-09-26 02:45:27',1,'',34,10,1,0,1,600),(34,'立方庭户型 安静舒适 中关村商圈 交通便利',86,7500,'13866665555','2017-09-26 02:40:13',1,'',34,9,1,0,1,1000),(32,'临近地铁 上地 永丰 中关村  颐和园 西北旺 马连洼',17,1500,'13025555555','2017-09-26 02:01:40',1,'',34,7,3,0,1,200);
/*!40000 ALTER TABLE `tb_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_house_image`
--

DROP TABLE IF EXISTS `tb_house_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_house_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `houseId` int(11) DEFAULT NULL,
  `imageId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcgd8g9uf7r0dpkvdqupwrmff` (`imageId`),
  KEY `FKs502fgbdkd4kes0w73fgj6lbd` (`houseId`)
) ENGINE=MyISAM AUTO_INCREMENT=115 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_house_image`
--

LOCK TABLES `tb_house_image` WRITE;
/*!40000 ALTER TABLE `tb_house_image` DISABLE KEYS */;
INSERT INTO `tb_house_image` VALUES (93,'main',32,98),(94,'detail',32,99),(95,'detail',32,100),(96,'detail',33,101),(97,'main',33,102),(98,'detail',33,103),(99,'main',34,104),(100,'detail',34,105),(101,'detail',34,106),(102,'detail',34,107),(103,'main',35,108),(104,'detail',35,109),(105,'detail',35,110),(106,'detail',36,111),(107,'main',36,112),(108,'detail',36,113),(109,'main',37,114),(110,'detail',37,115),(111,'detail',37,116),(112,'detail',38,117),(113,'detail',38,118),(114,'main',38,119);
/*!40000 ALTER TABLE `tb_house_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_house_type`
--

DROP TABLE IF EXISTS `tb_house_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_house_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_house_type`
--

LOCK TABLES `tb_house_type` WRITE;
/*!40000 ALTER TABLE `tb_house_type` DISABLE KEYS */;
INSERT INTO `tb_house_type` VALUES (1,'一室一厅',NULL),(2,'一室两厅',NULL),(3,'两室一厅',NULL),(4,'两室两厅',NULL);
/*!40000 ALTER TABLE `tb_house_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_image`
--

DROP TABLE IF EXISTS `tb_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_image` (
  `id` int(11) NOT NULL,
  `imagePath` varchar(255) NOT NULL,
  `isValid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_image`
--

LOCK TABLES `tb_image` WRITE;
/*!40000 ALTER TABLE `tb_image` DISABLE KEYS */;
INSERT INTO `tb_image` VALUES (119,'1506394812992-main.jpg',1),(118,'1506394813005-detail.jpg',1),(117,'1506394812999-detail.jpg',1),(116,'1506394470522-detail.jpg',1),(115,'1506394470529-detail.jpg',1),(114,'1506394470512-main.jpg',1),(113,'1506394199371-detail.jpg',1),(112,'1506394199358-main.jpg',1),(111,'1506394199365-detail.jpg',1),(110,'1506393927283-detail.jpg',1),(109,'1506393927276-detail.jpg',1),(108,'1506393927269-main.jpg',1),(107,'1506393613120-detail.jpg',1),(106,'1506393613127-detail.jpg',1),(105,'1506393613133-detail.jpg',1),(104,'1506393613113-main.jpg',1),(103,'1506393272231-detail.jpg',1),(102,'1506393272211-main.jpg',1),(101,'1506393272224-detail.jpg',1),(100,'1506391300179-detail.jpg',1),(99,'1506391300172-detail.jpg',1),(98,'1506391300120-main.jpg',1);
/*!40000 ALTER TABLE `tb_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_order`
--

DROP TABLE IF EXISTS `tb_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookingUserId` int(11) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `houseId` int(11) NOT NULL,
  `createDate` datetime NOT NULL,
  `dealDate` datetime DEFAULT NULL,
  `isValid` int(1) NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKam9aouncflh5hg1aulugb65mr` (`bookingUserId`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_order`
--

LOCK TABLES `tb_order` WRITE;
/*!40000 ALTER TABLE `tb_order` DISABLE KEYS */;
INSERT INTO `tb_order` VALUES (72,35,34,38,'2017-09-26 03:01:09','2017-09-26 03:01:11',1,2);
/*!40000 ALTER TABLE `tb_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_order_detail`
--

DROP TABLE IF EXISTS `tb_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_order_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `bookingUserName` varchar(50) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `area` double NOT NULL,
  `price` double NOT NULL,
  `deposit` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK40jtwdcjsgdf31n51gj9kukg7` (`orderId`)
) ENGINE=MyISAM AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_order_detail`
--

LOCK TABLES `tb_order_detail` WRITE;
/*!40000 ALTER TABLE `tb_order_detail` DISABLE KEYS */;
INSERT INTO `tb_order_detail` VALUES (70,72,'henry','jack','丽泽景园 崭新两居出租 精致装修 随时看房 此房业主长期出租',90,5600,800);
/*!40000 ALTER TABLE `tb_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_privilege`
--

DROP TABLE IF EXISTS `tb_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_privilege` (
  `privilegeId` int(11) NOT NULL AUTO_INCREMENT,
  `privilegeName` varchar(100) NOT NULL,
  `privilegePath` varchar(200) DEFAULT NULL,
  `parentId` int(11) DEFAULT '0',
  `isValid` int(1) NOT NULL DEFAULT '1',
  `privilegeDesc` varchar(200) DEFAULT NULL,
  `isMenu` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`privilegeId`),
  KEY `FKrsej1b1wpx36vfgkehml7rqec` (`parentId`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_privilege`
--

LOCK TABLES `tb_privilege` WRITE;
/*!40000 ALTER TABLE `tb_privilege` DISABLE KEYS */;
INSERT INTO `tb_privilege` VALUES (1,'用户管理','/user/userManagement',0,1,NULL,1),(2,'房屋信息管理','/common/house',0,1,NULL,1),(3,'新建用户','/user/add',1,1,NULL,0),(4,'修改用户信息','/user/update',1,1,NULL,0),(5,'删除用户','/user/delete',1,1,NULL,0),(6,'权限设置','/common/privilegeSetting',0,1,NULL,1),(7,'权限管理','/common/privilege',6,1,NULL,1),(8,'新增权限','/common/addPrivilege',7,1,NULL,0),(9,'修改权限信息','/common/updatePrivilege',7,1,NULL,0),(10,'删除权限','/common/delPrivilege',7,1,NULL,0),(11,'角色管理','/common/role',6,1,NULL,1),(12,'新增角色','/common/createRole',11,1,NULL,0),(13,'修改角色信息','/common/updateRole',11,1,NULL,0),(14,'删除角色','/common/delRoles',11,1,NULL,0),(15,'新建房屋信息','/common/createHouse',2,1,NULL,0),(16,'修改房屋信息','/common/updateHouse',2,1,NULL,0),(17,'发布房屋信息','/common/issueHouses',2,1,NULL,0),(18,'下架房屋信息','/common/unShelveHouses',2,1,NULL,0),(19,'删除房屋信息','common/delHouses',2,1,NULL,0),(20,'数据库备份','/common/dbBackup',0,1,NULL,1),(21,'订单管理','/common/order',0,1,NULL,1),(22,'访问个人中心','/user/me',0,1,NULL,0),(23,'删除订单','/user/deleteOrder',0,1,NULL,0);
/*!40000 ALTER TABLE `tb_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(20) NOT NULL,
  `roleDesc` varchar(200) DEFAULT NULL,
  `isValid` int(1) NOT NULL DEFAULT '1',
  `parentId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKapmfb7r5lkpixbnxyw2bokp5o` (`parentId`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES (1,'admin','超级管理员',1,0),(2,'出租人','出租人',1,1),(3,'普通用户','普通用户',1,1);
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_privilege`
--

DROP TABLE IF EXISTS `tb_role_privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(100) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL,
  `privilegeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8abnqki8pluyuoacf7ih04p4` (`roleId`),
  KEY `FKpaqk08knsq0nda8j3t2jehs24` (`privilegeId`)
) ENGINE=MyISAM AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_privilege`
--

LOCK TABLES `tb_role_privilege` WRITE;
/*!40000 ALTER TABLE `tb_role_privilege` DISABLE KEYS */;
INSERT INTO `tb_role_privilege` VALUES (1,NULL,1,1),(2,NULL,1,2),(3,NULL,1,3),(4,NULL,1,4),(5,NULL,1,5),(6,NULL,1,6),(7,NULL,1,7),(8,NULL,1,8),(9,NULL,1,9),(10,NULL,1,10),(11,NULL,1,11),(12,NULL,1,12),(13,NULL,1,13),(14,NULL,1,14),(15,NULL,1,15),(16,NULL,1,16),(17,NULL,1,17),(18,NULL,1,18),(19,NULL,1,19),(20,NULL,1,20),(21,NULL,1,21),(105,NULL,2,2),(106,NULL,2,15),(107,NULL,2,16),(108,NULL,2,17),(109,NULL,2,18),(110,NULL,2,19),(115,NULL,3,22),(116,NULL,3,23);
/*!40000 ALTER TABLE `tb_role_privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sub_district`
--

DROP TABLE IF EXISTS `tb_sub_district`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_sub_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `districtId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4luvx2mmp9ahug1h5bgi4qvap` (`districtId`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sub_district`
--

LOCK TABLES `tb_sub_district` WRITE;
/*!40000 ALTER TABLE `tb_sub_district` DISABLE KEYS */;
INSERT INTO `tb_sub_district` VALUES (1,'中关村大街',1),(2,'知春路',1),(3,'学院路',1),(4,'朝阳路',2),(5,'中山路',2),(6,'七里庄',3),(7,'马连洼',1),(8,'车道沟',1),(9,'苏州桥',1),(10,'世纪城',1),(11,'来广营',2),(12,'定福庄',2);
/*!40000 ALTER TABLE `tb_sub_district` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `telephone` varchar(20) NOT NULL,
  `realname` varchar(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `isValid` int(1) NOT NULL DEFAULT '1',
  `roleId` int(11) NOT NULL,
  `deptno` int(11) NOT NULL,
  `point` int(8) DEFAULT NULL,
  `grade` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `FKg21iho4tmq4f29bqt5nnhh43i` (`deptno`),
  KEY `FKvq039bkvpokq1krmcjuccnhm` (`roleId`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'harry','MTExMTF4MXkyejM=','13922293333','harry','2017-08-23 03:25:21',1,1,1,0,NULL),(34,'henry','MTExMTF4MXkyejM=','13922693333','henry','2017-09-04 03:43:16',1,2,6,0,NULL),(35,'jack','MTExMTF4MXkyejM=','13922283333','jack','2017-09-04 04:12:26',1,3,6,500,'青铜会员');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-26 16:32:42
