package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.web.service.WebUserService;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO;
import static org.youngmonkeys.ezyplatform.constant.CommonConstants.ZERO_LONG;

@Service
@AllArgsConstructor
public class WebBookStoreBookAuthorService {

    private final WebBookService webBookService;
    private final WebUserService userService;

    public Map<Long, List<UserModel>> getAuthorUsersMapByBooks(
        Collection<ProductBookModel> books
    ) {
        return books
            .stream()
            .collect(
                Collectors
                    .toMap(
                    ProductBookModel::getProductId,
                    this::getAuthorUsersByBook
                )
            );
    }

    public List<UserModel> getAuthorUsersByBook(
        ProductBookModel book
    ) {
        if (book == null) {
            return Collections.emptyList();
        }
        Set<Long> authorIds = new LinkedHashSet<>();
        long authorUserId = book.getAuthorUserId();
        if (authorUserId > ZERO_LONG) {
            authorIds.add(authorUserId);
        }
        authorIds.addAll(
            webBookService.getAuthorIdsByBookId(
                book.getProductId(),
                ZERO,
                book.getNumberOfAuthors()
            )
        );
        return userService.getUserListByIds(authorIds);
    }
}
