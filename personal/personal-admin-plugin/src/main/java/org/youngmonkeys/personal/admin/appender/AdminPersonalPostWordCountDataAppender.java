package org.youngmonkeys.personal.admin.appender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.Next;
import org.youngmonkeys.ezyarticle.sdk.entity.PostHistory;
import org.youngmonkeys.ezyplatform.admin.appender.AdminDataAppender;
import org.youngmonkeys.ezyplatform.admin.service.AdminSettingService;
import org.youngmonkeys.ezyplatform.time.ClockProxy;
import org.youngmonkeys.personal.admin.repo.AdminPersonalPostHistoryRepository;
import org.youngmonkeys.personal.admin.repo.AdminPersonalPostWordCountRepository;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;

import java.util.Arrays;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.last;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.LIMIT_100_RECORDS;

@EzySingleton
public class AdminPersonalPostWordCountDataAppender
    extends AdminDataAppender<PostHistory, PersonalPostWordCount, Long> {

    private final ClockProxy clock;
    private final AdminPersonalPostHistoryRepository postHistoryRepository;
    private final AdminPersonalPostWordCountRepository postWordCountRepository;

    public AdminPersonalPostWordCountDataAppender(
        ClockProxy clock,
        ObjectMapper objectMapper,
        AdminSettingService settingService,
        AdminPersonalPostHistoryRepository postHistoryRepository,
        AdminPersonalPostWordCountRepository postWordCountRepository
    ) {
        super(objectMapper, settingService);
        this.clock = clock;
        this.postHistoryRepository = postHistoryRepository;
        this.postWordCountRepository = postWordCountRepository;
    }

    @Override
    protected boolean defaultStarted() {
        return true;
    }

    @Override
    protected void addDataRecord(PersonalPostWordCount dataRecord) {
        postWordCountRepository.save(dataRecord);
    }

    @Override
    protected PersonalPostWordCount toDataRecord(
        PostHistory value
    ) {
        long postId = value.getParentId();
        String content = value.getContent();
        long wordCount = Arrays
            .stream(content.trim().split("\\s+"))
           .filter(s -> !s.isEmpty())
           .count();
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
    protected List<PostHistory> filterValueList(
        List<PostHistory> valueList
    ) {
        return valueList;
    }

    @Override
    protected List<PostHistory> getValueList(Long pageToken) {
        return postHistoryRepository.findByIdGt(
            pageToken,
            Next.limit(LIMIT_100_RECORDS)
        );
    }

    @Override
    protected Long extractNewLastPageToken(
        List<PostHistory> valueList,
        Long currentLastPageToken
    ) {
         return valueList.isEmpty()
            ? currentLastPageToken
            : last(valueList).getId();
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
