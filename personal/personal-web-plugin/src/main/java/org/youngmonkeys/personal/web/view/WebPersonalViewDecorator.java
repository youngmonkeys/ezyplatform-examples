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

package org.youngmonkeys.personal.web.view;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyarticle.web.manager.WebPageFragmentManager;
import org.youngmonkeys.ezyplatform.web.controller.service.WebLanguageControllerService;
import org.youngmonkeys.ezyplatform.web.service.WebSettingService;
import org.youngmonkeys.ezyplatform.web.view.WebViewDecorator;

import javax.servlet.http.HttpServletRequest;

import static org.youngmonkeys.ezyplatform.util.StringConverters.trimOrNull;
import static org.youngmonkeys.ezysupport.constant.EzySupportConstants.SETTING_NAME_LOGO_WITHOUT_TEXT_URL;

@EzySingleton
@AllArgsConstructor
public class WebPersonalViewDecorator extends WebViewDecorator {

    private final WebPageFragmentManager pageFragmentManager;
    private final WebSettingService settingService;
    private final WebLanguageControllerService languageControllerService;

    @SuppressWarnings("MethodLength")
    @Override
    public void decorate(HttpServletRequest request, View view) {
        super.decorate(request, view);
        view.setVariable(
            "requestURI",
            request.getRequestURI()
        );
        view.setVariable(
            "siteLogoWithoutTextUrl",
            trimOrNull(
                settingService.getTextValue(
                    SETTING_NAME_LOGO_WITHOUT_TEXT_URL
                )
            )
        );
        String languageCode = languageControllerService
            .getLanguageCodeOrDefault(request);
        view.setVariable(
            "commonFragments",
            pageFragmentManager.getPageFragmentMap(
                "common",
                languageCode
            )
        );
    }

    @Override
    protected boolean includeWebLanguages() {
        return true;
    }
}
