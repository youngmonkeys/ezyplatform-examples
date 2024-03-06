/*
 * Copyright 2023 youngmonkeys.org
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

package org.youngmonkeys.bookstore.pagination;

import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ezyplatform.pagination.ComplexPaginationParameterConverter;
import org.youngmonkeys.ezyplatform.pagination.PaginationParameterConverter;
import org.youngmonkeys.ezyplatform.time.ClockProxy;

import java.util.Map;
import java.util.function.Function;

public class BookPaginationParameterConverter
    extends ComplexPaginationParameterConverter<
        String,
        ProductBookModel
    > {

    private final ClockProxy clock;

    public BookPaginationParameterConverter(
        ClockProxy clock,
        PaginationParameterConverter converter
    ) {
        super(converter);
        this.clock = clock;
    }

    @Override
    protected void mapPaginationParametersToTypes(
        Map<String, Class<?>> map
    ) {
        map.put(
            BookPaginationSortOrder.ID_DESC.toString(),
            IdDescBookPaginationParameter.class
        );
        map.put(
            BookPaginationSortOrder.RELEASED_AT_DESC_ID_DESC.toString(),
            ReleasedAtDescIdDescBookPaginationParameter.class
        );
    }

    @Override
    protected void addPaginationParameterExtractors(
        Map<String, Function<ProductBookModel, Object>> map
    ) {
        map.put(
            BookPaginationSortOrder.ID_DESC.toString(),
            model -> new IdDescBookPaginationParameter(
                model.getProductId()
            )
        );
        map.put(
            BookPaginationSortOrder.RELEASED_AT_DESC_ID_DESC.toString(),
            model -> new ReleasedAtDescIdDescBookPaginationParameter(
                clock.toLocalDateTime(model.getReleasedAt()),
                model.getProductId()
            )
        );
    }
}
