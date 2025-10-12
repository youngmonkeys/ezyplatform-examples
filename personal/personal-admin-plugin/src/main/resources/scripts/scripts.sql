CREATE TABLE IF NOT EXISTS `personal_post_word_counts` (
    `post_id` bigint unsigned NOT NULL,
    `word_count` bigint NOT NULL DEFAULT 0,
    `updated_at` datetime,
    PRIMARY KEY (`post_id`),
    INDEX `index_word_count_post_id` (`word_count`, `post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;
