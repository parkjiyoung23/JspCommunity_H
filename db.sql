DROP DATABASE IF EXISTS Jsp_Community;
CREATE DATABASE Jsp_Community;
USE Jsp_Community;


# 게시물 생성
CREATE TABLE article (
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(200) NOT NULL,
	`body` LONGTEXT NOT NULL
);

SELECT * FROM article;

# 게시물 테이블 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

SELECT * FROM article;

INSERT INTO article(regDate,updateDate,title, `body`)
SELECT NOW(),NOW(), CONCAT('제목_',RAND()),CONCAT('내용_',RAND())
FROM article;

# 회원 테이블 생성
CREATE TABLE `member`(
	id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(100) NOT NULL UNIQUE,
	loginPw CHAR(100) NOT NULL,
	`name` LONGTEXT NOT NULL
);
SELECT * FROM `member`;
