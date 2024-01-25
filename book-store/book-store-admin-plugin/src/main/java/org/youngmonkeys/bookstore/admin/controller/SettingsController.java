package org.youngmonkeys.bookstore.admin.controller;

import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;

@Controller
@Authenticated
public class SettingsController {

    @DoGet("/settings")
    public View settingsGet() {
        return View.builder()
            .template("book-store/settings")
            .build();
    }
}
