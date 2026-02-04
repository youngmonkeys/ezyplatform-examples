package org.youngmonkeys.bookstore.web.controller.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.converter.WebBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.bookstore.web.service.WebBookService;
import org.youngmonkeys.bookstore.web.service.WebBookStoreBookAuthorService;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ecommerce.web.service.WebProductBookService;
import org.youngmonkeys.ecommerce.web.service.WebProductDescriptionService;
import org.youngmonkeys.ecommerce.web.service.WebProductMediaService;
import org.youngmonkeys.ecommerce.web.service.WebProductPriceService;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.service.WebPostService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.PaginationModel;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;
import org.youngmonkeys.ezyplatform.web.service.WebUserService;

import java.util.*;
import java.util.stream.Collectors;


import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static com.tvd12.ezyfox.io.EzyMaps.newHashMap;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO_LONG;

@EzySingleton
@AllArgsConstructor
public class WebBookModelDecorator {

    private final WebMediaService mediaService;
    private final WebPostService postService;
    private final WebProductBookService productBookService;
    private final WebProductMediaService productMediaService;
    private final WebProductDescriptionService productDescriptionService;
    private final WebProductPriceService productPriceService;
    private final WebUserService userService;
    private final WebBookStoreModelToResponseConverter modelToResponseConverter;
    private final WebBookService bookService;
    private final WebBookStoreBookAuthorService webBookStoreBookAuthorService;

    @SuppressWarnings("MethodLength")
    public List<WebBookResponse> decorateToBookResponses(
        List<ProductModel> models,
        ProductCurrencyModel currency
    ) {
        List<Long> productIds = newArrayList(
            models,
            ProductModel::getId
        );
        Map<Long, ProductBookModel> bookById = productBookService
            .getProductBookMapByIds(productIds);
        Set<Long> mediaIds = models
            .stream()
            .map(ProductModel::getBannerImageId)
            .filter(it -> it > 0)
            .collect(Collectors.toSet());
        Map<Long, Long> descriptionPostIdByProductId = productDescriptionService
            .getProductDescriptionPostIdMapByIds(
                productIds
            );
        Map<Long, List<UserModel>> authorUsersByBookId = webBookStoreBookAuthorService.getAuthorUsersMapByBooks(bookById.values());
        Set<Long> authorAvatarIds = authorUsersByBookId.values().stream()
            .flatMap(List::stream)
            .map(UserModel::getAvatarImageId)
            .filter(id -> id > ZERO_LONG)
            .collect(Collectors.toSet());
        return Reactive.multiple()
            .register("avatarById", () ->
                mediaService.getMediaNameMapByIds(authorAvatarIds)
            )
            .register("mediaById", () ->
                mediaService.getMediaNameMapByIds(mediaIds)
            )
            .register("descriptionById", () ->
                postService.getPostMapByIds(
                    descriptionPostIdByProductId.values()
                )
            )
            .register("priceByProductId", () ->
                productPriceService.getProductPriceMap(
                    productIds,
                    currency.getId()
                )
            )
            .blockingGet(map -> {
                Map<Long, MediaNameModel> avatarById = map.get("avatarById");
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                Map<Long, PostModel> descriptionById = map.get("descriptionById");
                Map<Long, ProductPriceModel> priceByProductId = map.get("priceByProductId");
                return newArrayList(models, it -> {
                    List<UserModel> authorUsers = authorUsersByBookId
                        .getOrDefault(it.getId(), Collections.emptyList());

                    return modelToResponseConverter.toBookResponse(
                        it,
                        bookById.getOrDefault(
                            it.getId(),
                            ProductBookModel.builder().build()
                        ),
                        authorUsers,
                        avatarById,
                        mediaById.get(it.getBannerImageId()),
                        descriptionById.getOrDefault(
                            descriptionPostIdByProductId.getOrDefault(
                                it.getId(),
                                ZERO_LONG
                            ),
                            PostModel.builder().build()
                        ),
                        priceByProductId.getOrDefault(
                            it.getId(),
                            ProductPriceModel.ZERO
                        ),
                        currency
                    );
                });
            });
    }

    public WebBookDetailsResponse decorateToBookDetailsResponse(
        ProductModel model,
        ProductCurrencyModel currency
    ) {
        long productId = model.getId();
        long bannerId = model.getBannerImageId();
        ProductBookModel bookById = productBookService.getProductBookById(productId);
        List<Long> mediaIds = new ArrayList<>();
        if (bannerId > ZERO_LONG) {
            mediaIds.add(bannerId);
        }
        mediaIds.addAll(productMediaService.getMediaIdsByProductId(productId));
        long descriptionPostId = productDescriptionService
            .getProductDescriptionPostIdById(productId);
        List<UserModel> authorUsers = webBookStoreBookAuthorService.getAuthorUsersByBook(bookById);
        Set<Long> authorAvatarIds = authorUsers.stream()
            .map(UserModel::getAvatarImageId)
            .filter(id -> id > ZERO_LONG)
            .collect(Collectors.toSet());
        return Reactive.multiple()
            .register("avatarById", () ->
                mediaService.getMediaNameMapByIds(authorAvatarIds)
            )
            .register("mediaById", () ->
                mediaService.getMediaNameListByIds(mediaIds)
            )
            .register("description", () ->
                postService.getPostById(descriptionPostId)
            )
            .register("productPrice", () ->
                productPriceService.getProductPrice(
                    productId,
                    currency.getId()
                )
            )
            .blockingGet(map ->
                modelToResponseConverter.toBookDetailsResponse(
                    model,
                    map.get(
                        "description",
                        PostModel.builder().build()
                    ),
                    map.get(
                        "productPrice",
                        ProductPriceModel.ZERO
                    ),
                    currency,
                    map.get(
                        "mediaById",
                        Collections.emptyList()
                    ),
                    authorUsers,
                    map.get("avatarById")
                )
            );
    }

    public Map<Long, WebBookResponse> decorateToBookResponseByIdMap(
        List<ProductModel> models,
        ProductCurrencyModel currency
    ) {
        return newHashMap(
            decorateToBookResponses(models, currency),
            WebBookResponse::getId
        );
    }

    public PaginationModel<WebBookResponse> decorateToBookPaginationResponse(
        PaginationModel<ProductModel> pagination,
        ProductCurrencyModel currency
    ) {
        Map<Long, WebBookResponse> productResponseById =
            decorateToBookResponseByIdMap(
                pagination.getItems(),
                currency
            );
        return pagination.map(
            it -> productResponseById.get(it.getId())
        );
    }
}
