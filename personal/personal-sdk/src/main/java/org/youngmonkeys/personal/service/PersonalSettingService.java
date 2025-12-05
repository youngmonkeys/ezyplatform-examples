package org.youngmonkeys.personal.service;

import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.service.SettingService;

import static org.youngmonkeys.personal.constant.PersonalConstants.SETTING_KEY_ALLOW_CALCULATE_POST_READ_TIME;

@AllArgsConstructor
public class PersonalSettingService {

    private final SettingService settingService;

    public boolean isAllowCalculatePostReadTime() {
        return settingService.getBooleanValue(
            SETTING_KEY_ALLOW_CALCULATE_POST_READ_TIME,
            Boolean.TRUE
        );
    }
}
