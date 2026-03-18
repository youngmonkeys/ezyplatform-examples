package org.youngmonkeys.bookstore.web.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WebBookFlashSaleResponse {
    private long id;
    private String displayName;
    private long startApplyAtMs;
    private long finishApplyAtMs;
    private long serverNowMs;
    private List<WebBookResponse> books;
}
