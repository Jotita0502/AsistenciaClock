CREATE DATABASE  IF NOT EXISTS `asistenciagm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `asistenciagm`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: asistenciagm
-- ------------------------------------------------------
-- Server version	8.0.45

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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `workspace_id` int NOT NULL,
  `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `archivado` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_cl_ws` (`workspace_id`),
  CONSTRAINT `fk_cl_ws` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,2,'Cliente General','cliente@gmail.com','Lima',0,'2026-05-22 18:53:32','2026-05-22 18:53:32');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etiquetas`
--

DROP TABLE IF EXISTS `etiquetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etiquetas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `workspace_id` int NOT NULL,
  `nombre` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `color` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '#1D9E75',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_et_nombre` (`workspace_id`,`nombre`),
  CONSTRAINT `fk_et_ws` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etiquetas`
--

LOCK TABLES `etiquetas` WRITE;
/*!40000 ALTER TABLE `etiquetas` DISABLE KEYS */;
INSERT INTO `etiquetas` VALUES (3,2,'Backend','#FF5733'),(4,2,'Frontend','#FF5733'),(8,2,'Tag PUT Actualizado','#0000FF'),(10,9,'GM PROYECTO','#1D9E75'),(13,15,'DAS','#1D9E75'),(15,12,'dasd','#1D9E75');
/*!40000 ALTER TABLE `etiquetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyectos`
--

DROP TABLE IF EXISTS `proyectos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyectos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `workspace_id` int NOT NULL,
  `cliente_id` int DEFAULT NULL,
  `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `color` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '#3B8BD4',
  `billable` tinyint(1) NOT NULL DEFAULT '0',
  `archivado` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_pr_cliente` (`cliente_id`),
  KEY `idx_pr_ws` (`workspace_id`),
  CONSTRAINT `fk_pr_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_pr_ws` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyectos`
--

LOCK TABLES `proyectos` WRITE;
/*!40000 ALTER TABLE `proyectos` DISABLE KEYS */;
INSERT INTO `proyectos` VALUES (2,2,NULL,'Sistema joaquin','#0000FF',1,1,'2026-05-16 17:29:29','2026-05-22 18:50:42'),(12,2,1,'Proyecto backend angular','#0000FF',0,1,'2026-05-22 18:54:19','2026-06-02 13:22:49'),(14,2,1,'Sistema ERP Actualizado','#0000FF',0,1,'2026-05-23 00:00:28','2026-05-23 00:02:25'),(15,2,1,'Sistema de comida','#FF5733',1,0,'2026-05-25 17:38:11','2026-05-25 17:38:11'),(16,2,1,'Sistema de comida','#FF5733',1,1,'2026-05-25 17:38:53','2026-05-25 18:06:07'),(17,2,1,'Sitema de nutrucion Actualizado','#0000FF',0,0,'2026-05-25 17:39:22','2026-05-26 14:59:58'),(18,2,1,'Sistema de juego','#FF5733',1,0,'2026-05-25 17:40:16','2026-05-25 17:40:16'),(19,2,1,'GM PROYECTO','#87c936',0,0,'2026-05-25 17:40:46','2026-06-04 14:18:34'),(20,2,1,'Sistema de manejo','#FF5733',1,0,'2026-05-25 18:01:40','2026-05-25 18:01:40'),(23,2,1,'Proyecto Validaciones','#123ABC',1,0,'2026-05-27 13:56:31','2026-05-27 13:56:31'),(24,9,1,'Proyecto Front Angular','#33ffc2',1,1,'2026-06-02 13:17:23','2026-06-04 14:18:22'),(25,12,1,'MANAGER','#ff0000',0,0,'2026-06-02 13:27:19','2026-06-05 12:12:13'),(26,9,1,'D','#FF5733',1,0,'2026-06-02 13:28:19','2026-06-05 13:32:41'),(27,2,1,'Proyecto Postman','#123ABC',1,1,'2026-06-02 13:33:12','2026-06-02 13:55:07'),(28,2,1,'Proyecto Postman','#123ABC',1,0,'2026-06-02 13:33:30','2026-06-02 13:33:30'),(29,2,1,'Proyecto Postman','#123ABC',1,1,'2026-06-02 13:33:41','2026-06-02 13:45:38'),(31,15,1,'BACKEND dasd','#FF5733',0,0,'2026-06-04 14:17:39','2026-06-05 13:32:40'),(32,15,1,'Joaquin ricardo','#FF5733',1,0,'2026-06-05 12:11:44','2026-06-05 12:11:44'),(33,9,1,'dads','#FF5733',1,0,'2026-06-05 13:27:57','2026-06-05 13:27:57');
/*!40000 ALTER TABLE `proyectos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_etiquetas`
--

DROP TABLE IF EXISTS `registro_etiquetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_etiquetas` (
  `registro_id` int NOT NULL,
  `etiqueta_id` int NOT NULL,
  PRIMARY KEY (`registro_id`,`etiqueta_id`),
  KEY `fk_re_etq` (`etiqueta_id`),
  CONSTRAINT `fk_re_etq` FOREIGN KEY (`etiqueta_id`) REFERENCES `etiquetas` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_re_reg` FOREIGN KEY (`registro_id`) REFERENCES `registros_tiempo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_etiquetas`
--

