package org.youngmonkeys.personal.web.service;

import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.ezyarticle.sdk.entity.PostStatus;
import org.youngmonkeys.ezyarticle.sdk.entity.PostType;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.sdk.repo.PostRepository;
import org.youngmonkeys.ezyarticle.sdk.result.PostIdAndTitleResult;
import org.youngmonkeys.ezyarticle.web.response.WebPostContentResponse;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;
import org.youngmonkeys.personal.result.PostTitleAndFeaturedImageResult;
import org.youngmonkeys.personal.web.repo.WebPersonalBlogTopViewRepository;
import org.youngmonkeys.personal.web.response.TopBlogResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WebPersonalBlogTopViewService {
    private final WebPersonalBlogTopViewRepository blogTopViewRepository;
    private final PostRepository postRepository;
    private final WebMediaService mediaService;

    public WebPersonalBlogTopViewService(
        WebPersonalBlogTopViewRepository blogTopViewRepository,
        PostRepository postRepository,
        WebMediaService mediaService) {
        this.blogTopViewRepository = blogTopViewRepository;
        this.postRepository = postRepository;
        this.mediaService = mediaService;
    }

    public List<TopBlogResponse> getTopViewedBlogs(int limit) {
        List<PostTitleAndFeaturedImageResult> posts = blogTopViewRepository.findTopPublicBlogOrderByViews(
            "víews",
            PostType.BLOG.toString(),
            PostStatus.PUBLISHED.toString(),
            Next.limit(6)
        );

        if (posts.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> imageIds = posts.stream()
            .map(PostTitleAndFeaturedImageResult::getFeaturedImageId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());

        Map<Long, MediaNameModel> mediaMap = imageIds.isEmpty()
            ? Collections.emptyMap()
            : mediaService.getMediaNameMapByIds(imageIds);

        return posts.stream().map(post -> {
            MediaNameModel media = mediaMap.get(post.getFeaturedImageId());
            String imageUrl = media != null
                ? media.getUrlOrDefault(null)
                : "";
            return new TopBlogResponse(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                imageUrl,
                post.getViews(),
                post.getPublishedAt()
            );
        }).collect(Collectors.toList());
    }

    public List<PostIdAndTitleResult> getTopViewedPostIds(int limit) {
        List<Long> postIds =
            blogTopViewRepository.findTopPostIdsByMetaKey(
                "views",
                Next.limit(limit)
            );

        if (postIds.isEmpty()) {
            return Collections.emptyList();
        }

        return postRepository.findPostIdAndTitlesByIdIn(postIds);
    }
}
