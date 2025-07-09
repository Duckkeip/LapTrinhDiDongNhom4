-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2025 at 11:43 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lms`
--

-- --------------------------------------------------------

--
-- Table structure for table `giaodich`
--

CREATE TABLE `giaodich` (
  `id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `note` text DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `user` varchar(255) NOT NULL,
  `type` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `giaodich`
--

INSERT INTO `giaodich` (`id`, `amount`, `note`, `date`, `category_id`, `user`, `type`) VALUES
(12, 100, 'toi', '2025-07-07 17:54:50', 4, 'dmc', 'expense'),
(19, 1000, 'toideptrai', '2025-07-07 18:42:24', 2, 'dmc', 'income'),
(20, 10000, 'abc', '2025-07-07 18:51:00', 4, 'dmc', 'expense'),
(21, 1213123, 'sss', '2025-07-08 00:44:59', 2, 'dyc', 'income'),
(22, 1111, 'xx', '2025-07-08 00:45:47', 4, 'dyc', 'expense'),
(23, 5656565, 'qqqq', '2025-07-08 01:38:35', 1, 'dmc', 'income'),
(26, 5001, 'abc', '2025-07-09 02:14:51', 3, 'dmc', 'expense'),
(27, 5000, 'xyz', '2025-07-09 02:14:59', 3, 'dmc', 'expense');

-- --------------------------------------------------------

--
-- Table structure for table `giaodich_tag`
--

CREATE TABLE `giaodich_tag` (
  `transaction_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loaichitieu`
--

CREATE TABLE `loaichitieu` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `type` text NOT NULL CHECK (`type` in ('income','expense'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `loaichitieu`
--

INSERT INTO `loaichitieu` (`id`, `name`, `type`) VALUES
(1, 'Lương', 'income'),
(2, 'Thưởng', 'income'),
(3, 'Ăn uống', 'expense'),
(4, 'Giải trí', 'expense');

-- --------------------------------------------------------

--
-- Table structure for table `ngansach`
--

CREATE TABLE `ngansach` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `role` varchar(10) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `role`) VALUES
('dmc', '123456', 'user'),
('dyc', '123456', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `vi`
--

CREATE TABLE `vi` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `balance` double DEFAULT 0,
  `user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `giaodich`
--
ALTER TABLE `giaodich`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `giaodich_tag`
--
ALTER TABLE `giaodich_tag`
  ADD PRIMARY KEY (`transaction_id`,`tag_id`),
  ADD KEY `tag_id` (`tag_id`);

--
-- Indexes for table `loaichitieu`
--
ALTER TABLE `loaichitieu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ngansach`
--
ALTER TABLE `ngansach`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `vi`
--
ALTER TABLE `vi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `giaodich`
--
ALTER TABLE `giaodich`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `loaichitieu`
--
ALTER TABLE `loaichitieu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `ngansach`
--
ALTER TABLE `ngansach`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tag`
--
ALTER TABLE `tag`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vi`
--
ALTER TABLE `vi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `giaodich`
--
ALTER TABLE `giaodich`
  ADD CONSTRAINT `giaodich_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `loaichitieu` (`id`),
  ADD CONSTRAINT `giaodich_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`username`);

--
-- Constraints for table `giaodich_tag`
--
ALTER TABLE `giaodich_tag`
  ADD CONSTRAINT `giaodich_tag_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `giaodich` (`id`),
  ADD CONSTRAINT `giaodich_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`);

--
-- Constraints for table `ngansach`
--
ALTER TABLE `ngansach`
  ADD CONSTRAINT `ngansach_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `loaichitieu` (`id`),
  ADD CONSTRAINT `ngansach_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`username`);

--
-- Constraints for table `vi`
--
ALTER TABLE `vi`
  ADD CONSTRAINT `vi_ibfk_1` FOREIGN KEY (`user`) REFERENCES `users` (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
