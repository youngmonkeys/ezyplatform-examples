/*
 * Copyright 2022 youngmonkeys.org
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

package org.youngmonkeys.bookstore.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.web.service.WebShoppingCartService;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;
import org.youngmonkeys.ezyplatform.web.view.WebViewDecorator;

import javax.servlet.http.HttpServletRequest;

@EzySingleton
@AllArgsConstructor
public class BookStoreWebViewDecorator extends WebViewDecorator {

    private final WebPageFragmentManager pageFragmentManager;
    private final WebShoppingCartService shoppingCartService;
    private final WebLanguageControllerService languageControllerService;

    @Override
    public void decorate(HttpServletRequest request, View view) {
        super.decorate(request, view);
        String languageCode = languageControllerService
            .getLanguageCodeOrDefault(request);
        view.setVariable(
            "commonFragments",
            pageFragmentManager.getPageFragmentMap(
                "common",
                languageCode
            )
        );
    }

    @Override
    protected void decorateWithUserData(
        HttpServletRequest request,
        View view,
        UserModel user
    ) {
        view.setVariable(
            "shoppingCartProductCount",
            shoppingCartService
                .getShoppingCartProductCountByOwnerId(
                    user.getId()
                )
        );
    }

    @Override
    protected boolean includeWebLanguages() {
        return true;
    }
}
