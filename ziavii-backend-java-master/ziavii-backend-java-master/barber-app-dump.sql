-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: fcc
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appoint_id` int NOT NULL AUTO_INCREMENT,
  `appointment_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  `slot` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` int NOT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`appoint_id`),
  KEY `FKmyowslj1th8d9j6j3wlbwrtoe` (`customer_id`),
  KEY `FKadokiiukw73cfb368mq108m9` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'2020-09-14 00:00:00','2020-09-13 23:35:15',NULL,'09:00','BOOKED',1,1),(2,'2020-09-14 00:00:00','2020-09-13 23:35:15',NULL,'09:15','BOOKED',1,1),(3,'2020-09-14 00:00:00','2020-09-13 23:35:15',NULL,'09:30','BOOKED',1,1);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `coupon_id` int NOT NULL AUTO_INCREMENT,
  `coupon_code` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `max_price` varchar(255) DEFAULT NULL,
  `percentage` varchar(255) DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  PRIMARY KEY (`coupon_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'FLAT10','2020-09-11 01:04:28','100','10','2020-09-16 01:04:28'),(2,'SEPT50','2020-09-11 01:04:48','100','50','2020-09-11 01:04:48'),(3,'SEPT20','2020-09-11 01:04:58','100','20','2020-09-16 01:04:58');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `otp_created_time` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  `latiude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `one_time_pass` varchar(255) DEFAULT NULL,
  `otp_verified` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'2020-09-05 14:11:00','2020-09-05 14:11:00','mohammad.siddiqui@srmtechsol.com',NULL,NULL,NULL,'9795203833','Shariq','1363',_binary '','112233','CUSTOMER');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_record`
--

