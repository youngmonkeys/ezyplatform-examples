package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.controller.decorator.WebBookModelDecorator;
import org.youngmonkeys.bookstore.web.response.WebBookFlashSaleResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.bookstore.web.service.WebProductPriceListProductService;
import org.youngmonkeys.bookstore.web.service.WebProductPriceListService;
import org.youngmonkeys.ecommerce.entity.ShopProductPriceList;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.web.service.WebProductService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WebBookFlashSaleControllerService {
    private final WebProductService productService;
    private final WebProductPriceListService productPriceListService;
    private final WebProductPriceListProductService productPriceListProductService;
    private final WebBookModelDecorator bookModelDecorator;

    public WebBookFlashSaleResponse getFlashSale(
        ProductCurrencyModel currency,
        int limit
    ) {
        ShopProductPriceList flashSale =
            productPriceListService.getActiveFlashSalePriceList();
        if (flashSale == null) {
            return null;
        }

        List<WebBookResponse> books = getFlashSaleBooks(
            flashSale.getId(),
            currency,
            limit
        );

        return toFlashSaleResponse(
            flashSale,
            books
        );
    }

    private List<WebBookResponse> getFlashSaleBooks(
        long priceListId,
        ProductCurrencyModel currency,
        int limit
    ) {
        List<Long> productIds =
            productPriceListProductService.getProductIdsByPriceListId(
                priceListId,
                limit
            );

        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductModel> products = productService.getProductsByIds(productIds);
        return bookModelDecorator.decorateToBookResponses(
            products,
            currency
        );
    }

    private WebBookFlashSaleResponse toFlashSaleResponse(
        ShopProductPriceList flashSale,
        List<WebBookResponse> books
    ) {
        return WebBookFlashSaleResponse.builder()
            .id(flashSale.getId())
            .displayName(flashSale.getDisplayName())
            .startApplyAtMs(toEpochMs(flashSale.getStartApplyAt()))
            .finishApplyAtMs(toEpochMs(flashSale.getFinishApplyAt()))
            .serverNowMs(System.currentTimeMillis())
            .books(books)
            .build();
    }

    private long toEpochMs(LocalDateTime time) {
        if (time == null) {
            return 0L;
        }
        return time.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
    }
}
