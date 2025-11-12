package org.youngmonkeys.bookstore.web.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.response.WebBookDetailsResponse;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyarticle.web.manager.WebPostDecoratorManager;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;
import org.youngmonkeys.ezyplatform.model.UserModel;

@EzySingleton
@AllArgsConstructor
public class WebBookStoreModelToResponseConverter {

    private final WebPostDecoratorManager postDecoratorManager;

    public WebBookResponse toBookResponse(
        ProductModel model,
        ProductBookModel book,
        UserModel author,
        MediaNameModel bannerImage,
        PostModel descriptionPost
    ) {
        return WebBookResponse.builder()
            .id(model.getId())
            .name(model.getProductName())
            .authorName(author.getDisplayName())
            .bannerImage(bannerImage)
            .shortedDescription(descriptionPost.getShortedContent())
            .publisher(book.getPublisher())
            .build();
    }

    public WebBookDetailsResponse toBookDetailsResponse(
        ProductModel model,
        ProductBookModel book,
        MediaNameModel bannerImage,
        PostModel descriptionPost
    ) {
        String description = postDecoratorManager
            .decorateContent(
                descriptionPost.getContent(),
                descriptionPost.getContentType()
            );
        return WebBookDetailsResponse.builder()
            .id(model.getId())
            .name(model.getProductName())
            .authorName(book.getAuthor())
            .bannerImage(bannerImage)
            .description(description)
            .build();
    }
}
