package org.youngmonkeys.personal.web.service;

import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.ezyarticle.sdk.entity.PostStatus;
import org.youngmonkeys.ezyarticle.sdk.entity.PostType;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.sdk.service.PostService;
import org.youngmonkeys.personal.result.PostIdAndNumberViewsResult;
import org.youngmonkeys.personal.web.decorator.WebTopBlogDecorator;
import org.youngmonkeys.personal.web.repo.WebPersonalBlogTopViewRepository;
import org.youngmonkeys.personal.web.response.TopBlogResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebPersonalBlogTopViewControllerService {
    private final WebPersonalBlogTopViewRepository blogTopViewRepository;
    private final PostService postService;
    private final WebTopBlogDecorator blogDecorator;

    public WebPersonalBlogTopViewControllerService(
        WebPersonalBlogTopViewRepository blogTopViewRepository,
        PostService postService,
        WebTopBlogDecorator blogDecorator
    ) {
        this.blogTopViewRepository = blogTopViewRepository;
        this.postService = postService;
        this.blogDecorator = blogDecorator;
    }

    public List<TopBlogResponse> getTopViewedBlogs(int limit, String language) {
        List<PostIdAndNumberViewsResult> rows = blogTopViewRepository
            .findTopPostByMetaKeyAndTypeAndStatusOrderByViews(
            "víews",
            PostType.BLOG.toString(),
            PostStatus.PUBLISHED.toString(),
            Next.limit(6)
        );

        if (rows.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> postIds = rows.stream()
            .map(PostIdAndNumberViewsResult::getId)
            .collect(Collectors.toList());

        List<PostModel> posts =
            postService.getShortedPostsByIds(postIds);

        Map<Long, Long> viewsMap = rows.stream()
            .collect(Collectors.toMap(
                PostIdAndNumberViewsResult::getId,
                PostIdAndNumberViewsResult::getViews
            ));

        return blogDecorator
            .decorate(posts, viewsMap, language);
    }
}
