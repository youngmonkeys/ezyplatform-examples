package org.youngmonkeys.personal.web.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import org.youngmonkeys.ezyarticle.sdk.entity.PostMeta;
import org.youngmonkeys.personal.result.PostIdAndNumberViewsResult;

import java.util.List;

@EzyRepository
public interface WebPersonalBlogTopViewRepository extends EzyDatabaseRepository<Long, PostMeta> {

    @EzyQuery(
        "SELECT p.id, pm.metaNumberValue AS views " +
        "FROM Post p " +
        "JOIN PostMeta pm ON p.id = pm.postId " +
        "WHERE p.postType = ?1 " +
            "AND p.status = ?2 " +
            "AND pm.metaKey = ?0 " +
        "ORDER BY pm.metaNumberValue DESC"
    )
    List<PostIdAndNumberViewsResult> findTopPostByMetaKeyAndTypeAndStatusOrderByViews(
        String metaKey, String postType, String status, Next next);
}
