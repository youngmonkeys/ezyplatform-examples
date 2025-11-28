package org.youngmonkeys.personal.web.controller.view;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.Setter;
import org.youngmonkeys.ezyarticle.sdk.entity.PostStatus;
import org.youngmonkeys.ezyarticle.sdk.entity.PostType;
import org.youngmonkeys.ezyarticle.sdk.entity.TermType;
import org.youngmonkeys.ezyarticle.sdk.pagination.DefaultPostFilter;
import org.youngmonkeys.ezyarticle.web.controller.service.WebPostControllerService;
import org.youngmonkeys.ezyarticle.web.controller.service.WebTermControllerService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;
import org.youngmonkeys.ezyarticle.web.response.WebPostContentResponse;
import org.youngmonkeys.ezyarticle.web.response.WebTermResponse;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.model.UuidNameModel;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;
import org.youngmonkeys.ezyplatform.web.service.WebSettingService;
import org.youngmonkeys.ezyplatform.web.validator.WebCommonValidator;
import org.youngmonkeys.personal.web.service.WebPersonalAdminAvatarService;
import org.youngmonkeys.personal.web.service.WebPersonalPostWordCountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static org.youngmonkeys.ezyplatform.util.StringConverters.trimOrNull;
import static org.youngmonkeys.ezysupport.constant.EzySupportConstants.SETTING_NAME_BANNER_IMAGE_URL;

@Setter
public class PersonalHomeController {

    @EzyAutoBind
    private WebPageFragmentManager pageFragmentManager;

    @EzyAutoBind
    private WebPersonalAdminAvatarService adminAvatarService;

    @EzyAutoBind
    private WebPersonalPostWordCountService postWordCountService;

    @EzyAutoBind
    private WebSettingService settingService;

    @EzyAutoBind
    private WebLanguageControllerService languageControllerService;

    @EzyAutoBind
    private WebPostControllerService postControllerService;

    @EzyAutoBind
    private WebTermControllerService termControllerService;

    @EzyAutoBind
    private WebCommonValidator commonValidator;

    @DoGet("/")
    public View home(
        HttpServletRequest request,
        @RequestParam(value = "keyword") String keyword,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        commonValidator.validatePageSize(limit);
        DefaultPostFilter.Builder filterBuilder = DefaultPostFilter
            .builder()
            .postType(PostType.BLOG.toString())
            .postStatus(PostStatus.PUBLISHED.toString());
        String languageCode = languageControllerService
            .getLanguageCodeOrDefault(request);
        PaginationModel<WebPostContentResponse> pagination = postControllerService
            .getPublishedBlogPagination(
                filterBuilder,
                sortOrder,
                keyword,
                languageCode,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit
            );
        List<WebTermResponse> topCategories = termControllerService
            .getActivatedTermsByTypeOrderByDisplayOrderDesc(
                TermType.CATEGORY.toString(),
                5
            );
        List<WebTermResponse> topTags = termControllerService
            .getActivatedTermsByTypeOrderByDisplayOrderDesc(
                TermType.TAG.toString(),
                50
            );
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
        return View.builder()
            .template("home")
            .addVariable("pagination", pagination)
            .addVariable("topCategories", topCategories)
            .addVariable("topTags", topTags)
            .addVariable(
                "readTimeInMinutesByPostId",
                postWordCountService.getReadTimeInMinutesByPostIds(postIds)
            )
            .addVariable(
                "authorAvatarByUuid",
                adminAvatarService.getAvatarMapByUuids(authorUuids)
            )
            .addVariable(
                "headingFragments",
                pageFragmentManager.getPageFragmentMap(
                    "main_page_heading",
                    languageCode
                )
            )
            .addVariable(
                "webBannerImageUrl",
                trimOrNull(
                    settingService.getTextValue(
                        SETTING_NAME_BANNER_IMAGE_URL
                    )
                )
            )
            .addVariable("pageTitle", "home")
            .build();
    }
}
