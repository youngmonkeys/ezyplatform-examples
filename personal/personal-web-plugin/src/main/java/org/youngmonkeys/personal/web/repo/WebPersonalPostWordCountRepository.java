package org.youngmonkeys.personal.web.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;
import org.youngmonkeys.personal.repo.PersonalPostWordCountRepository;

@EzyRepository
public interface WebPersonalPostWordCountRepository extends
    PersonalPostWordCountRepository,
    EzyDatabaseRepository<Long, PersonalPostWordCount> {}
