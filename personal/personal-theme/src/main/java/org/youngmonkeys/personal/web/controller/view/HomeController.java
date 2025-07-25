package org.youngmonkeys.personal.web.controller.view;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.Setter;
import org.youngmonkeys.ezyarticle.sdk.entity.PostStatus;
import org.youngmonkeys.ezyarticle.sdk.entity.PostType;
import org.youngmonkeys.ezyarticle.sdk.pagination.DefaultPostFilter;
import org.youngmonkeys.ezyarticle.web.controller.service.WebPostControllerService;
import org.youngmonkeys.ezyarticle.web.response.WebPostItemResponse;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;
import org.youngmonkeys.ezyplatform.web.validator.WebCommonValidator;

import javax.servlet.http.HttpServletRequest;

@Setter
@Controller
public class HomeController {

    @EzyAutoBind
    private WebLanguageControllerService languageControllerService;

    @EzyAutoBind
    private WebPostControllerService postControllerService;

    @EzyAutoBind
    private WebCommonValidator commonValidator;

    @DoGet("/")
    public View home(
        HttpServletRequest request,
        @RequestParam(value = "sortOrder") String sortOrder,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        commonValidator.validatePageSize(limit);
        DefaultPostFilter filter = DefaultPostFilter
            .builder()
            .postType(PostType.BLOG.toString())
            .postStatus(PostStatus.PUBLISHED.toString())
            .build();
        String language = languageControllerService
            .getLanguageCodeOrDefault(request);
        PaginationModel<WebPostItemResponse> pagination = postControllerService
            .getPostItemPagination(
                filter,
                language,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit
            );
        return View.builder()
            .template("home")
            .addVariable("pagination", pagination)
            .addVariable("pageTitle", "home")
            .build();
    }
}
