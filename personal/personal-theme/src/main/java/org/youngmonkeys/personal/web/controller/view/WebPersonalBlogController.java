/*
 * Copyright 2024 youngmonkeys.org
 * 
 * Licensed under the ezyplatform, Version 1.0.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://youngmonkeys.org/licenses/ezyplatform-1.0.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.youngmonkeys.personal.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.web.controller.view.BlogController;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;
import org.youngmonkeys.personal.web.view.WebPersonalBlogDetailsViewDecorator;
import org.youngmonkeys.personal.web.view.WebPersonalBlogsViewDecorator;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class WebPersonalBlogController extends BlogController {

    private final WebPersonalBlogsViewDecorator blogsViewDecorator;
    private final WebPersonalBlogDetailsViewDecorator blogDetailsViewDecorator;
    private WebLanguageControllerService languageControllerService;

    @Override
    protected void decorateBlogView(
        HttpServletRequest request,
        View view
    ) {
        blogsViewDecorator.decorateBlogView(view);
    }

    @Override
    protected void decorateBlogDetailsView(
        HttpServletRequest request,
        View view
    ) {
        String languageCode =
            languageControllerService.getLanguageCodeOrDefault(request);
        blogDetailsViewDecorator.decorateBlogDetailsView(view, languageCode);
    }
}
