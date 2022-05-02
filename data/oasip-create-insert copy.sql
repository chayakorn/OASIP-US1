CREATE DATABASE
IF NOT EXISTS `oasip` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oasip`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: oasip
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `eventbooking`
--

DROP TABLE IF EXISTS `eventbooking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventbooking`
(
  `bookingId` int NOT NULL AUTO_INCREMENT,
  `eventCategoryId` int NOT NULL,
  `bookingName` varchar
(100) NOT NULL,
  `bookingEmail` varchar
(100) NOT NULL,
  `eventStartTime` datetime NOT NULL,
  `eventDuration` int NOT NULL,
  `eventNotes` varchar
(500) DEFAULT NULL,
  `name` varchar
(200) DEFAULT NULL,
  PRIMARY KEY
(`bookingId`),
  KEY `fk_event_eventCategory_idx`
(`eventCategoryId`),
  CONSTRAINT `fk_event_eventCategory` FOREIGN KEY
(`eventCategoryId`) REFERENCES `eventcategory`
(`eventCategoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventbooking`
--

LOCK TABLES `eventbooking` WRITE;
/*!40000 ALTER TABLE `eventbooking` DISABLE KEYS */;
/*!40000 ALTER TABLE `eventbooking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventcategory`
--

DROP TABLE IF EXISTS `eventcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventcategory`
(
  `eventCategoryId` int NOT NULL,
  `eventCategoryName` varchar
(100) NOT NULL,
  `eventCategoryDescription` varchar
(500) DEFAULT NULL,
  `eventDuration` int NOT NULL,
  `image` varchar
(100) DEFAULT NULL,
  PRIMARY KEY
(`eventCategoryId`),
  UNIQUE KEY `eventCategoryName_UNIQUE`
(`eventCategoryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventcategory`
--

LOCK TABLES `eventcategory` WRITE;
/*!40000 ALTER TABLE `eventcategory` DISABLE KEYS */;
INSERT INTO `
eventcategory`
VALUES
  (1, 'Project Management Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา', 30, './images/project.png'),
  (2, 'DevOps/Infra Clinic', 'Use this event category for DevOps/Infra clinic.', 20, './images/devops.png'),
  (3, 'Database Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I', 15, './images/database.png'),
  (4, 'Client-side Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I', 30, './images/frontend.png'),
  (5, 'Server-side Clinic', '', 30, './images/backend.png');
/*!40000 ALTER TABLE `eventcategory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

create user 'us1'@'%' identified by 'us-1-t2p';
grant all on *.* to 'us1'@'%';
-- Dump completed on 2022-04-27 17:56:20
