package org.youngmonkeys.personal.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinPriceApiResult {

    @JsonProperty("base_symbol")
    private String baseSymbol;

    @JsonProperty("base_name")
    private String baseName;

    private String price;

    @JsonProperty("price_change")
    private String priceChange;
}
