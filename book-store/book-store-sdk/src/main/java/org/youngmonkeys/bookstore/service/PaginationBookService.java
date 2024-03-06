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

package org.youngmonkeys.bookstore.service;

import org.youngmonkeys.bookstore.pagination.BookFilter;
import org.youngmonkeys.bookstore.pagination.BookPaginationParameter;
import org.youngmonkeys.bookstore.pagination.BookPaginationParameterConverter;
import org.youngmonkeys.bookstore.pagination.IdDescBookPaginationParameter;
import org.youngmonkeys.bookstore.repo.PaginationBookRepository;
import org.youngmonkeys.ecommerce.converter.EcommerceEntityToModelConverter;
import org.youngmonkeys.ecommerce.entity.ProductBook;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ezyplatform.service.CommonPaginationService;

public class PaginationBookService extends CommonPaginationService<
    ProductBookModel,
    BookFilter,
    BookPaginationParameter,
    Long,
    ProductBook> {

    private final EcommerceEntityToModelConverter entityToModelConverter;

    public PaginationBookService(
        PaginationBookRepository repository,
        EcommerceEntityToModelConverter entityToModelConverter,
        BookPaginationParameterConverter paginationParameterConverter
    ) {
        super(repository, paginationParameterConverter);
        this.entityToModelConverter = entityToModelConverter;
    }


    @Override
    protected ProductBookModel convertEntity(ProductBook entity) {
        return entityToModelConverter.toModel(entity);
    }

    @Override
    protected BookPaginationParameter defaultPaginationParameter() {
        return new IdDescBookPaginationParameter();
    }
}
