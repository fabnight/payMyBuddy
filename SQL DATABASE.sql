-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddy
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `app_transaction`
--

DROP TABLE IF EXISTS `app_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `amount` float DEFAULT NULL,
  `fees` float DEFAULT NULL,
  `operation_date` varchar(20) DEFAULT NULL,
  `operation_description` varchar(80) DEFAULT NULL,
  `operation_type` varchar(50) DEFAULT NULL,
  `receiver_bank_account_nb` varchar(27) DEFAULT NULL,
  `receiver_id` int DEFAULT NULL,
  `sender_bank_account_nb` varchar(27) DEFAULT NULL,
  `sender_id` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_transaction`
--

LOCK TABLES `app_transaction` WRITE;
/*!40000 ALTER TABLE `app_transaction` DISABLE KEYS */;
INSERT INTO `app_transaction` VALUES (1,500,0,'19-05-2021','fund','fund','FR7612341234123412341234123',3,'FR7612341234123412341234123',3),(2,800,0,'20-06-2021','fund','fund','FR7612341234123412341234123',3,'FR7612341234123412341234123',3),(3,800,0,'20-06-2021','fund','fund','FR7612341234123412341234123',3,'FR7612341234123412341234123',3),(4,60,0,'21-06-2021','fund','fund','FR4442424242424242424242424',9,'FR4442424242424242424242424',9),(5,10,0,'21-06-2021','fund','fund','FR6363636363636363636363636',9,'FR6363636363636363636363636',9),(6,343,0,'21-06-2021','fund','fund','FR6363636363636363636363636',9,'FR6363636363636363636363636',9),(7,50,0.25,'21-06-2021','gift Paul','payment','FR8112341234123412341234124',4,'FR7612341234123412341234123',3),(8,500,0,'21-06-2021','fund','fund','FR7612341234123412341234123',3,'FR7612341234123412341234123',3),(9,11,0.05,'22-06-2021','TRANSPORT','payment','FR6363636363636363636363636',9,'FR7612341234123412341234123',3),(10,42,0.21,'22-06-2021','cinema','payment','FR8112341234123412341234124',4,'FR7612341234123412341234123',3),(11,55,0.28,'22-06-2021','ff','payment','FR8112341234123412341234124',4,'FR7612341234123412341234123',3),(12,0.01,0,'22-06-2021','ttt','payment','FR6363636363636363636363636',9,'FR7612341234123412341234123',3),(13,0.31,0,'22-06-2021','gg','payment','FR8112341234123412341234124',4,'FR7612341234123412341234123',3),(14,0.57,0,'22-06-2021','gift Bruno','payment','FR8112341234123412341234124',4,'FR7612341234123412341234123',3),(15,100,0.5,'22-06-2021','withdraw','withdraw','FR7612341234123412341234123',3,'FR7612341234123412341234123',3);
/*!40000 ALTER TABLE `app_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_transaction_bank_accounts`
--

DROP TABLE IF EXISTS `app_transaction_bank_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_transaction_bank_accounts` (
  `app_transactions_transaction_id` int NOT NULL,
  `bank_accounts_user_id` int NOT NULL,
  KEY `FKktspv77610g9v56xsn6oawvoa` (`bank_accounts_user_id`),
  KEY `FKtiep16it58d2l0t334jbxjmpl` (`app_transactions_transaction_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_transaction_bank_accounts`
--

LOCK TABLES `app_transaction_bank_accounts` WRITE;
/*!40000 ALTER TABLE `app_transaction_bank_accounts` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_transaction_bank_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `user_id` int NOT NULL,
  `email` varchar(115) DEFAULT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `iban` varchar(27) DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `username` varchar(65) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_1j9d9a06i600gd43uu3km82jw` (`email`),
  KEY `FK49hx9nj6onfot1fxtonj986ab` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (8,'CSMITH@GMAIL.COM','SMITH','FR1111112222223333333333333','CLAUDE','$2a$10$1j6F3eEzB6iHP2NfTK70WORtOeRhB4jVPzip5fJ5VZwtTENnY4uUW','CSMITH@GMAIL.COM',2),(1,'admin@gmail.com','Admin','FR761234123412341234999999','Admin','$2a$10$bpNMKeaQXKpJ4JVxOHWvu.tZdmCLT9nKcZreJ/ELfCgmTCyhC7GPy','admin',1),(2,'user@gmail.com','User','FR761234123412341234888888','User','$2a$10$TA.UfUqLa8uDeGkt95FfLeq7T5Y5vpDpzAtvJrHSLzLliY/PARXUq','user',2),(3,'fgarnier@hotmail.com','Fabrice','FR7612341234123412341234123','Garnier','$2a$10$TA.UfUqLa8uDeGkt95FfLeq7T5Y5vpDpzAtvJrHSLzLliY/PARXUq','fgarnier@hotmail.com',2),(4,'pmartin@gmail.com','Pierre','FR8112341234123412341234124','Martin','$2a$10$TA.UfUqLa8uDeGkt95FfLeq7T5Y5vpDpzAtvJrHSLzLliY/PARXUq','pmartin@gmail.com',2),(9,'jp@gmail.com','jean','FR6363636363636363636363636','PIERRE','$2a$10$lIESLOQe8qxXLcUoijLKrOZxWW2zFfdwB6VoGwmk7TiWsbbpOIK/C','jp@gmail.com',2),(10,'jcalment@gmail.com','Jeanne','FR8484848484844444333322222','CALMENT','$2a$10$.1Ut5iIlcPEyE/khInXyTu7qxtoJ9ByI5f8U0mMRSJEKFScQaA2pa','jcalment@gmail.com',2);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_user_user_contacts`
--

DROP TABLE IF EXISTS `app_user_user_contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user_user_contacts` (
  `app_user_user_id` int NOT NULL,
  `user_contacts_user_id` int NOT NULL,
  KEY `FK58c088jwc88n59k1p3d1hw9er` (`user_contacts_user_id`),
  KEY `FKq854md4avfc23oxtjntlufro6` (`app_user_user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user_user_contacts`
--

LOCK TABLES `app_user_user_contacts` WRITE;
/*!40000 ALTER TABLE `app_user_user_contacts` DISABLE KEYS */;
INSERT INTO `app_user_user_contacts` VALUES (9,3),(3,9),(3,4),(3,10);
/*!40000 ALTER TABLE `app_user_user_contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `user_id` int NOT NULL,
  `balance` float DEFAULT NULL,
  `holder` varchar(80) DEFAULT NULL,
  `iban` varchar(27) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `iban` (`iban`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES (8,0,'Mr SMITH','FR1111112222223333333333333'),(1,0,'ADMIN','FR761234123412341234999999'),(2,0,'USER','FR761234123412341234888888'),(3,1839.82,'MR GARNIER FABRICE','FR7612341234123412341234123'),(4,147.88,'MR MARTIN PIERRE','FR8112341234123412341234124'),(9,424.01,'MR SSST','FR6363636363636363636363636'),(10,0,'MME CALMENT JEANNE','FR8484848484844444333322222');
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
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
INSERT INTO `hibernate_sequence` VALUES (11);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `role_name` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('ee32ca9e-c638-4bdc-b8b4-e831c80d9321','5de9d1ce-4163-4be3-9f37-7db68dec40e6',1624262097653,1624325765796,30000,1624355765796,'fgarnier@hotmail.com');
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
INSERT INTO `spring_session_attributes` VALUES ('ee32ca9e-c638-4bdc-b8b4-e831c80d9321','SPRING_SECURITY_CONTEXT',_binary '¨\Ì\0sr\0=org.springframework.security.core.context.SecurityContextImpl\0\0\0\0\0\0\0L\0authenticationt\02Lorg/springframework/security/core/Authentication;xpsr\0Oorg.springframework.security.authentication.UsernamePasswordAuthenticationToken\0\0\0\0\0\0\0L\0credentialst\0Ljava/lang/Object;L\0	principalq\0~\0xr\0Gorg.springframework.security.authentication.AbstractAuthenticationToken”™(~nGd\0Z\0\rauthenticatedL\0authoritiest\0Ljava/util/Collection;L\0detailsq\0~\0xpsr\0&java.util.Collections$UnmodifiableList¸%1µ\Ïé\0L\0listt\0Ljava/util/List;xr\0,java.util.Collections$UnmodifiableCollectionB\0Ä\À^˜\0L\0cq\0~\0xpsr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0sr\0Borg.springframework.security.core.authority.SimpleGrantedAuthority\0\0\0\0\0\0\0L\0rolet\0Ljava/lang/String;xpt\0	ROLE_USERxq\0~\0\rsr\0Horg.springframework.security.web.authentication.WebAuthenticationDetails\0\0\0\0\0\0\0L\0\rremoteAddressq\0~\0L\0	sessionIdq\0~\0xpt\00:0:0:0:0:0:0:1ppsr\02org.springframework.security.core.userdetails.User\0\0\0\0\0\0\0Z\0accountNonExpiredZ\0accountNonLockedZ\0credentialsNonExpiredZ\0enabledL\0authoritiest\0Ljava/util/Set;L\0passwordq\0~\0L\0usernameq\0~\0xpsr\0%java.util.Collections$UnmodifiableSetÄí—èõÄU\0\0xq\0~\0\nsr\0java.util.TreeSet›òPìï\Ìá[\0\0xpsr\0Forg.springframework.security.core.userdetails.User$AuthorityComparator\0\0\0\0\0\0\0\0xpw\0\0\0q\0~\0xpt\0fgarnier@hotmail.com');
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-22  4:40:05
