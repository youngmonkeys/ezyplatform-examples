package org.youngmonkeys.personal.admin.controller.api;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.personal.admin.request.AdminPersonalSaveSettingsRequest;
import org.youngmonkeys.personal.admin.service.AdminPersonalSettingService;

@Api
@Authenticated
@Controller("/api/v1")
@EzyFeature("settings_management")
@AllArgsConstructor
public class AdminApiSettingController {

    private final AdminPersonalSettingService personalSettingService;

    @SuppressWarnings("MethodLength")
    @DoPut("/settings")
    public ResponseEntity settingsPut(
        @RequestBody AdminPersonalSaveSettingsRequest request
    ) {
        Reactive.multiple()
            .registerOperation(() ->
                personalSettingService.setAllowCalculatePostReadTime(
                    request.isAllowCalculatePostReadTime()
                )
            )
            .registerOperation(() ->
                personalSettingService.setShowCoinWidget(
                    request.isShowCoinWidget()
                )
            )
            .blockingExecute();
        return ResponseEntity.noContent();
    }
}
