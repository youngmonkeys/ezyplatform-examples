package org.youngmonkeys.personal.admin.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import org.youngmonkeys.ezyarticle.admin.appender.AdminAbstractPostHistoryAppender;
import org.youngmonkeys.ezyarticle.admin.repo.AdminPostHistoryRepository;
import org.youngmonkeys.ezyarticle.sdk.entity.PostHistory;
import org.youngmonkeys.ezyarticle.sdk.result.SimplePostHistoryResult;
import org.youngmonkeys.ezyplatform.admin.event.AdminEventHandlerManager;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.ezyplatform.time.ClockProxy;
import org.youngmonkeys.personal.admin.repo.AdminPersonalPostWordCountRepository;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;

import java.util.Arrays;

import static org.youngmonkeys.ezyarticle.sdk.util.Contents.removeHtmlFromContent;
import static org.youngmonkeys.ezyplatform.util.Strings.emptyIfNull;
import static org.youngmonkeys.personal.constant.PersonalConstants.INTERNAL_EVENT_NAME_POST_WORD_COUNT;

@EzySingleton
public class AdminPersonalPostWordCountDataAppender
    extends AdminAbstractPostHistoryAppender<PersonalPostWordCount> {

    private final ClockProxy clock;
    private final AdminEventHandlerManager eventHandlerManager;
    private final AdminPersonalPostWordCountRepository postWordCountRepository;

    public AdminPersonalPostWordCountDataAppender(
        ClockProxy clock,
        AdminEventHandlerManager eventHandlerManager,
        ObjectMapper objectMapper,
        AdminSettingService settingService,
        AdminPostHistoryRepository postHistoryRepository,
        AdminPersonalPostWordCountRepository postWordCountRepository
    ) {
        super(
            objectMapper,
            settingService,
            postHistoryRepository
        );
        this.clock = clock;
        this.eventHandlerManager = eventHandlerManager;
        this.postWordCountRepository = postWordCountRepository;
    }

    @Override
    protected boolean defaultStarted() {
        return true;
    }

    @Override
    protected void addDataRecord(PersonalPostWordCount dataRecord) {
        postWordCountRepository.save(dataRecord);
        eventHandlerManager.handleEvent(
            INTERNAL_EVENT_NAME_POST_WORD_COUNT,
            EzyMapBuilder.mapBuilder()
                .put("postId", dataRecord.getPostId())
                .put("wordCount", dataRecord.getWordCount())
                .toMap()
        );
    }

    @Override
    protected PersonalPostWordCount toDataRecord(
        SimplePostHistoryResult value
    ) {
        long historyId = value.getId();
        PostHistory postHistory = getPostHistoryById(historyId);
        String content = removeHtmlFromContent(
            emptyIfNull(postHistory.getContent())
        );
        long wordCount = Arrays
            .stream(content.trim().split("\\s+"))
            .filter(s -> !s.isEmpty())
            .count();
        long postId = value.getParentId();
        logger.info(
            "post history id: {}, post id: {}, word count: {}",
            value.getId(),
            postId,
            wordCount
        );
        PersonalPostWordCount entity = new PersonalPostWordCount();
        entity.setPostId(postId);
        entity.setWordCount(wordCount);
        entity.setUpdatedAt(clock.nowDateTime());
        return entity;
    }

    @Override
    protected Long defaultPageToken() {
        return 0L;
    }

    @Override
    protected Class<Long> pageTokenType() {
        return Long.class;
    }

    @Override
    protected String getAppenderNamePrefix() {
        return "personal_post_word_count";
    }
}
