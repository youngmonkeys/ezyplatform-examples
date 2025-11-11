package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.converter.WebBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.web.service.WebProductBookService;
import org.youngmonkeys.ecommerce.web.service.WebProductDescriptionService;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.service.WebPostService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO_LONG;

@EzySingleton
@AllArgsConstructor
public class WebBookModelDecorator {

    private final WebMediaService mediaService;
    private final WebPostService postService;
    private final WebProductBookService productBookService;
    private final WebProductDescriptionService productDescriptionService;
    private final WebBookStoreModelToResponseConverter
        modelToResponseConverter;

    public List<WebBookResponse> decorateToBookResponse(
        List<ProductModel> models
    ) {
        List<Long> productIds = newArrayList(
            models,
            ProductModel::getId
        );
        Set<Long> mediaIds = models
            .stream()
            .map(ProductModel::getBannerImageId)
            .filter(it -> it > 0)
            .collect(Collectors.toSet());
        Map<Long, Long> descriptionPostIdByProductId = productDescriptionService
            .getProductDescriptionPostIdMapByIds(
                productIds
            );
        return Reactive.multiple()
            .register("bookById", () ->
                productBookService.getProductBookMapByIds(productIds)
            )
            .register("mediaById", () ->
                mediaService.getMediaNameMapByIds(mediaIds)
            )
            .register("descriptionById", () ->
                postService.getPostMapByIds(
                    descriptionPostIdByProductId.values()
                )
            )
            .blockingGet(map -> {
                Map<Long, ProductBookModel> bookById = map.get("bookById");
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                Map<Long, PostModel> descriptionById = map.get("descriptionById");
                return newArrayList(models, it ->
                    modelToResponseConverter.toBookResponse(
                        it,
                        bookById.getOrDefault(
                            it.getId(),
                            ProductBookModel.builder().build()
                        ),
                        mediaById.get(it.getBannerImageId()),
                        descriptionById.getOrDefault(
                            descriptionPostIdByProductId.getOrDefault(
                                it.getId(),
                                ZERO_LONG
                            ),
                            PostModel.builder().build()
                        )
                    )
                );
            });
    }
}
