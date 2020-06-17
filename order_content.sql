CREATE TABLE `order_content` (
  `orderid` int(10) unsigned NOT NULL,
  `productid` int(10) unsigned NOT NULL,
  `quantity` int(10) NOT NULL,
  PRIMARY KEY (`orderid`,`productid`),
  KEY `productid` (`productid`),
  CONSTRAINT `order_content_ibfk_1` FOREIGN KEY (`orderid`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_content_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `products` (`id`)
);
