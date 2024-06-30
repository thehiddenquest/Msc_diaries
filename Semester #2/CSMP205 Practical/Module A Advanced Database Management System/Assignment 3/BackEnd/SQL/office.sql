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

INSERT INTO
    `dept` (`deptID`, `deptName`)
VALUES ('D001', 'Operations'),
    ('D002', 'Engineering'),
    ('D003', 'Sales and Marketing'),
    ('D004', 'Quality Assurance'),
    ('D005', 'Product Management'),
    ('D006', 'Technical Support');

ALTER TABLE `salary` add COLUMN `DA` int(3);



ALTER TABLE `employee`
DROP FOREIGN KEY `FK_employee_deptID`, -- Drop existing foreign key constraint (if it exists)
ADD CONSTRAINT `FK_employee_deptID` FOREIGN KEY (`deptID`) REFERENCES `dept` (`deptID`) ON DELETE SET NULL;


ALTER TABLE `salary`
DROP FOREIGN KEY `FK_salary_empID_salary`,  -- Drop existing foreign key constraint (if it exists)
ADD CONSTRAINT `FK_salary_empID` FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`) ON DELETE CASCADE;
COMMIT;