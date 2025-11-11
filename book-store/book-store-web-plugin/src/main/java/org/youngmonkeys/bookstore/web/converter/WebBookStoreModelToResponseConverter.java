package org.youngmonkeys.bookstore.web.converter;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.bookstore.web.response.WebBookResponse;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.model.ProductModel;
import org.youngmonkeys.ezyarticle.sdk.model.PostModel;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

@EzySingleton
public class WebBookStoreModelToResponseConverter {

    public WebBookResponse toBookResponse(
        ProductModel model,
        ProductBookModel book,
        MediaNameModel bannerImage,
        PostModel description
    ) {
        return WebBookResponse.builder()
            .id(model.getId())
            .name(model.getProductName())
            .authorName(book.getAuthor())
            .bannerImage(bannerImage)
            .shortedDescription(description.getShortedContent())
            .build();
    }
}
