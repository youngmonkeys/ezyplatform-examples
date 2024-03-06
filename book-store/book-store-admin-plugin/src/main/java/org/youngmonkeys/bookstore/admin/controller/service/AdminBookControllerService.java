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

import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.controller.decorator.AdminBookModelDecorator;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.bookstore.admin.service.AdminPaginationBookService;
import org.youngmonkeys.bookstore.pagination.BookFilter;
import org.youngmonkeys.ecommerce.admin.controller.decorator.AdminProductModelDecorator;
import org.youngmonkeys.ecommerce.admin.service.AdminProductService;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.response.ProductResponse;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import static java.util.Collections.singletonMap;
import static org.youngmonkeys.ezyplatform.pagination.PaginationModelFetchers.getPaginationModel;

@Service
@AllArgsConstructor
public class AdminBookControllerService {

    private final AdminProductService productService;
    private final AdminPaginationBookService paginationBookService;
    private final AdminProductModelDecorator productModelDecorator;
    private final AdminBookModelDecorator bookModelDecorator;

    public ProductResponse getBookById(
        long productId,
        long currencyId,
        String currencyFormat
    ) {
        ProductModel model = productService.getProductById(
            productId
        );
        if (model == null) {
            throw new HttpNotFoundException(
                singletonMap("product", "notFound")
            );
        }
        return productModelDecorator.decorate(
            model,
            currencyId,
            currencyFormat
        );
    }

    public ProductResponse getProductByCode(
        String productCode,
        long currencyId,
        String currencyFormat
    ) {
        ProductModel model = productService.getProductByCode(
            productCode
        );
        if (model == null) {
            throw new HttpNotFoundException(
                singletonMap("product", "notFound")
            );
        }
        return productModelDecorator.decorate(
            model,
            currencyId,
            currencyFormat
        );
    }

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
