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

package org.youngmonkeys.bookstore.admin.controller.api;

import com.tvd12.ezyfox.annotation.EzyFeature;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.controller.service.AdminBookControllerService;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.bookstore.pagination.DefaultBookFilter;
import org.youngmonkeys.ecommerce.admin.service.AdminProductCurrencyService;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ezyplatform.admin.validator.AdminCommonValidator;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

@Api
@Authenticated
@Controller("/api/v1")
@EzyFeature("book_management")
@AllArgsConstructor
public class AdminApiBookController {

    private final AdminProductCurrencyService productCurrencyService;
    private final AdminBookControllerService bookControllerService;
    private final AdminCommonValidator commonValidator;

    @DoGet("/books")
    public PaginationModel<AdminBookResponse> booksGet(
        @RequestParam(name = "status") String status,
        @RequestParam(value = "keyword") String keyword,
        @RequestParam(value = "nextPageToken") String nextPageToken,
        @RequestParam(value = "prevPageToken") String prevPageToken,
        @RequestParam(value = "lastPage") boolean lastPage,
        @RequestParam(value = "limit", defaultValue = "30") int limit
    ) {
        commonValidator.validatePageSize(limit);
        ProductCurrencyModel defaultCurrency = productCurrencyService
            .getDefaultCurrency();
        return bookControllerService.getBooks(
            DefaultBookFilter.builder()
                .status(status)
                .likeKeyword(keyword)
                .build(),
            nextPageToken,
            prevPageToken,
            lastPage,
            limit,
            defaultCurrency.getId(),
            defaultCurrency.getFormat()
        );
    }
}
