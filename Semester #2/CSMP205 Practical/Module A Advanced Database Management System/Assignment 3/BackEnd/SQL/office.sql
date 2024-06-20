-- Active: 1718901595292@@127.0.0.1@3306@office
use office;

CREATE TABLE IF NOT EXISTS `dept` (
    `deptID` varchar(5) NOT NULL,
    `deptName` text NOT NULL,
    PRIMARY KEY (`deptID`)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `employee` (
    `empID` varchar(5) NOT NULL,
    `empName` text NOT NULL,
    `empGender` text NOT NULL,
    `empPosition` text NOT NULL,
    `doj` date NOT NULL,
    `deptID` varchar(5) NOT NULL,
    PRIMARY KEY (`empID`),
    FOREIGN KEY (`deptID`) REFERENCES `dept` (`deptID`)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `salary` (
    `sno` int(11) NOT NULL AUTO_INCREMENT,
    `empID` varchar(5) DEFAULT NULL,
    `salAmt` decimal(10, 0) NOT NULL,
    `commAmt` decimal(10, 0) NOT NULL,
    PRIMARY KEY (`sno`),
    FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`)
) DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

COMMIT;