package org.youngmonkeys.personal.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.youngmonkeys.personal.entity.PersonalCoinPrice;
import org.youngmonkeys.personal.result.CoinPriceApiResult;

import java.util.Collection;

@EzyRepository
public interface PersonalCoinPriceRepository
    extends EzyDatabaseRepository<Long, PersonalCoinPrice> {

    @EzyQuery(
        "SELECT p.symbol, p.name, p.price, p.priceChange " +
        "FROM PersonalCoinPrice p " +
        "WHERE p.symbol IN ?0 " +
            "AND p.updatedAt = (" +
                "SELECT MAX(p2.updatedAt) " +
                "FROM PersonalCoinPrice p2 " +
                "WHERE p2.symbol = p.symbol" +
            ")"
    )
    Collection<CoinPriceApiResult> findLatestBySymbols(Collection<String> symbols);
}
