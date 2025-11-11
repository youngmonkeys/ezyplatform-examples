package org.youngmonkeys.bookstore.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.view.View;
import org.youngmonkeys.ezyarticle.web.controller.view.PostController;

@Controller
public class WebBookStorePostController extends PostController {

    @Override
    protected void decoratePostsView(View.Builder viewBuilder) {
        throw new UnsupportedOperationException("unsupported");
    }
}
