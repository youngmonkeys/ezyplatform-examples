package org.youngmonkeys.personal.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.repo.PersonalCoinPriceRepository;
import org.youngmonkeys.personal.result.CoinPriceApiResult;

import java.util.Arrays;
import java.util.Collection;

@EzySingleton
@AllArgsConstructor
public class PersonalCoinPriceService {
    private final PersonalCoinPriceRepository coinPriceRepository;

    public Collection<CoinPriceApiResult> getLatestPrices(String symbols) {
        Collection<String> symbolsList = Arrays.asList(symbols.split(","));
        return coinPriceRepository.findLatestBySymbols(symbolsList);
    }
}
