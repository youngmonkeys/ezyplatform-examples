package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductType;
import org.youngmonkeys.bookstore.web.controller.decorator.WebBookModelDecorator;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ecommerce.pagination.*;
import org.youngmonkeys.ecommerce.service.PaginationProductPriceService;
import org.youngmonkeys.ecommerce.service.PaginationProductService;
import org.youngmonkeys.ecommerce.web.service.WebProductService;
import org.youngmonkeys.ezyplatform.exception.ResourceNotFoundException;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static org.youngmonkeys.ezyplatform.pagination.PaginationModelFetchers.getPaginationModelBySortOrder;

@Service
@AllArgsConstructor
public class WebBookControllerService {

    private final WebProductService productService;
    private final PaginationProductService paginationProductService;
    private final PaginationProductPriceService paginationProductPriceService;
    private final WebBookModelDecorator bookModelDecorator;
    private final ProductPaginationParameterConverter
        productPaginationParameterConverter;
    private final ProductPricePaginationParameterConverter
        productPricePaginationParameterConverter;

    public List<WebBookResponse> getTopBooksByShopId(
        long shopId,
        ProductCurrencyModel currency
    ) {
        List<ProductModel> models = productService
            .getProductsByShopIdAndProductTypeInAndStatusInSortByByDisplayOrderDescIdDesc(
                shopId,
                Collections.singletonList(BookStoreProductType.BOOK.toString()),
                Collections.singletonList(ProductStatus.PUBLISHED.toString()),
                0,
                1
            );
        return bookModelDecorator.decorateToBookResponses(
            models,
            currency
        );
    }

    public WebBookDetailsResponse getBookDetailsById(
        long productId,
        ProductCurrencyModel currency
    ) {
        ProductModel model = productService.getProductByIdAndStatus(
            productId,
            ProductStatus.PUBLISHED.toString()
        );
        if (model == null) {
            throw new ResourceNotFoundException("book");
        }
        return bookModelDecorator.decorateToBookDetailsResponse(
            model,
            currency
        );
    }

    public PaginationModel<WebBookResponse> getBookPagination(
        ProductFilter productFilter,
        ProductPriceFilter productPriceFilter,
        String sortOrder,
        String nextPageToken,
        String prevPageToken,
        boolean lastPage,
        int limit,
        ProductCurrencyModel currency
    ) {
        if (ProductPricePaginationSortOrder.containsValue(sortOrder)) {
            return getBookPagination(
                productPriceFilter,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit,
                currency
            );
        }
        return getBookPagination(
            productFilter,
            sortOrder,
            nextPageToken,
            prevPageToken,
            lastPage,
            limit,
            currency
        );
    }

    public PaginationModel<WebBookResponse> getBookPagination(
        ProductFilter filter,
        String sortOrder,
        String nextPageToken,
        String prevPageToken,
        boolean lastPage,
        int limit,
        ProductCurrencyModel currency
    ) {
        PaginationModel<ProductModel> pagination =
            getPaginationModelBySortOrder(
                paginationProductService,
                productPaginationParameterConverter,
                filter,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit
            );
        return bookModelDecorator.decorateToBookPaginationResponse(
            pagination,
            currency
        );
    }

    public PaginationModel<WebBookResponse> getBookPagination(
        ProductPriceFilter filter,
        String sortOrder,
        String nextPageToken,
        String prevPageToken,
        boolean lastPage,
        int limit,
        ProductCurrencyModel currency
    ) {
        PaginationModel<ProductPriceModel> pagination =
            getPaginationModelBySortOrder(
                paginationProductPriceService,
                productPricePaginationParameterConverter,
                filter,
                sortOrder,
                nextPageToken,
                prevPageToken,
                lastPage,
                limit
            );
        List<Long> productIds = newArrayList(
            pagination.getItems(),
            ProductPriceModel::getProductId
        );
        List<ProductModel> products = productService.getProductsByIds(
            productIds
        );
        Map<Long, WebBookResponse> productItemById =
            bookModelDecorator.decorateToBookResponseByIdMap(
                products,
                currency
            );
        return pagination.map(it ->
            productItemById.get(it.getProductId())
        );
    }
}
