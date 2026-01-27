package org.youngmonkeys.personal.web.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TopBlogResponse {
    private long id;
    private String title;
    private String slug;
    private String featuredImageUrl;
    private long views;
    private LocalDateTime publishedAt;
}
