package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.controller.decorator.WebBookModelDecorator;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.bookstore.web.service.WebBookService;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.*;
import org.youngmonkeys.ecommerce.pagination.*;
import org.youngmonkeys.ecommerce.service.PaginationProductPriceService;
import org.youngmonkeys.ecommerce.service.PaginationProductService;
import org.youngmonkeys.ecommerce.web.service.WebProductCategoryProductService;
import org.youngmonkeys.ecommerce.web.service.WebProductCategoryService;
import org.youngmonkeys.ecommerce.web.service.WebProductService;
import org.youngmonkeys.ezyplatform.exception.ResourceNotFoundException;
import org.youngmonkeys.ezyplatform.model.PaginationModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static org.youngmonkeys.bookstore.constant.BookStoreConstants.*;
import static org.youngmonkeys.ezyplatform.pagination.PaginationModelFetchers.getPaginationModelBySortOrder;

@Service
@AllArgsConstructor
public class WebBookControllerService {

    private final WebBookService bookService;
    private final WebProductService productService;
    private final WebProductCategoryService productCategoryService;
    private final WebProductCategoryProductService productCategoryProductService;
    private final PaginationProductService paginationProductService;
    private final PaginationProductPriceService paginationProductPriceService;
    private final WebBookModelDecorator bookModelDecorator;
    private final ProductPaginationParameterConverter
        productPaginationParameterConverter;
    private final ProductPricePaginationParameterConverter
        productPricePaginationParameterConverter;

    public List<WebBookResponse> getHighlightBooks(
        ProductCurrencyModel currency,
        int limit
    ) {
        return getBooksByCategoryName(
            CATEGORY_NAME_HIGHLIGHT_BOOK,
            currency,
            limit
        );
    }

    public List<WebBookResponse> getBestsellingBooks(
        ProductCurrencyModel currency,
        int limit
    ) {
        return getBooksByCategoryName(
            CATEGORY_NAME_BESTSELLING_BOOK,
            currency,
            limit
        );
    }

    public List<WebBookResponse> getNewBooks(
        ProductCurrencyModel currency,
        int limit
    ) {
        return getBooksByCategoryName(
            CATEGORY_NAME_NEW_BOOK,
            currency,
            limit
        );
    }

    public List<WebBookResponse> getBooksByCategoryName(
        String categoryName,
        ProductCurrencyModel currency,
        int limit
    ) {
        ProductCategoryModel category = productCategoryService
            .getCategoryByName(categoryName);
        if (category == null) {
            return Collections.emptyList();
        }
        List<Long> productIds = newArrayList(
            productCategoryProductService.getProductCategoryProductsByCategory(
                category.getId(),
                limit
            ),
            ProductCategoryProductModel::getProductId
        );
        return getBooksByIds(productIds, currency);
    }

    public List<WebBookResponse> getBooksByIds(
        Collection<Long> bookIds,
        ProductCurrencyModel currency
    ) {
        List<ProductModel> models = productService
            .getProductsByIds(bookIds);
        return bookModelDecorator.decorateToBookResponses(
            models,
            currency
        );
    }

    public List<WebBookResponse> randomSameAuthorBooksByBookId(
        long bookId,
        ProductCurrencyModel currency,
        int limit
    ) {
        List<Long> bookIds = bookService
            .randomSameAuthorBookIdsByBookId(
                bookId,
                limit
            );
        return getBooksByIds(bookIds, currency);
    }

    public WebBookDetailsResponse getBookDetailsByCode(
        String productCode,
        ProductCurrencyModel currency
    ) {
        ProductModel model = productService.getProductByCodeAndStatus(
            productCode,
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
