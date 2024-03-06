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

package org.youngmonkeys.bookstore.admin.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

import java.math.BigDecimal;

import static org.youngmonkeys.ecommerce.util.DecimalPrices.formatPrice;

@EzySingleton
public class AdminBookStoreModelToResponseConverter {

    public AdminBookResponse toResponse(
        ProductBookModel model,
        ProductModel product,
        MediaNameModel iconImage,
        ProductPriceModel price,
        String currencyFormat
    ) {
        BigDecimal priceValue = price.getPrice();
        return AdminBookResponse.builder()
            .productId(model.getProductId())
            .bookName(product.getProductName())
            .bookCode(product.getProductCode())
            .author(model.getAuthor())
            .authorUserId(model.getAuthorUserId())
            .authorUrl(model.getAuthorUrl())
            .bookType(model.getBookType())
            .pages(model.getPages())
            .coverType(model.getCoverType())
            .affiliate(model.getAffiliate())
            .previewLink(model.getPreviewLink())
            .distributionCompany(model.getDistributionCompany())
            .publisher(model.getPublisher())
            .iconImage(iconImage)
            .price(priceValue)
            .formattedPrice(formatPrice(priceValue, currencyFormat))
            .status(product.getStatus())
            .createdAt(product.getCreatedAt())
            .releasedAt(model.getReleasedAt())
            .build();
    }
}
