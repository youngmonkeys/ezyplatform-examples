package org.youngmonkeys.sweet_bakery.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_DESCRIPTION;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_PAGE_TITLE;

@Controller
public class WebSweetBakeryOrderLookupController {

    @DoGet("/tra-cuu-don-hang")
    public View orderLookup() {
        return View.builder()
            .template("order-lookup")
            .addVariable(VIEW_VARIABLE_PAGE_TITLE, "order_lookup_page_title")
            .addVariable(VIEW_VARIABLE_PAGE_DESCRIPTION, "order_lookup_page_description")
            .build();
    }
}
