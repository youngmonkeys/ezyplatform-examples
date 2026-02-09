package org.youngmonkeys.personal.admin.event;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;
import org.youngmonkeys.ezyplatform.event.AbstractEventHandler;

import java.util.Map;

import static org.youngmonkeys.personal.constant.PersonalConstants.INTERNAL_EVENT_NAME_COIN_PRICE_UPDATE;

@EzySingleton
@AllArgsConstructor
public class AdminCoinPriceRequestEventHandler
    extends AbstractEventHandler<Map<String, Object>, Void> {

    @Override
    public String getEventName() {
        return INTERNAL_EVENT_NAME_COIN_PRICE_UPDATE;
    }
}
