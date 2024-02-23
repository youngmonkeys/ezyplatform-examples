package org.youngmonkeys.bookstore.admin.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import com.tvd12.ezyhttp.server.core.view.ViewDecorator;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.admin.service.AdminBookAuthorService;
import org.youngmonkeys.ecommerce.admin.service.AdminProductBookService;
import org.youngmonkeys.ezyplatform.rx.Reactive;

import javax.servlet.http.HttpServletRequest;

import static org.youngmonkeys.ezyplatform.constant.CommonConstants.VIEW_VARIABLE_MENUITEM_BADGES;

@EzySingleton
@AllArgsConstructor
public class AdminBookStoreViewDecorator implements ViewDecorator {

    private final AdminBookAuthorService bookAuthorService;
    private final AdminProductBookService productBookService;


    @Override
    public void decorate(HttpServletRequest request, View view) {
        Reactive.multiple()
            .register(
                "book_store.books",
                productBookService::countAllBooks
            )
            .register(
                "book_store.authors",
                bookAuthorService::countAllAuthors
            )
            .blockingConsume(it ->
                view.putKeyValuesToVariable(
                    VIEW_VARIABLE_MENUITEM_BADGES,
                    it.valueMap()
                )
            );
    }
}
