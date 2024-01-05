USE my_database;
-- supermarket.booth_info definition
CREATE TABLE `booth_info` (
  `BOOTH_ID` int NOT NULL AUTO_INCREMENT,
  `BOOTH_NAME` varchar(100) NOT NULL,
  `UDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`BOOTH_ID`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 DEFAULT CHARSET = utf8mb4;
-- supermarket.campaign definition
CREATE TABLE `campaign` (
  `CAMPAIGN_ID` int NOT NULL AUTO_INCREMENT,
  `CAMPAIGN_NAME` varchar(100) NOT NULL,
  `TARGET_CUSTOMER_LEVEL` varchar(100) NOT NULL,
  `CAMPAIGN_CODE` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `STATUS` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'New, Scheduled, Running, Completed',
  `MSG_CONTENT` varchar(100) NOT NULL,
  `CAMPAIGN_TIMESTAMP` timestamp NOT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`CAMPAIGN_ID`)
) ENGINE = InnoDB AUTO_INCREMENT = 15 DEFAULT CHARSET = utf8mb4;
-- supermarket.categories definition
CREATE TABLE `categories` (
  `CATEGORY` varchar(100) NOT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`CATEGORY`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- supermarket.customer_level definition
CREATE TABLE `customer_level` (
  `LEVEL` varchar(100) NOT NULL,
  PRIMARY KEY (`LEVEL`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- supermarket.measurements definition
CREATE TABLE `measurements` (
  `MEASUREMENT` varchar(100) NOT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`MEASUREMENT`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- supermarket.customers definition
CREATE TABLE `customers` (
  `CUSTOMER_ID` int NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `PHONENUMBERS` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `EMAIL` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `ADDRESS` varchar(200) CHARACTER SET utf8mb4 NOT NULL,
  `LEVEL` varchar(100) NOT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `IS_ACTIVE` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`),
  KEY `customers_FK` (`LEVEL`),
  CONSTRAINT `customers_FK` FOREIGN KEY (`LEVEL`) REFERENCES `customer_level` (`LEVEL`)
) ENGINE = InnoDB AUTO_INCREMENT = 24 DEFAULT CHARSET = utf8mb4;
-- supermarket.items definition
CREATE TABLE `items` (
  `ITEM_ID` int NOT NULL AUTO_INCREMENT,
  `ITEM_NAME` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `CATEGORY` varchar(100) NOT NULL,
  `MEASUREMENT` varchar(100) NOT NULL,
  `QUANTITY` int NOT NULL,
  `UNIT_PRICE` float NOT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ITEM_ID`),
  KEY `items_FK_1` (`CATEGORY`),
  KEY `items_FK` (`MEASUREMENT`),
  CONSTRAINT `items_FK` FOREIGN KEY (`MEASUREMENT`) REFERENCES `measurements` (`MEASUREMENT`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `items_FK_1` FOREIGN KEY (`CATEGORY`) REFERENCES `categories` (`CATEGORY`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET = utf8mb4;
-- supermarket.`user` definition
CREATE TABLE `user` (
  `USER_ID` int NOT NULL AUTO_INCREMENT,
  `USER_NAME` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `BOOTH_ID` int DEFAULT NULL,
  `UPDATED_DATE` timestamp NULL DEFAULT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `ROLE` varchar(100) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `user_FK` (`BOOTH_ID`),
  CONSTRAINT `user_FK` FOREIGN KEY (`BOOTH_ID`) REFERENCES `booth_info` (`BOOTH_ID`)
) ENGINE = InnoDB AUTO_INCREMENT = 16 DEFAULT CHARSET = utf8mb4;
-- supermarket.customer_invoice definition
CREATE TABLE `customer_invoice` (
  `CUSTOMER_ID` int NOT NULL,
  `INVOICE_ID` int NOT NULL AUTO_INCREMENT,
  `TRADING_TIME` timestamp NOT NULL,
  `BOOTH_ID` int NOT NULL,
  `TOTAL` float NOT NULL,
  `UPDATED_DATE` timestamp NOT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  `USER_ID` int DEFAULT NULL,
  PRIMARY KEY (`INVOICE_ID`),
  KEY `customer_invoice_FK_1` (`BOOTH_ID`),
  KEY `customer_invoice_FK` (`CUSTOMER_ID`),
  KEY `customer_invoice_FK_2` (`USER_ID`),
  CONSTRAINT `customer_invoice_FK` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customers` (`CUSTOMER_ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `customer_invoice_FK_1` FOREIGN KEY (`BOOTH_ID`) REFERENCES `booth_info` (`BOOTH_ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `customer_invoice_FK_2` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE = InnoDB AUTO_INCREMENT = 59 DEFAULT CHARSET = utf8mb4;
-- supermarket.invoice_detail definition
CREATE TABLE `invoice_detail` (
  `INVOICE_ID` int NOT NULL,
  `ITEM_ID` int NOT NULL,
  `ITEM_NAME` varchar(100) NOT NULL,
  `QUANTITY` int NOT NULL,
  `UNIT_PRICE` float NOT NULL,
  `UPDATED_DATE` timestamp NOT NULL,
  `CREATED_DATE` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`INVOICE_ID`, `ITEM_ID`),
  KEY `invoice_detail_FK_1` (`ITEM_ID`),
  CONSTRAINT `invoice_detail_FK` FOREIGN KEY (`INVOICE_ID`) REFERENCES `customer_invoice` (`INVOICE_ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `invoice_detail_FK_1` FOREIGN KEY (`ITEM_ID`) REFERENCES `items` (`ITEM_ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
-- supermarket.booth_info definition
INSERT INTO `booth_info` (
    `BOOTH_ID`,
    `BOOTH_NAME`,
    `UDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (NULL, 'Booth 1', NULL, NULL);
INSERT INTO `booth_info` (
    `BOOTH_ID`,
    `BOOTH_NAME`,
    `UDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (NULL, 'Booth 2', NULL, NULL);
INSERT INTO `booth_info` (
    `BOOTH_ID`,
    `BOOTH_NAME`,
    `UDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (NULL, 'Booth 3', NULL, NULL);
-- supermarket.user definition
INSERT INTO `user` (
    `USER_ID`,
    `USER_NAME`,
    `PASSWORD`,
    `BOOTH_ID`,
    `UPDATED_DATE`,
    `CREATED_DATE`,
    `ROLE`
  )
VALUES (NULL, 'admin', 'admin', '5', NULL, NULL, 'ADMIN');
-- SALE_PERSON
INSERT INTO `user` (
    `USER_ID`,
    `USER_NAME`,
    `PASSWORD`,
    `BOOTH_ID`,
    `UPDATED_DATE`,
    `CREATED_DATE`,
    `ROLE`
  )
VALUES (
    NULL,
    'sale_person',
    'sale_person',
    '6',
    NULL,
    NULL,
    'SALE_PERSON'
  );
-- supermarket.customer_level definition
INSERT INTO `customer_level` (`LEVEL`)
VALUES ('NEW');
INSERT INTO `customer_level` (`LEVEL`)
VALUES ('SILVER');
INSERT INTO `customer_level` (`LEVEL`)
VALUES ('GOLD');
INSERT INTO `customer_level` (`LEVEL`)
VALUES ('DIAMOND');
INSERT INTO `customer_level` (`LEVEL`)
VALUES ('VIP');
-- supermarket.categories definition
INSERT INTO `categories` (`CATEGORY`)
VALUES ('FOOD');
INSERT INTO `categories` (`CATEGORY`)
VALUES ('DRINK');
INSERT INTO `categories` (`CATEGORY`)
VALUES ('CLOTHES');
INSERT INTO `categories` (`CATEGORY`)
VALUES ('ELECTRONIC');
INSERT INTO `categories` (`CATEGORY`)
VALUES ('OTHERS');
-- supermarket.measurements definition
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('KG');
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('L');
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('M');
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('CM');
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('MM');
INSERT INTO `measurements` (`MEASUREMENT`)
VALUES ('UNIT');
-- supermarket.items definition
INSERT INTO `items` (
    `ITEM_ID`,
    `ITEM_NAME`,
    `CATEGORY`,
    `MEASUREMENT`,
    `QUANTITY`,
    `UNIT_PRICE`,
    `UPDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (
    NULL,
    'Coca Cola',
    'DRINK',
    'L',
    '100',
    '1.5',
    NULL,
    NULL
  );
INSERT INTO `items` (
    `ITEM_ID`,
    `ITEM_NAME`,
    `CATEGORY`,
    `MEASUREMENT`,
    `QUANTITY`,
    `UNIT_PRICE`,
    `UPDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (
    NULL,
    'Pepsi',
    'DRINK',
    'L',
    '100',
    '1.5',
    NULL,
    NULL
  );
INSERT INTO `items` (
    `ITEM_ID`,
    `ITEM_NAME`,
    `CATEGORY`,
    `MEASUREMENT`,
    `QUANTITY`,
    `UNIT_PRICE`,
    `UPDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (
    NULL,
    'Fanta',
    'DRINK',
    'L',
    '100',
    '1.5',
    NULL,
    NULL
  );
INSERT INTO `items` (
    `ITEM_ID`,
    `ITEM_NAME`,
    `CATEGORY`,
    `MEASUREMENT`,
    `QUANTITY`,
    `UNIT_PRICE`,
    `UPDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (
    NULL,
    'Sprite',
    'DRINK',
    'L',
    '100',
    '1.5',
    NULL,
    NULL
  );
INSERT INTO `items` (
    `ITEM_ID`,
    `ITEM_NAME`,
    `CATEGORY`,
    `MEASUREMENT`,
    `QUANTITY`,
    `UNIT_PRICE`,
    `UPDATED_DATE`,
    `CREATED_DATE`
  )
VALUES (
    NULL,
    'Pepsi',
    'DRINK',
    'L',
    '100',
    '1.5',
    NULL,
    NULL
  );