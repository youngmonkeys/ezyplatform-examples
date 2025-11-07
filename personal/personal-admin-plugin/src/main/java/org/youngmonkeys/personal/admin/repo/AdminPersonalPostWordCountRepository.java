package org.youngmonkeys.personal.admin.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;
import org.youngmonkeys.personal.repo.PersonalPostWordCountRepository;

@EzyRepository
public interface AdminPersonalPostWordCountRepository extends
    PersonalPostWordCountRepository,
    EzyDatabaseRepository<Long, PersonalPostWordCount> {}
