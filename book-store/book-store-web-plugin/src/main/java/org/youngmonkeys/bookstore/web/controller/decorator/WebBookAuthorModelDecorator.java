package org.youngmonkeys.bookstore.web.controller.decorator;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.converter.WebBookStoreModelToResponseConverter;
import org.youngmonkeys.bookstore.web.response.WebBookAuthorDetailResponse;
import org.youngmonkeys.ezycrm.model.CustomerModel;
import org.youngmonkeys.ezycrm.web.service.WebCustomerService;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.rx.Reactive;
import org.youngmonkeys.ezyplatform.web.service.WebMediaService;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO_LONG;

@EzySingleton
@AllArgsConstructor
public class WebBookAuthorModelDecorator {

    private final WebCustomerService customerService;
    private final WebMediaService mediaService;
    private final WebBookStoreModelToResponseConverter modelToResponseConverter;

    public WebBookAuthorDetailResponse decorateToBookAuthorDetailResponse(
        UserModel user
    ) {
        long avatarImageId = user.getAvatarImageId();
        long coverImageId = user.getCoverImageId();
        Set<Long> mediaIds = Stream.of(
            avatarImageId,
            coverImageId
        )
            .filter(it -> it > ZERO_LONG)
            .collect(Collectors.toSet());
        return Reactive.multiple()
            .register("customer", () ->
                customerService.getCustomerById(
                    user.getId()
                )
            )
            .register("mediaById", () ->
                mediaService.getMediaNameMapByIds(mediaIds)
            )
            .blockingGet(map -> {
                Map<Long, MediaNameModel> mediaById = map.get("mediaById");
                return modelToResponseConverter.toBookAuthorDetailResponse(
                    user,
                    map.get("customer", CustomerModel.EMPTY),
                    mediaById.get(avatarImageId),
                    mediaById.get(coverImageId)
                );
            });
    }
}
