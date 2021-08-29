-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jwd
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `c_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` (`id`, `c_name`) VALUES (1,'Франция'),(2,'США'),(3,'Южная Корея'),(4,'Беларусь'),(5,'Украина'),(6,'fghjk'),(7,'Италия'),(8,'Китай');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `id` int NOT NULL,
  `g_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` (`id`, `g_name`) VALUES (1,'comedy'),(2,'drama'),(3,'romance'),(4,'adventure'),(5,'cartoon'),(6,'detective'),(7,'triller'),(8,'horror');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `m_name` varchar(45) NOT NULL,
  `year` year NOT NULL,
  `description` varchar(2000) NOT NULL,
  `countryId` int NOT NULL,
  `rating` float DEFAULT '0',
  `genreId` int NOT NULL,
  `imagePath` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `films_genre__fk` (`genreId`),
  KEY `movies_country__fk` (`countryId`),
  CONSTRAINT `films_genre__fk` FOREIGN KEY (`genreId`) REFERENCES `genre` (`id`),
  CONSTRAINT `movies_country__fk` FOREIGN KEY (`countryId`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`id`, `m_name`, `year`, `description`, `countryId`, `rating`, `genreId`, `imagePath`) VALUES (13,'Круэлла',2020,'Великобритания, 1960-е годы. Эстелла была необычным ребёнком, и особенно трудно ей было мириться со всякого рода несправедливостью. Вылетев из очередной школы, она с мамой отправляется в Лондон. По дороге они заезжают в особняк известной модельерши по имени Баронесса, где в результате ужасного несчастного случая мама погибает. Добравшись до Лондона, Эстелла знакомится с двумя мальчишками — уличными мошенниками Джаспером и Хорасом.\r\n\r\n10 лет спустя та же компания промышляет на улицах британской столицы мелким воровством, но Эстелла никак не может оставить мечту сделать карьеру в мире моды. Хитростью устроившись в фешенебельный универмаг, девушка привлекает внимание Баронессы, и та берёт её к себе в штат дизайнеров. :)',2,7.25,6,'1618943448-245556070.jpg'),(27,'Леон',1994,'«Леон» – один из самых известных фильмов Люка Бессона, принесший мировую славу Натали Портман и закрепивший за Жаном Рено имя заглавного персонажа. Маленькая Матильда живет в неблагополучной семье, где она никому особенно не нужна: мать с сестрой заняты только своей внешностью, отец и вовсе торгует наркотиками, да еще и ввязывается в авантюру, которая стоит жизни его семье. Матильде везет, ее нет дома, когда жестокий преступник по имени Стэнсфилд приходит и убивает всех ее родных. Ей некуда пойти и не к кому обратиться за помощью, кроме как к дружелюбному соседу по имени Леон. Но Леон тоже не так прост, как кажется: он один из лучших наемных убийц. Но Матильду это не смущает, напротив, она одержима идеей нанять Леона, чтобы тот отомстил за ее семью и убил Стэнсфилда. А пока девочка не нашла деньги на осуществление задуманного, она полна решимости везде сопровождать своего нового друга, который на самом деле вовсе не так уж жесток.',1,6.375,2,'poster.png'),(45,'fb',2001,'&ltscript&gt alert(\"qwer\"); &lt/script&gt hjcvhcbdwkjc  iod oi dsjio  sodi\r\nwhcuiwhcoqwch d owie oiwsdj ci jc jcxc xcdsil po jqo usjoucdp9uju uewe \r\n&ltscript&gt alert(\"qwer\"); &lt/script&gt',2,3.75,1,'s1200.webp');
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS deleteReview_after_delete */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `deleteReview_after_delete` AFTER DELETE ON `movie` FOR EACH ROW BEGIN
    DELETE
    FROM review
    WHERE review.filmId = old.id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `filmId` int NOT NULL,
  `userId` int NOT NULL,
  `value` int NOT NULL,
  `text` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`filmId`,`userId`),
  KEY `nomination_id` (`userId`),
  KEY `film_id` (`filmId`),
  CONSTRAINT `FK_movie` FOREIGN KEY (`filmId`) REFERENCES `movie` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_User` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` (`filmId`, `userId`, `value`, `text`) VALUES (13,1,8,'средний уровень, в целом хорошо'),(13,25,10,'даа'),(13,26,7,''),(13,27,0,'qwertyui'),(13,28,10,'nnn'),(13,29,10,''),(13,32,5,'gfxcfvgbhnjmk,l.;;lkjhgftyuijkl,.;lpoiujhbv@@!~'),(13,38,8,'qwdsxdcsd'),(27,1,10,'super!!!'),(27,25,9,''),(27,26,10,''),(27,27,4,'Мне не понравилось'),(27,28,9,'!'),(27,29,6,''),(27,32,3,'34'),(27,38,0,'a'),(45,1,0,'qwedf'),(45,25,10,'&ltscript&gt alert(\"qwer\"); &lt/script&gt'),(45,32,1,'ertyui'),(45,38,4,'q');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
