CREATE TABLE usr (
    id int NOT NULL AUTO_INCREMENT,
    nickname varchar(255) NOT NULL,
    friend_code varchar(255) NOT NULL,
    reputation int NOT NULL DEFAULT 50,
    PRIMARY KEY (id)
)