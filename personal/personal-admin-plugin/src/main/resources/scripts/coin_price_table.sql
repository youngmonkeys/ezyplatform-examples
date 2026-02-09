CREATE TABLE IF NOT EXISTS `personal_coin_price` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `symbol` varchar(10) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `name` varchar(50) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `price` char(85) COLLATE utf8mb4_unicode_520_ci NOT NULL,
    `price_change` char(85) COLLATE utf8mb4_unicode_520_ci,
    `updated_at` datetime NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;