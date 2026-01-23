package org.youngmonkeys.personal.web.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.ezyarticle.sdk.model.PostI18nModel;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.service.WebPostI18nService;
import org.youngmonkeys.ezyarticle.web.service.WebPostSlugService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;
import org.youngmonkeys.personal.web.response.TopBlogResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EzySingleton
public class WebTopBlogDecorator {
    private final WebMediaService mediaService;
    private final WebPostSlugService postSlugService;
    private final WebPostI18nService postI18nService;

    public WebTopBlogDecorator(
        WebMediaService mediaService,
        WebPostSlugService postSlugService,
        WebPostI18nService postI18nService
    ) {
        this.mediaService = mediaService;
        this.postSlugService = postSlugService;
        this.postI18nService = postI18nService;
    }

    public List<TopBlogResponse> decorate(
        List<PostModel> posts,
        Map<Long, Long> viewsMap,
        String language
    ) {
        if (posts.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> postIds = posts.stream()
            .map(PostModel::getId)
            .collect(Collectors.toList());

        Set<Long> mediaIds = posts.stream()
            .map(PostModel::getFeaturedImageId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());

        Map<Long, PostI18nModel> i18nMap =
            postI18nService.getShortedPostI18nMapByPostIdsAndLanguage(
                postIds, language
            );

        Map<Long, String> slugMap =
            postSlugService.getLatestSlugMapByPostIds(postIds);

        Map<Long, MediaNameModel> mediaMap =
            mediaService.getMediaNameMapByIds(mediaIds);

        return posts.stream()
            .map(post -> {
                PostI18nModel i18n = i18nMap.get(post.getId());
                MediaNameModel media = mediaMap.get(post.getFeaturedImageId());

                String title =
                    i18n != null ? i18n.getTitle() : post.getTitle();

                String imageUrl =
                    MediaNameModel.getMediaUrlOrNull(media);

                return new TopBlogResponse(
                    post.getId(),
                    title,
                    slugMap.get(post.getId()),
                    imageUrl,
                    viewsMap.get(post.getId()),
                    post.getPublishedAtDateTime()
                );
            })
            .collect(Collectors.toList());
    }
}
