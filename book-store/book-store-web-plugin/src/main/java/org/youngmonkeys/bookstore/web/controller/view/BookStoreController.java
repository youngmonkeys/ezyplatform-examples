package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.service.ProductCategoryService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

@AllArgsConstructor
public class BookStoreController {

    private final WebPageFragmentManager pageFragmentManager;
    private final ProductCategoryService myProductCategoryService;
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
                        myProductCategoryService.getListCategoryMenuItems()
                ).build();
    }
}
