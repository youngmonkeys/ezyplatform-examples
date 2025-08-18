package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.Setter;
import org.youngmonkeys.bookstore.constant.BookStoreProductCategoryType;
import org.youngmonkeys.ecommerce.entity.ProductCategoryStatus;
import org.youngmonkeys.ecommerce.model.ProductCategoryModel;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.pagination.DefaultProductPriceFilter;
import org.youngmonkeys.ecommerce.response.ProductResponse;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductCategoryControllerService;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductControllerService;
import org.youngmonkeys.ecommerce.web.response.WebProductResponse;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ecommerce.web.validator.WebProductCategoryValidator;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Setter
public class StoreController {

    @EzyAutoBind
    private WebProductCurrencyService productCurrencyService;

    @EzyAutoBind
    private WebLanguageControllerService languageControllerService;

    @EzyAutoBind
    private WebProductControllerService productControllerService;

    @EzyAutoBind
    private WebProductCategoryControllerService productCategoryControllerService;

    @EzyAutoBind
    private WebProductCategoryValidator productCategoryValidator;


    @DoGet("/store")
    public View storeGet(
        HttpServletRequest request,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        String language = languageControllerService
            .getLanguageCodeOrDefault(request);
        ProductCurrencyModel defaultCurrency = productCurrencyService
            .getDefaultCurrency();
        PaginationModel<WebProductResponse> books = productControllerService
            .getWebProductPagination(
                language,
                DefaultProductFilter.builder().build(),
                DefaultProductPriceFilter.builder().build(),
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                defaultCurrency
        );
        return View
            .builder()
            .template("store")
            .addVariable("pageTitle", "store")
            .addVariable("books", books)
            .addVariable("currencyId", defaultCurrency.getId())
            .addVariable("categories",
                productCategoryControllerService.getProductCategoryMenusByTypeAndStatuses(
                    BookStoreProductCategoryType.BOOK.toString(),
                    Collections.singletonList(ProductCategoryStatus.SHOW.toString())
                ))
            .build();
    }

    @DoGet("/book-categories/{categoryName}")
    public View categoryGet(
        HttpServletRequest request,
        @PathVariable("categoryName") String categoryName,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        ProductCategoryModel category = productCategoryValidator
            .validateCategoryName(categoryName);
        String language = languageControllerService
            .getLanguageCodeOrDefault(request);
        ProductCurrencyModel defaultCurrency = productCurrencyService
            .getDefaultCurrency();
        long categoryId = category.getId();
        PaginationModel<WebProductResponse> books = productControllerService
            .getWebProductPagination(
                language,
                DefaultProductFilter.builder()
                    .inclusiveCategoryId(categoryId)
                    .build(),
                DefaultProductPriceFilter.builder()
                    .inclusiveCategoryId(categoryId)
                    .build(),
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                defaultCurrency
        );
        return View
            .builder()
            .template("store")
            .addVariable("pageTitle", categoryName)
            .addVariable("books", books)
            .addVariable("categories",
                productCategoryControllerService.getProductCategoryMenusByTypeAndStatuses(
                    BookStoreProductCategoryType.BOOK.toString(),
                    Collections.singletonList(ProductCategoryStatus.SHOW.toString()))
            )
            .addVariable("currencyId", defaultCurrency.getId())
            .build();
    }

    @DoGet("/api/v1/books/{bookId}")
    public ProductResponse getProductById(
        @PathVariable("bookId") long bookId,
        @RequestParam("currencyId") long currencyId
    ) {
        ProductCurrencyModel currency = productCurrencyService
            .getCurrencyByIdOrDefault(currencyId);
        return productControllerService.getProductById(
            bookId,
            currency
        );
    }
}
