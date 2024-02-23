package org.youngmonkeys.bookstore.service;

import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.service.SettingService;

import static org.youngmonkeys.bookstore.constant.BookStoreConstants.SETTING_NAME_BOOK_AUTHOR_ROLE_ID;

@AllArgsConstructor
public class BookStoreSettingService {

    private final SettingService settingService;

    public long getBookAuthorRoleId() {
        return settingService.getLongValue(
            SETTING_NAME_BOOK_AUTHOR_ROLE_ID
        );
    }
}
