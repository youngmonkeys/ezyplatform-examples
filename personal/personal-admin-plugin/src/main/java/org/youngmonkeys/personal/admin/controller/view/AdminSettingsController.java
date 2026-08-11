package org.youngmonkeys.personal.admin.controller.view;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.admin.manager.AdminDataAppenderManager;
import org.youngmonkeys.personal.admin.service.AdminPersonalSettingService;

@Controller
@Authenticated
@EzyFeature("settings_management")
@AllArgsConstructor
public class AdminSettingsController {

    private final AdminDataAppenderManager dataAppenderManager;
    private final AdminPersonalSettingService personalSettingService;

    @DoGet("/settings")
    public View settingsGet() {
        return View.builder()
            .template("personal/settings")
            .addVariable(
                "dataAppenderManager",
                dataAppenderManager
            )
            .addVariable(
                "allowCalculatePostReadTime",
                personalSettingService.isAllowCalculatePostReadTime()
            )
            .addVariable(
                "showCoinWidget",
                personalSettingService.isShowCoinWidget()
            )
            .build();
    }
}
