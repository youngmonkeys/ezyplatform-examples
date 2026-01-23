package org.youngmonkeys.personal.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.web.response.TopBlogResponse;
import org.youngmonkeys.personal.web.service.WebPersonalBlogTopViewService;

import java.util.List;

@EzySingleton
@AllArgsConstructor
public class WebPersonalBlogDetailsViewDecorator {

    private final WebPersonalBlogTopViewService blogService;

    public void decorateBlogDetailsView(
        View view,
        String languageCode
    ) {
        List<TopBlogResponse> topViewedBlogs = blogService.getTopViewedBlogs(6, languageCode);

        view.setVariable(
            "topViewedBlogs",
            topViewedBlogs
        );
    }
}
