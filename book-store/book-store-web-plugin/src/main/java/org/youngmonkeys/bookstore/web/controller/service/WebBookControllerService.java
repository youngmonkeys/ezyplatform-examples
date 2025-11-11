package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.constant.BookStoreProductType;
import org.youngmonkeys.bookstore.web.controller.decorator.WebBookModelDecorator;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.entity.ProductStatus;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.web.service.WebProductService;
import org.youngmonkeys.ezyplatform.exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WebBookControllerService {

    private final WebProductService productService;
    private final WebBookModelDecorator bookModelDecorator;

    public List<WebBookResponse> getTopBooksByShopId(
        long shopId
    ) {
        List<ProductModel> models = productService
            .getProductsByShopIdAndProductTypeInAndStatusInSortByByDisplayOrderDescIdDesc(
                shopId,
                Collections.singletonList(BookStoreProductType.BOOK.toString()),
                Collections.singletonList(ProductStatus.PUBLISHED.toString()),
                0,
                1
            );
        return bookModelDecorator.decorateToBookResponse(models);
    }

    public WebBookDetailsResponse getBookDetailsById(
        long productId
    ) {
        ProductModel model = productService.getProductByIdAndStatus(
            productId,
            ProductStatus.PUBLISHED.toString()
        );
        if (model == null) {
            throw new ResourceNotFoundException("book");
        }
        return bookModelDecorator.decorateToBookResponse(model);
    }
}
