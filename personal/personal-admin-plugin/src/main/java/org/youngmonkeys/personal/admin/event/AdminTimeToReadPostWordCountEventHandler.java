package org.youngmonkeys.personal.admin.event;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.admin.service.AdminPostMetaService;
import org.youngmonkeys.ezyplatform.event.AbstractEventHandler;

import java.math.BigDecimal;
import java.util.Map;

import static org.youngmonkeys.personal.constant.PersonalConstants.INTERNAL_EVENT_NAME_POST_WORD_COUNT;
import static org.youngmonkeys.personal.constant.PersonalConstants.META_KEY_TIME_TO_READ;

@EzySingleton
@AllArgsConstructor
public class AdminTimeToReadPostWordCountEventHandler
    extends AbstractEventHandler<Map<String, Object>, Void> {

    private final AdminPostMetaService postMetaService;

    private static final BigDecimal READ_TIME_PER_WORD =
        new BigDecimal("0.24");

    @Override
    protected void processEventData(Map<String, Object> data) {
        long postId = (long) data.get("postId");
        long wordCount = (long) data.get("wordCount");
        long readTime = BigDecimal.valueOf(wordCount)
            .multiply(READ_TIME_PER_WORD)
            .longValue();
        postMetaService.savePostMetaUniqueKey(
            postId,
            META_KEY_TIME_TO_READ,
            readTime
        );
    }

    @Override
    public String getEventName() {
        return INTERNAL_EVENT_NAME_POST_WORD_COUNT;
    }
}
