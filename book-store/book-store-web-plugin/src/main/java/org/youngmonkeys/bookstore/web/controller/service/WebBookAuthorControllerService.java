package org.youngmonkeys.bookstore.web.controller.service;

import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import org.youngmonkeys.bookstore.web.controller.decorator.WebBookAuthorModelDecorator;
import org.youngmonkeys.bookstore.web.response.WebBookAuthorDetailResponse;
import org.youngmonkeys.ezyplatform.exception.ResourceNotFoundException;
import org.youngmonkeys.ezyplatform.model.UserModel;
import org.youngmonkeys.ezyplatform.web.service.WebUserService;

@Service
@AllArgsConstructor
public class WebBookAuthorControllerService {

    private final WebUserService userService;
    private final WebBookAuthorModelDecorator bookAuthorModelDecorator;

    public WebBookAuthorDetailResponse getAuthorDetailsByUuid(
        String uuid
    ) {
        UserModel user = userService.getUserByUuid(uuid);
        if (user == null) {
            throw new ResourceNotFoundException("author");
        }
        return bookAuthorModelDecorator.decorateToBookAuthorDetailResponse(
            user
        );
    }
}
