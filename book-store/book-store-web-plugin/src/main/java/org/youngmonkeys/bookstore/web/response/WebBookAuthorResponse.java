package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;
import org.youngmonkeys.ezyplatform.model.MediaNameModel;

@Getter
@Builder
public class WebBookAuthorResponse {
    private String displayName;
    private String uuid;
    private MediaNameModel avatarImage;
}
