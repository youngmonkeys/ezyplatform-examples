package org.youngmonkeys.bookstore.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.controller.service.WebBookControllerService;
import org.youngmonkeys.ecommerce.web.service.WebShopService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

@EzySingleton
@AllArgsConstructor
public class ViewFactory {

    private final WebPageFragmentManager pageFragmentManager;
    private final WebShopService shopService;
    private final WebBookControllerService bookControllerService;

    public View.Builder newHomeViewBuilder(String language) {
        long shopId = shopService.getDefaultShopId();
        return View.builder()
            .template("home")
            .addVariable("pageTitle", "home")
            .addVariable(
                "topBooks",
                bookControllerService.getTopBooksByShopId(shopId)
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
