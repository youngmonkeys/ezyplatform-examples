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
import org.youngmonkeys.ezyarticle.web.controller.service.WebPostControllerService;
import org.youngmonkeys.ezyarticle.web.controller.view.BlogController;
import org.youngmonkeys.ezyarticle.web.validator.WebTermValidator;
import org.youngmonkeys.ezyplatform.web.validator.WebCommonValidator;

@Controller
public class WebPersonalBlogController extends BlogController {

    public WebPersonalBlogController(
        WebPostControllerService postControllerService,
        WebCommonValidator commonValidator,
        WebTermValidator termValidator
    ) {
        super(
            postControllerService,
            commonValidator,
            termValidator
        );
    }
}
