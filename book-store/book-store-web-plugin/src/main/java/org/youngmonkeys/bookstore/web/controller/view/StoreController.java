package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.Setter;
import org.youngmonkeys.bookstore.constant.BookStoreProductCategoryType;
import org.youngmonkeys.bookstore.constant.BookStoreProductType;
import org.youngmonkeys.bookstore.web.controller.service.WebBookControllerService;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.entity.ProductCategoryStatus;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.ProductCategoryModel;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.pagination.DefaultProductPriceFilter;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductCategoryControllerService;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ecommerce.web.validator.WebProductCategoryValidator;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Setter
public class StoreController {

    @EzyAutoBind
    private WebBookControllerService bookControllerService;

    @EzyAutoBind
    private WebProductCurrencyService currencyService;

    @EzyAutoBind
    private WebProductCategoryControllerService productCategoryControllerService;

    @EzyAutoBind
    private WebProductCategoryValidator productCategoryValidator;


    @DoGet("/store")
    public View storeGet(
        @RequestParam(value = "currencyId") long currencyId,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        ProductCurrencyModel currency = currencyService
            .getCurrencyByIdOrDefault(currencyId);
        PaginationModel<WebBookResponse> books = bookControllerService
            .getBookPagination(
                DefaultProductFilter.builder()
                    .productType(BookStoreProductType.BOOK.toString())
                    .status(ProductStatus.PUBLISHED.toString())
                    .build(),
                DefaultProductPriceFilter.builder()
                    .productType(BookStoreProductType.BOOK.toString())
                    .productStatus(ProductStatus.PUBLISHED.toString())
                    .build(),
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                currency
        );
        return newStoreViewBuilder(books, currency.getId())
            .addVariable("pageTitle", "store")
            .build();
    }

    @DoGet("store/books/categories/{id}")
    public View storeBooksCategoriesIdCat(
        HttpServletRequest request,
        @PathVariable long categoryId,
        @RequestParam("currencyId") long currencyId,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        ProductCategoryModel category = productCategoryValidator
            .validateCategoryId(categoryId);
        ProductCurrencyModel currency = currencyService
            .getCurrencyByIdOrDefault(currencyId);
        PaginationModel<WebBookResponse> books = bookControllerService
            .getBookPagination(
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
                currency
        );
        return newStoreViewBuilder(books, currencyId)
            .addVariable("pageTitle", category.getDisplayName())
            .build();
    }

    @DoGet("/store/books/{bookId}")
    public View storeBooksBookIdGet(
        @PathVariable long bookId,
        @RequestParam("currencyId") long currencyId
    ) {
        ProductCurrencyModel currency = currencyService
            .getCurrencyByIdOrDefault(currencyId);
        WebBookDetailsResponse book = bookControllerService
            .getBookDetailsById(bookId, currency);
        return View.builder()
            .template("book-details")
            .addVariable("pageTitle", book.getName())
            .addVariable("book", book)
            .addVariable("currencyId", currency.getId())
            .build();
    }

    private View.Builder newStoreViewBuilder(
        PaginationModel<WebBookResponse> books,
        long currencyId
    ) {
        return View.builder()
            .template("store")
            .addVariable("currencyId", currencyId)
            .addVariable("books", books)
            .addVariable("categories",
                productCategoryControllerService
                    .getProductCategoryMenusByTypeAndStatuses(
                        BookStoreProductCategoryType.BOOK.toString(),
                        Collections.singletonList(
                            ProductCategoryStatus.SHOW.toString()
                        )
                    )
            );
    }
}
