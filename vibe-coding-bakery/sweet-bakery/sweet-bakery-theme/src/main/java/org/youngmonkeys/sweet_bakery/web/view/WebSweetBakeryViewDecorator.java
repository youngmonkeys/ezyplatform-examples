package org.youngmonkeys.sweet_bakery.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import org.youngmonkeys.ezyplatform.web.view.WebViewDecorator;

@EzySingleton
public class WebSweetBakeryViewDecorator extends WebViewDecorator {

    @Override
    protected boolean includeWebLanguages() {
        return true;
    }
}
