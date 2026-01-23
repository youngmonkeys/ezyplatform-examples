package org.youngmonkeys.personal.result;

import com.tvd12.ezyfox.database.annotation.EzyQueryResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzyQueryResult
public class PostIdAndNumberViewsResult {
    private long id;
    private long views;
}
