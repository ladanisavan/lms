/*
SQLyog Community v12.4.2 (64 bit)
MySQL - 5.6.19 : Database - jpa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jpa` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `jpa`;

/*Table structure for table `emp_addr` */

DROP TABLE IF EXISTS `emp_addr`;

CREATE TABLE `emp_addr` (
  `ID` bigint(20) NOT NULL,
  `ADDRESS1` varchar(50) NOT NULL,
  `ADDRESS2` varchar(50) DEFAULT NULL,
  `CITY` varchar(50) NOT NULL,
  `COUNTRY` varchar(50) NOT NULL,
  `STATE` varchar(50) NOT NULL,
  `ZIPCODE` varchar(6) DEFAULT NULL,
  `EMP_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKfwvti8lg222ygbnvad39xdt9q` (`EMP_ID`),
  CONSTRAINT `FKfwvti8lg222ygbnvad39xdt9q` FOREIGN KEY (`EMP_ID`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `emp_addr` */

insert  into `emp_addr`(`ID`,`ADDRESS1`,`ADDRESS2`,`CITY`,`COUNTRY`,`STATE`,`ZIPCODE`,`EMP_ID`) values 
(1,'680 Gale Place',NULL,'Yonkers','United States','New York','31098',1),
(2,'47 Acker Plaza','54723 Grasskamp Plaza','Seattle','United States','Washington','56772',2),
(3,'2 Union Parkway',NULL,'Philadelphia','United States','Pennsylvania','37665',3),
(4,'93 Continental Junction',NULL,'Milwaukee','United States','Wisconsin','71599',4),
(5,'5367 Briar Crest Street','507 Miller Street','Omaha','United States','Nebraska','88762',5),
(6,'5751 Bashford Plaza',NULL,'Birmingham','United States','Alabama','77757',6),
(7,'442 Vernon Junction','7 Anhalt Pass','Alexandria','United States','Virginia','82785',7),
(8,'15824 Kipling Way','531 Tennyson Parkway','Fayetteville','United States','North Carolina','88119',8),
(9,'717 Bobwhite Parkway','86749 Bluejay Avenue','Buffalo','United States','New York','63829',9),
(10,'0059 Brown Circle',NULL,'Pittsburgh','United States','Pennsylvania','97665',10),
(11,'51 School Court','9044 Novick Court','Anderson','United States','South Carolina','13469',11),
(12,'88988 Lighthouse Bay Alley',NULL,'Charlotte','United States','North Carolina','18356',12),
(13,'9195 Northwestern Pass','0165 Carey Drive','Winston Salem','United States','North Carolina','63587',13),
(14,'4 Towne Plaza',NULL,'Tampa','United States','Florida','56439',14),
(15,'65802 Macpherson Road',NULL,'Abilene','United States','Texas','80444',15);

/*Table structure for table `emp_leave_balance` */

DROP TABLE IF EXISTS `emp_leave_balance`;

CREATE TABLE `emp_leave_balance` (
  `ID` bigint(20) NOT NULL,
  `CL` float NOT NULL,
  `CO` float NOT NULL,
  `PH` float NOT NULL,
  `EMP_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK3chpnr58m9q3u772xbs0g66w6` (`EMP_ID`),
  CONSTRAINT `FK3chpnr58m9q3u772xbs0g66w6` FOREIGN KEY (`EMP_ID`) REFERENCES `employee` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `emp_leave_balance` */

insert  into `emp_leave_balance`(`ID`,`CL`,`CO`,`PH`,`EMP_ID`) values 
(1,18.96,11,1,1),
(2,10.92,15,1,2),
(3,10.51,21,0,3),
(4,22.01,27,0,4),
(5,16.94,7,0,5),
(6,7.56,20,1,6),
(7,19.69,30,0,7),
(8,20.54,14,1,8),
(9,6,29,0,9),
(10,20.68,15,1,10),
(11,11.19,19,1,11),
(12,20.07,24,0,12),
(13,15.95,6,1,13),
(14,6.97,13,1,14),
(15,17.17,23,0,15);

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `ID` bigint(20) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) NOT NULL,
  `EMP_ID` varchar(50) NOT NULL,
  `EMAIL_ID` varchar(255) NOT NULL,
  `DESIGNATION` enum('Software Engineer','Jr. Software Engineer','Sr. Software Engineer','Sr. Software Engineer - Team Lead') NOT NULL,
  `DOB` date NOT NULL,
  `GENDER` enum('Male','Female') NOT NULL,
  `CELL_NO` varchar(10) DEFAULT NULL,
  `JOINING_DATE` date NOT NULL,
  `LEAVING_DATE` date DEFAULT NULL,
  `ACTIVE` tinyint(1) NOT NULL DEFAULT '0',
  `CREATED_BY` varchar(50) NOT NULL,
  `CREATED_DATE` date NOT NULL,
  `UPDATED_BY` varchar(50) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNIQUE_EMAIL` (`EMAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`ID`,`FIRST_NAME`,`MIDDLE_NAME`,`LAST_NAME`,`EMP_ID`,`EMAIL_ID`,`DESIGNATION`,`DOB`,`GENDER`,`CELL_NO`,`JOINING_DATE`,`LEAVING_DATE`,`ACTIVE`,`CREATED_BY`,`CREATED_DATE`,`UPDATED_BY`,`UPDATED_DATE`,`UPDATE_DATE`) values 
(1,'Savan','D','Ladani','EMP20','savan.ladani@nividous.com','Sr. Software Engineer - Team Lead','1987-12-25','Male','9909007671','2014-05-25',NULL,1,'Ankur Shah','2014-05-25',NULL,NULL,NULL),
(2,'Les','A','Froome','EMP21','lfroome0@cnbc.com','Sr. Software Engineer','2016-01-20','Male','9162622846','2015-11-13',NULL,0,'Ankur Shah','2015-11-13',NULL,NULL,NULL),
(3,'Sayers',NULL,'Keough','EMP22','skeough1@mysql.com','Sr. Software Engineer','2015-02-25','Male','9249275501','2015-08-10',NULL,1,'Ankur Shah','2015-08-10',NULL,NULL,NULL),
(4,'Garek','C','Dilleston','EMP23','gdilleston2@rambler.ru','Sr. Software Engineer','2017-01-17','Male','8783075801','2016-03-10',NULL,1,'Ankur Shah','2016-03-10',NULL,NULL,NULL),
(5,'Fern',NULL,'Mabey','EMP24','fmabey3@miibeian.gov.cn','Sr. Software Engineer','2014-12-20','Female','6141886647','2014-12-02',NULL,1,'Ankur Shah','2014-12-02',NULL,NULL,NULL),
(6,'Purcell','E','Reding','EMP25','preding4@dion.ne.jp','Sr. Software Engineer','2015-08-26','Male','5193726410','2015-10-24',NULL,1,'Ankur Shah','2015-10-24',NULL,NULL,NULL),
(7,'Goldie','Zita','Fretson','EMP26','zfretson0@netvibes.com','Sr. Software Engineer','2015-03-23','Female','5993831995','2018-04-18',NULL,0,'Ankur Shah','2018-04-18',NULL,NULL,NULL),
(8,'Gunther','Erl','Beincken','EMP27','ebeincken1@is.gd','Sr. Software Engineer','2014-09-23','Male','3330746335','2017-09-22',NULL,0,'Ankur Shah','2017-09-22',NULL,NULL,NULL),
(9,'Glad','Ethelda','Woosnam','EMP28','ewoosnam2@ow.ly','Sr. Software Engineer','2016-11-27','Female','2801970492','2018-04-23',NULL,0,'Ankur Shah','2018-04-23',NULL,NULL,NULL),
(10,'Cobbie','Shepherd','Anthoin','EMP29','santhoin3@netvibes.com','Sr. Software Engineer','2015-09-02','Male','8005368267','2018-03-29',NULL,0,'Ankur Shah','2018-03-29',NULL,NULL,NULL),
(11,'Antonin',NULL,'Jewsbury','EMP30','tjewsbury4@sourceforge.net','Sr. Software Engineer','2014-11-26','Male','3160808327','2018-05-03',NULL,0,'Ankur Shah','2018-05-03',NULL,NULL,NULL),
(12,'Nedda','Minne','Cockton','EMP31','mcockton5@lycos.com','Sr. Software Engineer','2013-05-06','Female','3035925828','2017-12-13',NULL,0,'Ankur Shah','2017-12-13',NULL,NULL,NULL),
(13,'Amalie',NULL,'Breagan','EMP32','rbreagan6@indiegogo.com','Sr. Software Engineer','2011-12-20','Female','3702183582','2017-10-01',NULL,0,'Ankur Shah','2017-10-01',NULL,NULL,NULL),
(14,'Elvis','Syd','Smorthit','EMP33','ssmorthit7@google.es','Sr. Software Engineer','2016-05-31','Male','2075480001','2018-04-02',NULL,0,'Ankur Shah','2018-04-02',NULL,NULL,NULL),
(15,'Kevan',NULL,'Ottee','EMP34','uottee8@cnet.com','Sr. Software Engineer','2012-10-05','Male','4524078630','2017-11-19',NULL,0,'Ankur Shah','2017-11-19',NULL,NULL,NULL);

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values 
(2),
(2);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`role_id`,`role`) values 
(1,'ADMIN'),
(2,'USER');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`user_id`,`active`,`email`,`last_name`,`name`,`password`) values 
(1,1,'abc@gmail.com','abc','abc','$2a$10$TV64IQsoNq3xfCLnIo81IuMU8FSdXBMlEtuP7335u1.7Lh90BCjHO');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values 
(1,1),
(1,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
