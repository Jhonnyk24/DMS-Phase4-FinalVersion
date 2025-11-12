-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: moviesdb
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `director` varchar(255) NOT NULL,
  `rating` double NOT NULL,
  `runtimeMinutes` int NOT NULL,
  `votes` int NOT NULL,
  `watched` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'The Exorcist',1973,'William Friedkin',8.1,122,350000,1),(2,'The Shining',1980,'Stanley Kubrick',8.4,146,950000,1),(3,'Halloween',1978,'John Carpenter',7.7,91,300000,1),(4,'Hereditary',2018,'Ari Aster',7.3,127,480000,0),(5,'The Conjuring',2013,'James Wan',7.5,112,550000,1),(6,'It',2017,'Andy Muschietti',7.4,135,600000,1),(7,'The Ring',2002,'Gore Verbinski',7.1,115,350000,1),(8,'Saw',2004,'James Wan',7.6,103,450000,1),(9,'Insidious',2010,'James Wan',6.8,103,330000,0),(10,'Sinister',2012,'Scott Derrickson',6.8,110,275000,1),(11,'Scream',1996,'Wes Craven',7.4,111,430000,1),(12,'The Babadook',2014,'Jennifer Kent',6.8,94,250000,0),(13,'Midsommar',2019,'Ari Aster',7.1,148,450000,0),(14,'The Witch',2015,'Robert Eggers',6.9,92,350000,0),(15,'Paranormal Activity',2007,'Oren Peli',6.3,86,290000,1),(16,'The Blair Witch Project',1999,'Daniel Myrick',6.5,81,250000,1),(17,'Get Out',2017,'Jordan Peele',7.7,104,700000,1),(18,'Us',2019,'Jordan Peele',6.8,116,260000,0),(19,'The Cabin in the Woods',2011,'Drew Goddard',7,95,400000,1),(20,'A Nightmare on Elm Street',1984,'Wes Craven',7.4,91,220000,1);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-05 18:00:40
