package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class WebBookDetailsResponse {
    private long id;
    private String code;
    private String name;
    private String authorName;
    private List<MediaNameModel> medias;
    private String description;
    private String originalPrice;
    private String formattedOriginalPriceIncludeIsoCode;
    private String formattedOriginalPriceIncludeSymbol;
    private String price;
    private String formattedPriceIncludeIsoCode;
    private String formattedPriceIncludeSymbol;
    private BigDecimal discountPercent;
}
