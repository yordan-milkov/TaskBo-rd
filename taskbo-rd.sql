-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 21, 2017 at 11:27 PM
-- Server version: 5.5.44-0+deb8u1
-- PHP Version: 5.6.22-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `taskbo-rd`
--

-- --------------------------------------------------------

--
-- Table structure for table `checks`
--

CREATE TABLE IF NOT EXISTS `checks` (
  `issueUID` int(11) NOT NULL,
  `name` varchar(1024) COLLATE utf8_bin NOT NULL,
  `isFinished` tinyint(1) NOT NULL DEFAULT '0',
`checkUID` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `checks`
--

INSERT INTO `checks` (`issueUID`, `name`, `isFinished`, `checkUID`) VALUES
(301, 'Солети', 1, 2),
(301, 'Бисквити', 1, 3),
(301, 'Сол', 1, 23),
(301, 'Лук', 0, 24),
(301, 'Сирене', 0, 25),
(301, 'Кашкавал', 1, 27),
(301, 'домати', 1, 28);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
`UID` int(11) NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `description` varchar(1024) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`UID`, `name`, `description`) VALUES
(100, 'sdada', 'asdasda'),
(101, 'пазарски списъци', 'разни пазърлъци а-у'),
(121, 'За колата', 'Неща по колата'),
(123, 'Лични работи', 'мои си неща');

-- --------------------------------------------------------

--
-- Table structure for table `groupusers`
--

CREATE TABLE IF NOT EXISTS `groupusers` (
  `userUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `groupUID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `groupusers`
--

INSERT INTO `groupusers` (`userUID`, `groupUID`) VALUES
('admin', 100),
('admin', 101),
('admin', 123),
('test', 101),
('yordanmilkov', 100),
('yordanmilkov', 101),
('yordanmilkov', 121);

-- --------------------------------------------------------

--
-- Table structure for table `issues`
--

CREATE TABLE IF NOT EXISTS `issues` (
`issueUID` int(11) NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `description` varchar(1024) COLLATE utf8_bin NOT NULL,
  `users` varchar(1024) COLLATE utf8_bin NOT NULL DEFAULT ',',
  `groupUID` int(11) NOT NULL,
  `isResolved` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `issues`
--

INSERT INTO `issues` (`issueUID`, `name`, `description`, `users`, `groupUID`, `isResolved`) VALUES
(300, 'Домашни задължения', 'Да не се пропуска', '', 100, 1),
(301, 'Пазарски списък', 'Нещата, които трябва да се купят', 'yordanmilkov,test,', 101, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `UID` varchar(32) COLLATE utf8_bin NOT NULL,
  `mail` varchar(128) COLLATE utf8_bin NOT NULL,
  `GSM` varchar(16) COLLATE utf8_bin NOT NULL,
  `password` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UID`, `mail`, `GSM`, `password`, `name`) VALUES
('admin', 'adminadmin', '000000000', '1234567890', 'Администратор'),
('test', 'test', 'test', 'test', 'Тестови акаунт'),
('yordanmilkov', 'yordan_milkov@abv.bg', '0897380260', '789456', 'Йордан');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `checks`
--
ALTER TABLE `checks`
 ADD PRIMARY KEY (`checkUID`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
 ADD PRIMARY KEY (`UID`);

--
-- Indexes for table `groupusers`
--
ALTER TABLE `groupusers`
 ADD PRIMARY KEY (`userUID`,`groupUID`);

--
-- Indexes for table `issues`
--
ALTER TABLE `issues`
 ADD PRIMARY KEY (`issueUID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`UID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `checks`
--
ALTER TABLE `checks`
MODIFY `checkUID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=84;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
MODIFY `UID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=124;
--
-- AUTO_INCREMENT for table `issues`
--
ALTER TABLE `issues`
MODIFY `issueUID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=318;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
