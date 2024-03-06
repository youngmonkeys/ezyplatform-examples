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

package org.youngmonkeys.bookstore.admin.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.controller.decorator.AdminBookModelDecorator;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.bookstore.admin.service.AdminPaginationBookService;
import org.youngmonkeys.bookstore.pagination.BookFilter;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import static org.youngmonkeys.ezyplatform.pagination.PaginationModelFetchers.getPaginationModel;

@Service
@AllArgsConstructor
public class AdminBookControllerService {

    private final AdminPaginationBookService paginationBookService;
    private final AdminBookModelDecorator bookModelDecorator;

    public PaginationModel<AdminBookResponse> getBooks(
        BookFilter filter,
        String nextPageToken,
        String prevPageToken,
        boolean lastPage,
        int limit,
        long currencyId,
        String currencyFormat
    ) {
        PaginationModel<ProductBookModel> pagination = getPaginationModel(
            paginationBookService,
            filter,
            nextPageToken,
            prevPageToken,
            lastPage,
            limit
        );
        return bookModelDecorator.decorate(
            pagination,
            currencyId,
            currencyFormat
        );
    }
}
