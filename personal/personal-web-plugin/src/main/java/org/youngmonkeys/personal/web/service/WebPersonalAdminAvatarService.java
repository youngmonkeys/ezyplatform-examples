package org.youngmonkeys.personal.web.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.entity.Admin;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;
import org.youngmonkeys.personal.web.repo.WebPersonalAdminRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WebPersonalAdminAvatarService {

    private final WebMediaService mediaService;
    private final WebPersonalAdminRepository adminRepository;

    public Map<String, MediaNameModel> getAvatarMapByUuids(
        Collection<String> uuids
    ) {
        if (uuids.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Long> avatarImageIdByUuid = adminRepository
            .findByUuidIn(uuids)
            .stream()
            .collect(
                Collectors.toMap(
                    Admin::getUuid,
                    Admin::getAvatarImageId
                )
            );
        Map<Long, MediaNameModel> avatarById = mediaService
            .getMediaNameMapByIds(avatarImageIdByUuid.values());
        return avatarImageIdByUuid
            .entrySet()
            .stream()
            .filter(e -> avatarById.containsKey(e.getValue()))
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    e -> avatarById.get(e.getValue())
                )
            );
    }
}
