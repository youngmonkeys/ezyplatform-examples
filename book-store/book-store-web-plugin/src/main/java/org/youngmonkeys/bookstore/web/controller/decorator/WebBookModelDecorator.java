package org.youngmonkeys.bookstore.web.controller.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.converter.WebBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.web.service.WebProductBookService;
import org.youngmonkeys.ecommerce.web.service.WebProductDescriptionService;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.service.WebPostService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;
import org.youngmonkeys.ezyplatform.web.service.WebUserService;

import java.util.HashSet;
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
    private final WebUserService userService;
    private final WebBookStoreModelToResponseConverter
        modelToResponseConverter;

    public List<WebBookResponse> decorateToBookResponse(
        List<ProductModel> models
    ) {
        List<Long> productIds = newArrayList(
            models,
            ProductModel::getId
        );
        Map<Long, ProductBookModel> bookById = productBookService
            .getProductBookMapByIds(productIds);
        Set<Long> userIds = bookById
            .values()
            .stream()
            .map(ProductBookModel::getAuthorUserId)
            .filter(it -> it > 0)
            .collect(Collectors.toSet());
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
            .register("userById", () ->
                userService.getUserMapByIds(userIds)
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
                Map<Long, UserModel> userById = map.get("userById");
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                Map<Long, PostModel> descriptionById = map.get("descriptionById");
                return newArrayList(models, it -> {
                    ProductBookModel book = bookById.getOrDefault(
                        it.getId(),
                        ProductBookModel.builder().build()
                    );
                    return modelToResponseConverter.toBookResponse(
                        it,
                        book,
                        userById.getOrDefault(
                            book.getAuthorUserId(),
                            UserModel.builder().build()
                        ),
                        mediaById.get(it.getBannerImageId()),
                        descriptionById.getOrDefault(
                            descriptionPostIdByProductId.getOrDefault(
                                it.getId(),
                                ZERO_LONG
                            ),
                            PostModel.builder().build()
                        )
                    );
                });
            });
    }

    public WebBookDetailsResponse decorateToBookResponse(
        ProductModel model
    ) {
        long productId = model.getId();
        long bannerId = model.getBannerImageId();
        Set<Long> mediaIds = new HashSet<>();
        if (bannerId > ZERO_LONG) {
            mediaIds.add(bannerId);
        }
        long descriptionPostId = productDescriptionService
            .getProductDescriptionPostIdById(productId);
        return Reactive.multiple()
            .register("book", () ->
                productBookService.getProductBookById(productId)
            )
            .register("mediaById", () ->
                mediaService.getMediaNameMapByIds(mediaIds)
            )
            .register("description", () ->
                postService.getPostById(
                    descriptionPostId
                )
            )
            .blockingGet(map -> {
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                return modelToResponseConverter.toBookDetailsResponse(
                    model,
                         map.get(
                        "bookById",
                        ProductBookModel.builder().build()
                    ),
                    mediaById.get(bannerId),
                        map.get(
                        "description",
                        PostModel.builder().build()
                    )
                );
            });
    }
}
