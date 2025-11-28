package org.youngmonkeys.personal.web.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.personal.service.PersonalPostWordCountService;
import org.youngmonkeys.personal.web.repo.WebPersonalPostWordCountRepository;

@Service
public class WebPersonalPostWordCountService
    extends PersonalPostWordCountService {

    public WebPersonalPostWordCountService(
        WebPersonalPostWordCountRepository postWordCountRepository
    ) {
        super(postWordCountRepository);
    }
}
