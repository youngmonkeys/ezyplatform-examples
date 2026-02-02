package org.youngmonkeys.personal.admin.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.ezyplatform.admin.appender.AdminDataAppender;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.ezyplatform.time.ClockProxy;
import org.youngmonkeys.personal.entity.PersonalCoinPrice;
import org.youngmonkeys.personal.repo.PersonalCoinPriceRepository;
import org.youngmonkeys.personal.result.CoinPriceApiResult;
import org.youngmonkeys.personal.service.PersonalCoinPriceService;

import java.time.ZoneId;
import java.util.List;

@EzySingleton
public class AdminPersonalCoinPriceDataAppender
    extends AdminDataAppender<CoinPriceApiResult, PersonalCoinPrice, Long> {

    private final ClockProxy clock;

    @EzyAutoBind
    private PersonalCoinPriceService coinPriceService;

    @EzyAutoBind
    private PersonalCoinPriceRepository coinPriceRepository;

    public AdminPersonalCoinPriceDataAppender(
        ClockProxy clock,
        ObjectMapper objectMapper, AdminSettingService settingService) {
        super(objectMapper, settingService);
        this.clock = clock;
    }

    @Override
    protected List<CoinPriceApiResult> getValueList(Long lastTimestamp) {
        return coinPriceService.fetchCoinPrice();
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
        if (!dataRecords.isEmpty()) {
            coinPriceRepository.save(dataRecords);
            //logger.info("Appended {} coin prices to database", dataRecords.size());
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
