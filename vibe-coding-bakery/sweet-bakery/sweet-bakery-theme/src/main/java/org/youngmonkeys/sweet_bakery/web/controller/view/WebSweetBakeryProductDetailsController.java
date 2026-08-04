package org.youngmonkeys.sweet_bakery.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.web.controller.service.WebProductControllerService;
import org.youngmonkeys.ecommerce.web.response.WebProductDetailsResponse;
import org.youngmonkeys.ecommerce.web.service.WebEcommerceSettingService;
import org.youngmonkeys.ecommerce.web.service.WebProductCurrencyService;
import org.youngmonkeys.ezyplatform.exception.ResourceNotFoundException;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
@AllArgsConstructor
public class WebSweetBakeryProductDetailsController {

    private static final String LANGUAGE = "vi";

    private final WebProductControllerService productControllerService;
    private final WebProductCurrencyService productCurrencyService;
    private final WebEcommerceSettingService ecommerceSettingService;

    @DoGet("/chi-tiet-banh")
    public View productDetails(
        @RequestParam("slug") String slug
    ) {
        ProductCurrencyModel currency = productCurrencyService
            .getDefaultCurrency();
        WebProductDetailsResponse pdProduct = null;
        if (slug != null) {
            try {
                pdProduct = productControllerService
                    .getProductDetailsByCodeAndStatuses(
                        slug,
                        ecommerceSettingService.getDisplayableProductStatuses(),
                        LANGUAGE,
                        currency
                    );
            } catch (ResourceNotFoundException e) {
                pdProduct = null;
            }
        }
        return View.builder()
            .template("product-details")
            .addVariable("pdProduct", pdProduct)
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "product_details_page_title")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "product_details_page_description")
            .build();
    }
}
