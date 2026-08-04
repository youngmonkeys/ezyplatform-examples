package org.youngmonkeys.sweet_bakery.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
public class WebSweetBakeryOrderConfirmationController {

    @DoGet("/xac-nhan-don-hang")
    public View orderConfirmation() {
        return View.builder()
            .template("order-confirmation")
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "order_confirmation_page_title")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "order_confirmation_page_description")
            .build();
    }
}
