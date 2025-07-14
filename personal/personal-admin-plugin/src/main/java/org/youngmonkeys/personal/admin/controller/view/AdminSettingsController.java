package org.youngmonkeys.personal.admin.controller.view;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;

@Controller
@Authenticated
@EzyFeature("settings_management")
public class AdminSettingsController {

    @DoGet("/settings")
    public View settingsGet() {
        return View.builder()
            .template("personal/settings")
            .build();
    }
}
