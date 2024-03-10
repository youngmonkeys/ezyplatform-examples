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

package org.youngmonkeys.bookstore.admin.controller.view;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.server.core.annotation.*;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.admin.controller.view.AdminProductController;
import org.youngmonkeys.ecommerce.admin.service.AdminProductCurrencyService;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;

@Authenticated
@Controller
@EzyFeature("book_management")
@AllArgsConstructor
public class AdminBookController {

    private final AdminProductCurrencyService productCurrencyService;
    private final AdminProductController productController;

    @DoGet("/books")
    public View booksGet(
        @RequestParam(name = "status") String status
    ) {
        ProductCurrencyModel defaultCurrency = productCurrencyService
            .getDefaultCurrency();
        return View.builder()
            .template("book-store/book/list")
            .addVariable("defaultCurrency", defaultCurrency)
            .addVariable("productSearchStatus", status)
            .addVariable("productStatuses", ProductStatus.values())
            .addVariable("enableAddNewButton", true)
            .build();
    }

    @DoGet("/books/{id}")
    public View booksIdGet(
        @PathVariable long productId,
        @RequestParam("i18n_lang") String language
    ) {
        View view = productController.productsIdGet(
            productId,
            language
        );
        decorateView(view);
        return view;
    }

    @DoGet("/books/add")
    public View booksAddGet() {
        View view = productController.productsAddGet();
        decorateView(view);
        return view;
    }

    @DoGet("/books/{id}/edit")
    public View booksIdEditGet(
        @PathVariable long productId
    ) {
        View view = productController.productsIdEditGet(
            productId
        );
        decorateView(view);
        return view;
    }

    private void decorateView(View view) {
        view.setVariable("currentParentTitle", "books");
        view.setVariable("currentParentURL", "/book-store/books");
    }
}
