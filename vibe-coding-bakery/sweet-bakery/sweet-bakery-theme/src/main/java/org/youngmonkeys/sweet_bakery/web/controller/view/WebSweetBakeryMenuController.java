package org.youngmonkeys.sweet_bakery.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.pagination.DefaultProductFilter;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductControllerService;
import org.youngmonkeys.ecommerce.web.response.WebProductResponse;
import org.youngmonkeys.ecommerce.web.service.WebEcommerceSettingService;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ecommerce.web.service.WebShopService;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
@AllArgsConstructor
public class WebSweetBakeryMenuController {

    private static final String LANGUAGE = "vi";
    private static final int LIMIT_MENU_PRODUCTS = 9;

    private final WebProductControllerService productControllerService;
    private final WebProductCurrencyService productCurrencyService;
    private final WebEcommerceSettingService ecommerceSettingService;
    private final WebShopService shopService;

    @DoGet("/thuc-don")
    public View menu() {
        long shopId = shopService.getDefaultShopId();
        ProductCurrencyModel currency = productCurrencyService
            .getDefaultCurrency();
        DefaultProductFilter productFilter = DefaultProductFilter.builder()
            .shopId(shopId)
            .statuses(ecommerceSettingService.getDisplayableProductStatuses())
            .build();
        PaginationModel<WebProductResponse> menuProducts =
            productControllerService.getWebProductPagination(
                LANGUAGE,
                productFilter,
                null,
                null,
                null,
                false,
                LIMIT_MENU_PRODUCTS,
                currency
            );
        return View.builder()
            .template("menu")
            .addVariable("menuProducts", menuProducts)
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "menu_page_title")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "menu_page_description")
            .build();
    }
}
