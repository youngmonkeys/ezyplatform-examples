package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductCategoryType;
import org.youngmonkeys.ecommerce.entity.ProductCategoryStatus;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductCategoryControllerService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

import java.util.Collections;

@AllArgsConstructor
public class BookStoreController {

    private final WebPageFragmentManager pageFragmentManager;
    private final WebProductCategoryControllerService productCategoryControllerService;

    @DoGet("/store")
    public View storeGet(@RequestParam("lang") String language) {
        return View.builder()
            .template("store")
            .addVariable("pageTitle", "store")
            .addVariable(
                "books",
                pageFragmentManager.getPageFragmentMap(
                    "store",
                    language
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
