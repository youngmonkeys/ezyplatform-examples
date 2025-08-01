package org.youngmonkeys.personal.admin.controller.view;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.admin.service.AdminPostService;
import org.youngmonkeys.ezyarticle.sdk.entity.PostStatus;
import org.youngmonkeys.ezyarticle.sdk.entity.PostType;

@Controller
@Authenticated
@AllArgsConstructor
@EzyFeature("dashboard")
public class PersonalAdminDashboardController {

    private final AdminPostService postService;

    @DoGet("/dashboard")
    public View dashboardGet() {
        return View.builder()
            .template("personal/dashboard/index")
            .addVariable(
                "blogCount",
                postService.countPostsByTypeAndStatus(
                    PostType.BLOG,
                    PostStatus.PUBLISHED
                )
            )
            .build();
    }

    @DoGet("/dashboard/posts")
    public View dashboardPostsGet() {
        return View.builder()
            .template("personal/dashboard/posts")
            .build();
    }
}
