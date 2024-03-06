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

package org.youngmonkeys.bookstore.admin.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import com.tvd12.ezyhttp.server.core.view.ViewDecorator;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.service.AdminBookAuthorService;
import org.youngmonkeys.ecommerce.admin.service.AdminProductBookService;
import org.youngmonkeys.ezyplatform.rx.Reactive;

import javax.servlet.http.HttpServletRequest;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_MENUITEM_BADGES;

@EzySingleton
@AllArgsConstructor
public class AdminBookStoreViewDecorator implements ViewDecorator {

    private final AdminBookAuthorService bookAuthorService;
    private final AdminProductBookService productBookService;


    @Override
    public void decorate(HttpServletRequest request, View view) {
        Reactive.multiple()
            .register(
                "book_store.books",
                productBookService::countAllBooks
            )
            .register(
                "book_store.authors",
                bookAuthorService::countAllAuthors
            )
            .blockingConsume(it ->
                view.putKeyValuesToVariable(
                    VIEW_VARIABLE_MENUITEM_BADGES,
                    it.valueMap()
                )
            );
    }
}
