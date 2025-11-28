package org.youngmonkeys.personal.web.controller.api;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.web.validator.WebCommonValidator;
import org.youngmonkeys.personal.service.PersonalPostWordCountService;

import java.util.Map;
import java.util.Set;

@Controller("/api/v1")
@AllArgsConstructor
public class PersonalWebApiPostController {

    private final PersonalPostWordCountService postWordCountService;
    private final WebCommonValidator commonValidator;

    @DoGet("/posts/read-time-in-minutes")
    public Map<Long, Long> postsReadTimeInMinutesGet(
        @RequestParam Set<Long> postIds
    ) {
        commonValidator.validateCollectionSize(
            "postIds",
            postIds
        );
        return postWordCountService.getReadTimeInMinutesByPostIds(
            postIds
        );
    }
}
