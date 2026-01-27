package org.youngmonkeys.bookstore.web.controller.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.converter.WebBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.bookstore.web.service.WebBookService;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductCurrencyModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ecommerce.model.ProductPriceModel;
import org.youngmonkeys.ecommerce.web.service.*;
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
        Map<Long, List<Long>> authorUserIdsByProductId = bookById
            .values()
            .stream()
            .collect(Collectors.toMap(
                ProductBookModel::getProductId,
                it -> {
                    Set<Long> ids = new LinkedHashSet<>();
                    if (it.getAuthorUserId() > 0) ids.add(it.getAuthorUserId());
                    ids.addAll(bookService.getAuthorIdsByBookId(it.getProductId(), 0, it.getNumberOfAuthors()));
                    return new ArrayList<>(ids);
                }
            ));
        Set<Long> authorUserIds = authorUserIdsByProductId
            .values()
            .stream()
            .flatMap(List::stream)
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
                userService.getUserMapByIds(authorUserIds)
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
                Map<Long, UserModel> userById = map.get("userById");
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                Map<Long, PostModel> descriptionById = map.get("descriptionById");
                Map<Long, ProductPriceModel> priceByProductId = map
                    .get("priceByProductId");
                return newArrayList(models, it -> {
                    ProductBookModel book = bookById.getOrDefault(
                        it.getId(),
                        ProductBookModel.builder().build()
                    );
                    List<UserModel> authorUsers = authorUserIdsByProductId
                        .getOrDefault(it.getId(), Collections.emptyList())
                        .stream()
                        .map(userById::get)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                    return modelToResponseConverter.toBookResponse(
                        it,
                        book,
                        authorUsers,
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
        Set<Long> authorUserIds = new LinkedHashSet<>();
        if (bookById.getAuthorUserId() > 0) authorUserIds.add(bookById.getAuthorUserId());
        authorUserIds.addAll(bookService.getAuthorIdsByBookId(bookById.getProductId(), 0, bookById.getNumberOfAuthors()));
        long descriptionPostId = productDescriptionService
            .getProductDescriptionPostIdById(productId);
        return Reactive.multiple()
            .register("book", () ->
                productBookService.getProductBookById(productId)
            )
            .register("userById", () ->
                userService.getUserMapByIds(authorUserIds)
            )
            .register("mediaById", () ->
                mediaService.getMediaNameMapByIds(mediaIds)
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
            .blockingGet(map -> {
                Map<Long, UserModel> userById = map.get("userById");
                List<UserModel> authorUsers = newArrayList(
                    userById,
                    (id, user) -> user
                );
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                List<MediaNameModel> medias = newArrayList(
                    mediaById,
                    (id, media) -> media
                );
                return modelToResponseConverter.toBookDetailsResponse(
                    model,
                    map.get(
                        "bookById",
                        ProductBookModel.builder().build()
                    ),
                    map.get(
                        "description",
                        PostModel.builder().build()
                    ),
                    map.get(
                        "productPrice",
                        ProductPriceModel.ZERO
                    ),
                    currency,
                    medias,
                    authorUsers
                );
            });
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
