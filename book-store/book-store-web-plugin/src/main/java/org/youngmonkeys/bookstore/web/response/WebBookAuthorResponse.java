package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WebBookAuthorResponse {
    private String displayName;
    private String uuid;
}
