package org.youngmonkeys.personal.admin.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.ezyplatform.admin.appender.AdminDataAppender;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.ezyplatform.time.ClockProxy;
import org.youngmonkeys.personal.admin.repo.AdminPersonalCoinPriceRepository;
import org.youngmonkeys.personal.admin.service.AminPersonalCoinPriceService;
import org.youngmonkeys.personal.entity.PersonalCoinPrice;
import org.youngmonkeys.personal.result.CoinPriceApiResult;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EzySingleton
public class AdminPersonalCoinPriceDataAppender
    extends AdminDataAppender<CoinPriceApiResult, PersonalCoinPrice, Long> {

    private final ClockProxy clock;
    private final AminPersonalCoinPriceService coinPriceService;
    private final AdminPersonalCoinPriceRepository coinPriceRepository;
    private long lastCallTime = System.currentTimeMillis();
    private static final int PERIOD = 60 * 1000;

    public AdminPersonalCoinPriceDataAppender(
        ClockProxy clock,
        ObjectMapper objectMapper,
        AminPersonalCoinPriceService coinPriceService,
        AdminSettingService settingService,
        AdminPersonalCoinPriceRepository coinPriceRepository
    ) {
        super(
            objectMapper,
            settingService
        );
        this.clock = clock;
        this.coinPriceService = coinPriceService;
        this.coinPriceRepository = coinPriceRepository;
    }

    @Override
    protected List<CoinPriceApiResult> getValueList(
        Long lastTimestamp
    ) {
        long callTime = lastCallTime + PERIOD;
        long now = System.currentTimeMillis();
        if (callTime < now) {
            lastCallTime = now;
            return Arrays.asList(coinPriceService.fetchCoinPrice());
        }
        return Collections.emptyList();
    }

    @Override
    protected PersonalCoinPrice toDataRecord(CoinPriceApiResult value) {
        PersonalCoinPrice entity = new PersonalCoinPrice();
        entity.setSymbol(value.getBaseSymbol());
        entity.setName(value.getBaseName());
        entity.setPrice(value.getPrice());
        entity.setPriceChange(value.getPriceChange());
        entity.setUpdatedAt(clock.nowDateTime());

        return entity;
    }

    @Override
    protected void addDataRecords(List<PersonalCoinPrice> dataRecords) {
        coinPriceRepository.save(dataRecords);
    }

    @Override
    protected Long extractNewLastPageToken(List<CoinPriceApiResult> list, Long aLong) {
        return clock.nowDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    protected String getAppenderNamePrefix() {
        return "personal_coin_price";
    }

    @Override
    protected Long defaultPageToken() {
        return 0L;
    }

    @Override
    protected Class<Long> pageTokenType() {
        return Long.class;
    }
}
