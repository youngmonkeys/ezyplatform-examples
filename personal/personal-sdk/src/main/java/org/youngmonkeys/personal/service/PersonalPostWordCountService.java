package org.youngmonkeys.personal.service;

import lombok.AllArgsConstructor;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;
import org.youngmonkeys.personal.repo.PersonalPostWordCountRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static org.youngmonkeys.personal.constant.PersonalConstants.READ_WORDS_PER_MINUTE;

@AllArgsConstructor
public class PersonalPostWordCountService {

    private final PersonalPostWordCountRepository postWordCountRepository;

    public Map<Long, Long> getReadTimeInMinutesByPostIds(
        Collection<Long> postIds
    ) {
        if (postIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return postWordCountRepository
            .findListByIds(postIds)
            .stream()
            .collect(
                Collectors.toMap(
                    PersonalPostWordCount::getPostId,
                    it -> Math.max(
                        1,
                        it.getWordCount() / READ_WORDS_PER_MINUTE
                    )
                )
            );
    }
}