LOCK TABLES `registro_etiquetas` WRITE;
/*!40000 ALTER TABLE `registro_etiquetas` DISABLE KEYS */;
INSERT INTO `registro_etiquetas` VALUES (12,3);
/*!40000 ALTER TABLE `registro_etiquetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registros_tiempo`
--

DROP TABLE IF EXISTS `registros_tiempo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registros_tiempo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `workspace_id` int NOT NULL,
  `proyecto_id` int DEFAULT NULL,
  `tarea_id` int DEFAULT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `inicio` datetime NOT NULL,
  `fin` datetime DEFAULT NULL,
  `duracion_seg` int GENERATED ALWAYS AS ((case when (`fin` is not null) then timestampdiff(SECOND,`inicio`,`fin`) else NULL end)) STORED,
  `billable` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fechahoramarcacion` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_rt_ws` (`workspace_id`),
  KEY `fk_rt_pr` (`proyecto_id`),
  KEY `fk_rt_ta` (`tarea_id`),
  KEY `idx_rt_usuario_ws` (`usuario_id`,`workspace_id`),
  KEY `idx_rt_inicio` (`inicio`),
  KEY `idx_rt_fin` (`fin`),
  CONSTRAINT `fk_rt_pr` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_rt_ta` FOREIGN KEY (`tarea_id`) REFERENCES `tareas` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_rt_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_rt_ws` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`) ON DELETE CASCADE,
  CONSTRAINT `chk_fechas` CHECK (((`fin` is null) or (`fin` >= `inicio`)))
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registros_tiempo`
--

LOCK TABLES `registros_tiempo` WRITE;
/*!40000 ALTER TABLE `registros_tiempo` DISABLE KEYS */;
INSERT INTO `registros_tiempo` (`id`, `usuario_id`, `workspace_id`, `proyecto_id`, `tarea_id`, `descripcion`, `inicio`, `fin`, `billable`, `created_at`, `updated_at`, `fechahoramarcacion`) VALUES (5,1,2,2,NULL,'Timer backend prueba','2026-05-18 22:06:54','2026-05-18 22:10:39',1,'2026-05-18 22:06:54','2026-05-18 22:10:39',NULL),(8,9,2,2,NULL,'Trabajando backend','2026-05-21 17:18:23','2026-05-21 17:19:43',1,'2026-05-21 17:18:23','2026-05-21 17:19:43',NULL),(9,18,2,14,3,'Desarrollando módulo login','2026-05-23 17:40:46','2026-05-23 17:49:11',1,'2026-05-23 17:40:46','2026-05-23 17:49:11',NULL),(10,18,2,14,3,'Desarrollando GM Asistencia','2026-05-23 17:49:11','2026-05-23 17:49:27',1,'2026-05-23 17:49:11','2026-05-23 17:49:27',NULL),(11,7,2,12,NULL,'Trabajando en backend','2026-05-25 18:55:18','2026-05-25 18:59:05',0,'2026-05-25 18:55:18','2026-05-25 18:59:05',NULL),(12,18,2,14,3,'Desarrollando GM Asistencia','2026-05-25 19:42:55','2026-05-25 19:43:27',1,'2026-05-25 19:42:55','2026-05-25 19:43:27',NULL),(15,9,2,12,NULL,'Prueba etiqueta registro','2026-05-27 18:16:02','2026-05-29 12:19:18',0,'2026-05-27 18:16:02','2026-05-29 12:19:18',NULL),(16,9,2,12,NULL,'Prueba timer validado','2026-05-29 12:19:18','2026-05-29 12:21:28',0,'2026-05-29 12:19:18','2026-05-29 12:21:28',NULL),(17,7,2,12,NULL,'g','2026-06-01 18:02:37','2026-06-01 18:02:38',0,'2026-06-01 18:02:37','2026-06-01 18:02:38',NULL),(18,7,9,24,NULL,'HOY ','2026-06-02 15:08:53','2026-06-02 15:09:05',1,'2026-06-02 15:08:53','2026-06-02 15:09:05',NULL),(19,7,9,24,NULL,'D','2026-06-02 15:09:36','2026-06-02 15:10:16',1,'2026-06-02 15:09:36','2026-06-02 15:10:16',NULL),(20,7,2,15,NULL,'HOY ','2026-06-02 15:18:53','2026-06-02 15:26:13',1,'2026-06-02 15:18:53','2026-06-02 15:26:13',NULL),(21,7,9,24,NULL,'j','2026-06-02 15:47:04','2026-06-02 15:47:09',1,'2026-06-02 15:47:04','2026-06-02 15:47:09',NULL),(22,7,2,23,NULL,'HOY ','2026-06-02 15:50:51','2026-06-02 16:03:03',0,'2026-06-02 15:50:51','2026-06-02 16:03:03',NULL),(23,7,9,24,NULL,'t','2026-06-02 16:39:22','2026-06-02 16:39:24',0,'2026-06-02 16:39:22','2026-06-02 16:39:24',NULL),(24,7,12,25,NULL,'HOY ','2026-06-03 13:10:32','2026-06-03 13:10:51',0,'2026-06-03 13:10:32','2026-06-03 13:10:51',NULL),(25,7,9,26,NULL,'HOY ','2026-06-03 15:06:25','2026-06-03 15:06:40',0,'2026-06-03 15:06:25','2026-06-03 15:06:40',NULL),(26,7,12,25,NULL,'j','2026-06-03 15:06:47','2026-06-03 15:07:07',0,'2026-06-03 15:06:47','2026-06-03 15:07:07',NULL),(27,7,12,25,NULL,'HOY ','2026-06-03 15:07:14','2026-06-03 15:12:17',0,'2026-06-03 15:07:14','2026-06-03 15:12:17',NULL),(28,7,12,25,NULL,'j','2026-06-03 15:12:24','2026-06-03 15:13:09',0,'2026-06-03 15:12:24','2026-06-03 15:13:09',NULL),(30,7,9,26,NULL,'HOY ','2026-06-03 17:51:18','2026-06-03 17:51:23',0,'2026-06-03 17:51:18','2026-06-03 17:51:23',NULL),(31,7,15,31,NULL,'j','2026-06-04 14:20:01','2026-06-04 14:20:16',1,'2026-06-04 14:20:01','2026-06-04 14:20:16',NULL),(32,7,12,25,NULL,'HOY ','2026-06-04 22:48:12','2026-06-04 22:48:17',0,'2026-06-04 22:48:12','2026-06-04 22:48:17',NULL),(33,7,12,25,NULL,'HOY ','2026-06-04 22:48:47','2026-06-04 22:48:58',1,'2026-06-04 22:48:47','2026-06-04 22:48:58',NULL),(34,7,12,25,NULL,'ga','2026-06-04 23:03:28','2026-06-04 23:03:47',0,'2026-06-04 23:03:28','2026-06-04 23:03:47',NULL),(35,8,12,25,NULL,'HOY ','2026-06-05 12:13:48','2026-06-05 12:14:02',0,'2026-06-05 12:13:48','2026-06-05 12:14:02',NULL),(36,8,12,25,NULL,'HOY ','2026-06-05 12:14:10','2026-06-05 12:14:14',0,'2026-06-05 12:14:10','2026-06-05 12:14:14',NULL),(37,8,12,25,NULL,'D','2026-06-05 12:14:50','2026-06-05 12:14:54',0,'2026-06-05 12:14:50','2026-06-05 12:14:54',NULL),(38,7,12,25,NULL,'D','2026-06-05 12:15:09','2026-06-05 12:15:13',0,'2026-06-05 12:15:09','2026-06-05 12:15:13',NULL),(39,8,12,25,NULL,'a','2026-06-05 13:29:20','2026-06-05 13:29:24',0,'2026-06-05 13:29:20','2026-06-05 13:29:24',NULL);
/*!40000 ALTER TABLE `registros_tiempo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tareas`
--

DROP TABLE IF EXISTS `tareas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tareas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `proyecto_id` int NOT NULL,
  `nombre` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `archivado` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `prioridad` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'media',
  PRIMARY KEY (`id`),
  KEY `fk_ta_pr` (`proyecto_id`),
  CONSTRAINT `fk_ta_pr` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tareas`
--

LOCK TABLES `tareas` WRITE;
/*!40000 ALTER TABLE `tareas` DISABLE KEYS */;
INSERT INTO `tareas` VALUES (3,17,'nombre MAANGER',1,'2026-05-23 17:39:11','media'),(4,14,'Implementar *** JWT',1,'2026-05-23 17:39:14','media'),(5,14,'Implementar login JWT',1,'2026-05-25 18:13:45','media'),(6,12,'Tarea actualizada desde validaciones',1,'2026-05-26 15:00:46','media'),(8,12,'Tarea Validaciones',1,'2026-05-27 15:36:00','media'),(9,17,'GM PROYECTO',0,'2026-06-02 14:04:00','media'),(11,15,'VALIDACIONES',0,'2026-06-04 14:18:53','media'),(12,15,'Joaquin ricardo',0,'2026-06-05 12:12:39','media');
/*!40000 ALTER TABLE `tareas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rol` enum('ADMIN','MANAGER','EMPLEADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'EMPLEADO',
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `estado` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'activo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_usuarios_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Admin','admin@gmail.com','$2a$10$brHNSCQTEp1VAmme3i3d8.Ekgoic/jU3bcwIUK4.yIJBTIitOJFyy','ADMIN',1,'2026-05-16 17:10:49','2026-05-20 19:36:22','activo'),(5,'Carlos Mendoza Refactorizado','carlos.nuevo@gmingenieros.com','$2a$10$gF1MuSFRstVSkK5BdkmucOTWxWSsYKEzSgb871z0VCDgqTetF5aKe','MANAGER',0,'2026-05-18 22:33:55','2026-05-22 23:19:16','activo'),(7,'SOFIA','sofia@gmail.com','$2a$10$F5MlarEMvSrN35xoGJXESefLY1WHZau/p1aS.eqXosDkW8EDD93Eq','ADMIN',1,'2026-05-18 22:42:49','2026-06-03 13:48:01','activo'),(8,'Manager crack','managercrack@gmail.com','$2a$10$GrrHDxx32AYWKCm5EfhR6eAPDVk7Du/K8.UzuhNnNfTHG59GYuHa6','MANAGER',1,'2026-05-18 22:58:06','2026-06-05 12:18:03','activo'),(9,'Empleado conde','empleadoc@gmail.com','$2a$10$r3.3Ugo1D18kouQkzN.YSOeR75NsLKxeLHr/4sF5A7ALn/b0mUed6','EMPLEADO',1,'2026-05-18 22:58:23','2026-06-05 12:17:02','activo'),(10,'Admin Extra','adminextra@gmail.com','$2a$10$FBUtmBqfs2rf7lOPu7dVkuWkSJeLKIIPUxpaR75v7gO8CDBVw2kfK','ADMIN',0,'2026-05-18 23:02:13','2026-05-25 17:35:58','activo'),(12,'Blas','blas@gmail.com','$2a$10$wLBlp8KicpFUm69OgvojuOWYpKy1CZVFhNBEK/X9XcFbeQ5jUMqRu','EMPLEADO',0,'2026-05-18 23:03:45','2026-06-03 15:41:41','activo'),(13,'Jenny','jenny@gmail.com','$2a$10$7sosQjotE.8RwXz2ENSYF.gDyn16b/pFJu0JDlLjfZHZfJ5WjFmp6','ADMIN',1,'2026-05-18 23:04:23','2026-06-04 12:33:40','activo'),(14,'Joaquin','joaquin@gmail.com','$2a$10$lHl2CUGmngHzXMn1Qpo1deZvCfT9LeDZRtHq8bPPYUkcOF2PXiZuq','ADMIN',0,'2026-05-18 23:11:17','2026-06-04 14:15:42','activo'),(15,'jack Extra','jackes@gmail.com','$2a$10$dmoOO8H7mPVD7mB1s4Krx.nKwMNwYZfLv8OfrfyNWJ.dA50NSlzcC','ADMIN',0,'2026-05-18 23:14:45','2026-06-01 18:28:30','activo'),(16,'Arturo','arturo@gmail.com','$2a$10$SBILO/dljVX9uszUnszqS.cnUm/SLpWtMwz8DrdSNliK2S/7OtAzm','ADMIN',1,'2026-05-19 16:25:15','2026-06-03 15:41:22','activo'),(17,'Ingeniero','ingeniero@gmail.com','$2a$10$1TKFO5A.7fOVWH1hLR/WLeF98yPR.mYF82IFiQOtjucure/cJYkHW','ADMIN',1,'2026-05-22 17:20:11','2026-05-22 17:20:11','activo'),(18,'Administrador Maestro','admin@gmingenieros.com','$2a$10$Ei4l7DCWhAtCULm9pDr7U.IA9LyPmr7fCFP3uUjCTj6SRgtGbG51K','ADMIN',1,'2026-05-22 22:18:27','2026-05-22 22:26:19','activo'),(19,'Joaquin Conde','joaquin@gmingenieros.com','$2a$10$GX3.0oM0cZplRDlQFeu9/O8.eNxJlEOezWQK1wmZuW8TghogN6tda','EMPLEADO',1,'2026-05-22 22:27:23','2026-06-01 18:36:01','activo'),(20,'Conde','conde@gmail.com','$2a$10$Ku0l6DUPyi3aG44pnENWL.jbJrcBop2YeCPk4lL1MuEHL.x9S39uC','ADMIN',1,'2026-05-25 17:28:07','2026-05-25 17:28:07','activo'),(22,'Espiritu','espiritu@gmail.com','$2a$10$BVooYQabZgYVqWNGdBI9a.CZmpgAVszIzpGaWZ9qQ48nbk9Jg06si','MANAGER',1,'2026-05-25 17:28:38','2026-05-25 17:28:38','activo'),(23,'Rodrigo','rodrigo@gmail.com','$2a$10$SzwxqOUASJNonbpQ5uIviu5ff4397Owo2RqGp3ooXaQhIdqRxxLTW','EMPLEADO',0,'2026-05-25 17:28:58','2026-06-01 18:28:06','activo'),(24,'Manager Demo','manager.demo@gmail.com','$2a$10$.IpuNAAIklezkFH9Jbax9.wU0bsTqjGS4inLOFq/TnD.FC5b9QFcu','MANAGER',1,'2026-05-26 14:52:46','2026-05-26 14:52:46','activo'),(25,'Empleado Demo','empleado.demo@gmail.com','$2a$10$8OAgzUTPoU8pCdbxCZCdNOAiLajZnQ09p.lZd77dGBHJIcbnNjA2C','EMPLEADO',1,'2026-05-26 14:53:03','2026-05-26 14:53:03','activo'),(26,'Usuario Local Prueba','local.prueba@gmail.com','$2a$10$V5Iogiv7PwFfF3sKHyUbSeFR9bEW4FwSXcLPttmXhkRITpycEdKpO','EMPLEADO',0,'2026-05-27 12:14:50','2026-05-27 12:51:23','activo'),(27,'Usuario Validacion ACTUAILIZADO','validacion.actualizado@gmail.com','$2a$10$brSfkLW0LjKudqv6SYi9kOznwGogLAj3HZOZDueX8pk7vGTJeFLSW','ADMIN',1,'2026-05-27 14:56:19','2026-05-27 15:16:16','activo'),(28,'front','frontend.test@gmail.com','$2a$10$AqFoRMzHnVTyT4nDURTdN.jYDOKPKBsOROOODoDCsStx6sjLfpiRG','MANAGER',1,'2026-06-01 18:21:48','2026-06-01 18:21:48','activo'),(29,'hermes','hermes@gmail.com','$2a$10$qibKbeyG3t2tFYwXtLuiIOy0ALoT4WDr5BlXBYAQvm0MH5WOM0ADu','EMPLEADO',1,'2026-06-03 15:41:00','2026-06-04 12:34:06','activo'),(30,'ga','ga@gmail.com','$2a$10$HEecNrpAQSINp79OeSlIg.x13ApmcVzPpXWYtQuWzvSgSVRcoW.qy','ADMIN',0,'2026-06-03 17:37:03','2026-06-04 13:28:41','activo'),(32,'JOAQUIN RICARDO CONDE ESPIRITU','joaquinricardo@gmail.com','$2a$10$PjtAEyNynas2DCZ3b5n1Y.7O66g52oDVgNinIZna.yvwqgJRa52PK','EMPLEADO',1,'2026-06-04 14:14:56','2026-06-04 14:15:23','activo'),(33,'das','joaquinricardo@fas','$2a$10$pDnwKMBnwqdiQiwKrmVBFeteapy.TZjy.gEaZfbUt9MnZUHNiENtK','EMPLEADO',0,'2026-06-04 14:15:53','2026-06-04 14:16:00','activo');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workspace`
--

DROP TABLE IF EXISTS `workspace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workspace` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `descripcion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `owner_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_ws_owner` (`owner_id`),
  CONSTRAINT `fk_ws_owner` FOREIGN KEY (`owner_id`) REFERENCES `usuarios` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workspace`
--

LOCK TABLES `workspace` WRITE;
/*!40000 ALTER TABLE `workspace` DISABLE KEYS */;
INSERT INTO `workspace` VALUES (2,'Workspace Principal Editado','Workspace actualizado',1,'2026-05-16 17:28:47','2026-05-25 18:28:39'),(5,'Workspace Demo Final Actualizado','Workspace actualizado para prueba final',7,'2026-05-26 14:54:56','2026-05-26 14:57:26'),(8,'Workspace Validaciones','Workspace para validar endpoints',7,'2026-05-27 15:59:18','2026-05-27 15:59:18'),(9,'SECUNDARIO prueba front','lider',7,'2026-06-01 23:33:05','2026-06-01 23:34:21'),(12,'Joaquin ESPIRITU MANAGERj','DA',8,'2026-06-01 23:51:09','2026-06-05 13:27:43'),(13,'Joaquin ricardo','d',18,'2026-06-03 15:30:09','2026-06-03 15:30:09'),(15,'TRABAJO DE JOAQUIN','DAS',27,'2026-06-04 14:16:44','2026-06-04 14:17:04');
/*!40000 ALTER TABLE `workspace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workspace_usuarios`
--

DROP TABLE IF EXISTS `workspace_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workspace_usuarios` (
  `workspace_id` int NOT NULL,
  `usuario_id` int NOT NULL,
  `rol_en_workspace` enum('ADMIN','MANAGER','EMPLEADO') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'EMPLEADO',
  `joined_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`workspace_id`,`usuario_id`),
  KEY `idx_wu_usuario` (`usuario_id`),
  CONSTRAINT `fk_wu_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_wu_workspace` FOREIGN KEY (`workspace_id`) REFERENCES `workspace` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workspace_usuarios`
--

LOCK TABLES `workspace_usuarios` WRITE;
/*!40000 ALTER TABLE `workspace_usuarios` DISABLE KEYS */;
INSERT INTO `workspace_usuarios` VALUES (5,7,'ADMIN','2026-05-26 14:54:56'),(8,7,'ADMIN','2026-05-27 15:59:18'),(9,7,'ADMIN','2026-06-01 23:33:05'),(12,13,'ADMIN','2026-06-01 23:51:09'),(13,18,'ADMIN','2026-06-03 15:30:09'),(15,32,'ADMIN','2026-06-04 14:16:44');
/*!40000 ALTER TABLE `workspace_usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'asistenciagm'
--

--
-- Dumping routines for database 'asistenciagm'
--
/*!50003 DROP PROCEDURE IF EXISTS `registrar_entrada` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `registrar_entrada`(
IN p_usuario INT,
IN p_proyecto INT,
IN p_tarea INT,
IN p_descripcion VARCHAR(255)
)
BEGIN

INSERT INTO registros_tiempo
(id_usuario,id_proyecto,id_tarea,descripcion,fecha,hora_inicio,facturable)

VALUES
(p_usuario,p_proyecto,p_tarea,p_descripcion,CURDATE(),CURTIME(),TRUE);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `registrar_salida` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `registrar_salida`(
IN p_registro INT
)
BEGIN

UPDATE registros_tiempo
SET hora_fin = CURTIME()
WHERE id_registro = p_registro;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_etiqueta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_actualizar_etiqueta"(
    IN p_id           INT,
    IN p_nombre       VARCHAR(80),
    IN p_color        CHAR(7)
)
BEGIN

    UPDATE etiquetas

    SET nombre = p_nombre,
        color  = p_color

    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_mi_password` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_mi_password`(
    IN p_id INT,
    IN p_password_hash VARCHAR(255)
)
BEGIN
    UPDATE usuarios
    SET password_hash = p_password_hash,
        updated_at = NOW()
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_mi_perfil` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_mi_perfil`(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_correo VARCHAR(150)
)
BEGIN
    UPDATE usuarios
    SET nombre = p_nombre,
        email = p_correo
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_actualizar_proyecto"(
    IN p_id           INT,
    IN p_cliente_id   INT,
    IN p_nombre       VARCHAR(100),
    IN p_color        CHAR(7),
    IN p_billable     TINYINT(1)
)
BEGIN
    UPDATE proyectos
    SET cliente_id = p_cliente_id,
        nombre     = p_nombre,
        color      = p_color,
        billable   = p_billable
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_tarea` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_actualizar_tarea"(
    IN p_id           INT,
    IN p_proyecto_id  INT,
    IN p_nombre       VARCHAR(150)
)
BEGIN

    UPDATE tareas

    SET proyecto_id = p_proyecto_id,
        nombre      = p_nombre

    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_actualizar_usuario"(
    IN p_id            INT,
    IN p_nombre        VARCHAR(100),
    IN p_email         VARCHAR(150),
    IN p_password_hash VARCHAR(255),
    IN p_rol           ENUM('ADMIN','MANAGER','EMPLEADO')
)
BEGIN
    UPDATE usuarios 
    SET nombre = p_nombre,
        email = p_email,
        password_hash = p_password_hash,
        rol = p_rol
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_usuario_datos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizar_usuario_datos`(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_correo VARCHAR(150),
    IN p_rol VARCHAR(30)
)
BEGIN
    UPDATE usuarios
    SET nombre = p_nombre,
        email = p_correo,
        rol = p_rol,
        updated_at = NOW()
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_actualizar_workspace"(
    IN p_id           INT,
    IN p_nombre       VARCHAR(100),
    IN p_descripcion  VARCHAR(255),
    IN p_owner_id     INT
)
BEGIN

    UPDATE workspace
    SET nombre      = p_nombre,
        descripcion = p_descripcion,
        owner_id    = p_owner_id
    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_agregar_etiqueta_registro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_agregar_etiqueta_registro"(
    IN p_registro_id INT,
    IN p_etiqueta_id INT
)
BEGIN

    -- VALIDAR REGISTRO
    IF NOT EXISTS (
        SELECT 1
        FROM registros_tiempo
        WHERE id = p_registro_id
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El registro no existe';

    END IF;

    -- VALIDAR ETIQUETA
    IF NOT EXISTS (
        SELECT 1
        FROM etiquetas
        WHERE id = p_etiqueta_id
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La etiqueta no existe';

    END IF;

    -- VALIDAR DUPLICADO
    IF EXISTS (
        SELECT 1
        FROM registro_etiquetas
        WHERE registro_id = p_registro_id
          AND etiqueta_id = p_etiqueta_id
    ) THEN

        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La etiqueta ya está asignada';

    END IF;

    -- INSERTAR
    INSERT INTO registro_etiquetas (
        registro_id,
        etiqueta_id
    )
    VALUES (
        p_registro_id,
        p_etiqueta_id
    );

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_agregar_miembro_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_agregar_miembro_workspace"(
    IN p_workspace_id     INT,
    IN p_usuario_id       INT,
    IN p_rol_en_workspace ENUM('ADMIN','MANAGER','EMPLEADO')
)
BEGIN
    INSERT INTO workspace_usuarios (workspace_id, usuario_id, rol_en_workspace)
    VALUES (p_workspace_id, p_usuario_id, p_rol_en_workspace)
    ON DUPLICATE KEY UPDATE rol_en_workspace = p_rol_en_workspace;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_archivar_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_archivar_cliente"(IN p_id INT, IN p_archivar TINYINT(1))
BEGIN
    UPDATE clientes SET archivado = p_archivar WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_archivar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_archivar_proyecto"(IN p_id INT, IN p_archivar TINYINT(1))
BEGIN
    UPDATE proyectos SET archivado = p_archivar WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_archivar_tarea` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_archivar_tarea"(
    IN p_id         INT,
    IN p_archivado  TINYINT(1)
)
BEGIN

    UPDATE tareas

    SET archivado = p_archivado

    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_cliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_cliente"(
    IN  p_workspace_id INT,
    IN  p_nombre       VARCHAR(100),
    IN  p_email        VARCHAR(150),
    IN  p_direccion    VARCHAR(255),
    OUT p_id           INT
)
BEGIN
    INSERT INTO clientes (workspace_id, nombre, email, direccion)
    VALUES (p_workspace_id, p_nombre, p_email, p_direccion);
    SET p_id = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_etiqueta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_etiqueta"(
    IN  p_workspace_id INT,
    IN  p_nombre       VARCHAR(80),
    IN  p_color        CHAR(7),
    OUT p_id           INT
)
BEGIN

    INSERT INTO etiquetas (
        workspace_id,
        nombre,
        color
    )
    VALUES (
        p_workspace_id,
        p_nombre,
        p_color
    );

    SET p_id = LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_proyecto"(
    IN  p_workspace_id INT,
    IN  p_cliente_id   INT,
    IN  p_nombre       VARCHAR(100),
    IN  p_color        CHAR(7),
    IN  p_billable     TINYINT(1),
    OUT p_id           INT
)
BEGIN
    INSERT INTO proyectos (workspace_id, cliente_id, nombre, color, billable)
    VALUES (p_workspace_id, p_cliente_id, p_nombre, p_color, p_billable);
    /* SET p_id = LAST_INSERT_ID(); */
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_tarea` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_tarea"(
    IN  p_proyecto_id INT,
    IN  p_nombre      VARCHAR(150),
    OUT p_id          INT
)
BEGIN

    INSERT INTO tareas (
        proyecto_id,
        nombre
    )
    VALUES (
        p_proyecto_id,
        p_nombre
    );

    SET p_id = LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_usuario"(
    IN  p_nombre        VARCHAR(100),
    IN  p_email         VARCHAR(150),
    IN  p_password_hash VARCHAR(255),
    IN  p_rol           ENUM('ADMIN','MANAGER','EMPLEADO'),
    OUT p_id            INT
)
BEGIN
    INSERT INTO usuarios (nombre, email, password_hash, rol)
    VALUES (p_nombre, p_email, p_password_hash, p_rol);
    SET p_id = LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_crear_workspace"(
    IN  p_nombre      VARCHAR(100),
    IN  p_descripcion VARCHAR(255),
    IN  p_owner_id    INT,
    OUT p_id          INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    START TRANSACTION;
        INSERT INTO workspace (nombre, descripcion, owner_id)
        VALUES (p_nombre, p_descripcion, p_owner_id);
        SET p_id = LAST_INSERT_ID();
        INSERT INTO workspace_usuarios (workspace_id, usuario_id, rol_en_workspace)
        VALUES (p_id, p_owner_id, 'ADMIN');
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_desactivar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_desactivar_usuario"(IN p_id INT)
BEGIN
    UPDATE usuarios SET activo = 0 WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_detener_timer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_detener_timer"(
    IN p_usuario_id INT
)
BEGIN

    UPDATE registros_tiempo
    SET fin = NOW()
    WHERE usuario_id = p_usuario_id
      AND fin IS NULL;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_etiqueta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_eliminar_etiqueta"(
    IN p_id INT
)
BEGIN

    DELETE FROM etiquetas

    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_eliminar_proyecto"(
    IN p_id INT
)
BEGIN
    UPDATE proyectos
    SET archivado = 1
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_tarea` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_eliminar_tarea"(
    IN p_id INT
)
BEGIN

    UPDATE tareas

    SET archivado = 1

    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_eliminar_usuario"(
    IN p_id INT
)
BEGIN
    UPDATE usuarios 
    SET activo = 0 
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_eliminar_workspace"(
    IN p_id INT
)
BEGIN

    DELETE FROM workspace
    WHERE id = p_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_etiquetas_por_registro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_etiquetas_por_registro"(
    IN p_registro_id INT
)
BEGIN

    SELECT
        e.id,
        e.workspace_id,
        e.nombre,
        e.color

    FROM registro_etiquetas re

    INNER JOIN etiquetas e
        ON re.etiqueta_id = e.id

    WHERE re.registro_id = p_registro_id

    ORDER BY e.nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_historial_timers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_historial_timers"(
    IN p_usuario_id INT
)
BEGIN

    SELECT
        rt.id,
        rt.usuario_id,
        rt.workspace_id,
        rt.proyecto_id,
        rt.tarea_id,
        rt.descripcion,
        rt.inicio,
        rt.fin,
        rt.billable,
        rt.duracion_seg,

        p.nombre AS proyecto_nombre,
        p.color  AS proyecto_color,

        t.nombre AS tarea_nombre

    FROM registros_tiempo rt

    LEFT JOIN proyectos p
        ON rt.proyecto_id = p.id

    LEFT JOIN tareas t
        ON rt.tarea_id = t.id

    WHERE rt.usuario_id = p_usuario_id

    ORDER BY rt.inicio DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_informe_detallado` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_informe_detallado"(
    IN p_workspace_id INT,
    IN p_usuario_id   INT,
    IN p_proyecto_id  INT,
    IN p_desde        DATE,
    IN p_hasta        DATE
)
BEGIN
    SELECT
        rt.id,
        rt.descripcion,
        rt.inicio,
        rt.fin,
        rt.duracion_seg,
        rt.billable,
        u.nombre  AS usuario_nombre,
        p.nombre  AS proyecto_nombre,
        p.color   AS proyecto_color,
        t.nombre  AS tarea_nombre,
        GROUP_CONCAT(e.nombre ORDER BY e.nombre SEPARATOR ', ') AS etiquetas
    FROM registros_tiempo rt
    INNER JOIN usuarios  u  ON rt.usuario_id  = u.id
    LEFT JOIN  proyectos p  ON rt.proyecto_id = p.id
    LEFT JOIN  tareas    t  ON rt.tarea_id    = t.id
    LEFT JOIN  registro_etiquetas re ON rt.id = re.registro_id
    LEFT JOIN  etiquetas e  ON re.etiqueta_id = e.id
    WHERE rt.workspace_id = p_workspace_id
      AND rt.fin IS NOT NULL
      AND DATE(rt.inicio) >= p_desde
      AND DATE(rt.inicio) <= p_hasta
      AND (p_usuario_id  IS NULL OR rt.usuario_id  = p_usuario_id)
      AND (p_proyecto_id IS NULL OR rt.proyecto_id = p_proyecto_id)
    GROUP BY rt.id
    ORDER BY rt.inicio DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_informe_por_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_informe_por_proyecto"(
    IN p_workspace_id INT,
    IN p_desde        DATE,
    IN p_hasta        DATE
)
BEGIN
    SELECT
        COALESCE(p.id, 0)      AS proyecto_id,
        COALESCE(p.nombre, 'Sin proyecto') AS proyecto_nombre,
        p.color                AS proyecto_color,
        SUM(rt.duracion_seg)   AS total_segundos,
        COUNT(rt.id)           AS total_registros
    FROM registros_tiempo rt
    LEFT JOIN proyectos p ON rt.proyecto_id = p.id
    WHERE rt.workspace_id = p_workspace_id
      AND rt.fin IS NOT NULL
      AND DATE(rt.inicio) >= p_desde
      AND DATE(rt.inicio) <= p_hasta
    GROUP BY p.id, p.nombre, p.color
    ORDER BY total_segundos DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_iniciar_timer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_iniciar_timer"(
    IN  p_usuario_id   INT,
    IN  p_workspace_id INT,
    IN  p_proyecto_id  INT,
    IN  p_tarea_id     INT,
    IN  p_descripcion  VARCHAR(255),
    IN  p_billable     TINYINT(1),
    OUT p_id           INT
)
BEGIN

    UPDATE registros_tiempo
    SET fin = NOW()
    WHERE usuario_id = p_usuario_id
      AND workspace_id = p_workspace_id
      AND fin IS NULL;

    INSERT INTO registros_tiempo (
        usuario_id,
        workspace_id,
        proyecto_id,
        tarea_id,
        descripcion,
        inicio,
        billable
    )
    VALUES (
        p_usuario_id,
        p_workspace_id,
        p_proyecto_id,
        p_tarea_id,
        p_descripcion,
        NOW(),
        p_billable
    );

    SET p_id = LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_clientes` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_listar_clientes"(
    IN p_workspace_id INT,
    IN p_incluir_archivados TINYINT(1)
)
BEGIN
    SELECT id, nombre, email, direccion, archivado, created_at
    FROM clientes
    WHERE workspace_id = p_workspace_id
      AND (p_incluir_archivados = 1 OR archivado = 0)
    ORDER BY nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_etiquetas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_listar_etiquetas"(
    IN p_workspace_id INT
)
BEGIN

    SELECT
        id,
        workspace_id,
        nombre,
        color

    FROM etiquetas

    WHERE workspace_id = p_workspace_id

    ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_proyectos` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_listar_proyectos"()
BEGIN
    SELECT p.id, p.workspace_id, p.nombre, p.color, p.billable, p.archivado,
           c.id AS cliente_id, c.nombre AS cliente_nombre
    FROM proyectos p
    LEFT JOIN clientes c ON p.cliente_id = c.id
    WHERE p.archivado = 0
    ORDER BY p.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_proyectos_archivados` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_listar_proyectos_archivados`()
BEGIN
    SELECT
        p.id,
        p.workspace_id,
        p.nombre,
        p.color,
        p.billable,
        p.archivado,
        c.id AS cliente_id,
        c.nombre AS cliente_nombre
    FROM proyectos p
    LEFT JOIN clientes c
        ON p.cliente_id = c.id
    WHERE p.archivado = 1
    ORDER BY p.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_usuarios` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_listar_usuarios"()
