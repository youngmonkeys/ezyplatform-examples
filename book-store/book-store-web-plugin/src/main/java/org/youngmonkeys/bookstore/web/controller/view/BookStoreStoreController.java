package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

import java.util.Arrays;

@AllArgsConstructor
public class BookStoreStoreController {

    private final WebPageFragmentManager pageFragmentManager;

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
                        Arrays.asList("cate test 1", "cate test 2", "cate test 3")
                )
                .build();
    }
}
