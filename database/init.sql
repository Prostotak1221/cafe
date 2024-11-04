-- MariaDB dump 10.19-11.1.2-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: cafe
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE cafe;
USE cafe;

--
-- Table structure for table `Cities`
--

DROP TABLE IF EXISTS `Cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cities`
--

LOCK TABLES `Cities` WRITE;
/*!40000 ALTER TABLE `Cities` DISABLE KEYS */;
INSERT INTO `Cities` VALUES
(1,'Москва'),
(2,'Санкт-Петербург'),
(3,'Екатеринбург'),
(4,'Новосибирск'),
(5,'Казань'),
(6,'Самара'),
(7,'Омск'),
(8,'Челябинск'),
(9,'Ростов-на-Дону'),
(10,'Уфа');
/*!40000 ALTER TABLE `Cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Clients`
--

DROP TABLE IF EXISTS `Clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `patronymic` varchar(40) NOT NULL,
  `phone` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Clients`
--

LOCK TABLES `Clients` WRITE;
/*!40000 ALTER TABLE `Clients` DISABLE KEYS */;
INSERT INTO `Clients` VALUES
(1,'Иван','Иванов','Иванович','+79101234567'),
(2,'Анна','Петрова','Сергеевна','+79105555555'),
(3,'Дмитрий','Смирнов','Алексеевич','+79109876543'),
(4,'Екатерина','Козлова','Александровна','+79101112233'),
(5,'Максим','Васнецов','Артемович','+79109998877'),
(6,'Ольга','Михайлова','Петровна','+79107776666'),
(7,'Павел','Краснов','Игоревич','+79101112233'),
(8,'Александра','Попова','Ивановна','+79103456789'),
(9,'Сергей','Сидоров','Андреевич','+79101234567'),
(10,'Юлия','Федорова','Валерьевна','+79101112233');
/*!40000 ALTER TABLE `Clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employees`
--

DROP TABLE IF EXISTS `Employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employees` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int NOT NULL,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `patronymic` varchar(40) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `address` varchar(40) NOT NULL,
  `job_title` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `Employees_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `Cities` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employees`
--

LOCK TABLES `Employees` WRITE;
/*!40000 ALTER TABLE `Employees` DISABLE KEYS */;
INSERT INTO `Employees` VALUES
(1,1,'Анна','Иванова','Алексеевна','+79105554433','ул. Ленина, 10','Бариста'),
(2,2,'Петр','Сергеев','Петрович','+79109998877','ул. Пушкина, 5','Менеджер по продажам'),
(3,3,'Екатерина','Казанцева','Сергеевна','+79101112233','ул. Екатерининская, 15','Бариста'),
(4,4,'Александр','Смирнов','Александрович','+79103456789','пр. Ленина, 20','Кассир'),
(5,5,'Виктория','Попова','Игоревна','+79107776666','ул. Гагарина, 7','Бариста'),
(6,6,'Дмитрий','Михайлов','Дмитриевич','+79101234567','пр. Кирова, 3','Менеджер по закупкам'),
(7,7,'Ирина','Сидорова','Игоревна','+79101112233','ул. Фрунзе, 12','Бариста'),
(8,8,'Максим','Краснов','Максимович','+79105555555','пр. Победы, 25','Бариста'),
(9,9,'Ольга','Васнецова','Андреевна','+79101234567','ул. Калинина, 8','Менеджер по персоналу'),
(10,10,'Сергей','Федоров','Сергеевич','+79103456789','ул. Гоголя, 18','Бариста');
/*!40000 ALTER TABLE `Employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderDetails`
--

DROP TABLE IF EXISTS `OrderDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderDetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_orders` int NOT NULL,
  `id_products` int NOT NULL,
  `price` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_products` (`id_products`),
  KEY `id_orders` (`id_orders`),
  CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`id_products`) REFERENCES `Products` (`id`) ON DELETE CASCADE,
  CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`id_orders`) REFERENCES `Orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderDetails`
--

LOCK TABLES `OrderDetails` WRITE;
/*!40000 ALTER TABLE `OrderDetails` DISABLE KEYS */;
INSERT INTO `OrderDetails` VALUES
(1,1,1,150,2),
(2,2,2,200,1),
(3,3,3,180,2),
(4,4,4,120,1),
(5,5,5,220,2),
(6,6,6,190,1),
(7,7,7,250,2),
(8,8,8,170,2),
(9,9,9,160,1),
(10,10,10,230,2);
/*!40000 ALTER TABLE `OrderDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_clients` int NOT NULL,
  `date` datetime NOT NULL,
  `total_price` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_clients` (`id_clients`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`id_clients`) REFERENCES `Clients` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES
(1,1,'2023-11-19 10:30:00',0),
(2,2,'2023-11-19 11:45:00',0),
(3,3,'2023-11-19 13:15:00',0),
(4,4,'2023-11-19 14:30:00',0),
(5,5,'2023-11-19 16:00:00',0),
(6,6,'2023-11-19 17:15:00',0),
(7,7,'2023-11-19 18:30:00',0),
(8,8,'2023-11-19 19:45:00',0),
(9,9,'2023-11-19 21:00:00',0),
(10,10,'2023-11-19 22:15:00',0),
(11,1,'2023-11-19 10:30:00',0),
(12,2,'2023-11-19 11:45:00',0),
(13,3,'2023-11-19 13:15:00',0),
(14,4,'2023-11-19 14:30:00',0),
(15,5,'2023-11-19 16:00:00',0),
(16,6,'2023-11-19 17:15:00',0),
(17,7,'2023-11-19 18:30:00',0),
(18,8,'2023-11-19 19:45:00',0),
(19,9,'2023-11-19 21:00:00',0),
(20,10,'2023-11-19 22:15:00',0);
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `price` int NOT NULL,
  `expiration_date` datetime NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

LOCK TABLES `Products` WRITE;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES
(1,'Кофе Американо',150,'2023-12-31 00:00:00',2),
(2,'Латте',200,'2023-12-31 00:00:00',3),
(3,'Капучино',180,'2023-12-31 00:00:00',2),
(4,'Эспрессо',120,'2023-12-31 00:00:00',1),
(5,'Мокка',220,'2023-12-31 00:00:00',4),
(6,'Флэт Уайт',190,'2023-12-31 00:00:00',1),
(7,'Раф',250,'2023-12-31 00:00:00',2),
(8,'Айс Кофе',170,'2023-12-31 00:00:00',2),
(9,'Кофе по-турецки',160,'2023-12-31 00:00:00',3),
(10,'Гляссе',230,'2023-12-31 00:00:00',3);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SupplierProducts`
--

DROP TABLE IF EXISTS `SupplierProducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SupplierProducts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplier_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `SupplierProducts_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `Suppliers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `SupplierProducts_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `Products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SupplierProducts`
--

LOCK TABLES `SupplierProducts` WRITE;
/*!40000 ALTER TABLE `SupplierProducts` DISABLE KEYS */;
INSERT INTO `SupplierProducts` VALUES
(1,1,1),
(2,2,2),
(3,3,3),
(4,4,4),
(5,5,5),
(6,6,6),
(7,7,7),
(8,8,8),
(9,9,9),
(10,10,10);
/*!40000 ALTER TABLE `SupplierProducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Suppliers`
--

DROP TABLE IF EXISTS `Suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Suppliers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int NOT NULL,
  `name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `Suppliers_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `Cities` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Suppliers`
--

LOCK TABLES `Suppliers` WRITE;
/*!40000 ALTER TABLE `Suppliers` DISABLE KEYS */;
INSERT INTO `Suppliers` VALUES
(1,1,'Кофе Магия'),
(2,2,'АромаДеликатесы'),
(3,3,'Экзотические Зерна'),
(4,4,'Волшебные Вкусы'),
(5,5,'Кофейное Искусство'),
(6,6,'Звездный Аромат'),
(7,7,'Альпийский Аромат'),
(8,8,'Кофейное Чудо'),
(9,9,'Секреты Арабики'),
(10,10,'Райский Кофе');
/*!40000 ALTER TABLE `Suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cafe'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

