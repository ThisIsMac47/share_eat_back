-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 08, 2016 at 12:05 AM
-- Server version: 5.5.47-0+deb8u1
-- PHP Version: 5.6.17-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dev`
--

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE IF NOT EXISTS `invitations` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `requester` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `receiver` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `type` text COLLATE utf8_bin,
  `meetup` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `state` text COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`id`, `requester`, `receiver`, `type`, `meetup`, `state`) VALUES
('6d5f6ae0-989d-4ec2-b11e-f4b84d0b18fd', '4f2e5f21-affa-4006-9f2c-410b8e3977f0', 'b3a9a13f-785a-4296-afa1-73f0d8e45acd', 'MEETUP', '0a8f6552-ca0d-410f-a0a2-a57180615a74', 'WAITING'),
('8427149d-59af-4422-b847-bf6efc5895c9', '4f2e5f21-affa-4006-9f2c-410b8e3977f0', '0290d0a1-b659-4fb7-b470-95c65d2e9c8e', 'MEETUP', '0a8f6552-ca0d-410f-a0a2-a57180615a74', 'ACCEPTED'),
('e2ea705c-25b4-44da-977a-92efc3a2c9b8', '4f2e5f21-affa-4006-9f2c-410b8e3977f0', '45b43682-0c23-499a-b1f2-5d84b276e4a9', 'MEETUP', '0a8f6552-ca0d-410f-a0a2-a57180615a74', 'WAITING');

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
  `id` varchar(36) NOT NULL,
  `position` text,
  `name` text,
  `image` text,
  `description` text,
  `type` text,
  `mail` text,
  `price` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locations`
--

INSERT INTO `locations` (`id`, `position`, `name`, `image`, `description`, `type`, `mail`, `price`) VALUES
('69d481e5-e79b-11e5-a1a7-fa163e3d7933', '37.17712,-93.17400', 'yolobistrot', 'https://reijosfood.files.wordpress.com/2012/02/tonys_deli_l.jpg?w=510', 'wsh c dla bone', 'bistrot', 'contact@yoloswag.fr', 45),
('dae136dc-d549-40c2-bb8a-05a3a22e1063', '47.08603,132.19759', 'MacDonalds', 'http://s.hswstatic.com/gif/10-fast-food-menu-items-1.jpg', 'c tro bon lol', 'fastfood', 'yolo@swag.fr', 35),
('e8247fd1-e61d-11e5-a1a7-fa163e3d7933', '38.04301,101.38830', 'O''Chinois', 'http://www.jonqueceleste.com/img/slide_1.jpg', 'Wsh ma gueule super resto', 'Chinois', 'contact@thisismac.fr', 35);

-- --------------------------------------------------------

--
-- Table structure for table `meetups`
--

CREATE TABLE IF NOT EXISTS `meetups` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `master` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `name` text COLLATE utf8_bin,
  `state` text COLLATE utf8_bin,
  `date` text COLLATE utf8_bin,
  `location` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `tags` text COLLATE utf8_bin
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `meetups`
--

INSERT INTO `meetups` (`id`, `master`, `name`, `state`, `date`, `location`, `tags`) VALUES
('0a8f6552-ca0d-410f-a0a2-a57180615a74', '4f2e5f21-affa-4006-9f2c-410b8e3977f0', 'Rendez-vous de la mort', 'WAITING', '2016-03-31T21:00', 'e8247fd1-e61d-11e5-a1a7-fa163e3d7933', 'startup,gastronomie');

-- --------------------------------------------------------

--
-- Table structure for table `payements`
--

CREATE TABLE IF NOT EXISTS `payements` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL,
  `user` varchar(36) COLLATE utf8_bin DEFAULT NULL,
  `live` tinyint(1) NOT NULL DEFAULT '0',
  `token` text COLLATE utf8_bin,
  `charge` text COLLATE utf8_bin,
  `meetup` varchar(36) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `payements`
--

INSERT INTO `payements` (`id`, `user`, `live`, `token`, `charge`, `meetup`) VALUES
('fafdda6f-333a-4a2b-9ec5-3d6e9006933c', '0290d0a1-b659-4fb7-b470-95c65d2e9c8e', 0, 'tok_17tQCpCIDM4TkzHyBpEk6LlF', 'ch_17tQCqCIDM4TkzHyYhcoIBtl', '0a8f6552-ca0d-410f-a0a2-a57180615a74');