/*!50032 DROP TRIGGER IF EXISTS after_review_insert */;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_review_insert` AFTER INSERT ON `review` FOR EACH ROW BEGIN
    UPDATE movie
    SET rating = (SELECT AVG(value)
                  FROM review
                  WHERE movie.id = review.filmId)
    WHERE movie.id = NEW.filmId;

    UPDATE user
    SET statusId = CASE
                       WHEN (((SELECT AVG(value) FROM `review` WHERE NEW.filmId = review.filmId) -
                              (SELECT review.value FROM review where filmId = NEW.filmId AND userId = id)) < 2)
                           THEN IF(user.statusId < 4, statusId + 1, 4)
                       WHEN (((SELECT AVG(value) FROM `review` WHERE NEW.filmId = review.filmId) -
                              (SELECT review.value FROM review where filmId = NEW.filmId AND userId = id)) > 5)
                           THEN IF(user.statusId > 1, statusId - 1, 1)
                       ELSE statusId
        END;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `r_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `r_name`) VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(40) NOT NULL,
  `password` varchar(400) NOT NULL,
  `roleId` int NOT NULL DEFAULT '2',
  `statusId` int NOT NULL DEFAULT '1',
  `email` varchar(50) NOT NULL,
  `age` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `users_role__fk` (`roleId`),
  KEY `users_userstatus__fk` (`statusId`),
  CONSTRAINT `users_role__fk` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `users_userstatus__fk` FOREIGN KEY (`statusId`) REFERENCES `userstatus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `login`, `password`, `roleId`, `statusId`, `email`, `age`) VALUES (1,'Dasha','$2a$04$OJRMieZ8b4pE.u.RBlD3rOOOEgeeQIu3At4.60Of95mXoff.tl.H2',1,4,'Dzakhveisha@gmail.com',19),(24,'lol','$2a$04$32ZEvYG.AcTMIGPPPXC6XeYt.uoJHkdstLt9efYx.PD4SOVAAy79W',2,1,'q@q',12),(25,'Brat','$2a$04$Y0N0RnSJ.Iw.jujyxdyX5ubmVpPfHLgQiT/hUkpFXqs8A/6562sGK',2,4,'Dzaka@gmail.com',16),(26,'testUser','$2a$04$0X5LNBg2g0xJX.oeTALQu.WDa4EEDc2TSvq46g5yfSiR3zzrV8NOe',2,4,'Dsha@gmail.com',18),(27,'Kris','$2a$04$Ly.eSp4J8css7cDsr8MhEe9bz8Nx4KA67bdikZduD74JhhQ/msoFy',2,1,'asx@dfb',18),(28,'London','$2a$04$xQSj7DV/Z4tgxo8QxrcMpONKH0azb5rwoWCNhqasqDwHlZ0i8brI2',2,4,'ty2@ef',23),(29,'New','$2a$04$i3I4TSPKuElfJweyRtqZ5OTW0HoTSuB7S1M3PImyw9hDk6HlUJYxO',2,4,'new@we',34),(32,'vaveyko','$2a$04$9JDYKc4z/SD2v1g.0IYEYeFB0SQQaDjQCUnltlSIqwyzJ2fxjGnai',2,3,'zakhve@hh',15),(38,'Test','$2a$04$j6Jis5SfqAM1uXh9e2LaD..itDxevEcY7.tiIS1xx4fA3jgEHmMZq',2,3,'Dzakhveisha@gmail.com',16);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userstatus`
--

DROP TABLE IF EXISTS `userstatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userstatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `s_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userstatus`
--

LOCK TABLES `userstatus` WRITE;
/*!40000 ALTER TABLE `userstatus` DISABLE KEYS */;
INSERT INTO `userstatus` (`id`, `s_name`) VALUES (1,'looser'),(2,'beginner'),(3,'middle'),(4,'expert');
/*!40000 ALTER TABLE `userstatus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-29 14:57:18
