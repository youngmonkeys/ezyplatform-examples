package org.youngmonkeys.bookstore.web.controller.api;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductType;
import org.youngmonkeys.bookstore.web.controller.service.WebBookControllerService;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.pagination.DefaultProductPriceFilter;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import static com.tvd12.ezyfox.io.EzyStrings.isBlank;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO;
import static org.youngmonkeys.ezyplatform.util.Keywords.toKeywords;
import static org.youngmonkeys.ezyplatform.util.StringConverters.trimOrNull;

@Controller("/api/v1")
@AllArgsConstructor
public class WebBookStoreApiBookController {

    private final WebBookControllerService bookControllerService;
    private final WebProductCurrencyService currencyService;

    @DoGet("/books")
    public PaginationModel<WebBookResponse> booksGet(
        @RequestParam(value = "categoryId") long categoryId,
        @RequestParam(value = "currencyId") long currencyId,
        @RequestParam(value = "keyword") String keyword,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        DefaultProductFilter.Builder productFilterBuilder = DefaultProductFilter
            .builder()
            .inclusiveCategoryId(categoryId)
            .productType(BookStoreProductType.BOOK.toString())
            .status(ProductStatus.PUBLISHED.toString());
        DefaultProductPriceFilter productPriceFilter = DefaultProductPriceFilter
            .builder()
            .inclusiveCategoryId(categoryId)
            .productType(BookStoreProductType.BOOK.toString())
            .productStatus(ProductStatus.PUBLISHED.toString())
            .build();
        ProductCurrencyModel currency = currencyService
            .getCurrencyByIdOrDefault(currencyId);
        PaginationModel<WebBookResponse> pagination;
        if (isBlank(keyword)) {
            pagination = bookControllerService.getBookPagination(
                productFilterBuilder
                    .build(),
                productPriceFilter,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                currency
            );
        } else {
            pagination = bookControllerService.getBookPagination(
                productFilterBuilder
                    .keyword(trimOrNull(keyword))
                    .build(),
                productPriceFilter,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                currency
            );
            if (pagination.getCount() == ZERO) {
                pagination = bookControllerService.getBookPagination(
                    productFilterBuilder
                        .keywords(toKeywords(keyword, Boolean.TRUE))
                        .build(),
                    productPriceFilter,
                    sortOrder,
                    nextPageToken,
                    prevPageToken,
                    lastPage,
                    limit,
                    currency
                );
            }
        }
        return pagination;
    }
}
