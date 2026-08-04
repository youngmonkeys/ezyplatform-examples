package org.youngmonkeys.sweet_bakery.admin.test;

import com.tvd12.ezyhttp.server.boot.EzyHttpApplicationBootstrap;
import com.tvd12.ezyhttp.server.core.annotation.ComponentsScan;
import com.tvd12.ezyhttp.server.core.annotation.PropertiesSources;

@PropertiesSources({
    "config.properties",
    "setup.properties"
})
@ComponentsScan({
    "org.youngmonkeys.ezyplatform",
    "org.youngmonkeys.sweet_bakery",
    "org.youngmonkeys.ezyarticle",
    "org.youngmonkeys.ecommerce",
    "org.youngmonkeys.ezymail",
    "org.youngmonkeys.ezysupport",
    "org.youngmonkeys.ezypayment",
    "org.youngmonkeys.ezydelivery",
    "org.youngmonkeys.accounting",
    "org.youngmonkeys.ezylogin"
})
public class SweetBakeryAdminPluginStartupTest {

    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(SweetBakeryAdminPluginStartupTest.class);
    }
}