BEGIN
    SELECT id, nombre, email, rol
    FROM usuarios
    WHERE activo = 1
    ORDER BY nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_workspaces` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_listar_workspaces"()
BEGIN
    SELECT id, nombre, descripcion, owner_id
    FROM workspace
    ORDER BY nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_marcar_asistencia` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_marcar_asistencia"(
    IN p_id_usuario INT,
    IN p_id_proyecto INT,
    IN p_tipo VARCHAR(255)
)
BEGIN
    INSERT INTO registros_tiempo (
        id_usuario,
        id_proyecto,
        descripcion,
        fecha,
        fechahoramarcacion
    )
    VALUES (
        p_id_usuario,
        p_id_proyecto,
        p_tipo,
        CURDATE(),
        NOW()
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_etiqueta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_obtener_etiqueta"(
    IN p_id INT
)
BEGIN

    SELECT
        id,
        workspace_id,
        nombre,
        color

    FROM etiquetas

    WHERE id = p_id

    LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_tarea` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_obtener_tarea"(
    IN p_id INT
)
BEGIN
    SELECT
        id,
        proyecto_id,
        nombre,
        archivado,
        created_at

    FROM tareas
    WHERE id = p_id
    LIMIT 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_tareas_totales` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_obtener_tareas_totales"()
