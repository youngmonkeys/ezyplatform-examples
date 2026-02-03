package org.youngmonkeys.personal.admin.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.personal.entity.PersonalCoinPrice;
import org.youngmonkeys.personal.repo.PersonalCoinPriceRepository;

@EzyRepository
public interface AdminPersonalCoinPriceRepository extends
    PersonalCoinPriceRepository, EzyDatabaseRepository<Long, PersonalCoinPrice> {}
