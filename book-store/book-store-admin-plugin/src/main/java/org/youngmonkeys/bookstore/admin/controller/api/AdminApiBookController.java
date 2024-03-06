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
import com.tvd12.ezyfox.io.EzyCollections;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.controller.service.AdminBookControllerService;
import org.youngmonkeys.bookstore.admin.response.AdminBookResponse;
import org.youngmonkeys.bookstore.pagination.DefaultBookFilter;
import org.youngmonkeys.ecommerce.admin.controller.service.AdminProductControllerService;
import org.youngmonkeys.ecommerce.admin.converter.AdminEcommerceRequestToModelConverter;
import org.youngmonkeys.ecommerce.admin.service.*;
import org.youngmonkeys.ecommerce.admin.validator.AdminProductValidator;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ecommerce.request.SaveProductRequest;
import org.youngmonkeys.ezyplatform.admin.validator.AdminCommonValidator;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.response.AddedIdResponse;

import java.math.BigInteger;
import java.util.List;

import static org.youngmonkeys.ezyplatform.util.Keywords.toKeywords;

@Api
@Authenticated
@Controller("/api/v1")
@EzyFeature("book_management")
@AllArgsConstructor
public class AdminApiBookController {

    private final AdminDeliverableProductService deliverableProductService;
    private final AdminProductService productService;
    private final AdminProductPriceService productPriceService;
    private final AdminProductCurrencyService productCurrencyService;
    private final AdminShopWarehouseProductService warehouseProductService;
    private final AdminProductControllerService productControllerService;
    private final AdminBookControllerService bookControllerService;
    private final AdminProductValidator productValidator;
    private final AdminCommonValidator commonValidator;
    private final AdminEcommerceRequestToModelConverter requestToModelConverter;

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

    @DoGet("/books/suggest")
    public List<ProductModel> booksSuggestGet(
        @RequestParam(value = "keyword") String keyword,
        @RequestParam(value = "limit", defaultValue = "12") int limit
    ) {
        commonValidator.validatePageSize(limit);
        return productService.getProductsByKeywords(
            toKeywords(keyword),
            limit
        );
    }
    
    @DoPost("/books/add")
    public AddedIdResponse booksAddPost(
        @RequestBody SaveProductRequest request
    ) {
        productValidator.validate(request);
        long productId = productService.addProduct(
            requestToModelConverter.toModel(request)
        );
        deliverableProductService.saveDeliverableProduct(
            requestToModelConverter.toModel(
                productId,
                request
            )
        );
        List<Long> warehouseIds = request.getWarehouseIds();
        if (!EzyCollections.isEmpty(warehouseIds)) {
            warehouseProductService.saveWarehousesProductIfNotExists(
                request.getWarehouseIds(),
                productId,
                request.getAmount().divide(
                    BigInteger.valueOf(warehouseIds.size())
                )
            );
        }
        return new AddedIdResponse(productId);
    }

    @DoPut("/books/{id}")
    public ResponseEntity booksIdPut(
        @PathVariable long productId,
        @RequestBody SaveProductRequest request
    ) {
        productValidator.validate(productId, request);
        productService.updateProduct(
            productId,
            requestToModelConverter.toModel(request)
        );
        deliverableProductService.saveDeliverableProduct(
            requestToModelConverter.toModel(
                productId,
                request
            )
        );
        List<Long> warehouseIds = request.getWarehouseIds();
        if (!EzyCollections.isEmpty(warehouseIds)) {
            warehouseProductService.saveWarehousesProductIfNotExists(
                request.getWarehouseIds(),
                productId,
                request.getAmount().divide(
                    BigInteger.valueOf(warehouseIds.size())
                )
            );
        }
        return ResponseEntity.noContent();
    }

    @DoGet("/books/{id}/price")
    public ProductPriceModel booksIdPriceGet(
        @PathVariable long productId,
        @RequestParam(value = "currencyId") long currencyId
    ) {
        ProductPriceModel productPrice = productPriceService.getProductPrice(
            productId,
            currencyId
        );
        return productPrice != null
            ? productPrice
            : ProductPriceModel.builder().build();
    }

    @DoDelete("/books/{id}")
    public ResponseEntity booksIdDelete(
        @PathVariable long productId
    ) {
        productService.deleteProductById(productId);
        deliverableProductService.deleteDeliverableProductByProductId(
            productId
        );
        return ResponseEntity.noContent();
    }
}
