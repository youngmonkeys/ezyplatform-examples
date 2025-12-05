package org.youngmonkeys.bookstore.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.controller.service.WebBookControllerService;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

@EzySingleton
@AllArgsConstructor
public class ViewFactory {

    private final WebPageFragmentManager pageFragmentManager;
    private final WebProductCurrencyService currencyService;
    private final WebBookControllerService bookControllerService;

    public View.Builder newHomeViewBuilder(
        long currencyId,
        String language
    ) {
        ProductCurrencyModel currency = currencyService
            .getCurrencyByIdOrDefault(currencyId);
        return View.builder()
            .template("home")
            .addVariable("pageTitle", "home")
            .addVariable(
                "highlightBooks",
                bookControllerService.getHighlightBooks(
                    currency,
                    1
                )
            )
            .addVariable(
                "bestsellingBooks",
                bookControllerService.getBestsellingBooks(
                    currency,
                    6
                )
            )
            .addVariable(
                "newBooks",
                bookControllerService.getNewBooks(
                    currency,
                    6
                )
            )
            .addVariable(
                "fragments",
                pageFragmentManager.getPageFragmentMap(
                    "home",
                    language
                )
            );
    }
}
