package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class WebBookResponse {
    private long id;
    private String code;
    private String name;
    private List<WebBookAuthorResponse> authors;
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
