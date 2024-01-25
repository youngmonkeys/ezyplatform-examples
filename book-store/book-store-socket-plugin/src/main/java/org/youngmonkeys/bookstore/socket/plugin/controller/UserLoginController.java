package org.youngmonkeys.bookstore.socket.plugin.controller;

import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {
	
	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		logger.info("{} login in", event.getUsername());
	}
}
