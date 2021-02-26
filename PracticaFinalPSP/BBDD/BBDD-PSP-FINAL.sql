CREATE DATABASE  IF NOT EXISTS `bd_finalpsp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bd_finalpsp`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_finalpsp
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `ID_Compra` int NOT NULL AUTO_INCREMENT,
  `Fecha` date NOT NULL,
  `ID_Empleado` int NOT NULL,
  `Caja` int NOT NULL,
  PRIMARY KEY (`ID_Compra`,`ID_Empleado`),
  KEY `fk_compra_Empleado1_idx` (`ID_Empleado`),
  CONSTRAINT `fk_compra_Empleado1` FOREIGN KEY (`ID_Empleado`) REFERENCES `empleado` (`ID_Empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
INSERT INTO `compra` VALUES (6,'2021-02-23',1,1),(7,'2021-02-23',1,1),(8,'2021-02-23',1,1),(9,'2021-02-23',1,1),(10,'2021-02-23',1,1),(11,'2021-02-23',1,1),(12,'2021-02-23',1,1),(13,'2021-02-23',1,1),(14,'2021-02-23',1,1),(15,'2021-02-23',1,1),(16,'2021-02-23',1,1),(17,'2021-02-23',1,1),(18,'2021-02-23',1,1),(19,'2021-02-23',1,1),(20,'2021-02-23',1,1),(21,'2021-02-23',1,1),(22,'2021-02-23',1,1),(23,'2021-02-23',1,1),(24,'2021-02-23',1,1),(25,'2021-02-23',1,1),(26,'2021-02-23',1,1),(27,'2021-02-23',1,1),(28,'2021-02-23',1,1),(29,'2021-02-23',1,1),(30,'2021-02-23',1,1),(31,'2021-02-23',1,1),(32,'2021-02-23',1,1),(33,'2021-02-23',1,1),(34,'2021-02-23',1,1),(35,'2021-02-23',1,2),(36,'2021-02-23',1,2),(37,'2021-02-24',1,2),(38,'2021-02-24',1,2);
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `ID_Empleado` int NOT NULL AUTO_INCREMENT,
  `Ultima_Sesion` datetime DEFAULT NULL,
  `Fecha_Contratacion` date NOT NULL,
  PRIMARY KEY (`ID_Empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,'2021-02-24 00:00:00','1994-03-15');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `ID_Producto` int NOT NULL,
  `ID_Compra` int NOT NULL,
  `Cantidad_Producto` int NOT NULL,
  PRIMARY KEY (`ID_Producto`,`ID_Compra`),
  KEY `fk_producto_has_compra_compra1_idx` (`ID_Compra`),
  KEY `fk_producto_has_compra_producto_idx` (`ID_Producto`),
  CONSTRAINT `fk_producto_has_compra_compra1` FOREIGN KEY (`ID_Compra`) REFERENCES `compra` (`ID_Compra`),
  CONSTRAINT `fk_producto_has_compra_producto` FOREIGN KEY (`ID_Producto`) REFERENCES `producto` (`ID_Producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,6,12),(1,8,2),(1,9,11),(1,10,12),(1,11,12),(1,12,12),(1,13,12),(1,14,12),(1,15,12),(1,16,12),(1,17,12),(1,18,12),(1,19,12),(1,20,12),(1,21,12),(1,22,12),(1,24,12),(1,25,121),(1,26,12),(1,27,12),(1,29,12121),(1,30,12),(1,31,12),(1,33,12),(1,34,12),(1,37,15),(1,38,500),(2,7,12),(2,23,1),(2,28,12),(2,32,500),(2,35,5600),(4,36,154);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `ID_Producto` int NOT NULL AUTO_INCREMENT,
  `Nombre_Producto` varchar(45) NOT NULL,
  `Precio_Venta` int NOT NULL,
  `Precio_Proveedor` int NOT NULL,
  `Cantidad_Stock` int NOT NULL,
  PRIMARY KEY (`ID_Producto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Hamburguesa',5,2,-440),(2,'Costillas',15,8,75),(3,'Croquetas',3,1,80),(4,'Lasaña',8,4,120);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bd_finalpsp'
--

--
-- Dumping routines for database 'bd_finalpsp'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-26 14:01:10
