package org.youngmonkeys.personal.admin.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyhttp.client.HttpClient;
import com.tvd12.ezyhttp.client.request.GetRequest;
import com.tvd12.ezyhttp.client.request.Request;
import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.result.CoinPriceApiResult;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.youngmonkeys.personal.constant.PersonalConstants.API_URL_COIN_PRICE;

@EzySingleton
@AllArgsConstructor
public class AminPersonalCoinPriceService extends EzyLoggable {

    private final HttpClient httpClient;

    public CoinPriceApiResult[] fetchCoinPrice() {
        try {
            Request request = new GetRequest()
                .setURL(API_URL_COIN_PRICE)
                .setResponseType(200, CoinPriceApiResult[].class);
            return httpClient.call(request);
        } catch (Exception e) {
            logger.warn("fetch coin price error", e);
        }
        return new CoinPriceApiResult[0];
    }

    private boolean isChanged(
        CoinPriceApiResult oldValue,
        CoinPriceApiResult newValue
    ) {
        if (oldValue == null || newValue == null) {
            return true;
        }

        return !Objects.equals(oldValue.getPrice(), newValue.getPrice());
    }

    public boolean isAnyCoinChanged(
        List<CoinPriceApiResult> oldList,
        List<CoinPriceApiResult> newList
    ) {
        if (oldList == null || newList == null) {
            return true;
        }
        Map<String, CoinPriceApiResult> oldMap =
            oldList.stream()
                .collect(Collectors.toMap(
                    CoinPriceApiResult::getBaseSymbol,
                    Function.identity()
                ));
        for (CoinPriceApiResult newValue : newList) {
            CoinPriceApiResult oldValue = oldMap.get(newValue.getBaseSymbol());

            if (isChanged(oldValue, newValue)) {
                return true;
            }
        }
        return false;
    }
}
