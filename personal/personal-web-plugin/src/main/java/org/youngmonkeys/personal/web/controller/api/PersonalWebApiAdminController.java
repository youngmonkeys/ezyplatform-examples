package org.youngmonkeys.personal.web.controller.api;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.web.validator.WebCommonValidator;
import org.youngmonkeys.personal.web.service.WebPersonalAdminAvatarService;

import java.util.Map;
import java.util.Set;

@Controller("/api/v1")
@AllArgsConstructor
public class PersonalWebApiAdminController {

    private final WebPersonalAdminAvatarService adminAvatarService;
    private final WebCommonValidator commonValidator;

    @DoGet("/admins/avatars")
    public Map<String, MediaNameModel> adminsAvatarsGet(
        @RequestParam Set<String> uuids
    ) {
        commonValidator.validateCollectionSize(
            "uuids",
            uuids
        );
        return adminAvatarService.getAvatarMapByUuids(uuids);
    }
}
