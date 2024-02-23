package org.youngmonkeys.bookstore.admin.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.admin.model.AddUserRoleNameModel;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.ezyplatform.admin.service.AdminUserRoleService;

import static org.youngmonkeys.bookstore.constant.BookStoreConstants.*;

@EzyConfigurationAfter
@AllArgsConstructor
public class AdminBookStoreConfig implements EzyBeanConfig {

    private final AdminSettingService settingService;
    private final AdminUserRoleService userRoleService;

    @Override
    public void config() {
        long savedBookAuthorRoleId = settingService.getLongValue(
            SETTING_NAME_BOOK_AUTHOR_ROLE_ID
        );
        if (savedBookAuthorRoleId <= 0) {
            long roleId = userRoleService.saveUserRoleNameIfNotExists(
                AddUserRoleNameModel.builder()
                    .name(ROLE_AUTHOR_NAME)
                    .displayName(ROLE_AUTHOR_DISPLAY_NAME)
                    .build()
            ).getId();
            settingService.setLongValue(
                SETTING_NAME_BOOK_AUTHOR_ROLE_ID,
                roleId
            );
        }
    }
}
