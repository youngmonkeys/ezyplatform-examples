package org.youngmonkeys.personal.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.sdk.entity.Post;
import org.youngmonkeys.ezyarticle.web.response.WebPostContentResponse;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.model.UuidNameModel;
import org.youngmonkeys.personal.web.service.WebPersonalAdminAvatarService;
import org.youngmonkeys.personal.web.service.WebPersonalPostWordCountService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class WebPersonalBlogsViewDecorator {

    private final WebPersonalAdminAvatarService adminAvatarService;
    private final WebPersonalPostWordCountService postWordCountService;

    public void decorateBlogView(
        View view
    ) {
        PaginationModel<WebPostContentResponse> pagination = view
            .getVariable("pagination");
        List<WebPostContentResponse> posts = pagination.getItems();
        List<Long> postIds = newArrayList(
            posts,
            WebPostContentResponse::getId
        );
        Set<String> authorUuids = posts
            .stream()
            .map(WebPostContentResponse::getAuthor)
            .filter(Objects::nonNull)
            .map(UuidNameModel::getUuid)
            .collect(Collectors.toSet());
        view.setVariable(
            "readTimeInMinutesByPostId",
            postWordCountService.getReadTimeInMinutesByPostIds(postIds)
        );
        view.setVariable(
            "authorAvatarByUuid",
            adminAvatarService.getAvatarMapByUuids(authorUuids)
        );
    }
}