DROP TABLE IF EXISTS `login_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login_record` (
  `login_record_id` int NOT NULL AUTO_INCREMENT,
  `device_token` varchar(255) NOT NULL,
  `device_type` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `login_date_time` datetime NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`login_record_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_record`
--

LOCK TABLES `login_record` WRITE;
/*!40000 ALTER TABLE `login_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opening_closing_time`
--

DROP TABLE IF EXISTS `opening_closing_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opening_closing_time` (
  `op_cl_id` int NOT NULL,
  `close_time` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `last_updated_at` datetime DEFAULT NULL,
  `open_time` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `week_day` varchar(255) DEFAULT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`op_cl_id`),
  KEY `FKfjshe6dww01r8xte86931clht` (`vendor_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opening_closing_time`
--

LOCK TABLES `opening_closing_time` WRITE;
/*!40000 ALTER TABLE `opening_closing_time` DISABLE KEYS */;
INSERT INTO `opening_closing_time` VALUES (1,'21:00','2020-09-05 13:51:26',NULL,'9:00','OPEN','Mon',1),(2,'21:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Tue',1),(3,'21:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Wed',1),(4,'20:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Thu',1),(5,'21:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Fri',1),(6,'21:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Sat',1),(7,'21:00','2020-09-05 13:51:27',NULL,'9:00','OPEN','Sun',1),(8,'20:00','2020-09-05 13:52:37',NULL,'9:00','OPEN','Mon',2),(9,'21:00','2020-09-05 13:52:37',NULL,'9:00','OPEN','Tue',2),(10,'21:00','2020-09-05 13:52:38',NULL,'9:00','OPEN','Wed',2),(11,'20:00','2020-09-05 13:52:38',NULL,'9:00','OPEN','Thu',2),(12,'21:00','2020-09-05 13:52:38',NULL,'9:00','OPEN','Fri',2),(13,'21:00','2020-09-05 13:52:38',NULL,'9:00','OPEN','Sat',2),(14,'','2020-09-05 13:52:38',NULL,'','CLOSE','Sun',2);
/*!40000 ALTER TABLE `opening_closing_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_media`
--

DROP TABLE IF EXISTS `user_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_media` (
  `media_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `is_profile_pic` bit(1) NOT NULL,
  `last_updated_at` datetime DEFAULT NULL,
  `media_extension` varchar(255) DEFAULT NULL,
  `media_name` varchar(255) DEFAULT NULL,
  `media_size` varchar(255) DEFAULT NULL,
  `media_type` varchar(255) DEFAULT NULL,
  `media_url` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `customer_id` int DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  PRIMARY KEY (`media_id`),
  KEY `FK23l1onvsm9e9qqriy35a40gep` (`customer_id`),
  KEY `FKed3awb2k44oe26hrhfa6gjrli` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_media`
--

LOCK TABLES `user_media` WRITE;
/*!40000 ALTER TABLE `user_media` DISABLE KEYS */;
INSERT INTO `user_media` VALUES (1,'2020-09-05 13:48:17',_binary '\0',NULL,NULL,'Asad-Picture1.PNG',NULL,NULL,'images/Asad-Picture1.PNG',_binary '',NULL,1),(2,'2020-09-05 13:48:17',_binary '\0',NULL,NULL,'Asad-Picture2.jpeg',NULL,NULL,'images/Asad-Picture2.jpeg',_binary '',NULL,1),(3,'2020-09-05 13:49:45',_binary '\0',NULL,NULL,'Muzain-Picture1.PNG',NULL,NULL,'images/Muzain-Picture1.PNG',_binary '',NULL,2),(4,'2020-09-05 13:49:45',_binary '\0',NULL,NULL,'Muzain-Picture2.jpeg',NULL,NULL,'images/Muzain-Picture2.jpeg',_binary '',NULL,2),(5,'2020-09-05 14:11:00',_binary '\0',NULL,NULL,'CustomerPicture.PNG',NULL,NULL,'images/CustomerPicture.PNG',_binary '',1,NULL);
/*!40000 ALTER TABLE `user_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor`
--

DROP TABLE IF EXISTS `vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor` (
  `vendor_id` int NOT NULL AUTO_INCREMENT,
  `otp_created_time` datetime DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  `latiude` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `one_time_pass` varchar(255) DEFAULT NULL,
  `otp_verified` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `register_step` varchar(255) DEFAULT NULL,
  `seats` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor`
--

LOCK TABLES `vendor` WRITE;
/*!40000 ALTER TABLE `vendor` DISABLE KEYS */;
INSERT INTO `vendor` VALUES (1,'2020-09-05 13:51:27','HazratGanj','2020-09-05 13:48:17','tempsid95@gmail.com',NULL,'26.846251','80.949028','9795203833','Asad','1189',_binary '','112266','2','1','VENDOR'),(2,'2020-09-05 13:52:38','Gomti Nagar','2020-09-05 13:49:45','shariq1926@outlook.com',NULL,'26.862337','81.019958','9795203833','Muzain','4158',_binary '','112266','2','2','VENDOR');
/*!40000 ALTER TABLE `vendor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor_ratings`
--

DROP TABLE IF EXISTS `vendor_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor_ratings` (
  `rate_id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `rating` bigint NOT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`rate_id`),
  KEY `FKiiwkslm67pt70if41yph3xu0o` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor_ratings`
--

LOCK TABLES `vendor_ratings` WRITE;
/*!40000 ALTER TABLE `vendor_ratings` DISABLE KEYS */;
INSERT INTO `vendor_ratings` VALUES (1,'2020-09-05 13:51:26',4,1),(2,'2020-09-05 13:51:26',5,1),(3,'2020-09-05 13:51:26',3,1),(4,'2020-09-05 13:51:26',2,2),(5,'2020-09-05 13:51:26',4,2),(6,'2020-09-05 13:51:26',2,2),(7,'2020-09-05 13:51:26',1,2),(8,NULL,5,2);
/*!40000 ALTER TABLE `vendor_ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendor_services`
--

DROP TABLE IF EXISTS `vendor_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendor_services` (
  `service_id` int NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  `service_price` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `time_in_min` varchar(255) DEFAULT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`service_id`),
  KEY `FKot2pvpu2y2gv4jqriuwt44pim` (`vendor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendor_services`
--

LOCK TABLES `vendor_services` WRITE;
/*!40000 ALTER TABLE `vendor_services` DISABLE KEYS */;
INSERT INTO `vendor_services` VALUES (1,'2020-09-05 13:51:26',NULL,'Hair cutting','120',_binary '','15',1),(2,'2020-09-05 13:51:26',NULL,'Facial','350',_binary '','30',1),(3,'2020-09-05 13:51:26',NULL,'Shaving','100',_binary '','15',1),(4,'2020-09-05 13:52:37',NULL,'Hair cutting','120',_binary '','15',2),(5,'2020-09-05 13:52:37',NULL,'Facial','350',_binary '','30',2),(6,'2020-09-05 13:52:37',NULL,'Shaving','100',_binary '','15',2),(7,'2020-09-05 13:52:37',NULL,'Massage','300',_binary '','15',2);
/*!40000 ALTER TABLE `vendor_services` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-19 14:58:22
