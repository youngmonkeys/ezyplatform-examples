package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

import java.math.BigDecimal;

@Getter
@Builder
public class WebBookResponse {
    private long id;
    private String code;
    private String name;
    private String authorName;
    private String authorUuid;
    private MediaNameModel bannerImage;
    private String shortedDescription;
    private String publisher;
    private String originalPrice;
    private String formattedOriginalPrice;
    private String formattedOriginalPriceIncludeIsoCode;
    private String formattedOriginalPriceIncludeSymbol;
    private String price;
    private String formattedPrice;
    private String formattedPriceIncludeIsoCode;
    private String formattedPriceIncludeSymbol;
    private BigDecimal discountPercent;
}
