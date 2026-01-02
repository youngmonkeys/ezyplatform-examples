package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.repo.WebBookAuthorRepository;
import org.youngmonkeys.ezyplatform.result.IdResult;

import java.math.BigInteger;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.LIMIT_300_RECORDS;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO;
import static org.youngmonkeys.ezyplatform.util.Randoms.randomSkipLimit;

@Service
@AllArgsConstructor
public class WebBookService {

    private final WebBookAuthorRepository bookAuthorRepository;

    public List<Long> getAuthorIdsByBookId(
        long bookId,
        int limit
    ) {
        return getAuthorIdsByBookId(bookId, ZERO, limit);
    }

    public List<Long> getAuthorIdsByBookId(
        long bookId,
        int skip,
        int limit
    ) {
        return newArrayList(
            bookAuthorRepository.findAuthorIdsByBookId(
                bookId,
                Next.fromSkipLimit(skip, limit)
            ),
            IdResult::getId
        );
    }

    public List<Long> randomSameAuthorBookIdsByBookId(
        long bookId,
        int limit
    ) {
        List<BigInteger> authorIds = newArrayList(
            getAuthorIdsByBookId(
                bookId,
                LIMIT_300_RECORDS
            ),
            BigInteger::valueOf
        );
        Next next = randomSkipLimit(
            bookAuthorRepository.countBookIdsByBookIdExclusiveAndAuthorIdIn(
                bookId,
                authorIds
            ),
            limit
        );
        return newArrayList(
            bookAuthorRepository.findBookIdsByBookIdExclusiveAndAuthorIdIn(
                bookId,
                authorIds,
                next
            ),
            IdResult::getId
        );
    }
}
