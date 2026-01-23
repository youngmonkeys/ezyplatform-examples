package org.youngmonkeys.personal.result;

import com.tvd12.ezyfox.database.annotation.EzyQueryResult;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EzyQueryResult
public class PostTitleAndFeaturedImageResult {
    private long id;
    private String title;
    private String slug;
    private long featuredImageId;
    private long views;
    private LocalDateTime publishedAt;
}
