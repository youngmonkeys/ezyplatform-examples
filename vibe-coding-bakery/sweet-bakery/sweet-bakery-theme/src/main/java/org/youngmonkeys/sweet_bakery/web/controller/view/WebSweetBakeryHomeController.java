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
import org.youngmonkeys.ecommerce.web.service.WebProductCategoryService;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ecommerce.web.service.WebShopService;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
@AllArgsConstructor
public class WebSweetBakeryHomeController {

    private static final String CATEGORY_NAME = "banh";
    private static final String LANGUAGE = "vi";
    private static final int LIMIT_FEATURED_PRODUCTS = 12;

    private final WebProductCategoryService productCategoryService;
    private final WebProductControllerService productControllerService;
    private final WebProductCurrencyService productCurrencyService;
    private final WebEcommerceSettingService ecommerceSettingService;
    private final WebShopService shopService;

    @DoGet(value = "/sweet-home-bakery", otherUris = "/")
    public View home() {
        long shopId = shopService.getDefaultShopId();
        long categoryId = productCategoryService.getCategoryIdByNameAndStatuses(
            CATEGORY_NAME,
            ecommerceSettingService.getDisplayableProductCategoryStatuses()
        );
        ProductCurrencyModel currency = productCurrencyService
            .getDefaultCurrency();
        DefaultProductFilter productFilter = DefaultProductFilter.builder()
            .shopId(shopId)
            .statuses(ecommerceSettingService.getDisplayableProductStatuses())
            .inclusiveCategoryId(categoryId)
            .build();
        PaginationModel<WebProductResponse> featuredProducts =
            productControllerService.getWebProductPagination(
                LANGUAGE,
                productFilter,
                null,
                null,
                null,
                false,
                LIMIT_FEATURED_PRODUCTS,
                currency
            );
        return View.builder()
            .template("home")
            .addVariable("featuredProducts", featuredProducts)
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "site_name")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "home_page_description")
            .build();
    }
}
