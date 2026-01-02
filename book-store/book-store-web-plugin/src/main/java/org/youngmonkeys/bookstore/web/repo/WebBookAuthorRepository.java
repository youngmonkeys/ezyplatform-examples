package org.youngmonkeys.bookstore.web.repo;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;
import org.youngmonkeys.ecommerce.entity.ProductMeta;
import org.youngmonkeys.ezyplatform.result.IdResult;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static org.youngmonkeys.ecommerce.constant.EcommerceConstants.META_KEY_BOOK_CO_AUTHOR_USER_ID;

@EzyRepository
public interface WebBookAuthorRepository
    extends EzyDatabaseRepository<Long, ProductMeta> {

    @EzyQuery(
        "SELECT DISTINCT e.metaNumberValue FROM ProductMeta e " +
            "WHERE e.productId = ?0 " +
            "AND e.metaKey = '" + META_KEY_BOOK_CO_AUTHOR_USER_ID + "'"
    )
    List<IdResult> findAuthorIdsByBookId(
        long bookId,
        Next next
    );

    @EzyQuery(
        "SELECT DISTINCT e.productId FROM ProductMeta e " +
            "WHERE e.productId != ?0 " +
            "AND e.metaKey = '" + META_KEY_BOOK_CO_AUTHOR_USER_ID + "' " +
            "AND e.metaNumberValue IN ?1"
    )
    List<IdResult> findBookIdsByBookIdExclusiveAndAuthorIdIn(
        long bookIdExclusive,
        Collection<BigInteger> authorIds,
        Next next
    );

    @EzyQuery(
        "SELECT COUNT(e) FROM ProductMeta e " +
            "WHERE e.productId != ?0 " +
            "AND e.metaKey = '" + META_KEY_BOOK_CO_AUTHOR_USER_ID + "' " +
            "AND e.metaNumberValue IN ?1"
    )
    long countBookIdsByBookIdExclusiveAndAuthorIdIn(
        long bookIdExclusive,
        Collection<BigInteger> authorIds
    );
}
