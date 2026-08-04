package org.youngmonkeys.sweet_bakery.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
public class WebSweetBakeryCheckoutController {

    @DoGet("/thanh-toan")
    public View checkout() {
        return View.builder()
            .template("checkout")
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "checkout_page_title")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "checkout_page_description")
            .build();
    }
}
