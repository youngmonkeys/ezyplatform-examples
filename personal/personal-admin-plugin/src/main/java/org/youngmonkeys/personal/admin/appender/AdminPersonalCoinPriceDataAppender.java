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
import org.youngmonkeys.personal.service.PersonalCoinPriceService;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.youngmonkeys.personal.constant.PersonalConstants.COIN_SYMBOLS;

@EzySingleton
public class AdminPersonalCoinPriceDataAppender
    extends AdminDataAppender<CoinPriceApiResult, PersonalCoinPrice, Long> {

    private final ClockProxy clock;
    private final AminPersonalCoinPriceService adminCoinPriceService;
    private final PersonalCoinPriceService coinPriceService;
    private final AdminPersonalCoinPriceRepository coinPriceRepository;
    private long lastCallTime = System.currentTimeMillis();
    private static final int PERIOD = 60 * 1000;

    public AdminPersonalCoinPriceDataAppender(
        ClockProxy clock,
        ObjectMapper objectMapper,
        AminPersonalCoinPriceService adminCoinPriceService,
        PersonalCoinPriceService coinPriceService,
        AdminSettingService settingService,
        AdminPersonalCoinPriceRepository coinPriceRepository
    ) {
        super(
            objectMapper,
            settingService
        );
        this.clock = clock;
        this.adminCoinPriceService = adminCoinPriceService;
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
            return Arrays.asList(adminCoinPriceService.fetchCoinPrice());
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
        List<CoinPriceApiResult> lastestResults =
            new ArrayList<>(coinPriceService.getLatestPrices(COIN_SYMBOLS));
        List<CoinPriceApiResult> incomingResults = dataRecords.stream()
            .map(coin -> {
                CoinPriceApiResult result = new CoinPriceApiResult();
                result.setPrice(coin.getPrice());
                result.setBaseSymbol(coin.getSymbol());
                result.setBaseName(coin.getName());
                result.setPriceChange(coin.getPriceChange());
                return result;
            }).collect(Collectors.toList());
        if (adminCoinPriceService.isAnyCoinChanged(lastestResults, incomingResults)) {
            coinPriceRepository.save(dataRecords);
        }
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
