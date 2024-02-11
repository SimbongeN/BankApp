CREATE DATABASE  IF NOT EXISTS `bankapp_database` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bankapp_database`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: bankapp_database
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Dumping data for table `account_information`
--

LOCK TABLES `account_information` WRITE;
/*!40000 ALTER TABLE `account_information` DISABLE KEYS */;
INSERT INTO `account_information` VALUES ('user1',1000101,'deposited:2024-01-23:2500:1000101 | Transfered:2024-01-24:1900:1000103|deposited:2024-01-26:30.0:1000107|deposited:2024-01-26:5.0:1000107|deposited:2024-01-26:30.0:1000107|deposited:2024-01-26:40.0:1000107|deposited:2024-01-26:5.0:1000107|deposited:2024-01-26:2.0:1000107|Transfered:2024-01-26:20.0:1000107',57),('user2',1000102,'deposited:2024-01-23:1900:1000102| deposited:2024-01-25:200.0:1000103| deposited:2024-01-25:50.0:1000103|deposited:2024-01-26:50.0:1000103|deposited:2024-01-26:200.0:1000106',650),('user3 ',1000103,'Transfered:2024-01-25:50.0:10020344|Transfered:2024-01-23:400:1000103|Transfered:2024-01-23:900:1000103|deposited:2024-01-23:1700:1000103|Transfered:2023-12-24:250:1000103|Transfered:2023-12-28:100:1000103|Transfered:2023-12-24:1900:1000103|deposited:2023-12-23:3700:1000103|Transfered:2023-11-25:50.0:10020344|Transfered:2023-11-25:50.0:30021044|Transfered:2023-11-25:50.0:185902730|Transfered:2023-11-25:50.0:18057230|Transfered:2024-01-28:400.0:1000110|Transfered:2024-02-09:200.0:1000111|Transfered:2024-02-10:30.0:145003992',370),('user5',1000106,'deposited:2024-01-26:500.0:1000103|deposited:2024-01-26:40.0:1000103|deposited:2024-01-26:500.0:1000103|deposited:2024-01-26:40.0:1000103|Transfered:2024-01-26:200.0:1000102',200),('user6',1000107,'null|deposited:2024-01-26:20.0:1000101',23),('user10',1000108,'null|deposited:2024-01-27:20.0:1000109',280),('user4',1000109,'Transfered:2024-01-27:20.0:1000108',30),('sizo',1000110,'Transfered:2024-01-28:20.0:18059270|deposited:2024-01-28:400.0:1000103',430),('user20',1000111,'deposited:2024-02-09:200.0:1000103|Transfered:2024-02-09:20.0:120464490',230);
/*!40000 ALTER TABLE `account_information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `userinformation`
--

LOCK TABLES `userinformation` WRITE;
/*!40000 ALTER TABLE `userinformation` DISABLE KEYS */;
INSERT INTO `userinformation` VALUES (1,'user1','user@gmail.com',1000101,'user1Pass'),(2,'user2','user2@gmail.com',1000102,'user2Pass'),(3,'user3','user3@gmail.com',1000103,'user3Pass'),(6,'user5','user5@gmail.com',1000106,'user5Pass'),(7,'user6','user6@gmail.com',1000107,'user6Pass'),(8,'user10','user10@gmail.com',1000108,'user10Pass'),(9,'user4','user4@gmail.com',1000109,'user4Pass'),(10,'sizo','sizo@gmail.com',1000110,'sizoUknow'),(11,'user20','user20@gmail.com',1000111,'user20Pass');
/*!40000 ALTER TABLE `userinformation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-11 22:17:25
