package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

@Getter
@Builder
public class WebBookDetailsResponse {
    private long id;
    private String code;
    private String name;
    private String authorName;
    private MediaNameModel bannerImage;
    private String description;
}