-- --------------------------------------------------------

--
-- Table structure for table `relations`
--

CREATE TABLE IF NOT EXISTS `relations` (
  `user` varchar(36) CHARACTER SET latin1 NOT NULL,
  `friend` varchar(36) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf32 COLLATE=utf32_unicode_ci;

--
-- Dumping data for table `relations`
--

INSERT INTO `relations` (`user`, `friend`) VALUES
('0290d0a1-b659-4fb7-b470-95c65d2e9c8e', 'b3a9a13f-785a-4296-afa1-73f0d8e45acd');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `value` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`value`) VALUES
('networking'),
('cinema'),
('startup'),
('entrepreneur'),
('finance'),
('partager'),
('gastronomie');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `accesstoken` varchar(32) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `phone` text COLLATE utf8_unicode_ci,
  `mail` text COLLATE utf8_unicode_ci NOT NULL,
  `avatar` text COLLATE utf8_unicode_ci,
  `school` text COLLATE utf8_unicode_ci,
  `description` text COLLATE utf8_unicode_ci,
  `tags` text COLLATE utf8_unicode_ci,
  `job` text COLLATE utf8_unicode_ci,
  `password` text COLLATE utf8_unicode_ci,
  `role` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `age`, `accesstoken`, `phone`, `mail`, `avatar`, `school`, `description`, `tags`, `job`, `password`, `role`) VALUES
('0290d0a1-b659-4fb7-b470-95c65d2e9c8e', 'Valentin', 18, '5kgukkt8c1bubfrkaqhlg948gd', '05500550', 'asas@asas.ass', 'https://scontent-mrs1-1.xx.fbcdn.net/hprofile-xtl1/v/t1.0-1/p160x160/12806023_1045998362113159_2585904765424059194_n.jpg?oh=2a09e0d51a1b10bdf5df93ea2cf540bb&oe=578FE6A5', '42', '42 student and thats pretty cool', 'finance,gastronomie,partager,startup,entrepreneur', 'FullStack Developper', 'lol', 'USER'),
('4f2e5f21-affa-4006-9f2c-410b8e3977f0', 'Alain Térieur', 25, '6mc7ohnd8s1tro0otnkb2em15n', '05500550', 'lol@lol.fr', 'http://www.altomare.fr/wp-content/uploads/2014/05/store-interieur-menuiserie-nord-13-150x150.jpg', 'D&CO', 'je décore les intérieurs, le swag absolue en some', 'startup,finance,entrepreneur,networking', 'Décorateur', '$2a$10$oV.So83MihOZK9Gt5ODOAeRRMenQ7uqvfJI0ZCFQ2F7s7mXKUxBJG', NULL),
('45b43682-0c23-499a-b1f2-5d84b276e4a9', 'Alex Térieur', 31, 'ol1jrg2kur38kkn7a82e086usl', '05500550', 'swag@swag.fr', 'http://www.penseelibre.fr/wp-content/uploads/2012/07/2-cheverny-exterieur-150x150.png', 'LurbanisteDeLextreme', 'je suis urbaniste professionel et fier de l''être', 'finance,networking,startup', 'Urbaniste', '$2a$10$fDqaHndUwrLtE9u0IAwRLexOptaxvBc4TIXtpq.GYdXtdjzOo44WC', NULL),
('1fa71247-e02a-495a-ae50-972348144b6e', 'Fabien', 25, 'qkdai9qtjhnt5m64h98ntvl8kk', '05500550', 'asas.sa@asas.com', 'img/default_avatar.jpg', '42', 'Top', '', 'Entrepreneur', 'lol', NULL),
('b3a9a13f-785a-4296-afa1-73f0d8e45acd', 'Vincent', 0, 'veg6f3l8k2i3b7d1074b6p740', NULL, 'asas.asas@asasa.fr', NULL, NULL, NULL, 'startup', NULL, 'lol', 'USER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `locations`
--
ALTER TABLE `locations`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `meetups`
--
ALTER TABLE `meetups`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payements`
--
ALTER TABLE `payements`
 ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `relations`
--
ALTER TABLE `relations`
 ADD PRIMARY KEY (`user`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`accesstoken`), ADD UNIQUE KEY `name` (`name`,`accesstoken`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
