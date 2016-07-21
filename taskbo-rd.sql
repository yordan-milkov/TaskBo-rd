-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 21, 2016 at 11:33 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `taskbo-rd`
--

-- --------------------------------------------------------

--
-- Table structure for table `checks`
--

CREATE TABLE `checks` (
  `issueUID` int(11) NOT NULL,
  `name` varchar(1024) COLLATE utf8_bin NOT NULL,
  `isFinished` tinyint(1) NOT NULL DEFAULT '0',
  `checkUID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `checks`
--

INSERT INTO `checks` (`issueUID`, `name`, `isFinished`, `checkUID`) VALUES
(301, 'Бира', 0, 1),
(301, 'солети', 0, 2),
(300, 'мивка', 0, 3),
(302, 'препарат', 0, 4),
(301, 'захар', 0, 5),
(301, 'ориз', 0, 6);

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE `groups` (
  `UID` int(11) NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `description` varchar(1024) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`UID`, `name`, `description`) VALUES
(100, 'Домашни задължения', 'Проблеми и задачи в домакинството'),
(101, 'Пазарски списъци', 'Продукти и стоки, които трябва да бъдат закупени'),
(102, 'Лични задъжения', 'Мои лични работи');

-- --------------------------------------------------------

--
-- Table structure for table `groupusers`
--

CREATE TABLE `groupusers` (
  `userUID` varchar(32) COLLATE utf8_bin NOT NULL,
  `groupUID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `groupusers`
--

INSERT INTO `groupusers` (`userUID`, `groupUID`) VALUES
('admin', 100),
('admin', 101),
('test', 100),
('test', 101),
('yordanmilkov', 100),
('yordanmilkov', 101),
('yordanmilkov', 102);

-- --------------------------------------------------------

--
-- Table structure for table `issues`
--

CREATE TABLE `issues` (
  `issueUID` int(11) NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `description` varchar(1024) COLLATE utf8_bin NOT NULL,
  `users` varchar(1024) COLLATE utf8_bin NOT NULL DEFAULT ',',
  `groupUID` int(11) NOT NULL,
  `isResolved` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `issues`
--

INSERT INTO `issues` (`issueUID`, `name`, `description`, `users`, `groupUID`, `isResolved`) VALUES
(300, 'Почистване баня', 'Банята трябва да бъде почистена', ',yordanmilkov', 100, 0),
(301, 'Продукти', 'Продукти за закупуване от хранителен магазин', ',test,yordanmilkov', 101, 0),
(302, 'Почистване прозорци', 'прозорците и тераста трябва да бъдат почистени', ',yordanmilkov,admin', 100, 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
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
('admin', 'adminadmin', '000000000', '1234567890', 'Акаунт Админ'),
('test', 'test', 'test', 'test', 'I''m QA'),
('yordanmilkov', 'yordan_milkov@abv.bg', '0897380260', 'mypass789456', 'Йордан Милков');

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
  MODIFY `checkUID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `issues`
--
ALTER TABLE `issues`
  MODIFY `issueUID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=303;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
