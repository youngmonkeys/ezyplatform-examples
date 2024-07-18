package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductCategoryType;
import org.youngmonkeys.ecommerce.entity.ProductCategoryStatus;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.pagination.DefaultProductPriceFilter;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductCategoryControllerService;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductControllerService;

import java.util.Collections;

@AllArgsConstructor
public class BookStoreController {

    private final WebProductCategoryControllerService productCategoryControllerService;
    private final WebProductControllerService webProductControllerService;


    @DoGet("/store")
    public View storeGet(@RequestParam("lang") String language) {
        int defaultPageLimit = 10;
        String defaultSortType = "ASC";
        String defaultCurrency = "USD";
        int defaultCurrencyId = 1; //don't know where this information
        return View.builder()
            .template("store")
            .addVariable("pageTitle", "store")
            .addVariable(
                "books",
                webProductControllerService.getWebProductPagination(
                    language,
                    DefaultProductFilter.builder().build(),
                    DefaultProductPriceFilter.builder().build(),
                    defaultSortType,
                    null,
                    null,
                    Boolean.FALSE,
                    defaultPageLimit,
                    defaultCurrencyId,
                    defaultCurrency
                )
            )
            .addVariable(
                "categories",
                productCategoryControllerService.getProductCategoryMenusByTypeAndStatuses(
                    BookStoreProductCategoryType.BOOK.toString(),
                    Collections.singletonList(ProductCategoryStatus.SHOW.toString())
                )
            )
            .build();
    }
}
