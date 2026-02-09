package org.youngmonkeys.personal.web.controller.api;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.result.CoinPriceApiResult;
import org.youngmonkeys.personal.service.PersonalCoinPriceService;

import java.util.Collection;

import static org.youngmonkeys.personal.constant.PersonalConstants.COIN_SYMBOLS;

@Controller("/api/v1")
@AllArgsConstructor
public class PersonalWebApiCoinPriceController {

    private final PersonalCoinPriceService coinPriceService;

    @DoGet("/coins/latest")
    public Collection<CoinPriceApiResult> getLastestCoinPrices() {
        return  coinPriceService.getLatestPrices(COIN_SYMBOLS);
    }
}
