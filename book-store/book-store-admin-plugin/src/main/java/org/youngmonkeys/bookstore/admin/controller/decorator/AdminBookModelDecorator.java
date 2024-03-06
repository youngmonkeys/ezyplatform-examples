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

package org.youngmonkeys.bookstore.admin.controller.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.converter.AdminBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.ecommerce.admin.converter.AdminEcommerceModelToResponseConverter;
import org.youngmonkeys.ecommerce.admin.service.AdminProductPriceService;
import org.youngmonkeys.ecommerce.admin.service.AdminProductService;
import org.youngmonkeys.ecommerce.admin.service.AdminShopService;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ecommerce.response.ProductResponse;
import org.youngmonkeys.ezyplatform.admin.service.AdminMediaService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminBookModelDecorator {

    private final AdminMediaService mediaService;
    private final AdminShopService shopService;
    private final AdminProductService productService;
    private final AdminProductPriceService productPriceService;
    private final AdminEcommerceModelToResponseConverter modelToResponseConverter;
    private final AdminBookStoreModelToResponseConverter bookStoreModelToResponseConverter;

    public ProductResponse decorate(
        ProductModel model,
        long currencyId,
        String currencyFormat
    ) {
        Set<Long> mediaIds = new HashSet<>();
        long iconImageId = model.getIconImageId();
        if (iconImageId > 0) {
            mediaIds.add(iconImageId);
        }
        long bannerImageId = model.getBannerImageId();
        if (bannerImageId > 0) {
            mediaIds.add(bannerImageId);
        }
        return Reactive.multiple()
            .register(
                "mediaMap",
                () -> mediaService.getMediaNameMapByIds(mediaIds)
            )
            .register(
                "price",
                () -> productPriceService.getProductPrice(
                    model.getId(),
                    currencyId
                )
            )
            .register(
                "shop",
                () -> shopService.getShopNameById(model.getShopId())
            )
            .mapBegin(map -> {
                Map<Long, MediaNameModel> mediaMap = map.get("mediaMap");
                ProductPriceModel price = map.get(
                    "price",
                    ProductPriceModel.ZERO
                );
                return modelToResponseConverter.toResponse(
                    model,
                    map.get("shop"),
                    mediaMap.get(iconImageId),
                    mediaMap.get(bannerImageId),
                    price,
                    currencyFormat
                );
            })
            .blockingGet();
    }

    @SuppressWarnings("MethodLength")
    public PaginationModel<AdminBookResponse> decorate(
        PaginationModel<ProductBookModel> pagination,
        long currencyId,
        String currencyFormat
    ) {
        List<ProductBookModel> books = pagination.getItems();
        List<Long> productIds = newArrayList(
            books,
            ProductBookModel::getProductId
        );
        Map<Long, ProductModel> productById = productService
            .getProductMapByIds(productIds);
        Set<Long> mediaIds = productById
            .values()
            .stream()
            .map(ProductModel::getIconImageId)
            .filter(it -> it > 0)
            .collect(Collectors.toSet());
        return Reactive.multiple()
            .register(
                "mediaMap",
                () -> mediaIds.isEmpty()
                    ? Collections.emptyMap()
                    : mediaService.getMediaNameMapByIds(mediaIds)
            )
            .register(
                "productPriceMap",
                () -> productPriceService
                    .getProductPriceMap(
                        productIds,
                        currencyId
                    )
            )
            .mapBegin(map -> {
                Map<Long, MediaNameModel> mediaMap = map.get("mediaMap");
                Map<Long, ProductPriceModel> productPriceMap = map.get("productPriceMap");
                return pagination.map(it -> {
                    long productId = it.getProductId();
                    ProductModel product = productById.getOrDefault(
                        productId,
                        ProductModel.builder().build()
                    );
                    ProductPriceModel price = productPriceMap.getOrDefault(
                        productId,
                        ProductPriceModel.ZERO
                    );
                    return bookStoreModelToResponseConverter.toResponse(
                        it,
                        product,
                        mediaMap.get(product.getId()),
                        price,
                        currencyFormat
                    );
                });
            })
            .blockingGet();
    }
}
