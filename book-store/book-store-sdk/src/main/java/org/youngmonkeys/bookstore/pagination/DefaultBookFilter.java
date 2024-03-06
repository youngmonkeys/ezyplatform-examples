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

package org.youngmonkeys.bookstore.pagination;

import com.tvd12.ezydata.database.query.EzyQueryConditionBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

import static org.youngmonkeys.ecommerce.constant.EcommerceTableNames.TABLE_NAME_PRODUCT;

@Getter
@Builder
public class DefaultBookFilter implements BookFilter {
    public final String status;
    public final String likeKeyword;
    public final Collection<String> keywords;

    @Override
    public void decorateQueryStringBeforeWhere(
        StringBuilder queryString
    ) {
        queryString.append(" INNER JOIN Product a ON e.productId = a.id");
        if (keywords != null) {
            queryString.append(" INNER JOIN DataIndex k ON e.productId = k.dataId");
        }
    }

    @Override
    public String matchingCondition() {
        EzyQueryConditionBuilder answer = new EzyQueryConditionBuilder();
        if (status != null) {
            answer.append("e.status = :status");
        }
        if (keywords != null) {
            answer
                .and("k.dataType = '" + TABLE_NAME_PRODUCT + "'")
                .and("k.keyword IN :keywords");
        }
        if (likeKeyword != null) {
            String query = new EzyQueryConditionBuilder()
                .append("(")
                .append("a.productName LIKE CONCAT('%', :likeKeyword, '%')")
                .or("a.productCode LIKE CONCAT('%', :likeKeyword, '%')")
                .or("e.author LIKE CONCAT('%', :likeKeyword, '%')")
                .or("e.affiliate LIKE CONCAT('%', :likeKeyword, '%')")
                .or("e.distributionCompany LIKE CONCAT('%', :distributionCompany, '%')")
                .or("e.publisher LIKE CONCAT('%', :publisher, '%')")
                .append(")")
                .build();
            answer.or(query);
        }
        return answer.build();
    }
}
