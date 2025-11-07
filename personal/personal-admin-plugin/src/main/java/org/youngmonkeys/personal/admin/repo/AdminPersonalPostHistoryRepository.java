package org.youngmonkeys.personal.admin.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import org.youngmonkeys.ezyarticle.sdk.entity.PostHistory;

import java.util.List;

@EzyRepository
public interface AdminPersonalPostHistoryRepository
    extends EzyDatabaseRepository<Long, PostHistory> {

    @EzyQuery(
        "SELECT e FROM PostHistory e " +
            "WHERE e.id > ?0 " +
            "ORDER BY e.id ASC"
    )
    List<PostHistory> findByIdGt(long idGt, Next next);
}
