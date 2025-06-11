-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: ID429845_g82
-- ------------------------------------------------------
-- Server version	8.0.36-28

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
-- Table structure for table `Speler`
--

DROP TABLE IF EXISTS `Speler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Speler` (
  `gebruikersnaam` varchar(50) NOT NULL,
  `geboortejaar` int NOT NULL,
  `aantalgewonnen` int NOT NULL,
  `aantalGespeeld` int NOT NULL,
  PRIMARY KEY (`gebruikersnaam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Speler`
--

LOCK TABLES `Speler` WRITE;
/*!40000 ALTER TABLE `Speler` DISABLE KEYS */;
INSERT INTO `Speler` VALUES ('53151313',2005,0,0),('Frosty',2002,81,243),('Frosty2',2005,1,3),('loldle',2006,0,0),('lollll',2014,0,0);
/*!40000 ALTER TABLE `Speler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tegels`
--

DROP TABLE IF EXISTS `Tegels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Tegels` (
  `idTegels` int NOT NULL,
  `AantalKronen` int DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `nummerAchterkant` int DEFAULT NULL,
  PRIMARY KEY (`idTegels`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tegels`
--

LOCK TABLES `Tegels` WRITE;
/*!40000 ALTER TABLE `Tegels` DISABLE KEYS */;
INSERT INTO `Tegels` VALUES (1,0,'Zand',1),(2,0,'Zand',1),(3,0,'Zand',2),(4,0,'Zand',2),(5,0,'Bos',3),(6,0,'Bos',3),(7,0,'Bos',4),(8,0,'Bos',4),(9,0,'Bos',5),(10,0,'Bos',5),(11,0,'Bos',6),(12,0,'Bos',6),(13,0,'Water',7),(14,0,'Water',7),(15,0,'Water',8),(16,0,'Water',8),(17,0,'Water',9),(18,0,'Water',9),(19,0,'Gras',10),(20,0,'Gras',10),(21,0,'Gras',11),(22,0,'Gras',11),(23,0,'Aarde',12),(24,0,'Aarde',12),(25,0,'Zand',13),(26,0,'Bos',13),(27,0,'Zand',14),(28,0,'Water',14),(29,0,'Zand',15),(30,0,'Gras',15),(31,0,'Zand',16),(32,0,'Aarde',16),(33,0,'Bos',17),(34,0,'Water',17),(35,0,'Bos',18),(36,0,'Gras',18),(37,1,'Zand',19),(38,0,'Water',19),(39,1,'Zand',20),(40,0,'Bos',20),(41,1,'Zand',21),(42,0,'Gras',21),(43,1,'Zand',22),(44,0,'Aarde',22),(45,1,'Zand',23),(46,0,'Mijn',23),(47,1,'Bos',24),(48,0,'Zand',24),(49,1,'Bos',25),(50,0,'Zand',25),(51,1,'Bos',26),(52,0,'Zand',26),(53,1,'Bos',27),(54,0,'Zand',27),(55,1,'Bos',28),(56,0,'Water',28),(57,1,'Bos',29),(58,0,'Gras',29),(59,1,'Water',30),(60,0,'Zand',30),(61,1,'Water',31),(62,0,'Zand',31),(63,1,'Water',32),(64,0,'Bos',32),(65,1,'Water',33),(66,0,'Bos',33),(67,1,'Water',34),(68,0,'Bos',34),(69,1,'Water',35),(70,0,'Bos',35),(71,0,'Zand',36),(72,1,'Gras',36),(73,0,'Water',37),(74,1,'Gras',37),(75,0,'Zand',38),(76,1,'Aarde',38),(77,0,'Gras',39),(78,1,'Aarde',39),(79,1,'Mijn',40),(80,0,'Zand',40),(81,0,'Zand',41),(82,2,'Gras',41),(83,0,'Water',42),(84,2,'Gras',42),(85,0,'Zand',43),(86,2,'Aarde',43),(87,0,'Gras',44),(88,2,'Aarde',44),(89,2,'Mijn',45),(90,0,'Zand',45),(91,0,'Aarde',46),(92,2,'Mijn',46),(93,0,'Aarde',47),(94,2,'Mijn',47),(95,0,'Zand',48),(96,3,'Mijn',48);
/*!40000 ALTER TABLE `Tegels` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-12 15:42:00
