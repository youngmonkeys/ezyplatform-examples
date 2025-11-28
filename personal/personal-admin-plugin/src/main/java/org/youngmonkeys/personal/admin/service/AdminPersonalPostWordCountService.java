package org.youngmonkeys.personal.admin.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.personal.admin.repo.AdminPersonalPostWordCountRepository;
import org.youngmonkeys.personal.service.PersonalPostWordCountService;

@Service
public class AdminPersonalPostWordCountService
    extends PersonalPostWordCountService {

    public AdminPersonalPostWordCountService(
        AdminPersonalPostWordCountRepository postWordCountRepository
    ) {
        super(postWordCountRepository);
    }
}
