package org.youngmonkeys.personal.web.controller.api;

import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;

@Controller("/api/v1")
public class LuckyWheelController {

    @DoGet("/personal")
    public Object info() {
        return EzyMapBuilder.mapBuilder()
            .put("name", "personal")
            .build();
    }
}
