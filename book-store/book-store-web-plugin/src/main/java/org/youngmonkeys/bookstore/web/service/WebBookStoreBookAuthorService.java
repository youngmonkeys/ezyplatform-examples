package org.youngmonkeys.bookstore.web.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ecommerce.model.ProductBookModel;
import org.youngmonkeys.ecommerce.service.ProductBookService;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.web.service.WebUserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WebBookStoreBookAuthorService {

    private final WebBookService webBookService;
    private final WebUserService userService;

    public Map<Long, List<UserModel>> getAuthorUsersMapByBooks(Collection<ProductBookModel> books) {
        return books.stream().collect(Collectors.toMap(
            ProductBookModel::getProductId,
            this::getAuthorUsersByBook
        ));
    }
    public List<UserModel> getAuthorUsersByBook(ProductBookModel book) {
        if (book == null) return Collections.emptyList();

        Set<Long> authorIds = new LinkedHashSet<>();
        if (book.getAuthorUserId() > 0) {
            authorIds.add(book.getAuthorUserId());
        }
        authorIds.addAll(webBookService.getAuthorIdsByBookId(
            book.getProductId(), 0, book.getNumberOfAuthors()
        ));

        return userService.getUserListByIds(authorIds);
    }
}
