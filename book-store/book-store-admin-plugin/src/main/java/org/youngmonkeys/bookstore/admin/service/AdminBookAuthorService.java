package org.youngmonkeys.bookstore.admin.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.admin.repo.AdminUserRoleRepository;

@Service
@AllArgsConstructor
public class AdminBookAuthorService {

    private final AdminBookStoreSettingService settingService;
    private final AdminUserRoleRepository userRoleRepository;

    public long countAllAuthors() {
        return userRoleRepository.countByRoleId(
            settingService.getBookAuthorRoleId()
        );
    }
}
