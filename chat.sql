-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 24-11-2012 a las 14:10:41
-- Versión del servidor: 5.1.41
-- Versión de PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `actores`
--
CREATE DATABASE IF NOT EXISTS chat;
USE chat;
	
CREATE TABLE IF NOT EXISTS `usuarios` (
   `nombrepc` varchar(500) NOT NULL,
  `ip` varchar(500) NOT NULL,
  `nombreusuario` varchar(500) NOT NULL,
  `contrasena` varchar(500) NOT NULL
  
 
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


