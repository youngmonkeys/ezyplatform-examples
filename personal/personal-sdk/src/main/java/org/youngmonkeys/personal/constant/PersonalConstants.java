package org.youngmonkeys.personal.constant;

public final class PersonalConstants {

    public static final String TABLE_NAME_WORD_COUNT =
        "personal_post_word_counts";

    public static final String INTERNAL_EVENT_NAME_POST_WORD_COUNT =
        "personal_post_word_count";

    public static final int READ_WORDS_PER_MINUTE = 200;

    public static final String META_KEY_TIME_TO_READ = "time_to_read";

    public static final String SETTING_KEY_ALLOW_CALCULATE_POST_READ_TIME =
        "allow_calculate_post_read_time";

    public static final String TABLE_NAME_COIN_PRICE =
        "personal_coin_price";

    public static final String INTERNAL_EVENT_NAME_COIN_PRICE_UPDATE =
        "personal_coin_price";

    public static final String COIN_SYMBOLS = "BTC,ETH,BNB,LTC,XRP";

    public static final String API_URL_COIN_PRICE =
        "https://coinyep.com/api/v1/?list=" + COIN_SYMBOLS;

    public static final String SETTING_KEY_SHOW_COIN_PRICE =
        "show_coin_widget";

    private PersonalConstants() {}
}
