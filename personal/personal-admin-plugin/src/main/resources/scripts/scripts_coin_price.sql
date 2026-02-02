CREATE TABLE IF NOT EXISTS `personal_coin_price` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `symbol` varchar(10) NOT NULL,
    `name` varchar(50) NOT NULL,
    `price` varchar(64) NOT NULL,
    `price_change` varchar(32),
    `updated_at` datetime,
    PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;