/*
 * Copyright 2022 youngmonkeys.org
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

package org.youngmonkeys.bookstore.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;

@EzySingleton
@AllArgsConstructor
public class ViewFactory {

    private final WebPageFragmentManager pageFragmentManager;

    public View.Builder newHomeViewBuilder(String language) {
        return View.builder()
            .template("home")
            .addVariable("pageTitle", "home")
            .addVariable(
                "fragments",
                pageFragmentManager.getPageFragmentMap(
                    "home",
                    language
                )
            );
    }
}
