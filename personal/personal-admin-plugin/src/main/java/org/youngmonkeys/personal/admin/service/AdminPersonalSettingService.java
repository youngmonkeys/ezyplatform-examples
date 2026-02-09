package org.youngmonkeys.personal.admin.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.personal.service.PersonalSettingService;

import static org.youngmonkeys.personal.constant.PersonalConstants.SETTING_KEY_ALLOW_CALCULATE_POST_READ_TIME;
import static org.youngmonkeys.personal.constant.PersonalConstants.SETTING_KEY_SHOW_COIN_PRICE;

@Service
public class AdminPersonalSettingService extends PersonalSettingService {

    private final AdminSettingService settingService;

    public AdminPersonalSettingService(
        AdminSettingService settingService
    ) {
        super(settingService);
        this.settingService = settingService;
    }

    public void setAllowCalculatePostReadTime(boolean allow) {
        settingService.setBooleanValue(
            SETTING_KEY_ALLOW_CALCULATE_POST_READ_TIME,
            allow
        );
    }

    public void setShowCoinWidget(boolean allow) {
        settingService.setBooleanValue(
            SETTING_KEY_SHOW_COIN_PRICE,
            allow
        );
    }
}