BEGIN
	SELECT id, proyecto_id, nombre, archivado, created_at
    FROM tareas;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_panel_resumen` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_panel_resumen"(
    IN p_workspace_id INT,
    IN p_usuario_id   INT,       -- NULL = todos los usuarios
    IN p_desde        DATE,
    IN p_hasta        DATE
)
BEGIN
    SELECT
        u.id   AS usuario_id,
        u.nombre AS usuario_nombre,
        COUNT(rt.id)              AS total_registros,
        COALESCE(SUM(rt.duracion_seg), 0) AS total_segundos,
        COALESCE(SUM(CASE WHEN rt.billable = 1 THEN rt.duracion_seg ELSE 0 END), 0) AS segundos_billable
    FROM registros_tiempo rt
    INNER JOIN usuarios u ON rt.usuario_id = u.id
    WHERE rt.workspace_id = p_workspace_id
      AND DATE(rt.inicio)  >= p_desde
      AND DATE(rt.inicio)  <= p_hasta
      AND rt.fin IS NOT NULL
      AND (p_usuario_id IS NULL OR rt.usuario_id = p_usuario_id)
    GROUP BY u.id, u.nombre
    ORDER BY total_segundos DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_quitar_etiqueta_registro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_quitar_etiqueta_registro"(
    IN p_registro_id INT,
    IN p_etiqueta_id INT
)
BEGIN

    DELETE FROM registro_etiquetas

    WHERE registro_id = p_registro_id
      AND etiqueta_id = p_etiqueta_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_remover_miembro_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_remover_miembro_workspace"(
    IN p_workspace_id INT,
    IN p_usuario_id   INT
)
BEGIN
    DELETE FROM workspace_usuarios
    WHERE workspace_id = p_workspace_id AND usuario_id = p_usuario_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_restaurar_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_restaurar_proyecto`(
    IN p_id INT
)
BEGIN
    UPDATE proyectos
    SET archivado = 0
    WHERE id = p_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_tareas_por_proyecto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_tareas_por_proyecto"(
    IN p_proyecto_id INT,
    IN p_incluir_archivadas TINYINT(1)
)
BEGIN

    SELECT
        id,
        proyecto_id,
        nombre,
        archivado,
        created_at

    FROM tareas

    WHERE proyecto_id = p_proyecto_id
      AND (
            p_incluir_archivadas = 1
            OR archivado = 0
      )

    ORDER BY nombre;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_timer_activo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_timer_activo"(
    IN p_usuario_id INT
)
BEGIN

    SELECT
        rt.id,
        rt.usuario_id,
        rt.workspace_id,
        rt.proyecto_id,
        rt.tarea_id,
        rt.descripcion,
        rt.inicio,
        rt.fin,
        rt.billable,
        rt.duracion_seg,

        p.nombre AS proyecto_nombre,
        p.color  AS proyecto_color,

        t.nombre AS tarea_nombre

    FROM registros_tiempo rt

    LEFT JOIN proyectos p
        ON rt.proyecto_id = p.id

    LEFT JOIN tareas t
        ON rt.tarea_id = t.id

    WHERE rt.usuario_id = p_usuario_id
      AND rt.fin IS NULL

    LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_toggle_etiqueta_registro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_toggle_etiqueta_registro"(
    IN p_registro_id INT,
    IN p_etiqueta_id INT
)
BEGIN
    IF EXISTS (SELECT 1 FROM registro_etiquetas
               WHERE registro_id = p_registro_id AND etiqueta_id = p_etiqueta_id) THEN
        DELETE FROM registro_etiquetas
        WHERE registro_id = p_registro_id AND etiqueta_id = p_etiqueta_id;
    ELSE
        INSERT INTO registro_etiquetas (registro_id, etiqueta_id)
        VALUES (p_registro_id, p_etiqueta_id);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_usuarios_por_workspace` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_usuarios_por_workspace"(IN p_workspace_id INT)
BEGIN
    SELECT u.id, u.nombre, u.email, u.rol, wu.rol_en_workspace, wu.joined_at
    FROM usuarios u
    INNER JOIN workspace_usuarios wu ON u.id = wu.usuario_id
    WHERE wu.workspace_id = p_workspace_id AND u.activo = 1
    ORDER BY u.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_usuario_por_email` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_usuario_por_email"(IN p_email VARCHAR(150))
BEGIN
    SELECT id, nombre, email, password_hash, rol, activo, created_at
    FROM usuarios
    WHERE email = p_email AND activo = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_workspaces_de_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'REAL_AS_FLOAT,PIPES_AS_CONCAT,ANSI_QUOTES,IGNORE_SPACE,ONLY_FULL_GROUP_BY,ANSI,STRICT_ALL_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER="avnadmin"@"%" PROCEDURE "sp_workspaces_de_usuario"(IN p_usuario_id INT)
BEGIN
    SELECT w.id, w.nombre, w.descripcion, w.owner_id, wu.rol_en_workspace
    FROM workspace w
    INNER JOIN workspace_usuarios wu ON w.id = wu.workspace_id
    WHERE wu.usuario_id = p_usuario_id
    ORDER BY w.nombre;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-05 14:01:56
