package org.youngmonkeys.personal.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.client.HttpClient;
import com.tvd12.ezyhttp.client.request.GetRequest;
import com.tvd12.ezyhttp.client.request.Request;
import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.result.CoinPriceApiResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.youngmonkeys.personal.constant.PersonalConstants.API_URL_COIN_PRICE;

@EzySingleton
@AllArgsConstructor
public class PersonalCoinPriceService {

    @EzyAutoBind
    private HttpClient httpClient;

    public List<CoinPriceApiResult> fetchCoinPrice() {
        try {
            Request request = new GetRequest()
                .setURL(API_URL_COIN_PRICE)
                .setResponseType(200, CoinPriceApiResult[].class);

            CoinPriceApiResult[] responseArray = httpClient.call(request);
            if (responseArray != null) {
                return Arrays.asList(responseArray);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyList();
    }
}
