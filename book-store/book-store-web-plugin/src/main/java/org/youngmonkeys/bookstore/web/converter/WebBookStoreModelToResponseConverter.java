package org.youngmonkeys.bookstore.web.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.response.WebBookAuthorDetailResponse;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.manager.WebPostDecoratorManager;
import org.youngmonkeys.ezycrm.model.CustomerModel;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.UserModel;

import java.math.BigDecimal;
import java.util.List;

import static org.youngmonkeys.ecommerce.util.DecimalPrices.toDiscountPercent;
import static org.youngmonkeys.ezyplatform.util.Numbers.toNoTrailingZerosString;

@EzySingleton
@AllArgsConstructor
public class WebBookStoreModelToResponseConverter {

    private final WebPostDecoratorManager postDecoratorManager;

    public WebBookResponse toBookResponse(
            ProductModel model,
            ProductBookModel book,
            UserModel author,
            MediaNameModel bannerImage,
            PostModel descriptionPost,
            ProductPriceModel price,
            ProductCurrencyModel currency
    ) {
        BigDecimal priceValue = price.getPrice();
        BigDecimal originalPriceValue = price.getOriginalPrice();
        return WebBookResponse.builder()
                .id(model.getId())
                .name(model.getProductName())
                .code(model.getProductCode())
                .authorName(author.getDisplayName())
                .authorUuid(author.getUuid())
                .bannerImage(bannerImage)
                .shortedDescription(descriptionPost.getShortedContent())
                .publisher(book.getPublisher())
                .originalPrice(toNoTrailingZerosString(originalPriceValue))
                .formattedOriginalPriceIncludeIsoCode(
                        currency.formatPriceIncludeIsoCode(originalPriceValue)
                )
                .formattedOriginalPriceIncludeSymbol(
                        currency.formatPriceIncludeSymbol(originalPriceValue)
                )
                .price(toNoTrailingZerosString(priceValue))
                .formattedPriceIncludeIsoCode(
                        currency.formatPriceIncludeIsoCode(priceValue)
                )
                .formattedPriceIncludeSymbol(
                        currency.formatPriceIncludeSymbol(priceValue)
                )
                .discountPercent(toDiscountPercent(priceValue, originalPriceValue))
                .build();
    }

    public WebBookDetailsResponse toBookDetailsResponse(
            ProductModel model,
            ProductBookModel book,
            PostModel descriptionPost,
            ProductPriceModel price,
            ProductCurrencyModel currency,
            List<MediaNameModel> medias
    ) {
        BigDecimal priceValue = price.getPrice();
        BigDecimal originalPriceValue = price.getOriginalPrice();
        String description = postDecoratorManager
                .decorateContent(
                        descriptionPost.getContentType(),
                        descriptionPost.getContent()
                );
        return WebBookDetailsResponse.builder()
                .id(model.getId())
                .name(model.getProductName())
                .code(model.getProductCode())
                .authorName(book.getAuthor())
                .medias(medias)
                .description(description)
                .originalPrice(toNoTrailingZerosString(originalPriceValue))
                .formattedOriginalPriceIncludeIsoCode(
                        currency.formatPriceIncludeIsoCode(originalPriceValue)
                )
                .formattedOriginalPriceIncludeSymbol(
                        currency.formatPriceIncludeSymbol(originalPriceValue)
                )
                .price(toNoTrailingZerosString(priceValue))
                .formattedPriceIncludeIsoCode(
                        currency.formatPriceIncludeIsoCode(priceValue)
                )
                .formattedPriceIncludeSymbol(
                        currency.formatPriceIncludeSymbol(priceValue)
                )
                .discountPercent(toDiscountPercent(priceValue, originalPriceValue))
                .build();
    }

    public WebBookAuthorDetailResponse toBookAuthorDetailResponse(
            UserModel model,
            CustomerModel customer,
            MediaNameModel avatarImage,
            MediaNameModel coverImage
    ) {
        return WebBookAuthorDetailResponse.builder()
                .displayName(model.getDisplayName())
                .uuid(model.getUuid())
                .avatarImage(avatarImage)
                .coverImage(coverImage)
                .websiteUrl(model.getUrl())
                .description(customer.getDescription())
                .build();
    }
}
