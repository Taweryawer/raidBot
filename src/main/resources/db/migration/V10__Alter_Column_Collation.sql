ALTER TABLE usr
    MODIFY COLUMN nickname varchar(255) COLLATE utf8mb4_unicode_ci,
    MODIFY COLUMN friend_code varchar(255) COLLATE utf8mb4_unicode_ci
