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
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.ezyplatform.web.view.WebViewDecorator;

import javax.servlet.http.HttpServletRequest;

@EzySingleton
@AllArgsConstructor
public class BookStoreWebViewDecorator extends WebViewDecorator {

    private final WebShoppingCartService shoppingCartService;

    @Override
    protected void decorateWithUserData(
        HttpServletRequest request,
        View view,
        UserModel user
    ) {
        Reactive.multiple()
            .register(
                "shoppingCartProductCount",
                () -> shoppingCartService
                    .getShoppingCartProductCountByOwnerId(user.getId())
            )
            .blockingConsume(map ->
                view.setVariables(map.valueMap())
            );
    }

    @Override
    protected boolean includeWebLanguages() {
        return true;
    }
}
