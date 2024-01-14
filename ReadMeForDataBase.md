# DataBase 

This a summary of the proccess I went through to set up the DB (data base) on my machine.

[Link To My Notion Notes On Setting Up a DB With IntelliJ](https://www.notion.so/Setting-up-Database-4db9b2cb499645dea5d581a81ec1314e?pvs=4)

## Versions
- RDBMS: [MySQL **8.0.26**](https://dev.mysql.com/downloads/windows/installer/8.0.html) (Relational DataBase Management System)  
  -Selected Products during install:  
  --MySQL Server  
  --MySQL Workbench

- JDBC: [MySQL/Connector/J **8.0.26**](https://dev.mysql.com/downloads/windows/installer/8.0.html) (Java DataBase Connector)
---
## User & DB Configuration
####Instructions to make New User Account & DB:  
**New User:**  
> MySQL Workbench > Home tab > Local instance MySQL80 >"_Left Side View Window_" Navigator -bottomTab-> Administration > Users and Privileges -inNewViewView-> Add Account btn -default-> Login tab >{Set:  
>>- Login Name : `sqlUser`
>>- Authentication Type : `caching_sha2_password`
>>- Limit to Host Matching : `localhost`
>>- Password : `Passw0rd!`
>- _This is to match the WGU Virtual Machine's DB_  
>
> }-> Administrative Roles tab >{ Set:  
> > - DBA : `[On]`  
>
> } -> Apply btn <`done`

**New DB:**  
>MySQL Workbench > Home tab > "Plus icon right of MySQ Connections" >{Set:  
>
> >- connection name to : `C195DBClient`
> >- hostname to : `localhost`
> >- Username to the user we created : `sqlUser`  
>
>}-> test connection btn -ifWorks-> Ok btn <`done`


## Populating DataBase

- DDL = Data Definition Language (Used for Creating tables, DBs, Indexes, etc.) Often used by database admins/professionals.
- DML = Data Manipulation Language (Used for selecting, inserting, deleting, etc.) Often used by program developers.

####How to populate initial DB:  
We are basically copying a functionally Identical DB to our machine to test with.

**Create A new Schema:**  
> MySQL Workbench -> Home tab > `C195DBClient` Connection -loginAs`sqlUser`->"_Left Side View Window_" Navigator -bottomTab-> Schemas < take note of the one schema named:`sys` 
> > This is represented by a stacked cylinder schema symbol.
> 
> Tool Bar > "new schema" -makesNewTab->
> > This is represented by a stacked cylinder schema symbol WITH A PLUS on the bottom just below: 
> > >File - Edit - View - Query 
> 
> client_schedule_schema > {Set:
>  > - name to :`client_schedule`
> 
> You should now see a `client_schedule` schema in the Schemas panel along with the `sys` schema  


**Define the DB by running the DDL Script:**

Make a new query, copy the script below, and run(lightning symbol)  
###### DDL Script (Data Definition Language):
```
USE client_schedule;

/*DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS first_level_divisions;
DROP TABLE IF EXISTS countries;
*/

CREATE TABLE IF NOT EXISTS `countries` (
  `Country_ID` int NOT NULL AUTO_INCREMENT,
  `Country` varchar(50) DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  `Created_By` varchar(50) DEFAULT NULL,
  `Last_Update` timestamp NULL DEFAULT NULL,
  `Last_Updated_By` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Country_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `first_level_divisions` (
  `Division_ID` int NOT NULL AUTO_INCREMENT,
  `Division` varchar(50) DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  `Created_By` varchar(50) DEFAULT NULL,
  `Last_Update` timestamp NULL DEFAULT NULL,
  `Last_Updated_By` varchar(50) DEFAULT NULL,
  `Country_ID` int NOT NULL,
  PRIMARY KEY (`Division_ID`),
  KEY `fk_country_id_idx` (`Country_ID`),
  CONSTRAINT `fk_country_id` FOREIGN KEY (`Country_ID`) REFERENCES `countries` (`Country_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3979 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `customers` (
  `Customer_ID` int NOT NULL AUTO_INCREMENT,
  `Customer_Name` varchar(50) DEFAULT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `Postal_Code` varchar(50) DEFAULT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  `Created_By` varchar(50) DEFAULT NULL,
  `Last_Update` timestamp NULL DEFAULT NULL,
  `Last_Updated_By` varchar(50) DEFAULT NULL,
  `Division_ID` int NOT NULL,
  PRIMARY KEY (`Customer_ID`),
  KEY `fk_division_id_idx` (`Division_ID`),
  CONSTRAINT `fk_division_id` FOREIGN KEY (`Division_ID`) REFERENCES `first_level_divisions` (`Division_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `User_ID` int NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(50) DEFAULT NULL,
  `Password` text,
  `Create_Date` datetime DEFAULT NULL,
  `Created_By` varchar(50) DEFAULT NULL,
  `Last_Update` timestamp NULL DEFAULT NULL,
  `Last_Updated_By` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`User_ID`),
  UNIQUE KEY `User_Name_UNIQUE` (`User_Name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `contacts` (
  `Contact_ID` int NOT NULL AUTO_INCREMENT,
  `Contact_Name` varchar(50) DEFAULT NULL,
  `Email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Contact_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `appointments` (
  `Appointment_ID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(50) DEFAULT NULL,
  `Description` varchar(50) DEFAULT NULL,
  `Location` varchar(50) DEFAULT NULL,
  `Type` varchar(50) DEFAULT NULL,
  `Start` datetime DEFAULT NULL,
  `End` datetime DEFAULT NULL,
  `Create_Date` datetime DEFAULT NULL,
  `Created_By` varchar(50) DEFAULT NULL,
  `Last_Update` timestamp NULL DEFAULT NULL,
  `Last_Updated_By` varchar(50) DEFAULT NULL,
  `Customer_ID` int NOT NULL,
  `User_ID` int NOT NULL,
  `Contact_ID` int NOT NULL,
  PRIMARY KEY (`Appointment_ID`),
  KEY `fk_customer_id_idx` (`Customer_ID`),
  KEY `fk_user_id_idx` (`User_ID`),
  KEY `fk_contact_id_idx` (`Contact_ID`),
  CONSTRAINT `fk_contact_id` FOREIGN KEY (`Contact_ID`) REFERENCES `contacts` (`Contact_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_id` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`Customer_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`User_ID`) REFERENCES `users` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
> Note: you should get successful actions in action output on the bottom panel.

**Populate the DB by Running the DDL Script:**


Make a new query, copy the script below, and run(lightning symbol).

###### DML Script (Data Definition Language):
```
-- 
-- THIS SCRIPT ONLY FOR STUDENTS IN THE LATEST VERSION OF THE ASSESSMENT
-- (Start course on/after 9-15-2020 - PLease check with CI if unsure)
-- This script should be run in the MySql Workbench
-- It will reset the data to insert the countries and first level divisions
-- that you should use in the project
-- NOTE: This Script removes EVERYTHING because of the necessary FK dependencies
-- 
-- questions mark.kinkead@wgu.edu
-- 
USE client_schedule; -- line added by malcolm.wabara@wgu.edu

DELETE FROM appointments WHERE Appointment_ID < 10000;
DELETE FROM customers WHERE Customer_ID < 10000;
DELETE FROM first_level_divisions where Division_ID < 10000;
DELETE FROM countries where Country_ID < 10000;
DELETE FROM users WHERE User_ID < 10000;
DELETE FROM contacts where Contact_ID < 10000;

-- users

INSERT INTO users VALUES(1, 'test', 'test', NOW(), 'script', NOW(), 'script');
INSERT INTO users VALUES(2, 'admin', 'admin', NOW(), 'script', NOW(), 'script');

-- contacts

INSERT INTO contacts VALUES(1,	'Anika Costa', 'acoasta@company.com');
INSERT INTO contacts VALUES(2,	'Daniel Garcia',	'dgarcia@company.com');
INSERT INTO contacts VALUES(3,	'Li Lee',	'llee@company.com');

-- Counties

INSERT INTO countries VALUES(1,	'U.S',	NOW(), 'script', NOW(), 'script');
INSERT INTO countries VALUES(2,	'UK',	NOW(), 'script', NOW(), 'script');
INSERT INTO countries VALUES(3,	'Canada',	NOW(), 'script', NOW(), 'script');

-- First Level Divisions

INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Alabama', 1, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Alaska', 54, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Arizona', 02, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Arkansas', 03, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('California', 04, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Colorado', 05, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Connecticut', 06, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Delaware', 07, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('District of Columbia', 08, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Florida', 09, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Georgia', 10, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Hawaii', 52, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Idaho', 11, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Illinois', 12, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Indiana', 13, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Iowa', 14, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Kansas', 15, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Kentucky', 16, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Louisiana', 17, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Maine', 18, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Maryland', 19, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Massachusetts', 20, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Michigan', 21, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Minnesota', 22, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Mississippi', 23, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Missouri', 24, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Montana', 25, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Nebraska', 26, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Nevada', 27, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('New Hampshire', 28, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('New Jersey', 29, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('New Mexico', 30, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('New York', 31, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('North Carolina', 32, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('North Dakota', 33, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Ohio', 34, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Oklahoma', 35, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Oregon', 36, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Pennsylvania', 37, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Rhode Island', 38, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('South Carolina', 39, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('South Dakota', 40, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Tennessee', 41, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Texas', 42, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Utah', 43, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Vermont', 44, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Virginia', 45, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Washington', 46, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('West Virginia', 47, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Wisconsin', 48, NOW(), 'script', NOW(), 'script', 1 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Wyoming', 49, NOW(), 'script', NOW(), 'script', 1 );



INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Alberta', 61, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('British Columbia', 62, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Manitoba', 63, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('New Brunswick', 64, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Newfoundland and Labrador', 72, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Northwest Territories', 60, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Nova Scotia', 65, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Nunavut', 70, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Ontario', 67, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Prince Edward Island', 66, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Québec', 68, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Saskatchewan', 69, NOW(), 'script', NOW(), 'script', 3 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Yukon', 71, NOW(), 'script', NOW(), 'script', 3 );

INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('England', 101, NOW(), 'script', NOW(), 'script', 2 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Wales', 102, NOW(), 'script', NOW(), 'script', 2 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Scotland',103, NOW(), 'script', NOW(), 'script', 2 );
INSERT INTO first_level_divisions(Division, Division_ID, Create_Date, Created_By, Last_Update, Last_Updated_By, COUNTRY_ID) VALUES('Northern Ireland', 104, NOW(), 'script', NOW(), 'script', 2 );

-- Customers

INSERT INTO customers VALUES(1, 'Daddy Warbucks', '1919 Boardwalk', '01291', '869-908-1875', NOW(), 'script', NOW(), 'script', 29);
INSERT INTO customers VALUES(2, 'Lady McAnderson', '2 Wonder Way', 'AF19B', '11-445-910-2135', NOW(), 'script', NOW(), 'script', 103);
INSERT INTO customers VALUES(3, 'Dudley Do-Right', '48 Horse Manor ', '28198', '874-916-2671', NOW(), 'script', NOW(), 'script', 60);

-- Appointments

INSERT INTO appointments VALUES(1, 'title', 'description', 'location', 'Planning Session', '2020-05-28 12:00:00', '2020-05-28 13:00:00', NOW(), 'script', NOW(), 'script', 1, 1, 3);
INSERT INTO appointments VALUES(2, 'title', 'description', 'location', 'De-Briefing', '2020-05-29 12:00:00', '2020-05-29 13:00:00', NOW(), 'script', NOW(), 'script', 2, 2, 2);
```

> Note: you can refresh in the top right of schemas panel to check if it worked, in addition each table will have a symbol to your right to check their contents.