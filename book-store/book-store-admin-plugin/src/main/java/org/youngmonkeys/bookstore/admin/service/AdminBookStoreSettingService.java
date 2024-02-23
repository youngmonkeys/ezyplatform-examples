package org.youngmonkeys.bookstore.admin.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.bookstore.service.BookStoreSettingService;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;

@Service
public class AdminBookStoreSettingService
    extends BookStoreSettingService {

    public AdminBookStoreSettingService(
        AdminSettingService settingService
    ) {
        super(settingService);
    }
}
