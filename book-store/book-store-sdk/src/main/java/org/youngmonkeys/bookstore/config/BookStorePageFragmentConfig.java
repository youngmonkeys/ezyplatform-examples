package org.youngmonkeys.bookstore.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import lombok.Setter;
import org.youngmonkeys.ezyarticle.sdk.manager.PageFragmentManager;

@Setter
public class BookStorePageFragmentConfig implements EzyBeanConfig {

    @EzyAutoBind
    private PageFragmentManager pageFragmentManager;

    @Override
    public void config() {
        pageFragmentManager.registerFragmentNames(
            "common",
            "styles",
            "scripts",
            "header",
            "footer"
        );
        pageFragmentManager.registerFragmentNames(
            "home",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "blog_list",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "blog_details",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "news_list",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "news_details",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "posts",
            "container"
        );
        pageFragmentManager.registerFragmentNames(
            "post_details",
            "container"
        );
    }
}
