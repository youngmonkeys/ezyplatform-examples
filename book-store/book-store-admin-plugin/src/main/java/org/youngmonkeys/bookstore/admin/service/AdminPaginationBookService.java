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

package org.youngmonkeys.bookstore.admin.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import org.youngmonkeys.bookstore.admin.pagination.AdminBookPaginationParameterConverter;
import org.youngmonkeys.bookstore.admin.repo.AdminPaginationBookRepository;
import org.youngmonkeys.bookstore.service.PaginationBookService;
import org.youngmonkeys.ecommerce.admin.converter.AdminEcommerceEntityToModelConverter;

@Service
public class AdminPaginationBookService extends PaginationBookService {

    public AdminPaginationBookService(
        AdminPaginationBookRepository repository,
        AdminEcommerceEntityToModelConverter entityToModelConverter,
        AdminBookPaginationParameterConverter paginationParameterConverter
    ) {
        super(
            repository,
            entityToModelConverter,
            paginationParameterConverter
        );
    }
}
