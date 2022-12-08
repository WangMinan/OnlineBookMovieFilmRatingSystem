-- MySQL dump 10.13  Distrib 5.7.40, for Win64 (x86_64)
--
-- Host: 121.41.227.153    Database: AssessmentSystem
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Admin` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` varchar(120) NOT NULL,
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES (1,'wangminan','$2a$10$lYJUiQhak9SKSUpDRppvBu3HwJ/PuinroKHtpRDfoPHxFnI8VwCuO',0);
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Assessment`
--

DROP TABLE IF EXISTS `Assessment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Assessment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `objectId` bigint NOT NULL,
  `objectType` varchar(10) NOT NULL COMMENT '评价对象的类型:{"book","film","music"}',
  `assessment` varchar(500) DEFAULT NULL,
  `postDate` varchar(20) NOT NULL COMMENT '发布该条留言的日期',
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`),
  KEY `Assessment_User_username_fk` (`username`),
  CONSTRAINT `Assessment_User_username_fk` FOREIGN KEY (`username`) REFERENCES `User` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Assessment`
--

LOCK TABLES `Assessment` WRITE;
/*!40000 ALTER TABLE `Assessment` DISABLE KEYS */;
INSERT INTO `Assessment` VALUES (1,'wangminan',1,'book','非常好的作品。有东欧的典型特征。是不可多得的佳作.','2022-12-04 17:14:29',0),(2,'wangminan',2,'book','我和苏兹并没有有情人终成眷属。最终命运之旗一挥，我们的感情戛然而止。她选择了一条路，而我选择了另外一条。我们不过是对方生活的过客，但是在此之前，在火苗熄灭之前，我们在西四街的公寓里度过了很长的时光','2022-12-04 14:22:17',0),(3,'wangminan',1,'book','有两种学习方式：从外部学习和从内部学习。前者通常被以为是最好的，或者甚至是唯一的方式。因为人们常常是通过旅行、观察、阅读、上大学、听课来进行学习——他们依赖那些发生在他们身外的事物学习。人时愚蠢的生物，所以必须学习。于是人就像贴金似的往自己身上粘贴知识，像蜜蜂似的搜集知识.','2022-12-05 10:46:30',0),(4,'13777864458',1,'book','这是一个新建账号的测试留言','2022-12-01 20:37:25',0),(5,'wangminan',3,'book','我始终为你而紧张，为你而颤抖；可是你对此毫无感觉，就像你口袋里装了怀表，你对它紧绷的发条没有感觉一样；这根发条在暗中耐心地为你数着你的钟点，计算着你的时间，以它听不见的心跳陪着你东奔西走，','2022-12-03 21:37:18',0),(6,'wangminan',1,'book','hi','2022-12-03 21:39:42',1),(7,'wangminan',2,'book','hello','2022-12-03 21:38:17',1),(8,'jakub',1,'book','hello','2022-12-04 19:26:22',0),(9,'wangminan',1,'book','                                            hihi','2022-12-03 21:40:09',1),(10,'wangminan',1,'book','                                      hh h     ','2022-12-03 21:59:44',1),(11,'wangminan',2,'book','                                        ','2022-12-03 23:14:57',1),(12,'wangminan',1,'book','                                        sddssad','2022-12-03 23:38:50',1),(13,'wangminan',1,'music','哈迷不会不喜欢这首音乐的 ','2022-12-04 01:24:39',0),(14,'wangminan',1,'film',' 最优秀的科幻电影，没有之一。                                      ','2022-12-04 01:24:59',0),(15,'wangminan',1,'film','                                        ','2022-12-04 01:25:03',1),(16,'wangminan',1,'film','                                        ','2022-12-04 01:26:06',1),(17,'wangminan',1,'film','                                        ','2022-12-04 01:26:57',1),(18,'wangminan',1,'music','                                        ','2022-12-04 01:27:19',1),(19,'wangminan',1,'music','                                        ','2022-12-04 01:27:44',1),(20,'wangminan',1,'music','                                        ','2022-12-04 01:28:01',1),(21,'wangminan',1,'music','                                        ','2022-12-04 01:29:01',1),(22,'wangminan',1,'music','                                        ','2022-12-04 01:29:20',1),(23,'wangminan',1,'music','                                        ','2022-12-04 01:31:12',1),(24,'wangminan',1,'book','hello                                        ','2022-12-04 10:55:12',1),(25,'wangminan',1,'film','','2022-12-04 11:04:57',1),(26,'wangminan',1,'film','我想给1000颗星，这可能是我至今看过最令我震撼的电影了。到电影结束时候还不断流泪，奇迹不是因为有上帝 也不是有什么不可知的 某种力量，正是我们自己，人类不相信人类的力量把那些我们无可预知的奇迹都认为是上帝的功劳。若兰把我的世界观如此震撼人心的展现出来，我感觉我现在都激动得语无伦次了               ','2022-12-04 11:28:22',0),(27,'wangminan',1,'music','trytry                                    ','2022-12-04 14:09:53',1),(28,'wangminan',1,'book',' 123                                           ','2022-12-04 14:09:31',1),(29,'anotherTestUserAA',2,'film','今年(2022)最好的科幻电影         ','2022-12-04 22:13:30',0),(30,'lailailai',1,'book','王旻安说得对','2022-12-05 20:39:37',0);
/*!40000 ALTER TABLE `Assessment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `isbn` varchar(64) NOT NULL,
  `author` varchar(64) DEFAULT NULL,
  `workType` varchar(20) NOT NULL DEFAULT 'book',
  `type` varchar(10) NOT NULL,
  `publishYear` varchar(5) NOT NULL DEFAULT '2022' COMMENT '出版年份',
  `description` varchar(300) NOT NULL,
  `picUrl` varchar(64) NOT NULL COMMENT '前端需要注意文件名长度限制',
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book`
--

LOCK TABLES `Book` WRITE;
/*!40000 ALTER TABLE `Book` DISABLE KEYS */;
INSERT INTO `Book` VALUES (1,'太古和其他的时间','9787220103735','奥尔加·托卡尔丘克','book','长篇小说','2017','《太古和其他的时间》（原作名字Prawiek i inne czasy）是2017年12月四川人民出版社出版图书，作者是（波兰）奥尔加·托卡尔丘克，译者是易丽君、袁汉镕。《太古和其他的时间》小说共 84 个章节，每一个章节以“xxx的时间”命名，通过不同的视角讲述了太古之中各种人物，甚至动物、植物和东西的故事，以三代人的人生故事，折射了波兰二十世纪动荡起伏的历史命运。','http://121.41.227.153:8081/file/太古和其他的时间.jpg',0),(2,'编年史','9787564926274','鲍勃·迪伦','book','自传','2017','《编年史》是美国鲍勃·迪伦创作的回忆录，也译为《像一块滚石》，繁体中文版译名为《摇滚记》。该书记录的不仅是作者发明创造和灵感迸发的辉煌时刻，还有意气消沉的时刻。\n该书出版以后，获得了如潮的好评：有媒体把它与杰克·凯鲁亚克的《在路上》相提并论，也有媒体说它写作手法直追意识流大师普鲁斯特。','http://121.41.227.153:8081/file/编年史.jpg',0),(3,'一个陌生女人的来信','9787020091089','斯蒂芬·茨威格','book','中篇小说','1922','《一个陌生女人的来信》是茨威格最具代表性的作品之一。《一个陌生女人的来信》描写一个男子在四十一岁生日当天收到一封没有署名和地址的信，信中一个临死的女人讲述了一个刻骨铭心的爱情故事，而故事的男主人公也就是收信的这个男人，而却对此一无所知。故事始自十八年前，当时她还是个孩子，之后经历了少女的痴迷、青春的激情，甚而流落风尘，独自抚养着他的孩子，尽管历尽艰辛，但从未改变对男人的爱，直至临死前才决定把事情的真相告诉他。','http://121.41.227.153:8081/file/一个陌生女人的来信.png',0);
/*!40000 ALTER TABLE `Book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Film`
--

DROP TABLE IF EXISTS `Film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Film` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `workType` varchar(20) NOT NULL DEFAULT 'film',
  `type` varchar(10) NOT NULL,
  `publishYear` int NOT NULL DEFAULT '2022',
  `picUrl` varchar(64) NOT NULL,
  `description` varchar(300) NOT NULL,
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Film`
--

LOCK TABLES `Film` WRITE;
/*!40000 ALTER TABLE `Film` DISABLE KEYS */;
INSERT INTO `Film` VALUES (1,'星际穿越','film','科幻',2014,'http://121.41.227.153:8081/file/星际穿越.png','《星际穿越》是2014年美英联合制作的科幻电影。该片由克里斯托弗·诺兰执导，马修·麦康纳、安妮·海瑟薇领衔主演。该片在物理学家基普·索恩的黑洞理论之上进行改编，主要讲述了一组宇航员通过穿越虫洞来为人类寻找新家园的冒险故事。',0),(2,'沙丘','film','科幻',2022,'http://121.41.227.153:8081/file/Dune.png','《沙丘》是由丹尼斯·维伦纽瓦执导，提莫西·查拉梅主演的科幻电影，该片于2021年9月3日在意大利威尼斯国际电影节首映。该片改编自弗兰克·赫伯特的同名小说，讲述了控制着珍贵资源的厄崔迪家族在遭遇背叛后，家族的继承人保罗决定接受命运的指引，去保卫自己的家族和人民的故事。2022年3月，《沙丘》获“英国奥斯卡”英国电影学院奖5项技术奖。',0);
/*!40000 ALTER TABLE `Film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Music`
--

DROP TABLE IF EXISTS `Music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Music` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `workType` varchar(20) NOT NULL DEFAULT 'music',
  `type` varchar(10) DEFAULT NULL,
  `publishYear` varchar(5) NOT NULL DEFAULT '2022',
  `description` varchar(300) NOT NULL,
  `picUrl` varchar(64) NOT NULL,
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Music`
--

LOCK TABLES `Music` WRITE;
/*!40000 ALTER TABLE `Music` DISABLE KEYS */;
INSERT INTO `Music` VALUES (1,'Hedwig\'s Theme','music','电影配乐','2013','《Hedwig\'s Theme》是哈利波特系列电影中的第一部——哈利波特与魔法石的主题曲。由约翰·威廉姆斯作曲。之后成为哈利波特系列乃至神奇动物系列的中心旋律。完美刻画了哈迷心中的魔法世界','http://121.41.227.153:8081/file/Hedwig\'s Theme.png',0),(2,'A Moment Apart','music','电子','2017','《A Moment Apart》是Odesza于2017年9月8日发行的音乐专辑中的同名主打曲目，专辑共收录16首歌。 2017年11月28日，《A Moment Apart》获得第60届格莱美最佳舞曲/电子专辑提名。 2018年4月，《A Moment Apart》获得2018年度公告牌音乐奖最佳舞曲/电子乐专辑提名。在国内具有较高知名度是因为其作为《地平线4》的开场音乐极其经典。','http://121.41.227.153:8081/file/A moment apart.jpg',0);
/*!40000 ALTER TABLE `Music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(60) NOT NULL,
  `password` varchar(120) NOT NULL,
  `mail` varchar(64) DEFAULT NULL,
  `isDeleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除字段,0未删除,1已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'wangminan','$2a$10$nenXnnSlRwC/tNIyWKwFqeod7YQykF9dtPWf5gayt/T4cTS2QfK8m','wangminan0811@hotmail.com',0),(2,'testUser','$2a$10$9ISnjfwBxfGreOUsozm2nurx0iKye8MCk8/lRqR3r5gjwh6TkgWgC','wangminan0811@icloud.com',0),(3,'testUser2','$2a$10$M3DNKBjuDa8eaZrJOEOMQOI3OfJDggpV7MPZvPwBNN26/WPTemO/6','wangminan0811@gmail.com',0),(4,'userControllerTest','$2a$10$6eH0vm2jHWPnfQnW7/iWved4fso6U0AAWLwo3zJ3clI.Uytstgyy.','test@hotmail.com',0),(5,'lyzihao','$2a$10$4p44dcfsGxN69mBFYbUzsulwagSxH9Uyvrtk9rgxlyVwRDpb1SwIe','lyzihao1118@qq.com',0),(6,'testDeleteUser','$2a$10$gdFihH0esSV/gDmt0mP86Oz0SdwFOn9f1Im69B7pi.YsyreI5dzwy','deleteuser@188.com',0),(7,'testDeleteUser2','$2a$10$pWAccUT77UYVOf3EzcdUDOM36rjcn0UxQ6vg1ZeywcFM9qkC0.j8O','delete@qq.com',0),(8,'0','$2a$10$phU2.knugTPfRpA643UP2.w3fiGVUMOdlF7PFVu/pC.C/cBSjRWPW','string',0),(11,'string','$2a$10$q765t1Dyx27DjI3ebAQCAuW4UqHvB7FPTm/SlyLyKOgp0XjCJWnYS','string',0),(15,'jakub','$2a$10$08aumggaOkM3DseYPksvGOj2rCGqJLXsjwl05BxhtIpm2OZFm7q.a','123@qq.com',0),(19,'111','$2a$10$nMT9.3HXTTcefhUgk/BIZeAC0qO..gPBF02DlcS6e414lN8tX0Zbi','111@qq.com',0),(22,'13777864458','$2a$10$OM7FyBCPG9sp5e7ktAknQudiViBmdgyGDVNTCx5h3PS0NlkhTI1zC','wangminan0811@gmail.com',0),(23,'jakubeva','$2a$10$gpVjhlXL9ZZgmSJalBXsWukYa8OlO10sckB2OO/6Xb1cBVo3ckkHS','123@qq.com',0),(25,'222','$2a$10$uiWHVX9IJl6QOWqyux/ZoOcQnhuV/99sf1N8ai1MBLtKS73hiRWQK','123@qq.com',0),(26,'党艳','$2a$10$Y0oIVc.qJo4o1uH30cD/O.jkek8GC5xa.Qo43pm3WIciFdemUJDZK','1138523924@qq.com',0),(27,'anotherTestUserAA','$2a$10$PZWqtzc7JsaRUOhQUwTHNeI.Vuw5kLYvXtP2OH3K5xGBFTD3XDi8m','testUser@qq.com',0),(28,'850','$2a$10$boftT0zJMCy5kYPXw8jcaetVt95ZDPtL0dpvHWh2M9K/NTa/BOdV.','123@qq.com',0),(29,'19283284','$2a$10$b6fiU2X2.IL5soJf7gvVHu.mMRtAIyOTJ0C6buvw1AHOXgJ2TOblK','123@qq.com',0),(30,'85077','$2a$10$jhTM/dK9MW5UDcqAXKzqDu4sb2X4di4r3FcV7J8yS.LgbmPkqEMHu','123@qq.com',0),(31,'lailailai','$2a$10$0W38Vi4282LbL6Oa8zUUH.la5lKXWh5tCE.LyqBESdFs5Ue60hbma','1234444',0),(32,'Conor','$2a$10$rLiAI0Gf7kTtom35MsLVUejkpdt1fTLXlo1yaEaoRjiN7cnN6iEI6','1726882022@qq.com',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VueRoute`
--

DROP TABLE IF EXISTS `VueRoute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VueRoute` (
  `id` bigint NOT NULL,
  `authname` varchar(64) NOT NULL,
  `path` varchar(64) DEFAULT NULL,
  `father` bigint DEFAULT NULL,
  KEY `VueRoute_VueRoute_id_fk` (`father`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VueRoute`
--

LOCK TABLES `VueRoute` WRITE;
/*!40000 ALTER TABLE `VueRoute` DISABLE KEYS */;
INSERT INTO `VueRoute` VALUES (201,'书影音管理',NULL,NULL),(202,'书籍管理','/books',201),(102,'用户列表','/users',101),(101,'用户管理','null',NULL),(203,'电影管理','/films',201),(302,'留言列表','/assessments',301),(301,'留言管理',NULL,NULL),(204,'音乐管理','/musics',201);
/*!40000 ALTER TABLE `VueRoute` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-08 21:46:10
