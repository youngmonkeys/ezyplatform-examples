package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductCategoryType;
import org.youngmonkeys.ecommerce.entity.ProductCategoryStatus;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.pagination.DefaultProductPriceFilter;
import org.youngmonkeys.ecommerce.response.ProductResponse;
import org.youngmonkeys.ecommerce.service.ProductCurrencyService;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductCategoryControllerService;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductControllerService;
import org.youngmonkeys.ecommerce.web.response.WebProductCategoryResponse;

import java.util.Collections;

@AllArgsConstructor
public class BookStoreController {

    private final WebProductCategoryControllerService webProductCategoryControllerService;
    private final WebProductControllerService webProductControllerService;
    private final ProductCurrencyService productCurrencyService;


    @DoGet("/store")
    public View storeGet(
        @RequestParam("lang") String language,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        String defaultSortType = "ASC";
        ProductCurrencyModel defaultCurrency = productCurrencyService.getDefaultCurrency();
        return View
            .builder()
            .template("store")
            .addVariable("pageTitle", "store")
            .addVariable("books",
                webProductControllerService.getWebProductPagination(
                    language,
                    DefaultProductFilter.builder().build(),
                    DefaultProductPriceFilter.builder().build(),
                    defaultSortType,
                    null,
                    null,
                    Boolean.FALSE,
                    limit,
                    defaultCurrency.getId(),
                    defaultCurrency.getFormat()
                ))
            .addVariable("currencyId", defaultCurrency.getId())
            .addVariable("categories",
                webProductCategoryControllerService.getProductCategoryMenusByTypeAndStatuses(
                    BookStoreProductCategoryType.BOOK.toString(),
                    Collections.singletonList(ProductCategoryStatus.SHOW.toString())
                ))
            .build();
    }

    @DoGet("/book-categories/{category-name}")
    public View categoryGet(
        @RequestParam("lang") String language, @PathVariable("category-name") String categoryName,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        String defaultSortType = "ASC";
        ProductCurrencyModel defaultCurrency = productCurrencyService.getDefaultCurrency();
        WebProductCategoryResponse category = webProductCategoryControllerService
            .getWebProductCategoryItemByNameAndTypeAndStatuses(
                categoryName,
                BookStoreProductCategoryType.BOOK.name(),
                Collections.singletonList(ProductCategoryStatus.SHOW.name()),
                language
            );
        Long categoryId = null;
        if (category != null) {
            categoryId = category.getId();

        }
        return View
            .builder()
            .template("store")
            .addVariable("pageTitle", categoryName)
            .addVariable("books",
                webProductControllerService.getWebProductPagination(
                    language,
                    DefaultProductFilter.builder().inclusiveCategoryId(categoryId).build(),
                    DefaultProductPriceFilter.builder().build(),
                    defaultSortType,
                    null,
                    null,
                    Boolean.FALSE,
                    limit,
                    defaultCurrency.getId(),
                    defaultCurrency.getFormat()
                ))
            .addVariable("categories",
                webProductCategoryControllerService.getProductCategoryMenusByTypeAndStatuses(
                    BookStoreProductCategoryType.BOOK.toString(),
                    Collections.singletonList(ProductCategoryStatus.SHOW.toString()))
            )
            .addVariable("currencyId", defaultCurrency.getId())
            .build();
    }

    @DoGet("/api/v1/books/{bookId}")
    public ProductResponse getProductById(
        @PathVariable("bookId") Long bookId,
        @RequestParam("currencyId") Long currencyId
    ) {
        if (currencyId == null) {
            currencyId = productCurrencyService.getDefaultCurrency().getId();
        }
        if (bookId == null) {
            return null;
        }
        ProductCurrencyModel currencyModel = productCurrencyService.getCurrencyById(currencyId);
        return webProductControllerService.getProductById(
            bookId,
            currencyModel.getId(),
            currencyModel.getFormat()
        );
    }
}
