-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbsysteminfo
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.9-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbclientes`
--

DROP TABLE IF EXISTS `tbclientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbclientes` (
  `idpes` int(11) NOT NULL AUTO_INCREMENT,
  `pesDataCad` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pesTipo` varchar(11) NOT NULL,
  `pesSexo` varchar(11) NOT NULL,
  `pesNome` varchar(100) NOT NULL,
  `pesDataNasc` varchar(11) NOT NULL,
  `pesCPF_CNPJ` varchar(14) NOT NULL,
  `pesRG_IE` varchar(15) NOT NULL,
  `pesFantasia` varchar(25) DEFAULT NULL,
  `pesEMAIL` varchar(100) DEFAULT NULL,
  `pesTelefone` varchar(15) DEFAULT NULL,
  `pesCelular` varchar(15) DEFAULT NULL,
  `pesEndereco` varchar(40) NOT NULL,
  `pesEndNum` varchar(6) NOT NULL,
  `pesEndComp` varchar(20) DEFAULT NULL,
  `pesBairro` varchar(40) DEFAULT NULL,
  `pesNaturalidade` varchar(50) DEFAULT NULL,
  `pesEstadoCivil` varchar(15) NOT NULL,
  `pesObservacao` varchar(300) DEFAULT NULL,
  `idcidade` int(11) NOT NULL,
  `idconv` int(11) DEFAULT NULL,
  `pesSenha` varchar(14) DEFAULT NULL,
  `pesAtivo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`idpes`),
  KEY `idcidade` (`idcidade`),
  KEY `idconv` (`idconv`),
  CONSTRAINT `tbclientes_ibfk_1` FOREIGN KEY (`idcidade`) REFERENCES `tbcidade` (`idcidade`),
  CONSTRAINT `tbclientes_ibfk_2` FOREIGN KEY (`idconv`) REFERENCES `tbconvenios` (`idconv`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbclientes`
--

LOCK TABLES `tbclientes` WRITE;
/*!40000 ALTER TABLE `tbclientes` DISABLE KEYS */;
INSERT INTO `tbclientes` VALUES (2,'2019-03-08 04:00:00','A','M','Ederson','1982-12-09','70487340272','566379','Nogara','tes@tes.com','12121','23232','rua a','222','','jd','Cascavel','C','',1,1,'','A');
/*!40000 ALTER TABLE `tbclientes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-05  7:50:33
