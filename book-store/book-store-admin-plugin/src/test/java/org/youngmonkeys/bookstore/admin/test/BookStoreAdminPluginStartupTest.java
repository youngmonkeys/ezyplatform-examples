package org.youngmonkeys.bookstore.admin.test;

import com.tvd12.ezyhttp.server.boot.EzyHttpApplicationBootstrap;
import com.tvd12.ezyhttp.server.core.annotation.ComponentsScan;
import com.tvd12.ezyhttp.server.core.annotation.PropertiesSources;

@PropertiesSources({
    "config.properties",
    "setup.properties"
})
@ComponentsScan({
    "org.youngmonkeys.ezyplatform",
    "org.youngmonkeys.bookstore",
    "org.youngmonkeys.ezyarticle",
    "org.youngmonkeys.ecommerce",
    "org.youngmonkeys.ezypayment",
    "org.youngmonkeys.ezysupport",
    "org.youngmonkeys.ezymail",
    "org.youngmonkeys.ezylogin"
})
public class BookStoreAdminPluginStartupTest {

    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(BookStoreAdminPluginStartupTest.class);
    }
}
