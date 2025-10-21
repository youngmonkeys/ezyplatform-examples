package org.youngmonkeys.personal.config;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import lombok.Setter;
import org.youngmonkeys.ezyarticle.sdk.manager.PageFragmentManager;

@Setter
public class PersonalPageFragmentConfig implements EzyBeanConfig {

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
            "content"
        );
        pageFragmentManager.registerFragmentNames(
            "blog_details",
            "content"
        );
    }
}
