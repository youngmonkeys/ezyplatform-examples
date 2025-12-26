/*
 * Copyright 2024 youngmonkeys.org
 * 
 * Licensed under the ezyplatform, Version 1.0.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://youngmonkeys.org/licenses/ezyplatform-1.0.0.txt
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.youngmonkeys.bookstore.web.test;

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
    "org.youngmonkeys.ezylogin",
    "org.youngmonkeys.ezyaccount",
    "org.youngmonkeys.ezyrating",
    "org.youngmonkeys.ezymarketing",
    "org.youngmonkeys.ezychat",
    "org.youngmonkeys.ezycrm"
})
public class BookStoreWebPluginStartupTest {

    public static void main(String[] args) throws Exception {
        EzyHttpApplicationBootstrap.start(BookStoreWebPluginStartupTest.class);
    }
}
